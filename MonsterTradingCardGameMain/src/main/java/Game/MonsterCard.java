package Game;

public class MonsterCard extends Card {

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    private MonsterType monsterType;

    public MonsterCard(){

    }


    public MonsterCard(String id, String name, float damage, MonsterType monsterType, ElementType element) {
        super(id, name, damage, element);
        this.monsterType = monsterType;
    }

    public boolean defeatsInstantly(final Card other){
        // Monster vs Monster
        if(other instanceof MonsterCard){
            //Goblins are too afraid of Dragons to attack
            MonsterCard otherMonster = (MonsterCard)other;
            if(this.getMonsterType() == MonsterType.DRAGON && otherMonster.getMonsterType() == MonsterType.GOBLIN){
                return true;
            }
            //Wizzards can control Orks so they are not able to damage them
            if(this.getMonsterType() == MonsterType.WIZARD && otherMonster.getMonsterType() == MonsterType.ORK){
                return true;
            }
            //The FireElves know Dragons since they were little and can evade their attacks.
            if(this.getMonsterType() == MonsterType.FIREELF && this.getElement() == ElementType.FIRE && otherMonster.getMonsterType() == MonsterType.DRAGON){
                return true;
            }

        }

        // Spell vs Spell

        // Monster vs Spell
        if(other instanceof SpellCard){
            //The Kraken is immune against spells.
            return this.getMonsterType() == MonsterType.KRAKEN;
        }
        return false;
    }
}
