import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;

public class window2 {


    JFrame frame = new JFrame();
    JTextArea textArea2 = new JTextArea();
    window2(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800,800);

        textArea2 = new JTextArea();
        textArea2.setEditable(false);
        textArea2.setSize(25,25);
        textArea2.setForeground(Color.BLACK);
        textArea2.setFont(new Font("italic" ,Font.ITALIC, 40));
        frame.add(textArea2);


        usbInfo();
    }

    public void usbInfo(){









    }







}

