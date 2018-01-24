import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.text.DecimalFormat;
import java.io.IOException;
import java.io.File;

class ImageProcessor {
    private static final String IMAGE_DIRECTORY_PATH = "\\images\\testerImages";

    public ImageProcessor() throws IOException {
        VFI_Map.Init();
    }

	public void processEntireSelection() {
		String directory = System.getProperty("user.dir");
		File parentDirectory = new File(directory).getParentFile();
		System.out.println(parentDirectory);
		
		File directoryOfImages = new File(parentDirectory.getAbsolutePath() + IMAGE_DIRECTORY_PATH);
		System.out.println(directoryOfImages.getAbsolutePath());
		File[] listOfImages = directoryOfImages.listFiles();
		
		for (int i = 0; i < listOfImages.length; i++) {
			File imageFile = listOfImages[i];
			System.out.println(imageFile.getAbsolutePath());
		}


	}
	
	private ArrayList<Map> processSingleImage(BufferedImage imageToProcess) {
        
		ArrayList<Map> listOfVectors = new ArrayList<Map>();

		for(int y = 0; y < imageToProcess.getHeight(); y++)
		{
			for(int x = 0; x < imageToProcess.getWidth(); x++)
			{
				Color c = new Color(imageToProcess.getRGB(x, y));
				int color = imageToProcess.getRGB(x, y);
				float[] hsv = new float[3];

				Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);
				//If the color is not gray scale (aka "is color")
				if((hsv[1] > 0.2)&&(hsv[2] > 0.2))
				{
					HashMap vector = new HashMap();
					vector.put("x", x);
					vector.put("y", y);
					vector.put("Vx", truncate(VFI_Map.getVx(color)));
					vector.put("Vy", truncate(VFI_Map.getVy(color)));
					vector.put("speed", truncate(VFI_Map.getVelocity(color)));
					listOfVectors.add(vector);
				}
			}
		}
		System.out.println("Finished processing single image");
		
		return listOfVectors;
	}
   
    private double truncate(double value) {
		DecimalFormat df = new DecimalFormat("#.##");
		String trunc = df.format(value);

		return Double.parseDouble(trunc);
    }   
}