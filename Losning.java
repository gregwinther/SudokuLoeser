/**
* Klasse for en Losning
*
* @author Sebastian G. Winther-Larsen
*
*/

import java.util.Arrays;

class Losning {

	private Rute[][] lostBrett;

	public Losning(Rute[][] brett) {

		int dimensjon = brett.length;
		Rute[][] losning = new Rute[dimensjon][dimensjon];

		for (int i = 0; i < dimensjon; i++) { // Rad
			for (int j = 0; j < dimensjon; j++) {
				Rute rute = brett[i][j];
				Rute lostRute;
				if (brett[i][j] instanceof TomRute) {
					lostRute = new TomRute(rute.hentVerdi(), null);
				} else {
					lostRute = new FyltRute(rute.hentVerdi(), null);
				}
				losning[i][j] = lostRute;
			}
		}
		this.lostBrett = losning;
	}

	public void skrivUt() {
		int dimensjon = lostBrett.length;

		for (int i = 0; i < (dimensjon); i++) {
			for (int j = 0; j < (dimensjon); j++) {
				System.out.print(lostBrett[i][j].hentVerdi() + " ");
			}
			System.out.println();
		}
		System.out.println();

	}

	public Rute[][] hentRuter() {
		return lostBrett;
	}
}