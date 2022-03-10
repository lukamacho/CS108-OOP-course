import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.awt.*;


 public class SudokuFrame extends JFrame {
	private JTextArea puzzleText;
	private JTextArea solution;
	private JButton check;
	private JCheckBox autoCheck;
	private Box controlPanel;
	public SudokuFrame() {
		super("Sudoku Solver");
		
		setLayout(new BorderLayout(4,4));
		puzzleText = new JTextArea(15,20);
		puzzleText.setBorder(new TitledBorder("Puzzle"));
		add(puzzleText,BorderLayout.CENTER);

		solution = new JTextArea(15,20);
		solution.setBorder(new TitledBorder("Solution"));
		add(solution,BorderLayout.EAST);

		paintLowerLine();

		// Could do this:
		// setLocationByPlatform(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		addActionListeners();
		setVisible(true);
	}
	 private void paintLowerLine(){
		 controlPanel = new Box(BoxLayout.X_AXIS);
		 add(controlPanel,BorderLayout.SOUTH);
		 check = new JButton("Check");
		 controlPanel.add(check);

		 autoCheck = new JCheckBox("Auto check",true);
		 controlPanel.add(autoCheck);
	 }
	 private void addActionListeners() {
		 puzzleText.getDocument().addDocumentListener(new DocumentListener() {
			 @Override
			 public void insertUpdate(DocumentEvent documentEvent) {
				 if(autoCheck.isSelected()){
				 	writeSolution();
				 }
			 }

			 @Override
			 public void removeUpdate(DocumentEvent documentEvent) {
			 	if(autoCheck.isSelected()){
			 		writeSolution();
				}
			 }

			 @Override
			 public void changedUpdate(DocumentEvent documentEvent) {
				if(autoCheck.isSelected()){
					writeSolution();
				}
			 }
		 });
	 }

	 private void writeSolution() {
		 String result ="";
		 try{
		 	Sudoku sudoku = new Sudoku(Sudoku.textToGrid(puzzleText.getText()));
		 	int solutions = sudoku.solve();
		 	if(solutions>0){
		 		result = sudoku.getSolutionText();
		 		result += "\n";
		 		result += (solutions + "solutions \n");
		 		result += ("Elapsed: " + sudoku.getElapsed() + "ms \n");
			}
		 }catch(Exception exception){
				result = "You have entered illegal formatted string";
		 }
		 solution.setText(result);
	 }


	 public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
		
		SudokuFrame frame = new SudokuFrame();
		frame.setVisible(true);
	}

}
