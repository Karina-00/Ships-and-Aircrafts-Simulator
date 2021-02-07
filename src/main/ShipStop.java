package main;


public class ShipStop extends MapEntity{
    private final int id;
    private final Storage<ShipStop> neighbours = new Storage<>();

    public ShipStop(int id, double x, double y) {
        super(x, y);
        this.id = id;
        this.setRadius(2.0);
    }

    public int getId() {
        return id;
    }

    public Storage<ShipStop> getNeighbours() {
        return neighbours;
    }

    public ShipStop getRandomElementExcept(int id){
        ShipStop random;
        do {
            random = getNeighbours().getRandomElement();
        } while(random.getId() == id);
        return random;
    }

    @Override
    public String toString() {
        return "ShipStop id = " + id;
    }
}
