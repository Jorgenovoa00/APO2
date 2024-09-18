
import java.util.Scanner;

public class Calculadora {
    public static void main(String[] args) {
        int valor1, valor2, total, op;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce el primer número: ");
        valor1 = scanner.nextInt(); 

        System.out.print("Introduce el segundo número: ");
        valor2 = scanner.nextInt(); 

        System.out.println("Introduce operacion a Realizar: ");
        System.out.println("1- Suma");
        System.out.println("2- Resta");
        System.out.println("3- Multiplicacion");
        op = scanner.nextInt();

        if (op == 1){
            total=Suma(valor1, valor2);
            System.out.printf("El resultado es: %d", total);
        } else if (op==2) {
            total=Resta(valor1, valor2);
            System.out.printf("El resultado es: %d", total);
        } else if (op==3) {
            total=Mult(valor1, valor2);
            System.out.printf("El resultado es: %d", total);
        } else {
            System.out.println("Dele pa tras mi hermano");
        }

        

    }
   
    public static int Suma(int a, int b){
        int s = a+b;
        return s;
    }
    public static int Resta(int c, int d){
        int r = c-d;
        return r;
    }
    public static int Mult(int e, int f){
        int m = e*f;
        return m;
    }
}