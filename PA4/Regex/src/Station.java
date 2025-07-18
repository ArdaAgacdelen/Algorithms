import java.io.Serializable;

public class Station implements Serializable {
    static final long serialVersionUID = 55L;

    public Point coordinates;
    public String description;

    public Station(Point coordinates, String description) {
        this.coordinates = coordinates;
        this.description = description;
    }

    public String toString() {
        return this.description;
    }

    public boolean equals(Station o){
        return this.coordinates.equals(o.coordinates);
    }
}
class StationNode implements Comparable<StationNode>{
    private Station station;
    private Station prevStation;
    private double pathCost;
    private boolean cart;

    public StationNode(double cost, Station prevStation, Station station, boolean isCart) {
        this.pathCost = cost;
        this.prevStation = prevStation;
        this.station = station;
        this.cart = isCart;
    }

    @Override
    public int compareTo(StationNode o) {
        return Double.compare(this.pathCost, o.pathCost);
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Station getPrevStation() {
        return prevStation;
    }

    public void setPrevStation(Station prevStation) {
        this.prevStation = prevStation;
    }

    public double getPathCost() {
        return pathCost;
    }

    public void setPathCost(double pathCost) {
        this.pathCost = pathCost;
    }

    public boolean isCart() {
        return cart;
    }

    public void setIsCart(boolean isCart) {
        this.cart = isCart;
    }
    
}