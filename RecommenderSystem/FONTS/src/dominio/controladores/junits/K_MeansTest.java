package dominio.controladores.junits;

import dominio.clases.algorithm.k_means.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class K_MeansTest {
    private static Scanner sc;

    public static Map<Integer, Float> leerUsuario(){
        Map<Integer, Float> user = new TreeMap<>();
        int numItems = sc.nextInt();
        for(int j = 0; j < numItems; ++j){
            int itemID = sc.nextInt();
            float itemVal = sc.nextFloat();
            user.put(itemID, itemVal);
        }
        return user;
    }

    @org.junit.Test
    public void testcosineSquaredSimilBasic() throws FileNotFoundException {
        sc = new Scanner(new File("dominio/controladores/junits/Input_Junit_K_Means.txt"));
        K_Means km = new K_Means();
        Map<Integer, Float> u1 = leerUsuario();
        Map<Integer, Float> u2 = leerUsuario();
        Map<Integer, Float> u3 = leerUsuario();
        Map<Integer, Float> u4 = leerUsuario();

        float delta = 0.001f;

        float simil = km.cosineSquaredSimil(u1, u2);
        double expected = 0.7352f;

        assertEquals(expected, simil, delta);

        simil = km.cosineSquaredSimil(u1, u3);
        expected = 0.0f;

        assertEquals(expected, simil, delta);

        simil = km.cosineSquaredSimil(u2, u4);
        expected = 1.0f;

        assertEquals(expected, simil, delta);
    }
}