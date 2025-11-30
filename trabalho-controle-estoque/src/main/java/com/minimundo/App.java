package com.minimundo;

import com.minimundo.fx.ListaView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        ListaView lista = new ListaView();
        Scene scene = new Scene(lista.getRoot(), 800, 500);
        stage.setTitle("Sistema de Controle de Estoque - Minimundo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}