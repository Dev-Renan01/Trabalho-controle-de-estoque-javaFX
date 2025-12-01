package com.minimundo.swing;

import com.minimundo.database.Database;
import com.minimundo.model.Produto;
import com.minimundo.fx.ListaView;
import com.minimundo.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchDialog extends JDialog {

    public SearchDialog(ListaView listaView) {
        setTitle("Buscar Produto");
        setModal(true);
        setSize(300,150);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        JLabel lblQuery = new JLabel("Nome:");
        JTextField tfQuery = new JTextField(15);
        JButton btnSearch = new JButton("Buscar");

        btnSearch.addActionListener(e -> {
            try {
                String query = tfQuery.getText();
                if(query.isEmpty()){
                    JOptionPane.showMessageDialog(this,"Digite algo para buscar!");
                    return;
                }
                List<Produto> result = Database.search(query);
                listaView.loadProductsByList(result);
                dispose();
            } catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"Erro na busca!");
            }
        });

        add(lblQuery);
        add(tfQuery);
        add(btnSearch);
    }
}