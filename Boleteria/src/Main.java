import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.text.Document;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Random;

public class Main {
    private static HashSet<Integer> numerosGenerados = new HashSet<>();
    private static int limiteInferior = 2;
    private static int limiteSuperior = 6;

    public static void main(String[] args) {

        // Mensaje que se mostrará en el menú de opciones
        String mensaje = "\n1) Repetir" +
                "\n2) Escoger el rango" +
                "\n3) Buscar número generado" +
                "\n4) Ver listado de números generados" +
                "\n6) Salir";
        int opciones = 0;

        do{
            // Mostrar el menú y obtener la opción seleccionada por el usuario
            opciones = Integer.parseInt(JOptionPane.showInputDialog("Por favor escoja una opción" + mensaje));
            switch (opciones) {
                case 1:
                    mostrarRango();
                    generarNumeroAleatorio();
                    break;
                case 2:
                    establecerRango();
                    break;
                case 3:
                    buscarNumeroGenerado();
                    break;
                case 4:
                    verListadoNumerosGenerados();
                    break;
                case 5:

                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Gracias por escogernos");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Por favor escoja una opción correcta");
                    break;
            }

        }while(opciones != 6);

    }//Dont't touch

    /**
     * Muestra el rango actual de números generados al usuario
     * */
    private static void mostrarRango(){
        JOptionPane.showMessageDialog(null,"El número menor es"+limiteInferior);
        JOptionPane.showMessageDialog(null,"El número mayor es"+limiteSuperior);
    }

    /**
     * Establece un nuevo rango de números a generar, ingrewsando por el usuario
     * */

    private static void establecerRango(){
        limiteInferior = Integer.parseInt((JOptionPane.showInputDialog("Por favor escoja el número menor")));
        limiteSuperior = Integer.parseInt((JOptionPane.showInputDialog("Por favor escoja el número mayor")));
    }

    /**
     * Genera un número aleatorio dentro del rango especificado sin repeticiones
     */
    private static void generarNumeroAleatorio(){
        int numeroAleatorio = obtenerNumeroAleatorio();

        if (numeroAleatorio == -1){
            JOptionPane.showMessageDialog(null,"Ya se han generado todos los números posibles");
        }else{
            JOptionPane.showMessageDialog(null,"El número aleatorio generado es: "+ numeroAleatorio);
        }
    }

    private static int obtenerNumeroAleatorio(){
        Random rand = new Random();
        int rango = limiteSuperior-limiteInferior+1;
        int numeroAleatorio;

        if(numerosGenerados.size() == rango){
            return -1; //Valor de retorno invalido
        }
        do {
            numeroAleatorio = rand.nextInt(rango) + limiteInferior;
        } while (numerosGenerados.contains(numeroAleatorio));

        // Agregar el número generado al conjunto de números generados
        numerosGenerados.add(numeroAleatorio);

        return numeroAleatorio;
    }

    private static void buscarNumeroGenerado() {
        int numeroBuscado = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el número a buscar"));
        if (numerosGenerados.contains(numeroBuscado)) {
            JOptionPane.showMessageDialog(null, "El número " + numeroBuscado + " ha sido GENERADO  previamente.");
        } else {
            JOptionPane.showMessageDialog(null, "El número " + numeroBuscado + " NO ha sido generado aún.");
        }
    }

    /**
     * Muestra un listado de los números que ya han sido generados.
     */
    private static void verListadoNumerosGenerados() {
        StringBuilder listado = new StringBuilder("Listado de números generados:\n");
        for (int numero : numerosGenerados) {
            listado.append(numero).append("\n");
        }
        JOptionPane.showMessageDialog(null, listado.toString());
    }

    private static void impresionIMG() {
        try {
            // Nombre del archivo PDF
            String nombreArchivo = "ListadoNumerosGeneradosConImagen.pdf";

            // Crear el documento PDF
            com.itextpdf.text.Document documento = new com.itextpdf.text.Document();

            // Crear el objeto PdfWriter para escribir en el archivo PDF
            PdfWriter.getInstance(documento, new FileOutputStream(new File(nombreArchivo)));

            // Abrir el documento para agregar contenido
            documento.open();

            // Agregar la imagen al documento
            Image imagen = Image.getInstance("ruta/a/la/imagen.jpg"); // Reemplaza con la ruta de tu imagen
            Rectangle pageSize = new Rectangle(imagen.getWidth(), imagen.getHeight());
            documento.setPageSize(pageSize);
            documento.newPage();
            documento.add(imagen);

            // Agregar el contenido al documento (números generados)
            for (int numero : numerosGenerados) {
                documento.add(new com.itextpdf.text.Paragraph(String.valueOf(numero)));
            }

            // Cerrar el documento
            documento.close();

            // Notificar que se ha generado el PDF
            JOptionPane.showMessageDialog(null, "El listado de números generados con imagen se ha impreso en el archivo: " + nombreArchivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al imprimir el listado en PDF: " + e.getMessage());
        }
    }



}//Don't touch