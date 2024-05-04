import java.util.ArrayList;
import java.util.List;

public class Node {
    String word;
    List<Node> neighbors;
    int cost;
    
    public Node(String word) {
        this.word = word;
        this.neighbors = new ArrayList<>();
    }
    
    public String getWord() {
        return word;
    }
    
    public List<Node> getNeighbors() {
        return neighbors;
    }
    
    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }

    public int getCost() {
        return cost;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
}

