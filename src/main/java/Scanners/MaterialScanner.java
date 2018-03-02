package Scanners;

import Model.GameMaterial;
import Model.GameMaterialProperty;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Ivan on 25-6-2017.
 */

public class MaterialScanner {
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    public static GameMaterial ReadMaterial(Path fFilePath) throws IOException {
        try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){

            ArrayList<GameMaterialProperty> properties = new ArrayList<GameMaterialProperty>();

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter("=");
                String index = lineScanner.next();
                if(lineScanner.hasNext()){
                    String value = lineScanner.next();
                    properties.add(new GameMaterialProperty(index,value));
                }else{
                    properties.add(new GameMaterialProperty("name",index));
                }
            }

            int id = Integer.parseInt(FindGameMaterialProperty(properties,"id"));
            String name = FindGameMaterialProperty(properties,"name");
            String mapChance = FindGameMaterialProperty(properties,"mapChance");
            float mapChanceFloat = Float.parseFloat(new Scanner(mapChance).useDelimiter("#").next());
            GameMaterial object = new GameMaterial(id,name,mapChanceFloat);
            return object;
        }
    }


    public static String FindGameMaterialProperty(ArrayList<GameMaterialProperty> properties,String index){
        for(GameMaterialProperty property : properties) {
            if(Objects.equals(property.index, index)) {
                return property.value;
            }
        }
        return null;
    }

    protected static void processLine(String aLine){
        //use a second Scanner to parse the content of each line
        Scanner scanner = new Scanner(aLine);
        scanner.useDelimiter(" ");
        if (scanner.hasNext()){
            //assumes the line has a certain structure
            String name = scanner.next();
            if(scanner.hasNext()) {
                String value = scanner.next();

                log("Name is : " + name.trim() + ", and Value is : " + value.trim());
            }else{
                log(name);
            }
        }
        else {
            log("Empty or invalid line. Unable to process.");
        }
    }


    private static void log(Object aObject){
        System.out.println(String.valueOf(aObject));
    }

}
