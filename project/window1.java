import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.Border;
import java.awt.*;

public class window1 {
    JFrame frame = new JFrame();
    JTextArea textArea1 = new JTextArea();


    window1() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800, 800);
        frame.setResizable(false);



        textArea1 = new JTextArea();
        textArea1.setEditable(false);
        textArea1.setSize(25,25);
        textArea1.setForeground(Color.BLACK);
        textArea1.setFont(new Font("italic" ,Font.ITALIC, 30));
        frame.add(textArea1);



        cpuInfo();
    }


    public void cpuInfo() {

        cpuInfo cpu = new cpuInfo();
        cpu.read(0);

        appendToTextArea("-------------- MY CPU INFO -------------\n\n");

        // Show CPU model, CPU sockets and cores per socket
        appendToTextArea("CPU MODEL:\t"+cpu.getModel());
        appendToTextArea("SOCKETS:\t"+ cpu.socketCount()+"\n");
        appendToTextArea("CORES PER SOCKET:\t"+cpu.coresPerSocket()+"\n");
        appendToTextArea("");

        // Show sizes of L1,L2 and L3 cache
        float cacheArray[] = {cpu.l1dCacheSize(), cpu.l1iCacheSize(), cpu.l2CacheSize(), cpu.l3CacheSize()};
        appendToTextArea("CACHE LEVEL\tSIZE\n");
        appendToTextArea("----------\t----\n");

        for (int counter = 0; counter < cacheArray.length; counter++)
            appendToTextArea(counter+"\t"+ Math.round((cacheArray[counter] / 1000000.0)*100.0)/100.0 +"MB\n");

        // Sleep for 1 second and display the idle time percentage for
        // core 1.  This assumes 10Hz so in one second we have 100

        float Idle2 = cpu.getIdleTime(1);
        float Idle1 = cpu.getIdleTime(0);

        float user1 = cpu.getUserTime(0);
        float user2 = cpu.getUserTime(1);

        float system1 = cpu.getSystemTime(0);
        float system2 = cpu.getSystemTime(1);


        float idleDiff = Idle2/Idle1;
        float utDiff = user2/user1;
        float stDiff = system2/system1;


        cpu.read(1);
        appendToTextArea("CORE:\t1\t2\tDIFF RATIO(core 2: core 1)\n");

        appendToTextArea("----\t--\t--\t------------\n");

        cpu.read(1);
        appendToTextArea("IDLE:\t"+cpu.getIdleTime(1)+"%\t"+cpu.getIdleTime(0)+"%\t"+idleDiff+"\n");

        cpu.read(1);
        appendToTextArea("USER TIME:\t"+cpu.getUserTime(1)+"%\t"+cpu.getUserTime(0)+"%\t"+utDiff+"\n" );

        cpu.read(1);
        appendToTextArea("SYSTEM:\t"+cpu.getSystemTime(1)+"%\t"+cpu.getUserTime(0)+"%\t"+stDiff+"\n");





    }




    private void appendToTextArea(String text){
        textArea1.append(text); // Append text to JTextArea
        textArea1.setCaretPosition(textArea1.getDocument().getLength());

    }

}