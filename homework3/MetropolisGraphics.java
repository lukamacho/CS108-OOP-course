import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class MetropolisGraphics extends JFrame {
    private JTextField metropolisField, continentField, populationField;
    private JButton addButton, searchButton;
    private JComboBox populationComboBox, matchTypeComboBox;
    public static final String[] populationSearchList = {"Population larger than", "Population smaller than"};
    public static final String[] matchTypeSearchList = {"Exact match", "Partial match"};
    private int textFieldSize = 20;
    private MetropolisModel metropolisModel;


    public MetropolisGraphics () throws SQLException, ClassNotFoundException {
        super("Metropolis viewer");
        setLayout(new BorderLayout(4,4));
        paintGrid();
        upperBoard();
        boardRightPart();
        pack();
        addActionListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This function paints the upper part of the panel
     */
    private void upperBoard(){
        JPanel panel = new JPanel();
        add(panel,BorderLayout.NORTH);

        JLabel metropolis = new JLabel("Metropolis: ");
        panel.add(metropolis);
        metropolisField = new JTextField(textFieldSize);
        panel.add(metropolisField);

        JLabel continent = new JLabel("Continent: ");
        panel.add(continent);
        continentField = new JTextField(textFieldSize);
        panel.add(continentField);

        JLabel population = new JLabel("Population: ");
        panel.add(population);
        populationField = new JTextField(textFieldSize);
        panel.add(populationField);
    }

    /**
     * This function paint the right part of the board.
     */
    private void boardRightPart(){
        Box right =  Box.createVerticalBox();
        add(right,BorderLayout.EAST);

        addButton = new JButton("Add");
        right.add(addButton);

        searchButton = new JButton("Search");
        right.add(searchButton);

        Box smallBox = new Box (BoxLayout.Y_AXIS);
        smallBox.setBorder(new TitledBorder("Search option"));
        right.add(smallBox);

        populationComboBox = new JComboBox();
        populationComboBox.addItem(populationSearchList[0]);
        populationComboBox.addItem(populationSearchList[1]);
        smallBox.add(populationComboBox);

        matchTypeComboBox = new JComboBox();
        matchTypeComboBox.addItem(matchTypeSearchList[0]);
        matchTypeComboBox.addItem(matchTypeSearchList[1]);

        smallBox.add(matchTypeComboBox);

        alignButtons();
        setDimension();
    }

    /**
     * This function aligns the buttons;
     */
    private void alignButtons(){
        addButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        searchButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        populationComboBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        matchTypeComboBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
    }

    /**
     * Function to set dimensions of boxes;
     */
    private void setDimension(){
        Dimension menuSize = new Dimension(matchTypeComboBox.getMaximumSize().width,matchTypeComboBox.getPreferredSize().height);
        matchTypeComboBox.setMaximumSize(menuSize);
        populationComboBox.setMaximumSize(menuSize);

        Dimension buttonDimension = new Dimension(searchButton.getMaximumSize());
        addButton.setMaximumSize(buttonDimension);
        searchButton.setMaximumSize(buttonDimension);
    }

    /**
     * @throws SQLException
     * @throws ClassNotFoundException
     * Grid formation function.
     */
    private void paintGrid() throws SQLException, ClassNotFoundException {
        metropolisModel = new MetropolisModel();
        JTable jt = new JTable(metropolisModel);
        JScrollPane panel = new JScrollPane(jt);
        add(panel,BorderLayout.CENTER);
    }

    /**
     * Function to define buttons' procedures.
     */
    private void addActionListeners(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                    metropolisModel.addMetropolis(metropolisField.getText(),
                                                    continentField.getText(),
                                                    populationField.getText());
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean pop=(populationSearchList[0].equals((String)populationComboBox.getSelectedItem()));
                boolean match = matchTypeSearchList[0].equals((String)matchTypeComboBox.getSelectedItem());
                try {
                    metropolisModel.searchMetropolis(metropolisField.getText(),
                                                    continentField.getText(),
                                                    populationField.getText(), pop, match);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MetropolisGraphics mp = new MetropolisGraphics();
        mp.setVisible(true);
    }
}
