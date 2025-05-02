package com.flashcards;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlashcardApp {
    private static final String HELP_MESSAGE = "Usage: flashcard <cards-file> [options]\n" +
            "Options:\n" +
            "--help                       Display this help message\n" +
            "--order <order>              Specify the card order (random, worst-first, recent-mistakes-first)\n" +
            "--repetitions <num>          Number of times each card must be answered correctly\n" +
            "--invertCards                Swap question and answer for each card";

    private static List<Card> cards = new ArrayList<>();
    private static List<Achievement> achievements = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Missing cards file!");
            System.out.println(HELP_MESSAGE);
            return;
        }

        String cardsFile = "";
        String order = "random";
        int repetitions = 1;
        boolean invertCards = false;
        
        // Parse command line arguments
        for (int i = 0; i < args.length; i++) {
            if (i == 0 && !args[i].startsWith("--")) {
                cardsFile = args[i];
            } else if (args[i].equals("--help")) {
                System.out.println(HELP_MESSAGE);
                return;
            } else if (args[i].equals("--order") && i + 1 < args.length) {
                order = args[++i];
                if (!order.equals("random") && !order.equals("worst-first") && !order.equals("recent-mistakes-first")) {
                    System.out.println("Invalid order type: " + order);
                    System.out.println(HELP_MESSAGE);
                    return;
                }
            } else if (args[i].equals("--repetitions") && i + 1 < args.length) {
                try {
                    repetitions = Integer.parseInt(args[++i]);
                    if (repetitions <= 0) {
                        System.out.println("Repetitions must be a positive number");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid repetitions value: " + args[i]);
                    return;
                }
            } else if (args[i].equals("--invertCards")) {
                invertCards = true;
            } else {
                System.out.println("Unknown option: " + args[i]);
                System.out.println(HELP_MESSAGE);
                return;
            }
        }

        // Initialize achievements
        QuickAnswerAchievement quickAchievement = new QuickAnswerAchievement();
        CorrectAnswerAchievement correctAchievement = new CorrectAnswerAchievement();
        RepeatAnswerAchievement repeatAchievement = new RepeatAnswerAchievement();
        ConfidentAnswerAchievement confidentAchievement = new ConfidentAnswerAchievement();
        
        achievements.add(quickAchievement);
        achievements.add(correctAchievement);
        achievements.add(repeatAchievement);
        achievements.add(confidentAchievement);

        // Load cards from file
        try {
            loadCards(cardsFile, invertCards);
        } catch (IOException e) {
            System.out.println("Error loading cards file: " + e.getMessage());
            return;
        }

        if (cards.isEmpty()) {
            System.out.println("No cards loaded from file!");
            return;
        }

        // Select card organizer
        CardOrganizer organizer;
        switch (order) {
            case "worst-first":
                organizer = new WorstFirstSorter();
                break;
            case "recent-mistakes-first":
                organizer = new RecentMistakesFirstSorter();
                break;
            case "random":
            default:
                organizer = new RandomSorter();
                break;
        }

        // Main flashcard loop
        boolean allCardsCompleted = false;
        while (!allCardsCompleted) {
            List<Card> organizedCards = organizer.organize(cards);
            
            int totalCorrect = 0;
            long totalAnswerTime = 0;
            int totalAnswers = 0;
            boolean allCorrectThisRound = true;
            
            for (Card card : organizedCards) {
                if (card.getTimesCorrect() >= repetitions) {
                    continue;
                }
                
                System.out.println("\nQuestion: " + card.getQuestion());
                System.out.print("Your answer: ");
                
                long startTime = System.currentTimeMillis();
                String answer = scanner.nextLine().trim();
                long endTime = System.currentTimeMillis();
                
                long answerTime = (endTime - startTime) / 1000;
                totalAnswerTime += answerTime;
                totalAnswers++;
                
                card.incrementAsked();
                card.setLastAnswerTime(answerTime);
                
                if (answer.equalsIgnoreCase(card.getAnswer())) {
                    System.out.println("Correct! (Time: " + answerTime + "s)");
                    card.markCorrect();
                    totalCorrect++;
                    
                    // Check for achievements
                    if (card.getTimesAsked() > 5) {
                        repeatAchievement.setAchieved(true);
                    }
                    
                    if (card.getTimesCorrect() >= 3) {
                        confidentAchievement.setAchieved(true);
                    }
                } else {
                    System.out.println("Incorrect. The correct answer is: " + card.getAnswer());
                    card.markIncorrect();
                    allCorrectThisRound = false;
                }
            }
            
            // Check if all cards have been answered correctly the required number of times
            allCardsCompleted = true;
            for (Card card : cards) {
                if (card.getTimesCorrect() < repetitions) {
                    allCardsCompleted = false;
                    break;
                }
            }
            
            // Update achievements
            if (totalAnswers > 0) {
                double avgTime = (double) totalAnswerTime / totalAnswers;
                if (avgTime < 5.0) {
                    quickAchievement.setAchieved(true);
                }
            }
            
            if (allCorrectThisRound && totalAnswers > 0) {
                correctAchievement.setAchieved(true);
            }
            
            // Display achievements
            System.out.println("\n--- Achievements ---");
            for (Achievement achievement : achievements) {
                if (achievement.isAchieved()) {
                    System.out.println(achievement.getName() + ": " + achievement.getDescription());
                }
            }
            
            if (!allCardsCompleted) {
                System.out.println("\nPress Enter to continue to the next round...");
                scanner.nextLine();
            }
        }
        
        System.out.println("\nCongratulations! You have completed all cards.");
    }
    
    private static void loadCards(String filename, boolean invertCards) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                if (parts.length == 2) {
                    String question = parts[0].trim();
                    String answer = parts[1].trim();
                    
                    if (invertCards) {
                        // Swap question and answer
                        String temp = question;
                        question = answer;
                        answer = temp;
                    }
                    
                    cards.add(new Card(question, answer));
                }
            }
        }
    }
}
