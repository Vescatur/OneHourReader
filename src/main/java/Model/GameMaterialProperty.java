package Model;

/**
 * Created by Ivan on 3-1-2018.
 */
public class GameMaterialProperty {

    public String index;
    public String value;

    public GameMaterialProperty(String index, String value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public String toString() {
        return index + '=' + value;
    }
}
