package src.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import src.objects.Player;
import src.window.Handler;

public class KeyInput extends KeyAdapter{

    Handler handler;

    public KeyInput(Handler handler){
        this.handler=handler;
    }

    public void keyPressed(KeyEvent e){
        int key=e.getKeyCode(); //ogni comando ha una key

        for(int i =0 ; i<handler.object.size(); i++){
            GameObject tempObject= handler.object.get(i);

            if (tempObject.getId() == ObjectId.Player) {
            
                if (key == KeyEvent.VK_SPACE && ((Player) tempObject).canSwitchGravity ) { //isjumping mi permetti di risaltare solo quando tocco terra
                    tempObject.setGravityInverted(!tempObject.isGravityInverted());
                    ((Player) tempObject).canSwitchGravity = false;
                }
            }
        }

        if(key== KeyEvent.VK_ESCAPE){
            System.exit(1);
        }
    }
    
}