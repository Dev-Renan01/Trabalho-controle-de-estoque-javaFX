package com.minimundo.fx;


import com.minimundo.database.Database;
import com.minimundo.model.Produto;
import com.minimundo.swing.SearchDialog;
import com.minimundo.swing.UpdateDialog;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.List;

import java.awt.*;

public class ListaView {

    private BorderPane root;
    private TableView<Produto> table;
    private ObservableList<Produto> products;

    public ListaView() {
        root = new BorderPane();
        table = new TableView<>();
        products = FXCollections.observableArrayList();

        setupTable();
        setupControls();
        loadProducts();
    }

    private void setupTable() {
        TableColumn<Produto, String> nameCol = new TableColumn<>("Nome");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        nameCol.setMinWidth(200);

        TableColumn<Produto, Integer> qtyCol = new TableColumn<>("Quantidade");
        qtyCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        qtyCol.setMinWidth(100);

        TableColumn<Produto, Double> priceCol = new TableColumn<>("Preço");
        priceCol.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        priceCol.setMinWidth(100);

        table.getColumns().addAll(nameCol, qtyCol, priceCol);
        root.setCenter(table);
    }

    private void setupControls() {
        Button btnAdd = new Button("Cadastrar");
        btnAdd.setOnAction(e -> {
            CadastroView cadastro = new CadastroView(this);
            cadastro.show();
        });

        Button btnEdit = new Button("Editar");
        btnEdit.setOnAction(e -> {
            Produto selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                UpdateDialog dialog = new UpdateDialog(selected, this);
                dialog.setVisible(true);
            }
        });

        Button btnDelete = new Button("Excluir");
        btnDelete.setOnAction(e -> {
            Produto selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    Database.deleteProduct(selected.getId());
                    loadProducts();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button btnSearch = new Button("Buscar");
        btnSearch.setOnAction(e -> {
            SearchDialog dialog = new SearchDialog(this);
            dialog.setVisible(true);
        });

        HBox controls = new HBox(10, btnAdd, btnEdit, btnDelete, btnSearch);
        controls.setPadding(new Insets(10));
        root.setTop(controls);
    }

    public void loadProducts() {
        try {
            List<Produto> list = Database.getAllProducts();
            products.setAll(list);
            table.setItems(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BorderPane getRoot() {
        return root;
    }

    public void loadProductsByList(List<Produto> list) {
        products.setAll(list);
        table.setItems(products);
    }
}