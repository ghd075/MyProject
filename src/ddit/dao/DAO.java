package ddit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * DB 연결 클래스
 * */
public class DAO {
	private final static String db_url = "jdbc:oracle:thin:@192.168.36.61:1521:xe";
	private final static String db_id = "ddit";			//유저ID
	private final static String db_pw = "java";			//유저PW
	private final static String db_drv = "oracle.jdbc.driver.OracleDriver";
	
	//싱글톤패턴
	public static Connection getConnection() {
		Connection conn = null;	  // Connection은 반드시 예외처리 해줘야 함
		try {
			Class.forName(db_drv);
//			System.out.println("드라이버 로드 성공");
			conn = DriverManager.getConnection(db_url, db_id, db_pw);
//			System.out.println("db연결 성공");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
    public static void close(Connection conn) {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
//                System.out.println("Connection 해제 성공");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void close(PreparedStatement pstm) {
        try {
            if(pstm != null && !pstm.isClosed()) {
            	pstm.close();
//                System.out.println("Connection 해제 성공");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void close(ResultSet rSet) {
        try {
            if(rSet != null && !rSet.isClosed()) {
                rSet.close();
//                System.out.println("Connection 해제 성공");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }	
}
