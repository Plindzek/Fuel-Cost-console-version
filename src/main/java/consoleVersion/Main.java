package consoleVersion;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	var trip = new Trip();

	var consoleReader = new ConsoleReader(trip);
	consoleReader.getData();


	var fuelCost = new FuelCost(trip);
	fuelCost.calculateFuelCost();

	var dv = new DataViewer(trip, fuelCost);
	dv.viewData();

	System.out.println("Wybierz '0' żeby zakończyć lub dowolny znak, żeby zacząć nowe wyliczenie");

	Scanner scan = new Scanner(System.in);
	String answer = scan.nextLine();

	if (!answer.equals("0")) {

	    Main.main(args);
	}

	else {
	    scan.close();
	}
    }

}
