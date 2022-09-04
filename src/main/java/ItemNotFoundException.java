public class ItemNotFoundException extends Throwable {
    public ItemNotFoundException(String itemName, String restaurantName) {
        super(String.format("%s item is not found in the %s restaurant", itemName, restaurantName));
    }
}
