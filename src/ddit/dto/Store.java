package ddit.dto;

/**가게 클래스*/
public class Store {
	private String stoNo;    
	private String stoName;  
	private String stoPhone;  
	private String stoAddress;
	private String mnctCode;
	private int stoOrder;             
	private int sNo;
	
	public static final String KOR_FOOD = "MC001";
	public static final String CHI_FOOD = "MC002";
	public static final String JAP_FOOD = "MC003";
	public static final String FAST_FOOD = "MC004";
	public static final String SNACK_FOOD = "MC005";
	
	public Store() { }
	
	public Store(String stoNo, String stoName, String stoAddress) {
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoAddress = stoAddress;
	}

	public Store(String stoNo, String stoName, String stoAddress, String mnctCode) {
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoAddress = stoAddress;
		this.mnctCode = mnctCode;
	}


	public Store(String stoNo, String stoName, String stoAddress, int stoOrder) {
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoAddress = stoAddress;
		this.stoOrder = stoOrder;
	}

	public Store(String stoNo, String stoName, int stoOrder) {
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoOrder = stoOrder;
	}
	
	public Store(int sNo, String stoNo, String stoName, int stoOrder) {
		this.sNo = sNo;
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoOrder = stoOrder;
	}

	public Store(String stoNo, String stoName, String stoAddress, String mnctCode, int stoOrder) {
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoAddress = stoAddress;
		this.mnctCode = mnctCode;
		this.stoOrder = stoOrder;
	}

	/**해당 가게고유번호를 불러오는 메소드*/
	public String getStoNo() {
		return stoNo;
	}

	/**해당 가게고유번호를 설정하는 메소드*/
	public void setStoNo(String stoNo) {
		this.stoNo = stoNo;
	}

	/**해당 가게이름을 불러오는 메소드*/
	public String getStoName() {
		return stoName;
	}

	/**해당 가게이름을 설정하는 메소드*/
	public void setStoName(String stoName) {
		this.stoName = stoName;
	}

	/**해당 가게번호를 불러오는 메소드*/
	public String getStoPhone() {
		return stoPhone;
	}

	/**해당 가게번호를 설정하는 메소드*/
	public void setStoPhone(String stoPhone) {
		this.stoPhone = stoPhone;
	}

	/**해당 가게 주소를 불러오는 메소드*/
	public String getStoAddress() {
		return stoAddress;
	}

	/**해당 가게 주소 시를 설정하는 메소드*/
	public void setStoAddress(String stoAddress) {
		this.stoAddress = stoAddress;
	}



	/**해당 가게 누적 주문수를 불러오는 메소드*/
	public int getStoOrder() {
		return stoOrder;
	}

	/**해당 가게 누적 주문수를 설정하는 메소드*/
	public void setStoOrder(int stoOrder) {
		this.stoOrder = stoOrder;
	}
	
	/**순번을 설정하는 메소드*/
	public int getsNo() {
		return sNo;
	}
	
	/**순번을 설정하는 메소드*/
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	
	public String getMnctCode() {
		return mnctCode;
	}

	public void setMnctCode(String mnctCode) {
		this.mnctCode = mnctCode;
	}

	@Override
	public String toString() {
		return "Store [stoNo=" + stoNo + ", stoName=" + stoName + ", stoPhone=" + stoPhone + ", stoAddress="
				+ stoAddress + ", stoOrder=" + stoOrder + ", sNo=" + sNo + "]";
	}

}
