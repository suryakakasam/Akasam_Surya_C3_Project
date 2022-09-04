public class DuplicateItemException extends Throwable {
    public DuplicateItemException(String itemName, String restaurantName) {
        super(String.format("The %s restaurants menu already has an item with the %s name", restaurantName, itemName));
    }
}
