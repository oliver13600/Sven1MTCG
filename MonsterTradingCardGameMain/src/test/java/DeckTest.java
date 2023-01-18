import Game.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class DeckTest {
    @Test
    public void testRemoveCard() {
        Deck deck = new Deck(null);
        Card testcard1 = new MonsterCard("123","test", 2, MonsterType.DRAGON, ElementType.FIRE);
        deck.addCard(testcard1);
        deck.removeCard(testcard1);
        assertEquals(0, deck.getSize());
    }

    @Test
    public void testAddCard() {
        Deck deck = new Deck(null);
        Card testcard1 = new MonsterCard("123","test", 2, MonsterType.DRAGON, ElementType.FIRE);
        deck.addCard(testcard1);
        assertEquals(1, deck.getSize());
    }

    @Test
    public void testGetRandomCard() {
        Deck deck = new Deck(null);
        Card testcard1 = new MonsterCard("123","test", 2, MonsterType.DRAGON, ElementType.FIRE);
        Card testcard2 = new MonsterCard("123","test", 2, MonsterType.GOBLIN, ElementType.FIRE);
        deck.addCard(testcard1);
        assertTrue(deck.getRandomCard() instanceof MonsterCard);
    }

    @Test
    public void testIsEmpty() {
        Deck deck = new Deck(null);
        assertTrue(deck.isEmpty());
        Card testcard1 = new MonsterCard("123","test", 2, MonsterType.DRAGON, ElementType.FIRE);
        deck.addCard(testcard1);
        assertFalse(deck.isEmpty());
    }

    @Test
    public void testGetSize() {
        Deck deck = new Deck(null);
        assertEquals(0, deck.getSize());
        Card testcard1 = new MonsterCard("123","test", 2, MonsterType.DRAGON, ElementType.FIRE);;
        deck.addCard(testcard1);
        assertEquals(1, deck.getSize());
    }
}
