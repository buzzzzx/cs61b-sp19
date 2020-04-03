package creatures;

import static org.junit.Assert.*;

import huglife.*;
import org.junit.Test;

import javax.crypto.spec.PSource;
import java.util.HashMap;

/** Test for Clorus
 * @author Remie Choo
 */

public class TestClorus {
    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2);
        Clorus babyClorus = c.replicate();
        assertEquals(1.0, c.energy(), 0.01);
        assertEquals(1.0, babyClorus.energy(), 0.01);
    }

    @Test
    public void testChoose() {
        // Case 1: No empty adjacent spaces; stay.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Plip());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // Case 2: With one Plip and two empty; Attack.
        HashMap<Direction, Occupant> attackSurrounded = new HashMap<>();
        attackSurrounded.put(Direction.TOP, new Empty());
        attackSurrounded.put(Direction.BOTTOM, new Plip());
        attackSurrounded.put(Direction.LEFT, new Empty());
        attackSurrounded.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(attackSurrounded);
        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(expected, actual);

        // Case 3: Energy >= 1 and no Plip and all empty; Replicate.
        c = new Clorus(2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<>();

        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);

        // Case 4: Energy >= 1 and no plip and top empty; Replicate.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);

        // Case 5: two empty and twp Plip creatures; Attack;
        c = new Clorus(1);
        HashMap<Direction, Occupant> twoPlipTwoempty = new HashMap<Direction, Occupant>();
        twoPlipTwoempty.put(Direction.TOP, new Empty());
        twoPlipTwoempty.put(Direction.BOTTOM, new Plip());
        twoPlipTwoempty.put(Direction.LEFT, new Plip());
        twoPlipTwoempty.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(twoPlipTwoempty);
        unexpected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertNotEquals(unexpected, actual);

    }
}
