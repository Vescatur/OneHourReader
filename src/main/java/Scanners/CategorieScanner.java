package Scanners;

import Model.GameCategorie;
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

@SuppressWarnings("Duplicates")
public class CategorieScanner {
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    public static GameCategorie ReadGameCategorie(Path fFilePath) throws IOException {
        try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){

            ArrayList<GameCategorie> properties = new ArrayList<>();

            String line1 = scanner.nextLine();
            Scanner lineScanner1 = new Scanner(line1);
            lineScanner1.useDelimiter("=");
            lineScanner1.next();
            int id = Integer.parseInt(lineScanner1.next());

            String line2 = scanner.nextLine();
            Scanner lineScanner2 = new Scanner(line2);
            lineScanner2.useDelimiter("=");
            lineScanner2.next();

            int numberOfItems = Integer.parseInt(lineScanner2.next());

            int[] childIds= new int[numberOfItems];
            int i = 0;
            while(scanner.hasNextLine()){
                childIds[i] = Integer.parseInt(scanner.nextLine());
                i++;
            }

            return new GameCategorie(id,childIds);
        }
    }




    private static void log(Object aObject){
        System.out.println(String.valueOf(aObject));
    }

}
