package dominos.model;

import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DominoCycleFinder  extends Thread{

    private List<Dice> dices;
    private List<Dice> path;
    private Dice start;
    private int remaining;
    private int requiredLength;
    private int cycleLength;
    private int cyclesCount = 0;
    private int j = 0;
    private int[] temp;
    private boolean[] dumbFlag;
    private Text text;
    private Text cycleText;
    private ListView listView;
    
    private static final String def = " derivative cycle";
    
    private DominoTable dominoTable;

    public DominoCycleFinder(DominoTable dominoTable, ListView<String> listView,  Text text, Text cycleText, int reqiredLength) {
        
        this.dominoTable = dominoTable;
        this.listView = listView;
        this.text = text;
        this.cycleText = cycleText;
        dumbFlag = new boolean[7];
        Arrays.fill(dumbFlag, false);
        this.requiredLength = reqiredLength;
        path = new ArrayList<>();
        dices = new ArrayList<>();
        start = new Dice(0,0);
    }
    
    public static void initFadeIn(Text text) {
        text.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
        fadeTransition.setNode(text);
        fadeTransition.setCycleCount(2);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }
    
    

    public void dfs(Dice dice) {

        if (dice.getValue().equals(start.getValue()) && cycleLength > 2) {
            if (cycleLength >= requiredLength - 7 && cycleLength <= requiredLength) {
                resetDummies();
                remaining = requiredLength - cycleLength;
                Platform.runLater(() -> initFadeIn(text));
                //out << "---------------------------------" << endl;
                final int ctr =  upgradePath(0);
                
                Platform.runLater(() -> {
                    cycleText.setText("+" + String.valueOf(ctr) + def);
                    initFadeIn(cycleText);
                });

                //out << "---------------------------------" << endl;
            }
            markAsCycleMakers();
            return;
        } else if (cycleLength == requiredLength) {
            markAsCycleMakers();
            return;
        }

        for (int i = j; i < dices.size(); i++) {
            if (dices.get(i).isDeleted() == false && dices.get(i).isBlocked() == false && invert(dices.get(i)).isBlocked() == false && incident(dice, dices.get(i))) {
                if (invert(dices.get(i)).getValue().equals(start.getValue())) {
                    continue;
                }
                dices.get(i).setBlocked(true);
                invert(dices.get(i)).setBlocked(true);
                path.add(dices.get(i));
                dices.get(i).setInPath(true);

                //System.out.println(diceToPut.getValue().getKey() + "" + diceToPut.getValue().getValue
                if(cycleLength != requiredLength-1) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {

                    }
                    final Dice diceToPut = dices.get(i);
                    Platform.runLater(() -> dominoTable.putTile(diceToPut.getValue().getKey(), diceToPut.getValue().getValue()));
                }
                cycleLength++;
                dfs(dices.get(i));
                cycleLength--;
                dices.get(i).setInPath(false);
                path.remove(path.size() - 1);
                if(cycleLength != requiredLength-1) {
                    try{
                        Thread.sleep(500);
                    } catch(InterruptedException e) {

                    }
                    Platform.runLater(() -> dominoTable.removeTile());
                }
                //dominoTable.removeTile();
                if (dices.get(i).isCycleMaker() == true) {
                    unblock(dices.get(i));
                    unblock(invert(dices.get(i)));
                }

            }
        }

    }

    private Dice invert(Dice dice) {
        int index = dice.getValue().getKey() + dice.getValue().getValue() * 7;
        index -= dice.getValue().getKey() > dice.getValue().getValue() ? dice.getValue().getValue() : dice.getValue().getValue() - 1;
        return dices.get(index - 1);
    }

    private void unblock(Dice dice) {
        dice.setBlocked(false);
        //invert(dice).blocked = false;
        for (int i = j; i < dices.size(); i++) {
            if (dices.get(i).isDeleted() == false && invert(dices.get(i)).isDeleted() == false && dices.get(i).isInPath() == false && dices.get(i).isBlocked() == true && incident(dices.get(i), dice) == true) {
                unblock(dices.get(i));
            }
        }
    }

    private void markAsCycleMakers() {
        for (int i = j; i < dices.size(); i++) {
            if (dices.get(i).isInPath() == true) {
                dices.get(i).setCycleMaker(true);
            }
        }
    }

    private void solve() {
        for (int i = 0; i < dices.size(); i++) {
            start = dices.get(i);
            path.add(dices.get(i));
            final Dice diceToPut = dices.get(i);
            Platform.runLater(() -> dominoTable.putTile(diceToPut.getValue().getKey(), diceToPut.getValue().getValue()));
            dfs(dices.get(i));
            dices.get(i).setDeleted(true);
            invert(dices.get(i)).setDeleted(true);
            j++;
            reset();
        }
        System.out.println(cyclesCount);
    }

    private boolean incident(Dice first, Dice second) {
        return first.getValue().getValue() == second.getValue().getKey();
    }

    private void resetDummies() {
        temp = new int[path.size()];
        Arrays.fill(temp, -1);
        Arrays.fill(dumbFlag, false);
    }

    private int upgradePath(int iStart) {

        int ctr = 0;
        
        if (remaining == 0) {
            printPath();
            return 1;
        }

        for (int i = iStart; i < path.size() - 1; i++) {
            int number = path.get(i).getValue().getKey();
            if (temp[i] == -1 && !dumbFlag[number]) {
                temp[i] = number;
                dumbFlag[number] = true;
                remaining--;
                ctr += upgradePath(i + 1);
                remaining++;
                dumbFlag[number] = false;
                temp[i] = -1;
            }
        }
        return ctr;
    }

    private void printPath() {
        cyclesCount++;
        String res = "";
        for (int i = 0; i < path.size() - 1; i++) {
            if (temp[i] != -1) {
                res += temp[i] + "." + temp[i] + " ";
                //System.out.print(res);
            }
            res += path.get(i).getValue().getKey() + "." + path.get(i).getValue().getValue() + " ";
        }
        System.out.print(res);
        System.out.println("");
        final String str = res;
        Platform.runLater(() -> {
            listView.getItems().add(str);
        });
    }

    private void readInput() {
        if (requiredLength < 3 || requiredLength > 28) {
            System.out.println("wrong count");
            exit(0);
        }
    }

    private void reset() {
        for (int i = 0; i < dices.size(); i++) {
            dices.get(i).setBlocked(false);
            dices.get(i).setInPath(false);
            dices.get(i).setCycleMaker(false);
        }
        cycleLength = 0;
        path.clear();
        Platform.runLater(() -> dominoTable.removeTile());
    }

    private void initDices() {
        dices.clear();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i != j) {
                    dices.add(new Dice(i, j));
                }
            }
        }
    }

    @Override
    public void run() {
        readInput();
        initDices();
        solve();
    }

}
