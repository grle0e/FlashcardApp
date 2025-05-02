package com.flashcards;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecentMistakesFirstSorter implements CardOrganizer {
    private final LinkedList<Card> recentMistakes = new LinkedList<>();

    @Override
    public List<Card> organize(List<Card> cards) {
        List<Card> result = new ArrayList<>();

        // Шинээр буруу хариулсан картуудыг List-ийн эхэнд нэмнэ
        for (Card card : cards) {
            if (card.wasWrongLastTime()) {
                // давхардахаас сэргийлнэ
                recentMistakes.remove(card); 
                recentMistakes.addFirst(card);
            }
        }

        // recentMistakes-д байгаа картуудыг эхэнд нэмэх
        for (Card card : recentMistakes) {
            if (cards.contains(card) && !result.contains(card)) {
                result.add(card);
            }
        }

        // бусад зөв хариулсан картуудыг дараа нь нэмэх
        for (Card card : cards) {
            if (!result.contains(card)) {
                result.add(card);
            }
        }

        return result;
    }
}
