
import javax.swing.*;

public class MyFenetre extends JFrame {
    public MyFenetre()  {
        add(new MonPanel());
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
}
