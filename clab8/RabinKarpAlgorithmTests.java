import org.junit.Test;
import static org.junit.Assert.*;

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "hello";
        String pattern = "all";
        assertEquals(-1, RabinKarpAlgorithm.rabinKarp(input, pattern));

        String input1 = "hello world";
        String pattern1 = "orl";
        assertEquals(7, RabinKarpAlgorithm.rabinKarp(input1, pattern1));
    }
}
