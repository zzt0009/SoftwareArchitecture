
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FinderTest {
    @Before public void setUp() {
    }

    @Test
    public void findMaxWithValidArray() {
        Finder f = new Finder();
        int[] intArray = {7, 3, 0, 1, 4};
        int ans = 7;
        int res = f.findMax(intArray);
        Assert.assertEquals(ans, res);
    }

    @Test
    public void findMinWithValidArray() {
        Finder f = new Finder();
        int[] intArray = {7, 3, 0, 1, 4};
        int ans = 0;
        int res = f.findMin(intArray);
        Assert.assertEquals(ans, res);
    }

    @Test
    public void findMaxWithNull() {
        Finder f = new Finder();
        Integer res = f.findMax(null);
        Assert.assertEquals(null, res);
    }

    @Test
    public void findMinWithNull() {
        Finder f = new Finder();
        Integer res = f.findMin(null);
        Assert.assertEquals(null, res);
    }

    @Test
    public void findMaxWithInvalidArray() {
        Finder f = new Finder();
        int[] intArray = {};
        Integer res = f.findMax(intArray);
        Assert.assertEquals(null, res);
    }

    @Test
    public void findMinWithInvalidArray() {
        Finder f = new Finder();
        int[] intArray = {};
        Integer res = f.findMin(intArray);
        Assert.assertEquals(null, res);
    }
}