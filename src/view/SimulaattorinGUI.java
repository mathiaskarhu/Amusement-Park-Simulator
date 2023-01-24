package view;


import java.text.DecimalFormat;
import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.TietokannanEsittely;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * Simulaattorin graafinen käyttöliittymä
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public class SimulaattorinGUI extends Application implements ISimulaattorinUI{

	// Kontrollerin esittely (tarvitaan käyttöliittymässä)
	private IKontrolleri kontrolleri;

	// Käyttöliittymäkomponentit:
	private TextField aika;
	private TextField viive;
	
	private TextField textfield1;
	private TextField textfield2;
	
	private Label tulos;
	private Label aikaLabel;
	private Label viiveLabel;
	private Label tulosLabel;
	
	private Label textfield1Label;
	private Label textfield2Label;
	
	private Button kaynnistaButton;
	private Button hidastaButton;
	private Button nopeutaButton;
	private Button esittelyButton;
	

	private IVisualisointi naytto;
	
	TietokannanEsittely tietokanta = new TietokannanEsittely();
	
	
	@Override
	public void init(){
		
		Trace.setTraceLevel(Level.INFO);
		
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public void start(Stage primaryStage) {
		// Käyttöliittymän rakentaminen
		try {
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});
						
			
			primaryStage.setTitle("Simulaattori");

			kaynnistaButton = new Button();
			kaynnistaButton.setText("Käynnistä simulointi");;
			kaynnistaButton.setPrefWidth(150);
			kaynnistaButton.setPrefHeight(40);
			kaynnistaButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                kontrolleri.kaynnistaSimulointi();
	                kaynnistaButton.setDisable(true);
	            }
	        });

			hidastaButton = new Button();
			hidastaButton.setText("Hidasta");
			hidastaButton.setOnAction(e -> kontrolleri.hidasta());

			nopeutaButton = new Button();
			nopeutaButton.setText("Nopeuta");
			nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());

			aikaLabel = new Label("Simulointiaika:");
			aikaLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
	        aika = new TextField("Syötä aika");
	        aika.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
	        aika.setPrefWidth(100);

	        viiveLabel = new Label("Viive:");
			viiveLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
	        viive = new TextField("Syötä viive");
	        viive.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
	        viive.setPrefWidth(100);
	        
	        textfield1Label = new Label("Jakauman keskiarvo: ");
	        textfield1Label.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
			textfield1 = new TextField("Syötä arvo");
			textfield1.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
			textfield1.setPrefWidth(100);
	        
			textfield2Label = new Label("Jakauman varianssi: (>0)");
			textfield2Label.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
			textfield2 = new TextField("Syötä arvo");
			textfield2.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
			textfield2.setPrefWidth(100);
	                	        
	        tulosLabel = new Label("Kokonaisaika:");
			tulosLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
	        tulos = new Label();
	        tulos.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
	        tulos.setPrefWidth(100);
	        
	        esittelyButton = new Button();
			esittelyButton.setText("Tietokanta");
			esittelyButton.setOnAction(e -> tietokanta.esittely());

	        HBox hBox = new HBox();
	        hBox.setPadding(new Insets(15, 15, 15, 15)); // marginaalit ylä, oikea, ala, vasen
	        hBox.setSpacing(10);   // noodien välimatka 10 pikseliä
	        
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setVgap(10);
	        grid.setHgap(5);

	        grid.add(aikaLabel, 0, 0);   // sarake, rivi
	        grid.add(aika, 1, 0);          // sarake, rivi
	        grid.add(viiveLabel, 0, 1);      // sarake, rivi
	        grid.add(viive, 1, 1);           // sarake, rivi
	        
	        grid.add(textfield1Label, 0, 3);
	        grid.add(textfield1, 1, 3);
	        grid.add(textfield2Label, 0, 4);
	        grid.add(textfield2, 1, 4);
	        
	        grid.add(tulosLabel, 0, 2);      // sarake, rivi
	        grid.add(tulos, 1, 2);           // sarake, rivi
	        grid.add(kaynnistaButton,0, 5);  // sarake, rivi
	        grid.add(nopeutaButton, 0, 6);   // sarake, rivi
	        grid.add(hidastaButton, 1, 6);   // sarake, rivi
	        
	        grid.add(esittelyButton, 0, 9);
	        
	        naytto = new Visualisointi(800,600);
	       
	        // Täytetään boxi:
	        hBox.getChildren().addAll(grid, (Pane)naytto);
	        
	        Scene scene = new Scene(hBox);
	        primaryStage.setScene(scene);
	        primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	//Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

	@Override
	public double getAika(){
		return Double.parseDouble(aika.getText());
	}

	@Override
	public long getViive(){
		return Long.parseLong(viive.getText());
	}
	
	@Override
	public int getI() {
		return Integer.parseInt(textfield1.getText());
	}
	
	@Override
	public int getJ() {
		return Integer.parseInt(textfield2.getText());
	}

	@Override
	public void setLoppuaika(double aika){
		 DecimalFormat formatter = new DecimalFormat("#0.00");
		 this.tulos.setText(formatter.format(aika));
	}


	@Override
	public IVisualisointi getVisualisointi() {
		 return naytto;
	}
	
	
	// JavaFX-sovelluksen (käyttöliittymän) käynnistäminen

	public static void main(String[] args) {
		launch(args);
	}

	
}