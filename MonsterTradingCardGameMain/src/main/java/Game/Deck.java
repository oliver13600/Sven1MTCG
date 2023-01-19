package Game;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private final List<Card> cards = new ArrayList<>();

    public Deck(List<Card> deck) {
        if (deck != null) {
            for (int i = 0; deck.size() > i && i < 4; i++) {
                this.cards.add(deck.get(i));
            }
        }
    }

    public void removeCard(Card card) {
        if (cards != null) {
            cards.remove(card);
        }
    }

    public void addCard(Card card) {
        if (!cards.contains(card)) {
            cards.add(card);
        }
    }

    public Card getRandomCard() {
        if (cards != null && cards.size() > 0) {
            return cards.get((int) (Math.random() * cards.size()));
        }
        return null;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int getSize() {
        if (!isEmpty()) {
            return cards.size();
        }
        return 0;
    }
}
