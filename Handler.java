package src.window;

import java.awt.Graphics;
import java.util.LinkedList;

import src.framework.GameObject;


//classe Handler è un componente fondamentale per la gestione e l'aggiornamento di tutti gli oggetti del gioco
//è responsabile della gestione di tutti i GameObject nel gioco, aggiornandoli (tick) e renderizzandoli (render).
public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>(); //contiene tutti gli oggetti di gioco attivi.

    private GameObject tempObject; //riferimento temporaneo a ciascun oggetto della lista mentre si itera su di essa, posso farne anche a meno

    /* 
    il metodo tick viene chiamato ad ogni ciclo di gioco per aggiornare lo stato di ciascun oggetto
    scorre la lista di tutti gli oggetti di gioco
    Per ogni oggetto, chiama il suo metodo tick() (che è definito nella classe concreta che estende GameObject), 
    */
    public void tick(){

        for(int i =0; i< object.size(); i++){
            tempObject = object.get(i);

            tempObject.tick(object);
        }
    }

    /*
    Scorre la lista degli oggetti, come nel metodo tick.
    Per ogni oggetto, chiama il suo metodo render(Graphics g) per disegnare l'oggetto
    */
    public void render (Graphics g){
        for(int i = 0; i < object.size(); i++){
            tempObject = object.get(i);

            tempObject.render(g);   
        }
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject( GameObject object){
        this.object.remove(object);
    }


}
/*
 *La classe Handler è un componente chiave che centralizza la gestione di tutti gli oggetti nel gioco. I suoi compiti principali includono:

Aggiornare ogni oggetto chiamando il loro metodo tick().
Renderizzare ogni oggetto chiamando il loro metodo render(Graphics g).
Gestire l'aggiunta e la rimozione di oggetti dal gioco, mantenendo aggiornata la lista degli oggetti attivi.
 */
