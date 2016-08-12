/**
* klasse Brett
*
* Fungerer som en beholder for Rute-objekter i tillegg til
* aa holde styr paa hvilke egenskaper brettet har.
* Egenskaper er: 
* rader i en boks (raderBoks), 
* kolonner i en boks (kolonnerBoks),
* ruter i kolonne, boks, rad (dimensjon=raderBoks*kolonnerBoks)
* antall ruter til sammen (ruterTotalt=dimensjon*dimensjon)
* 
* @author Sebastian G. Winther-Larsen
*
*/

class Brett {

	private int raderBoks;
	private int kolonnerBoks;
	private int dimensjon;
	private int ruterTotalt;

	// Matrise (eller 2D-array) av ruter
	private Rute[][] ruter;

	// Hovedspillet
	private Sudoku spillet;

	// Constructor	
	Brett (int raderBoks, int kolonnerBoks, Rute[][] ruter, Sudoku spillet) {

		this.raderBoks = raderBoks;
		this.kolonnerBoks = kolonnerBoks;

		dimensjon = raderBoks * kolonnerBoks;
		// Naa skal dimensjon == ruter.length()

		ruterTotalt = dimensjon * dimensjon;

		this.ruter = ruter;

		this.spillet = spillet;
	}

	public void skrivBrett() {
		System.out.println(raderBoks);
		System.out.println(kolonnerBoks);

		for (int i = 0; i < (dimensjon); i++) {
			for (int j = 0; j < (dimensjon); j++) {
				System.out.print(ruter[i][j].hentVerdi() + " ");
			}
			System.out.println();
		}
	}

	public void opprettDatastruktur() {

		// Legger til rader
		int i = 0;
		int j = 0;
		while (i < dimensjon) {
			Rad rad = new Rad(i+1);
			while(j < dimensjon) {
				rad.settInnRute(ruter[i][j]);
				ruter[i][j].settRad(rad);
				j++;
			}
			j = 0;
			i++;
		}
		
		// Legger til kolonner
		i = 0;
		j = 0;
		while (j < dimensjon) {
			Kolonne kolonne = new Kolonne(j+1);
			while(i < dimensjon) {
				kolonne.settInnRute(ruter[i][j]);
				ruter[i][j].settKolonne(kolonne);
				i++;
			}
			i = 0;
			j++;
		}

		// Legger til bokser
		i = 0;
		j = 0;
		int k = 0;
		int l = 0;
		while (k < dimensjon) { // rad av bokser
			while (l < dimensjon) { // kolonne av bokser
				Boks boks = new Boks();
				// Her kjøres det gjennom en hel boks
				while (i < raderBoks) { // rad i boksen
					while (j < kolonnerBoks) {
							boks.settInnRute(ruter[i+k][j+l]);
							ruter[i+k][j+l].settBoks(boks);
						j++;
					}
					j = 0;
					i++;
				}
				i = 0;
				l = l + kolonnerBoks;
			}
			l = 0;
			k = k + raderBoks;
		}

		// Nestepekere
		i = 0;
		j = 0;
		while (i < dimensjon) { // Rad
			while (j < dimensjon) { // kolonne
				// Siste kolonne, ikke siste rad
				if ((i == (dimensjon-1)) && (j == (dimensjon-1))) { // Aller siste rute
					ruter[i][j].neste = null; // Siste rute har ingen neste.
				} else if (j == (dimensjon-1)) { // Siste kolonne
					ruter[i][j].neste = ruter[i+1][0];  
				} else {
					ruter[i][j].neste = ruter[i][j+1]; // ArrayListter neste til neste :)
				}
			j++;
			}
		j = 0;
		i++;
		}

		// Pekere fra ruter til dette brettet
		i = 0;
		j = 0;
		while (i < dimensjon) { // Rad
			while (j < dimensjon) { // Kolonne
				ruter[i][j].settBrett(this);
				j++;
			}
			j = 0;
			i++;
		}
	}

	// Denne er for saa vidt overfloedig
	public void finnLosning() {
		// Finner foerst foerste tomme rute, om det er noen.
		int i = 0;
		int j = 0;
		//System.out.print("Ho!");
		while (i < dimensjon) {
			while (j < dimensjon) {
				if (ruter[i][j].hentVerdi() == 0){
					ruter[i][j].fyllUtDenneRuteOgResten();
				}
				j++;
			}
			i++;
		}
		//this.finnLosning();		
	}


	public int hentDimensjon() {
		return dimensjon;
	}

	public int hentRaderBoks() {
		return raderBoks;
	}

	public int hentKolonnerBoks() {
		return kolonnerBoks;
	}

	public int hentRuterTotalt() {
		return ruterTotalt;
	}

	public Rute[][] hentRuter() {
		return ruter;
	}

	public void settRuter(Rute[][] ruter) {
		this.ruter = ruter;
	}

	public Sudoku hentSpill() {
		return spillet;
	}


}