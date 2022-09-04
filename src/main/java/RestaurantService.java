import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantService {
    private static final List<Restaurant> restaurants = new ArrayList<>();

    /** FEATURE IMPLEMENTED
     * Details : Iterate over restaurants list and search for the given name.
     *           If found, return the corresponding Restaurant object.
     *           Otherwise, throw RestaurantNotFoundException
     */
    public Restaurant findRestaurantByName(String restaurantName) throws RestaurantNotFoundException {
        //DELETE ABOVE STATEMENT AND WRITE CODE HERE
        for (Restaurant restaurant: restaurants) {
            if (restaurant.getName().equals(restaurantName)) {
                return restaurant;
            }
        }

        throw new RestaurantNotFoundException(restaurantName);
    }

    /** FEATURE UPDATED
     *  Details: Updated the addRestaurant method to throw a new DuplicateRestaurantException
     *           This exception will be thrown if the Admin tries to add duplicate Restaurant
     */
    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) throws DuplicateRestaurantException{
        try {
            findRestaurantByName(name);
            throw new DuplicateRestaurantException(name);
        } catch (RestaurantNotFoundException e) {
            Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
            restaurants.add(newRestaurant);
            return newRestaurant;
        }
    }

    public Restaurant removeRestaurant(String restaurantName) throws RestaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    /** FEATURE ADDED
     * Details : Added a new feature to remove all Restaurants.
     *           This method can also be used to reset the static variable restaurants before each test.
     *           If this were not the case, the tests will never be truly isolated.
     */
    public void removeAllRestaurants() {
        restaurants.clear();
    }

    /** FEATURE UPDATED
     * Details : Return an unmodifiable restaurants list to force read-only access
     */
    public List<Restaurant> getRestaurants() {
        return Collections.unmodifiableList(restaurants);
    }
}
