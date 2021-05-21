package ui;

import dao.Kuvanlukija;
import domain.Sovelluslogiikka;
import java.io.DataInputStream;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 * JavaFX-toteutus algoritmin toiminnan havainnollistamiseksi
 */
public class GraafinenKayttoliittyma extends Application {
    @Override
    public void start(Stage stage) {
        Sovelluslogiikka sl = new Sovelluslogiikka();
        Image kohdeKuva = sl.kirjoitaKeskiarvokasvot();
            
        ImageView kuva = new ImageView(kohdeKuva);

        Pane pane = new Pane();
        pane.getChildren().add(kuva);

        stage.setScene(new Scene(pane));
        stage.show();   
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}  
