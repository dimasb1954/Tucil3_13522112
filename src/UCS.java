import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class UCS {
    public String start;
    public String end;
    public Set<String> dictionary;

    public UCS(String a, String b, String dictionaryFilePath) throws IOException {
        this.start = a;
        this.end = b;
        this.dictionary = loadDictionary(dictionaryFilePath, a.length());
    }

    private Set<String> loadDictionary(String filePath, int length) throws IOException {
        Set<String> dictionary = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == length){
                    dictionary.add(line.trim().toLowerCase());
                }
            }
        }

        return dictionary;
    }

    public boolean isOneLetterDifference(String word1, String word2) {
        int diffCount = 0;

        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diffCount++;
            }
            if (diffCount > 1) {
                return false;
            }
        }

        return diffCount == 1;
    }

    public String[] findUCS(String startWord, String endWord, Set<String> wordList) {
        Queue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        Set<String> exploredSet = new HashSet<>();
        Map<String, Integer> costMap = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>();
        
        Node startNode = new Node(startWord);
        priorityQueue.add(startNode);
        costMap.put(startWord, 0);
        parentMap.put(startWord, null);
        
        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            String currentWord = currentNode.getWord();
            int currentCost = costMap.get(currentWord);
            
            if (currentWord.equals(endWord)) {
                List<String> ladder = new ArrayList<>();
                while (currentWord != null) {
                    ladder.add(0, currentWord);
                    currentWord = parentMap.get(currentWord);
                }

                return ladder.toArray(new String[0]);
            }
            
            exploredSet.add(currentWord);
            
            for (String neighbor : wordList) {
                if (!exploredSet.contains(neighbor) && isOneLetterDifference(currentWord, neighbor)) {
                    int newCost = currentCost + 1;

                    if (!costMap.containsKey(neighbor) || newCost < costMap.get(neighbor)) {
                        costMap.put(neighbor, newCost);
                        parentMap.put(neighbor, currentWord);
                        Node neighborNode = new Node(neighbor);
                        neighborNode.setCost(newCost);
                        priorityQueue.add(neighborNode);
                    }
                }
            }
        }
        
        return new String[]{"No ladder found"};
    }
}
