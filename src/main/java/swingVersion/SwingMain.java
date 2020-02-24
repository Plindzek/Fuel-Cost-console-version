package swingVersion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import consoleVersion.FuelCost;
import consoleVersion.Trip;

public class SwingMain extends JFrame implements FocusListener{
    	
    	
	private static final long serialVersionUID = 1L;
	Trip trip = new Trip();
    FuelCost fuelCost = new FuelCost(trip);

	private JTextField jLpgOn100km, jLpgPrice, jKmOnLPG, jPb95On100km, jPb95Price, jKmOnPB95, jCost;
	private JLabel title, title1, lpgOn100kmDesc, lpgPriceDesc, kmOnLPGDesc, pb95On100kmDesc, pb95PriceDesc,
			kmOnPB95Desc, solutionDesc;
	private JButton solveButton, exitButton, saveButton, loadButton;
	private JPanel titlePart, centerPart, bottomPart;

	@Override
	public Insets getInsets() {// ramka okna
		return new Insets(40, 20, 20, 20);
	}

	private SwingMain() {// konstruktor okna
		super("Kalkulator spalania");
		setBounds(200, 10, 640, 480);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		title = new JLabel("Wprowadz dane:");
		title1 = new JLabel("");
		jLpgOn100km = new JTextField("0", 10);
		lpgOn100kmDesc = new JLabel("Spalanie LPG w l/100km:");
		jLpgPrice = new JTextField("0", 10);
		lpgPriceDesc = new JLabel("Cena LPG:");
		jKmOnLPG = new JTextField("0", 10);
		kmOnLPGDesc = new JLabel("Długość trasy w km na LPG:");
		jPb95On100km = new JTextField("0", 10);
		pb95On100kmDesc = new JLabel("Spalanie PB95/ON w l/100km:");
		jPb95Price = new JTextField("0", 10);
		pb95PriceDesc = new JLabel("Cena PB95/ON:");
		jKmOnPB95 = new JTextField("0", 10);
		kmOnPB95Desc = new JLabel("Długość trasy w km na PB95/ON:");
		jCost = new JTextField("0", 10);
		solutionDesc = new JLabel("Koszt trasy o podanych parametrach wyniesie:");
		solveButton = new JButton("Oblicz");
		solveButton.setPreferredSize(new Dimension(100, 60));
		exitButton = new JButton("Wyjście");
		exitButton.setPreferredSize(new Dimension(100, 60));
		saveButton = new JButton("Zapisz");
		saveButton.setPreferredSize(new Dimension(100, 60));
		loadButton = new JButton("Wczytaj");
		loadButton.setPreferredSize(new Dimension(100, 60));
		jCost.setEditable(false);

		jLpgOn100km.addFocusListener(this); // tworzenie reakcji na klikanie oraz aktywację/deaktywację pól
		jLpgPrice.addFocusListener(this);
		jKmOnLPG.addFocusListener(this);
		jPb95On100km.addFocusListener(this);
		jPb95Price.addFocusListener(this);
		jKmOnPB95.addFocusListener(this);

		solveButton.addActionListener(listen);
		exitButton.addActionListener(listen);
		saveButton.addActionListener(listen);
		loadButton.addActionListener(listen);

		titlePart = new JPanel(); // rozmieszcznie elementów w trzech grupach w okreslonej kolejności
		titlePart.add(title1);
		titlePart.add(title);

		centerPart = new JPanel();
		GridLayout grid = new GridLayout(6, 2, 1, 10);
		centerPart.setLayout(grid);
		centerPart.add(lpgOn100kmDesc);
		centerPart.add(jLpgOn100km);
		centerPart.add(lpgPriceDesc);
		centerPart.add(jLpgPrice);
		centerPart.add(kmOnLPGDesc);
		centerPart.add(jKmOnLPG);
		centerPart.add(pb95On100kmDesc);
		centerPart.add(jPb95On100km);
		centerPart.add(pb95PriceDesc);
		centerPart.add(jPb95Price);
		centerPart.add(kmOnPB95Desc);
		centerPart.add(jKmOnPB95);

		bottomPart = new JPanel();
		bottomPart.add(solveButton);
		bottomPart.add(loadButton);
		bottomPart.add(saveButton);
		bottomPart.add(exitButton);
		bottomPart.add(solutionDesc);
		bottomPart.add(jCost);

		setLayout(new BorderLayout(10, 10));
		add(titlePart, BorderLayout.NORTH);
		add(centerPart, BorderLayout.CENTER);
		add(bottomPart, BorderLayout.SOUTH);
		bottomPart.setPreferredSize(new Dimension(100, 100));

		setVisible(true);
	}

	public static void main(String[] arguments) {
		new SwingMain();
	}

	private ActionListener listen = (ActionEvent e) -> {// test lambdy - reakcja na wcisniecie przyciskow
		//title.setText(" ");
		//title1.setText("");
		Object source = e.getSource();
		if (source == solveButton) {
		    
		    	parseInput();
	    fuelCost.calculateFuelCost();
			updateFields();
		}
		if (source == exitButton) {
			System.exit(0);
		}
		if (source == saveButton) {
			saveToTxt();
			saveToDB();
		}
		if (source == loadButton) {
			readFromDB();
		}
	};

	@Override
	public void focusGained(FocusEvent e1e) {// reakcja na aktywacje elementu

	}

	@Override
	public void focusLost(FocusEvent e1e) {// reakcja na wyjscie z elementu
		
		parseInput();
	}

