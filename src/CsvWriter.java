class CsvWriter {
    
    public CsvWriter() {
        
    }

	private void writeFile(ArrayList listOfFramesOfVectors) {
		String directory = System.getProperty("user.dir");
		String fullpath = directory + "/test" + "AllColor.csv";

		File file = new File(fullpath);
		PrintWriter printWriter = new PrintWriter(file);

		String headers = makeHeaders();
		printWriter.write(headers);

		for (int i = 0; i < listOfFramesOfVectors.size(); i++) {
			ArrayList listOfVectors = listOfFramesOfVectors(i);
			for (int j = 0; j < listOfVectors.size(); j++) {
				Map<String, Double> vector = listOfVectors(j);

			}
		}


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