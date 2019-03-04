public class pyramid {

	
	public static void buildPyramid(int pyramidSize) {

		int pyramidLenght = 1;
		int counter = 0;
		while (pyramidSize > 0) {
			while (pyramidLenght > counter){
				counter++;
				System.out.print("*");
			}	
			System.out.println("");
        	counter = 0;
        	pyramidLenght++;
        	pyramidSize--;
		}
	}

    public static void main(String[] args) {
        buildPyramid(5);
    }
}