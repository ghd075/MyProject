package ddit.dto;

/**고객 클래스*/
public class Customer {
	private String cstNo;
	private int cstCls;
	private String name;
	private String phone;
	private String address;
	
	public Customer() { }

	public Customer(String name, String phone, String address) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public Customer(int cstCls, String name, String phone, String address) {
		this.cstCls = cstCls;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	/**고객번호를 가져오는 메소드*/
	public String getCstNo() {
		return cstNo;
	}

	/**고객번호를 설정하는 메소드*/
	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	/**회원구분값을 가져오는 메소드*/
	public int getCstCls() {
		return cstCls;
	}

	/**회원구분값을 설정하는 메소드*/
	public void setCstCls(int cstCls) {
		this.cstCls = cstCls;
	}

	/**해당 회원명을 불러오는 메소드*/
	public String getName() {
		return name;
	}

	/**해당 회원명을 설정하는 메소드*/
	public void setName(String name) {
		this.name = name;
	}

	/**해당 회원전화번호를 불러오는 메소드*/
	public String getPhone() {
		return phone;
	}

	/**해당 회원전화번호를 설정하는 메소드*/
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**해당 회원 주소를 불러오는 메소드*/
	public String getAddress() {
		return address;
	}

	/**해당 회원 주소를 설정하는 메소드*/
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer [cstNo=" + cstNo + ", cstCls=" + cstCls + ", name=" + name + ", phone=" + phone + ", address="
				+ address + "]";
	}
}
