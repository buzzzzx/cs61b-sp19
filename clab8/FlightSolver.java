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
    private PriorityQueue<Flight> minStartPQ;
    private PriorityQueue<Flight> minEndPQ;

    public FlightSolver(ArrayList<Flight> flights) {
        /* FIX ME */
        minStartPQ = new PriorityQueue<>(new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                return Integer.compare(o1.startTime(), o2.startTime());
            }
        });

        minEndPQ = new PriorityQueue<>(new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                return Integer.compare(o1.endTime(), o2.endTime());
            }
        });

        minStartPQ.addAll(flights);
        minEndPQ.addAll(flights);

    }

    public int solve() {
        int count = 0;
        int maxNum = 0;

        while (minStartPQ.size() != 0) {
            if (minStartPQ.peek().startTime() <= minEndPQ.peek().endTime()) {
                count += minStartPQ.poll().passengers();
                maxNum = maxNum > count ? maxNum : count;
            } else {
                count -= minEndPQ.poll().passengers();
            }
        }
        return maxNum;
    }

}
