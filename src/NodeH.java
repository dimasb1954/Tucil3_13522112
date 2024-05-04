import java.util.ArrayList;
import java.util.List;

public class NodeH {
    String word;
    List<NodeH> neighbors;
    int cost;
    int heuristic;
    
    public NodeH(String word, int cost, int heuristic) {
        this.word = word;
        this.neighbors = new ArrayList<>();
        this.cost = cost;
        this.heuristic = heuristic;
    }
    
    public String getWord() {
        return word;
    }
    
    public List<NodeH> getNeighbors() {
        return neighbors;
    }
    
    public void addNeighbor(NodeH neighbor) {
        neighbors.add(neighbor);
    }
    
    public int getTotalCost() {
        return cost + heuristic;
    }
}

