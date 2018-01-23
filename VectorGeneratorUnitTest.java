
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;

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
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public void writeFullFile(BufferedImage imageToProcess) {
        
        int lineCount = 1;

        Main.alert("Choose location to save file");

        String directory = "";
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File name = fc.getSelectedFile();
            //Grab full path of directory
            directory = name.getAbsolutePath();
        }
        else
            Main.alert("Must be a directory");

        //If a directory is chosen
        if(!directory.equals(""))
        {
           String fullpath = directory + "/" + View.panel.getFrameName() + "AllColor.csv";

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
              Main.alert("Error when creating file");
           }

           Main.alert("Finished writing file");
        }
        else
        {
           Main.alert("No directory chosen (file was not written)");
        }
     }
   
    public double truncate(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        String trunc = df.format(value);

        return Double.parseDouble(trunc);
    }   
}
