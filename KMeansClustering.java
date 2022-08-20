import java.util.LinkedList;
import java.util.Random;

public class KMeansClustering {
	Random randomizer = new Random();
	int[][] points;
	int k;
	int maxIterations;

	// array of linked lists since number of clusters is known
	// but number of elements in each cluster is not known
	LinkedList<int[]>[] centroids;

	@SuppressWarnings("unchecked")
	public KMeansClustering(int[][] points, int k, int maxIterations) {
		this.points = points;
		this.k = k;
		this.centroids = (LinkedList<int[]>[]) new LinkedList<?>[this.k];

		this.initialiseCentroids(true);
	}

	private void initialiseCentroids(boolean addRandom) {
		// allocate memory for each linked list
		for (int i = 0; i < this.k; i++) {
			this.centroids[i] = new LinkedList<int[]>();

			if (addRandom) {
				// add a random pair of points to each centroid to start
				for (int x = 0; x < 2; x++) {
					int[] randomPoint = this.points[this.randomizer.nextInt(this.points.length)];
					this.centroids[i].add(randomPoint);
				}
			}
		}
	}

	public void iterate() {
		for (int iteration = 0; iteration < this.maxIterations; iteration ++) {
			int[][] centroidPositions = new int[this.k][2];

			// calculate new positions of centroids by averaging position of all points
			for (int i = 0; i < this.k; i++) {
				int totalX = 0;
				int totalY = 0;
				LinkedList<int[]> centroidList = this.centroids[i];

				for (int x = 0; x < centroidList.size(); x++) {
					int[] centroid = centroidList.get(x);
					totalX += centroid[0];
					totalY += centroid[1];
				}

				centroidPositions[i][0] = totalX / centroidList.size();
				centroidPositions[i][1] = totalY / centroidList.size();
			}

			// reset the centroid lists to empty lists
			this.initialiseCentroids(false);

			// calculate new distances between each point and centroid and move points to
			// closest centroid
			for (int i = 0; i < this.points.length; i++) {
				double lowestDistance = Double.POSITIVE_INFINITY;
				int lowestIndex = 0; // index of centroid closest to point
				int[] point = this.points[i];

				for (int x = 0; x < this.k; x++) {
					int[] centroid = centroidPositions[x];
					// use squared euclidean distance as distance metric
					double distanceSquared = Math.pow(point[0] - centroid[0], 2) + Math.pow(point[1] - centroid[1], 2);

					if (distanceSquared < lowestDistance) {
						lowestIndex = x;
						lowestDistance = distanceSquared;
					}
				}

				this.centroids[lowestIndex].add(point);
			}
		}
	}

	public void displayResults() {
		String output = "";

		for (int i = 0; i < this.centroids.length; i++) {
			output += "Group " + i + ":\n";
			LinkedList<int[]> group = this.centroids[i];

			for (int x = 0; x < group.size(); x++) {
				int[] point = group.get(x);
				output += "Point(" + point[0] + ", " + point[1] + ")\n";
			}

			output += "\n\n";
		}

		System.out.println(output);
	}

	public static void main(String[] args) {
		int[][] points = { { 1, 1 }, { 6, 5 }, { 2, 1 }, { 5, 5 }, { 1, 0 }, { 5, 3 }, { 2, 0 }, { 5, 4 } };

		KMeansClustering kMeans = new KMeansClustering(points, 2, 10);
		kMeans.iterate();
		kMeans.displayResults();
	}
}
