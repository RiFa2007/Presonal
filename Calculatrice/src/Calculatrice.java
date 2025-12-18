import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;
// import javax.swing.border.Border;


public class Calculatrice {
    
    int bordwidth = 1000;
    int bordHeight = 700;

    Color grisClaire = new Color(212,212,210);
    Color grisFonce = new Color(80,80,80);

    Color noir = new Color(28,28,28);

    Color orange = new Color(255,149,0);

    JFrame frame = new JFrame("calculatrice");

    JLabel label = new JLabel();

    JPanel panel = new JPanel();
    
    JPanel boutonp = new JPanel();

    String[] buttonValues = {
        "AC", "+/-", "%", "Del","÷",
        "7", "8", "9", "pi","×",  
        "4", "5", "6","sin" ,"-",
        "1", "2", "3", "cos","+",
         ".","0", "√", "^","="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "=","^"};
    String[] topSymbols = {"AC", "+/-", "%","Del","cos","sin"};


    // A et B seron les valeur qu'on va utiliser pour les opp 
    /*quand on met la premiere val elle est stoquee
     * dans A puis l'operateur est stoquee dans la valeur au meme nom
     * puis le deuxieme nombre est stoque dans B 
    */
    String A = "0";
    String operateur =null;
    String B = null;


//On met les parametres de l'ecran dans le constructeur et tous mdr 
    Calculatrice(){
        
        frame.setSize(bordwidth,bordHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(400, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        label.setBackground(noir);//couleur du backgrround
        label.setForeground(Color.WHITE);//couleur du texte
        label.setFont(new Font("Arial",Font.PLAIN,80));
        label.setHorizontalAlignment(JLabel.RIGHT);// pour que le texte sois afficher de droite a gauche
        label.setText("0");
        label.setOpaque(true);

        panel.setLayout(new BorderLayout());
        panel.add(label);
        frame.add(panel,BorderLayout.NORTH);

        boutonp.setLayout(new GridLayout(5,5));
        boutonp.setBackground(noir);
        frame.add(boutonp);

        for(int i = 0; i<buttonValues.length;i++){

            JButton bouton = new JButton();
            String valeurBout = buttonValues[i];
            bouton.setFont(new Font("Arial",Font.PLAIN,30));
            bouton.setText(valeurBout);
            bouton.setFocusable(false);
            bouton.setBorder(new LineBorder(Color.BLACK));
            
            

            //pour changer le background des bouton en haut
            if(Arrays.asList(topSymbols).contains(valeurBout)){

                bouton.setBackground(grisClaire);
                bouton.setForeground(noir);                
            }else if(Arrays.asList(rightSymbols).contains(valeurBout)){
                bouton.setBackground(orange);
                bouton.setForeground(Color.white);     
            }else {
                bouton.setBackground(grisFonce);
                bouton.setForeground(Color.white);   
                }
                if(valeurBout == "pi"){
                    bouton.setBackground(grisClaire);
                    bouton.setForeground(noir);  
                }

            boutonp.add(bouton);

            bouton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){

                    JButton button = (JButton) e.getSource();

                    String valeurBouton = button.getText();

                    if(Arrays.asList(rightSymbols).contains(valeurBout)){
                        if(valeurBouton=="="){
                            if(A!=null){
                                B = label.getText();

                                float numA = Float.parseFloat(A);
                                float numB = Float.parseFloat(B);

                                if(operateur == "+"){
                                    label.setText(enleveZeroDecimal(numA+numB));
                                }else if(operateur == "-"){
                                    label.setText(enleveZeroDecimal(numA-numB));
                                }else if(operateur == "×"){
                                    label.setText(enleveZeroDecimal(numA*numB));
                                }else if(operateur == "÷"){
                                    label.setText(enleveZeroDecimal(numA/numB));
                                }else if(operateur =="^"){
                                label.setText(enleveZeroDecimal((float)Math.pow(numA, numB)));
                                }

                                clearAll();
                            }

                        }else if("+-÷×^".contains(valeurBout) ){

                            if(operateur == null){
                                A = label.getText();

                                label.setText("0");

                                B="0";

                            }/*si on ne checque pas ca, l'utilisateur peut
                              apuiller deux fois sur l'operation.
                              donc ce if sert a conserver les valeurs de A  mais 
                              ont change juste l'operateur */

                            operateur = valeurBout;
                           
                        }
                    }else if(Arrays.asList(topSymbols).contains(valeurBout)){

                        if(valeurBout=="AC"){
                          clearAll();
                          label.setText("0");
                        }else if(valeurBout=="+/-"){
                            // pour rendre positif ou nefatif
                            float numlab = Float.parseFloat(label.getText());

                            numlab *=-1;

                            label.setText(enleveZeroDecimal(numlab));

                        }else if(valeurBout=="%"){

                            float numlab = Float.parseFloat(label.getText());

                            numlab /=100;

                            label.setText(enleveZeroDecimal(numlab));

                        }else if(valeurBout == "Del"){

                            String num = label.getText();

                            StringBuilder sb = new StringBuilder(num);

                            sb.deleteCharAt(sb.length()-1);

                            String numNew = sb.toString();
                            if(sb.length()>=1){
                                label.setText(numNew);
                            }
                            else if(label.getText().charAt(0)=='-' && label.getText().length()==1 ){
                                
                                label.setText("0");
                             }
                            else{
                                label.setText("0");
                            }

                        }else if(valeurBout == "cos"){

                            double num = Double.parseDouble(label.getText());

                            float cos = (float)Math.cos(num);

                            label.setText(enleveZeroDecimal(cos));

                        }else if(valeurBout == "sin"){

                            double num = Float.parseFloat(label.getText());

                            float sinus =0 ;
                            if(num == Math.PI || num == 1){
                                sinus = Math.round(Math.sin(Math.PI));
                            }
                            else{
                                 sinus = (float)Math.sin(num);
                                }
                           label.setText(enleveZeroDecimal(sinus));


                        }
                    }else if(valeurBout == "√"){

                          float num = Float.parseFloat(label.getText());

                          label.setText(enleveZeroDecimal((float)Racine(num)));

                        }else{//les nombres (digit)
                            
                            
                        if(valeurBout == "."){
                            //check si le point est la ou pas 
                            if(!label.getText().contains(valeurBout)){
                                label.setText(label.getText()+valeurBout);
                            }
                        }else if(valeurBout == "pi"){
                                if(label.getText() != "0"){
                                    label.setText(label.getText());
                                }else{
                                    label.setText(Double.toString(Math.PI));
                                }
                            }else if("0123456789".contains(valeurBout)){
                            if(label.getText() == "0" ){
                                label.setText(valeurBout);
                            }
                            else{
                                label.setText(label.getText()+valeurBout);
                            }
                        }
                    }

                }
            });
            frame.setVisible(true);
        }

        

    }
        void clearAll(){
            A = "0";
            operateur = null;
            B = null;

    }
    String enleveZeroDecimal(float numero){

        if(numero % 1 == 0){
            return Integer.toString((int)numero);
        }else{
            return Float.toString(numero);
        }

    }

    double Racine (double num){

        if(num>=0){

            return (Math.sqrt(num));

        }else {
            return (num);
        }
    }

}
