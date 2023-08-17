package ddit.dao;

import java.util.List;

import ddit.dto.Menu;
import ddit.dto.Store;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 메뉴, 가게관련 DAO 클래스
 * */
public class MunuDAO {
    Connection conn = null; // 자바와 오라클에 대한 연결 설정
    PreparedStatement pstm = null; // SQL문을 담는 객체 // sql문을 수행하기 위한 객체
    ResultSet rs = null; // statement 동작에 대한 결과로 전달되는 DB의 내용..!
	
    private static MunuDAO instance = null;
    
    public MunuDAO() {	}
	
    //싱글톤패턴
	public static MunuDAO getInstance() {
		if(instance == null) instance = new MunuDAO();
		return instance;
	}
    
	// 주소 매칭
	public String addressSelect(String address) {
	    String sql = "SELECT REGEXP_SUBSTR(ADDRESS, '[가-힣A-Za-z]+(동)') AS ADDRESS " +
	                 "FROM CUSTOMER " +
	                 "WHERE REGEXP_SUBSTR(ADDRESS, '[가-힣A-Za-z]+(동)') IS NOT NULL " +
	                 "AND ADDRESS LIKE ?";
	    
	    List<Object[]> resultList = DAO.selectList(sql, "%" + address + "%");

	    if (!resultList.isEmpty()) {
	        Object[] row = resultList.get(0);
	        String mAddress = (String) row[0]; // 결과 리스트의 첫 번째 행의 첫 번째 열 값
	        return mAddress; // search 성공
	    }

	    return null; // DB 오류
	}
	
	// 가게정보 조회
	public List<Store> storeSelect(String etc) {
	    List<Store> list = new ArrayList<>(); // 반환할 리스트를 위해 list 객체 생성
	    String sql = "SELECT STONO, STONAME, STOPHONE, STOADDRESS, STOORDER " +
	                 "FROM STORE " +
	                 "WHERE 1 = 1 " +
	                 "AND STOADDRESS LIKE ?";

	    List<Object[]> resultList = DAO.selectList(sql, "%" + etc + "%");

	    try {
	        for (Object[] row : resultList) {
	            String stoNo = (String) row[0];
	            String stoName = (String) row[1];
	            String stoPhone = (String) row[2];
	            String stoAddress = (String) row[3];
	            BigDecimal stoOrderBigDecimal = (BigDecimal) row[4];
	            int stoOrder = stoOrderBigDecimal != null ? stoOrderBigDecimal.intValue() : 0;
	            Store store = new Store(stoNo, stoName, stoPhone, stoAddress, stoOrder);
	            list.add(store);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	
	// 가게 분류 체크
	public boolean checkStono(String stono, String address) {
	    boolean flag = false;
	    String query = "SELECT COUNT(STONO) FROM STORE WHERE STONO LIKE ? AND STOADDRESS LIKE ?";
	    
	    List<Object[]> resultList = DAO.selectList(query, "%" + stono + "%", "%" + address + "%");

	    if (!resultList.isEmpty()) {
	        Object[] row = resultList.get(0);
	        int count = ((BigDecimal) row[0]).intValue();
	        flag = count > 0;
	    }

	    return flag;
	}

	
	// 주변 가게 리스트 조회(가게고유번호, 가게주소받아서)
	public List<Store> storeOneSelect(String stono, String address) {
	    List<Store> list = new ArrayList<>(); // 반환할 리스트를 위해 list 객체 생성
	    String query = "SELECT STONO, STONAME, STOORDER " +
	                   "FROM STORE " +
	                   "WHERE STONO LIKE ? " +
	                   "AND STOADDRESS LIKE ?";

	    List<Object[]> resultList = DAO.selectList(query, "%" + stono + "%", "%" + address + "%");

	    try {
	        for (Object[] row : resultList) {
	            String stoNo = (String) row[0];
	            String stoName = (String) row[1];
	            BigDecimal stoOrderBigDecimal = (BigDecimal) row[2];
	            int stoOrder = stoOrderBigDecimal != null ? stoOrderBigDecimal.intValue() : 0;
	            Store store = new Store(stoNo, stoName, stoOrder);
	            list.add(store);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	
	// 누적 주문량 순으로 조회
	public List<Store> storeRankSelect(String stono, String address) {
	    List<Store> list = new ArrayList<>(); // 반환할 리스트를 위해 list 객체 생성
	    String query = "SELECT DENSE_RANK() OVER (ORDER BY STOORDER DESC) AS SNO, " +
	                   "       STONO, " +
	                   "       STONAME, " +
	                   "       STOORDER " +
	                   "FROM STORE " +
	                   "WHERE STONO LIKE ? " +
	                   "AND STOADDRESS LIKE ?";

	    List<Object[]> resultList = DAO.selectList(query, "%" + stono + "%", "%" + address + "%");

	    try {
	        for (Object[] row : resultList) {
	            BigDecimal sNoBigDecimal = (BigDecimal) row[0];
	            int sNo = sNoBigDecimal != null ? sNoBigDecimal.intValue() : 0;
	            String stoNo = (String) row[1];
	            String stoName = (String) row[2];
	            BigDecimal stoOrderBigDecimal = (BigDecimal) row[3];
	            int stoOrder = stoOrderBigDecimal != null ? stoOrderBigDecimal.intValue() : 0;
	            
	            Store store = new Store(sNo, stoNo, stoName, stoOrder);
	            list.add(store);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	
	// 메뉴정보 조회
	public List<Menu> meunSelect(String sto) {
	    List<Menu> list = new ArrayList<>(); // 반환할 리스트를 위해 list 객체 생성
	    String sql = "SELECT ROWNUM AS MNO " +
	    			" , MNNAME " +
	    			" , PRICE " +
	    			" , MNCODE " +
	                 " FROM MENU " +
	                 " WHERE STONO = ?";

	    List<Object[]> resultList = DAO.selectList(sql, sto);

	    try {
	        for (Object[] row : resultList) {
	        	BigDecimal mNoBigDecimal = (BigDecimal) row[0];
	        	String mNo = mNoBigDecimal != null ? mNoBigDecimal.toString() : null;;
	            String memeNu = (String) row[1];
	            BigDecimal priceBigDecimal = (BigDecimal) row[2];
	            int price = priceBigDecimal != null ? priceBigDecimal.intValue() : 0;
	            String mnCode = (String) row[3];
	            Menu menuPrice = new Menu(mNo, memeNu, price, mnCode);
	            list.add(menuPrice);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}


}
