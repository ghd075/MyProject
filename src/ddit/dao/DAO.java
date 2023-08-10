package ddit.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * DB 연결 클래스
 * */
public class DAO {
	private final static String db_url = "jdbc:oracle:thin:@192.168.36.61:1521:xe";
	private final static String db_id = "ddit";			//유저ID
	private final static String db_pw = "java";			//유저PW
	private final static String db_drv = "oracle.jdbc.driver.OracleDriver";
	
	private static Connection conn = null; // 자바와 오라클에 대한 연결 설정
	private static PreparedStatement pstm = null; // SQL문을 담는 객체 // sql문을 수행하기 위한 객체
	private static ResultSet rs = null; // statement 동작에 대한 결과로 전달되는 DB의 내용..!
	private static CallableStatement cstmt;
	 
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
    
	public static int update(String sql, Object... param) {
		// sql => "DELETE FROM JAVA_BOARD WHERE BOARD_NUMBER=?"
		// sql => "UPDATE JAVA_BOARD SET TITLE='하하' WHERE BOARD_NUMBER=?"
		// sql => "INSERT MY_MEMBER (MEM_ID, MEM_PASS, MEM_NAME) VALUES (?, ?, ?)"
		int result = 0;
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(sql);

			conn.setAutoCommit(false); // 수동 커밋 설정
			
			for (int i = 0; i < param.length; i++) {
				pstm.setObject(i + 1, param[i]);
            }

			result = pstm.executeUpdate();
			if(result > 0)
				conn.commit();
			
		}catch (Exception e) {
			e.printStackTrace();
	          if(conn!=null) {
	              try{
	                  conn.rollback();
	              }catch(SQLException sqle) { }
	          }
		}finally {
			close(pstm);
			close(conn);
		}
		return result;
	} 
	
	// 고객번호를 가져오기 위해
	public static String insertAndGetCSTNO(String sql, Object... param) {
		// sql => "DELETE FROM JAVA_BOARD WHERE BOARD_NUMBER=?"
		// sql => "UPDATE JAVA_BOARD SET TITLE='하하' WHERE BOARD_NUMBER=?"
		// sql => "INSERT MY_MEMBER (MEM_ID, MEM_PASS, MEM_NAME) VALUES (?, ?, ?)"
		int result = 0;
		String CSTNO = null;

	    try {
	        conn = DAO.getConnection();
	        pstm = conn.prepareStatement(sql, new String[]{"CSTNO"}); // "CSTNO" 컬럼을 반환값으로 설정

	        conn.setAutoCommit(false); // 수동 커밋 설정

	        for (int i = 0; i < param.length; i++) {
	        	pstm.setObject(i + 1, param[i]);
	        }
	        
	        result = pstm.executeUpdate();

	        if(result > 0) {
	        	rs = pstm.getGeneratedKeys();
	        	 if (rs.next()) {
	        		 CSTNO = rs.getString(1); // CSTNO 값을 가져옴
                }
	        }
	        close(rs);

	        conn.commit();

	    } catch (Exception e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback();
	            } catch (SQLException sqle) {
	            }
	        }
	    } finally {
	        close(cstmt);
	        close(conn);
	    }
	    return CSTNO;
	}
	
}
