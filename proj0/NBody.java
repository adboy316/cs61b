/**
Class that will actually run the simulation.
*/
public class NBody {

	public static String background = "images/starfield.jpg";


	public static void main(String[] args) {

	// Store the 0th and 1st command line arguments as doubles named T and dt. 
	// Hint: the arguments come in as Strings. You will have to Google 
	// around in order to learn how to convert the Strings to doubles!

	if (args.length < 2) {
			System.out.println("Please supply T, dt, and filename.");
		}	

	double T =  Double.parseDouble(args[0]);
	double dt = Double.parseDouble(args[1]);
	String filename = args[2];

	Body[] bodies = readBodies(filename);
	double radius = readRadius(filename);

	/** Enables double buffering.
		  * A animation technique where all drawing takes place on the offscreen canvas.
		  * Only when you call show() does your drawing get copied from the
		  * offscreen canvas to the onscreen canvas, where it is displayed
		  * in the standard drawing window. */
	StdDraw.enableDoubleBuffering();

	/** Sets up the universe so it goes from
		  * -100, -100 up to 100, 100 */
	StdDraw.setScale(-radius, radius);

	
	/** Create universe Animation. */
	for (double t = 0; t < T; t = t+dt) {

		/* Create an xForces array and yForces array. */
		double[] xForces = new double[bodies.length];
		double[] yForces = new double[bodies.length];
		/** Check that loop is working*/
		
		
		/* Calculate the net x and y forces for each Body, 
		storing these in the xForces and yForces arrays respectively.
		 */
		for (int i = 0; i<bodies.length; i++) {
			xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
			yForces[i] = bodies[i].calcNetForceExertedByY(bodies);	
			bodies[i].update(dt, xForces[i], yForces[i]);
		}

		/** Draw background image. */
		StdDraw.picture(0, 0, background);

		/** Draw bodies. */
		for (Body b : bodies){
			b.draw();
		}

		/* Shows the drawing to the screen, and waits 2000 milliseconds. */
		StdDraw.show();
		StdDraw.pause(10);
		
	}

	/* Prints the universe when simulation is over. */
	StdOut.printf("%d\n", bodies.length);
	StdOut.printf("%.2e\n", radius);
	for (int i = 0; i < bodies.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
              bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
              bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);  
 
	}


}

/**
Return the radius of the universe contained in file
*/
public static double readRadius(String file) {
	In in = new In(file);
	in.readLine();
	double r = in.readDouble();
	return r;	
}

/**
Return an array of Bodys corresponding to the bodies in the file
*/
public static Body[] readBodies(String file) {
	In in = new In(file);
	int size = in.readInt();
	in.readLine();
	in.readLine();
	Body[] bodies = new Body[size];
	for (int i=0; i<bodies.length; i++) {
	 	bodies[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), 
	 		in.readDouble(), in.readDouble(), in.readString());
	 }
	
	return bodies;
}	

}