package com.flashcards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void testWorstFirst() {
        Card card1 = new Card("Q1", "A1"); // 50% зөв
        card1.incrementAsked();
        card1.markCorrect();
        card1.incrementAsked();
        card1.markIncorrect();

        Card card2 = new Card("Q2", "A2"); // 0% зөв
        card2.incrementAsked();
        card2.markIncorrect();
        card2.incrementAsked();
        card2.markIncorrect();

        List<Card> cards = Arrays.asList(card1, card2);

        CardOrganizer sorter = new WorstFirstSorter();
        List<Card> sorted = sorter.organize(cards);

        assertEquals("Q2", sorted.get(0).getQuestion()); // хамгийн муу үнэлгээтэй
        assertEquals("Q1", sorted.get(1).getQuestion());
    }
}
