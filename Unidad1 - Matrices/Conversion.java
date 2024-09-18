
import java.util.Scanner;

public class Conversion {
 public static void main(String[] args) {
    int Opcion;
    float ValOr, ValConv;
    Scanner lector = new Scanner(System.in);
     System.out.println("Elija una opcion:");
     System.out.println("1 Celcius a Fahrenheit");
     System.out.println("2 Kelvin a Fahrenheit");
     System.out.println("3 Fahrenheit a Celsius ");
     System.err.println("4 Kelvin a Celcius");
     Opcion = lector.nextInt();
     System.out.println("Valor de la temperatura: ");
     ValOr=lector.nextFloat();
     switch (Opcion) {
         case 1:
            ValConv=ValOr*1.8f+32;
            System.out.printf("Valor de la temperatura a F es %f",ValConv);
             break;
         case 2:
            ValConv=1.8f*(ValOr-273.15f)+32;
            System.out.printf("Valor de la temperatura a K es %f",ValConv);
            break; 
         case 3:
            ValConv=0.55f*ValOr-32;
            System.out.printf("Valor de la temperatura a C es %f",ValConv);
            break;
         case 4:
            ValConv=ValOr-273.15f;
            System.out.printf("Valor de la temperatura a C es %f",ValConv);
            break;           
         default:
             throw new AssertionError();
     }
 }   
}