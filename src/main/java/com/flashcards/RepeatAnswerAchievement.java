package com.flashcards;

public class RepeatAnswerAchievement implements Achievement {
    private boolean achieved = false;
    
    @Override
    public String getName() {
        return "REPEAT";
    }
    
    @Override
    public String getDescription() {
        return "Answered one card more than 5 times";
    }
    
    @Override
    public boolean isAchieved() {
        return achieved;
    }
    
    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }
}
