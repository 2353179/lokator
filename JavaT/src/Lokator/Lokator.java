package Lokator;
public class Lokator {

	// vraca indeks na koji bi datu stanicu trebalo umetnuti
	public int RasporediStanicu(double y, double x, String name, Linija linija)

	{
		double razlika = 1000;
		double novarazlika;
		int index = 0;
		int brojac = 0;
		// System.out.println("BROJ KORDIANTA LINIJE" + linija.kordinate.size());

		for (Kordinate k : linija.kordinate) {
			// System.out.println("Kordinate u liniji su "+k.getY()+"," +k.getX());
			brojac++;
			// System.out.println(k.getX());
			// System.out.println(x);
			double prvi = (k.getX() - x);
			// System.out.println("prvi je"+prvi);
			if (prvi < 0)
				prvi *= -1;
			double drugi = (k.getY() - y);
			if (drugi < 0)
				drugi *= -1;

			novarazlika = prvi + drugi;
			// System.out.println("nova razlika"+novarazlika);

			if (novarazlika < razlika) {
				razlika = novarazlika;
				// System.out.println("indeks je"+index);
				index = linija.kordinate.indexOf(k);

			}

		}
		// System.out.println(brojac);
		return index;

	}

//Udaljenost u metrima
	public double Udaljenost(Kordinate prvaTacka, Kordinate drugaTacka) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(drugaTacka.getY() - prvaTacka.getY());
		double lonDistance = Math.toRadians(drugaTacka.getX() - prvaTacka.getX());
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(prvaTacka.getY()))
				* Math.cos(Math.toRadians(drugaTacka.getY())) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // m

		distance = Math.pow(distance, 2);

		return Math.sqrt(distance);
	}

}
