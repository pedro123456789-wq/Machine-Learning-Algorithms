//Linear Regression implementation using gradient descent
//involves calculating cost function and computing partial derivatives to maximize parameters of prediction

//model: y = mx + a, where y = prediction, x = data point, m and a are parameters to be optimized

public class LinearRegression {
	//initialize m and c to random values	
	private int m = 1;
	private int a = 1;
	private double[][] dataPoints;
	private double learningRate;
	private double lastCost;
	
	public LinearRegression(double[][] dataPoints, double learningRate) {
		this.dataPoints = dataPoints;
		this.learningRate = learningRate;
		this.lastCost = Double.POSITIVE_INFINITY;
	}
	
	public double predict(double value) {
		return (this.m * value) + this.a;
	}
	
	public double calculateError(double prediction, double value) {
		return value - prediction;
	}
	
	public double calculateCost() {
		//C = (E(x1) + E(x2) + ... + E(xn)) / n		(average of all errors)
		double cost = 0;
		
		for (int i = 0; i < this.dataPoints.length; i ++) {
			double xi = this.dataPoints[i][0];
			double yi = this.dataPoints[i][1];
			double prediction = this.predict(xi);
			cost += Math.pow(this.calculateError(prediction, yi), 2);
		}
		
		return cost / this.dataPoints.length;
	}
	
	public void gradientDescent() {
		double dcBydm = 0; //partial derivative of c w.r.t m 
		double dcByda = 0; //partial derivative of c w.r.t a
		
		for (int i = 0; i < this.dataPoints.length; i ++) {
			double xi = this.dataPoints[i][0];
			double yi = this.dataPoints[i][1];
			
			double prediction = this.predict(xi);
			double error = this.calculateError(prediction, yi);
			
			dcBydm += error * xi;
			dcByda += error;
		}
		
		
		dcBydm = dcBydm * (-2.0 / this.dataPoints.length);
		dcByda = dcByda * (-2.0 / this.dataPoints.length);
		
		this.m -= (this.learningRate * dcBydm);
		this.a -= (this.learningRate * dcByda);
	}
	
	
	public void stochasticGradientDescent() {
		{}
	}
	
	
	public void train(int maxIterations) {
		double currentCost = this.calculateCost();
		int iterations = 0;
		
		//keep performing gradient descent until a minimum is found and the cost value converges
		while (iterations < maxIterations && currentCost < this.lastCost) {
			this.lastCost = currentCost;
			this.gradientDescent();
			currentCost = this.calculateCost();
			iterations ++;
		}
		
		System.out.println("Finished Training model");
		System.out.println("Iterations: " + iterations);
		System.out.println("Error: " + currentCost);
		System.out.println("Model: Y = " + this.m + "x + " + this.a);
	}
	
	
	public static void main(String[] args) {
		//example data		
		double[][] dataPoints = {{1, 2}, {2, 4}, {3, 6}, {4, 9}, {5, 10}, {6, 13}, {7, 19}, {8, 17}, {9, 20}, {11, 33}};
		LinearRegression regressor = new LinearRegression(dataPoints, 0.01);
		regressor.train(100);
		
		//-----Expected output-----
		//Finished Training model
        //Iterations: 2
		//Error: 12.1
		//Model: Y = 2x + 1
	}
}
