/** Superklasse Enhet, med subklasser Boks, Kolonne og Rad.
*
* @author Sebastian G. Winther-Larsen
*
*/

import java.util.ArrayList;

class Enhet {

	private int nr;
	private ArrayList<Rute> ruter = new ArrayList<Rute>();

	Enhet() {}

	Enhet(int id) {
		nr = id;
	}

	public void settInnRute(Rute r) {
		ruter.add(r);
	}

	public ArrayList<Rute> hentRuter() {
		return ruter;
	}

	public boolean harVerdi(int verdi) {
		for (Rute r: this.hentRuter()) {
			if (r.hentVerdi() == verdi) {
				return true;
			}
		}	
		return false;
	}

	public int hentAntall() {
		return ruter.size();
	}

	public int hentNummer() {return nr;}
}


// Subklasse Boks
class Boks extends Enhet{
	
	int raderBoks;
	int kolonnerBoks;

	Boks() {}

	Boks(int id, int raderBoks, int kolonnerBoks) {
		super(id);
		this.raderBoks = raderBoks;
		this.kolonnerBoks = kolonnerBoks;
	}

}

// Subklasse Kolonne
class Kolonne extends Enhet {

	Kolonne(int id) {
		super(id);
	}

}


// Subklasse Rad
class Rad extends Enhet {

	Rad(int id) {
		super(id);
	}

}