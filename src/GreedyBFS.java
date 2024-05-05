import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GreedyBFS {
    public String start;
    public String end;
    public Set<String> dictionary;
    public int total;

    public GreedyBFS(String a, String b, String dictionaryFilePath) throws IOException {
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
    
    public List<String> reconstructPath(Map<String, String> parentMap, String endWord) {
        List<String> ladder = new ArrayList<>();
        String currentWord = endWord;
        while (currentWord != null) {
            ladder.add(0, currentWord);
            currentWord = parentMap.get(currentWord);
        }

        return ladder;
    }

    public String[] findGBFS(String startWord, String endWord, Set<String> wordList) {
        Queue<Node> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();
        
        Node startNode = new Node(startWord);
        queue.add(startNode);
        visited.add(startWord);
        parentMap.put(startWord, null);
        this.total = 2;
        
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            String currentWord = currentNode.getWord();
            
            if (currentWord.equals(endWord)) {
                List<String> ladder = reconstructPath(parentMap, endWord);
                return ladder.toArray(new String[0]);
            }
            
            for (String neighbor : wordList) {
                if (!visited.contains(neighbor) && isOneLetterDifference(currentWord, neighbor)) {
                    visited.add(neighbor);
                    Node neighborNode = new Node(neighbor);
                    queue.add(neighborNode);
                    parentMap.put(neighbor, currentWord);
                    this.total++;
                }
            }
        }
        
        return new String[]{"No ladder found"};
    }
}
