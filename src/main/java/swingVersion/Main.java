package swingVersion;

class Main {

    public static void main(String[] arguments) {

// because of interface use we can connect any database to our program, creating class implementing our interface and
// creating own methods saving data
        new SwingWindow(new DerbyOrTxtSaver());
    }
}
