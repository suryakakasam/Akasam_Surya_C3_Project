import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;

    //>>>>>>>>>>>>>>>>>>>>>>BEFORE EACH<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /** REFACTOR ALL THE REPEATED LINES OF CODE
     * Details : Refactored the Restaurant object instantiation code into @BeforeEach setUp() method.
     *           Added Mockito Spy to mock the functionality of Restaurant Open/Close tests.
    */
    @BeforeEach
    public void setUp() throws DuplicateItemException {
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = Mockito.spy(new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime));
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>BEFORE EACH<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    /** TEST IMPLEMENTED
     * Details : Test if isRestaurantOpen() method returns true for 11:30:00 for Amelie's cafe
     *           Use the mocked Restaurant object to return operationalTime when getCurrentTime() method is called
     *           Assert that restaurant is open during the operational hours
     *           Verify that the getCurrentTime() method has been called exactly one time
     */
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        //WRITE UNIT TEST CASE HERE
        LocalTime operationalTime = LocalTime.parse("11:30:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(operationalTime);
        assertTrue(restaurant.isRestaurantOpen());
        Mockito.verify(restaurant, Mockito.times(1)).getCurrentTime();
    }

    /** TEST IMPLEMENTED
     * Details : Test if isRestaurantOpen() method returns false for 23:30:00 for Amelie's cafe
     *           Use the mocked Restaurant object to return nonOperationalTime when getCurrentTime() method is called
     *           Assert that restaurant is closed during the non-operational hours
     *           Verify that the getCurrentTime() method has been called exactly one time
     */
    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        //WRITE UNIT TEST CASE HERE
        LocalTime nonOperationalTime = LocalTime.parse("23:30:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(nonOperationalTime);
        assertFalse(restaurant.isRestaurantOpen());
        Mockito.verify(restaurant, Mockito.times(1)).getCurrentTime();
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() throws DuplicateItemException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    /** NEW TEST
     * Details: Make sure more than one Item in the menu does not have the same name
     */
    @Test
    public void adding_duplicate_item_to_menu_should_throw_exception() {
        assertThrows(DuplicateItemException.class,
                () -> restaurant.addToMenu("Sweet corn soup", 125));
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws ItemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    /** NEW TEST
     * Details : Added a new test to make sure menu size is reset to 0 when all items are removed
     */
    @Test
    public void removing_all_items_from_menu_should_set_menu_size_to_0() {
        restaurant.removeAllFromMenu();
        assertEquals(0, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(ItemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }

    /** NEW TEST - FEATURE getOrderValue IMPLEMENTED
     * Details : When getOrderValue on a restaurant object is called with valid Item Names,
     *           the method should return the estimated cost.
     */
    @Test void get_order_value_must_return_the_estimated_cost_when_valid_item_names_are_passed()
            throws DuplicateItemException, ItemNotFoundException {
        String item1Name = "Item-1", item2Name = "Item-2";
        int item1Price = 10, item2Price = 20;

        restaurant.addToMenu(item1Name, item1Price);
        restaurant.addToMenu(item2Name, item2Price);

        assertEquals((item1Price + item2Price), restaurant.getOrderValue(item1Name, item2Name));
    }

    /** NEW TEST - FEATURE getOrderValue IMPLEMENTED
     * Details : When getOrderValue on a restaurant object is called with invalid Item Names,
     *           the method should throw an ItemNotFoundException
     */
    @Test void get_order_value_should_throw_exception_when_invalid_item_name_is_passed() {
        assertThrows(ItemNotFoundException.class,
                () -> restaurant.getOrderValue("Unknown Item"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    //<<<<<<<<<<<<<<<<<<<<<<<OTHERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /** NEW TEST
     * Details : Test the toString() method
     */
    @Test
    public void restaurants_to_string_method_should_return_desired_result() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        //Setup Streams
        System.setOut(new PrintStream(outContent));

        //Execute Test
        restaurant.displayDetails();
        assertEquals(
                "Restaurant:Amelie's cafe\n" +
                "Location:Chennai\n" +
                "Opening time:10:30\n" +
                "Closing time:22:00\n" +
                "Menu:\n" +
                "[Sweet corn soup:119\n" +
                ", Vegetable lasagne:269\n" +
                "]" + System.getProperty("line.separator")
                , outContent.toString()
        );

        //Restore Streams
        System.setOut(originalOut);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<OTHERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    //>>>>>>>>>>>>>>>>>>>>>>AFTER EACH<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * RESET OBJECTS AFTER EACH TEST
     */
    @AfterEach
    public void tearDown() {
        restaurant = null;
        openingTime = null;
        closingTime = null;
    }
    //>>>>>>>>>>>>>>>>>>>>>>AFTER EACH<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}