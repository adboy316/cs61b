public class MaxArray {
	
    /** Returns the maximum value from m. */
    public static int max(int[] m) {

    	int largestNumberSoFar = 0;
    	int arrayCurrentPosition = 0;
    	int arrayNextPosition = 1;
    	int counter = m.length-1;
    	while (counter > 0) {  		 		
    		if (m[arrayCurrentPosition] > m[arrayNextPosition]) {
    			if (m[arrayCurrentPosition] > largestNumberSoFar) {
    			largestNumberSoFar = m[arrayCurrentPosition]; 
    			}
    		} else if (m[arrayNextPosition] > m[arrayCurrentPosition]) {
    			if (m[arrayNextPosition] > largestNumberSoFar) {
    			largestNumberSoFar = m[arrayNextPosition]; 
    			}
    		}
    		arrayCurrentPosition++;
    		arrayNextPosition++;
    		counter--;
    	}
        return largestNumberSoFar;
    }

    /** Returns the maximum value from m using a for loop. */
    public static int forMax(int[] m) {
    	int largestNumberSoFar = 0;
    	for (int pos0 = 0, pos1 = 1; pos1 <= m.length-1; pos0++, pos1++) {
    		if (m[pos0] > m[pos1]) {
    			if (m[pos0] > largestNumberSoFar) {
    			largestNumberSoFar = m[pos0]; 
    			}
    		} else if (m[pos1] > m[pos0]) {
    			if (m[pos1] > largestNumberSoFar) {
    			largestNumberSoFar = m[pos1]; 
    			}
    	}
    }

    return largestNumberSoFar;
}

    public static void main(String[] args) {
       int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};  
       System.out.println(max(numbers));
       System.out.println(forMax(numbers));
         
    }
}