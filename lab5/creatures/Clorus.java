package creatures;

import huglife.*;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

/**
 * An implementation of a predator photosynthesizer.
 */
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
     * Energy lost with MOVE action.
     */
    private static final double moveEnergy = - 0.03;
    /**
     * Energy lost with STAY action.
     */
    private static final double stayEnergy = - 0.01;


    /**
     * creates plip with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }
    /**
     * creates a clorus with energy equal to 1.
     */
    public Clorus() { this(1);
    }

    /**
     * Called when this creature moves.
     */
    @Override
    public void move() {
        energy += moveEnergy;

    }

    /**
     * Called when this creature attacks C.
     *
     * @param c
     */
    @Override
    public void attack(Creature c) {
        Double creatureEnergy = c.energy();
        this.energy += creatureEnergy;
        c.setEnergy(0.0);
    }

    /**
     * Called when this creature chooses replicate.
     * Must return a creature of the same type.
     */
    @Override
    public Clorus replicate() {
        Clorus newClorus = new Clorus(energy * 0.5);
        this.energy = energy * 0.5;
        return newClorus;
    }

    /**
     * Called when this creature chooses stay.
     */
    @Override
    public void stay() {
        energy += stayEnergy;
    }

    /**
     * Returns an action. The creature is provided information about its
     * immediate NEIGHBORS with which to make a decision.
     *
     * @param neighbors
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        if(!adjacentSpacesAreEmpty(neighbors)){
            return new Action(Action.ActionType.STAY);
        }
        // Rule 2
        if(plipsAreSeen(neighbors)) {
            return new Action(Action.ActionType.ATTACK, attackRandom(neighbors));
        }
        // Rule 3
        if(this.energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, moveEmptyRandom(neighbors));
        }
        // Rule 4
        return new Action(Action.ActionType.MOVE, moveEmptyRandom(neighbors));
    }

    /**
     * Required method that returns a color.
     */
    @Override
    public Color color() {
        return color(r, g, b);
    }

    /**
     *If any value in neighbor is not Impassible, return false, else return true
     */
    private boolean adjacentSpacesAreEmpty(Map<Direction, Occupant> neighbors){
        for (Occupant creature: neighbors.values()){
            if (creature.getClass().equals(Empty.class) || creature.getClass().equals(Plip.class)){
                return true;
            }
        }
        return false;
    }

    /**
     *If any of the neighbors are a Plip, return true, else return false.
     */
    private boolean plipsAreSeen(Map<Direction, Occupant> neighbors) {
        for (Occupant creature : neighbors.values()) {
            if (creature.getClass().equals(Plip.class)) {
                return true;
            }
        }
        return false;
    }


    public Direction attackRandom(Map<Direction, Occupant> neighbors){
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        for (Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            Direction key = entry.getKey();
            Occupant value = entry.getValue();
            if (value.getClass().equals(Plip.class)) {
                emptyNeighbors.add(key);
            }
        }
        return randomEntry(emptyNeighbors);
    }

    public Direction moveEmptyRandom(Map<Direction, Occupant> neighbors){
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        for (Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            Direction key = entry.getKey();
            Occupant value = entry.getValue();
            if (value.getClass().equals(Empty.class)) {
                emptyNeighbors.add(key);
            }
        }
        return randomEntry(emptyNeighbors);
    }

}
