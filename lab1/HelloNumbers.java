public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int cummulative = 0;
        while (x < 10) {
        	cummulative = x + cummulative; // 0, 1, 3, 
            System.out.print(cummulative + " ");
            x = x + 1;
        }
    }
}