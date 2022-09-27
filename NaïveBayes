import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class NaiveBaynes {
	private int[][] data;
	private int featureNumber;
	private int dataPoints;
	private LinkedList<Integer> labels;
	private HashMap<Integer, LinkedList<Integer>> features;

	public NaiveBaynes(int[][] data) {
		this.data = data;
		this.featureNumber = this.data[0].length - 1;
		this.dataPoints = this.data.length;

		this.getLabels();
		this.getFeatures();
		this.generateTables();
	}

	private void getLabels() {
		// get set of all labels in the data provided
		LinkedList<Integer> labels = new LinkedList<Integer>();

		for (int i = 0; i < this.dataPoints; i++) {
			int label = this.data[i][this.featureNumber];
			if (!labels.contains(label)) {
				labels.push(label);
			}
		}

		this.labels = labels;
	}

	private void getFeatures() {
		// get set of all values for each feature in order to generate probability
		// tables
		HashMap<Integer, LinkedList<Integer>> features = new HashMap<Integer, LinkedList<Integer>>();

		for (int[] dataPoint : this.data) {
			for (int i = 0; i < this.featureNumber; i++) {
				int feature = dataPoint[i];

				// initialise linked lists for all dictionary keys
				features.putIfAbsent(i, new LinkedList<Integer>());

				// add new feature to linked list if it is not in it yet
				if (!features.get(i).contains(feature)) {
					LinkedList<Integer> featureList = features.get(i);
					featureList.push(feature);
					features.put(i, featureList);
				}
			}
		}

		this.features = features;
	}

	private void generateTables() {
		// Probability tables represented using linked list and two nested hash maps
		// the linked list index corresponds to the feature index
		// the first hash map contains keys for the possibilities of each feature
		// second hash map contains keys for possibilities of each label

		LinkedList<HashMap<Integer, HashMap<Integer, Double>>> probabilityTables = new LinkedList<HashMap<Integer, HashMap<Integer, Double>>>();

		// initialise all table entries to zero
		for (int i = 0; i < featureNumber; i++) {
			HashMap<Integer, HashMap<Integer, Double>> table = new HashMap<Integer, HashMap<Integer, Double>>();
			LinkedList<Integer> featureOptions = this.features.get(i);

			for (Integer option : featureOptions) {
				HashMap<Integer, Double> counts = new HashMap<Integer, Double>();

				for (Integer label : this.labels) {
					counts.put(label, 0.0);
				}

				table.put(option, counts);
			}

			probabilityTables.push(table);
		}

		// get probabilities for each feature variant by dividing total number of
		// occurences by number of data points
		for (int[] dataPoint : this.data) {
			int label = dataPoint[this.featureNumber];

			for (int i = 0; i < this.featureNumber; i++) {
				HashMap<Integer, HashMap<Integer, Double>> featureTable = probabilityTables.get(i);
				Integer variant = dataPoint[i];

				// add one to count of occurences of specific feature variant and divide by number of data points to get new probability
				HashMap<Integer, Double> countTable = featureTable.get(variant);
				double currentTotal = countTable.get(label) * this.dataPoints; //mutiply probability by data point number to get total
				countTable.put(label, (currentTotal + 1) / this.dataPoints);

				featureTable.put(variant, countTable);
				probabilityTables.set(i, featureTable);
			}
		}
		
		System.out.println(probabilityTables);
	}
	
	
	private double P(int x) {
		return -1.0;
	}
	
	
	private double PXgivenY(int x, int y) {
		return -1.0;
	}
	


	
	private void getProbability(int[] dataPoint) {
		if (dataPoint.length != this.featureNumber) {
			System.out.println("The datapoint has invalid dimensions");
		}
	}

	public void showLabels() {
		System.out.println(this.labels);
	}

	public static void main(String[] args) {
		// colour: red(0), yellow(1)
		// type: sports(0), SUV(1)
		// origin: domestic(0), imported(1)
		// stolen: yes(1), no(0)

		int[][] data = { { 0, 0, 0, 1 }, 
				         { 0, 0, 0, 0 }, 
				         { 0, 0, 0, 1 }, 
				         { 1, 0, 0, 0 }, 
				         { 1, 0, 1, 1 }, 
				         { 1, 1, 1, 0 },
				         { 1, 1, 1, 1 }, 
				         { 1, 1, 0, 0 }, 
				         { 0, 1, 1, 0 }, 
				         { 0, 0, 1, 1 }};

		NaiveBaynes classifier = new NaiveBaynes(data);
		classifier.showLabels();
	}
}
