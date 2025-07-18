import java.io.Serializable;

public class RouteDirection implements Serializable {
    static final long serialVersionUID = 44L;

    public String startStationName;
    public String endStationName;
    public double duration;
    boolean cartRide; // true if it's a ride on cart, false if it's a walk

    public RouteDirection(String startStationName, String endStationName, double duration, boolean cartRide) {
        this.startStationName = startStationName;
        this.endStationName = endStationName;
        this.duration = duration;
        this.cartRide = cartRide;
    }

    @Override
    public String toString() {
        if (cartRide) {
            return String.format(
                    "Ride the cart from \"%s\" to \"%s\" for %.2f minutes.", startStationName, endStationName, duration);
        } else {
            return String.format("Walk from \"%s\" to \"%s\" for %.2f minutes.", startStationName, endStationName, duration);
        }
    }
}
