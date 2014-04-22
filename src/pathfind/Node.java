package pathfind;

import java.util.ArrayList;
import java.util.Map;



//Class that is used as a node 
public class Node implements Comparable<Node>
{
	private ArrayList<Edge> links;
    private double distanceFromSource = Double.POSITIVE_INFINITY; //The distance between this node, and the source
    
    private Node previous; //The vertex previous to this one
    private String name; //The name of the vertex
    private boolean building; //Whether the vertex is a building or not
    private String code; //The code used to find the vertex
    private int xloc; //The y-location of the vertex
    private int yloc; //The x-location of the vertex
    private String desc; //A description of what the vertex is
    
    //Getters and setters
    public boolean isBuilding(){return building;}
	public int getX(){return xloc;}
	public int getY(){return yloc;}
	public String getDesc(){return desc;}
	public String getCode(){return code;}
	public String getName(){return name;}
    public String toString() { return name; } 
    

    public ArrayList<Edge> getLinks(){
    	return links;
    }
    
    public void setNewNodeInfo(double ds, Node x){
    	setDistanceFromSource(ds);
    	setPreviousNode(x);
    }
    
    public Node getPreviousNode(){
    	return previous;
    }
    
    public void setPreviousNode(Node p){
    	previous = p;
    }
    
    public void setDistanceFromSource(double s){
    	distanceFromSource = s;
    }
    public double getDistanceFromSource(){
    	return distanceFromSource;
    }
    
	public Node(String argName, int x, int y, String c, String d, boolean b){
    	name = argName;
    	xloc = x;
    	yloc = y;
    	desc = d;
    	code = c;
    	building = b;
    	links = new ArrayList<Edge>();
    }
    
    public int compareTo(Node other)
    {
        return Double.compare(distanceFromSource, other.distanceFromSource);
    }

    public boolean addEdge(Edge ln){
    	links.add(ln);
    	return true;
    }
    public boolean setLinks(ArrayList<Edge> ln){
    	links = ln;
    	return true;
    }
}