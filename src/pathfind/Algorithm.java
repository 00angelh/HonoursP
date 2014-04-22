package pathfind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.List;






//Class that uses dijkstra's algorithm in order to find the shortest path 
public class Algorithm
{
	
	//Calculates the shortest distance to all the nodes from the source
    public void findDistancesFromSource(Node source)
    {
    	
    	//Sets the starting node
        source.setDistanceFromSource(0);
        PriorityQueue<Node> nodeList = new PriorityQueue<Node>();
      	nodeList.add(source);

      	
      	//Goes through all the nodes to set their distance
		while (!nodeList.isEmpty()) {
		    Node current = nodeList.poll();
	
	            // Visit each edge exiting u
	            for (Edge e : current.getLinks())
	            {
	                Node v = e.getConnectingNode();
	                double weight = e.getWeight();
	                double dstFromSource = current.getDistanceFromSource() + weight;
					if (dstFromSource < v.getDistanceFromSource()) {
					    nodeList.remove(v);
					    v.setNewNodeInfo(dstFromSource,current);
					    nodeList.add(v);
					}
	            }
	        }
    }

    
    //Returns the shortest path from the current source to the target node
    public List<Node> getShortestPathToSource(Node target)
    {
        List<Node> path = new ArrayList<Node>();
        for (Node currentNode = target; currentNode != null; currentNode = currentNode.getPreviousNode()){
        	path.add(currentNode);
        }
            
        
        //Reverses the path so that it's in the right order
        Collections.reverse(path);
        return path;
    }

    
}
