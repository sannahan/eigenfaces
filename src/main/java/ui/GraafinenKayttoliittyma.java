package ui;

import domain.Sovelluslogiikka;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
        VBox tilastot = luoTilastot();
        
        asettelu.setLeft(kuvat);
        asettelu.setCenter(haku);
        asettelu.setRight(tilastot);
        
        Insets marginaali = new Insets(20);
        BorderPane.setMargin(kuvat, marginaali);
        BorderPane.setMargin(haku, marginaali);
        BorderPane.setMargin(tilastot, marginaali);
        
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
    
    private VBox luoTilastot() {
        VBox tilastot = new VBox();
        tilastot.setSpacing(10);
        
        VBox napit = new VBox();
        napit.setSpacing(10);
        Button nappiOnnistumisprosenteille = new Button("Onnistumisprosentit (n. 10 s)");
        Button nappiSuorituskykytesteille = new Button("Suorituskykytestit (n. 30 min)");
        napit.getChildren().addAll(nappiOnnistumisprosenteille, nappiSuorituskykytesteille);
        
        VBox kaavioAlue = new VBox();
        kaavioAlue.maxWidth(30);
        kaavioAlue.maxHeight(100);
        
        nappiOnnistumisprosenteille.setOnAction((event) -> {
            kaavioAlue.getChildren().clear();
            String onnistumisprosentit = sovelluslogiikka.testaaOnnistumisprosentit();
            String[] onnistumisprosentitArray = onnistumisprosentit.split(";");
            for (int i = 0; i < 3; i++) {
               PieChart piirakkakaavio = luoPiirakkakaavio(onnistumisprosentitArray[i+3]);
               piirakkakaavio.setTitle(onnistumisprosentitArray[i]);
               kaavioAlue.getChildren().add(piirakkakaavio);
            }
        });
        
        nappiSuorituskykytesteille.setOnAction((event) -> {
            kaavioAlue.getChildren().clear();
            String tulokset = sovelluslogiikka.testaaTehokkuus();
            String[] tuloksetArray = tulokset.split(",");
            String[] koot = {tuloksetArray[0], tuloksetArray[1], tuloksetArray[2]};
            String[] init = {tuloksetArray[3], tuloksetArray[4], tuloksetArray[5]};
            String[] tunnistus = {tuloksetArray[6], tuloksetArray[7], tuloksetArray[8]};
            BarChart initTilastot = luoPylvaskaavio(koot, init);
            initTilastot.setTitle("Eigenface-luokan alustaminen");
            BarChart tunnistusTilastot = luoPylvaskaavio(koot, tunnistus);
            tunnistusTilastot.setTitle("Kuvan tunnistaminen");
            kaavioAlue.getChildren().addAll(initTilastot, tunnistusTilastot);
        });
        
        tilastot.getChildren().addAll(new Label("Suorita haluamasi testaustoiminnallisuus"), napit, kaavioAlue);
        
        return tilastot;
    }
    
    private PieChart luoPiirakkakaavio(String maara) {
        PieChart piirakkakaavio = new PieChart();
        piirakkakaavio.setLegendVisible(false);
        
        PieChart.Data pala1 = new PieChart.Data("Tunnistettu", Double.valueOf(maara));
        PieChart.Data pala2 = new PieChart.Data("Ei tunnistettu", 100 - Double.valueOf(maara));

        piirakkakaavio.getData().add(pala1);
        piirakkakaavio.getData().add(pala2);
        
        return piirakkakaavio;
    }
    
    private BarChart luoPylvaskaavio(String[] luokat, String[] maarat) {
        CategoryAxis xAkseli = new CategoryAxis();
        xAkseli.setLabel("Kuvien määrä");
        NumberAxis yAkseli = new NumberAxis();
        yAkseli.setLabel("Kulunut aika (ms)");
        BarChart<String, Number> pylvaskaavio = new BarChart<>(xAkseli, yAkseli);
        pylvaskaavio.setLegendVisible(false);
        
        XYChart.Series tilastot = new XYChart.Series();
        for (int i = 0; i < luokat.length; i++) {
            tilastot.getData().add(new XYChart.Data(luokat[i], Double.valueOf(maarat[i])));
        }
        
        pylvaskaavio.getData().add(tilastot);
        return pylvaskaavio;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}