package ddit.dto;

/**비회원 클래스*/
public class NoMember {
	
	private String noName;
	private String noPhone;
	private String noAddress;
	
	
	/**비회원 이름을 불러오는 메소드*/
	public String getNoName() {
		return noName;
	}
	
	/**비회원 이름을 설정하는 메소드*/
	public void setNoName(String noName) {
		this.noName = noName;
	}
	
	/**비회원 전화번호를 불러오는 메소드*/
	public String getNoPhone() {
		return noPhone;
	}
	
	/**비회원 전화번호를 설정하는 메소드*/
	public void setNoPhone(String noPhone) {
		this.noPhone = noPhone;
	}
	
	/**비회원 주소를 불러오는 메소드*/
	public String getNoAddress() {
		return noAddress;
	}
	
	/**비회원 주소를 설정하는 메소드*/
	public void setNoAddress(String noAddress) {
		this.noAddress = noAddress;
	}

	@Override
	public String toString() {
		return "NoMember [noName=" + noName + ", noPhone=" + noPhone + ", noAddress=" + noAddress + "]";
	}
	
}
