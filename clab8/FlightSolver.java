import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {

    ArrayList<Flight> flights;


    public FlightSolver(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    /** Find the largest number of people that have ever been in flight at once. */
    public int solve() {
        /* FIX ME */

        PriorityQueue<Flight> fq = new PriorityQueue<>(flightComparator);
        PriorityQueue<Flight> arrivalsDepartures = new PriorityQueue<>(flightComparator);

        int maxPassengerCounter = 0;
        int currentPassengerCounter = 0;

        for (Flight f: flights) {
            fq.add(f);
        }

        while (!fq.isEmpty()) {
            Flight currentFlight = fq.poll();
            arrivalsDepartures.add(currentFlight);
            checkFlightOverlap(arrivalsDepartures, currentFlight);
            currentPassengerCounter = calculatePassengerCount(arrivalsDepartures);
            if (currentPassengerCounter > maxPassengerCounter) {
                maxPassengerCounter = currentPassengerCounter;
            }

        }
        return maxPassengerCounter;
    }

    /** Checks the arrivalsDepartures to see which flights overlap with f. */
    private void checkFlightOverlap(PriorityQueue<Flight> arrivalsDepartures, Flight f) {
        if (arrivalsDepartures.size() > 1) {
            // If the end of peek is less than the start of new flight (possible recursion)
            if (arrivalsDepartures.peek().endTime() < f.startTime()) {
                arrivalsDepartures.remove();
                checkFlightOverlap(arrivalsDepartures, f);
            }
        }
    }

    /** Returns total number of passengers in current Queue. */
    private int calculatePassengerCount(PriorityQueue<Flight> arrivalsDepartures) {

        int passengerCount = 0;
        for (Flight f: arrivalsDepartures) {
            passengerCount += f.passengers;
        }
        return passengerCount;
    }

    /** Sort flights by flight time. If flight times overlaps return 0; */
    Comparator<Flight> flightComparator = (f1, f2) -> {
        if (f1.startTime < f2.startTime) {
            return -1;
        }
        if (f1.startTime > f2.startTime) {
            return 1;
        }
        return 0;
    };

}
