package com.zybooks.studyhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class QuestionEditActivity extends AppCompatActivity {

    public static final String EXTRA_QUESTION_ID = "com.zybooks.studyhelper.question_id";
    public static final String EXTRA_SUBJECT = "com.zybooks.studyhelper.subject";

    private EditText mQuestionText;
    private EditText mAnswerText;

    private StudyDatabase mStudyDb;
    private long mQuestionId;
    private Question mQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_edit);

        mQuestionText = findViewById(R.id.questionText);
        mAnswerText = findViewById(R.id.answerText);

        mStudyDb = StudyDatabase.getInstance(getApplicationContext());

        // Get question ID from QuestionActivity
        Intent intent = getIntent();
        mQuestionId = intent.getLongExtra(EXTRA_QUESTION_ID, -1);

        ActionBar actionBar = getSupportActionBar();

        if (mQuestionId == -1) {
            // Add new question
            mQuestion = new Question();
            setTitle(R.string.add_question);
        }
        else {
            // Update existing question
            mQuestion = mStudyDb.getQuestion(mQuestionId);
            mQuestionText.setText(mQuestion.getText());
            mAnswerText.setText(mQuestion.getAnswer());
            setTitle(R.string.update_question);
        }

        String subject = intent.getStringExtra(EXTRA_SUBJECT);
        mQuestion.setSubject(subject);
    }

    public void saveButtonClick(View view) {

        mQuestion.setText(mQuestionText.getText().toString());
        mQuestion.setAnswer(mAnswerText.getText().toString());

        if (mQuestionId == -1) {
            // New question
            mStudyDb.addQuestion(mQuestion);
        } else {
            // Existing question
            mStudyDb.updateQuestion(mQuestion);
        }

        // Send back question ID
        Intent intent = new Intent();
        intent.putExtra(EXTRA_QUESTION_ID, mQuestion.getId());
        setResult(RESULT_OK, intent);
        finish();
    }
}