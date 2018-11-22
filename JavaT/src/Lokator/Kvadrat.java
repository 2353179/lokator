package Lokator;

public class Kvadrat {

	private Kordinate GL;
	private Kordinate GD;
	private Kordinate DL;
	private Kordinate DD;
	private double stranica;
	private Kordinate sredisnjaTacka;

	public Kvadrat(double rastojanje, Kordinate sredisnjaTacka) {

		this.stranica = rastojanje * 2;

		double lat = sredisnjaTacka.getY();
		double lon = sredisnjaTacka.getX();
		double R = 6378137;

		double dNORTH = rastojanje;
		double dEAST = -1 * rastojanje;

		double dLat = dNORTH / R;
		double dLon = dEAST / (R * Math.cos(3.14 * lat / 180));

		double latO = lat + dLat * 180 / 3.14;
		double lonO = lon + dLon * 180 / 3.14;

		GL = new Kordinate(latO, lonO);

		lat = sredisnjaTacka.getY();
		lon = sredisnjaTacka.getX();

		R = 6378137;
		this.sredisnjaTacka = new Kordinate(51, 0);

		dNORTH = rastojanje;
		dEAST = rastojanje;

		dLat = dNORTH / R;
		dLon = dEAST / (R * Math.cos(3.14 * lat / 180));

		latO = lat + dLat * 180 / 3.14;
		lonO = lon + dLon * 180 / 3.14;

		GD = new Kordinate(latO, lonO);

		lat = sredisnjaTacka.getY();
		lon = sredisnjaTacka.getX();
		R = 6378137;
		this.sredisnjaTacka = new Kordinate(51, 0);
		dNORTH = -1 * rastojanje;
		dEAST = -1 * rastojanje;

		dLat = dNORTH / R;
		dLon = dEAST / (R * Math.cos(3.14 * lat / 180));

		latO = lat + dLat * 180 / 3.14;
		lonO = lon + dLon * 180 / 3.14;

		DL = new Kordinate(latO, lonO);

		lat = sredisnjaTacka.getY();
		lon = sredisnjaTacka.getX();
		R = 6378137;
		this.sredisnjaTacka = new Kordinate(51, 0);
		dNORTH = -1 * rastojanje;
		dEAST = rastojanje;

		dLat = dNORTH / R;
		dLon = dEAST / (R * Math.cos(3.14 * lat / 180));

		latO = lat + dLat * 180 / 3.14;
		lonO = lon + dLon * 180 / 3.14;

		DD = new Kordinate(latO, lonO);

	}

	public boolean Pripada(Kordinate kor) {
		// AKo ispunjava ovaj if znaci da tacka kor se nalazi u datom kvadratu ciji je
		// centar pocetna tacka
		if ((GD.getX() >= kor.getX() && kor.getX() >= GL.getX())
				&& (GL.getY() >= kor.getY() && kor.getY() >= DL.getY()))
			return true;

		else
			return false;

	}

}