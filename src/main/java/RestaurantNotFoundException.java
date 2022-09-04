public class RestaurantNotFoundException extends Throwable {
    public RestaurantNotFoundException(String restaurantName) {
        super(String.format("Restaurant with name %s is not found", restaurantName));
    }
}
