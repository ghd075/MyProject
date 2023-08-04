package ddit.dto;

/**메뉴 클래스*/
public class Menu {
	private String meCode;
	private String meName;
	
	public Menu() { }

	public Menu(String meCode, String meName) {
		this.meCode = meCode;
		this.meName = meName;
	}

	/**해당 메뉴분류코드를 불러오는 메소드*/
	public String getMeCode() {
		return meCode;
	}

	/**해당 메뉴분류코드를 설정하는 메소드*/
	public void setMeCode(String meCode) {
		this.meCode = meCode;
	}

	/**해당 메뉴분류명을 불러오는 메소드*/
	public String getMeName() {
		return meName;
	}

	/**해당 메뉴분류명을 불러오는 메소드*/
	public void setMeName(String meName) {
		this.meName = meName;
	}

	@Override
	public String toString() {
		return "Menu [meCode=" + meCode + ", meName=" + meName + "]";
	}
}
