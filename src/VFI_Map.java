import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.awt.Color;

class VFI_Map
{
   private static int mHeight; //height of color key image
   private static int mWidth; //width of color key image
   private static double Vmax;
   private static double Dmax; //maximum distance from center of color hue to edge
   private static BufferedImage image; //The actual image of the color key
   private static HashMap<Integer,Double> RGB_VelMap = new HashMap<>(); //mapping from a color to a velocity
   private static HashMap<Integer,int[]> RGB_DisMap = new HashMap<>(); //mapping from a color to a distance from the center of the key
   private static HashMap<Integer, Double> RGB_VxMap = new HashMap<>(); //mapping from a color to Vx
   private static HashMap<Integer, Double> RGB_VyMap = new HashMap<>(); //mapping from a color to Vx

   /****************************************************************************************
   //Method: findVelDis
   //Return: None (void)
   //Purpose: Use the preloaded color key to map from RGB values to velocity and distance
   //   distance = center to RGB value
   ****************************************************************************************/
   public static void findVelDis()
   {
      for(int i = 0; i < mHeight; i++)
      {
         for(int j = 0; j < mWidth; j++){
            int Dx = mWidth/2-j;
            int Dy = mHeight/2-i;
            int[] dis = {Dx, Dy};
            double d = Math.sqrt(Math.pow(Dx,2) + Math.pow(Dy,2));
            double v = (d*Vmax)/Dmax;
            double Vx = (-1)*Dx*(Vmax/Dmax);
            double Vy = Dy*(Vmax/Dmax);
            if(RGB_VelMap.containsKey(image.getRGB(j,i))){
              //System.out.println("Duplicate of " + image.getRGB(j,i) + "with velocity: " + v);
            }
            else{
              if(image.getRGB(j,i) == -16777216)
                RGB_VelMap.put(image.getRGB(j,i), 0.0);
              else
                RGB_VelMap.put(image.getRGB(j,i), v);
                RGB_DisMap.put(image.getRGB(j,i), dis);
                RGB_VxMap.put(image.getRGB(j,i), Vx);
                RGB_VyMap.put(image.getRGB(j,i), Vy);

            }
          }
      }
   }

   /****************************************************************************************
   //Method: searchMap
   //Return: RGB integer
   //Purpose: Take in the map (either color to velocity or color to distance) and the selected
   //   color from the frame and find the closest RGB value in our mapping (generated by key)
   *****************************************************************************************/
   public static int searchMap(Map mp, int color)
   {
       Iterator it = mp.entrySet().iterator();
       //minDifference = big value to make sure that the value is set when searching the map
       //Value will store the RGB color from the map that is closest to the selected color
       float minDifference = 10000000;
       int value = 0;

       //
       float[] selectedHSL = new float[3];
       Color sel = new Color(color);
       Color.RGBtoHSB(sel.getRed(), sel.getGreen(), sel.getBlue(), selectedHSL);

       while (it.hasNext())
       {
           Map.Entry pair = (Map.Entry)it.next();

           Color map = new Color((int)pair.getKey());
           float[] hsl = new float[3];
           Color.RGBtoHSB(map.getRed(), map.getGreen(), map.getBlue(), hsl);
           float difference = Math.abs(selectedHSL[0] - hsl[0]) + Math.abs(selectedHSL[1] - hsl[1]) + Math.abs(selectedHSL[2] - hsl[2]);

           if(difference < minDifference)
           {
              minDifference = difference;
              value = (int)pair.getKey();
           }
       }
       return value;
   }

   /**************************************************************************************
   //Method: getDistances
   //Return: integer array that contains 'x' distance in 0th position and 'y' distance in
   //    1st position ->  Array[x, y]
   //Purpose: Find the closest RGB to the one selected and return how far away that color
   //   is from center of key to generate proper length of vector in frame
   **************************************************************************************/
   public static int[] getDistances(int RGB){
     int close = searchMap(RGB_DisMap, RGB);
     return RGB_DisMap.get(close);
   }

   /*************************************************************************************
   //Method: getVelocity
   //Return: double velocity magnitude
   //Purpose: find the closest RGB value from the one selected and return the velocity
   *************************************************************************************/
   public static double getVelocity(int RGB){
     int close = searchMap(RGB_VelMap, RGB);
     return RGB_VelMap.get(close);
   }

   /*************************************************************************************
   //Method: getVx
   //Return: double velocity x component magnitude
   //Purpose: find the closest RGB value from the one selected and return the Vx component of velocity
   *************************************************************************************/
   public static double getVx(int RGB){
     int close = searchMap(RGB_VxMap, RGB);
     return RGB_VxMap.get(close);
   }

   /*************************************************************************************
   //Method: getVy
   //Return: double velocity y component magnitude
   //Purpose: find the closest RGB value from the one selected and return the Vy component of velocity
   *************************************************************************************/
   public static double getVy(int RGB){
     int close = searchMap(RGB_VyMap, RGB);
     return RGB_VyMap.get(close);
   }

   /************************************************************************************
   //Method: setMaxVelocity
   //Return: None (void)
   //Purpose: Tied to set max velocity button in GUI to allow user to set max velocity
   ************************************************************************************/
   public static void setMaxVelocity(double max)
   {
      Vmax = max;
      findVelDis();
   }

   /************************************************************************************
   //Method: Init
   //Return: None (void)
   //Purpose: initialize the color key from the image of the key in the "images" directory
   *************************************************************************************/
   public static void Init() throws IOException
   {
      //Preloaded key for color mapping
      File file = new File("../images/colorKey.png");
      image = ImageIO.read(file);
      mHeight = image.getHeight();
      mWidth = image.getWidth();

      //Preset the maximum velocity (offer button to set this if needed)
      Vmax = 236.5;
      //Find the maximum distance to the center black pixel using pythagorean theorem
      Dmax = Math.sqrt(Math.pow(mHeight/2,2) + Math.pow(mWidth/2,2));

      //Find Velocity for given pixel based on RGB value and the distance to the origin
      findVelDis();
   }
}
