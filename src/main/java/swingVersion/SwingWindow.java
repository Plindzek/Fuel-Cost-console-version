package swingVersion;

import consoleVersion.FuelCost;
import consoleVersion.Trip;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class SwingWindow extends JFrame implements FocusListener {

    private JTextField jLpgOn100km, jLpgPrice, jKmOnLPG, jPb95On100km, jPb95Price, jKmOnPB95, jCost;
    private JLabel title, title1, lpgOn100kmDesc, lpgPriceDesc, kmOnLPGDesc, pb95On100kmDesc, pb95PriceDesc,
            kmOnPB95Desc, solutionDesc;
    private JButton solveButton, exitButton, saveButton, loadButton;
    private JPanel titlePart, centerPart, bottomPart;
    private Trip trip = new Trip();
    private FuelCost fuelCost;

    SwingWindow(IO_operations io_operation) {// konstruktor okna
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

        //title.setText(" ");
        //title1.setText("");
        ActionListener listen = (ActionEvent e) -> {
            //title.setText(" ");
            //title1.setText("");
            Object source = e.getSource();
            if (source == solveButton) {

                createTrip();
                this.fuelCost = new FuelCost(trip);
                fuelCost.calculateFuelCost();
                jCost.setText("" + fuelCost.getCost());
                title.setText("Wykonano obliczenia. Wprowadz nowe dane:");
            }
            if (source == exitButton) {
                System.exit(0);
            }
            if (source == saveButton) {
                io_operation.saveData(trip, fuelCost);
                String status = io_operation.saveData(trip);
                title.setText(status);
            }
            if (source == loadButton) {
                String status = io_operation.readData(trip);
                jLpgOn100km.setText("" + trip.getLpgOn100Km());
                jLpgPrice.setText("" + trip.getLpgPrice());
                jKmOnLPG.setText("" + trip.getKmOnLpg());
                jPb95On100km.setText("" + trip.getPbOn100Km());
                jPb95Price.setText("" + trip.getPbPrice());
                jKmOnPB95.setText("" + trip.getKmOnPb());
                title.setText(status);
            }
        };
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


    private void createTrip() {// zamiana danych wprowadzonych przez usera na wartosci double + obsluga formatu
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

    @Override
    public Insets getInsets() {// ramka okna
        return new Insets(40, 20, 20, 20);
    }

    @Override
    public void focusGained(FocusEvent e1e) {
    }// reakcja na aktywacje elementu

    @Override
    public void focusLost(FocusEvent e1e) {// reakcja na wyjscie z elementu
        createTrip();
    }

}