import java.io.File;
import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;

class CsvWriter {
    
    public CsvWriter(ArrayList<ArrayList> listOfFramesOfVectors, String outputPath) {
        try {
		writeFile(listOfFramesOfVectors, outputPath);
		} catch (IOException e) {
			System.out.println("Writing to file failed");
		}
    }

	private void writeFile(ArrayList<ArrayList> listOfFramesOfVectors, String outputPath) throws IOException {
		String directory = System.getProperty("user.dir");
		//String fullpath = directory + "/test" + "AllColor.csv";
		//path provided by gui should be an absolute path on the users machine
		String fullpath = outputPath;

		File file = new File(fullpath);
		file.getParentFile().mkdirs();
		PrintWriter printWriter = new PrintWriter(file);

		String headers = makeHeaders();
		printWriter.println(headers);

		for (int i = 0; i < listOfFramesOfVectors.size(); i++) {
			ArrayList<Map> listOfVectors = listOfFramesOfVectors.get(i);
			for (int j = 0; j < listOfVectors.size(); j++) {
				Map<String, Double> vector = listOfVectors.get(j);
				printWriter.println(i + "," + j + "," + vector.get("x") + "," + vector.get("y") + "," + vector.get("Vx") + "," + vector.get("Vy") + "," + vector.get("speed"));
			}
		}
		printWriter.close();
	}

	private String makeHeaders() {
		StringBuilder sb = new StringBuilder();
		sb.append("Frame Index");
		sb.append(',');
		sb.append("Vector Index");
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
		return(sb.toString());
	}
}
