package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import utils.AppConstants;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

import java.util.ArrayList;


/**
 * The board class will contain tile objects stored in a 2D array data structure and will contain
 * methods to set the constraints of the available moves and total size of the board (9x5).
 * The Board object consists of several tile objects.
 */

public class Board {

    Tile[][] tiles = null;

    public Board(ActorRef out) {
        tiles = new Tile[AppConstants.boardWidth][AppConstants.boardHeight];
        setTiles(out);
    }

    /**
     * This method creates tile objects and assign those tiles to the board object.
     *
     * @param out
     */

    public void setTiles(ActorRef out) {

        // Create a tile object
        Tile tile;

        // Iterate through the tiles array
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                // Create a new tile object
                tile = BasicObjectBuilders.loadTile(i, j);

                // Assign that tile object to the array position
                tiles[i][j] = tile;

                // Draw the tile on the front end
                BasicCommands.drawTile(out, tile, 0);


            }
        }

    }


    /**
     * Getter method to return tiles objects of a board
     *
     * @return Tile
     */

    public Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * this method will take in an x and y parameter and return the tile object at that position
     *
     * @param x
     * @param y
     * @return
     */
    public Tile returnTile(int x, int y) {
        return tiles[x][y];
    }


    /**
     * This method will take in a tile and return an ArrayList of the two cardinal and
     * one diagonal tiles available for a standard move in the game
     *
     * @param out
     * @param tile
     * @return
     */
    public ArrayList<Tile> getAdjacentTiles(ActorRef out, Tile tile) {

        // arrayList to store the available tiles
        ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();

        // tile co-ordinates
        int x = tile.getTilex();
        int y = tile.getTiley();

        // for the cardinal tiles I will check if the tile if either 2 or 1 tiles away from the edge of the board so
        // we avoid any nullPointExceptions

        // checking for the top-most tiles
        if (y > 1) {
            adjacentTiles.add(returnTile(x, y - 1));
            adjacentTiles.add(returnTile(x, y - 2));
        } else if (y == 1) {
            adjacentTiles.add(returnTile(x, y - 1));
        }

        // checking for the right-most tiles
        if (x < AppConstants.boardWidth - 2) {
            adjacentTiles.add(returnTile(x + 1, y));
            adjacentTiles.add(returnTile(x + 2, y));
        } else if (x == AppConstants.boardWidth - 2) {
            adjacentTiles.add(returnTile(x + 1, y));
        }

        // checking for the bottom-most tiles
        if (y < AppConstants.boardHeight - 2) {
            adjacentTiles.add(returnTile(x, y + 1));
            adjacentTiles.add(returnTile(x, y + 2));
        } else if (y == AppConstants.boardHeight - 2) {
            adjacentTiles.add(returnTile(x, y + 1));
        }

        // checking for the left-most tiles
        if (x > 1) {
            adjacentTiles.add(returnTile(x - 1, y));
            adjacentTiles.add(returnTile(x - 2, y));
        } else if (x == 1) {
            adjacentTiles.add(returnTile(x - 1, y));
        }

        // top-right
        if (x < AppConstants.boardWidth - 1 && y > 0) {
            adjacentTiles.add(returnTile(x + 1, y - 1));
        }

        // bottom-right
        if (x < AppConstants.boardWidth - 1 && y < AppConstants.boardHeight - 1) {
            adjacentTiles.add(returnTile(x + 1, y + 1));
        }

        // bottom-left
        if (x > 0 && y < AppConstants.boardHeight - 1) {
            adjacentTiles.add(returnTile(x - 1, y + 1));
        }

        // top-left
        if (x > 0 && y > 0) {
            adjacentTiles.add(returnTile(x - 1, y - 1));
        }

        return adjacentTiles;
    }


    /**
     * This method will take in a tile and return an ArrayList of the two cardinal and
     * one diagonal tiles available for a standard attack in the game
     *
     * @param out
     * @param tile
     * @return
     */
    public ArrayList<Tile> getAdjacentTilesToAttack(ActorRef out, Tile tile) {

        // arrayList to store the available tiles
        ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();

        // tile co-ordinates
        int x = tile.getTilex();
        int y = tile.getTiley();


        // checking for the top-most tiles
        if (y > 0) {
            adjacentTiles.add(returnTile(x, y - 1));
        }

        // checking for the right-most tiles
        if (x < AppConstants.boardWidth - 2) {
            adjacentTiles.add(returnTile(x + 1, y));
        }

        // checking for the bottom-most tiles
        if (y < AppConstants.boardHeight - 2) {
            adjacentTiles.add(returnTile(x, y + 1));
        }

        // checking for the left-most tiles
        if (x > 0) {
            adjacentTiles.add(returnTile(x - 1, y));
        }

        // top-right
        if (x < AppConstants.boardWidth - 2 && y > 0) {
            adjacentTiles.add(returnTile(x + 1, y - 1));
        }

        // bottom-right
        if (x < AppConstants.boardWidth - 2 && y < AppConstants.boardHeight - 2) {
            adjacentTiles.add(returnTile(x + 1, y + 1));
        }

        // bottom-left
        if (x > 0 && y < AppConstants.boardHeight - 2) {
            adjacentTiles.add(returnTile(x - 1, y + 1));
        }

        // top-left
        if (x > 0 && y > 0) {
            adjacentTiles.add(returnTile(x - 1, y - 1));
        }

        return adjacentTiles;
    }

    /**
     * This method will take in a tile and return an ArrayList of the two cardinal and
     * one diagonal tiles available for a standard move and attack thereafter in the game
     *
     * @param out
     * @param board
     * @param tile
     * @return
     */
    public ArrayList<Tile> getAdjacentTilesToMoveAndAttack(ActorRef out, Board board, Tile tile) {

        // arrayList to store the available tiles
        ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();

        // tile co-ordinates
        int x = tile.getTilex();
        int y = tile.getTiley();

        // for the cardinal tiles I will check if the tile if either 2 or 1 tiles away from the edge of the board so
        // we avoid any nullPointExceptions

        // checking for the top-most tiles
        if (y > 2) {
            adjacentTiles.add(returnTile(x, y - 1));
            adjacentTiles.add(returnTile(x, y - 2));

            if (board.tiles[x][y - 3].getUnitFromTile() != null) // adding the tile,which can be attacked after the movement
                adjacentTiles.add(returnTile(x, y - 3));

        } else if (y > 1) {
            adjacentTiles.add(returnTile(x, y - 1));
            adjacentTiles.add(returnTile(x, y - 2));
        } else if (y == 1) {
            adjacentTiles.add(returnTile(x, y - 1));
        }


        // checking for the right-most tiles
        if (x < AppConstants.boardWidth - 3) {
            adjacentTiles.add(returnTile(x + 1, y));
            adjacentTiles.add(returnTile(x + 2, y));

            if (board.tiles[x + 3][y].getUnitFromTile() != null) // adding the tile,which can be attacked after the movement
                adjacentTiles.add(returnTile(x + 3, y));

        } else if (x < AppConstants.boardWidth - 2) {
            adjacentTiles.add(returnTile(x + 1, y));
            adjacentTiles.add(returnTile(x + 2, y));
        } else if (x == AppConstants.boardWidth - 2) {
            adjacentTiles.add(returnTile(x + 1, y));
        }


        // checking for the bottom-most tiles
        if (y < AppConstants.boardHeight - 3) {
            adjacentTiles.add(returnTile(x, y + 1));
            adjacentTiles.add(returnTile(x, y + 2));

            if (board.tiles[x][y + 3].getUnitFromTile() != null) // adding the tile,which can be attacked after the movement
                adjacentTiles.add(returnTile(x, y + 3));

        } else if (y < AppConstants.boardHeight - 2) {
            adjacentTiles.add(returnTile(x, y + 1));
            adjacentTiles.add(returnTile(x, y + 2));
        } else if (y == AppConstants.boardHeight - 2) {
            adjacentTiles.add(returnTile(x, y + 1));
        }


        // checking for the left-most tiles
        if (x > 2) {
            adjacentTiles.add(returnTile(x - 1, y));
            adjacentTiles.add(returnTile(x - 2, y));

            if (board.tiles[x - 3][y].getUnitFromTile() != null) // adding the tile,which can be attacked after the movement
                adjacentTiles.add(returnTile(x - 3, y));

        } else if (x > 1) {
            adjacentTiles.add(returnTile(x - 1, y));
            adjacentTiles.add(returnTile(x - 2, y));
        } else if (x == 1) {
            adjacentTiles.add(returnTile(x - 1, y));
        }


        // top-right
        if (x < AppConstants.boardWidth - 1 && y > 0) {
            adjacentTiles.add(returnTile(x + 1, y - 1));
        }
        if (x < AppConstants.boardWidth - 2 && y > 0) {
            if (board.tiles[x + 2][y - 1].getUnitFromTile() != null) // adding the tile,which can be attacked after the movement
                adjacentTiles.add(returnTile(x + 2, y - 1));
        }

        // bottom-right
        if (x < AppConstants.boardWidth - 1 && y < AppConstants.boardHeight - 1) {
            adjacentTiles.add(returnTile(x + 1, y + 1));
        }
        if (x < AppConstants.boardWidth - 2 && y < AppConstants.boardHeight - 1) {
            if (board.tiles[x + 2][y + 1].getUnitFromTile() != null) // adding the tile,which can be attacked after the movement
                adjacentTiles.add(returnTile(x + 2, y + 1));
        }

        // bottom-left
        if (x > 0 && y < AppConstants.boardHeight - 1) {
            adjacentTiles.add(returnTile(x - 1, y + 1));
        }
        if (x > 0 && y < AppConstants.boardHeight - 2) {
            if (board.tiles[x + 2][y + 1].getUnitFromTile() != null) // adding the tile,which can be attacked after the movement
                adjacentTiles.add(returnTile(x - 1, y + 2));
        }

        // top-left
        if (x > 0 && y > 0) {
            adjacentTiles.add(returnTile(x - 1, y - 1));
        }


        return adjacentTiles;
    }

    /**
     * method to iterate through the arrayList of adjacent tiles and drawTile() with white highlighting
     *
     * @param out
     * @param tiles
     */
    public void highlightTilesWhite(ActorRef out, ArrayList<Tile> tiles) {
        for (Tile tile : tiles) {
            BasicCommands.drawTile(out, tile, 1);
        }
    }

    /**
     * method to iterate through the arrayList of adjacent tiles and drawTile() with red highlighting
     *
     * @param out
     * @param tiles
     */
    public void highlightTilesRed(ActorRef out, ArrayList<Tile> tiles) {

        for (Tile tile : tiles) {
            BasicCommands.drawTile(out, tile, 2);
        }
    }


    public void addUnitToBoard(int x, int y, Unit unit) {
        tiles[x][y].setUnitToTile(unit);

    }

    public void addDummyUnitsonBoard(ActorRef out) {
        // TODO Auto-generated method stub


        // Place a unit with attack:3 and health:2 at [2,2]
        int x = 2, y = 2;
        Unit unit1 = BasicObjectBuilders.loadUnit(StaticConfFiles.u_fire_spitter, 1, Unit.class);
        unit1.setAttack(3);
        unit1.setHealth(2);
        addUnitToBoard(x, y, unit1);

        unit1.setPositionByTile(tiles[2][2]);
        BasicCommands.drawUnit(out, unit1, tiles[x][y]);
        AppConstants.callSleep(100);

        BasicCommands.setUnitHealth(out, unit1, unit1.getHealth());
        AppConstants.callSleep(100);

        BasicCommands.setUnitAttack(out, unit1, unit1.getAttack());
        AppConstants.callSleep(100);
        AppConstants.printLog("------> addDummyUnitsonBoard :: Placed unit at [2,2]");


    }

    // check whether a tile has a unit on it and returns a list of tiles occupied by units
    public ArrayList<Tile> getTilesWithUnits(ActorRef out, Tile[][] tiles) {
        ArrayList<Tile> tilesWithUnits = new ArrayList<>();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile tile = tiles[i][j];
                if (tile.getUnitFromTile() != null) {
                    tilesWithUnits.add(tile);
                }
            }
        }

        return tilesWithUnits;
    }



}


