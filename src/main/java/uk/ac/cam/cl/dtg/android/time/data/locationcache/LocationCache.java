/**
 * LocationCache uses a recursive spatial index to store a set of (x,y) points.
 * The data structure enables fast queries to retrieve the top n points in a given rectangle.
 * 
 * Copyright 2009 Alastair R. Beresford
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.cam.cl.dtg.android.time.data.locationcache;

public class LocationCache {

	//number of points to cache at this level before forming children
	private static final int pointsPerLevel = 25;

	//store nth pair of points (x,y) at 2*n + 2*n+1
	private int[] points = null;
	private int numPoints;

	//array of direct children
	private LocationCache[] children = null;

	//extent of location area included in the cache
	private int topLeftX;
	private int topLeftY;
	private int bottomRightX;
	private int bottomRightY;

	/**
	 * LocationCache stores a set of points in a quadtree data structure within a specified
	 * rectangle boundary. If any points outside the boundary are added, they are ignored.
	 * The left and top boundary edges are inclusive and the bottom and right edges are 
	 * exclusive of any points stored in the LocationCache.
	 *
	 * @param topLeftX The x value of the top left of the boundary rectangle
	 * @param topLeftY The y value of the top left of the boundary rectangle
	 * @param bottomRightX The x value of the bottom right of the boundary rectangle
	 * @param bottomRightY The y value of the bottom right of the boundary rectangle
	 */
	public LocationCache(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
		points = new int[2*pointsPerLevel];
		numPoints = 0;
		//ensure topLeft is always further left and up from bottomRight
		this.topLeftX = topLeftX<bottomRightX ? topLeftX : bottomRightX;
		this.topLeftY = topLeftY>bottomRightY ? topLeftY : bottomRightY;
		this.bottomRightX = topLeftX<bottomRightX ? bottomRightX : topLeftX;
		this.bottomRightY = topLeftY>bottomRightY ? bottomRightY : topLeftY;
	}

	/**
	 * Add a point to the current set of points stored in the location cache.
	 *
	 * @param x The x coordinate of the point
	 * @param y THe y coordinate of the point
	 */
	public void addPoint(int x, int y) {
		if (!isInside(x,y))
			return; //ignore points outside our bounds

		if (numPoints < pointsPerLevel) {

			points[2*numPoints] = x;
			points[2*numPoints+1] = y;
			numPoints++;

		} else {

			int splitX = topLeftX + (bottomRightX - topLeftX)/2;
			int splitY = bottomRightY + (topLeftY - bottomRightY)/2;

			if (children == null) {
				if (splitX==topLeftX || splitY==bottomRightY) {
					//no more integer resolution left to sub-divide; build linked list of points
					//by using the same area as this LocationCache object above
					//TODO: set some kind of warning flag or throw exception in this case?
					children = new LocationCache[] {
							new LocationCache(topLeftX,topLeftY,bottomRightX,bottomRightY)};
				} else {
					/* Build four children which divide up our space:
					 *   TL | TR
					 *   -------
					 *   BL | BR
					 */
					children = new LocationCache[] {
							new LocationCache(topLeftX,topLeftY,splitX,splitY),
							new LocationCache(splitX,topLeftY,bottomRightX,splitY),
							new LocationCache(topLeftX,splitY,splitX,bottomRightY),
							new LocationCache(splitX,splitY,bottomRightX,bottomRightY)};
				}
			}

			for(int i=0; i<children.length; i++)
				children[i].addPoint(x,y);   
			//TODO: substitute top-level cache points to maintain even distribution of points
		}
	}

	/**
	 * Determine whether the point (x,y) is inside or outside the LocationCache boundary
	 * rectangle.
	 */
	private boolean isInside(int x, int y) {
		return (x >= topLeftX && x < bottomRightX && y <= topLeftY && y > bottomRightY);
	}

	/**
	 * Query the LocationCache to find at most max points inside the bounds of the query.
	 * The left and top boundary edges are inclusive and the bottom and right edges are 
	 * exclusive of any points stored in the LocationCache.
	 *
	 * @param topLeftX The x value of the top left of the query rectangle
	 * @param topLeftY The y value of the top left of the query rectangle
	 * @param bottomRightX The x value of the bottom right of the query rectangle
	 * @param bottomRightY The y value of the bottom right of the query rectangle
	 * @param max The maximum number of elements to return.
	 *
	 * @return       The first element of the array is the number of valid points found 
	 *               in the bounds and the remaining elements store the values of those 
	 *               (x,y) pairs, stored as consecutive elements in the array; for example,
	 *               {(1,2),(4,3)} would be stored as [2,1,2,4,3,...]
	 */
	public int[] getQuotaInBounds(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY, int max) {
		int[] result = new int[2*max+1];
		getQuotaInBounds(result,topLeftX,topLeftY,bottomRightX,bottomRightY,max);
		return result;
	}

	/**
	 * Query the LocationCache to find at most max points inside the bounds of the query.
	 * The left and top boundary edges are inclusive and the bottom and right edges are 
	 * exclusive of any points stored in the LocationCache.
	 * <p>
	 * This is a private version of the public function of the same name.
	 *
	 * @param result The first element of the array is the number of valid points found 
	 *               in the bounds and the remaining elements store the values of those 
	 *               (x,y) pairs, stored as consecutive elements in the array; for example,
	 *               {(1,2),(4,3)} would be stored as [2,1,2,4,3,...]
	 * @param topLeftX The x value of the top left of the query rectangle
	 * @param topLeftY The y value of the top left of the query rectangle
	 * @param bottomRightX The x value of the bottom right of the query rectangle
	 * @param bottomRightY The y value of the bottom right of the query rectangle
	 * @param max The maximum number of elements to return.
	 */
	private void getQuotaInBounds(int[] result, int topLeftX, int topLeftY, int bottomRightX, int bottomRightY, int max) {

		//Two boundaries: boundary lines of this LocationCache object, "lc-bound"
		//                boundary lines of the query (info in method call), "q-bound".
		// Return straight away if there is no intersection of q-bound and lc-bound.
		// Intersection occurs if topLeft point of q-bound is further up and left of bottomRight
		// point of lc-bound AND bottomRight point of q-bound is below and right of topLeft
		// lc-bound. (Draw a diagram on a piece of paper to convince yourself!)
		if (!(topLeftX < this.bottomRightX && topLeftY > this.bottomRightY &&
				bottomRightX > this.topLeftX && bottomRightY < this.topLeftY))
			return;

		for(int i=0; i<numPoints; i++) {
			final int numResults = result[0];
			if (numResults >= max)
				return; 
			final int x = points[2*i];
			final int y = points[2*i+1];
			if (x >= topLeftX && x < bottomRightX && y <= topLeftY && y > bottomRightY) {
				result[2*numResults+1] = x; //+1 leaves space at start of array for numResults
				result[2*numResults+2] = y;
				result[0]++;
			}
		}

		if (children == null)
			return;

		//TODO: balance results from all children?
		for(int i=0; i<children.length; i++)
			children[i].getQuotaInBounds(result, topLeftX, topLeftY, bottomRightX, bottomRightY, max);
	}

	public static void main(String[] args) {

		//Regular grid of points which stretches beyond LocationCache region
		LocationCache lc = new LocationCache(0,100,100,0);
		for(int i=-10; i<110; i +=10)
			for(int j=-10; j<110; j +=10)
				lc.addPoint(i,j);

		//Check points on top and left edge are included and bottom and right edge are excluded
		//Therefore expect "lc" to contain 9 x 9 grid, or 81 points
		int[] data = lc.getQuotaInBounds(-15,115,115,-15,1000);
		if (data[0] != 100) 
			System.out.println("Failed points LocationCache crop test with "+data[0]+" points");

		//Check that points are similarly included/excluded as above with Quota Boundary
		data = lc.getQuotaInBounds(10,60,60,10,1000);
		if (data[0] != 25) 
			System.out.println("Failed points Quota Boundary crop test with "+data[0]+" points");

		//Check we get no points back when quota bounds doesn't over lap lc
		data = lc.getQuotaInBounds(-50,110,-30,120,1000);
		if (data[0] != 0) 
			System.out.println("Failed non-overlap test 1 with "+data[0]+" points");
		data = lc.getQuotaInBounds(-50,50,-30,30,1000);
		if (data[0] != 0)
			System.out.println("Failed non-overlap test 2 with "+data[0]+" points");
		data = lc.getQuotaInBounds(-50,-50,-30,-30,1000);
		if (data[0] != 0)
			System.out.println("Failed non-overlap test 3 with "+data[0]+" points");
		//TODO: finish these off

		//Check an LC with zero area can never contain points (but doesn't fail)
		lc = new LocationCache(0,0,0,0);
		for(int i=0; i<10; i++)
			lc.addPoint(0,0);
		data = lc.getQuotaInBounds(-1,1,1,-1,1000);
		if (data[0] != 0)
			System.out.println("Failed zero area test with "+data[0]+" points");

		//Check that the linked-list fallback works correctly
		lc = new LocationCache(0,1,1,0);
		for(int i=0; i<10; i++) {
			lc.addPoint(0,1); //only valid point to add
			lc.addPoint(0,0); //invalid: on right edge so excluded
			lc.addPoint(1,0); //invalid: on bottom edge so excluded
			lc.addPoint(1,1); //invalid: on right and bottom edges so excluded
		}
		data = lc.getQuotaInBounds(-1,1,1,-1,1000);
		if (data[0] != 10)
			System.out.println("Failed zero fallback linked-list test with "+data[0]+" points");
		if (data[1] != 0 || data[2] != 1)
			System.out.println("Failed zero fallback linked-list test with incorrect point");

		//set to true and edit to print out data from a specific example above
		if (true) {
			lc = new LocationCache(0,100,100,0);
			for(int i=-10; i<110; i +=10)
				for(int j=-10; j<110; j +=10)
					lc.addPoint(i,j);
			data = lc.getQuotaInBounds(10,60,60,10,1000);
			for(int i=0; i<data[0]; i++)
				System.out.println(data[2*i+1]+","+data[2*i+2]);
		}
	}
}
