import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainClassTest extends MainClass{
    @Before
    public void testStartMessage() {
        System.out.println("================================================");
        System.out.println("Test has started");
    }

    @After
    public void testFinishMessage() {
        System.out.println("Test has finished");
    }

    @Test
    public void testGetLocalNumber() {
        int expected = 14;

        Assert.assertTrue("expected result " + expected + " != actual result " + getLocalNumber(), expected == getLocalNumber());
    }

    @Test
    public void testGetClasslNumber() {
        int expected = 45;

        Assert.assertTrue("Class number value" + getClassNumber() + " <= expected value " + expected, getClassNumber() > expected);
    }

    @Test
    public void testGetClasslString() {
        String expected1 = "Hello";
        String expected2 = "hello";
        String actual = getClassString();

        Assert.assertTrue("String '" + actual+ "' doesn't contain '" + expected1 + "' or '" + expected2 + "'", actual.contains(expected1) | actual.contains(expected2));
    }
}
