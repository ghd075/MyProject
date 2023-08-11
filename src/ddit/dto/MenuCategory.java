package ddit.dto;

/**메뉴 클래스*/
public class MenuCategory {
	private String mnctCode;
	private String mnctName;
	
	public MenuCategory() { }

	public MenuCategory(String mnctCode, String mnctName) {
		this.mnctCode = mnctCode;
		this.mnctName = mnctName;
	}

	/**해당 메뉴분류코드를 불러오는 메소드*/
	public String getMeCode() {
		return mnctCode;
	}

	/**해당 메뉴분류코드를 설정하는 메소드*/
	public void setMeCode(String mnctCode) {
		this.mnctCode = mnctCode;
	}

	/**해당 메뉴분류명을 불러오는 메소드*/
	public String getMeName() {
		return mnctName;
	}

	/**해당 메뉴분류명을 불러오는 메소드*/
	public void setMeName(String mnctName) {
		this.mnctName = mnctName;
	}

	@Override
	public String toString() {
		return "MenuCategory [mnctCode=" + mnctCode + ", mnctName=" + mnctName + "]";
	}
}
