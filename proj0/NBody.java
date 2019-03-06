/**
Class that will actually run the simulation.
*/
public class NBody {

/**
Return the radius of the universe contained in file
*/
public static double readRadius(String file) {
	In in = new In(file);
	in.readLine();
	double r = in.readDouble();
	return r;	
}


public static Body[] readBodies(String file) {
	In in = new In(file);
	Body[] bodies = new Body[5];
	in.readLine();
	in.readLine();

	for (int i=0; i<5; i++) {

		bodies[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), 
			in.readDouble(), in.readDouble(), in.readString());

	}




	return bodies;
}	

}