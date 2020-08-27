package swingVersion;

import consoleVersion.FuelCost;
import consoleVersion.Trip;


public interface IO_operations {

    /**
     * should return status (read/save success/fail) which can be displayed to user
     */
    String saveData(Trip trip);

    /**
     * should return status (read/save success/fail) which can be displayed to user
     */
    String saveData(Trip trip, FuelCost fuelCost);

    /**
     * should return status (read/save success/fail) which can be displayed to user
     */
    String readData(Trip trip);

    }
