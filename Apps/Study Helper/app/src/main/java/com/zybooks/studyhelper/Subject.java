package com.zybooks.studyhelper;

public class Subject {

    private String mText;
    private long mUpdateTime;

    public Subject() {}

    public Subject(String text) {
        mText = text;
        mUpdateTime = System.currentTimeMillis();
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public long getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(long updateTime) {
        mUpdateTime = updateTime;
    }
}
