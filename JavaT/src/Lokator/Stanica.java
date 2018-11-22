package Lokator;

import java.util.ArrayList;
import java.util.List;

public class Stanica {

	String ime;
	Kordinate kordinate;
	List<String> linijeKojeStaju = new ArrayList<>();
	String linijaKojaStaje;

	public Stanica(String ime, Kordinate kordinate, List<String> linijeKojeStaju) {
		this.ime = ime;
		this.kordinate = kordinate;
		this.linijeKojeStaju = linijeKojeStaju;

	}

}
