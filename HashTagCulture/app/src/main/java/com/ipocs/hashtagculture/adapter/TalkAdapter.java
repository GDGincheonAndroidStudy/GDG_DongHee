package com.ipocs.hashtagculture.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipocs.hashtagculture.R;
import com.ipocs.hashtagculture.model.Message;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2015-12-15.
 */
public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.TalkViewHolder> {

    public static final String TAG = TalkAdapter.class.getSimpleName();

    public static final int[] emotionRes = {
            R.drawable.img_emotion_a, R.drawable.img_emotion_b, R.drawable.img_emotion_c, R.drawable.img_emotion_d, R.drawable.img_emotion_e,
            R.drawable.img_emotion_f, R.drawable.img_emotion_g, R.drawable.img_emotion_h, R.drawable.img_emotion_i, R.drawable.img_emotion_j
    };

    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private ArrayList<Message> mMessageArrayList;

    public TalkAdapter(Context context, ArrayList<Message> messageArrayList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mMessageArrayList = messageArrayList;
    }

    @Override
    public TalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;

        switch (viewType) {
            case Message.TYPE_MESSAGE_MY:
                Log.e(TAG, " TYPE_MESSAGE_MY");
                layout = R.layout.item_talk_message_my;
                break;
            case Message.TYPE_MESSAGE_OTHER:
                Log.e(TAG, " TYPE_MESSAGE_OTHER");
                layout = R.layout.item_talk_message_other;
                break;
            case Message.TYPE_EMOTION_MY:
                Log.e(TAG, " TYPE_EMOTION_MY");
                layout = R.layout.item_talk_emotion_my;
                break;
            case Message.TYPE_EMOTION_OTHER:
                Log.e(TAG, " TYPE_EMOTION_OTHER");
                layout = R.layout.item_talk_emotion_other;
                break;
            case Message.TYPE_ENTER:
                Log.e(TAG, " TYPE_ENTER");
                layout = R.layout.item_talk_enter;
                break;
        }
        View itemView = mLayoutInflater.inflate(layout, parent, false);

        return new TalkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TalkViewHolder holder, int position) {

        Message message = mMessageArrayList.get(position);

        Log.e(TAG, " position " + position);
        Log.e(TAG, " getType " + message.getType());

        switch (message.getType()) {
            case Message.TYPE_MESSAGE_MY:
                holder.tvMessage.setText(message.getMessage());
                //holder.tvTime.setText(TimeUtils.UnixTimeStampToStringTime(message.getTime()));
                break;
            case Message.TYPE_MESSAGE_OTHER:
                holder.tvUserName.setText(message.getUserName());
                holder.tvMessage.setText(message.getMessage());
//                holder.ivProfile.setImageResource();
                //holder.tvTime.setText(TimeUtils.UnixTimeStampToStringTime(message.getTime()));
                break;
            case Message.TYPE_EMOTION_MY:
                holder.ivEmotion.setImageResource(emotionRes[message.getEmotion()]);
                //holder.tvTime.setText(TimeUtils.UnixTimeStampToStringTime(message.getTime()));
                break;
            case Message.TYPE_EMOTION_OTHER:
                holder.tvUserName.setText(message.getUserName());
                holder.ivEmotion.setImageResource(emotionRes[message.getEmotion()]);
//                holder.ivProfile.setImageResource();
                //holder.tvTime.setText(TimeUtils.UnixTimeStampToStringTime(message.getTime()));
                break;
            case Message.TYPE_ENTER:
                holder.tvUserName.setText(message.getUserName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessageArrayList.get(position).getType();
    }

    public static class TalkViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imageView_profile)
        @Nullable
        ImageView ivProfile;

        @Bind(R.id.imageView_emotion)
        @Nullable
        ImageView ivEmotion;

        @Bind(R.id.textView_user_name)
        @Nullable
        TextView tvUserName;

        @Bind(R.id.textView_message)
        @Nullable
        TextView tvMessage;
        @Bind(R.id.textView_time)
        @Nullable
        TextView tvTime;

        View mView;

        public TalkViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            ButterKnife.bind(this, mView);
        }
    }
}
