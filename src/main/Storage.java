package main;

import java.util.ArrayList;


public class Storage<T> {
    private ArrayList<T> elements = new ArrayList<T>();
    private int idGenerator = 0;

    public Storage(){}

    public ArrayList<T> getElements() {
        return elements;
    }

    public void setElements(ArrayList<T> elements){
        this.elements = elements;
    }

    public void addElement(T element) {
        this.elements.add(element);
    }

    public T getRandomElement() {
        int n = elements.size() - 1;
        int randomIndex = (int)(Math.random() * (n + 1));
        return elements.get(randomIndex);
    }

    public int generateId(){
        return idGenerator++;
    }
}
