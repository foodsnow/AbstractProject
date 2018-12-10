import java.lang.reflect.Array;
import java.util.ArrayList;

public class Drop {
    ArrayList<Attribute> attributes;
    Drop(ArrayList<Attribute> drop){
        this.attributes = drop;
    }
    public ArrayList<Attribute> getDrop() {
        return attributes;
    }

}
