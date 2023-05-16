package object;

import techdebt.Design;
import techdebt.Maintenance;
import techdebt.Regulation;

public interface P {
    String getName();
    Maintenance getMaintenance();
    Regulation getRegulation();
    Design getDesign();
}
