package com.coffeemenu.coffeeSpring;

import java.util.List;

public class Coffee {
    private int id;
    private String name;
    private String type;
    private String size;
    private double price;
    private String roastLevel;
    private String origin;
    private boolean isDecaf;
    private int stock;
    private List<String> flavorNotes;
    private String brewMethod;

    /**
     * Constructor for coffee data
     * @param id ID no.
     * @param name Coffee name
     * @param type Coffee bean type
     * @param size Coffee cup size
     * @param price Coffee price
     * @param roastLevel Type of coffee bean roast
     * @param origin Coffee bean origin
     * @param isDecaf Tells whether the coffee is decaf or not
     * @param stock Checks if the coffee is available
     * @param flavorNotes Coffee flavor notes
     * @param brewMethod How the coffee was brewed
     */
    public Coffee(int id, String name, String type, String size, double price, String roastLevel, String origin, boolean isDecaf, int stock, List<String> flavorNotes, String brewMethod) {
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
    }

    /**
     * Object encapsulation
     * @return the following in the Constructor
     */
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
}
