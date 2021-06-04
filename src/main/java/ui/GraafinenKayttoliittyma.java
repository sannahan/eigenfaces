package ui;

import domain.Sovelluslogiikka;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX-toteutus algoritmin toiminnan havainnollistamiseksi
 */
public class GraafinenKayttoliittyma extends Application {
    private Sovelluslogiikka sovelluslogiikka;
    
    @Override
    public void start(Stage stage) {
        sovelluslogiikka = new Sovelluslogiikka();
        
        BorderPane asettelu = new BorderPane();
        
        VBox kuvat = luoHavainnollistavatKuvat();
        VBox haku = luoHakutoiminnallisuus();
        
        asettelu.setLeft(kuvat);
        asettelu.setRight(haku);
        Insets marginaali = new Insets(10);
        BorderPane.setMargin(kuvat, marginaali);
        BorderPane.setMargin(haku, marginaali);
        
        stage.setScene(new Scene(asettelu));
        stage.show();   
    }
    
    private VBox luoHavainnollistavatKuvat() {
        VBox kuvat = new VBox();
  
        ImageView keskiarvoKasvot = new ImageView(sovelluslogiikka.kirjoitaKeskiarvokasvot());
        Pane pane = new Pane();
        pane.getChildren().add(keskiarvoKasvot);
        
        GridPane gridpane = new GridPane();
        int eigenface = 0;
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 5; y++) {
                gridpane.add(new ImageView(sovelluslogiikka.kirjoitaEigenface(eigenface)), x, y);
                eigenface++;
            }
        }
        
        kuvat.setSpacing(10);
        kuvat.getChildren().addAll(new Label("Keskiarvokasvot"), pane, new Label("Eigenfaces"), gridpane);
        return kuvat;
    }
    
    private VBox luoHakutoiminnallisuus() {
        Label ohjeet = new Label("Testaa valitsemalla hlo 20-29 ja kuva 5-10");
        TextField hloNro = new TextField();
        TextField kuvaNro = new TextField();
        Button nappi = new Button("Etsi");
        
        VBox hakuToiminnallisuus = new VBox();
        hakuToiminnallisuus.setSpacing(10);
        hakuToiminnallisuus.getChildren().addAll(ohjeet, hloNro, kuvaNro, nappi);
        
        VBox hakuTulokset = new VBox();
        hakuTulokset.setSpacing(10);
        nappi.setOnAction((event) -> {
            hakuTulokset.getChildren().clear();
            hakuTulokset.getChildren().add(new Label("Hakemasi kuva"));
            hakuTulokset.getChildren().add(new ImageView(sovelluslogiikka.kirjoitaHakutulos(hloNro.getText(), kuvaNro.getText())));
            hakuTulokset.getChildren().add(new Label("Hakutulos"));
            hakuTulokset.getChildren().add(new ImageView(sovelluslogiikka.haeHenkilo(hloNro.getText(), kuvaNro.getText())));  
        });
        hakuToiminnallisuus.getChildren().add(hakuTulokset);
        
        return hakuToiminnallisuus;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}