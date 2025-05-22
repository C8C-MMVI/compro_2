package com.coffeemenu.coffeeSpring.modelCodes;

import jakarta.validation.constraints.*;
import java.util.List;

public class Coffee {
    private int id;

    @NotBlank(message = "Coffee name is required")
    private String name;
    @NotBlank(message = "Select a coffee bean type")
    private String type;
    @NotBlank(message = "Coffee size is required")
    private String size;
    @Positive(message = "Coffee price must be greater than 0")
    private double price;
    @NotBlank(message = "Coffee bean roast level is needed")
    private String roastLevel;
    private String origin;
    private boolean isDecaf;
    @Min(value = 0, message = "Stock must be 0 or more")
    private int stock;
    private List<String> flavorNotes;
    @NotBlank(message = "Brew method is needed")
    private String brewMethod;
    @NotNull(message = "Image needed")
    private String picture;

    public Coffee() {}

    public Coffee(int id, String name, String type, String size,
                  double price, String roastLevel, String origin,
                  boolean isDecaf, int stock, List<String> flavorNotes,
                  String brewMethod, String picture) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = price;
        this.roastLevel = roastLevel;
        this.origin = origin;
        this.isDecaf = isDecaf;
        this.stock = stock;
        this.flavorNotes = flavorNotes;
        this.brewMethod = brewMethod;
        this.picture = picture;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    public String getRoastLevel() { return roastLevel; }
    public String getOrigin() { return origin; }
    public boolean isDecaf() { return isDecaf; }
    public int getStock() { return stock; }
    public List<String> getFlavorNotes() { return flavorNotes; }
    public String getBrewMethod() { return brewMethod; }
    public String getPicture() {return picture;}

    public void setId(int id) { this.id = id; }

    public void setName(String name) {
        name = name.trim();
        String[] words = name.split("\\s+");
        StringBuilder formatted = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                if (word.length() > 2)
                    formatted.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase()).append(" ");
                else
                    formatted.append(word.toUpperCase()).append(" ");
            }
        }
        this.name = formatted.toString().trim();
    }

    public void setType(String type) { this.type = type; }
    public void setSize(String size) { this.size = size; }
    public void setPrice(double price) { this.price = price; }
    public void setRoastLevel(String roastLevel) { this.roastLevel = roastLevel; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDecaf(boolean isDecaf) { this.isDecaf = isDecaf; }
    public void setStock(int stock) { this.stock = stock; }
    public void setFlavorNotes(List<String> flavorNotes) { this.flavorNotes = flavorNotes; }
    public void setBrewMethod(String brewMethod) { this.brewMethod = brewMethod; }
    public void setPicture(String picture) {this.picture = picture;}
}
