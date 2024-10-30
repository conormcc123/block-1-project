import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.util.*;

public class window3 {
    JFrame frame = new JFrame();
    JTextArea textArea1 = new JTextArea();
    window3(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800,800);


        textArea1 = new JTextArea();
        textArea1.setEditable(false);
        textArea1.setSize(25,25);
        textArea1.setForeground(Color.BLACK);
        textArea1.setFont(new Font("italic" ,Font.ITALIC, 30));
        frame.add(textArea1);



        showPCI();
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



    private void appendToTextArea(String text){
        textArea1.append(text); // Append text to JTextArea
        textArea1.setCaretPosition(textArea1.getDocument().getLength());

    }











}
