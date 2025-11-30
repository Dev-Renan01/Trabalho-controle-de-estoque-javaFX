package com.minimundo.fx;



import com.minimundo.model.Produto;
import com.minimundo.database.Database;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CadastroView {

    private Stage stage;
    private ListaView listaView;

    public CadastroView(ListaView listaView) {
        this.listaView = listaView;
        stage = new Stage();
        stage.setTitle("Cadastro de Produto");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        Label lblName = new Label("Nome:");
        TextField tfName = new TextField();

        Label lblQty = new Label("Quantidade:");
        TextField tfQty = new TextField();

        Label lblPrice = new Label("Preço:");
        TextField tfPrice = new TextField();

        Button btnSave = new Button("Cadastrar");
        btnSave.setOnAction(e -> {
            try {
                String name = tfName.getText();
                int qty = Integer.parseInt(tfQty.getText());
                double price = Double.parseDouble(tfPrice.getText());

                if (name.isEmpty() || qty < 0 || price < 0) {
                    showAlert("Erro", "Preencha todos os campos corretamente!");
                    return;
                }

              Database.insertProduct(name, qty, price);
                listaView.loadProducts();
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Erro", "Erro ao salvar produto!");
            }
        });

        grid.add(lblName, 0, 0);
        grid.add(tfName, 1, 0);
        grid.add(lblQty, 0, 1);
        grid.add(tfQty, 1, 1);
        grid.add(lblPrice, 0, 2);
        grid.add(tfPrice, 1, 2);
        grid.add(btnSave, 1, 3);

        Scene scene = new Scene(grid, 300, 200);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}