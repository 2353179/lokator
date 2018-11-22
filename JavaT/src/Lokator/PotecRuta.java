package Lokator;

import java.util.ArrayList;
import java.util.List;

public class PotecRuta {

	List<String> stringovi = new ArrayList<>();
	int brojStanica;
	double ukupnoVreme;

	public PotecRuta(List<String> stringovi, int brojStanica, double ukupnoVreme) {
		super();
		this.stringovi = stringovi;
		this.brojStanica = brojStanica;
		this.ukupnoVreme = ukupnoVreme;
	}

	public void Ispis(PotecRuta r) {

		for (String s : r.stringovi) {

			System.out.println(s);
		}

	}

}
