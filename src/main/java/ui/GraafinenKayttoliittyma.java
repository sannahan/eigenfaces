package ui;

import domain.Sovelluslogiikka;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * JavaFX-toteutus algoritmin toiminnan havainnollistamiseksi
 */
public class GraafinenKayttoliittyma extends Application {
    @Override
    public void start(Stage stage) {
        Sovelluslogiikka sl = new Sovelluslogiikka();
        
        BorderPane asettelu = new BorderPane();
        
        Image kohdeKuva = sl.kirjoitaKeskiarvokasvot();    
        ImageView kuva = new ImageView(kohdeKuva);
        Pane pane = new Pane();
        pane.getChildren().add(kuva);
        
        GridPane gridpane = new GridPane();
        int eigenface = 0;
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 5; y++) {
                gridpane.add(new ImageView(sl.kirjoitaEigenface(eigenface)), x, y);
                eigenface++;
            }
        }
        asettelu.setTop(pane);
        asettelu.setCenter(gridpane);
        stage.setScene(new Scene(asettelu));
        stage.show();   
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}  
