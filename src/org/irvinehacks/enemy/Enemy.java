package org.irvinehacks.enemy;

import org.irvinehacks.App;

public class Enemy
{
  public int hp;
  public int attack;
  public boolean isDead;
  public int xPos;
  public int yPos;
  public int range;
  public char character;
  public int turnsToMove;
  private int moveCounter;
  
  public Enemy(char c, int h, int atk, int r, int ttm, int xP, int yP)
  {
    hp = h;
    attack = atk;
    isDead = false;
    range = r;
    xPos = xP;
    yPos = yP;
    range = 1;
    character = c;
    turnsToMove = ttm;
    moveCounter = 0;
  }
  
  public String toString()
  {
    switch(character) {
      case 'O':
        return "Orc";
      case 'G':
        return "Goblin";
      case 'B':
        return "Behemoth";
      default:
        return "monster";
    }
  }

  public int attack()
  {
   return attack; 
  }
  
  public int getHP()
  {
   return hp; 
  }
  
  public void takeDamage(int d)
  {
    if(isDead)
      return;

    hp -= d;

    isDead = (hp <= 0);

    if (isDead) {
      System.out.println(this + " has perished!");
      App.announcements++;
      attack = 0;
      character = ' ';
    }
  }
  
  public void move(char c)
  {
    moveCounter++;

    if(moveCounter< turnsToMove)
      return;

    moveCounter=0;

    switch(c) {
      case 'w':
        yPos++;
        break;
      case 's':
        yPos--;
        break;
      case 'a':
        xPos++;
        break;
      case 'd':
        xPos--;
        break;
    }
  }
}