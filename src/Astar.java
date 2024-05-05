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
import java.util.Set;

public class Astar {
    public String start;
    public String end;
    public Set<String> dictionary;
    public int total;

    public Astar(String a, String b, String dictionaryFilePath) throws IOException {
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

    public int heuristic(String word, String endWord) {
        int diffCount = 0;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != endWord.charAt(i)) {
                diffCount++;
            }
        }

        return diffCount;
    }
    
    public List<String> reconstructPath(Map<String, String> parentMap, String endWord) {
        List<String> ladder = new ArrayList<>();

        String currentWord = endWord;
        while (currentWord != null) {
            ladder.add(0, currentWord);
            currentWord = parentMap.get(currentWord);
        }

        return ladder;
    }

    public String[] findAstar(String startWord, String endWord, Set<String> wordList) {
        PriorityQueue<NodeH> openSet = new PriorityQueue<>(Comparator.comparingInt(NodeH::getTotalCost));
        Set<String> closedSet = new HashSet<>();
        Map<String, Integer> costMap = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>();
        
        NodeH startNode = new NodeH(startWord, 0, heuristic(startWord, endWord));
        openSet.add(startNode);
        costMap.put(startWord, 0);
        parentMap.put(startWord, null);
        this.total = 2;
        
        while (!openSet.isEmpty()) {
            NodeH currentNode = openSet.poll();
            String currentWord = currentNode.getWord();
            
            if (currentWord.equals(endWord)) {
                List<String> ladder = reconstructPath(parentMap, endWord);
                return ladder.toArray(new String[0]);
            }
            closedSet.add(currentWord);
            
            for (String neighbor : wordList) {
                if (!closedSet.contains(neighbor) && isOneLetterDifference(currentWord, neighbor)) {
                    int newCost = costMap.get(currentWord) + 1;
                    if (!costMap.containsKey(neighbor) || newCost < costMap.get(neighbor)) {
                        costMap.put(neighbor, newCost);
                        parentMap.put(neighbor, currentWord);
                        NodeH neighborNode = new NodeH(neighbor, newCost, heuristic(neighbor, endWord));
                        openSet.add(neighborNode);
                        this.total++;
                    }
                }
            }
        }

        return new String[]{"No ladder found"};
    }
    
    
}
