package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import utils.AppConstants;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;//importing for cards in deck and hand
import java.util.*;

/**
 * A basic representation of of the Player. A player
 * has health and mana.
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Player {

	int playerID=1; // 1=player1, 2= computerPlayer
	int health;
	int mana;
	int cardID=0;//variable to set card id
	int position = 1;//variable to set card position in hand
	
	boolean highlighted=false; 
	boolean moved=false; // variable to check whether the player has already moved or not
	boolean attacked=false; // variable to check whether the player has already attacked other units or not

	String[] cardsFiles; //  of cards 
	
	int currentXpos=0,currentYpos=0;

	public static List<Card> player1Deck = new ArrayList<Card>();//player's deck of card
	public static List<Card> player1Hand = new ArrayList<Card>();//player's hand of card

	/** constructor to create a player with set health and mana which calls setPlayer to place the data on the front end.
	 * 
	 * @param playerID
	 * @param out
	 * @param avatar
	 * @param cardsDeck
	 */
	public Player(int playerID, ActorRef out, BetterUnit avatar, String[] cardsFiles) {
		this.playerID=playerID;
		this.health = avatar.getHealth();
		this.mana = 2; // this will be set to player turn +1 once we have player turn available
		this.cardsFiles=cardsFiles;
		setPlayer(out);
	}
	public Player(int health, int mana) {
		super();
		this.health = health;
		this.mana = mana;
	}
	public int getID() {
		return playerID;
	}
	public void setID(int playerID) {
		this.playerID = playerID;
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
	
	public boolean getHighlighted() {
		return highlighted;
	}
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	
	
	public boolean getMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
	
	public boolean getAttacked() {
		return attacked;
	}
	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
		
	public int getCurrentXpos() {
		return currentXpos;
	}
	public void setCurrentXpos(int currentXpos) {
		this.currentXpos = currentXpos;
	}
	public int getCurrentYpos() {
		return currentYpos;
	}
	public void setCurrentYpos(int currentYpos) {
		this.currentYpos = currentYpos;
	}
	
	
	/** Setting the player health and mana on the front end
	 * 
	 * @param out
	 */
	public void setPlayer(ActorRef out){

		if(playerID==1)
		{
			BasicCommands.setPlayer1Health(out, this);
			AppConstants.callSleep(100);
		
			BasicCommands.setPlayer1Mana(out, this);
			AppConstants.callSleep(100);
		}else {
			 BasicCommands.setPlayer2Health(out, this);
			 AppConstants.callSleep(100);

		     BasicCommands.setPlayer2Mana(out, this);
			 AppConstants.callSleep(100);
		}


	}


	public void createP1Deck() {
		for(int j=0;j<cardsFiles.length;j++){
			Card card = BasicObjectBuilders.loadCard(cardsFiles[j], cardID, Card.class);
			cardID++;
			player1Deck.add(j, card);
			System.out.println("Card " + player1Deck.get(j).getCardname() + " added to deck" + "at position "+ j);
		}
		for(int j=0;j<cardsFiles.length;j++){
			Card card = BasicObjectBuilders.loadCard(cardsFiles[j], cardID, Card.class);
			cardID++;
			player1Deck.add(10+j, card);
			System.out.println("Card " + player1Deck.get((10+j)).getCardname() + " added to deck"+ "at position "+ (10+j));
		}
		
		
	}

	//method to get total cards in the deck
	public int getCardInP1Deck(){
		return player1Deck.size();
	}
	
	/** This method sets the hand of the corresponding player object
	 * 
	 * @param out
	 */
    public void setHand(ActorRef out) {
        for(int i=0;i<AppConstants.minCardsInHand;i++){
			//move the top card from deck to hand
			player1Hand.add(i, player1Deck.get(0));
			System.out.println("Card " + player1Deck.get(0).getCardname() + " removing from deck");
			player1Deck.remove(0);
			System.out.println("Card " + player1Hand.get(i).getCardname() + " added to hand");
			
            // drawCard [i]
			BasicCommands.drawCard(out, player1Hand.get(i), position, 0);
			AppConstants.callSleep(500);
			// increment the position
			position++;
        }
    }

    /** This method draws a card from the deck and adds that card to the hand
     * of the corresponding player object
     * 
     * @param out
     */
    
	public void drawAnotherCard(ActorRef out) {
		if(position<=AppConstants.maxCardsInHand){
			//move the top card from deck to hand
			player1Hand.add(position-1, player1Deck.get(0));
			player1Deck.remove(0);
			//draw the card
        	BasicCommands.drawCard(out,player1Hand.get(position-1) , position, 0);
    		AppConstants.callSleep(500);
			//increment the position
			position++;
		}
		else {
			AppConstants.printLog("------> drawAnotherCard:: but the hand positions are full !");
			if(playerID==1)
				BasicCommands.addPlayer1Notification(out, "Hand positions are full", 2);
				AppConstants.printLog("------> drawAnotherCard:: card to be burn at position: "+ position);
				player1Deck.remove(position);
				AppConstants.printLog("------> drawAnotherCard:: card burn complted!");
				AppConstants.callSleep(500);
		}
		
	}

	
	/** This method check whether the player's avatar occupies the given tile or not
	 * 
	 * @param tilex
	 * @param tiley
	 * @return
	 */
	public boolean isAvatarOnTile(int tilex, int tiley) {
		// TODO Auto-generated method stub
		AppConstants.printLog("------> isAvatarOnTile:: POS: "+currentXpos+","+currentYpos);

		if (currentXpos==tilex && currentYpos==tiley) // occupied by avatar
			return true;
		else
			return false;
	}
	
	
	
}
