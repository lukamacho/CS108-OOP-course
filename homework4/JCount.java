// JCount.java
/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JCount extends JPanel {
 	// Some private variables.
	private static int max_workers  = 4;
	private final JTextField num_field;
	private final int textFieldSize = 10;
	private final JLabel cur_value;
	private final JButton start,stop;
	private Worker worker = null;

	public JCount() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		num_field = new JTextField("100000000",textFieldSize);
		add(num_field);
		cur_value = new JLabel("0");
		add(cur_value);
		start = new JButton("Start");
		add(start);
		stop = new JButton("Stop");
		add(stop);
		add(Box.createRigidArea(new Dimension(0,40)));
		addActionListeners();
	}

	private void addActionListeners(){
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(worker != null && !worker.isInterrupted()) {
					worker.interrupt();
				}
				worker = new Worker(Integer.parseInt(num_field.getText()));
				worker.start();
			}
		});
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(worker != null && !worker.isInterrupted()){
					worker.interrupt();
					worker = null;
				}
			}
		});
	}

	/**
	 * Function to start all the workers to start working and show the information on the
	 * screen.
	 */
	private static void createAndShowGui(){
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		for(int i=0;i <max_workers;i++){
			frame.add(new JCount());
		}
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	static public void main(String[] args)  {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGui();
			}
		});

	}

	/**
	 * Worker thread class.
	 */
	private class Worker extends Thread{
			private final static int changeTime  = 100000;
			private final int sleepTime  = 1000;
			private int maxNum;

			public Worker(int maxNum){
				this.maxNum = maxNum;
			}

			@Override
			public void run(){
				for(int i = 0;i <= maxNum;i++){
					if(isInterrupted()){
						break;
					}
					if(i % changeTime == 0) {
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							return;
						}
						String textValue = Integer.toString(i);
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								cur_value.setText(textValue);
							}
						});
					}
				}
			}
	}
}

