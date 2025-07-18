import java.io.Serializable;
import java.util.*;

public class CartLine implements Serializable {
    static final long serialVersionUID = 77L;
    public String cartLineName;
    public List<Station> cartLineStations;

    public CartLine(String cartLineName, List<Station> cartLineStations) {
        this.cartLineName = cartLineName;
        this.cartLineStations = cartLineStations;
    }
}
