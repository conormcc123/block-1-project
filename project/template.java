/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class template extends JFrame implements ActionListener {


        JButton button;
        JButton button2;
        JButton button3;
        JButton button4;

        template(){
        ImageIcon icon = new ImageIcon("/media/sf_project/pictures/CPUicon.jpg");
        ImageIcon icon2 = new ImageIcon("/media/sf_project/pictures/USBinfo.jpg");
        ImageIcon icon3 = new ImageIcon("/media/sf_project/pictures/PCI-info.jpg");
        ImageIcon icon4 = new ImageIcon("/media/sf_project/pictures/DiskInfo.jpg");


        Border border1 = BorderFactory.createLineBorder(Color.GREEN, 5);
            Border border2 = BorderFactory.createLineBorder(Color.BLUE, 5);
            Border border3 = BorderFactory.createLineBorder(Color.RED, 5);
            Border border4 = BorderFactory.createLineBorder(Color.ORANGE, 5);

            button = new JButton();
            button.addActionListener(this);
            button.setBounds(0, 0, 400, 400);
            button.setIcon(icon);
            button.setBorder(border1);


            button2 = new JButton();
            button2.addActionListener(this);
            button2.setBounds(400, 0, 400, 400);
            button2.setIcon(icon2);
            button2.setBorder(border2);

            button3 = new JButton();
            button3.addActionListener(this);
            button3.setBounds(400, 400, 400, 400);
            button3.setIcon(icon3);
            button3.setBorder(border3);

            button4 = new JButton();
            button4.addActionListener(this);
            button4.setBounds(0, 400, 400, 400);
            button4.setIcon(icon4);
            button4.setBorder(border4);

            // Set up frame properties
            this.setTitle("ISE Group 4 project.");
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setSize(800, 800);
            this.setLayout(null);// Use absolute positioning for button to take effect
            this.setResizable(false);
            this.setLayout(new GridLayout(2, 2, 5, 5));

            // Set icon image
            ImageIcon image = new ImageIcon("Logo.png");
            this.setIconImage(image.getImage());

            // Background color
            this.getContentPane().setBackground(new Color(144, 132, 250));

            // Add button to frame
            this.add(button);
            this.add(button2);
            this.add(button3);
            this.add(button4);
            this.setVisible(true);
        }

    public static String vedorIdConverter(String vendorId){
        String[] vendorNames = {"intel","AMD","Ralink","Realtek Semiconductor", "02 micro", "Nvidia", "Emulex", "Fujitsu",
        };
        String[] vendorIdList = {"0x8086","0x1022","0x1814", "0x10ec", "0x1217","0x10de", "0x19a2", "0x1734" };
        for(int i = 0;i < vendorIdList.length;i++){
            if(vendorId.compareTo(vendorIdList[i]) == 0){
                vendorId = vendorNames[i];
            }
        }
        return vendorId;

    }

    //PCI Information - Shows Information about the PCI devices on your machine
    public static void showPCI() {
        pciInfo pci = new pciInfo();
        pci.read();
        String intel = "0x8086";
        String vendor;
        int intelCount = 0;
        int vendorCount = 0;
        ArrayList<String> pciDevices = new ArrayList<>();
        int pciCount = 0;

        System.out.println("\nThis machine has " +
                pci.busCount() + " PCI buses ");

        // Iterate through each bus
        for (int i = 0; i < pci.busCount(); i++) {
            System.out.println("Bus " + i + " has " +
                    pci.deviceCount(i) + " devices");

            // Iterate for up to 32 devices.  Not every device slot may be populated
            // so ensure at least one function before printing device information
            for (int j = 0; j < 32; j++) {
                if (pci.functionCount(i, j) > 0) {
                    System.out.println("Bus " + i + " device " + j + " has " +
                            pci.functionCount(i, j) + " functions");

                    // Iterate through up to 8 functions per device.
                    for (int k = 0; k < 8; k++) {
                        if (pci.functionPresent(i, j, k) > 0) {
                            System.out.println("Bus " + i + " device " + j + " function " + k +
                                    " has vendor " + vedorIdConverter(String.format("0x%04X", pci.vendorID(i, j, k))) +
                                    " and product " + String.format("0x%04X", pci.productID(i, j, k)));
                            vendor = String.format("0x%04X", pci.vendorID(i, j, k));
                            if (intel.compareTo(vendor) == 0) {
                                intelCount++;
                                vendorCount++;
                            } else {
                                vendorCount++;
                            }
                            pciCount++;
                            pciDevices.add(vendor);
                        }
                    }
                }
            }
        }
        //Prints out what percentage of the devices in use are intel
        System.out.printf("this device is %d percent intel, intelCount = %d, venderCount" +
                " = %d", (intelCount * 100 / vendorCount), intelCount, vendorCount);
        for(int i = 0;i < pciDevices.size(); i++){
            System.out.print("\n" + pciDevices.get(i));
        }
    }

    public static void showUSB() {
        usbInfo usb = new usbInfo();
        usb.read();
        System.out.println("\nThis machine has " +
                usb.busCount() + " USB buses ");

        // Iterate through all of the USB buses
        for (int i = 1; i <= usb.busCount(); i++) {
            System.out.println("Bus " + i + " has " +
                    usb.deviceCount(i) + " devices");

            // Iterate through all of the USB devices on the bus
            for (int j = 1; j <= usb.deviceCount(i); j++) {
                System.out.println("Bus " + i + " device " + j +
                        " has vendor " + String.format("0x%04X", usb.vendorID(i, j)) +
                        " and product " + String.format("0x%04X", usb.productID(i, j)));
            }
        }
    }

    public static void showCPU()
    {
        cpuInfo cpu = new cpuInfo();
        cpu.read(0);

        // Show CPU model, CPU sockets and cores per socket
        System.out.println("CPU " + cpu.getModel() + " has "+
            cpu.socketCount() + " sockets each with "+
            cpu.coresPerSocket() + " cores");

        // Show sizes of L1,L2 and L3 cache
        float cacheArray[] = {cpu.l1dCacheSize(), cpu.l1iCacheSize(), cpu.l2CacheSize(), cpu.l3CacheSize()};
        System.out.printf("%s%10s%n", "cache level", "size");

        for (int counter = 0; counter < cacheArray.length; counter++)
            System.out.printf("%5d%10.2f MB%n", counter, cacheArray[counter] / 1000000);

        // Sleep for 1 second and display the idle time percentage for
        // core 1.  This assumes 10Hz so in one second we have 100
        cpu.read(1);
        System.out.println("core 1 idle="+cpu.getIdleTime(1)+"%");

        cpu.read(1);
        System.out.println("core 1 in user mode = "+cpu.getUserTime(1)+ "%");

        cpu.read(1);
        System.out.println("core 1 in system = " +cpu.getSystemTime(1)+ "%");

    }

    public static void showDisk()
    {
        diskInfo disk = new diskInfo();
        disk.read();

        // Iterate through all of the disks
        for (int i = 0; i < disk.diskCount(); i++) {
            System.out.println ("disk "+disk.getName(i)+" has "+
                disk.getTotal(i)+" blocks, of which "+
                disk.getUsed(i)+" are used");
        }
    }

    public static void showMem()
    {
        memInfo mem = new memInfo();
        mem.read();

        System.out.println ("There is "+mem.getTotal()+" memory of which "+
            mem.getUsed()+" is used");
    }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button) {
                window1 window1 = new window1();
            }
            if (e.getSource()== button2){
                window2 window2 = new window2();
            }
            if (e.getSource() == button3){
                window3 window3 = new window3();
            }
            if (e.getSource() == button4){
                window4 window4 = new window4();
            }
        }








    public static void main(String[] args)
    {
        System.loadLibrary("sysinfo");
        sysInfo info = new sysInfo();
        cpuInfo cpu = new cpuInfo();
        cpu.read(0);

        new template();
        showCPU();
        showPCI();
        showUSB();
        showDisk();
        showMem();
    }
}

