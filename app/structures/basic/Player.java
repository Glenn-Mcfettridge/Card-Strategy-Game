package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import utils.AppConstants;
import utils.BasicObjectBuilders;

/**
 * A basic representation of of the Player. A player
 * has health and mana.
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Player {

	int health;
	int mana;
	// constructor to create a player with set health and mana which calls setPlayer to place the data on the front end.
	public Player(ActorRef out) {
		super();
		this.health = AppConstants.playerMaxHealth;
		this.mana = 0;
		setPlayer(out);
	}
	public Player(int health, int mana) {
		super();
		this.health = health;
		this.mana = mana;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getMana() {
		return mana;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}
	// Setting the player 1 health and mana on the front end.
	public void setPlayer(ActorRef out){

		BasicCommands.setPlayer1Health(out, this);
		BasicCommands.setPlayer1Mana(out, this);

	}
	
	
}
