package main.baseClasses;

import java.util.ArrayList;


/**
 *  Storage represents an improved ArrayList with build-in functions of accessing and updating data.
 */
public class Storage<T> {
    private ArrayList<T> elements = new ArrayList<T>();

    public Storage(){}

    public ArrayList<T> getElements() {
        return elements;
    }

    /**
     * Sets the storage's content.
     */
    public void setElements(ArrayList<T> elements){
        this.elements = elements;
    }

    /**
     * Adds element to the storage.
     */
    public void addElement(T element) {
        this.elements.add(element);
    }

    /**
     * @return Returns random element of the storage.
     */
    public T getRandomElement() {
        int n = elements.size() - 1;
        int randomIndex = (int)(Math.random() * (n + 1));
        return elements.get(randomIndex);
    }
}
