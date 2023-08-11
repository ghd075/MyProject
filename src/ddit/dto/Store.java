package ddit.dto;

/**가게 클래스*/
public class Store {
	private String stoNo;    
	private String stoName;  
	private String stoPhone;  
	private String stoAddress;
	private int stoOrder;             
	private String sNo;
	
	public Store() { }
	
	public Store(String stoNo, String stoName, int stoOrder) {
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoOrder = stoOrder;
	}
	
	public Store(String sNo, String stoNo, String stoName, int stoOrder) {
		this.sNo = sNo;
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoOrder = stoOrder;
	}

	public Store(String stoNo, String stoName, String stoPhone, String stoAddress, int stoOrder) {
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoPhone = stoPhone;
		this.stoAddress = stoAddress;
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
	public String getsNo() {
		return sNo;
	}
	
	/**순번을 설정하는 메소드*/
	public void setsNo(String sNo) {
		this.sNo = sNo;
	}

	@Override
	public String toString() {
		return "Store [stoNo=" + stoNo + ", stoName=" + stoName + ", stoPhone=" + stoPhone + ", stoAddress="
				+ stoAddress + ", stoOrder=" + stoOrder + ", sNo=" + sNo + "]";
	}

}
