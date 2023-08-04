package ddit.dto;

/**메뉴가격 클래스*/
public class MenuPrice {
	private String memeNu; 
	private int price;  
	private int proCnt;              
	private String meCode;
	private String stoNo; 
	private String stoOrder; 
	
	public MenuPrice() { }
	
	public MenuPrice(String stoNo, String stoOrder, String memeNu, int price) {
		this.stoNo = stoNo;
		this.stoOrder = stoOrder;
		this.memeNu = memeNu;
		this.price = price;
	}

	public MenuPrice(String memeNu, int price, int proCnt, String meCode, String stoNo) {
		this.memeNu = memeNu;
		this.price = price;
		this.proCnt = proCnt;
		this.meCode = meCode;
		this.stoNo = stoNo;
	}

	/**메뉴명을 불러오는 메소드*/
	public String getMemeNu() {
		return memeNu;
	}

	/**메뉴명을 설정하는 메소드*/
	public void setMemeNu(String memeNu) {
		this.memeNu = memeNu;
	}

	/**메뉴가격을 불러오는 메소드*/
	public int getPrice() {
		return price;
	}

	/**메뉴가격을 설정하는 메소드*/
	public void setPrice(int price) {
		this.price = price;
	}

	/**메뉴수량을 불러오는 메소드*/
	public int getProCnt() {
		return proCnt;
	}

	/**메뉴수량을 설정하는 메소드*/
	public void setProCnt(int proCnt) {
		this.proCnt = proCnt;
	}

	/**해당 메뉴분류코드를 불러오는 메소드*/
	public String getMeCode() {
		return meCode;
	}

	/**해당 메뉴분류코드를 설정하는 메소드*/
	public void setMeCode(String meCode) {
		this.meCode = meCode;
	}

	/**해당 가게고유번호를 불러오는 메소드*/
	public String getStoNo() {
		return stoNo;
	}

	/**해당 가게고유번호를 설정하는 메소드*/
	public void setStoNo(String stoNo) {
		this.stoNo = stoNo;
	}

	/**해당 가게 누적 주문수를 불러오는 메소드*/
	public String getStoOrder() {
		return stoOrder;
	}

	/**해당 가게 누적 주문수를 설정하는 메소드*/
	public void setStoOrder(String stoOrder) {
		this.stoOrder = stoOrder;
	}

	@Override
	public String toString() {
		return "MenuPrice [memeNu=" + memeNu + ", price=" + price + ", proCnt=" + proCnt + ", meCode=" + meCode
				+ ", stoNo=" + stoNo + "]";
	}
}
