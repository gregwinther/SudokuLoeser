/** 
* Beholderklasse for en Sudokulosning
*
* @author Sebastian G. Winther-Larsen (sebastwi)
*
*/

import java.util.ArrayList;

class SudokuBeholder {

	private int antallLosninger;
	private ArrayList<Losning> losninger;
	private final int maksLosninger;

	public SudokuBeholder() {
		this.antallLosninger = 0;
		maksLosninger = 3500;
		losninger = new ArrayList<>(maksLosninger);
	}

	public Losning taUt(int plass) {
		return losninger.get(plass);
	}

	public int hentAntall() {
		return antallLosninger;
	}

	public void settInn(Losning brett) {
		antallLosninger++;
		if (losninger.size() < 3500) {
			losninger.add(brett);	
		}
	}

	public ArrayList<Losning> hentLosninger() {
		return losninger;
	}
}