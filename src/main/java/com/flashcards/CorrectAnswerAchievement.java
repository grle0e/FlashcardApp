package com.flashcards;

public class CorrectAnswerAchievement implements Achievement {
    private boolean achieved = false;
    
    @Override
    public String getName() {
        return "CORRECT";
    }
    
    @Override
    public String getDescription() {
        return "All cards answered correctly in the last round";
    }
    
    @Override
    public boolean isAchieved() {
        return achieved;
    }
    
    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }
}
