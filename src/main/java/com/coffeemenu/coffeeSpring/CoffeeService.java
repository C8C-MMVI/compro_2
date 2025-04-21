package com.coffeemenu.coffeeSpring;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private ArrayList<Coffee> coffee;
    private static final String FILE_NAME = "coffee.csv";

    public CoffeeService(){
        coffee = new ArrayList<>();
        readFromDisk();
    }

    public ArrayList<Coffee> getCoffee() {
        return coffee;
    }

    public void deleteCoffee(int id){
        coffee.removeIf(coffee -> coffee.getId() == id);
        writeToDisk();
    }

    public List<Coffee> searchCoffee(String keyword){
        if(keyword.trim().isEmpty()){
            return coffee;
        }

        return coffee.stream().filter(c ->
                c.getName().toLowerCase().contains(keyword.toLowerCase())
                || c.getType().toLowerCase().contains(keyword.toLowerCase())
                || c.getSize().toLowerCase().contains(keyword.toLowerCase())
                || String.valueOf(c.getPrice()).contains(keyword.toLowerCase())
                || c.getRoastLevel().toLowerCase().contains(keyword.toLowerCase())
                || c.getOrigin().toLowerCase().contains(keyword.toLowerCase())
                || String.valueOf(c.isDecaf()).contains(keyword.toLowerCase())
                || String.valueOf(c.getStock()).contains(keyword.toLowerCase())
                || c.getFlavorNotes().stream().anyMatch(note -> note.toLowerCase().contains(keyword.toLowerCase()))
                || c. getBrewMethod().toLowerCase().contains(keyword.toLowerCase())
        ).collect(Collectors.toList());
    }

    public Coffee getCoffee(int id){
        for(Coffee c: coffee){
            if(c.getId() == id)
                return c;
        }

        return null;
    }

    public void updateCoffee(int id, Coffee update){
        for(int i = 0; i < coffee.size(); i++){
            if(coffee.get(i).getId() == id){
                coffee.set(i, update);
                writeToDisk();
                break;
            }
        }
    }
    public void addCoffee(Coffee coffees){
//        coffee.setId(getLastId() + 1);
        coffee.add(coffees);
        writeToDisk();
    }

    public int getLastId(){
        if(coffee.isEmpty()){
            return 0;
        }
        return coffee.get(coffee.size()-1).getId();
    }

    public void writeToDisk(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))){
            //write the content of the arraylist into csv
            for(Coffee c : coffee){
                bw.write(c.getId() + ","
                        + c.getName() + ","
                        + c.getType() + ","
                        + c.getSize() + ","
                        + c.getPrice() + ","
                        + c.getRoastLevel() + ","
                        + c.getOrigin() + ","
                        + c.isDecaf() + ","
                        + c.getStock() + ","
                        + c.getFlavorNotes() + ","
                        + c.getBrewMethod()
                );
                bw.newLine();
            }
        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This read the CSV file and loads it to the students ArrayList
     */
    public void readFromDisk(){
        File file = new File(FILE_NAME);
        if(!file.exists()){
            System.out.println("File not found");
            return;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(",");

                Coffee c = new Coffee();
                c.setId(Integer.parseInt(data[0]));
                c.setName(data[1]);
                c.setType(data[2]);
                c.setSize(data[3]);
                c.setPrice(Double.parseDouble(data[4]));
                c.setRoastLevel(data[5]);
                c.setOrigin(data[6]);
                Boolean.parseBoolean(data[7]);
                c.setStock(Integer.parseInt(data[8]));
                c.setFlavorNotes(Collections.singletonList(data[9]));
                c.setBrewMethod(data[10]);
                coffee.add(c);
            }
        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
