package Model;

/**
 * Created by Ivan on 3-1-2018.
 */
public class GameRecipe {
    public GameMaterial Requirement1;
    public GameMaterial Requirement2;

    public GameMaterial Result1;
    public GameMaterial Result2;

    public int time;

    public GameRecipe(GameMaterial requirement1, GameMaterial requirement2, GameMaterial result1, GameMaterial result2, int time) {
        Requirement1 = requirement1;
        Requirement2 = requirement2;
        Result1 = result1;
        Result2 = result2;
        this.time = time;
    }
}