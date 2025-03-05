package HRmanager0305.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

// validator같은 공통된 기능을 제공하는.. 예외처리도 ?
public class utildemo {
    private static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("src.cofing.dbinfo");
        try {
            Class.forName(bundle.getString("driver")); // db 드라이버 조회
        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    bundle.getString("url"),
                    bundle.getString("user"),
                    bundle.getString("password"));
        } catch (SQLException e) {
            System.out.println("연결실패");
            return null;
        }
    }
}
