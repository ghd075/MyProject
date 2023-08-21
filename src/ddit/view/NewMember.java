package ddit.view;

import ddit.dao.MemberDAO;
import ddit.dto.Member;
import ddit.util.Util;

/**
 *  회원의 주문 및 회원 정보 관리 클래스입니다.
 * 
 */
public class NewMember {

	
	public static Member memberdata = new Member();
	private static NewMember instance = null;
	private static MemberDAO mDao = MemberDAO.getInstance();
	
	public NewMember() {	}
	
	public static NewMember getInstance() {
		if(instance == null) instance = new NewMember();
		return instance;
	}

	/**
	 * 
	 * 
	 * 회원정보 실행 페이지입니다. 인적사항을 기입한 후 ID와 PW를 정하는 페이지입니다.
	 * 
	 * 
	 */
	public void NewMembermain() {

		boolean loop = true;

		while (loop) {

			Util.clearScreen();
			System.out.println("\n\n\n\n");
			System.out.println("\t--------------------------------------------------\n");
			System.out.println("\t▒▒▒▒▒[회원가입을 위해 정보를 입력해주세요!]▒▒▒▒▒\n\n\n");

			memberdata.setName(Util.nameCheck());

			memberdata.setmId(Util.createid());;

			System.out.println("\n\t   사용가능한 아이디 입니다.");
			System.out.println();

//			System.out.print("\t비밀 번호 : ");
//			String memberPW = Util.sc.nextLine();
//			memberdata.setmPw(memberPW);;
//			System.out.println();
			
			memberdata.setmPw(Util.pwCheck());

			memberdata.setPhone(Util.phonenumber());

			memberdata.setAddress(Util.Address());
			
			memberdata.setmPoint(500);

			System.out.println();
			
			int result = mDao.join(memberdata);

			if(result!=0) {
				System.out.println("\n\n\t   회원가입이 완료되었습니다. o(^^o) (o^^)o ");
				System.out.println();
				System.out.println("\n\t   PRESS ENTER TO CONTUNUE...");
				Util.sc.nextLine();
				loop = false;
			}else {
				System.out.println("\n\n\t   회원가입에 실패했습니다. ( ͡°- ͡°) ");
			}

		} // loop

	} // main
}
