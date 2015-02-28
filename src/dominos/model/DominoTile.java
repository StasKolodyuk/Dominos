
package dominos.model;

import javafx.scene.image.Image;


public class DominoTile {    
    
    private final int leftNumber;
    private final int rightNumber;
    private final Image tileImage;

    public DominoTile(int leftNumber, int rightNumber) {
        this.leftNumber = leftNumber;//Math.min(leftNumber, rightNumber);
        this.rightNumber = rightNumber;//Math.max(leftNumber, rightNumber);
        
        System.out.println("/dominos/resources/dominoe_" + this.leftNumber + this.rightNumber + ".png");
        this.tileImage = new Image(this.getClass().getResourceAsStream("/dominos/resources/dominoe_" + this.leftNumber + this.rightNumber + ".png"), 60, 80, true, true);
    }

    public Image getImage() {
        return tileImage;
    }
    
}