package ddit.view;

import java.sql.Connection;

import ddit.dao.DAO;

/**
 * DB 연결 테스트 클래스
 * */
public class Test {

	public static void main(String[] args) {
		Connection conn = null;
		DAO.getConnection();
		DAO.close(conn);
	}

}
