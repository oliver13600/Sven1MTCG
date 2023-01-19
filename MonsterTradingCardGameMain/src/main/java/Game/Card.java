package Game;

public abstract class Card {

    private String id;
    private String name;
    private ElementType element;
    private float damage;

    private int bonusPoints = 0;

    public Card() {

    }

    public Card(String id, String name, float damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
    }


    public Card(String id, String name, float damage, ElementType element) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.element = element;
    }

    public abstract boolean defeatsInstantly(final Card other);

    //water -> fire
    //• fire -> normal
    //• normal -> water

    public float calculateDamage(final Card other) {
        float result = this.getDamage();
        //The element type does not effect pure monster fights.
        if (this instanceof MonsterCard && other instanceof MonsterCard) {
            return result;
        }
        //Identical Element
        if (this.element == other.getElement()) {
            return result;
        }
        if (this.element == ElementType.WATER && other.getElement() == ElementType.FIRE || this.element == ElementType.FIRE && other.getElement() == ElementType.NORMAL || this.element == ElementType.NORMAL && other.getElement() == ElementType.WATER) {
            return result * 2;
        }
        if (this.element == ElementType.FIRE && other.getElement() == ElementType.WATER || this.element == ElementType.NORMAL && other.getElement() == ElementType.FIRE || this.element == ElementType.WATER && other.getElement() == ElementType.NORMAL) {
            return result / 2;
        }
        return result;
    }

    public void levelUp() {
        this.bonusPoints++;
        if (this.bonusPoints > 3) {
            this.damage *= 1.25;
            this.bonusPoints = 0;
        }
    }

    public void LevelDown() {
        if (this.bonusPoints > 0) {
            this.bonusPoints--;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElementType getElement() {
        return element;
    }

    public void setElement(ElementType element) {
        this.element = element;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
