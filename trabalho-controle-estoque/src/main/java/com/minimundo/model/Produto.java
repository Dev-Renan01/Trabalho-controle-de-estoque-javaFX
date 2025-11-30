package com.minimundo.model;

public class Produto {

        private int id;
        private String name;
        private int quantity;
        private double price;

        public Produto(int id, String name, int quantity, double price){
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }

        @Override
        public String toString() {
            return id + " - " + name + " (" + quantity + ") R$ " + price;
        }
    }

