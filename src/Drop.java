import java.lang.reflect.Array;
import java.util.ArrayList;

public class Drop {
    private int idItem;
    ArrayList<Attribute> attributes;
    Drop(ArrayList<Attribute> drop, int idItem){
        this.attributes = drop;
        this.idItem = idItem;
    }
    public ArrayList<Attribute> getDrop() {
        return attributes;
    }
    public int getItemID(){
        return idItem;
    }
}
