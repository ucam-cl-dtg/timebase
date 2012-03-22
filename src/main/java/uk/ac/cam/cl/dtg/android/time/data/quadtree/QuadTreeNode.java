package uk.ac.cam.cl.dtg.android.time.data.quadtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

class QuadTreeNode<E> {


	public static final int BranchingFactor = 4;
	
    QuadTreeBounds Bounds;
    int Depth;
    int coordx, coordy;
    ArrayList<QuadTreeNode<E>> childNodes;
    Set<QuadTreeItem<E>> Items;
    
	// The proportion of a child's nodes which are visible one level up
	private float DetailDecayFactor = 0.5f;

    /**
     * Creates a new QuadTreeNode. This is a region which may be subdivided into a grid of BranchingFactor x BranchingFactor
     * (at the moment 4x4) further child QuadTreeNodes.
     * @param NodeDepth How many layers deep the QuadTreeNode is to be. Values over 1 cause further subdivision to occur.
     * @param NodeBounds The area bounded by the QuadTreeNode.
     */
    public QuadTreeNode(int NodeDepth, QuadTreeBounds NodeBounds) {
    	
    	Depth = NodeDepth;
    	Bounds = NodeBounds;
    	Items = new LinkedHashSet<QuadTreeItem<E>>();
    	
    	childNodes = new ArrayList<QuadTreeNode<E>>();
    	
    	// Create the child nodes, but only if we're above the bottom level
    	if(NodeDepth > 1) {
    		
	    	
	    	for(int i = 0; i < (Math.pow(BranchingFactor,2)); i++)
	    	{
	    		// Calculate the bounds for this new node
	    		QuadTreeBounds newbounds = new QuadTreeBounds();
	    		
	    		int xcoord;
	    		int ycoord;
	    		
	    		xcoord = (int)Math.floor(i / BranchingFactor);
	    		ycoord = i % BranchingFactor;
	    		
	    		float newleft = Bounds.getLeft() + Bounds.getWidth()*((float)xcoord/BranchingFactor);
	    		float newright = Bounds.getLeft() + Bounds.getWidth()*((float)(xcoord+1)/BranchingFactor);
	    		float newtop = Bounds.getBottom() + Bounds.getHeight()*((float)(ycoord+1)/BranchingFactor);
	    		float newbottom = Bounds.getBottom() + Bounds.getHeight()*((float)ycoord/BranchingFactor);
	    		
	    		newbounds.setLeft((int)newleft);
	    		newbounds.setTop((int)newtop);
	    		newbounds.setBottom((int)newbottom);
	    		newbounds.setRight((int)newright);
	    		
	    		//System.out.println("Child : "+xcoord+"/"+ycoord+" : "+newbounds);
	    		
	    		QuadTreeNode<E> newChild = new QuadTreeNode<E>(NodeDepth - 1, newbounds);
	    		newChild.coordx = xcoord;
	    		newChild.coordy = ycoord;
	    		
	    		childNodes.add(i, newChild);
	    	}
    	}
    }
    
    /**
     * Inserts an item into the QuadTreeNode.
     * 
     * @param item The item to be inserted
     * @param x The X co-ordinate of the item to be inserted
     * @param y The Y co-ordinate of the item to be inserted
     */
    public void insertItem(E item, int x, int y) throws QuadTreeException
    {
    	/* Are we a leaf node i.e. on the bottom layer? If so then insert the
    	 * item straight into our items collection
    	 */
    	
    	if(Depth == 1) {
    		
    		//System.out.println("Item "+item+" inserted into child node covering "+Bounds);

    		Items.add(new QuadTreeItem<E>(item,x,y));
    		
    		//System.out.println("Node now contains: "+getItems());
    		
    	} else {
    		
    		// Is the item outside our bounds?
    		if(!Bounds.contains(x, y)) throw new QuadTreeException("Trying to insert outside tree bounds. Bounds are "+Bounds+", co-ords are "+x+"/"+y);
    		
    		// Calculate within which child node the item lies
    		float proportion_x = (float)(x - Bounds.getLeft()) / Bounds.getWidth();
    		float proportion_y = (float)(y - Bounds.getBottom()) / Bounds.getHeight();
    		
    		int xcoord = (int) Math.floor(proportion_x * BranchingFactor);
    		int ycoord = (int) Math.floor(proportion_y * BranchingFactor);
    		
    		// Edge case (literally :) )
    		if(xcoord == BranchingFactor) xcoord--;
    		if(ycoord == BranchingFactor) ycoord--;
    		
    		int childnum = xcoord * BranchingFactor + ycoord;
    		//System.out.println("Inserting ("+x+"/"+y+"). x-c/y-c: ("+xcoord+"/"+ycoord+"). Going to child "+childnum+" we have "+childNodes.size()+" children. Our bounds: "+Bounds+" p-x/p-y:"+proportion_x+"/"+proportion_y);
    		//System.out.println("Trying to insert into child with coords "+xcoord+"/"+ycoord+" we have "+childNodes.size());
    		
    		childNodes.get(childnum).insertItem(item, x, y);
   		
    		
    	}
    }
    
