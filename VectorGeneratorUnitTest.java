
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        String directory = System.getProperty("user.dir");
        String imagePath = directory + "\\src\\TestScreenshot.PNG";
        System.out.println("Finding Image at " + imagePath);
        BufferedImage testImage = null;
        try {
            testImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Failed to load file.");
        }
        
        writeFullFile(testImage);
    }
    
    public static void writeFullFile(BufferedImage imageToProcess) {
        
        int lineCount = 1;


        String directory = System.getProperty("user.dir");

        //If a directory is chosen
        if(!directory.equals(""))
        {
           String fullpath = directory + "/test" + "AllColor.csv";

           try
           {
              File file = new File(fullpath);
              PrintWriter printWriter = new PrintWriter(file);
              //Build the header of the spreadsheet file
              StringBuilder sb = new StringBuilder();
              sb.append("Line");
              sb.append(',');
              sb.append("X");
              sb.append(",");
              sb.append("Y");
              sb.append(",");
              sb.append("Vx");
              sb.append(",");
              sb.append("Vy");
              sb.append(",");
              sb.append("Speed");
              sb.append('\n');

              printWriter.write(sb.toString());

              for(int i = 0; i < imageToProcess.getHeight(); i++)
              {
                 for(int j = 0; j < imageToProcess.getWidth(); j++)
                 {
                    Color c = new Color(imageToProcess.getRGB(j, i));
                    int color = imageToProcess.getRGB(j, i);
                    float[] hsv = new float[3];

                    Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);
                    //If the color is not gray scale (aka "is color")
                    if((hsv[1] > 0.2)&&(hsv[2] > 0.2))
                    {
                       double Vx = VFI_Map.getVx(color);
                       double Vy = VFI_Map.getVy(color);
                       double velocity = VFI_Map.getVelocity(color);
                       printWriter.write(lineCount + ", " + j + ", " + i + ", " + truncate(Vx) + ", " + truncate(Vy) + ", " + truncate(velocity) + "\n");
                       lineCount++;
                    }
                 }
              }
              printWriter.close();
           }
           catch(FileNotFoundException e)
           {
              System.out.println("Error when creating file");
           }

           System.out.println("Finished writing file");
        }
        else
        {
           System.out.println("No directory chosen (file was not written)");
        }
     }
   
    public static double truncate(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        String trunc = df.format(value);

        return Double.parseDouble(trunc);
    }   
}
