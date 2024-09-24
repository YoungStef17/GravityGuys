package src.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import src.window.Handler;
import src.framework.GameObject;
import src.framework.ObjectId;

public class Player extends GameObject {

    private float width=32 , height=64;

    private float gravity = 0.1f; //il suffisso f serve per indicare che è float
    private final float MAX_SPEED=10;
    private Handler handler;

    public Player(float x, float y, Handler handler, ObjectId id){
        super(x, y, id);
        this.handler=handler;
    }


    public void tick(LinkedList<GameObject> object) {
        //Aggiorna la posizione del giocatore in base alla sua velocità.
        x+=velX;
        y+=velY;        
        
        if(gravityInverted){
            velY+= -gravity; //inverti gravità --> sale
        }else 
            velY+=gravity; //gravità normale --> scende

        if(velY> MAX_SPEED){
            velY=MAX_SPEED; //limito la velocità
        } else if (velY < -MAX_SPEED) {
            velY = -MAX_SPEED;
            }
        
        x+=2;
        Collision(object);

    }

    private void Collision(LinkedList<GameObject> objects){

        for(int i=0; i< handler.object.size(); i++){
            GameObject tempObject= handler.object.get(i);

            if(tempObject.getId() == ObjectId.Block){

                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y=tempObject.getY() +32;
                    velY = 0;
                    canSwitchGravity=true;
                }

                if (getBounds().intersects(tempObject.getBounds())) { //intersects verifica se 2 rettangoli si intersecano
                    y=tempObject.getY() - height;
                    velY = 0;
                    canSwitchGravity=true;
                    
                }
            

                //Right
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x=tempObject.getX() -32;
                }
                //left
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x=tempObject.getX() +32;
                }

                
            }
        }
        

    }

    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect((int)x, (int) y,(int)width, (int)height);

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);
        g2d.draw(getBounds());
        g2d.draw(getBoundsTop());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        
    }


    //definisco i rettangoli di collisione in diverse direzioni (alto, basso, sinistra e destra) per il giocatore
    public Rectangle getBounds() {

        return new Rectangle((int)(x+(width/2)-(width/2)/2), (int) (y+(height/2)) ,(int)width/2, (int)height/2 );
    } 
    

    public Rectangle getBoundsTop() {

        return new Rectangle((int)(x+(width/2)-(width/2)/2), (int) y,(int)width/2, (int)height/2 );
    }

    public Rectangle getBoundsRight() {

        return new Rectangle((int)(x+width-5), (int) y+5,(int) 5, (int)height-10 );
    }

    public Rectangle getBoundsLeft() {

        return new Rectangle((int)x, (int) y+5,(int) 5, (int)height-10 );
    }
    /*_________________
      |     |    |     |
      |--|  |    |  |--|
      |  |  |----|  |  |
      |--|  |    |  |--| 
      |_____|____|_____| 
      il rettangolo è il giocatore, tutti i piccoli rettangoli sono i vari rettangoli di collisione    
     */

    

    

    
    

    
    
}
