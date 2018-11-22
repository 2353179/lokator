package Lokator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class main {

	public static void main(String[] args) {
		// INPUT vrednosti
		
		System.out.println("Enter: ");
		Scanner scanner = new Scanner(System.in);
		String unos = scanner.nextLine();

		String[] strings = unos.split(" ");
		//PRIMER INPUTA 
		//45.255151,19.851358 45.240013,19.885315 05:50 myJSON.json
		
		Sat vremePolaska = new Sat(strings[2]);
		Kordinate K = new Kordinate(strings[1]);
		Kordinate P = new Kordinate(strings[0]);

		Lokator lok = new Lokator();
		List<Linija> sveLinije = new ArrayList<>();

		// ****************************************************************

		// Kreiramo kvadrat oko pocntne tacke tj ona je u centru
		Kvadrat pocetniKvadrat = new Kvadrat(lok.Udaljenost(P, K), P);
		// Sad treba proveriti koje se sve stanice nalaze unutar

		// PARSER********************************************************
		JSONParser parser = new JSONParser();

		String name;
		String description;
		List<Kordinate> kordinate = new ArrayList<>();
		List<Sat> satnice = new ArrayList<>();
		;
		List<Stanica> sveStanice = new ArrayList<>();
		List<PotecRuta> sveRute = new ArrayList<>();

		try {
			Object obj = parser.parse(new FileReader(strings[3]));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray linesArray = (JSONArray) jsonObject.get("lines");

			for (Object o : linesArray) {
				JSONObject jsoo = (JSONObject) o;
				name = (String) jsoo.get("name");
				// System.out.println(name);

				description = (String) jsoo.get("description");
				// System.out.println(description);

				JSONArray kordinateArray = (JSONArray) jsoo.get("coordinates");
				for (Object ob : kordinateArray) {
					JSONObject jsooo = (JSONObject) ob;
					String lat = (String) jsooo.get("lat");
					// System.out.println(lat);

					String lon = (String) jsooo.get("lon");
					// System.out.println(lon);
					// System.out.println(lat+","+lon);
					kordinate.add(new Kordinate(lat + "," + lon));

					// System.out.println("size " +kordinate.size());

				}
				JSONArray satniceArray = (JSONArray) jsoo.get("timeTable");
				for (Object sat : satniceArray) {
					String s = "" + sat;
					satnice.add(new Sat(s));
				}

				sveLinije.add(new Linija(name, description, kordinate, satnice, sveStanice));
				kordinate.clear();

			}
			// __________________________________________________
			// System.out.println(sveLinije.get(3).ime);

			JSONArray stopsArray = (JSONArray) jsonObject.get("stops");

			for (Object s : stopsArray)

			{

				JSONObject jsoo = (JSONObject) s;
				String nname = (String) jsoo.get("name");
				// System.out.println(name);

				String lat = (String) jsoo.get("lat");
				// System.out.println(lat);

				String lon = (String) jsoo.get("lon");
				// System.out.println(lon);

				JSONArray linijeArray = (JSONArray) jsoo.get("lines");

				// Za svaku stanicu i bus koji na njoj staje trazimo sve kordiante te linije busa i
				// umecemo stanicu kao jednu od kordinata u spisak kordinata linije
				for (Object ob : linijeArray) {
					
					for (Linija l : sveLinije) {
						int pomocna=1;
						if (l.ime.equals(ob.toString())) {
							//System.out.println(l.ime+" " + nname);


							// System.out.println(l.ime+" "+ob.toString() + " stanica " + nname);
							// System.out.println(ob.toString());

							// System.out.println("******");
							
							for(Kordinate g : l.kordinate)
							{
								if(g.stanica.equals(ob.toString()))
									pomocna=0;
							}
							if(pomocna==0)
								break;
							int index = lok.RasporediStanicu(new Double(lat), new Double(lon), nname, l);
							// System.out.println("***index je***"+index);

							// System.out.println("broj kordinata u mainu"+ l.kordinate.size());
							
							l.kordinate.add(index, new Kordinate(new Double(lat), new Double(lon), nname));
							// System.out.println("***da li je dobro***"+l.kordinate.get(index).stanica);

							break;
						}
					}
				}

			}

		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ------------------------do sad su zavrseni ocitavanje jsona zatim ubacivanje
		// stanica u listu kordinata svake linije---------------------

		// ---------------SAD ocitavamo prvo slonodno vreme za dati bus kao i rutu

		Sat potrebnoVremePolaskaLinije = new Sat("99:99");
		for (Linija l : sveLinije) {

			int brojac = 0;
			Kordinate najblizaStanicaP = new Kordinate(0, 0);
			Kordinate najblizaStanicaK = new Kordinate(0, 0);

			int provera = 0;

			// Provera da li neki deo puta autobusa pripada kvadratu i trazi najblizu i
			// najdalju stanicu
			for (Kordinate kor : l.kordinate) {
				if (pocetniKvadrat.Pripada(kor) && (kor.stanica.equals("null") == false)) {

					//System.out.println(l.ime+" PRIPADA " + kor.stanica);
					while (brojac < 1) {
						brojac++;
						najblizaStanicaP = kor;
						najblizaStanicaK = kor;

					}

					if (lok.Udaljenost(P, najblizaStanicaP) > lok.Udaljenost(P, kor)) {
						najblizaStanicaP = kor;
						provera++;

					}

					if (lok.Udaljenost(K, najblizaStanicaK) > lok.Udaljenost(K, kor)) {
						najblizaStanicaK = kor;
						provera++;

					}

					// Znaci da data kordinata linije pripada u tom kvadratu prelazimo onda
					// na sledecu kordinatu, sve dok ne naidjemo na neku koja ne pripada vise
					// kad krene da pripada treba racunati ujedno ovde i proveravati i stanice
					// i satnicu koliko se kretao
					// a pre toga treba imati neku funkciju koja racuna udaljenost

				}

			}
			// ako ta linija nemu tu rutu treba prekinuti tu petlju jer je bespotrebna
			if (najblizaStanicaP.stanica.equals("null"))
				continue;


			// ********Kad smo nasli stanicu najblizu i najdalju,treba izracunati vreme
			// pesacenja do te stanice, zatim vreme dolaska autobusa
			// zatim broj stanica koje ce preci i na kraju vreme pesecanja do destinacije
			// odnosno ukupno vreme, ukupan broj stanica, i sve aktivnosti

			// *********Logika izracunavanje vremena i stanica

			double vremeuMin = 0;
			// u minutima vreme pesacenja
			vremeuMin = (lok.Udaljenost(P, najblizaStanicaP) / 66.666);
			List<String> stringovi = new ArrayList<>();

			stringovi.add(l.ime);

			stringovi.add("***********************Pocetak rute " + vremePolaska.getSati() + ":" + vremePolaska.getMinute());
			stringovi.add("Aktivnost:Setnja    Pocetna tacka: " + P.y + "," + P.x + " Krajnja tacka: "
					+ najblizaStanicaP.y + "," + najblizaStanicaP.x);
			stringovi.add("Vreme potrebno do najblize stanice " + najblizaStanicaP.stanica + " laganim hodom je "
					+ Math.round(vremeuMin) + " min.");

			int br = 0;
			int c = 0;
			Kordinate prethodna = new Kordinate(0, 0);

			int brojStanica = 0;
			double vremeUVoznji = 0;
			double vremeUPutu = 0;
			double ukupnoVreme = 0;

			
			if (provera > 0) {
				for (Kordinate kor : l.kordinate) {
					// System.out.println(sveLinije.size());

					if (br < 1) {
						br = 2;
						prethodna = kor;
						continue;
					}
					// System.out.println(l.ime+" STANICE"+ kor.stanica);

					if (c == 0)
						vremeUPutu += (lok.Udaljenost(prethodna, kor) / 666.666);

					// Ovo je ako se dupliraju stanice u JSON kao u ona dva primera sto smo dobili
					if ((lok.Udaljenost(prethodna, kor) / 666.666) == 0)
						continue;

					// Vreme dolaska autobusa
					for (Sat s : l.satnice) {// System.out.println(s.getSati()+":"+s.getMinute());

						if ((vremePolaska.getSati() <= s.getSati() && vremePolaska.getMinute() <= s.getMinute())
								|| (vremePolaska.getSati() < s.getSati())) {
							potrebnoVremePolaskaLinije = s;
							break;
						}

					}

					if (kor.stanica.equals(najblizaStanicaP.stanica) && c == 0) {
						// ispise svo vreme
						stringovi.add("Aktivnost:Voznja");

						stringovi.add("Vreme dolaska autobusa na stanicu je "
								+ potrebnoVremePolaskaLinije.dodajMinute(potrebnoVremePolaskaLinije, (int) vremeUPutu));
						c = 3;
						ukupnoVreme += vremeuMin;

					}

					if (c==3)
						vremeUVoznji += (lok.Udaljenost(prethodna, kor) / 666.666);
					
					if (c == 3 && (kor.stanica.equals("null") == false)) {
						brojStanica++;
						stringovi.add("Spisak stanica  " + kor.stanica);

						

					}

					if (kor.stanica.equals(najblizaStanicaK.stanica)) {

						stringovi.add("Vreme izlaska iz autobusa  "
								+ potrebnoVremePolaskaLinije.dodajMinute(potrebnoVremePolaskaLinije, (int) vremeUVoznji)
								+ " na stanicu " + najblizaStanicaK.stanica);
						stringovi.add("Ukupan broj stanica  " + brojStanica);
						stringovi.add("Vreme trajanje voznje  " + vremeUVoznji);
						ukupnoVreme += vremeUVoznji;
						P = najblizaStanicaK;

						stringovi.add("Aktivnost:Setnja    Pocetna tacka: " + P.y + "," + P.x + " Krajnja tacka: " + K.y
								+ "," + K.x);
						vremeuMin = (lok.Udaljenost(P, K) / 66.666);
						stringovi.add("Vreme potrebno do krajnje destinacije" + " laganim hodom je "
								+ Math.round(vremeuMin) + " min.");
						stringovi.add("");
						ukupnoVreme += vremeuMin;

						PotecRuta ruta = new PotecRuta(stringovi, brojStanica, ukupnoVreme);
						sveRute.add(ruta);
						//if (ruta.brojStanica > 1)
							ruta.Ispis(ruta);

						break;

					}

					prethodna = kor;

				}
			}

			// --------------------------

		}

		// kraj maina
	}

}
