package com.zybooks.studyhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import java.util.ArrayList;
import java.util.List;

public class StudyDatabase extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "study.db";

    private static StudyDatabase mStudyDb;

    public enum SubjectSortOrder { ALPHABETIC, UPDATE_DESC, UPDATE_ASC };

    public static StudyDatabase getInstance(Context context) {
        if (mStudyDb == null) {
            mStudyDb = new StudyDatabase(context);
        }
        return mStudyDb;
    }

    private StudyDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class SubjectTable {
        private static final String TABLE = "subjects";
        private static final String COL_TEXT = "text";
        private static final String COL_UPDATE_TIME = "updated";
    }

    private static final class QuestionTable {
        private static final String TABLE = "questions";
        private static final String COL_ID = "_id";
        private static final String COL_TEXT = "text";
        private static final String COL_ANSWER = "answer";
        private static final String COL_SUBJECT = "subject";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create subjects table
        db.execSQL("create table " + SubjectTable.TABLE + " (" +
                SubjectTable.COL_TEXT + " primary key, " +
                SubjectTable.COL_UPDATE_TIME + " int)");

        // Create questions table with foreign key that cascade deletes
        db.execSQL("create table " + QuestionTable.TABLE + " (" +
                QuestionTable.COL_ID + " integer primary key autoincrement, " +
                QuestionTable.COL_TEXT + ", " +
                QuestionTable.COL_ANSWER + ", " +
                QuestionTable.COL_SUBJECT + ", " +
                "foreign key(" + QuestionTable.COL_SUBJECT + ") references " +
                SubjectTable.TABLE + "(" + SubjectTable.COL_TEXT + ") on delete cascade)");

        // Add some subjects
        String[] subjects = { "History", "Math", "Computing" };
        for (String sub: subjects) {
            Subject subject = new Subject(sub);
            ContentValues values = new ContentValues();
            values.put(SubjectTable.COL_TEXT, subject.getText());
            values.put(SubjectTable.COL_UPDATE_TIME, subject.getUpdateTime());
            db.insert(SubjectTable.TABLE, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + SubjectTable.TABLE);
        db.execSQL("drop table if exists " + QuestionTable.TABLE);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                db.execSQL("pragma foreign_keys = on;");
            } else {
                db.setForeignKeyConstraintsEnabled(true);
            }
        }
    }

    public List<Subject> getSubjects(SubjectSortOrder order) {
        List<Subject> subjects = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String orderBy;
        switch (order) {
            case ALPHABETIC:
                orderBy = SubjectTable.COL_TEXT + " collate nocase";
                break;
            case UPDATE_DESC:
                orderBy = SubjectTable.COL_UPDATE_TIME + " desc";
                break;
            default:
                orderBy = SubjectTable.COL_UPDATE_TIME + " asc";
                break;
        }

        String sql = "select * from " + SubjectTable.TABLE + " order by " + orderBy;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Subject subject = new Subject();
                subject.setText(cursor.getString(0));
                subject.setUpdateTime(cursor.getLong(1));
                subjects.add(subject);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return subjects;
    }

    public boolean addSubject(Subject subject) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SubjectTable.COL_TEXT, subject.getText());
        values.put(SubjectTable.COL_UPDATE_TIME, subject.getUpdateTime());
        long id = db.insert(SubjectTable.TABLE, null, values);
        return id != -1;
    }

    public void updateSubject(Subject subject) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SubjectTable.COL_TEXT, subject.getText());
        values.put(SubjectTable.COL_UPDATE_TIME, subject.getUpdateTime());
        db.update(SubjectTable.TABLE, values,
                SubjectTable.COL_TEXT + " = ?", new String[] { subject.getText() });
    }

    public void deleteSubject(Subject subject) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(SubjectTable.TABLE,
                SubjectTable.COL_TEXT + " = ?", new String[] { subject.getText() });
    }

    public List<Question> getQuestions(String subject) {
        List<Question> questions = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + QuestionTable.TABLE +
                " where " + QuestionTable.COL_SUBJECT + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { subject });
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setText(cursor.getString(1));
                question.setAnswer(cursor.getString(2));
                question.setSubject(cursor.getString(3));
                questions.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return questions;
    }

    public Question getQuestion(long questionId) {
        Question question = null;

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + QuestionTable.TABLE +
                " where " + QuestionTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { Float.toString(questionId) });

        if (cursor.moveToFirst()) {
            question = new Question();
            question.setId(cursor.getInt(0));
            question.setText(cursor.getString(1));
            question.setAnswer(cursor.getString(2));
            question.setSubject(cursor.getString(3));
        }

        return question;
    }

    public void addQuestion(Question question) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuestionTable.COL_TEXT, question.getText());
        values.put(QuestionTable.COL_ANSWER, question.getAnswer());
        values.put(QuestionTable.COL_SUBJECT, question.getSubject());
        long questionId = db.insert(QuestionTable.TABLE, null, values);
        question.setId(questionId);

        // Change update time in subjects table
        updateSubject(new Subject(question.getSubject()));
    }

    public void updateQuestion(Question question) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuestionTable.COL_ID, question.getId());
        values.put(QuestionTable.COL_TEXT, question.getText());
        values.put(QuestionTable.COL_ANSWER, question.getAnswer());
        values.put(QuestionTable.COL_SUBJECT, question.getSubject());
        db.update(QuestionTable.TABLE, values,
                QuestionTable.COL_ID + " = " + question.getId(), null);

        // Change update time in subjects table
        updateSubject(new Subject(question.getSubject()));
    }

    public void deleteQuestion(long questionId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(QuestionTable.TABLE,
                QuestionTable.COL_ID + " = " + questionId, null);
    }



}
