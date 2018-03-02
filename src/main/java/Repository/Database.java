package Repository; /**
 * Created by Ivan on 25-6-2017.
 */


import Model.GameCategorie;
import Model.GameMaterial;
import Model.GameRecipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    Connection con = null;
    PreparedStatement pst = null;

    String url = "jdbc:mysql://192.168.2.37:3306/Ivan";
    String user = "no";
    String password = "no";
    String materialName = "Test";

    public static void main(String[] args) {

    }

    public Database(){
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public void AddGameMaterial(ArrayList<GameMaterial> objects) throws SQLException {
        for (GameMaterial object : objects) {
            String[] arguments = {String.valueOf(object.ID),object.Name,String.valueOf(object.RecipeTier)};
            DoSql("INSERT INTO Material (ID,Name,RecipeTier) VALUES(?,?,?)",arguments);
        }
    }

    public void AddGameRecipes(ArrayList<GameRecipe> objects) throws SQLException {
        for (GameRecipe object : objects) {
            String[] arguments = {String.valueOf(object.Requirement1.ID),String.valueOf(object.Requirement2.ID),String.valueOf(object.Result1.ID),String.valueOf(object.Result2.ID),String.valueOf(object.time)};
            DoSql("INSERT INTO Recipe (Requirement1,Requirement2,Result1,Result2,RecipeTime) VALUES(?,?,?,?,?)",arguments);
        }
    }

    public void AddGameCategorie(ArrayList<GameCategorie> objects) throws  SQLException{
        for(GameCategorie object : objects){
            for(int MaterialID : object.childIds){
                String[] arguments = {String.valueOf(object.id),String.valueOf(MaterialID)};
                DoSql("INSERT INTO Categorie (CategorieID,MaterialID) VALUES(?,?)",arguments);
            }
        }
    }

    public void DoSql(String sql) throws SQLException {
        String[] StringArray = {};
        DoSql(sql,StringArray);
    }

    public void DoSql(String sql,String[] arguments) throws SQLException {
        pst = con.prepareStatement(sql);
        for(int i=0;i<arguments.length;i++){
            pst.setString(i+1,arguments[i]);
        }
        pst.executeUpdate();
    }

}