package dominio.controladores.drivers;

import persistencia.preprocessat.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;


public class DriverCSVparserRate {

    public static void options() {
        System.out.println("Choose an option with the necessary parameters needed:");
        System.out.println("\t 0) exit");
        System.out.println("\t 1) iniciar prueba 1 ");
        System.out.println("\t 2) iniciar prueba 2 ");
        System.out.println("\t 3) iniciar prueba 3 ");
        System.out.println("\t 4) iniciar prueba 4 ");
        System.out.println("\t 5) CSVparserRate(String path)");
        System.out.println("\t 6) getNumRows()");
        System.out.println("\t 7) getNumCols()");
        System.out.println("\t 8) getHeader()");
        System.out.println("\t 9) getPath()");
        System.out.println("\t 10) getContent()");
        System.out.println("\t 11) getMapRate()");
        System.out.println("\t 12) setNumRows(Integer numRows)");
        System.out.println("\t 13) setNumCols(Integer numCols)");
        System.out.println("\t 14) setHeader(List<String> header)");
        System.out.println("\t 15) setPath(String path)");
        System.out.println("\t 16) setContent(List<List<String>> dominio.controladores.clases.atribut.content)");
        System.out.println("\t 17) setMapRate(Map<Integer, Map<Integer, Float>> mapRate)");
        System.out.println("\t 18) readLoadRate()");
        System.out.println("\t 19) String_to_Int(String s)");
        System.out.println("\t 20) String_to_Float(String s)");
        System.out.println("\t 21) LoadRate(List<List<String>> rate_content)");
        System.out.println("\t 22) getRow(int i) (rango: 0 - (tamaño filas del csv)-1) ");
    }

    public static void main(String[] args) {
        System.out.println("Driver CSVparserRate class:");
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Por defector se inicia con la prueba 1, eliga opcion si desea cambiar de csv");

            CSVparserRate csv = new CSVparserRate("FONTS/src/dominio/controladores/drivers/provesCSVparserRate/prova1rating.csv");
            csv.readLoadRate();
            csv.LoadRate(csv.getContent());

            boolean finish = false;
            while (!finish) {
                options();

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
                            csv = new CSVparserRate("FONTS/src/dominio/controladores/drivers/provesCSVparserRate/prova1rating.csv");
                            csv.readLoadRate();
                            csv.LoadRate(csv.getContent());
                            System.out.println("Pruba 1 cargada");
                            break;
                        case "2" :
                            csv = new CSVparserRate("FONTS/src/dominio/controladores/drivers/provesCSVparserRate/prova2rating.csv");
                            csv.readLoadRate();
                            csv.LoadRate(csv.getContent());
                            System.out.println("Pruba 2 cargada");
                            break;
                        case "3" :
                            csv = new CSVparserRate("FONTS/src/dominio/controladores/drivers/provesCSVparserRate/prova3rating.csv");
                            csv.readLoadRate();
                            csv.LoadRate(csv.getContent());
                            System.out.println("Pruba 3 cargada");
                            break;
                        case "4" :
                            csv = new CSVparserRate("FONTS/src/dominio/controladores/drivers/provesCSVparserRate/prova4rating.csv");
                            csv.readLoadRate();
                            csv.LoadRate(csv.getContent());
                            System.out.println("Pruba 4 cargada");
                            break;
                        case "5" :
                            csv = new CSVparserRate(param[1]);
                            System.out.println("CSV created");
                            break;
                        case "6" :
                            int in = csv.getNumRows();
                            ++in;
                            System.out.println(in);
                            break;
                        case "7" :
                            int inn = csv.getNumCols();
                            ++inn;
                            System.out.println(inn);
                            break;
                        case "8" :
                            List<String> aux = csv.getHeader();
                            for (int i = 0; i < aux.size(); ++i){
                                System.out.println(aux.get(i));
                            }
                            break;
                        case "9" :
                            System.out.println(csv.getPath());
                            break;

                        case "10" :
                            List<List<String>>  l = csv.getContent();
                            int f = 0;
                            for (List<String> aux1 : l){
                                System.out.println("Fila: " + f);
                                System.out.println(aux1);
                                ++f;
                            }
                            break;
                        case "11" :
                            csv.LoadRate(csv.getContent());
                            Map<Integer, Map<Integer, Float>> mapRate = csv.getMapRate();
                            Map<Integer, Float> aux1;
                            for (int user : mapRate.keySet()) {
                                System.out.println("User: " + user);
                                aux1 = mapRate.get(user);
                                System.out.println("Items and rates: ");
                                System.out.println(Collections.singletonList(aux1));
                            }
                            break;
                        case "12" :
                            csv.setNumRows(parseInt(param[1]));
                            System.out.println("Number of rows changed");
                            break;
                        case "13" :
                            csv.setNumCols(parseInt(param[1]));
                            System.out.println("Number of columns changed");
                            break;
                        case "14" :
                            System.out.println("Define num of elements to insert and the elements: ");
                            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                            String line1;
                            String[] param1;
                            line1 = br1.readLine();
                            param1 = line1.split(" ");
                            int limt1 = parseInt(param1[0]);
                            List<String> aux2 = new ArrayList<>();
                            for (int j = 1; j <= limt1; ++j){
                                aux2.add(param1[j]);
                            }
                            csv.setHeader(aux2);
                            System.out.println("Header changed");
                            break;
                        case "15" :
                            System.out.println("!!you will change the document!! ");
                            System.out.println("Introduce path : ");
                            BufferedReader br4 = new BufferedReader(new InputStreamReader(System.in));
                            String resp1 = br4.readLine();
                            csv.setPath(resp1);
                            //fis = new FileInputStream(resp1);
                            csv = new CSVparserRate(resp1);
                            csv.readLoadRate();
                            System.out.println("Path changed");
                            break;
                        case "16" :
                            csv.setContent(csv.getContent());
                            System.out.println("Done, attributed new dominio.controladores.clases.atribut.content");
                            break;
                        case "17" :
                            csv.setMapRate(csv.getMapRate());
                            System.out.println("Done, attributed new mapRate");
                            break;
                        case "18" :
                            csv.readLoadRate();
                            System.out.println("Done, csv parsered");
                            System.out.println("Execute case 10 to see results");
                            break;
                        case "19" :
                            System.out.println(csv.String_to_Int(param[1]));
                            break;
                        case "20" :
                            System.out.println(csv.String_to_Float(param[1]));
                            break;
                        case "21" :
                            csv.LoadRate(csv.getContent());
                            System.out.println("Datos Preprocesados, ejecute el caso 11 para ver los resultados");
                            break;
                        case "22" :
                            System.out.println("Inserte numero fila a conusltar: ");
                            BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                            String res2 = br3.readLine();
                            System.out.println(csv.getRow(parseInt(res2)));
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
