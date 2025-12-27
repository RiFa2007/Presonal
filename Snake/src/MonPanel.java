
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class MonPanel extends JPanel implements ActionListener,KeyListener{
    int largeur = 500;
    int longeur = 500;
    
    int  taille = 20;
    Color rouge = new Color(245, 73, 39);
    Color teteserp = new Color(50, 158, 24);
    Color Corpsserp = new Color(59, 245, 15);
    boolean Gameover = false;
    ArrayList<Snake> serpent = new ArrayList<Snake>();
    
    final int CELL_SIZE =20;
    final int GRID_WIDTH = largeur/CELL_SIZE;
    final int GRID_HEIGHT = longeur/CELL_SIZE;

    int gridX = 10;
    int gridY = 10;
    Snake tete = new Snake(gridX,gridY,taille,teteserp);

    Timer time;
    Timer continu;
    boolean pass=false;
    Random pos = new Random();
    int cont;
    Image GameoverImage;


    int appleX = pos.nextInt(GRID_WIDTH);
    int appleY = pos.nextInt(GRID_HEIGHT);
    fruit pomme = new fruit(appleX, appleY, CELL_SIZE, rouge);
    
    direction dir = direction.RIGHT;
    boolean aMange = true;
    int score;
    int BestScore=0;

    
    public MonPanel() {
        setPreferredSize(new Dimension(largeur, longeur));
        setBackground(Color.GRAY);
        setFocusable(true);
        requestFocusInWindow(); 

        addKeyListener(this);
        serpent.add(tete);

        GameoverImage = new ImageIcon(getClass().getResource("./GameOver.png")).getImage();

        time = new Timer(160,this);

        time.start();
        continu = new Timer(500,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                    pass = !pass;
                
                repaint();
            }
        });
        
            continu.start();

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Corpsserp);
        for(Snake ser : serpent){
            ser.draw(g2d);
        }

        pomme.draw(g2d);

            g.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= GRID_WIDTH; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, longeur);
        }
        for (int j = 0; j <= GRID_HEIGHT; j++) {
            g.drawLine(0, j * CELL_SIZE, largeur, j * CELL_SIZE);
        }

        draw(g2d);
 
    }      
   
    public void draw(Graphics2D g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",19,25));
        g.drawString("SCORE: "+score, 10 , 35 );
        

        if(Gameover){
            g.drawImage(GameoverImage,0,0 , null);
            g.drawString("BEST SCORE: "+BestScore, 10 , 75 );
            if(pass){
            g.drawString(" CLIC SPACE TO CONTINUE ",largeur/4-50,400);
        }
    }
      
            
            

    }
    @Override
    public void keyPressed(KeyEvent e) {
     
        switch(e.getKeyCode()){
            case KeyEvent.VK_D:
                if(dir != direction.LEFT && cont!=1){
                    dir = direction.RIGHT;
                    cont =1;
                } 
                break;
            case KeyEvent.VK_W:
                if(dir != direction.DOWN && cont!=1){
                    dir = direction.UP;
                    cont =1;
                } 
                break;
            case KeyEvent.VK_A:
                if(dir != direction.RIGHT && cont!=1){
                    dir = direction.LEFT;
                    cont =1;
                } 
                break;
            case KeyEvent.VK_S:
                if(dir != direction.UP && cont!=1){
                    dir = direction.DOWN;
                   cont =1;
                } 
                break;
            case KeyEvent.VK_SPACE:
                if(Gameover){
                    Gameover = false;
                    serpent.clear();
                    tete.gridX = gridX;
                    tete.gridY = gridY;
                    serpent.add(tete);
                    time.start();
                    placerNouvellePomme();
                    score = 0;
                }
                
        }
       
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
  
       Snake ancienneTete = serpent.get(0);

    int newX = ancienneTete.gridX;
    int newY = ancienneTete.gridY;

    switch (dir) {
    case UP:    newY -= 1; break;
    case DOWN:  newY += 1; break;
    case LEFT:  newX -= 1; break;
    case RIGHT: newX += 1; break;
    }

    // nouvelle tête
    Snake nouvelleTete = new Snake(newX, newY, taille, teteserp);
    serpent.add(0, nouvelleTete);

    tete = serpent.get(0);

    if (nouvelleTete.gridX == pomme.gridX && nouvelleTete.gridY == pomme.gridY ) {
    aMange = true;
    score += 1;
    if(score>BestScore){
        BestScore=score;
    }
    placerNouvellePomme();
    }

    // si le serpent n'a PAS mangé → on enlève la queue
    if (!aMange) {
        serpent.remove(serpent.size() - 1);
    } else {
        aMange = false; // croissance faite
    }
        colision();
        cont =0;
        repaint();
    }
    
    public void colision(){
      if(serpent.get(0).gridX>=GRID_WIDTH || serpent.get(0).gridX<=0||
         serpent.get(0).gridY>=GRID_HEIGHT || serpent.get(0).gridY<=0){
        Gameover = true;
        time.stop();
      }

      for(int i =1;i<serpent.size();i++){
        Snake corps = serpent.get(i);

        if (tete.gridX == corps.gridX &&
        tete.gridY == corps.gridY) {
        Gameover = true;
        time.stop();
        break;
    }
      }

    }

    void placerNouvellePomme() {
    pomme.gridX = pos.nextInt(GRID_WIDTH);
    pomme.gridY = pos.nextInt(GRID_HEIGHT);
    if(pomme.gridX == 0 || pomme.gridY==0){
        placerNouvellePomme();
    }
}
    @Override
    public void keyTyped(KeyEvent e) {} 
    @Override
    public void keyReleased(KeyEvent e) {}
    }
 class Snake{
    public void anciencode(){
    // public Snake (int x , int y ,int taille,Color couleur,boolean vivant){
    /*      this.taille = taille;
        this.couleur=couleur;
        this.vivant=vivant;
        this.x=x;
        this.y=y;
    }
    
        Cette aproche convient quand on utitilise pas de grille

    public void draw(Graphics2D g){
        g.setColor(this.couleur);
        g.fillRect(x, y,taille, taille);
     }*/}
    
     int gridX;
    int gridY;
    int taille;
    Color couleur;

    public Snake(int gridX, int gridY, int taille, Color couleur) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.taille = taille;
        this.couleur = couleur;
    }

    public void draw(Graphics2D g) {
        g.setColor(couleur);
        g.fillRect(
            gridX * taille,
            gridY * taille,
            taille,
            taille
        );
    }

 }
 class fruit{
   int gridX;
    int gridY;
    int taille;
    Color couleur;

    public fruit(int gridX, int gridY, int taille, Color couleur) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.taille = taille;
        this.couleur = couleur;
    }

    public void draw(Graphics2D g) {
        g.setColor(couleur);
        g.fillRect(
            gridX * taille,
            gridY * taille,
            taille,
            taille);
        
        }
}
 enum direction {
    UP,
    DOWN,
    RIGHT,
    LEFT; 
 
    }