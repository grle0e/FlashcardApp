package com.flashcards;

public class Card {
    private String question;
    private String answer;
    private int timesAsked = 0;
    private int timesCorrect = 0;
    private boolean wasWrongLastTime = false;
    private long lastAnswerTime = 0;

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getTimesAsked() {
        return timesAsked;
    }

    public int getTimesCorrect() {
        return timesCorrect;
    }

    public boolean wasWrongLastTime() {
        return wasWrongLastTime;
    }

    public void incrementAsked() {
        timesAsked++;
    }

    public void markCorrect() {
        timesCorrect++;
        wasWrongLastTime = false;
    }

    public void markIncorrect() {
        wasWrongLastTime = true;
    }

    public void setLastAnswerTime(long time) {
        this.lastAnswerTime = time;
    }

    public long getLastAnswerTime() {
        return lastAnswerTime;
    }
}