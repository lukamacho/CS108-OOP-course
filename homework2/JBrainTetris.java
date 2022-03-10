import  java.awt.*;
import  javax.swing.*;

public class JBrainTetris extends JTetris {

    private JCheckBox brainPosition;
    private JSlider adversary;
    private int count;
    private Brain.Move brainMove;
    private DefaultBrain defaultBrain;
    private JLabel okLabel;
    /**
     * Creates a new JTetris where each game square
     * is drawn with the given number of pixels.
     *
     * @param pixels
     */
    JBrainTetris(int pixels) {
        super(pixels);
        defaultBrain = new DefaultBrain();
    }

    @Override
    public void tick(int verb){
        if(brainPosition.isSelected() && verb == DOWN){
            if(count != super.count){
                board.undo();
                count = super.count;
                brainMove = defaultBrain.bestMove(board,currentPiece,board.getHeight(),brainMove);
            }
            bestPosition();
        }
        super.tick(verb);
    }
    /**
     * This function chooses the best position to gain points.
     * */
    private void bestPosition(){
        if(brainMove != null){
            if(!currentPiece.equals(brainMove.piece)){
                super.tick(ROTATE);
            }
            if(currentX < brainMove.x) {
                super.tick(RIGHT);
            }else if(currentX < brainMove.x){
                super.tick(LEFT);
            }
        }
    }

    @Override
    public JComponent createControlPanel(){
        JComponent window = super.createControlPanel();
        window.add(new JLabel("Brain "));
        brainPosition= new JCheckBox("Brain active");
        brainPosition.setSelected(false);
        window.add(brainPosition);

        JPanel little = new JPanel();
        little.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0); // min, max, current
        adversary.setPreferredSize(new Dimension(100,15));
        little.add(adversary);

        okLabel = new JLabel("OK");
        little.add(okLabel);
        window.add(little);
        return window;
    }

    @Override
    public Piece pickNextPiece() {
        int randomNumber = random.nextInt(100);
        if(randomNumber >= adversary.getValue()){
            okLabel.setText("ok");
            return super.pickNextPiece();
        }

        return findBestPiece();
    }
    /**
     * This function finds the best piece to gain points.
     * */
    private Piece findBestPiece(){
        Piece best = pieces[0];
        double largestScore = 0;
        for(int i=0;i<pieces.length;i++){
            brainMove = defaultBrain.bestMove(board,pieces[i],board.getHeight(),brainMove);
            if(brainMove != null && brainMove.score > largestScore){
                best = pieces[i];
                largestScore = brainMove.score;
            }
        }
        okLabel.setText("*ok");
        return  best;
    }
    public static void main(String[] args){
        JBrainTetris game = new JBrainTetris(40);
        JFrame frame = JBrainTetris.createFrame(game);
        frame.setVisible(true);
    }
}
