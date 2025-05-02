package com.flashcards;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WorstFirstSorter implements CardOrganizer {
    @Override
    public List<Card> organize(List<Card> cards) {
        List<Card> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator.comparingDouble(card -> {
            if (card.getTimesAsked() == 0) return 0;
            return (double) card.getTimesCorrect() / card.getTimesAsked();
        }));
        return sorted;
    }
}