package Lokator;

public class Kordinate {

	double x;
	double y;

	// ako je stanica nece biti null vec ce biti naziv stanice, ako je null znaci da
	// je to samo neka geog kordinata
	public String stanica = "null";

	public Kordinate(String s) {

		String[] strings = s.split(",");
		y = Double.valueOf(strings[0]);
		x = Double.valueOf(strings[1]);
	}

	public Kordinate(double y, double x) {

		this.x = x;
		this.y = y;
	}

	public Kordinate(double y, double x, String stanica) {

		this.x = x;
		this.y = y;
		this.stanica = stanica;
	}

	public String getXY() {
		return x + "," + y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setXY(double y, double x) {
		this.x = x;
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

}
