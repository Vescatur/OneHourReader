package Model;

/**
 * Created by Ivan on 6-1-2018.
 */
public class GameCategorie {
    public int id;
    public int[] childIds;

    public GameCategorie(int id, int[] childIds) {
        this.id = id;
        this.childIds = childIds;
    }
}
