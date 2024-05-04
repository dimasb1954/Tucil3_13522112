import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WordValidator {
    //make a set to contain of all words in english 
    private Set<String> dictionary;

    //load from txt file as a reference of the
    public WordValidator(String dictionaryFilePath) throws IOException {
        this.dictionary = loadDictionary(dictionaryFilePath);
    }

    //read the txt file line by line and save it in the dictionary set before
    private Set<String> loadDictionary(String filePath) throws IOException {
        Set<String> dictionary = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line.trim().toLowerCase());
            }
        }
        return dictionary;
    }

    //check does a word exist in the dictionary, make sure it is lowercased
    public boolean isValidWord(String word) {
        return dictionary.contains(word.toLowerCase());
    }
}