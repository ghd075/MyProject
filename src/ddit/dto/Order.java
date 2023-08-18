package ddit.dto;

import java.sql.Timestamp;

import ddit.util.Util;

/**주문 클래스*/
public class Order {
    private String orderNo;
    private Timestamp orderDate;
    private int riderTime;
    private int totalPrice;
    private int price;
    private String mnName; 
    private String cstNo;
	
	public Order() { }
	
	public Order(int totalPrice, int riderTime, String mnName, int price) {
		this.totalPrice = totalPrice;
		this.riderTime = riderTime;
		this.mnName = mnName;
		this.price = price;
	}

	public Order(String orderNo, Timestamp orderDate, int riderTime, int totalPrice, String cstNo) {
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.riderTime = riderTime;
		this.totalPrice = totalPrice;
		this.cstNo = cstNo;
	}

	/**주문번호를 불러오는 메소드*/
	public String getOrderNo() {
		return orderNo;
	}

	/**주문번호를 설정하는 메소드*/
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**주문일시를 불러오는 메소드*/
	public Timestamp getOrderDate() {
		return orderDate;
	}

	/**주문일시를 설정하는 메소드*/
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	/**배달소요시간을 불러오는 메소드*/
	public int getRiderTime() {
		return riderTime;
	}

	/**배달소요시간을 설정하는 메소드*/
	public void setRiderTime(int riderTime) {
		this.riderTime = riderTime;
	}

	/**총가격을 불러오는 메소드*/
	public int getTotalPrice() {
		return totalPrice;
	}

	/**총각격을 설정하는 메소드*/
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**고객번호를 불러오는 메소드*/
	public String getCstNo() {
		return cstNo;
	}
	
	/**고객번호를 설정하는 메소드*/
	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}
	
	/**주문한 메뉴 가격을 불러오는 메소드*/	
	public int getPrice() {
		return price;
	}

	/**주문한 메뉴 가격을 설정하는 메소드*/
	public void setPrice(int price) {
		this.price = price;
	}

	/**주문한 메뉴명을 불러오는 메소드*/	
	public String getMnName() {
		return mnName;
	}

	/**주문한 메뉴명을 설정하는 메소드*/
	public void setMnName(String mnName) {
		this.mnName = mnName;
	}

	@Override
    public String toString() {
        return  String.format("\n\t\t\t%s \t%s원"
    			,	Util.convert(mnName, 25)		
    			,	Util.convert(price+"", 6));
    }
}
