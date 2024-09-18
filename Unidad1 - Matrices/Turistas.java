import java.util.Random;

public class Turistas {
    public static void main(String[] args) {

        final int FILAS = 12, COLS = 4;
        int[][] turistas = new int[FILAS][COLS];
        Random num_ram = new Random();

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLS; j++) {
                turistas[i][j] = 1000 + num_ram.nextInt(9001);
            }
        }
        String[] ciudades = {"Cali", "Medellin", "Bogota", "Barranquilla"};
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        /*Segun TuTubo el String[] sirve para declarar la cantidad de datos que habran y se deja vacio para que despues dde declararlo y colocar = coloque el nombre de las variables*/
        
        System.out.print("       ");  
        for (String ciudad : ciudades) {
            System.out.print(ciudad + "  ");
        } /*Parte 100% sacadda de chatgpt segun esto es para que coloque de seguido y no de renglon */
        System.out.println();


        for (int i = 0; i < FILAS; i++) {
            System.out.print(meses[i] + "  ");
            for (int j = 0; j < COLS; j++) {
                System.out.print(turistas[i][j] + "   ");
            }
            System.out.println();
        }

        int maxTuristas = 0;
        String mesMax = "";
        String ciudadMax = "";

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (turistas[i][j] > maxTuristas) {
                    maxTuristas = turistas[i][j];
                    mesMax = meses[i];
                    ciudadMax = ciudades[j];
                }
            }
        }
        

        MayorMesTurist(turistas, meses);
        System.out.println("El mes con más turistas es " + mesMax + " y la ciudad es " + ciudadMax + " con " + maxTuristas + " turistas.");


    }

    public static void MayorMesTurist(int[][] turistas, String[] meses) {
        int maxTuristas = 0;
        String mesMax = "";

        for (int i = 0; i < turistas.length; i++) {
            int totalTuristasMes = 0;
            for (int j = 0; j < turistas[i].length; j++) {
                totalTuristasMes += turistas[i][j];
            }

            if (totalTuristasMes > maxTuristas) {
                maxTuristas = totalTuristasMes;
                mesMax = meses[i];
            }
        }

        System.out.println("El mes con más turistas es " + mesMax + " con un total de " + maxTuristas + " turistas.");

    }
}
