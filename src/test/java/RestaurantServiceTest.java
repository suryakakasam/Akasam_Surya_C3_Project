import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {
    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;

    //>>>>>>>>>>>>>>>>>>>>>>BEFORE EACH<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /** REFACTOR ALL THE REPEATED LINES OF CODE
     * Details : Refactored the RestaurantService object instantiation code into @BeforeEach setUp() method.
     */
    @BeforeEach
    public void setUp() throws DuplicateItemException, DuplicateRestaurantException {
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>BEFORE EACH<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /** TEST IMPLEMENTED
     * Details : Test if the findRestaurantByName method returns Amelie's cafe restaurant object
     */
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws RestaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE
        assertSame(restaurant, service.findRestaurantByName("Amelie's cafe"));
    }

    /** TEST IMPLEMENTED
     * Details : Test if the findRestaurantByName method throws RestaurantNotFoundException for Unknown Restaurant
     */
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() {
        //WRITE UNIT TEST CASE HERE
        assertThrows(RestaurantNotFoundException.class, () -> service.findRestaurantByName("Unknown Restaurant"));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>



    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws RestaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
    }

    /** NEW TEST
     * Details : Added a new test to make sure restaurants size is reset to 0 when all Restaurants are removed
     */
    @Test
    public void removing_all_should_set_restaurants_size_to_0() {
        service.removeAllRestaurants();
        assertEquals(0, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() {
        assertThrows(RestaurantNotFoundException.class, () -> service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1() throws DuplicateRestaurantException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());
    }

    /** NEW TEST
     * Details : Added a new test to make sure DuplicateRestaurantException is thrown when an Admin adds a new
     *           Restaurant when another Restaurant with the same name already exists
     */
    @Test
    public void adding_duplicate_restaurant_should_throw_exception() {
        assertThrows(DuplicateRestaurantException.class,
                () -> service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime));
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>



    //>>>>>>>>>>>>>>>>>>>>>>AFTER EACH<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * RESET OBJECTS AFTER EACH TEST
     * Details : Reset service, restaurant, and other objects after each test
     *           The RestaurantService class declares the restaurants as a private class variable (i.e., Static)
     *           So, once the service object is instantiated, everytime a test adds a restaurant to perform its validation,
     *              a new Restaurant object gets added to the restaurant list
     *           This means the tests are *never really isolated* because the restaurants variable (being class variable) is
     *              shared across the tests and this behaviour might cause unpredictable results.
     *           The service objects restaurant variable needs to be reset after each test.
     */
    @AfterEach
    public void tearDown() {
        service.removeAllRestaurants();
        restaurant = null;
        openingTime = null;
        closingTime = null;
    }
    //>>>>>>>>>>>>>>>>>>>>>>AFTER EACH<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}