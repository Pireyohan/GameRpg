package vscode_rpg_correction.modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Maconnex {
    private static Connection conn = null;
    // String user = "M2I";
        // String password = "H3ll0M2I";
        // String database = "bddYohan";
        // int port = 3306;
        
        public Maconnex(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://51.68.227.19/bddYohan?" + "user=M2I&password=H3ll0M2I");
            System.out.println("Connection successfull");

            // do somthing
            // String successConnect = conn != null ? " SuccessfullApp" : "FailedApp";
            // System.out.println(successConnect);

        } catch (SQLException ex) {
            System.out.println("SQLException :" + ex.getMessage());
            System.out.println("SQLState : " + ((SQLException)ex).getSQLState());
            System.out.println("VendorError :" + ((SQLException)ex).getErrorCode());

        }

    }

    public static Connection getConnection() {
        if (conn == null) {
            new Maconnex();
        }
        return conn;
    }
}
