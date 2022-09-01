package dominio.controladores.drivers;


import dominio.clases.content.Content;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class DriverContent {
    public static void options() {
        System.out.println("Choose an option with the necessary parameters needed:");
        System.out.println("\t 0) exit");
        System.out.println("\t 1) Content()");
        System.out.println("\t 2) Content(String s, Integer i, Double d, List<String> categorics)");
        System.out.println("\t 3) getTag()");
        System.out.println("\t 4) getTag_numi()");
        System.out.println("\t 5) getTag_numd()");
        System.out.println("\t 6) getCategorics()");
        System.out.println("\t 7) setTag(String dominio.controladores.clases.atribut.tag)");
        System.out.println("\t 8) setTag_numi(Integer tag_numi)");
        System.out.println("\t 9) setTag_numd(Double tag_numd)");
        System.out.println("\t 10) setCategorics(List<String> categorics)");
    }

    public static void main(String[] args) {
        System.out.println("Driver Content class:");
        Content cont = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean finish = false;
            options();
            while (!finish) {

                String line;
                String[] param;
                String op;

                line = br.readLine();

                param = line.split(" ");
                op = param[0];
                try {
                    switch (op) {
                        case "0" : finish = true;
                            break;
                        case "1" :
                            cont = new Content();
                            System.out.println("Content created");
                            break;
                        case "2" :
                            String s = param[1];
                            int i = parseInt(param[2]);
                            double d = parseDouble(param[3]);
                            System.out.println("Define Categorics num to insert and the categorics: ");
                            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                            String line1;
                            String[] param1;
                            line1 = br1.readLine();
                            param1 = line1.split(" ");
                            int lim = parseInt(param1[0]);
                            ArrayList<String> aux = new ArrayList<>(Arrays.asList(param1).subList(1, lim + 1));
                            cont = new Content(s, i, d, aux);
                            System.out.println("Content Created");
                            break;
                        case "3" :
                            System.out.println(cont.getTag());
                            break;
                        case "4" :
                            System.out.println(cont.getTag_numi());
                            break;
                        case "5" :
                            System.out.println(cont.getTag_numd());
                            break;
                        case "6" :
                            List<String> l = cont.getCategorics();
                            for(String se : l){
                                System.out.println(se);
                            }
                            break;
                        case "7" :
                            cont.setTag(param[1]);
                            System.out.println("Tag modified");
                            break;
                        case "8" :
                            cont.setTag_numi(parseInt(param[1]));
                            System.out.println("Integer modified");
                            break;
                        case "9" :
                            cont.setTag_numd(parseDouble(param[1]));
                            System.out.println("Double modified");
                            break;
                        case "10":
                            System.out.println("Define Categorics num to insert and categorics: ");
                            BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
                            String line2;
                            String[] param2;
                            line2 = br2.readLine();
                            param2 = line2.split(" ");
                            int limt2 = parseInt(param2[0]);
                            ArrayList<String> aux2 = new ArrayList<>(Arrays.asList(param2).subList(1, limt2 + 1));
                            cont.setCategorics(aux2);
                            System.out.println("Categorics changed");
                            break;
                        default : System.out.println("Choose another option");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

}
