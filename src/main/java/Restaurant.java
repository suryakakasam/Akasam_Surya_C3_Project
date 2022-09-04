import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant {
    private final String name;
    private final String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private final List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    /** FEATURE IMPLEMENTED
     * Details : If currentTime is greater than or equal to openingTime and is less than or equal to closingTime
     *           Then, the Restaurant is still operational, so returns True
     *           Otherwise, the Restaurant hasn't been open yet or closed for the day, so return False
     */
    public boolean isRestaurantOpen() {
        LocalTime currentTime = getCurrentTime();
        return currentTime.compareTo(openingTime) >= 0 && currentTime.compareTo(closingTime) <= 0;
    }

    public LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    /** FEATURE UPDATED
     * Details : Returns an unmodifiable menu list to force read-only access
     */
    public List<Item> getMenu() {
        return Collections.unmodifiableList(menu);
    }

    private Item findItemByName(String itemName) {
        for (Item item : menu) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    /** FEATURE UPDATED
     * Details : Updated addToMenu method to throw a new DuplicateItemException.
     *           This exception will be thrown if the Admin/Restaurant Owner tries to add a new Item
     *           with a name that is already present in the Menu
     */
    public void addToMenu(String itemName, int itemPrice) throws DuplicateItemException {
        Item itemToBeAdded = findItemByName(itemName);
        if (itemToBeAdded != null)
            throw new DuplicateItemException(itemName, name);

        Item newItem = new Item(itemName, itemPrice);
        menu.add(newItem);
    }

    public void removeFromMenu(String itemName) throws ItemNotFoundException {
        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new ItemNotFoundException(itemName, name);

        menu.remove(itemToBeRemoved);
    }

    /** NEW FEATURE
     * Details: Remove all Items from a Restaurants menu.
     */
    public void removeAllFromMenu() {
        menu.clear();
    }

    /** NEW FEATURE
     * Details : This method takes in a Varargs of Item names (String) and returns the total cost of the order
     * Throws  : ItemNotFoundException
     */
    public int getOrderValue(String... itemNames) throws ItemNotFoundException {
        int orderValue = 0;

        for (String itemName : itemNames) {
            Item selectedItem = findItemByName(itemName);
            if (selectedItem == null)
                throw new ItemNotFoundException(itemName, name);
            orderValue += selectedItem.getPrice();
        }

        return orderValue;
    }

    public void displayDetails() {
        System.out.println("Restaurant:" + name + "\n"
                + "Location:" + location + "\n"
                + "Opening time:" + openingTime + "\n"
                + "Closing time:" + closingTime + "\n"
                + "Menu:" + "\n" + getMenu());
    }

    public String getName() {
        return name;
    }
}
