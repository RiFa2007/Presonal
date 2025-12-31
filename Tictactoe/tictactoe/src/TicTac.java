
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
//import javax.swing.border.Border;

public class TicTac {
    int hauteur = 600;
    int largeur = 600;

    Color grisBack = new Color(66, 58, 57);
    Color grisCase = new Color(112, 112, 125);
    Color vert = new Color(49, 189, 53);
    Color bleu = new Color(15, 7, 105);
    
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JPanel panelR = new JPanel();
    JButton start;

    ArrayList<JButton> boutL = new ArrayList<>();

    String currentPlayer = "O";

    static boolean partie;
    boolean victoire = false;

    public void verifgagnant(){ 
        
        int [][] wins = {
            {0,1,2},{3,4,5},{6,7,8},//lignes
            {0,3,6},{1,4,7},{2,5,8},//colones
            {0,4,8},{2,4,6}         //diagonales
        };
        
        for(int i = 0; i < wins.length; i++){
            int a = wins[i][0];
            int b = wins[i][1];
            int c = wins[i][2];

            String s1 = boutL.get(a).getText();
            String s2 = boutL.get(b).getText();
            String s3 = boutL.get(c).getText();

            if(!s1.isEmpty() && s1.equals(s2) && s2.equals(s3)){
                // On colore la ligne gagnante

                 
                boutL.get(a).setBackground(vert);
                boutL.get(b).setBackground(vert);
                boutL.get(c).setBackground(vert);

                victoire = true; 
                
             }

            if(victoire){
             
            for(JButton bout : boutL){

            bout.setEnabled(false);

            }
            partie = false;
            }

        }
    }
    // tableau pour L'IA
    int[][] lignes = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}};

    public TicTac(){

        frame.setSize(largeur,hauteur);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        panel.setLayout(new GridLayout(3,3));
        panel.setBackground(grisBack);
        panelR.setLayout(new GridLayout(1,1));
        panelR.setBackground(grisBack);

     for(int i=1;i<10;i++){
        
        String boutonVal = "";
        JButton bouton = new JButton(boutonVal);

        bouton.setBackground(bleu);
        bouton.setFont(new Font("Arial",Font.PLAIN,80));
        bouton.setForeground(Color.WHITE);
        bouton.setFocusable(false);
        bouton.setBorder(new LineBorder(Color.BLACK));
        bouton.setOpaque(true);
        bouton.setContentAreaFilled(true);

        bouton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e){
            if(bouton.getText().equals("")){
                playMove(bouton);
            }
            
           }

        });
        
      boutL.add(bouton);
      panel.add(bouton);

        // if(currentPlayer.equals("X")){


        //    playMove(IA ());
 
        // }


     }

    panelR.add(start());
    frame.add(panel);
    frame.add(panelR,BorderLayout.SOUTH);
    frame.setVisible(true);
    }

    public JButton start(){
        start = new JButton("start");
        start.setFont(new Font("Arial",Font.PLAIN,30));
        start.setBorder(new LineBorder(Color.black));
        start.setBackground(Color.black);
        start.setFocusable(false);
        start.setForeground(Color.WHITE);
        start.addActionListener(e -> resetGame());

            
    return start;
    }

    private void playMove(JButton bouton) {
    bouton.setText(currentPlayer);
    bouton.setForeground(currentPlayer.equals("O") ? Color.CYAN : Color.RED);

        SwingUtilities.invokeLater(() -> verifgagnant());

    // changement de joueur
    currentPlayer = currentPlayer.equals("O") ? "X" : "O";
        
    if(currentPlayer.equals("X") && !victoire){
                        
        // SwingUtilities.invokeLater(() ->playMove(IA()));
        SwingUtilities.invokeLater(() ->playMove(Gagne(boutL)));

                }

    

    }

    private void resetGame(){

        for(JButton bout : boutL){
                    
                    bout.setText("");
                    bout.setBackground(bleu);
                    bout.setEnabled(true);

                    victoire = false;

                    partie = true;

    }
}

    private ArrayList<Integer> casesVides() {
   
   ArrayList<Integer> bou = new ArrayList<>();
    for(int i=0;i<boutL.size();i++){
    

    if(boutL.get(i).getText().isEmpty()){
       
        bou.add(i);
    }
}
    return bou;
}

    private JButton IA (){

        Random neu = new Random();

         ArrayList<Integer> boutv = casesVides();

        JButton but = boutL.get(boutv.get(neu.nextInt(boutv.size())));
        if (boutv.size()>1){
             return but;
        }
       else{
        return null;
       }
    }

    private JButton Gagne(ArrayList<JButton> bouton){
        
    JButton nouvB;

    JButton block = bloque();
    JButton gagnefr = gagnefr();


    if( (bouton.get(0).getText().equals("O") ||
         bouton.get(2).getText().equals("O") ||
         bouton.get(6).getText().equals("O") ||
         bouton.get(8).getText().equals("O"))
         && bouton.get(4).getText().equals("") ) {
    
        nouvB = boutL.get(4); // jouer au centre

    } else if(block!=null) {
        nouvB = block;
        block = null;
    }else if(gagnefr!=null){
        nouvB = gagnefr;
    }
    else{
        nouvB = IA(); // coup random si pas applicable
    }
    return nouvB;
}

 private JButton bloque(){

    for (int[] l: lignes){
        int conteLesO =0;
        int CaseVide = -1;

        for(int i:l){
            if(boutL.get(i).getText().equals("O")){
                 conteLesO++;
            }else if(boutL.get(i).getText().equals("")){
                CaseVide = i;
            }
        }
        if( conteLesO == 2 && CaseVide != -1){
            return boutL.get(CaseVide);
        }
    }

    return null;
 }
 private JButton gagnefr(){
    for(int []l: lignes){
        int conteX = 0;
        int conteEs=-1;
        for(int index:l ){
            if(boutL.get(index).getText().equals("X")){
                conteX ++;
            }else if (boutL.get(index).getText().equals("")){
                conteEs = index;
            }
        }

        if(conteX == 2 && conteEs!=-1){
            return boutL.get(conteEs);
        }
    }


    return null;
 }

    
    
}