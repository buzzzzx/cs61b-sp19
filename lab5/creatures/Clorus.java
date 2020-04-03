package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;

    /**
     * green color.
     */
    private int g;

    /**
     * blue color.
     */
    private int b;

    /**
     * energy lost for move action.
     */
    private static final double EnergyLostForMove = 0.03;

    /**
     * energy lost for stay action.
     */
    private static final double EnergyLostForStay = 0.01;

    /**
     * fraction of energy to retain when replicating.
     */
    private static final double RepEnergyRetained = 0.5;

    /**
     * Constructor with given energy.
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /**
     * Constructor with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /**
     * returns a color.
     */
    public Color color() {
        return new Color(r, g, b);
    }

    /**
     * Clorus should lost energy when moving.
     */
    public void move() {
        energy -= EnergyLostForMove;
    }

    /**
     * Clorus should lost energy when staying.
     */
    public void stay() {
        energy -= EnergyLostForStay;
    }

    /**
     * Clorus gain another creature'energy when attacking.
     */
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Clorus lost 50% energy when replicating.
     */
    public Clorus replicate() {
        energy = energy * RepEnergyRetained;
        Clorus babyClorus = new Clorus(energy);
        return babyClorus;
    }

    /**
     * Clorus choose action.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {

        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> attackAvailablePlips = new ArrayDeque<>();
        boolean anyPlip = false;

        for (Direction dir : neighbors.keySet()) {
            Occupant neighbor = neighbors.get(dir);
            if (neighbor.name().equals("empty")) {
                emptyNeighbors.addLast(dir);
            } else if (neighbor.name().equals("plip")) {
                anyPlip = true;
                attackAvailablePlips.addLast(dir);
            } else {
                continue;
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (anyPlip) {
            return new Action(Action.ActionType.ATTACK, randomEntry(attackAvailablePlips));

        } else if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        } else {
            return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
        }
    }

    /**
     * Helper method to choose random item.
     */
    private static Direction randomEntry(Deque<Direction> d) {
        int randomIndex = new Random().nextInt(d.size());
        Direction[] dArray = d.toArray(new Direction[d.size()]);

        return dArray[randomIndex];
    }
}
