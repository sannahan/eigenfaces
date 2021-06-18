package ui;

import domain.Sovelluslogiikka;
import utils.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        //HBox tilastot = luoTilastot();
        
        asettelu.setLeft(kuvat);
        asettelu.setRight(haku);
        //asettelu.setBottom(tilastot);
        
        Insets marginaali = new Insets(10);
        BorderPane.setMargin(kuvat, marginaali);
        BorderPane.setMargin(haku, marginaali);
        //BorderPane.setMargin(tilastot, marginaali);
        
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
            hakuTulokset.getChildren().add(new ImageView(sovelluslogiikka.kirjoitaHakuKuvaksi(hloNro.getText(), kuvaNro.getText())));
            hakuTulokset.getChildren().add(new Label("Hakutulos"));
            hakuTulokset.getChildren().add(new ImageView(sovelluslogiikka.tunnistaHenkilo(hloNro.getText(), kuvaNro.getText())));  
        });
        hakuToiminnallisuus.getChildren().add(hakuTulokset);
        
        return hakuToiminnallisuus;
    }
    
    public HBox luoTilastot() {
        HBox tilastot = new HBox();
        tilastot.setSpacing(10);
        
        Label onnistumisprosenttiYhdenLuokanTapauksessa = new Label(String.valueOf(sovelluslogiikka.testaaOnnistumisprosenttiOpetusdatastaJossaYksiNeljanKuvanLuokkaPerHenkilo()));
        Label onnistumisprosenttiKahdenLuokanTapauksessa = new Label(String.valueOf(sovelluslogiikka.testaaOnnistumisprosenttiOpetusdatastaJossaKaksiNeljanKuvanLuokkaaPerHenkilo()));
        Label onnistumisprosenttiKolmenLuokanTapauksessa = new Label(String.valueOf(sovelluslogiikka.testaaOnnistumisprosenttiOpetusdatastaJossaKolmeKolmenKuvanLuokkaaPerHenkilo()));
        tilastot.getChildren().addAll(new Label("Yksi luokka"), onnistumisprosenttiYhdenLuokanTapauksessa, new Label("Kaksi luokkaa"), onnistumisprosenttiKahdenLuokanTapauksessa, new Label("Kolme luokkaa"), onnistumisprosenttiKolmenLuokanTapauksessa);
        
        return tilastot;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}