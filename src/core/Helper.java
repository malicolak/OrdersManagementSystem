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

    public static void alertBoxTR(){
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }

    public static void showMsgPnl(String msg){
        alertBoxTR();
        String message;
        String title;
        switch (msg) {
            case "fill" -> {
                message = "Tüm alanları doldurunuz!";
                title = "HATA!";
            }
            case "info" -> {
                message = "İşlem başarıyla tamamlandı!";
                title = "Bilgi!";
            }
            case "error" -> {
                message = "Bir sorun oluştu!";
                title = "HATA!";
            }
            default -> {
                message = msg;
                title = "Bilgi!";
            }
        }

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    public static boolean confirmMsgPnl(String msg){
        alertBoxTR();
        String message;
        if(msg.equals("sure")){
            message = "Emin misiniz?";
        }else{
            message = msg;
        }

        return JOptionPane.showConfirmDialog(null,message,"Uyarı!",JOptionPane.YES_NO_OPTION) == 0;
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

    public static boolean isValidEmail(String email){

        if(email == null || email.trim().isEmpty()) return false;

        if(!email.contains("@") || !email.contains(".")) return false;

        String[] partMail = email.split("@");
        if (partMail.length != 2) return false;

        if(partMail[0].trim().isEmpty()||partMail[1].trim().isEmpty()) return false;

        if(!partMail[1].contains(".")) return false;

        return true;
    }
    }