    /**
     * Calculates which QuadTreeItems are visible at which detail levels.
     */
    public void propagateItems() {
    	

    	
    	// Are we a leaf node? If so do nothing.
    	if(Depth == 1) return;
    	
    	// Ask our children to propagate themselves first.
    	for(QuadTreeNode<E> child : childNodes) {
    		child.propagateItems();
    	 	// debug
        	//System.out.println("================\nPROPAGATION FOR: "+child+"\n============");
    	
       	
    		// Now take the appropriate number of items from the child and add to ours
    		int numToTake = (int)( Math.round(DetailDecayFactor * child.Items.size()));
    		//System.out.println("Child has "+child.Items.size()+" Taking "+numToTake+" from child. Decay factor is "+DetailDecayFactor);
    		
    		// Iterate through child's items, taking items until we hit limit
    		int i = 0;
    		for(QuadTreeItem<E> item : child.Items) {
    			
    			// Break when we've taken enough
    			i++;  			
    			if(i > numToTake) break;
    			
    			// Otherwise add to our own items collection    			
    			boolean didwe = Items.add(item);
    			//System.out.println("-- Taking "+item+" from child. Our items are now: "+Items+" Did we do it? "+didwe);
    			
    			
    			
    		}
    	}
    }
    
    /**
     * Returns the set of items held in this node
     * @return
     */
    public Set<QuadTreeItem<E>> getItems() {
    	
    	return Items;
    }

	protected float getDetailDecayFactor() {
		return DetailDecayFactor;
	}

	protected void setDetailDecayFactor(float detailDecayFactor) {
		
		// Set our factor
		DetailDecayFactor = detailDecayFactor;
		
		// Return if we're not leaf node
		if(Depth == 1) return;
		
		// Otherwise, set our children's decay factors		
		for(QuadTreeNode<E> child : childNodes) child.setDetailDecayFactor(detailDecayFactor);
	}
    
    public String toString() {
     	return "[NODE Level " + Depth + " "+coordx+"/"+coordy+" covers "+Bounds+" items: "+Items.size()+" Children: "+childNodes.size()+"]\n";
     	  
    }
    
    /**
     * Returns a set of items within or near the rectangle provided, size of set is scaled
     * according to how large the rectangle is.
     * @param rect
     */
    protected Set<QuadTreeItem<E>> getItems(QuadTreeBounds rect) {
    	
    //	System.out.println("GetItems() called on "+this+" with rect "+rect);
    	
    	// Are we a leaf? If so then all we can do is return all the items we have
    	// stored right now.
    	if(Depth == 1) {
    	//	System.out.println("> getItems just been called on me but I'm a leaf.");
    		//return obtainItemSet();
    		return Items;
    	}
    	
    	/*
    	 * We're not a leaf. Then we have 2 courses of action:
    	 * 
    	 * 1) If rect covers 1, 2 or 4 of our child nodes then call getItems on each and
    	 * return the union of their result. If they're leaves, we just get all the items
    	 * in those areas. If they're not leaves, then more recursive calls happen.
    	 * 
    	 * Why 1, 2 and 4 and not just 1? To stop the edge cases where rect
    	 * is very small but spanning two / four child nodes.
    	 * 
    	 * 2) Otherwise return the UNION of every child node covered by rect. If they're leaves
    	 * then we will just get the same result as point 1) above, but if they're *not* then
    	 * because we don't do recursive calls into the covered child nodes we just end up
    	 * returning a limited subset of all the nodes that reside in the children.
    	 */
    	
  	
    //	System.out.println("We're not leaf, count how many overlap.");
    	
    	// List to hold overlap
    	LinkedList<QuadTreeNode<E>> overlappedNodes = new LinkedList<QuadTreeNode<E>>();
    	
    	for(QuadTreeNode<E> child : childNodes) {
    		
    		if(child.Bounds.overlaps(rect))
    		{
    			overlappedNodes.add(child);
    		}
 
    	}
    	
    //	System.out.println(overlappedNodes.size()+" child nodes overlap.");
    	
    	// Set to hold results
    	Set<QuadTreeItem<E>> theResults = new HashSet<QuadTreeItem<E>>();
    	int overlapCount = overlappedNodes.size();
    	
    	// Overlaps with 1, 2 or 4
    	if(overlapCount == 1 || overlapCount == 2 || overlapCount == 4) {
    		
    		//System.out.println("==> Asking for getItems() from each of the children");
    		for(QuadTreeNode<E> overlapped : overlappedNodes) {
    			theResults.addAll(overlapped.getItems(rect));
    		//	System.out.println("==> Our results now contain: "+theResults);
    		}
     	//} else if(overlapCount == Math.pow(BranchingFactor, 2)){
     	//	theResults = Items;
     	} else {
    		for(QuadTreeNode<E> overlapped : overlappedNodes) {
    			//System.out.println("==> Adding items from overlapped node to result set: "+overlapped.Items);
    			theResults.addAll(overlapped.Items);
    		//	System.out.println("==> Our results now contain: "+theResults);
    		}    		
     	}
    	
    	//System.out.println("> Returning back: "+theResults);
    	return theResults;
    	
    }
    
    /**
     * Returns a set of all the items held by the QuadTreeNode.
     * @return All items held by the QuadTreeNode.
     */
 
    
  
    
}
