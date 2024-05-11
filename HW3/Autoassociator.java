package mechanics.hw3;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Autoassociator {
	private int weights[][];
	private int trainingCapacity;

	private ArrayList<int[]> trainingSet;
	
	public Autoassociator(CourseArray courses) {
		weights = new int[courses.length()][courses.length()];

		for(int i = 0; i < courses.length(); i++){
			for(int j = 0; j < courses.length(); j++){
				if(j == i){
					weights[i][j] = 0;
					continue;
				}

				Course c1 = courses.elements[i];
				Course c2 = courses.elements[j];

				for (int f = 0; f < c1.clashesWith.size(); f++)
					if(c2 == c1.clashesWith.elementAt(f)){
						weights[i][j] = 1;
						break;
					}

				if(weights[i][j] != 1) weights[i][j] = -1;

			}
		}

		trainingCapacity = (int) (0.139 * courses.length());
		trainingSet = new ArrayList<>(trainingCapacity);
	}
	
	public int getTrainingCapacity() {
		return trainingCapacity;
	}
	
	public void training(int pattern[]) {
		trainingSet.add(pattern);
	}
	
	public int unitUpdate(int neurons[]) {
		// implements a single update step and
		// returns the index of the randomly selected and updated neuron
		int r =(int) (Math.random() * neurons.length);

		unitUpdate(neurons, r);
		
		return r;
	}
	
	public void unitUpdate(int neurons[], int index) {
		if(Math.random() < 0.05){
			float sum = 0.0f;
			for(int[] arr : trainingSet){
				sum += arr[index];
			}
			sum /= trainingSet.size();

			neurons[index] = (int) sum;
		}
		else{
			int f = -1;
			while(f == -1) f = trainingSet.get((int) (Math.random() * trainingCapacity))[index];
			neurons[index] = f;
		}
		//I don't know, just some random stuff for fun

	}
	
	public void chainUpdate(int neurons[], int steps) {
		for(int i = 0; i < steps; i++){
			unitUpdate(neurons);
		}
	}
	
	public void fullUpdate(int neurons[]) {
		while(! trainingSet.contains(neurons)){
			unitUpdate(neurons);
		}
	}
}
