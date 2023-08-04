package ddit.dto;

/**주문 클래스*/
public class Order {
	private String stoAccep;
	private String stoRide;
	private String stoNo; 
	private String meCode;
	
	public Order() { }

	public Order(String stoAccep, String stoRide, String stoNo, String meCode) {
		super();
		this.stoAccep = stoAccep;
		this.stoRide = stoRide;
		this.stoNo = stoNo;
		this.meCode = meCode;
	}

	/**가게의 해당주문 수락상태를 불러오는 메소드*/
	public String getStoAccep() {
		return stoAccep;
	}

	/**가게의 해당주문 수락상태를 설정하는 메소드*/
	public void setStoAccep(String stoAccep) {
		this.stoAccep = stoAccep;
	}

	/**라이더의 해당주문 수락상태를 불러오는 메소드*/
	public String getStoRide() {
		return stoRide;
	}

	/**라이더의 해당주문 수락상태를 설정하는 메소드*/
	public void setStoRide(String stoRide) {
		this.stoRide = stoRide;
	}

	/**해당 가게고유번호를 불러오는 메소드*/
	public String getStoNo() {
		return stoNo;
	}

	/**해당 가게고유번호를 설정하는 메소드*/
	public void setStoNo(String stoNo) {
		this.stoNo = stoNo;
	}

	/**해당 메뉴분류코드를 불러오는 메소드*/
	public String getMeCode() {
		return meCode;
	}

	/**해당 메뉴분류코드를 설정하는 메소드*/
	public void setMeCode(String meCode) {
		this.meCode = meCode;
	}
}
