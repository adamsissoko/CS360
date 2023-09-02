package com.zybooks.studyhelper;

public class Question {

    private long mId;
    private String mText;
    private String mAnswer;
    private String mSubject;

    public String getText() {
        return mText;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        this.mAnswer = answer;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        this.mSubject = subject;
    }
}
