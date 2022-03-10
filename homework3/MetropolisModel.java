import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class MetropolisModel extends AbstractTableModel {
    private ArrayList<ArrayList<String> > tableData;
    private static final String [] columns = {"Metropolis", "Continent", "Population"};
    private MetropolisBase metropolisBase;
    public MetropolisModel () throws SQLException, ClassNotFoundException {
        metropolisBase = new MetropolisBase("MyBase","Metropolises");
        tableData = new ArrayList<>();
    }
    @Override
    public int getRowCount() {
        return tableData.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int i, int j) {
        return tableData.get(i).get(j);
    }

    @Override
    public String getColumnName(int columnIndex){
        return columns[columnIndex];
    }

    /**
     * @param metropolis
     * @param continent
     * @param population
     * Metropolis addition procedure which gives non-null population,continent and metropolis.
     */
    public void addMetropolis(String metropolis,String continent, String population){
        int numPeople;
        try{
            numPeople = Integer.parseInt(population);
        }catch(Exception e){
            numPeople = 0;
        }
        if(!metropolis.equals("")  && !continent.equals("") && numPeople > 0){
            ArrayList<String> currentMetropolis = new ArrayList<>();
            currentMetropolis.add(metropolis);
            currentMetropolis.add(continent);
            currentMetropolis.add(population);
            tableData.clear();
            tableData.add(currentMetropolis);
            metropolisBase.addNewMetropolis(metropolis,continent,population);
            fireTableDataChanged();
        }
    }

    /**
     *
     * @param metropolis
     * @param continent
     * @param population
     * @param popFilter
     * @param exactFilter
     * @throws SQLException
     * Metropolis searching procedure which gives these parameters and screen the
     * metropolises which have all these options.
     */
    public void searchMetropolis(String metropolis, String continent, String population, boolean popFilter,boolean exactFilter) throws SQLException {
        tableData = metropolisBase.searchMetropolises(metropolis,continent,population,popFilter,exactFilter);
        fireTableDataChanged();
    }

}
