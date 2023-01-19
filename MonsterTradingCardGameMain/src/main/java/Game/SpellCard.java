package Game;

public class SpellCard extends Card {

    public SpellCard() {

    }

    public SpellCard(String id, String name, float damage, ElementType element) {
        super(id, name, damage, element);
    }

    public boolean defeatsInstantly(final Card other) {


        // Spell vs Spell

        // Spell vs Monster
        if (other instanceof MonsterCard) {
            var otherMonster = (MonsterCard) other;
            return this.getElement() == ElementType.WATER && otherMonster.getMonsterType() == MonsterType.KNIGHT;
        }
        return false;
    }
}
