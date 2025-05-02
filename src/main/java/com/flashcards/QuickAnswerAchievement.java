package com.flashcards;

public class QuickAnswerAchievement implements Achievement {
    private boolean achieved = false;
    
    @Override
    public String getName() {
        return "QUICK";
    }
    
    @Override
    public String getDescription() {
        return "Answered with an average time of less than 5 seconds per card";
    }
    
    @Override
    public boolean isAchieved() {
        return achieved;
    }
    
    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }
}