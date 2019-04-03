package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/** Tests the plip class
 *  @authr Ariel Delgado
 */
public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals("clorus", c.name());
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(2 - .03, c.energy(), 0.01);
        c.move();
        assertEquals(2 - .03 - .03, c.energy(), 0.01);
        c.stay();
        assertEquals(2 - .03 - .03 - 0.01, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2.0);
        double expectedEnergy = c.energy() * 0.5;
        Clorus r = c.replicate();
        assertEquals(expectedEnergy, c.energy(), 0.1);
        assertEquals(expectedEnergy, r.energy(), 0.1);
    }

    @Test
    public void testAttack() {
        Clorus c = new Clorus(2.0);
        Plip p = new Plip(2.0);
        c.attack(p);
        assertEquals(4, c.energy(), 0.01);
//        assertEquals(0, p.energy(), 0.01);
    }

    @Test
    public void testChooseAction() {
        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // No empty adjacent spaces, but there is a Plip nearby; attack.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
        topPlip.put(Direction.TOP, new Plip(2));
        topPlip.put(Direction.BOTTOM, new Impassible());
        topPlip.put(Direction.LEFT, new Impassible());
        topPlip.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topPlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);

        // Energy >= 1; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);

        // Energy < 1; MOVE towards an empty space.
        c = new Clorus(0.5);
        HashMap<Direction, Occupant> leftEmpty = new HashMap<Direction, Occupant>();
        leftEmpty.put(Direction.TOP, new Impassible());
        leftEmpty.put(Direction.BOTTOM, new Impassible());
        leftEmpty.put(Direction.LEFT, new Empty());
        leftEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(leftEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.LEFT);

        assertEquals(expected, actual);
    }

}
