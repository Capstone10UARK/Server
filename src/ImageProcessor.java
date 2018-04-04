import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.lang.*;

class ImageProcessor extends Thread implements ThreadCompleteListener{
    private final String IMAGE_DIRECTORY_PATH = "/images/testerImagesFullVideo";

    private String outputPath;
    private ProgressWrapper progress;
    private VFI_Map vfi;
    private int numberOfImages;
    private int completedImages = 0;
    private long startTime;

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
		startTime = System.currentTimeMillis();

		File[] listOfImages = getListOfImages();
        numberOfImages = listOfImages.length;

		for (int frameIndex = 0; frameIndex < numberOfImages; frameIndex++) {
			File imageFile = listOfImages[frameIndex];
			BufferedImage loadedImage = null;

			try {
				loadedImage = ImageIO.read(imageFile);
                NotifyingRunnable runner = new SingleImageRunnable(vfi, writer, frameIndex, loadedImage);
				runner.addListener(this);
                Thread thread = new Thread(runner);
                thread.start();

			} catch (IOException e) {
			    System.out.println("Failed to load file index " + Integer.toString(frameIndex));
			}
			
		}
	}

	private File[] getListOfImages() {
		String directory = System.getProperty("user.dir");
		File parentDirectory = new File(directory).getParentFile();
		
		File directoryOfImages = new File(parentDirectory.getAbsolutePath() + IMAGE_DIRECTORY_PATH);
		File[] listOfImages = directoryOfImages.listFiles();
		return listOfImages;
	}

	private void printProcessingTime() {
		long processingTimeMs = System.currentTimeMillis() - startTime;
		long processingTimeS = processingTimeMs / 1000;
		long processingTimeMin = processingTimeS / 60;
		processingTimeS = processingTimeS % 60;
		System.out.println("Processing took " + processingTimeMin + " minutes and " + processingTimeS + " seconds.");
	}
	
    public void threadComplete(Runnable runner) {
        completedImages = completedImages + 1;
        float newProgress = (float)completedImages / numberOfImages;
        progress.setProgress(newProgress);
        if(newProgress == 1)
            printProcessingTime();
    }
}
