import Model.GameCategorie;
import Model.GameMaterial;
import Model.GameRecipe;
import Repository.Database;
import Scanners.CategorieScanner;
import Scanners.MaterialScanner;
import Scanners.RecipeScanner;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class Main {

    public static void main(String... aArgs) throws IOException {

        //region Material lezen
        log("Material lezen");

//        String filePath = "E:\\Games\\OneLife_v20\\";
        String filePath = "/local/onelife/OneLifeData7/";


        ArrayList<GameMaterial> gameMaterialArrayList = new ArrayList<GameMaterial>();
        File ObjectFoler = new File(filePath+"objects");
        File[] listOfObjectFiles = ObjectFoler.listFiles();

        gameMaterialArrayList.add(new GameMaterial(-2,"Walk Over",1));
        gameMaterialArrayList.add(new GameMaterial(-1,"Time",1));
        gameMaterialArrayList.add(new GameMaterial(0,"Empty",1));

        for (int i = 0; i < listOfObjectFiles.length; i++) {
            if (!(listOfObjectFiles[i].getName().equals("cache.fcz") || listOfObjectFiles[i].getName().equals("nextObjectNumber.txt"))) {
                GameMaterial object = MaterialScanner.ReadMaterial(listOfObjectFiles[i].toPath());
                gameMaterialArrayList.add(object);
            } else {
                log("onjuiste name:" + listOfObjectFiles[i].getName());
            }
        }

        //endregion
        //region Recipe lezen
        log("Recipe lezen");

        ArrayList<GameRecipe> gameRecipeArrayList = new ArrayList<GameRecipe>();
        File RecipeFoler = new File(filePath+"transitions");
        File[] listOfRecipeFiles = RecipeFoler.listFiles();

        for (int i = 0; i < listOfRecipeFiles.length; i++) {
            if(!(listOfRecipeFiles[i].getName().equals("cache.fcz") |listOfRecipeFiles[i].getName().equals("nextObjectNumber.txt"))){
                GameRecipe gameRecipe = RecipeScanner.ReadRecipe(listOfRecipeFiles[i].toPath(),listOfRecipeFiles[i].getName(), gameMaterialArrayList);
                gameRecipeArrayList.add(gameRecipe);
            }else{
                log("onjuiste name:"+listOfRecipeFiles[i].getName());
            }
        }

        //endregion
        //region categorie

        ArrayList<GameCategorie> gameCategorieArrayList = new ArrayList<GameCategorie>();
        File CategorieFolder = new File(filePath+"categories");
        File[] listOfCategorieFiles = CategorieFolder.listFiles();

        for (int i = 0; i < listOfCategorieFiles.length; i++) {
            if(!(listOfCategorieFiles[i].getName().equals("cache.fcz") |listOfCategorieFiles[i].getName().equals("nextObjectNumber.txt"))){
                log(listOfCategorieFiles[i].getName());
                GameCategorie gameCategorie = CategorieScanner.ReadGameCategorie(listOfCategorieFiles[i].toPath());
                gameCategorieArrayList.add(gameCategorie);
            }else{
                log("onjuiste name:"+listOfRecipeFiles[i].getName());
            }
        }


        //endregion
        //region tier calculations
        log("Bezig met tier berekeningen.");

        ArrayList<GameRecipe> unUsedRecipe = (ArrayList<GameRecipe>) gameRecipeArrayList.clone();
        ArrayList<GameMaterial> materials = new ArrayList<>();

        for(GameMaterial material : gameMaterialArrayList){
            if(material.RecipeTier==0){
                materials.add(material);
            }
        }

        boolean newRecipe = true;
        int tierCounter = 1;
        log(tierCounter);
        while(newRecipe) {
            ArrayList<GameRecipe> tierRecipe = new ArrayList<>();
            for(GameRecipe recipe : unUsedRecipe){
                if(materials.contains(recipe.Requirement1)&&materials.contains(recipe.Requirement2)){
                    tierRecipe.add(recipe);
                }
            }
            if(tierRecipe.size()>0){
                for(GameRecipe recipe : tierRecipe){
                    if(!materials.contains(recipe.Result1)){
                        DoCategorieOfMaterial(materials,gameCategorieArrayList,recipe.Result1,tierCounter,gameMaterialArrayList);
                    }
                    if(!materials.contains(recipe.Result2)){
                        DoCategorieOfMaterial(materials,gameCategorieArrayList,recipe.Result2,tierCounter,gameMaterialArrayList);
                    }
                    unUsedRecipe.remove(recipe);
                }
            }else{
                newRecipe=false;
            }
            log(tierCounter++);
        }

        log("not reachable");
        for(GameMaterial material : gameMaterialArrayList){
            if(!materials.contains(material)){
                log(material);
            }
        }

        //endregion
        //region database
        log("Bezig met datbase.");

        Database database = new Database();
        try {
            database.DoSql("DELETE FROM Material");
            database.DoSql("DELETE FROM Recipe");
            database.DoSql("DELETE FROM Categorie");
            database.AddGameMaterial(gameMaterialArrayList);
            database.AddGameRecipes(gameRecipeArrayList);
            database.AddGameCategorie(gameCategorieArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //endregion

        log("Done.");
    }

    private static void  DoCategorieOfMaterial(ArrayList<GameMaterial> materials,ArrayList<GameCategorie> gameCategories, GameMaterial materialResult,int tierCounter,ArrayList<GameMaterial> gameMaterialArrayList){
        materialResult.RecipeTier = tierCounter;
        materials.add(materialResult);
        for(GameCategorie gameCategorie : gameCategories){
            for(int id : gameCategorie.childIds){
                if(id==materialResult.ID){
                    for(GameMaterial material: gameMaterialArrayList){
                        if(material.ID==gameCategorie.id){
                            material.RecipeTier= tierCounter;
                            materials.add(material);
                        }
                    }
                }
            }
        }
        log(materialResult);
    }

    private static void log(Object aObject){
        System.out.println(String.valueOf(aObject));
    }
}
