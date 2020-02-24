package consoleVersion;

class DataViewer {
    private Trip trip;
    private FuelCost fuelCost;

    DataViewer(Trip trip, FuelCost fuelCost) {
	this.trip = trip;
	this.fuelCost = fuelCost;
    }

    /**
     * Łączy pobrane dane i wynik w String oraz wyświetla użytkownikowi
     * 
     * @param cost wynik wyliczenia
     */
    void viewData() {
	
	StringBuilder b = new StringBuilder();
	b.append(trip);
	b.append("Koszt podróży wyniesie ");
	b.append(fuelCost.getCost());
	b.append(" PLN");
	System.out.println(b.substring(0));
    }
}
