import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;

/*
 * 
 *
 * 
 */

/**
 *
 * @author Greg
 */
public class VectorGeneratorUnitTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        VFI_Map.Init();
        //String directory = System.getProperty("user.dir");
        //String imagePath = directory + "\\images\\TestScreenshot.PNG";
        //System.out.println("Finding Image at " + imagePath);
        //BufferedImage testImage = null;
        //try {
        //    testImage = ImageIO.read(new File(imagePath));
        //} catch (IOException e) {
        //    System.out.println("Failed to load file.");
        //}
        
        ImageProcessor processor = new ImageProcessor();
		processor.processEntireSelection();
    }
}
