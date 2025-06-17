package core;

import javax.swing.*;

public class Helper {

    public static void getTheme(){
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if(info.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            }
        }

    public static boolean isFieldEmpty(JTextField field){
      return field.getText().trim().isEmpty();
    }
    public static boolean isFieldListEmpty(JTextField[] fields){
        for(JTextField field : fields){
            if(Helper.isFieldEmpty(field)) {
                return true;
            }
        }
        return false;
    }
    }

