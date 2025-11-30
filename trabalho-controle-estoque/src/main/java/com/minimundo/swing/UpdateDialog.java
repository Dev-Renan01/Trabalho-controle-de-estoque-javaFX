package com.minimundo.swing;

import com.minimundo.database.Database;
import com.minimundo.model.Produto;
import com.minimundo.fx.ListaView;

import javax.swing.*;
import java.awt.*;

public class UpdateDialog extends JDialog {

    public UpdateDialog(Produto product, ListaView listaView) {
        setTitle("Editar Produto");
        setModal(true);
        setSize(300, 200);
        setLayout(new GridLayout(4,2,5,5));
        setLocationRelativeTo(null);

        JLabel lblName = new JLabel("Nome:");
        JTextField tfName = new JTextField(product.getName());

        JLabel lblQty = new JLabel("Quantidade:");
        JTextField tfQty = new JTextField(String.valueOf(product.getQuantity()));

        JLabel lblPrice = new JLabel("Preço:");
        JTextField tfPrice = new JTextField(String.valueOf(product.getPrice()));

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(e -> {
            try {
                String name = tfName.getText();
                int qty = Integer.parseInt(tfQty.getText());
                double price = Double.parseDouble(tfPrice.getText());

                if(name.isEmpty() || qty < 0 || price < 0) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!");
                    return;
                }

                Database.updateProduct(product.getId(), name, qty, price);
                listaView.loadProducts();
                dispose();
            } catch(Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produto!");
            }
        });

        add(lblName); add(tfName);
        add(lblQty); add(tfQty);
        add(lblPrice); add(tfPrice);
        add(new JLabel()); add(btnSave);
    }
}