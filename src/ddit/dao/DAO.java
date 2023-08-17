package ddit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    
  //조회 공통모듈
    public static List<Object[]> selectList(String sql, Object... params) {
        List<Object[]> resultList = new ArrayList<>();;

        try {
            conn = DAO.getConnection();
            pstm = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
            	pstm.setObject(i + 1, params[i]);
            }

            rs = pstm.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                resultList.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstm);
            close(conn);
        }
        return resultList;
    }
    
    //추가, 수정, 삭제 공통모듈
	public static int update(String sql, Object... param) {
		// sql => "DELETE FROM JAVA_BOARD WHERE BOARD_NUMBER=?"
		// sql => "UPDATE JAVA_BOARD SET TITLE='하하' WHERE BOARD_NUMBER=?"
		// sql => "INSERT MY_MEMBER (MEM_ID, MEM_PASS, MEM_NAME) VALUES (?, ?, ?)"
		int result = 0;
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(sql);
//
////			conn.setAutoCommit(false); // 수동 커밋 설정
			
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
	
    //추가, 수정, 삭제 공통모듈(커밋없는 버전)
	public static int update(Connection conn, String sql, Object... param) {
	    int result = 0;
	    PreparedStatement pstm = null;
	    try {
	        pstm = conn.prepareStatement(sql);

	        for (int i = 0; i < param.length; i++) {
	            pstm.setObject(i + 1, param[i]);
	        }

	        result = pstm.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstm); // PreparedStatement를 닫음
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
	        close(pstm);
	        close(conn);
	    }
	    return CSTNO;
	}
	
	// 주문번호를 가져오기 위해
	public static String GetORDERNO(String sql, Object... param) {
		String ORDERNO = null;

	    try {
	        conn = DAO.getConnection();
	        pstm = conn.prepareStatement(sql); // "ORDERNO" 컬럼을 반환값으로 설정

	        for (int i = 0; i < param.length; i++) {
	        	pstm.setObject(i + 1, param[i]);
	        }
	        
	        rs = pstm.executeQuery();

	        if (rs.next()) {
	            ORDERNO = rs.getString("ORDERNO"); // ORDERNO 값을 가져옴
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	close(rs);
	        close(pstm);
	        close(conn);
	    }
	    return ORDERNO;
	}
	
}
