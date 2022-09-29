import java.util.LinkedList;
import java.util.Random;

public class GeneticAlgorithm {
	private final int maxIterations;
	private final int initialSize;
	private final Random randomizer;
	private LinkedList<double[]> instances;
	int mutationChance;
	

	public GeneticAlgorithm(int maxIterations, int initialSize, int mutationChance) {
		this.maxIterations = maxIterations;
		this.initialSize = initialSize;
		this.randomizer = new Random();
		this.instances = new LinkedList<double[]>();
		this.mutationChance = mutationChance;

		this.createInitialPopulation();
		this.sortInstances();
	}

	
	// function to be minimised
	public double F(double x, double y) {
		return (Math.pow(x, 2) + (x * y) + Math.pow(y, 2) + y);
	}

	
	private void createInitialPopulation() {
		// create initial population
		for (int i = 0; i < initialSize; i++) {
			double[] instance = new double[2];
			for (int x = 0; x < 2; x++) {
				instance[x] = this.randomizer.nextFloat() * 100 * Math.pow(-1, this.randomizer.nextInt()); // generate
																											// random in
																											// range
																											// (-100,100)
			}

			this.instances.add(instance);
		}
	}
	
	
	private double fitnessFunction(double[] instance) {
		return this.F(instance[0], instance[1]);
	}

	
	private void sortInstances() {
		//use bubble sort to sort instances in descending order
		boolean isSorted = false;
		
		while (!isSorted) {
			isSorted = true;
			
			for (int i = 0; i < this.instances.size() - 1; i ++) {
				double[] currentInstance = this.instances.get(i);
				double[] nextInstance = this.instances.get(i + 1);
				
				if (this.fitnessFunction(currentInstance) < this.fitnessFunction(nextInstance)) {
					isSorted = false;
					this.instances.set(i, nextInstance);
					this.instances.set(i + 1, currentInstance);
				}
			}
		}
	}
	
	
	private double[] cross(double[] instance1, double[] instance2) {
		double[] child = new double[instance1.length];
		//average position to breed instances
		
		for (int i = 0; i < instance1.length; i ++) {
			child[i] = (instance1[i] + instance2[i]) / 2;
		}
		
		return child;
	}
	
	
	private void mutate() {
		//add random mutations to each instance to create new instances
		//this will ensure that the solution space is covered fully
		int randomNumber = this.randomizer.nextInt(this.mutationChance);
		
		if (randomNumber == 1) {
			double[] bestCandidate = this.instances.get(0);
			double[] child = {bestCandidate[0] + 100, bestCandidate[1] + 100};
			this.instances.add(child);
		}
	}
	
	

	
	public void optimize() {
		//Use top 30% of population for breeding
		for (int iteration = 0; iteration < this.maxIterations; iteration++) {
			double bestFitness = this.fitnessFunction(this.instances.get(0));
			System.out.println("Current Fitness: " + bestFitness);
			System.out.println(this.instances);
			
			System.out.println("Breeding ...");
			double[] candidate1 = this.instances.get(0);
			double[] candidate2 = this.instances.get(1);
			this.instances.add(this.cross(candidate1, candidate2));
			
			
			System.out.println("Mutating ...");
			this.mutate();
			
			System.out.println("Sorting ...");
			this.sortInstances(); //re-sort instances after cross over and mutation 
		}
	}
	
	
	public static void main(String[] args) {
		GeneticAlgorithm GA = new GeneticAlgorithm(100, 5, 100);
		GA.optimize();
	}
}
