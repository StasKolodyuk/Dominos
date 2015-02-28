
package dominos.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class DominoTable {
    
    private final GridPane gridPane;
    
    private final DominoSet dominoSet;
    private int currentRow;
    private int currentColumn;
    private int tileCount;

    public DominoTable() {
        
        gridPane = new GridPane();
       // gridPane.setGridLinesVisible(true);
        dominoSet = DominoSet.newInstance();
        currentRow = 0;
        currentColumn = 0;
        tileCount = 0;
    }
    
    public void putTile(int i, int j) {
        
        ImageView imageView = new ImageView(dominoSet.getTile(i, j).getImage());
        Rectangle2D rect = new Rectangle2D(0, 0, 61, 67);
        imageView.setViewport(rect);
        
        if(tileCount < 8) {
            imageView.setRotate(180);
            currentColumn++;
        }
        else if(tileCount >= 8 && tileCount < 14) {
            imageView = new ImageView(dominoSet.getTile(j, i).getImage());
                        imageView.setRotate(90);

            imageView.setViewport(new Rectangle2D(0, 0, 61, 67));
            currentRow++;            
        }
        else if (tileCount > 14 && tileCount < 22) {
            currentColumn--;
            imageView.setViewport(new Rectangle2D(0, 0, 61, 67));
        }
        else if (tileCount == 14) {
            currentRow++;
            imageView.setViewport(new Rectangle2D(0, 0, 61, 67));
        }
        else if (tileCount >= 22 && tileCount < 28) {
            imageView.setRotate(-90);
            currentRow--;
            imageView.setViewport(new Rectangle2D(0, 0, 61, 67));
        }
        else return;
        
        
        tileCount++;
        
        gridPane.add(imageView, currentColumn, currentRow);

    }
    
    public void removeTile() {
        
        tileCount--;
        if(tileCount < 8) {
            currentColumn--;
        }
        else if(tileCount >= 8 && tileCount < 14) {
            currentRow--;            
        }
        else if (tileCount > 14 && tileCount < 22) {
            currentColumn++;
        }
        else if (tileCount == 14) {
            currentRow--;
        }
        else if (tileCount >= 22 && tileCount < 28) {
            currentRow++;
        }
        gridPane.getChildren().remove(gridPane.getChildren().size()-1);
    }
    
    public GridPane getRoot() {
        return gridPane;
    }
    
}
