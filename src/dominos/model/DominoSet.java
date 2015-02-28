
package dominos.model;


public class DominoSet {
    
    public static final int MAX_TILE_NUMBER = 7;
    
    private static DominoSet instance;
    private final DominoTile[][] dominoTiles;

    private DominoSet() {
        dominoTiles = new DominoTile[MAX_TILE_NUMBER][MAX_TILE_NUMBER];
        for(int i = 0; i < dominoTiles.length; i++) {
            for(int j = 0; j < dominoTiles.length; j++) {
                dominoTiles[i][j] = new DominoTile(i, j);
            }
        }
    }
    
    public static DominoSet newInstance() {
        if(instance == null) {
            instance = new DominoSet();
            return instance;
        }
        else {
            return instance;
        }
    }
    
    public DominoTile getTile(int i, int j) {
        return dominoTiles[j][i];
    }
    
}
