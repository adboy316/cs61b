package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {
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
    private static final double moveEnergy = - 0.15;
    /**
     * Energy gained with STAY action.
     */
    private static final double stayEnergy = 0.2;
    /**
     * If plip energy is 0, green should be value 63.
     */
    private static final int greenColorEnergy = 63;
    /**
     * If this sees a neighbor with name() clorus, it will move to empty
     * square with clorusMoveProbability
     */
    private double clorusMoveProbability = 0.5;
    /**
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        r = 99;
        g = (96 * (int) e) + greenColorEnergy;
        b = 76;
        energy = e;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() { this(1);
    }

    /**
     * Sets energy of Plip to x
     */
    public void setEnergy(double x) {
        this.energy = x;
    }


    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {


        g = (int) (96 * energy) + greenColorEnergy;
        return color(r, g, b);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        energy += moveEnergy;
        checkMaxMinEnergy();
        color();
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        energy += stayEnergy;
        checkMaxMinEnergy();
        color();
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        Plip newPlip = new Plip(energy * 0.5);
        this.energy = energy * 0.5;
        return newPlip;
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        boolean anyClorus = false;
        if(!adjacentSpacesAreEmpty(neighbors)){
            return new Action(Action.ActionType.STAY);
        }
        // Rule 2
        if (this.energy >= 1) {
            Direction availableSpace = findAvailableSpace(neighbors, emptyNeighbors);
            return new Action(Action.ActionType.REPLICATE, availableSpace);
        }
        // Rule 3
            //Search occupants for name with clorus
        for (Occupant creature: neighbors.values()) {
            if (creature.name().equals("clorus" )) {
                if (Math.random() < clorusMoveProbability) {
                    Direction availableSpace = findAvailableSpace(neighbors, emptyNeighbors);
                    return new Action(Action.ActionType.MOVE, availableSpace);
                }
            }
        }
        // Rule 4
        return new Action(Action.ActionType.STAY);
    }

    /**
     *If energy is greater than 2, then set to 2 instead.
     *If energy is less than 0, then set to 0 instead.
     */
    private void checkMaxMinEnergy() {
        if (energy > 2){
            energy = 2;
        }
        if (energy < 0){
            energy = 0;
        }
    }

    /**
     *If any value in neighbor is not Impassible, return false, else return true
     */
    private boolean adjacentSpacesAreEmpty(Map<Direction, Occupant> neighbors){
        for (Occupant creature: neighbors.values()){
            if (creature.getClass().equals(Empty.class)){
                return true;
            }
        }
        return false;
    }

    /**
     * Search neighbor map for Empty spaces, and adds empty spaces to emptyNeighbors
     * Return a random empty Direction
     * INVARIANT: Assumes that there is at least one empty space in neighbors
     */
    private Direction findAvailableSpace(Map<Direction, Occupant> neighbors, Deque<Direction> emptyNeighbors) {
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
