package pathfind;

import java.util.List;

public class MapSystem {
	
	protected Node[] MapPoints;
	private Algorithm algorithm;
	
	
	public MapSystem(){
		algorithm = new Algorithm();
	}
	
	public Node[] getPoints(){return MapPoints;}
	
	public boolean setPoints(Node[] p){
		MapPoints = p;
		return true;
	}
	
	public boolean addBidirectionalEdge(Node v1,Node v2){
		v1.addEdge(new Edge(v2));
		v2.addEdge(new Edge(v1));
		return true;
		
	}
	
	public List<Node> getPath(Node source, Node destination){
		algorithm.findDistancesFromSource(source);
	    List<Node> path = algorithm.getShortestPathToSource(destination);
	    return path;
	}
	
	public List<Node> getPathWithCode(String code1, String code2){
		return getPath(findNodeWithCode(code1),findNodeWithCode(code2));
	}
	
	//Use this method if length of nodes were not strictly given, but x,y co-ordinates were to calculate them
	public boolean computeLinkLengths(){
			 for (Node v : MapPoints)
				{
				 int x1 = v.getX();
				 int y1 = v.getY();
			      for (Edge e : v.getLinks()){
			    	  int x2 = e.getConnectingNode().getX();
			    	  int y2 = e.getConnectingNode().getY();
			    	  double cx = x2 - x1;
			    	  double cy = y2 - y1;
			    	  cx = cx*cx;
			    	  cy = cy*cy;
			    	  double linkLength = cx + cy;
			    	  linkLength = Math.sqrt(linkLength);
			    	  e.setWeight(linkLength);
			      }
				}
			return true;
	}

	//Finds the node with the code passed in
	public Node findNodeWithCode(String code){
		 for (Node v : MapPoints)
			{
		       if (v.getCode() == code){
		    	   return v;
		       } 
			}
		 return null;
	}
	
}
