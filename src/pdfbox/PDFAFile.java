package pdfbox;

import java.io.File;
import java.util.GregorianCalendar;
import org.apache.jempbox.xmp.XMPMetadata;
import org.apache.jempbox.xmp.XMPSchemaBasic;
import org.apache.jempbox.xmp.XMPSchemaDublinCore;
import org.apache.jempbox.xmp.pdfa.XMPSchemaPDFAId;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDMetadata;


/**
 * creates a simple PDF/A document.
 */
public class PDFAFile
{
    /**
     * Constructor.
     */
    public PDFAFile()
    {
        super();
    }
    
    /**
     * Create a simple PDF/A document.
     *
     * @param file
     *          The file to write the PDF to.
     * @throws Exception
     *           If something bad occurs
     */
    public void doIt(String file) throws Exception
    {
        // the document
        PDDocument doc = null;
        //Reads in pdf document
        PDDocument pdDoc = PDDocument.load(file);
        try
        {
            if (pdDoc.isEncrypted()){
                System.out.print("Archivo encriptado - NO procesado: ");
                System.out.println(file);
            }else{
         
            doc = new PDDocument();
            for ( int pagina = 0; pagina < pdDoc.getDocumentCatalog().getAllPages().size(); pagina ++ )
                doc.addPage((PDPage) pdDoc.getDocumentCatalog().getAllPages().get(pagina));
            
            PDDocumentCatalog cat = doc.getDocumentCatalog();
            PDMetadata metadata = new PDMetadata(doc);
            cat.setMetadata(metadata);
            
            //
            XMPMetadata xmp = new XMPMetadata();
            XMPSchemaPDFAId pdfaid = new XMPSchemaPDFAId(xmp);
            xmp.addSchema(pdfaid);          
            XMPSchemaDublinCore dc = xmp.addDublinCoreSchema();            
            String creator = "pdfbox-1.8.10";
            String producer = "PDFBOX";
            dc.addCreator(creator);
            dc.setAbout("");
            
            XMPSchemaBasic xsb = xmp.addBasicSchema();
            xsb.setAbout("");            
            xsb.setCreatorTool(creator);
            xsb.setCreateDate(GregorianCalendar.getInstance());
            
            PDDocumentInformation pdi = new PDDocumentInformation();
            pdi.setProducer(producer);
            pdi.setAuthor(creator);
            doc.setDocumentInformation(pdi);
            
            pdfaid.setConformance("A");
            pdfaid.setPart(1);
            pdfaid.setAbout("");
            metadata.importXMPMetadata(xmp.asByteArray());
            
            File fichero = new File(file);
            fichero.delete();
            //if (fichero.delete())
            //    System.out.println("El fichero ha sido borrado satisfactoriamente y se Escribira el nuevo fichero");
            //else
            //    System.out.println("El fichero no puede ser borrado");
            //System.out.println("--------------------------------------------------------");
            //System.out.println();
            
            doc.save(file);
            } 
        }
        finally
        {
            if (doc != null)
            {
                doc.close();
                pdDoc.close();
            }
        }
    }
    
    /**
     * This will create a hello world PDF/A document. <br />
     * see usage() for commandline
     *
     * @param args
     *          Command line arguments.
     */
    public static void main(String[] args)
    {
        PDFAFile app = new PDFAFile();
        try
        {
            if (args.length != 1)
            {
                app.usage();
            }
            else
            {
                app.doIt(args[0]);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * This will print out a message telling how to use this example.
     */
    private void usage()
    {
        System.err.println("usage: " + this.getClass().getName() + " <output-file>");
    }
}