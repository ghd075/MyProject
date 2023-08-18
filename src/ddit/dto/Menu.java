package ddit.dto;

/**메뉴가격 클래스*/
public class Menu {
	private String mNo;
    private String mnCode;
    private String mnName;
	private int price;
	private String formattedPrice; // 가격을 포맷팅된 문자열로 저장
    private String mnctCode;
    private String stono;
    private String rowNum;
    
	public Menu() { }

	public Menu(String mnName) {
		this.mnName = mnName;
	}
	
	public Menu(String mnCode, String mnName, String formattedPrice, String mnctCode, String rowNum) {
		this.mnCode = mnCode;
		this.mnName = mnName;
		this.formattedPrice = formattedPrice;
		this.mnctCode = mnctCode;
		this.rowNum = rowNum;
	}

	public Menu(String mnName, String formattedPrice) {
		this.mnName = mnName;
		this.formattedPrice = formattedPrice;
	}

	public Menu(String mNo, String mnName, int price, String mnCode) {
		this.mNo = mNo;
		this.mnName = mnName;
		this.price = price;
		this.mnCode = mnCode;
	}

	public Menu(String mNo, String mnCode, String mnName, int price, String mnctCode, String stono) {
		this.mNo = mNo;
		this.mnCode = mnCode;
		this.mnName = mnName;
		this.price = price;
		this.mnctCode = mnctCode;
		this.stono = stono;
	}
	
	/**메뉴식별번호를 불러오는 메소드*/
	public String getmNo() {
		return mNo;
	}

	/**메뉴식별번호를 설정하는 메소드*/
	public void setmNo(String mNo) {
		this.mNo = mNo;
	}

	/**메뉴코드를 불러오는 메소드*/
	public String getMnCode() {
		return mnCode;
	}

	/**메뉴코드를 설정하는 메소드*/
	public void setMnCode(String mnCode) {
		this.mnCode = mnCode;
	}

	/**메뉴명을 불러오는 메소드*/	
	public String getMnName() {
		return mnName;
	}

	/**메뉴명을 설정하는 메소드*/
	public void setMnName(String mnName) {
		this.mnName = mnName;
	}

	/**해당 메뉴 가격을 불러오는 메소드*/
	public int getPrice() {
		return price;
	}

	/**해당 메뉴 가격을 설정하는 메소드*/
	public void setPrice(int price) {
		this.price = price;
	}

	/**해당 메뉴 분류 코드를 불러오는 메소드*/
	public String getMnctCode() {
		return mnctCode;
	}

	/**해당 메뉴 분류 코드를 설정하는 메소드*/
	public void setMnctCode(String mnctCode) {
		this.mnctCode = mnctCode;
	}

	/**해당 가게 코드를 불러오는 메소드*/
	public String getStono() {
		return stono;
	}

	/**해당 가게 코드를 설정하는 메소드*/
	public void setStono(String stono) {
		this.stono = stono;
	}
	
	public String getRowNum() {
		return rowNum;
	}
	
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
	
	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}

	@Override
	public String toString() {
		return "Menu [mNo=" + mNo + ", mnCode=" + mnCode + ", mnName=" + mnName + ", price=" + price + ", mnctCode="
				+ mnctCode + ", stono=" + stono + "]";
	}

}
