package src.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import src.framework.KeyInput;
import src.framework.ObjectId;
import src.objects.Block;
import src.objects.Player;

public class Game extends Canvas implements Runnable{
    
    private boolean running = false;
    private Thread thread;
    public static int HEIGHT,WIDTH;

    private BufferedImage level ;

    Handler handler;
    Camera cam;
    Random rand = new Random();

    public synchronized void start(){
        if(running) //se è true il metodo ritorna immediatamente e non fa nulla (il gioco è già in esecuzione).
            return;
        running=true;
        thread = new Thread(this); // crea un nuovo thread che eseguirà il codice del metodo run() della tua classe Game(this si riferisce a game poichè thread accetta oggetto runnable e game implemetna runnable).
        thread.start(); //chiama il metodo run
    }

    private void init(){
        WIDTH= getWidth();
        HEIGHT = getHeight();

        BufferImageLoader loader = new BufferImageLoader();
        level= loader.loadImage("/src/res/level.png"); //loading level

        handler = new Handler();

        cam = new Camera(0,0);

        LoadImageLevel(level);
        
        /*handler.addObject(new Player(200,200, handler, ObjectId.Player));
        createLevel();*/

        this.addKeyListener(new KeyInput(handler));
    }



    public void run(){ //aggiungo ciclo di gioco
        init();
        this.requestFocus(); // Garantisce che la finestra di gioco riceva il focus per permettere l'interazione dell'utente tramite tastiera e mouse con la finestra di gioco.
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0; //il gioco aggiorna la sua logica 60 volte al secondo
        double ns = 1000000000/amountOfTicks; // è il numero di nanosecondi che devono passare tra un tick e l'altro per ottenere esattamente amountOfTicks tick al secondo
        double delta = 0; //delta è essenziale per mantenere il gioco aggiornato a un ritmo costante di 60 volte al secondo
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += ( now - lastTime)/ ns; // tiene traccia del tempo trascorso tra due esecuzioni del ciclo, per assicurarsi che gli aggiornamenti logici (tick) avvengano esattamente a 60
            lastTime = now ; //now - lastTime calcola quanto tempo è passato dall'ultima iterazione del ciclo
            while (delta >=1) { //Questo valore viene diviso per ns (cioè il tempo tra un tick e l'altro), per ottenere un valore che rappresenta quante frazioni di tick sono trascorse
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer+=1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames=0;
                updates=0;
            }
        }
    }
    //metodo che è responsabile dell'aggiornamento della logica del gioco (es. movimento dei personaggi, calcolo delle collisioni, ecc.)
    private void tick(){
        handler.tick();
        cam.tick();        
            
        
    }

    //metodo per disegnare sullo schermo usando una strategia di buffering per migliorare la fluidità e ridurre lo sfarfallio.
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();//Recupera la strategia di buffering corrente
        if(bs== null){ //Se non esiste una strategia di buffering, ne crea una
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d=(Graphics2D) g;//trasformo g in g2d perchè graphics2d fornisce funzioni + avanzate come la traslazione
        ////////////////////////////
        
        //Draw here
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g2d.translate(cam.getX(), cam.getY());//begin of cam
        //translate() sposta l'intera area di disegno in base alle coordinate della telecamera.

        handler.render(g);

        g2d.translate(-cam.getX(), -cam.getY());//end of cam
        //Se non usassi g2d.translate(-cam.getX(), -cam.getY()), la traslazione si accumulerebbe ad ogni ciclo di rendering, ciò che disegnerei nei cicli successivi sarebbe ulteriormente spostato di una certa quantità rispetto alla posizione corrente
        ////////////////////////////

        g.dispose();
        bs.show();
    }

    private void LoadImageLevel( BufferedImage image){

        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println("width, height: " + w + " "+ h);

        for(int xx =0; xx<h; xx++){
            for(int yy=0; yy < w; yy++){
                int pixel = image.getRGB(xx, yy);
                Color pixelColor = new Color(pixel);

                if( pixelColor.equals(Color.white)){
                    handler.addObject(new Block(xx*32, yy*32, ObjectId.Block));
                }

                if( pixelColor.equals(Color.blue)){
                    handler.addObject(new Player(xx*32, yy*32, handler,ObjectId.Player));
                }
//moltiplico per 32 perchè 1 pixel rappresenta un blocco 32x32

            }
        }
    }

    public static void main(String[] args) {
        new Window(800, 600, "prototype",new Game());
    }
}
