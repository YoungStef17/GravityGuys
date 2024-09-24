package src.window;


public class Camera {

    private float x,y;

    Camera(float x, float y){
        this.x=x;
        this.y=y;
    }

    public void tick(){
        x-=1.5; // a ogni frame la telecamera si sposta verso sx
//- invece che + perchè quando sposti questa la telecamera in una direzione, gli oggetti dentro la finestra sembrano muoversi nella direzione opposta
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
