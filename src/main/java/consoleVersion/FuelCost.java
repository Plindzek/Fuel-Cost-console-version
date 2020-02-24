package consoleVersion;

public class FuelCost {

    private Trip trip;
    private double cost;

    public FuelCost(Trip trip) {
	this.trip = trip;
    }

    public double getCost() {
        return cost;
    }

    /**
     * oblicza koszt paliwa
     * 
     * @return cost
     */
    public double calculateFuelCost() {
	cost =  (Math.round((trip.getLpgOn100Km() *trip.getLpgPrice()* trip.getKmOnLpg() / 100 + trip.getPbOn100Km() * trip.getPbPrice()
		* trip.getKmOnPb() / 100) * 100.0))
		/ 100.0;
	
	return cost;
    }

    
}
