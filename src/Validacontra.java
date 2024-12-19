import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validacontra {

    // Expresión regular para validar contraseñas
    private static final String PATRON_CONTRASENA =
            "^(?=(?:.*[A-Z]){2,})(?=(?:.*[a-z]){3,})(?=(?:.*\\d){1,})(?=.*[@#$%^&+=!])(?=.{8,}).*$";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Thread> hilos = new ArrayList<>();

        System.out.println("Ingrese el número de contraseñas a validar: ");
        int numContrasenas = leerEntero(scanner);

        for (int i = 0; i < numContrasenas; i++) {
            System.out.println("Ingrese la contraseña #" + (i + 1) + ": ");
            String contrasena = scanner.nextLine();

            Thread hilo = new Thread(new ValidadorContrasena(contrasena));
            hilos.add(hilo);
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar la finalización de un hilo: " + e.getMessage());
            }
        }

        System.out.println("Validación de contraseñas finalizada.");
        scanner.close();
    }

    private static int leerEntero(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero:");
            }
        }
    }

    static class ValidadorContrasena implements Runnable {
        private final String contrasena;

        public ValidadorContrasena(String contrasena) {
            this.contrasena = contrasena;
        }

        @Override
        public void run() {
            boolean esValida = validarContrasena(contrasena);
            System.out.println("Contraseña: \"" + contrasena + "\" -> " + (esValida ? "VÁLIDA" : "INVÁLIDA"));
        }

        private boolean validarContrasena(String contrasena) {
            return Pattern.matches(PATRON_CONTRASENA, contrasena);
        }
    }
}
