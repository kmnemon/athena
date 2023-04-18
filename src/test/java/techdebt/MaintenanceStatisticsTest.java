package techdebt;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static techdebt.MaintenanceStatistics.getMedianValue;

public class MaintenanceStatisticsTest {
    @Test
    public void testGetMedianValue(){
        Map<String, Integer> m = Map.of(
                "A", 3,
                "B", 6,
                "sdf", 1,
                "234", 4);

        int median =getMedianValue(m);
        assertEquals(4, median);
    }
}
