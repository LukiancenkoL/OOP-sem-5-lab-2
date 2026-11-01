package lab;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void testMain() {
        Main.main(new String[0]);

        assertTrue(new File("target/grouped_planes.xml").exists(), "Grouped XML should be created");
    }
}