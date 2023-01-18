import Game.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CardTest {
    @Test
    void defeatsInstantly0() {
        Card testcard1 = new MonsterCard("123","test", 2, MonsterType.DRAGON, ElementType.FIRE);
        Card testcard2 = new MonsterCard("123","test", 2, MonsterType.GOBLIN, ElementType.FIRE);
        assertTrue(testcard1.defeatsInstantly(testcard2));
    }
    @Test
    void defeatsInstantly1() {
        Card testcard1 = new SpellCard("123","test", 2, ElementType.FIRE);
        Card testcard2 = new MonsterCard("123","test", 2, MonsterType.KNIGHT, ElementType.FIRE);
        assertFalse(testcard1.defeatsInstantly(testcard2));
    }
    @Test
    void defeatsInstantly3() {
        Card testcard1 = new SpellCard("123","test", 2, ElementType.FIRE);
        Card testcard2 = new SpellCard("123","test", 2, ElementType.FIRE);
        assertFalse(testcard1.defeatsInstantly(testcard2));
    }
    @Test
    void calculateDamage0() {
        Card testcard1 = new MonsterCard("123","test", 2, MonsterType.DRAGON, ElementType.FIRE);
        Card testcard2 = new MonsterCard("123","test", 2, MonsterType.GOBLIN, ElementType.FIRE);
        assertEquals(testcard1.calculateDamage(testcard2), testcard2.calculateDamage(testcard1));
    }
    @Test
    void calculateDamage1() {
        Card testcard1 = new SpellCard("123","test", 2, ElementType.WATER);
        Card testcard2 = new MonsterCard("123","test", 2, MonsterType.GOBLIN, ElementType.FIRE);
        assertEquals(4, testcard1.calculateDamage(testcard2));
    }
    @Test
    void calculateDamage2() {
        Card testcard1 = new SpellCard("123","test", 5, ElementType.FIRE);
        Card testcard2 = new MonsterCard("123","test", 2, MonsterType.GOBLIN, ElementType.WATER);
        assertEquals(2.5, testcard1.calculateDamage(testcard2));
    }

}
