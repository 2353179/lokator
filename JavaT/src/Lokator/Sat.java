package Lokator;

public class Sat {

	int sati;
	int minute;

	public Sat(String s) {

		String[] strings = s.split(":");
		sati = Integer.valueOf(strings[0]);
		minute = Integer.valueOf(strings[1]);

	}

	public String dodajMinute(Sat vreme, int dodaj) {
		if (vreme.minute + dodaj >= 60) {
			int broj = (vreme.minute + dodaj) / 60;

			vreme.sati += broj;
			int min = (dodaj - (broj * 60));
			vreme.minute += min;
			// System.out.println((dodaj-(broj*60)));

			if (vreme.minute == 60) {
				vreme.sati++;
				vreme.minute = 0;
			}

		} else {
			vreme.minute += dodaj;
			if (vreme.minute == 60) {
				vreme.sati++;
				vreme.minute = 0;
			}
		}
		return vreme.sati + ":" + vreme.minute;

	}

	public int getSati() {
		return sati;
	}

	public void setSati(int sati) {
		this.sati = sati;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}


}
