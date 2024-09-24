package src.framework;

import java.util.LinkedList;
import java.awt.Graphics;
import java.awt.Rectangle;

//è una classe base per tutti gli oggetti del gioco
public abstract class GameObject{
    protected float x,y;
    protected float velX=0,velY=0;
    protected ObjectId id; 
    protected boolean gravityInverted=false;
    protected boolean canSwitchGravity = false; 

    //protected: significa che solo chi estende la classe può usare le variabili

    public GameObject(float x, float y, ObjectId id){
        this.x = x;
        this.y=y;
        this.id = id;
    }

    /*
    Usare una LinkedList<GameObject> come parametro permette a ogni oggetto di:
    Accedere a tutti gli altri oggetti nel gioco (che sono anch'essi istanze di GameObject o sue sottoclassi).
    Iterare attraverso la lista per verificare interazioni, collisioni, o altre logiche che coinvolgono più oggetti contemporaneamente. 
    Linkedlist perchè è facile aggiungere o rimuovere oggetti durante l'esecuzione del gioco, cosa che può essere comune in un contesto di gioco
    (ad esempio, quando un nemico viene eliminato, viene rimosso dalla lista). 
    */
    public abstract void tick(LinkedList <GameObject> object);
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();
    
    public  float getX(){
        return x;
    }
    public  float getY(){
        return y;
    }
    public  void setX(float x){
        this.x = x;
    }
    public  void setY(float y){
        this.y = y;
    }

    public  float getVelX(){
        return velX;
    }
    public  float getVelY(){
        return velY;
    }
    public  void setVelX(float velX){
        this.velX=velX;
    }
    public  void setVelY(float velY){
        this.velY=velY;
    }

    public boolean isGravityInverted(){
        return gravityInverted;
    }

    public void setGravityInverted(boolean gravityInverted){
        this.gravityInverted=gravityInverted;
    }

    public  ObjectId getId(){
        return id;
    }

}