 /**
  *
  * @author cvizzarri
  */


//#Paso 1
//generar el archivo de texto que va contener la ruta a cada uno de los pdf guardados en asserstores
//desde este archivo va leer el codigo java para aplicar los metodos
//#Ejecutar lo siguiente en el bash de cosola linux, colocar path a la carpeta donde dspace guarda los pdf
//#find RUTA_A_/assetstore/ -type f -print > RUTA_A_DONDE_GUARD0_LA SALIDA_de_FIND

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;
import pdfbox.PDFAFile;

public class TestPDFA extends PDFAFile{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            
            int  count=0,pros=0;
            String sChain;
            String sFile="/home/cvizzarri/Descargas/salida.txt";
           // String sFile="/home/cvizzarri/NetBeansProjects/TestPDFA3/File";
            BufferedReader bf = new BufferedReader(new FileReader(sFile));
            BufferedReader bf1 = new BufferedReader(new FileReader(sFile));
            long lNumeroLineas = 0;     
              while ((sChain = bf1.readLine())!=null) {     
                 lNumeroLineas++;
                } 
             System.out.println(lNumeroLineas);
             
            while ((sChain = bf.readLine())!=null) {     

                count++;
                Path file = Paths.get(sChain);                
                if("application/pdf".equals(Files.probeContentType(file))) {
                    System.out.println("path-> "+sChain);
                    PDFAFile pdfa = new PDFAFile();
                    pdfa.doIt(sChain);
                    Thread.sleep(10);
                    //PDFAFile.main(new String[] { sChain });
                    //System.out.println(count);
                    pros++;
                    System.out.println("Completado: "+ ((float)count/lNumeroLineas)*100 +"%");
                }
                
            }
            
            System.out.println("Total de archivos-> "+lNumeroLineas);
            System.out.println("Total de archivos procesados-> "+count);
            
        } catch (IOException ex) {
            Logger.getLogger(TestPDFA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestPDFA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
