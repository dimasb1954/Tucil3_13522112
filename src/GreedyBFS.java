import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    
    public List<String> findGBFS(String start, String end, Set<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);
        total = 2;

        while (!queue.isEmpty()) {
        String currentWord = queue.poll();

        if (currentWord.equals(end)) {
            // Solusi ditemukan, rekonstruksi word ladder
            List<String> ladder = new ArrayList<>();
            ladder.add(currentWord);
            while (currentWord != null) {
            currentWord = parent.get(currentWord);
            if (currentWord != null) {
                ladder.add(currentWord);
            }
            }
            Collections.reverse(ladder);
            return ladder;
        }

        for (String neighbor : getNeighbors(currentWord, wordList)) {
            if (!visited.contains(neighbor)) {
            queue.offer(neighbor);
            visited.add(neighbor);
            parent.put(neighbor, currentWord);
            total++;
            }
        }
        }

        return null; // Solusi tidak ditemukan
    }

    private static Set<String> getNeighbors(String word, Set<String> wordList) {
        Set<String> neighbors = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {
        for (char c = 'a'; c <= 'z'; c++) {
            if (c != word.charAt(i)) {
            String newWord = word.substring(0, i) + c + word.substring(i + 1);
            if (wordList.contains(newWord)) {
                neighbors.add(newWord);
            }
            }
        }
        }
        return neighbors;
    }
}
