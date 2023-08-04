package ddit.dto;

/**회원 클래스*/
public class Member {
	
	private int mNo;
	private String mId;
	private String mPw;
	private String mName;
	private String mPhone;
	private String mAddress;
	private int mPoint;
	
	public Member() {	}

	public Member(String mID, String mPW, String mName, String mPhone, String mAddress, int mPoint) {
		this.mId = mID;
		this.mPw = mPW;
		this.mName = mName;
		this.mPhone = mPhone;
		this.mAddress = mAddress;
		this.mPoint = mPoint;
	}

	/**시퀀스정보를 가져오는 메소드*/
	public int getmNo() {
		return mNo;
	}

	/**시퀀스정보를 설정하는 메소드*/
	public void setmNo(int mNo) {
		this.mNo = mNo;
	}

	/**해당 회원ID를 불러오는 메소드*/
	public String getmId() {
		return mId;
	}
	
	/**해당 회원ID를 설정하는 메소드*/
	public void setmId(String mID) {
		this.mId = mID;
	}
	
	/**해당 회원PW를 불러오는 메소드*/
	public String getmPw() {
		return mPw;
	}
	
	/**해당 회원PW를 설정하는 메소드*/
	public void setmPw(String mPW) {
		this.mPw = mPW;
	}
	
	/**해당 회원이름를 불러오는 메소드*/
	public String getmName() {
		return mName;
	}
	
	/**해당 회원이름를 설정하는 메소드*/
	public void setmName(String mName) {
		this.mName = mName;
	}
	
	/**해당 회원전화번호를 불러오는 메소드*/
	public String getmPhone() {
		return mPhone;
	}
	
	/**해당 회원전화번호를 설정하는 메소드*/
	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	
	/**해당 회원 주소를 불러오는 메소드*/
	public String getmAddress() {
		return mAddress;
	}
	
	/**해당 회원 주소를 설정하는 메소드*/
	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
	}
	
	/**해당 회원 포인트를 불러오는 메소드*/
	public int getmPoint() {
		return mPoint;
	}
	
	/**해당 회원 포인트를 설정하는 메소드*/
	public void setmPoint(int mPoint) {
		this.mPoint = mPoint;
	}

	@Override
	public String toString() {
		return "Member [mID=" + mId + ", mPW=" + mPw + ", mName=" + mName + ", mPhone=" + mPhone + ", mAddress="
				+ mAddress + ", mPoint=" + mPoint + "]";
	}
}
