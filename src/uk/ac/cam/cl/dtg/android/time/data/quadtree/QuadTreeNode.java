package uk.ac.cam.cl.dtg.android.time.data.quadtree;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

class QuadTreeNode<E> {


	public static final int BranchingFactor = 4;
	
    QuadTreeBounds Bounds;
    int Depth;
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
	    		
	    		float newleft = Bounds.getLeft() + Bounds.getWidth()*((float)xcoord/BranchingFactor);
	    		float newright = Bounds.getLeft() + Bounds.getWidth()*((float)(xcoord+1)/BranchingFactor);
	    		float newtop = Bounds.getBottom() + Bounds.getHeight()*((float)(ycoord+1)/BranchingFactor);
	    		float newbottom = Bounds.getBottom() + Bounds.getHeight()*((float)ycoord/BranchingFactor);
	    		
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
    		
    	//	System.out.println("Item "+item+" inserted into child node covering "+Bounds);

    		Items.add(new QuadTreeItem<E>(item,x,y));
    		
    	///	System.out.println("Node now contains: "+getItems());
    		
    	} else {
    		
    		// Is the item outside our bounds?
    		if(!Bounds.contains(x, y)) throw new QuadTreeException("Trying to insert item "+item+" outside the tree bounds");
    		
    		// Calculate within which child node the item lies
    		float proportion_x = (float)(x - Bounds.getLeft()) / Bounds.getWidth();
    		float proportion_y = (float)(y - Bounds.getBottom()) / Bounds.getHeight();
    		
    		int xcoord = (int) Math.floor(proportion_x * BranchingFactor);
    		int ycoord = (int) Math.floor(proportion_y * BranchingFactor);
    		
    		childNodes.get(xcoord * BranchingFactor + ycoord).insertItem(item, x, y);
   		
    		
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
    		
    		// Now take the appropriate number of items from the child and add to ours
    		int numToTake = (int) Math.ceil(DetailDecayFactor * child.Items.size());
    		System.out.println("Taking "+numToTake+" from child. Decay factor is "+DetailDecayFactor);
    		
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
     	return "[NODE Level " + Depth + " covers "+Bounds+" items: "+Items.size()+" Children: "+childNodes.size()+"]";
     	  
    }
    
    /**
     * Returns a set of items within or near the rectangle provided, size of set is scaled
     * according to how large the rectangle is.
     * @param rect
     */
    protected Set<E> getItems(QuadTreeBounds rect) {
    	
    	return new Set<E>(); // TODO: Implement!!! :)
    }
    
  
    
}
