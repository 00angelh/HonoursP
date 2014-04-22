package pathfind;

//Class that is used to represent a link between the target node, and the node that holds the edge
public class Edge
{
    private Node connectingNode;
    private double weight;
    
    public Edge(Node v){
    	connectingNode = v;
    }
    
    public Edge(Node v, double w)
    { connectingNode = v; weight = w; }
    public Node getConnectingNode(){
    	return connectingNode;
    }
    public double getWeight(){
    	return weight;
    }
    public void setWeight(Double w){
    	weight = w;
    }
}