	private void parseInput() {// zamiana danych wprowadzonych przez usera na wartosci double + obsluga formatu
	    title1.setText("");
		title.setText(" ");
		try {
			trip.setLpgOn100Km(Double.parseDouble(jLpgOn100km.getText()));
		} catch (IllegalArgumentException e) {
			title.setText("Błędny format danych! Wprowadz ponownie:");
			jLpgOn100km.setText("0.00");
		}
		try {
			trip.setLpgPrice(Double.parseDouble(jLpgPrice.getText()));
		} catch (IllegalArgumentException e) {
		    title.setText("Błędny format danych! Wprowadz ponownie:");
			jLpgPrice.setText("0.00");
		}
		try {
		    trip.setKmOnLpg(Double.parseDouble(jKmOnLPG.getText()));
		} catch (IllegalArgumentException e) {
		    title.setText("Błędny format danych! Wprowadz ponownie:");
			jKmOnLPG.setText("0.00");
		}
		try {
			trip.setPbOn100Km(Double.parseDouble(jPb95On100km.getText()));
		} catch (IllegalArgumentException e) {
		    title.setText("Błędny format danych! Wprowadz ponownie:");
			jPb95On100km.setText("0.00");
		}
		try {
			trip.setPbPrice(Double.parseDouble(jPb95Price.getText()));
		} catch (IllegalArgumentException e) {
		    title.setText("Błędny format danych! Wprowadz ponownie:");
			jPb95Price.setText("0.00");
		}
		try {
			trip.setKmOnPb(Double.parseDouble(jKmOnPB95.getText()));
		} catch (IllegalArgumentException e) {
		    title.setText("Błędny format danych! Wprowadz ponownie:");
			jKmOnPB95.setText("0.00");
		}

	}

	private void updateFields() {
	    jCost.setText("" + fuelCost.getCost());
	    title.setText("Wykonano obliczenia. Wprowadz nowe dane:");
	}

	private void saveToTxt() {// zapis do pliku txt
		Path plik = Paths.get("Dane.txt");
		try (BufferedWriter pw1 = Files.newBufferedWriter(plik, StandardCharsets.UTF_16);
		// PrintWriter pw = new PrintWriter(new BufferedWriter(new
		// FileWriter("Dane.txt")));
		) {
			pw1.write("Spalanie LPG na 100km: " + jLpgOn100km.getText() + "\n" + "Cena LPG:"
					+ jLpgPrice.getText() + "\n" + "Ilość kilometrów na LPG: " + jKmOnLPG.getText() + "\n"
					+ "Spalanie pb95 na 100km: " + jPb95On100km.getText() + "\n" + "Cena PB95: "
					+ jPb95Price.getText() + "\n" + "Ilość kilometrów na pb95: " + jKmOnPB95.getText() + "\n"
					+ "Koszt trasy wyniesie: " + jCost.getText());

		} catch (IOException e) {
			System.err.println("Błąd we/wy");
			e.printStackTrace();
		}

	}

	private void saveToDB() {// zapis do bazy danych
		
	    try (
			Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/D:/Databases/Baza Adama",
				"Adam", "1234")) {
			Statement mkTable = conn.createStatement();
			mkTable.executeUpdate(
					"CREATE TABLE cardata(lpgOn100km double, lpgPrice double, kmOnLPG double, pb95On100km double, pb95Price double, kmOnPB95 double)");
		} catch (SQLException ole) {
			System.out.println(ole.getMessage());
		}
		try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/D:/Databases/Baza Adama",
				"Adam", "1234");
				PreparedStatement upTable = conn.prepareStatement("INSERT INTO "
						+ "ADAM.CARDATA(lpgOn100km, lpgPrice, kmOnLPG, pb95On100km, pb95Price, kmOnPB95)\r\n"
						+ "VALUES(?,?,?,?,?,?)"))
		// "UPDATE ADAM.CARDATA SET DLPGON100KM=?" gdy chcemy nadpisac wszystkie wiersze
		// lub wybieramy wg klucza z WHERE. Nie dziala, gdy brak wierszy
		{
			upTable.setDouble(1, trip.getLpgOn100Km());
			upTable.setDouble(2, trip.getLpgPrice());
			upTable.setDouble(3, trip.getLpgOn100Km());
			upTable.setDouble(4, trip.getPbOn100Km());
			upTable.setDouble(5, trip.getPbPrice());
			upTable.setDouble(6, trip.getKmOnPb());
			upTable.executeUpdate();
			title.setText("Wartości zostały zapisane w bazie danych");
		} catch (SQLException ole) {
		    JOptionPane.showMessageDialog(null, "Błąd zapisu do bazy danych");	
		    System.out.println(ole.getMessage());
		}
	}

	private void readFromDB() {
	    
		try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/D:/Databases/Baza Adama",
				"Adam", "1234"); PreparedStatement upTable = conn.prepareStatement("SELECT*FROM ADAM.CARDATA")) {
			ResultSet result = upTable.executeQuery();

			while (result.next()) {
				jLpgOn100km.setText("" + result.getDouble("LPGON100KM"));
				jLpgPrice.setText("" + result.getDouble("LPGPRICE"));
				jKmOnLPG.setText("" + result.getDouble("KMONLPG"));
				jPb95On100km.setText("" + result.getDouble("PB95ON100KM"));
				jPb95Price.setText("" + result.getDouble("PB95PRICE"));
				jKmOnPB95.setText("" + result.getDouble("KMONPB95"));

			}
			title.setText("Wczytano ostatnio zapisane dane");
		}

		catch (SQLException ole) {
			System.out.println(ole.getMessage());
			title.setText("Brak rekordów, najpierw wykonaj zapis danych!");
		}

	}
}