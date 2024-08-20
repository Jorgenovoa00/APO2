import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Random num_ram = new Random(40);
        double [][] t_conv;
        final int FILAS=101, COLS=6;
        t_conv = new double[FILAS][COLS];

        for (int i=0; i<FILAS; i++){
               
               for (int j=0; j<COLS ; j++){
                t_conv[i][j] = num_ram.nextDouble() * 40;
                }

                System.out.printf("Bogota: %.2f, Cali: %.2f, Medellin: %.2f, Pasto: %.2f, Barranquilla: %.2f, Manizales: %.2f%n", t_conv[i][0], t_conv[i][1], t_conv[i][2], t_conv[i][3], t_conv[i][4], t_conv[i][5]);
        }        
            }
    }