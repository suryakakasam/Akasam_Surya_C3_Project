public class Item {
    private final String name;
    private final int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    /** NEW FEATURE
     * Details : Getter for the Price attribute
     */
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + ":" + price + "\n";
    }
}
