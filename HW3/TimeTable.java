package mechanics.hw3;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.*;

public class TimeTable extends JFrame implements ActionListener {

	private JPanel screen = new JPanel(), tools = new JPanel();
	private JButton tool[];
	private JTextField field[];
	private CourseArray courses;
	private Color CRScolor[] = {Color.RED, Color.GREEN, Color.BLACK};

	private Autoassociator autoassociator;

	private int globalMin = Integer.MAX_VALUE;
	
	public TimeTable() {
		super("Dynamic Time Table");
		setSize(500, 800);
		setLayout(new FlowLayout());
		
		screen.setPreferredSize(new Dimension(400, 800));
		add(screen);
		
		setTools();
		add(tools);
		
		setVisible(true);
	}
	
	public void setTools() {
		String capField[] = {"Slots:", "Courses:", "Clash File:", "Iters:", "Shift:", "TimeSlot"};
		field = new JTextField[capField.length];
		
		String capButton[] = {"Load", "Start", "Cont", "TStart" ,"Step", "Print", "TSlot", "Train", "Exit"};
		tool = new JButton[capButton.length];
		
		tools.setLayout(new GridLayout(2 * capField.length + capButton.length, 1));
		
		for (int i = 0; i < field.length; i++) {
			tools.add(new JLabel(capField[i]));
			field[i] = new JTextField(5);
			tools.add(field[i]);
		}
		
		for (int i = 0; i < tool.length; i++) {
			tool[i] = new JButton(capButton[i]);
			tool[i].addActionListener(this);
			tools.add(tool[i]);
		}
		
		field[0].setText("17");
		field[1].setText("381");
		field[2].setText("lse-f-91.stu");
		field[3].setText("1");
	}
	
	public void draw() {
		Graphics g = screen.getGraphics();
		int width = Integer.parseInt(field[0].getText()) * 10;
		for (int courseIndex = 1; courseIndex < courses.length(); courseIndex++) {
			g.setColor(CRScolor[courses.status(courseIndex) > 0 ? 0 : 1]);
			g.drawLine(0, courseIndex, width, courseIndex);
			g.setColor(CRScolor[CRScolor.length - 1]);
			g.drawLine(10 * courses.slot(courseIndex), courseIndex, 10 * courses.slot(courseIndex) + 10, courseIndex);
		}
	}
	
	private int getButtonIndex(JButton source) {
		int result = 0;
		while (source != tool[result]) result++;
		return result;
	}
	
	public void actionPerformed(ActionEvent click) {
		int min, step, clashes;
		
		switch (getButtonIndex((JButton) click.getSource())) {
			case 0: //load
				int slots = Integer.parseInt(field[0].getText());
				courses = new CourseArray(Integer.parseInt(field[1].getText()) + 1, slots);
				courses.readClashes(field[2].getText());
				draw();
				autoassociator = new Autoassociator(courses);
				break;
			case 1: //start
				min = Integer.MAX_VALUE;
				step = 0;
				for (int i = 1; i < courses.length(); i++) courses.setSlot(i, 0);
				for (int iteration = 1; iteration <= Integer.parseInt(field[3].getText()); iteration++) {
					courses.iterate(Integer.parseInt(field[4].getText()));
					draw();
					clashes = courses.clashesLeft();
					if (clashes < min) {
						min = clashes;
						step = iteration;
					}
				}

				globalMin = step;
				System.out.println("Shift = " + field[4].getText() + "\tMin clashes = " + min + "\tat step " + step);
				setVisible(true);
				break;
			case 2: //continue (same as start, without setting the slots to 0)
				min = Integer.MAX_VALUE;
				step = 0;

				//don't set the slot to 0

				for (int iteration = 1; iteration <= Integer.parseInt(field[3].getText()); iteration++) {
					courses.iterate(Integer.parseInt(field[4].getText()));
					draw();
					clashes = courses.clashesLeft();
					if (clashes < min) {
						min = clashes;
						step = iteration;
					}
				}
				globalMin = step;
				System.out.println("Shift = " + field[4].getText() + "\tMin clashes = " + min + "\tat step " + step);
				setVisible(true);
				break;
			case 3: //trained start
				min = Integer.MAX_VALUE;
				step = 0;
				for (int i = 1; i < courses.length(); i++) courses.setSlot(i, 0);
				for (int iteration = 1; iteration <= Integer.parseInt(field[3].getText()); iteration++) {
					courses.iterate(Integer.parseInt(field[4].getText()));
					draw();
					clashes = courses.clashesLeft();
					if (clashes < min) {
						min = clashes;
						step = iteration;
					}

					if(Math.random() < 0.25){
						int[] neurons = new int[courses.length()];
						for(int i = 1; i < neurons.length; i++){
							neurons[i] = courses.slot(i);
						}
						autoassociator.unitUpdate(neurons);
						System.out.println("autoassociation!");

						for(int i = 1; i < neurons.length; i++){
							courses.setSlot(i, neurons[i]);
						}
					}
				}




				globalMin = step;
				System.out.println("Shift = " + field[4].getText() + "\tMin clashes = " + min + "\tat step " + step);
				setVisible(true);
				break;
			case 4: //step
				courses.iterate(Integer.parseInt(field[4].getText()));
				draw();
				break;
			case 5: //print
				System.out.println("Slot\tclasses\tClashes");
				for(int slot = 0; slot < Integer.parseInt(field[0].getText()); slot++){
					int classes = 0;
					int clashAmount = 0;
					for (int i = 1; i < courses.length(); i++){

						if(courses.slot(i) == slot){
							classes += 1;
							clashAmount += courses.status(i);
						}
					}
					System.out.println(slot + "\t\t" + classes + "\t\t" + clashAmount + "\t");

				}

				break;
			case 6: //print specific time slot
				int timeSlot = Integer.parseInt(field[5].getText());
				System.out.println("Slots = " + field[0].getText() + "\t\t" + "Shift = " + field[4].getText() + "\t\t"+ "iteration Index = " + globalMin + "\t\t" + "time slot = " + timeSlot);
				for (int i = 1; i < courses.length(); i++){
					if (courses.slot(i) == timeSlot) System.out.print(1 + " ");
					else System.out.print(-1 + " ");
				}
				System.out.println();
				break;
			case 7: //train
				String filename = field[2].getText().substring(0, field[2].getText().length() - 4) + "-timeslots.txt";


				try{
					BufferedReader data = new BufferedReader(new FileReader(filename));
					String s = "a";
					data.readLine();
					while(s != null){
						String[] arr = data.readLine().split(" ");
						s = data.readLine();
						int pattern[] = new int[arr.length];
						for(int i = 0; i < arr.length; i++){
							pattern[i] = Integer.parseInt(arr[i]);
						}
						autoassociator.training(pattern);
					}
					System.out.println("successfully trained!");



				}
				catch (Exception e){
					System.out.println("you need to load the file first");
				}

				break;
			case 8: //exit
				System.exit(0);
		}

	}

	public static void main(String[] args) {
		new TimeTable();
	}
}
