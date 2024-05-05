import java.io.IOException;
import java.util.Scanner;

public class App {
       public static void main(String[] args) {
        String dictionaryFilePath = "words_alpha.txt";
        
        try (Scanner myObj = new Scanner(System.in)) {
            System.out.println("===================");
            System.out.println("WORLD LADDER SOLVER");
            System.out.println("   BY : 13522112   \n");
            
            System.out.println("Enter Start Word");
            String wordStart = myObj.nextLine();

            System.out.println();

            System.out.println("Enter End Word");
            String wordEnd = myObj.nextLine();

            try {
                WordValidator validator = new WordValidator(dictionaryFilePath);
                if (validator.isValidWord(wordStart) && validator.isValidWord(wordEnd)) {
                    if (wordStart.length() != wordEnd.length()){
                        System.out.println("\nBoth words must be in the same length\n");

                    } else {
                        if (wordEnd.equals(wordStart)){
                            System.out.println("\nMake sure the words are different\n");

                        } else {
                            System.out.println("\nSelect Your Algorithm");
                            System.out.println("1. UCS");
                            System.out.println("2. Greedy Best First Search");
                            System.out.println("3. A*");
                            System.out.println("\nEnter Your Algorithm (1/2/3)");
                            Integer opt = myObj.nextInt();

                            if (opt == 1){
                                long startTime = System.nanoTime();
                                UCS ucs = new UCS(wordStart, wordEnd, dictionaryFilePath);
                                String[] ladderUCS = ucs.findUCS(wordStart, wordEnd, ucs.dictionary);
                                System.out.println("\nWord ladder from " + wordStart + " to " +   wordEnd + ":");
                                for (String word : ladderUCS) {
                                    System.out.println(word);
                                }
                                System.out.println();
                                long endTime = System.nanoTime();
                                long duration = (endTime - startTime);
                                System.out.println("With the total of " + ucs.total + " nodes");
                                System.out.println("It takes " + duration/1000000 + " ms");
                                
                            } else if (opt == 2){
                                long startTime = System.nanoTime();
                                GreedyBFS GBFS = new GreedyBFS(wordStart, wordEnd, dictionaryFilePath);
                                String[] ladderGBFS = GBFS.findGBFS(wordStart, wordEnd, GBFS.dictionary);
                                System.out.println("\nWord ladder from " + wordStart + " to " +   wordEnd + ":");
                                for (String word : ladderGBFS) {
                                    System.out.println(word);
                                }
                                System.out.println();                 
                                long endTime = System.nanoTime();
                                long duration = (endTime - startTime);
                                System.out.println("With the total of " + GBFS.total + " nodes");
                                System.out.println("It takes " + duration/1000000 + " ms");

                            } else if (opt == 3){
                                long startTime = System.nanoTime();
                                Astar astar = new Astar(wordStart, wordEnd, dictionaryFilePath);
                                String[] ladderAstar = astar.findAstar(wordStart, wordEnd, astar.dictionary);
                                System.out.println("\nWord ladder from " + wordStart + " to " +   wordEnd + ":");
                                for (String word : ladderAstar) {
                                    System.out.println(word);
                                }
                                System.out.println();
                                long endTime = System.nanoTime();
                                long duration = (endTime - startTime);
                                System.out.println("With the total of " + astar.total + " nodes");
                                System.out.println("It takes " + duration/1000000 + " ms");

                            } else {
                                System.out.println("\nNot a valid option");
                            }
                        }
                    }

                } else {
                    if(!validator.isValidWord(wordStart) && validator.isValidWord(wordEnd)){
                        System.out.println("\n" + wordStart + " is not a valid word.\n");

                    } else if (validator.isValidWord(wordStart) && !validator.isValidWord(wordEnd)){
                        System.out.println("\n" + wordEnd + " is not a valid word.\n");

                    } else if (!validator.isValidWord(wordStart) && !validator.isValidWord(wordEnd)){
                        System.out.println("\nBoth words are not valid words.\n");
                    }
                }

            } catch (IOException e) {
                System.err.println("Error loading dictionary: " + e.getMessage());
            }
        }
    }
}
