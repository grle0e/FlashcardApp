package com.flashcards;

import java.util.ArrayList;
import java.util.List;

public class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public List<Card> organize(List<Card> cards) {
        List<Card> result = new ArrayList<>();
        List<Card> wrongCards = new ArrayList<>();
        List<Card> correctCards = new ArrayList<>();
        
        // Separate cards into wrong and correct ones
        for (Card card : cards) {
            if (card.wasWrongLastTime()) {
                wrongCards.add(card);
            } else {
                correctCards.add(card);
            }
        }
        
        // Combine lists - wrong cards first, then correct ones
        result.addAll(wrongCards);
        result.addAll(correctCards);
        
        return result;
    }
}
