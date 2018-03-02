package Model;

/**
 * Created by Ivan on 25-6-2017.
 */
public class GameMaterial {
    public int ID;
    public String Name;
    public float MapChance;
    public int RecipeTier;

    public GameMaterial(int id, String name, float mapChance) {
        ID = id;
        Name = name;
        MapChance = mapChance;
        if(MapChance==0){
            RecipeTier=-1;
        }else {
            RecipeTier=0;
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GameMaterial{");
        sb.append("ID=").append(ID);
        sb.append(", Name='").append(Name).append('\'');
        sb.append(", MapChance=").append(MapChance);
        sb.append(", RecipeTier=").append(RecipeTier);
        sb.append('}');
        return sb.toString();
    }
}
