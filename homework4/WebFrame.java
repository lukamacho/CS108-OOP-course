import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class WebFrame extends JFrame {
    private static final int textFieldSize = 4;
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel panel;
    private String linkTexts  = "links.txt";
    private JButton stopButton, singleThreadFetchButton,concurrentFetchButton;
    private JLabel runningLabel, completeLabel ,elapseLabel;

    private JProgressBar progressBar;
    private JTextField workerNumField;

    private Launcher launcher;
    private long executionTime;
    private Semaphore workerSem;
    private AtomicInteger finishedThreads = new AtomicInteger(0);
    private AtomicInteger runningThreads = new AtomicInteger(0);
    public WebFrame(){
        super("Web Loader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        panelTableCreation();
        fillTableModel();

        initializeButtons();
        initializeLabels();
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        panel.add(stopButton);
        add(panel);
        addActionListeners();
        pack();
        setVisible(true);

    }

    /**
     * Label initialization function.
     */
    private void initializeLabels(){
        runningLabel = new JLabel("Running: ");
        panel.add(runningLabel);
        completeLabel = new JLabel("Completed: ");
        panel.add(completeLabel);
        elapseLabel = new JLabel("Elapsed: ");
        panel.add(elapseLabel);

        progressBar = new JProgressBar(0,tableModel.getRowCount());
        progressBar.setValue(0);
        panel.add(progressBar);
    }
    /**
     * Button initialization function.
     */
    private void initializeButtons(){
        singleThreadFetchButton = new JButton("Single Thread Fetch");
        panel.add(singleThreadFetchButton);

        concurrentFetchButton = new JButton("Concurrent Fetch");
        panel.add(concurrentFetchButton);

        workerNumField = new JTextField("1",textFieldSize);
        workerNumField.setMaximumSize(workerNumField.getPreferredSize());
        panel.add(workerNumField);
    }

    /**
     * This function creates the new table model class object and paints them on the screen.
     */
    private void panelTableCreation(){
        tableModel = new DefaultTableModel(new String[] { "url", "status"}, 0);
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(600,300));
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(scrollpane);
    }
    private void addActionListeners(){
        singleThreadFetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(launcher != null) {
                    launcher.interrupt();
                }
                launcherInit(false);
                changeButtonConfigurations(false);


            }
        });
        concurrentFetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(launcher != null) {
                    launcher.interrupt();
                }
                launcherInit(true);
                changeButtonConfigurations(false);
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                 if(launcher!= null){
                     launcher.interrupt();
                 }
                 launcher  = null;
                 changeButtonConfigurations(true);
            }
        });
    }

    /**
     * This function changes the button configurations
     * @param finish
     */
    private void changeButtonConfigurations(boolean finish){
        if(finish == false){
            singleThreadFetchButton.setEnabled(false);
            concurrentFetchButton.setEnabled(false);
            stopButton.setEnabled(true);
            for(int i=0;i<tableModel.getRowCount();i++){
                tableModel.setValueAt("",i,1);
            }
        }else{
            singleThreadFetchButton.setEnabled(true);
            concurrentFetchButton.setEnabled(true);
            stopButton.setEnabled(false);
            executionTime = System.currentTimeMillis();

        }
    }

    /**
     * Luancher initialization function it gives boolean multiple variable if it is true
     * program will run with multiple thread else with single thread.
     * @param multiple
     */
    private void launcherInit(boolean multiple){
        int threadNum = 0;
        if(multiple){
            try{
                threadNum=Integer.parseInt(workerNumField.getText());
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            threadNum = 1;
        }
        launcher = new Launcher(this,threadNum);
        launcher.start();
    }

    /**
     * Function free the worker and write its working string.
     * @param row
     * @param status
     */
    public void releaseWorker(int row, String status){
             SwingUtilities.invokeLater(new Runnable() {
                 @Override
                 public void run() {
                     progressBar.setValue(finishedThreads.get());
                     tableModel.setValueAt(status,row,1);
                 }
             });
            finishedThreads.incrementAndGet();
            runningThreads.decrementAndGet();
            workerSem.release();
            updateTextFields();


    }
    private void updateTextFields(){
        long already = System.currentTimeMillis() - executionTime;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runningLabel.setText("Running: " + runningThreads);
                completeLabel.setText("Completed: "+ finishedThreads);
                elapseLabel.setText("Elapsed: " + already);
            }
        });
    }
    public class Launcher extends Thread{
        private ArrayList<WebWorker> workerThreads = new ArrayList<>();
        private WebFrame webFrame;

        /**
         * Launcher constructor whic gives frame and number of workers.
         * @param frame
         * @param workerNum
         */
        public Launcher(WebFrame frame, int workerNum){
            webFrame = frame;
            workerSem = new Semaphore(workerNum);
            runningThreads = new AtomicInteger(0);
            finishedThreads = new AtomicInteger(0);
        }
        @Override
        public void run(){
            runningThreads.incrementAndGet();
            for(int i =0;i<tableModel.getRowCount();i++){
                String url  = (String)tableModel.getValueAt(i,0);
                try {
                    workerSem.acquire();
                    WebWorker curWorker = new WebWorker(url,i,webFrame);
                    workerThreads.add(curWorker);
                    runningThreads.incrementAndGet();
                    curWorker.start();
                }catch (InterruptedException e){
                   breakWorkers();
                   break;
                }
            }
            runningThreads.decrementAndGet();
            for(WebWorker w: workerThreads){
                try{
                w.join();
                }catch (InterruptedException e){
                    breakWorkers();
                }
            }
            finishWorking();
        }

        /**
         * this function interrupts all workers.
         */
        private void breakWorkers(){
            for(WebWorker w: workerThreads){
                w.interrupt();
            }
        }

        /**
         * This function finishs working of the already started threads.
         */
        private void finishWorking(){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    changeButtonConfigurations(true);
                }
            });
        }
    }

    /**
     *This function fill the table with the links of the urls.
     */
    private void fillTableModel(){
        try{
            BufferedReader bf = new BufferedReader(new FileReader(new File(linkTexts)));
            while(true){
                String cur = bf.readLine();
                if(cur==null){
                    break;
                }
                tableModel.addRow(new String[]{cur,""});
            }
            bf.close();
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WebFrame();
            }
        });
    }
}
