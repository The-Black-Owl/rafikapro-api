import config.DatabaseConnection;

import users.userUI.UserUI;




public class App {
    public static void main(String[] args){
        DatabaseConnection.initialize();
        UserUI userUI=new UserUI();
        userUI.start();
    }

}
