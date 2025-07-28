import business.UserController;
import core.Helper;
import entity.User;
import view.DashboardUI;
import view.LoginUI;

public class App {
    public static void main(String[] args) {
        Helper.getTheme();
        //LoginUI loginUI = new LoginUI();
        UserController userController = new UserController();
        User user = userController.checkLogin("m.alicolak01@hotmail.com", "123456");
        DashboardUI dashboardUI = new DashboardUI(user);

    }
}
