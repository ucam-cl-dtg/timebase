package uk.ac.cam.dtg.android.time.data.quadtree;

import java.util.ArrayList;
import java.util.TreeSet;

public class QuadTreeNode<E> {


	public static final int BranchingFactor = 4;
	
    QuadTreeBounds Bounds;
    int Depth;
    ArrayList<QuadTreeNode<E>> childNodes;
    TreeSet<E> Items;
    

    /**
     * Creates a new QuadTreeNode. This is a region which may be subdivided into a grid of BranchingFactor x BranchingFactor
     * (at the moment 4x4) further child QuadTreeNodes.
     * @param NodeDepth How many layers deep the QuadTreeNode is to be. Values over 1 cause further subdivision to occur.
     * @param NodeBounds The area bounded by the QuadTreeNode.
     */
    public QuadTreeNode(int NodeDepth, QuadTreeBounds NodeBounds) {
    	
    	Depth = NodeDepth;
    	Bounds = NodeBounds;
    	
    	// Create the child nodes, but only if we're above the bottom level
    	if(NodeDepth > 1) {
    		
	    	childNodes = new ArrayList<QuadTreeNode<E>>();
	    	for(int i = 0; i < (Math.pow(BranchingFactor,2)); i++)
	    	{
	    		// Calculate the bounds for this new node
	    		QuadTreeBounds newbounds = new QuadTreeBounds();
	    		
	    		int xcoord;
	    		int ycoord;
	    		
	    		xcoord = (int)Math.floor(i / BranchingFactor);
	    		ycoord = i % BranchingFactor;
	    		
	    		float newleft = Bounds.getLeft() + (Bounds.getRight() - Bounds.getLeft())*((float)xcoord/BranchingFactor);
	    		float newright = Bounds.getLeft() + (Bounds.getRight() - Bounds.getLeft())*((float)(xcoord+1)/BranchingFactor);
	    		float newtop = Bounds.getBottom() + (Bounds.getTop() - Bounds.getBottom())*((float)(ycoord+1)/BranchingFactor);
	    		float newbottom = Bounds.getBottom() + (Bounds.getTop() - Bounds.getBottom())*((float)ycoord/BranchingFactor);
	    		
	    		newbounds.setLeft((int)newleft);
	    		newbounds.setTop((int)newtop);
	    		newbounds.setBottom((int)newbottom);
	    		newbounds.setRight((int)newright);
	    		
	    		//System.out.println("Child : "+xcoord+"/"+ycoord+" : "+newbounds);
	    		
	    		childNodes.add(i, new QuadTreeNode<E>(NodeDepth - 1, newbounds));
	    	}
    	}
    }
    
    /**
     * Returns the set of items which lie within and around the bounds given
     * @param rect
     * @return
     */
    public TreeSet<E> getItems(QuadTreeBounds rect) {
    	
    	return new TreeSet<E>();
    }
    
    
    
    
}
