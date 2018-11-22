package Lokator;
import java.util.ArrayList;
import java.util.List;

public class Linija {

String ime;
String opis;
List<Kordinate> kordinate;
List<Sat> satnice=new ArrayList<>();;
List<Stanica> stanice=new ArrayList<>(); 


public Linija(String ime) {
	this.ime=ime;
	
}
 
public Linija(String ime, String opis,List<Kordinate> kord,List<Sat> satnice,List<Stanica> stanice ) {
	this.ime=ime;
	this.opis=opis;
	kordinate=new ArrayList<>(kord);
	this.satnice=satnice;
	this.stanice=stanice;
	

	
}


}
