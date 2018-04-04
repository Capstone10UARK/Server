import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.lang.*;

class ImageProcessor extends Thread {
    private final String IMAGE_DIRECTORY_PATH = "/images/testerImages";

    private String outputPath;
    private ProgressWrapper progress;
    private VFI_Map vfi;

    public ImageProcessor(String outputPathPassed, ProgressWrapper progressPassed) throws IOException {
    	vfi = new VFI_Map();
		outputPath = outputPathPassed;
        progress = progressPassed;
    }

    public void run(){
        try {
        	CsvWriter writer = new CsvWriter(outputPath);
    	    processEntireSelection(writer);
        } catch (IOException e) {
            System.out.println("couldn't start processing");
        }
    }

	private void processEntireSelection(CsvWriter writer) throws IOException{
		long startTime = System.currentTimeMillis();

		File[] listOfImages = getListOfImages();

		for (int frameIndex = 0; frameIndex < listOfImages.length; frameIndex++) {
			File imageFile = listOfImages[frameIndex];
			BufferedImage loadedImage = null;

			try {
				loadedImage = ImageIO.read(imageFile);
				Thread thread = new Thread(new SingleImageRunnable(vfi, writer, frameIndex, loadedImage));

			} catch (IOException e) {
			    System.out.println("Failed to load file index " + Integer.toString(frameIndex));
			}
			progress.setProgress((float)(frameIndex+1) / listOfImages.length);
		}

		printProcessingTime(startTime);
	}

	private File[] getListOfImages() {
		String directory = System.getProperty("user.dir");
		File parentDirectory = new File(directory).getParentFile();
		
		File directoryOfImages = new File(parentDirectory.getAbsolutePath() + IMAGE_DIRECTORY_PATH);
		File[] listOfImages = directoryOfImages.listFiles();
		return listOfImages;
	}

	private void printProcessingTime(long startTime) {
		long processingTimeMs = System.currentTimeMillis() - startTime;
		long processingTimeS = processingTimeMs / 1000;
		long processingTimeMin = processingTimeS / 60;
		processingTimeS = processingTimeS % 60;
		System.out.println("Processing took " + processingTimeMin + " minutes and " + processingTimeS + " seconds.");
	}
	

}
