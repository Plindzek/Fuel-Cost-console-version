package consoleVersion;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleReader {
    private Trip trip;
    private Scanner scan;

    ConsoleReader(Trip trip) {
	this.trip = trip;
    }

    /**
     * ustawia wartości pól klasy Trip danymi pobranymi od użytkownika za pomocą
     * klasy Scanner
     * 
     */
    public void getData() {
	boolean isNotCorrect = true;

	do {
	    try {
		scan = new Scanner(System.in);
		System.out.print("Podaj szacowane średnie spalanie benzyny (w litrach/100km) w Twoim samochodzie \n"
			+ "wez pod uwagę charakter trasy, którą chcesz pokonać:");
		trip.setPbOn100Km(scan.nextDouble());
		System.out.print("Podaj szacowane średnie spalanie LPG (w litrach/100km) w Twoim samochodzie \n"
			+ "wez pod uwagę charakter trasy, którą chcesz pokonać:");
		trip.setLpgOn100Km(scan.nextDouble());
		System.out.print("Ile kosztuje litr benzyny?");
		trip.setPbPrice(scan.nextDouble());
		System.out.print("Ile kosztuje litr LPG?");
		trip.setLpgPrice(scan.nextDouble());
		System.out.print("Ile km przejedziesz na LPG?");
		trip.setKmOnLpg(scan.nextDouble());
		System.out.print("Ile km przejedziesz na benzynie?");
		trip.setKmOnPb(scan.nextDouble());

		isNotCorrect = false;
	    } catch (InputMismatchException | IllegalArgumentException e1) {
		System.out.println("Niepoprawny format danych, wprowadz ponownie");
	    }
	} while (isNotCorrect);
    }
}
