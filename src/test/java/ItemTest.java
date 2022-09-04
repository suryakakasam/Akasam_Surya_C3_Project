import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    /** NEW TEST
     * Details : Test the toString() method
     */
    @Test
    public void items_to_string_method_should_return_desired_result() {
        Item item = new Item("Test Item", 100);
        assertEquals("Test Item:100\n", item.toString());
    }
}