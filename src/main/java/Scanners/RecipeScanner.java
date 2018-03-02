package Scanners;

import Model.GameMaterial;
import Model.GameRecipe;
import Repository.Database;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ivan on 25-6-2017.
 */
public class RecipeScanner {
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    public static GameRecipe ReadRecipe(Path fFilePath, String FileName, ArrayList<GameMaterial> objects) throws IOException {
        try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){
            scanner.useDelimiter(" ");
            String item1 = scanner.next();
            String item2 = scanner.next();
            String time = scanner.next();

            Scanner NameScanner = new Scanner(FileName);
            NameScanner.useDelimiter("_");
            String item3 = NameScanner.next();
            String nextLine = NameScanner.next();

            Scanner Name2Scanner = new Scanner(nextLine);
            Name2Scanner.useDelimiter(".txt");
            String item4 = Name2Scanner.next();

            GameMaterial requirement1 = FindMaterial(objects,Integer.parseInt(item3));
            GameMaterial requirement2 = FindMaterial(objects,Integer.parseInt(item4));
            GameMaterial result1 = FindMaterial(objects,Integer.parseInt(item1));
            GameMaterial result2 = FindMaterial(objects,Integer.parseInt(item2));
            return new GameRecipe(requirement1,requirement2,result1,result2,Integer.parseInt(time));
        }
    }

    public static GameMaterial FindMaterial(ArrayList<GameMaterial> materials,int id){
        for(GameMaterial material : materials) {
            if(material.ID==id) {
                return material;
            }
        }
        return null;
    }


    public static String GetName(String itemID,ArrayList<GameMaterial> objects){
        int itemInt = Integer.parseInt(itemID);
        if(itemInt == 0) {
            return "Empty";
        }else if(itemInt == -1){
            return "Time";
        }else if(itemInt == -2) {
            return "?instant";
        }else{
            for(GameMaterial object : objects){
                if(object.ID==itemInt){
                    return object.Name;
                }
            }
        }
        return "";
    }

    private static void log(Object aObject){
        System.out.println(String.valueOf(aObject));
    }
}
