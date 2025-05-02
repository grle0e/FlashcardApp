package com.flashcards;

public class ConfidentAnswerAchievement implements Achievement {
    private boolean achieved = false;
    
    @Override
    public String getName() {
        return "CONFIDENT";
    }
    
    @Override
    public String getDescription() {
        return "Answered one card correctly at least 3 times";
    }
    
    @Override
    public boolean isAchieved() {
        return achieved;
    }
    
    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }
}