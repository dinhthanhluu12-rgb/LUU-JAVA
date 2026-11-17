package model;

public class Cake extends Product{
    private String brand;

    public Cake(String id, String name, double price, int stoke, String brand){
        super(id,name,price,stoke);
        this.brand = brand;
    }

    public String getBrand(){
        return brand;
    }

    public void setBrand(){
        this.brand = brand;
    }

    @Override
    public boolean isExpensive(){
        return this.getPrice() > 100000;
    }
}
