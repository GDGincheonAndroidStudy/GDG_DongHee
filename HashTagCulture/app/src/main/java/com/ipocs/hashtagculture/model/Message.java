package com.ipocs.hashtagculture.model;

import java.io.Serializable;

/**
 * Created by Dong on 2015-12-15.
 */
public class Message implements Serializable{

    public static final int TYPE_MESSAGE_MY = 0;  //메시지
    public static final int TYPE_MESSAGE_OTHER = 1;
    public static final int TYPE_LOG = 2;
    public static final int TYPE_ENTER = 3;
    public static final int TYPE_EMOTION_MY = 4;  //이모티콘
    public static final int TYPE_EMOTION_OTHER = 5;

    private int type;
    private String message;
    private String userName;
    private int channel;
    private int emotion;
    private long time;

    private Message(){
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setEmotion(int emotion) {
        this.emotion = emotion;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEmotion() {
        return emotion;
    }

    public long getTime() {
        return time;
    }

    public String getName() {
        return userName;
    }

    public int getChannel() {
        return channel;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public static class Builder {
        private int mType;
        private String mUserName;
        private String mMessage;
        private int mChannel;
        private int mEmotion;
        private long mTime;

        public Builder emotion(int emotion) {
            mEmotion = emotion;
            return this;
        }

        public Builder time(long time) {
            mTime = time;
            return this;
        }

        public Builder type(int type) {
            mType = type;
            return this;
        }

        public Builder channel(int channel) {
            mChannel = channel;
            return this;
        }

        public Builder userName(String userName) {
            mUserName = userName;
            return this;
        }

        public Builder message(String message) {
            mMessage = message;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.type = mType;
            message.userName = mUserName;
            message.message = mMessage;
            message.channel = mChannel;
            message.time = mTime;
            message.emotion = mEmotion;
            return message;
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", message='" + message + '\'' +
                ", userName='" + userName + '\'' +
                ", channel=" + channel +
                ", emotion=" + emotion +
                ", time=" + time +
                '}';
    }
}
