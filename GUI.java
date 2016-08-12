/**
* GUI for sudokuloser
*
* @author Sebastian G. Winther-Larsen
*
*/

import javafx.application.Application;
import javafx.application.Platform;

import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;

import javafx.scene.paint.Color;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.stage.FileChooser;

import java.util.ArrayList;


public class GUI extends Application {

	protected SudokuEvents hendelse;
	protected Sudoku spillet;

	protected Stage vindu;
	protected Scene scene;
	protected Button filvelgerknapp, startknapp, nesteknapp, forrigeknapp;
	protected GridPane[] guiLosninger;
	protected BorderPane layout;

	protected Text filnavn, antall;

	protected int antallLosninger, denneLosning;

	// Constr
	public GUI() {
		hendelse = new SudokuEvents(this);
	}

	// Metode som overskriver 'start' i Application
	@Override
	public void start(Stage primaryStage) throws Exception{
		this.vindu = primaryStage;
		this.vindu.setTitle("Sudokuløseren");

		lagStartMeny();
	}

	public void losningFunnet() {
		Platform.runLater(new Runnable() {
			public void run() {
				lagSudokuMeny();
				vindu.setScene(scene);
				vindu.show();
			}	
		});
		
	}

	// Startmeny
	public void lagStartMeny() {

		// Knapp for aa velge fil.
		filvelgerknapp = new Button("Velg fil");
		filvelgerknapp.setOnAction(hendelse);

		// Knapp for aa starte etter at fil er valgt.
		startknapp = new Button("LØS!");
		startknapp.setDisable(true);
		startknapp.setOnAction(hendelse);

		// Tekst som viser hvilken fil som er valgt
		filnavn = new Text("Ingen fil valgt.");

		// Knapper og filnavn i en enkel horisontal rad.
		HBox knapper = new HBox(10);
		knapper.getChildren().addAll(filvelgerknapp, startknapp, filnavn);


		// Tegning, layout etc
		BorderPane layout = new BorderPane();
		Pane topBlank = new Pane();
		Pane leftBlank = new Pane();
		topBlank.setPrefSize(300,30);
		leftBlank.setPrefSize(20,70);
		layout.setTop(topBlank);
		layout.setLeft(leftBlank);
		layout.setCenter(knapper);

		// Scene
		vindu.setScene(new Scene(layout));
		vindu.setMinWidth(600);
		vindu.setMinHeight(100);

		vindu.show();
	}

	// Mulighet for aa bla igjennom losningene
	public void lagSudokuMeny() {

		// Henter ut info
		antallLosninger = spillet.hentBeholder().hentAntall();
		ArrayList<Losning> losninger = spillet.hentBeholder().hentLosninger();
		this.guiLosninger = new GridPane[antallLosninger];

		int teller = 0;

		for (Losning losning: losninger) {

			int sisteRad = 1;
			int sisteKol = 1;

			GridPane guiLosning = new GridPane();

			this.guiLosninger[teller++] = guiLosning;

			guiLosning.setAlignment(Pos.CENTER);
			guiLosning.setPadding(new Insets(10,10,10,10));
			guiLosning.setVgap(4);
			guiLosning.setHgap(4);

			Rute[][] ruter = losning.hentRuter();

			for (int i = 0; i < ruter.length; i++) {
				
				Rute[] sub = ruter[i];

				for (int j = 0; j < sub.length; j++) {
					String verdi = Character.toUpperCase(
						Character.forDigit(sub[j].hentVerdi(), 36)) + "";
					Label tekst = new Label(verdi);

					tekst.setFont(Font.font(26));
					tekst.setMinWidth(26);
					tekst.setAlignment(Pos.CENTER);
					if (sub[j] instanceof TomRute) {
						tekst.setTextFill(Color.DIMGRAY);
					}

					// Fyller inn rute og modifierer
					final HBox ruteBoks = new HBox();
					ruteBoks.getChildren().add(tekst);
					ruteBoks.setAlignment(Pos.CENTER);

					guiLosning.setMargin(ruteBoks, new Insets(5,5,5,5));
					guiLosning.setConstraints(ruteBoks, (j+1), (i+1));

					guiLosning.getChildren().add(ruteBoks);
				}
			}
			int dimensjon = spillet.hentBrett().hentDimensjon();
			int nyTeller = 0;
			int oppfylt = 0;

			// Markerer bokser
			for (int i = 0; i < dimensjon; i++) {

				if (oppfylt == dimensjon) {
					sisteRad = sisteRad + spillet.hentBrett().hentRaderBoks();
					sisteKol = 1;
					nyTeller = 0;
					oppfylt = 0;
				}

				HBox boks = new HBox();
				boks.setBorder(new Border(new BorderStroke(Color.DARKBLUE,
												BorderStrokeStyle.SOLID,
												CornerRadii.EMPTY,
												new BorderWidths(2))));
				guiLosning.add(boks, sisteKol, sisteRad,
					spillet.hentBrett().hentKolonnerBoks(),
					spillet.hentBrett().hentRaderBoks());

				oppfylt = oppfylt + spillet.hentBrett().hentKolonnerBoks();
				sisteKol = sisteKol + spillet.hentBrett().hentKolonnerBoks();

				nyTeller++;

			}
		}

		// Mer Layout og knapper og slikt.

		// Nesteknapp
		nesteknapp = new Button("Neste");
		nesteknapp.setMinSize(50,30);
		nesteknapp.setOnAction(hendelse);

		// Forrigeknapp
		forrigeknapp = new Button("Forrige");
		forrigeknapp.setMinSize(50,30);
		forrigeknapp.setOnAction(hendelse);

		// Antall losninger label
		antall = new Text();
        antall.setText("Løsning nr " + (denneLosning + 1) 
        	+ " av " + antallLosninger);
        forrigeknapp.setDisable(true);
        if (!(antallLosninger > 1))
            nesteknapp.setDisable(true);

        // Knapper og info
        HBox info = new HBox(50);
        info.setPadding(new Insets(10,0,0,0));
        info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(forrigeknapp, antall, nesteknapp);

        // Mer layout
        layout = new BorderPane();
        layout.setCenter(info);
        layout.setBottom(this.guiLosninger[0]);

        // Oppdaterer scene
        scene = new Scene(layout);

	}

	public void nesteLosning() {
        Platform.runLater(new Runnable() {
            public void run() {
                layout.setBottom(guiLosninger[denneLosning + 1]);
                denneLosning++;
                antall.setText("Løsning nr " + (denneLosning + 1) 
                	+ " av " + antallLosninger);

                if (denneLosning > 0)
                    forrigeknapp.setDisable(false);
                if ((denneLosning + 1) == antallLosninger)
                    nesteknapp.setDisable(true);

                vindu.show();
            }
        });
    }

    public void forrigeLosning() {
        Platform.runLater(new Runnable() {
            public void run() {
                layout.setBottom(guiLosninger[denneLosning + -1]);
                denneLosning--;
                antall.setText("Løsning nr " + (denneLosning + 1) 
                	+ " av " + antallLosninger);

                if (denneLosning == 0) {
                    forrigeknapp.setDisable(true);
                    nesteknapp.setDisable(false);
                }

                vindu.show();
            }
        });
    }


}