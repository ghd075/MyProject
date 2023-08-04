package ddit.dto;

/**가게 클래스*/
public class Store {
	private String stoNo;    
	private String stoName;  
	private String stoPhone;  
	private String stoAddCity;
	private String stoAddDistrict;
	private String stoAddEtc;
	private int stoOrder;             
	private String mId;
	private int sNo;
	
	public Store() { }
	
	public Store(String stoNo, String stoName, int stoOrder) {
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoOrder = stoOrder;
	}

	public Store(String stoNo, String stoName, String stoPhone, String stoAddCity, String stoAddDistrict,
			String stoAddEtc, int stoOrder) {
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoPhone = stoPhone;
		this.stoAddCity = stoAddCity;
		this.stoAddDistrict = stoAddDistrict;
		this.stoAddEtc = stoAddEtc;
		this.stoOrder = stoOrder;
	}

	public Store(String stoNo, String stoName, String stoPhone, String stoAddCity, String stoAddDistrict,
			String stoAddEtc, int stoOrder, String mId) {
		this.stoNo = stoNo;
		this.stoName = stoName;
		this.stoPhone = stoPhone;
		this.stoAddCity = stoAddCity;
		this.stoAddDistrict = stoAddDistrict;
		this.stoAddEtc = stoAddEtc;
		this.stoOrder = stoOrder;
		this.mId = mId;
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

	/**해당 가게 주소 시를 불러오는 메소드*/
	public String getStoAddCity() {
		return stoAddCity;
	}

	/**해당 가게 주소 시를 설정하는 메소드*/
	public void setStoAddCity(String stoAddCity) {
		this.stoAddCity = stoAddCity;
	}


	/**해당 가게 주소 구를 불러오는 메소드*/
	public String getStoAddDistrict() {
		return stoAddDistrict;
	}

	/**해당 가게 주소 구를 설정하는 메소드*/
	public void setStoAddDistrict(String stoAddDistrict) {
		this.stoAddDistrict = stoAddDistrict;
	}

	/**해당 가게 나머지 주소를 불러오는 메소드*/
	public String getStoAddEtc() {
		return stoAddEtc;
	}

	/**해당 가게 나머지 주소를 설정하는 메소드*/
	public void setStoAddEtc(String stoAddEtc) {
		this.stoAddEtc = stoAddEtc;
	}


	/**해당 가게 누적 주문수를 불러오는 메소드*/
	public int getStoOrder() {
		return stoOrder;
	}

	/**해당 가게 누적 주문수를 설정하는 메소드*/
	public void setStoOrder(int stoOrder) {
		this.stoOrder = stoOrder;
	}
	
	/**해당 회원ID를 불러오는 메소드*/
	public String getmId() {
		return mId;
	}

	/**해당 회원ID를 설정하는 메소드*/
	public void 불러오는(String mId) {
		this.mId = mId;
	}

	/**순번을 설정하는 메소드*/
	public int getsNo() {
		return sNo;
	}
	
	/**순번을 설정하는 메소드*/
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	@Override
	public String toString() {
		return "Store [stoNo=" + stoNo + ", stoName=" + stoName + ", stoPhone=" + stoPhone + ", stoAddCity="
				+ stoAddCity + ", stoAddDistrict=" + stoAddDistrict + ", stoAddEtc=" + stoAddEtc + ", stoOrder="
				+ stoOrder + ", mId=" + mId + "]";
	}
}
