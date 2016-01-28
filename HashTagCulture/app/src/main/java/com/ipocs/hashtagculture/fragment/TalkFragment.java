package com.ipocs.hashtagculture.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ipocs.hashtagculture.R;
import com.ipocs.hashtagculture.adapter.TalkAdapter;
import com.ipocs.hashtagculture.model.Message;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TalkFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = TalkFragment.class.getSimpleName();

    private WebSocketClient mWebSocketClient;

    @Bind(R.id.editText_message)
    EditText etMessage;

    @Bind(R.id.imageButton_send)
    ImageButton ibtnSend;

    @Bind(R.id.imageButton_emotion)
    ImageButton ibtnEmotionShow;

    @Bind(R.id.recyclerView_talk)
    RecyclerView rvTalk;

    @Bind(R.id.linear_container_emotion)
    LinearLayout linearEmotionContainer;

    @Bind(R.id.imageView_emotion_a)
    ImageView ibtnEmotion1;
    @Bind(R.id.imageView_emotion_b)
    ImageView ibtnEmotion2;
    @Bind(R.id.imageView_emotion_c)
    ImageView ibtnEmotion3;
    @Bind(R.id.imageView_emotion_d)
    ImageView ibtnEmotion4;
    @Bind(R.id.imageView_emotion_e)
    ImageView ibtnEmotion5;
    @Bind(R.id.imageView_emotion_f)
    ImageView ibtnEmotion6;
    @Bind(R.id.imageView_emotion_g)
    ImageView ibtnEmotion7;
    @Bind(R.id.imageView_emotion_h)
    ImageView ibtnEmotion8;
    @Bind(R.id.imageView_emotion_i)
    ImageView ibtnEmotion9;
    @Bind(R.id.imageView_emotion_j)
    ImageView ibtnEmotion10;

    ArrayList<Message> mMessageArrayList = new ArrayList<>();
    TalkAdapter mTalkAdapter;

    boolean emotionShowFlag = false;

    public static TalkFragment newInstance() {
        TalkFragment fragment = new TalkFragment();
        return fragment;
    }

    public TalkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTalkAdapter = new TalkAdapter(getActivity(), mMessageArrayList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talk, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        connectWebSocket();

        rvTalk.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTalk.setAdapter(mTalkAdapter);

        etMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.send || id == EditorInfo.IME_NULL) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        ibtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        ibtnEmotionShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emotionShowFlag = !emotionShowFlag;

                if (emotionShowFlag) {
                    linearEmotionContainer.setVisibility(View.VISIBLE);
                } else {
                    linearEmotionContainer.setVisibility(View.GONE);
                }
            }
        });

        Log.e("fragment", TAG + " onViewCreated");
    }

    private void scrollToBottom() {
        rvTalk.scrollToPosition(mTalkAdapter.getItemCount() - 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebSocketClient.close();
    }

    private void connectWebSocket() {
        URI uri;
        try {
            // uri = new URI("ws://websockethost:8080");
            uri = new URI("ws://cultalk.herokuapp.com");

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.e(TAG, "Opened");
//                mWebSocketClient.send("Hello from" + Build.MANUFACTURER + " " + Build.MODEL);

                Message message = new Message.Builder()
                        .channel(categoryCode)
                        .emotion(-1)
                        .type(Message.TYPE_ENTER)
                        .userName(hostActivity.getPreferenceUid())
                        .build();

                String strJson = new Gson().toJson(message);

//                mWebSocketClient.send("{\"handle\":\"Dong\",\"text\":\"나 로그인함\"}");

                mWebSocketClient.send(strJson);
            }

            @Override
            public void onMessage(final String message) {

                Log.e(TAG, " onMessage message" + message);

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Message recMessage = new Gson().fromJson(message, Message.class);

                        Log.e(TAG, " recMessage " + recMessage);

                        if (recMessage.getEmotion() > -1) {
                            if (hostActivity.getPreferenceUid().equals(recMessage.getUserName())) {
                                recMessage.setType(Message.TYPE_EMOTION_MY);
                            } else {
                                recMessage.setType(Message.TYPE_EMOTION_OTHER);
                            }
                        } else if (recMessage.getType() == Message.TYPE_ENTER) {

                        } else {
                            if (hostActivity.getPreferenceUid().equals(recMessage.getUserName())) {
                                recMessage.setType(Message.TYPE_MESSAGE_MY);
                            } else {
                                recMessage.setType(Message.TYPE_MESSAGE_OTHER);
                            }
                        }

                        mMessageArrayList.add(recMessage);
                        mTalkAdapter.notifyItemInserted(mMessageArrayList.size() - 1);

                        scrollToBottom();
                    }
                });
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.e(TAG, "onClose " + reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.i(TAG, "onError" + ex.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    public void sendMessage() {

        Message message = new Message.Builder()
                .channel(super.getCategoryCode())
                .emotion(-1)
                .message(etMessage.getText().toString())
                .userName(super.getHostActivity().getPreferenceUid())
                .build();

        String strJson = new Gson().toJson(message);

        Log.e(TAG, " strJson " + strJson);

        mWebSocketClient.send(strJson);
        etMessage.setText("");
    }

    public void sendEmotion(int emotionId) {

        Message message = new Message.Builder()
                .channel(super.getCategoryCode())
                .emotion(emotionId)
                .userName(super.getHostActivity().getPreferenceUid())
                .build();

        String strJson = new Gson().toJson(message);

        Log.e(TAG, " strJson " + strJson);

        mWebSocketClient.send(strJson);
        etMessage.setText("");
    }

    @OnClick({R.id.imageView_emotion_a, R.id.imageView_emotion_b, R.id.imageView_emotion_c, R.id.imageView_emotion_d, R.id.imageView_emotion_e, R.id.imageView_emotion_f, R.id.imageView_emotion_g, R.id.imageView_emotion_h, R.id.imageView_emotion_i, R.id.imageView_emotion_j})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_emotion_a:
                sendEmotion(0);
                break;
            case R.id.imageView_emotion_b:
                sendEmotion(1);
                break;
            case R.id.imageView_emotion_c:
                sendEmotion(2);
                break;
            case R.id.imageView_emotion_d:
                sendEmotion(3);
                break;
            case R.id.imageView_emotion_e:
                sendEmotion(4);
                break;
            case R.id.imageView_emotion_f:
                sendEmotion(5);
                break;
            case R.id.imageView_emotion_g:
                sendEmotion(6);
                break;
            case R.id.imageView_emotion_h:
                sendEmotion(7);
                break;
            case R.id.imageView_emotion_i:
                sendEmotion(8);
                break;
            case R.id.imageView_emotion_j:
                sendEmotion(9);
                break;
        }
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.e("fragment", TAG + " onAttach");
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.e("fragment", TAG +  " onDestroyView");
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.e("fragment", TAG + " onDetach");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.e("fragment", TAG + " onDestroy");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.e("fragment", TAG + " onResume");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.e("fragment", TAG + " onPause");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.e("fragment", TAG + " onPause");
//    }
}
