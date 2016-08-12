/**
* klasse Rute
*
* Maa inneholde verdi.
* Men maa ogsaa ha pekere til objekter av klassen:
* Boks, Rad og Kolonne.
*
* @author Sebastian G. Winther-Larsen
*
*/

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

class Rute {
	
	private int verdi;

	//private Brett brett;
	private Rad rad;
	private Kolonne kolonne;
	private Boks boks;
	protected Brett brett;
	protected Sudoku spillet;
	protected Rute neste; // Link til neste rute i brettet.

	// Constructor
	Rute(int verdi, Sudoku spillet) {
		this.verdi = verdi;
		this.spillet = spillet;
	}

	// Finner mulige tall
	public int[] finnAlleMuligeTall() {

		int dimensjon;
		dimensjon = rad.hentAntall();
		Set<Integer> lovligeTall = new HashSet<Integer>();

		for (int i = 1; i <= dimensjon; i++) {
			if (!rad.harVerdi(i) && !kolonne.harVerdi(i) && !boks.harVerdi(i)) {
				lovligeTall.add(i);
			}
		}

		int antallLovlige = lovligeTall.size();
		int[] lovligeTall2 = new int[antallLovlige];
		int i = 0;

		// Konverterer til primitiv array
		for (int tall: lovligeTall) {
			lovligeTall2[i++] = tall;
		}

		return lovligeTall2;
	}

	public void angre(){
		if (this instanceof TomRute) {
			this.settVerdi(0);
		}

		if (this.neste != null) {
			this.neste.angre();
		}	
	}

	// Metode for å putte inn losninger
	public void fyllUtDenneRuteOgResten() {
		// Nuller ut det som er skrevet ved omstart.
		this.angre();
	}

	public void settRad(Rad r) {rad = r;}
	public Rad hentRad() {return rad;}
	
	public void settKolonne(Kolonne k) {kolonne = k;}
	public Kolonne hentKolonne() {return kolonne;}
	
	public void settBoks(Boks b) {boks = b;}
	public Boks hentBoks() {return boks;}

	public void settVerdi(int v) {verdi = v;}
	public int hentVerdi() {return verdi;}

	public Rute hentNeste() {return neste;}

	public void settBrett(Brett b) {brett = b;}
	public Brett hentBrett() {return brett;}
}

class FyltRute extends Rute {

	public FyltRute(int verdi, Sudoku spillet) {
		super(verdi, spillet);
	}

	public void fyllUtDenneRuteOgResten() {
		super.fyllUtDenneRuteOgResten();

		// Hopper over
		if (this.neste != null) {
			this.neste.fyllUtDenneRuteOgResten();
		}

		// Siste rute ?
		if (this.neste == null) {
			this.spillet.lagreBrett(this.hentBrett());	
			return;
		}
	}
}

class TomRute extends Rute {

	public TomRute(int verdi, Sudoku spillet) {
		super(verdi, spillet);
	}

	public void fyllUtDenneRuteOgResten() {
		super.fyllUtDenneRuteOgResten();

		// Finn mulige verdier
		int[] mulige = this.finnAlleMuligeTall();
		
		for (int verdi: this.finnAlleMuligeTall()) {
			this.settVerdi(verdi);

			if (this.neste != null) {
				this.neste.fyllUtDenneRuteOgResten();
			}
			
		}

		// Siste?
		if (this.neste == null) {
			if (!(this.hentVerdi() == 0)) {
				this.spillet.lagreBrett(this.hentBrett());
				return;
			}
		}
	}
}








