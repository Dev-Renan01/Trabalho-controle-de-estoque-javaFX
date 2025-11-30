package com.minimundo.database;

import com.minimundo.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class Database {

        private static final String URL = "jdbc:postgresql://localhost:5432/estoque";
        private static final String USER = "postgres";
        private static final String PASS = "2208"; // ajuste conforme seu PostgreSQL

        static {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)){
                String sql = """
                CREATE TABLE IF NOT EXISTS products (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    quantity INT NOT NULL,
                    price NUMERIC(10,2) NOT NULL
                );
            """;
                conn.createStatement().execute(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void insertProduct(String name, int qty, double price) throws Exception {
            String sql = "INSERT INTO products(name, quantity, price) VALUES(?,?,?)";
            try(Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1,name);
                ps.setInt(2, qty);
                ps.setDouble(3, price);
                ps.executeUpdate();
            }
        }

        public static List<Produto> getAllProducts() throws Exception {
            List<Produto> list = new ArrayList<>();
            String sql = "SELECT * FROM products ORDER BY id";
            try(Connection conn = DriverManager.getConnection(URL, USER, PASS);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)){
                while(rs.next()){
                    list.add(new Produto(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    ));
                }
            }
            return list;
        }

        public static void updateProduct(int id, String name, int qty, double price) throws Exception{
            String sql = "UPDATE products SET name=?, quantity=?, price=? WHERE id=?";
            try(Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1,name);
                ps.setInt(2,qty);
                ps.setDouble(3,price);
                ps.setInt(4,id);
                ps.executeUpdate();
            }
        }

        public static void deleteProduct(int id) throws Exception{
            String sql = "DELETE FROM products WHERE id=?";
            try(Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1,id);
                ps.executeUpdate();
            }
        }

        public static List<Produto> search(String query) throws Exception{
            List<Produto> list = new ArrayList<>();
            String sql = "SELECT * FROM products WHERE name ILIKE ?";
            try(Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1,"%"+query+"%");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    list.add(new Produto(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    ));
                }
            }
            return list;
        }
    }
