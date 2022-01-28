import org.junit.Test;

public class MainClassTest extends MainClass{
    @Test
    public void testGetLocalNumber() {
        int exp = 14;

        if (exp == getLocalNumber()) {
            System.out.println("Test passed, expected result: " + exp + "; actual result: " + getLocalNumber());
        } else {
            System.out.println("Test failed, expected result: " + exp + "; actual result: " + getLocalNumber());
        }
    }
}
