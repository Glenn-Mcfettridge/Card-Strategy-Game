package events;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import demo.CheckMoveLogic;
import demo.CommandDemo;
import structures.GameState;
import structures.basic.*;
import utils.AppConstants;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

/**
 * Indicates that both the core game loop in the browser is starting, meaning
 * that it is ready to recieve commands from the back-end.
 * <p>
 * {
 * messageType = “initalize”
 * }
 *
 * @author Dr. Richard McCreadie
 */
public class Initalize implements EventProcessor {

    @Override
    public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

        gameState.gameInitalised = true;

        AppConstants.printLog("------> Initialize :: creating board..");

		// Create a board object and assign it to the gameState board object
        gameState.board = new Board(out);

        AppConstants.printLog("------> Initialize :: Board created !");

        AppConstants.printLog("------> Initialise :: setting player1 health and mana..");

        // Create a player object and assign it to the gameState player object
        gameState.player1 = new Player(out);

		AppConstants.printLog("------> Initialise :: Player health and mana set !");



        // creating the avatar object
		gameState.avatar = new BetterUnit(out);






        // User 1 makes a change
        //CommandDemo.executeDemo(out); // this executes the command demo, comment out this when implementing your solution
        //CheckMoveLogic.executeDemo(out);
    }




}


