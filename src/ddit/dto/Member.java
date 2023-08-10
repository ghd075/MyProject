package ddit.dto;

/**회원 클래스*/
public class Member extends Customer {
	
	private String mId;
	private String mPw;
	private int mPoint;
	
	public Member() {	}

	public Member(int cstCls, String name, String phone, String address, String mId, String mPw) {
		super(cstCls, name, phone, address);
		this.mId = mId;
		this.mPw = mPw;
	}

	public Member(String mId, String mPw) {
		this.mId = mId;
		this.mPw = mPw;
	}

	public Member(String name, String phone, String address, String mId, String mPw, int mPoint) {
		super(name, phone, address);
		this.mId = mId;
		this.mPw = mPw;
		this.mPoint = mPoint;
	}

	public Member(int cstCls, String name, String phone, String address, String mId, String mPw, int mPoint) {
		super(cstCls, name, phone, address);
		this.mId = mId;
		this.mPw = mPw;
		this.mPoint = mPoint;
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
	
	
	/**해당 회원 포인트를 불러오는 메소드*/
	public int getmPoint() {
		return mPoint;
	}
	
	/**해당 회원 포인트를 설정하는 메소드*/
	public void setmPoint(int mPoint) {
		this.mPoint = mPoint;
	}

}
