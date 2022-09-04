public class DuplicateRestaurantException extends Throwable {
    public DuplicateRestaurantException(String restaurantName) {
        super(String.format("Restaurant with name %s already exists", restaurantName));
    }
}
