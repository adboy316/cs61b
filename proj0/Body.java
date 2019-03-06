public class Body {

public double xxPos;
public double yyPos;
public double xxVel;
public double yyVel;
public double mass;
public String imgFileName;
public static final double g = 6.67 * Math.pow(10, -11);

public Body(double xP, double yP, double xV,
				double yV, double m, String img) {
	this.xxPos = xP;
	this.yyPos = yP;
	this.xxVel = xV;
	this.yyVel = yV;
	this.mass = m;
	this.imgFileName = img;
}
   
public Body(Body b) {
	this.xxPos = b.xxPos;
	this.yyPos = b.yyPos;
	this.xxVel = b.xxVel;
	this.yyVel = b.yyVel;
	this.mass = b.mass;
	this.imgFileName = b.imgFileName;
}
	
/** 
Calculates the distance(r) between this.body and body(Body b) 
*/	
public double calcDistance(Body b) {

	double dx = calcdx(this.xxPos, b.xxPos);
	double dy = calcdy(this.yyPos, b.yyPos);
	double r = Math.sqrt(dx+dy);
	return r;
}


/** 
Calculates force exerted by body 
*/
public double calcForceExertedBy(Body b) {
	double r2 = Math.pow(calcDistance(b), 2);
	double m1 = this.mass;
	double m2 = b.mass;
	double force = (g * m1 * m2)/r2;
	return force;
}

/**
Calculates force exerted in the X direction 
*/
public double calcForceExertedByX (Body b) {

	double f = calcForceExertedBy(b);
	double dx = Math.sqrt(calcdx(this.xxPos, b.xxPos));
	double r = calcDistance(b);
	double fx = ((f * dx )/r);	
	return fx;

}

/**
Calculates force exerted in the Y direction 
*/
public double calcForceExertedByY (Body b) {

	double f = calcForceExertedBy(b);
	double dy = Math.sqrt(calcdy(this.yyPos, b.yyPos));
	double r = calcDistance(b);
	double fy = (f * dy)/r;
	return fy;
}

/**
Calculates the net force exerted in the X direction by all bodieas 
*/
public double calcNetForceExertedByX (Body[] bodies) {
	double netForceX = 0;
	for (Body b : bodies) {
		if (b.equals(this)){
			continue;
		}
		netForceX = netForceX + calcForceExertedByX(b);
	}
		return netForceX;
}

/**
Calculates the net force exerted in the Y direction by all bodieas 
*/
public double calcNetForceExertedByY (Body[] bodies) {
	double netForceY = 0;
	for (Body b : bodies) {
		if (b.equals(this)){
			continue;
		}
		netForceY = netForceY + calcForceExertedByY(b);
	}
		return netForceY;
}

/**
Updates the body’s position and velocity instance variables.
*/
public void update (double dt, double xForce, double yForce) {
	double aNetX = xForce/this.mass;
	double aNetY = yForce/this.mass;
	this.xxVel = this.xxVel + dt * aNetX;
	this.yyVel = this.yyVel + dt * aNetY;
	this.xxPos = this.xxPos + dt * this.xxVel;
	this.yyPos = this.yyPos + dt * this.yyVel;
}



// Helper Methods
/**
 Calculates dx
*/
public double calcdx (double xposB1, double xposB2 ) {
	double dx = Math.pow((xposB2 - xposB1), 2);
	return dx;
}

/**
Calculates dy
*/
public double calcdy (double yposB1, double yposB2 ) {
	double dy = Math.pow((yposB2 - yposB1), 2);
	return dy;
}



}