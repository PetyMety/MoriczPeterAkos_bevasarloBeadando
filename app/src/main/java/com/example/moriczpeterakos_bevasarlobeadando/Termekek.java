package com.example.moriczpeterakos_bevasarlobeadando;

public class Termekek {

    private int id;
    private String Name;
    private int Price;
    private float Count;
    private String Measure;
    private float BruttoPrice;

    public Termekek( String name, int price, float count, String measure ) {
        this.id = id;
        Name = name;
        Price = price;
        Count = count;
        Measure = measure;
        BruttoPrice = Math.round((price*count)*100)/100;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public float getCount() {
        return Count;
    }

    public void setCount(float count) {
        Count = count;
    }

    public String getMeasure() {
        return Measure;
    }

    public void setMeasure(String measure) {
        Measure = measure;
    }

    public float getBruttoPrice() {
        return BruttoPrice;
    }
}
