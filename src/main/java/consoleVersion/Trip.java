package consoleVersion;

public class Trip {

    private double lpgOn100Km, lpgPrice, kmOnLpg, pbOn100Km, pbPrice, kmOnPb;

    public double getLpgOn100Km() {
	return lpgOn100Km;
    }

    public void setLpgOn100Km(double lpgOn100Km) throws IllegalArgumentException {

	if (lpgOn100Km < 0) {
	    throw new IllegalArgumentException();
	} else {
	    this.lpgOn100Km = lpgOn100Km;
	}
    }

    public double getLpgPrice() {
	return lpgPrice;
    }

    public void setLpgPrice(double lpgPrice) throws IllegalArgumentException {
	if (lpgPrice < 0) {
	    throw new IllegalArgumentException();
	} else {
	    this.lpgPrice = lpgPrice;
	}

    }

    public double getKmOnLpg() {
	return kmOnLpg;
    }

    public void setKmOnLpg(double kmOnLpg) throws IllegalArgumentException {
	if (kmOnLpg < 0) {
	    throw new IllegalArgumentException();
	} else {
	    this.kmOnLpg = kmOnLpg;
	}

    }

    public double getPbOn100Km() {
	return pbOn100Km;
    }

    public void setPbOn100Km(double pbOn100Km) throws IllegalArgumentException {
	if (pbOn100Km < 0) {
	    throw new IllegalArgumentException();
	} else {
	    this.pbOn100Km = pbOn100Km;
	}
    }

    public double getPbPrice() {
	return pbPrice;
    }

    public void setPbPrice(double pbPrice) throws IllegalArgumentException {
	if (pbPrice < 0) {
	    throw new IllegalArgumentException();
	} else {
	    this.pbPrice = pbPrice;
	}
    }

    public double getKmOnPb() {
	return kmOnPb;
    }

    public void setKmOnPb(double kmOnPb) throws IllegalArgumentException {
	if (kmOnPb < 0) {
	    throw new IllegalArgumentException();
	} else {
	    this.kmOnPb = kmOnPb;
	}
    }

    @Override
    public String toString() {

	StringBuilder b = new StringBuilder();

	b.append("Cena LPG za 1 litr: ");
	b.append(lpgPrice);
	b.append(" PLN\nCena benzyny za 1 litr: ");
	b.append(pbPrice);
	b.append(" PLN\n");
	b.append(kmOnLpg);
	b.append(" kilometrów na LPG\n");
	b.append(kmOnPb);
	b.append(" kilometrów na benzynie\n");
	b.append(lpgOn100Km);
	b.append(" - spalanie LPG na 100km\n");
	b.append(pbOn100Km);
	b.append(" - spalanie benzyny na 100km\n");
	return b.substring(0);
    }

}
