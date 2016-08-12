/**
* Event Handlers for det som skjer i Sudoku GUI
*
* Trenger mulighet for aa haandtere:
* 1. Trykk paa filvelgerknapp,
* 2. Trykk paa startknapp,
* 3. Trykk paa knapp for aa vise neste
* 4. Trykk paa knapp for aa vise forrige
*
* @author Sebastian G. Winther-Larsen (sebastwi)
*
*/

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.stage.FileChooser;

import java.io.File;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;

public class SudokuEvents implements EventHandler<ActionEvent> {

	protected GUI gui;
	protected Sudoku spillet;

	protected String filnavn = null;



	public SudokuEvents(GUI gui) {
		this.gui = gui;
		this.spillet = gui.spillet;
	}

	public void handle(ActionEvent knappetrykk) {

		if (knappetrykk.getSource() == gui.startknapp) {

			Task<Sudoku> loseSudoku = new Task<Sudoku>() {
				@Override protected Sudoku call() {
				spillet = new Sudoku();
				spillet.lesFil(filnavn);
				Brett brett = spillet.hentBrett();
				brett.opprettDatastruktur();
				brett.hentRuter()[0][0].fyllUtDenneRuteOgResten();
				return spillet;
			}
		};

		loseSudoku.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
    	
    		public void handle(WorkerStateEvent evt) {
        	gui.spillet = loseSudoku.getValue();
        	gui.losningFunnet();

    		}
		});

		new Thread(loseSudoku).start();


		} else if (knappetrykk.getSource() == gui.filvelgerknapp) {
			
			// Filvelger
			FileChooser filvelger = new FileChooser();
			FileChooser.ExtensionFilter filtyper 
			= new FileChooser.ExtensionFilter("Txt fil (*.txt)","*.txt");
			filvelger.getExtensionFilters().add(filtyper);
			filvelger.setTitle("Åpne");
			
			File fil = filvelger.showOpenDialog(gui.vindu);

			// Fremvisning av filsti og aktivering av startknapp
			if (fil != null) {
				String filsti = fil.getAbsolutePath();
				this.filnavn = filsti;
				gui.startknapp.setDisable(false);
				gui.filnavn.setText(filsti);
			}

		} else if (knappetrykk.getSource() == gui.nesteknapp) {
			gui.nesteLosning();
		} else if (knappetrykk.getSource() == gui.forrigeknapp) {
			gui.forrigeLosning();
		}
	}
	
	

}