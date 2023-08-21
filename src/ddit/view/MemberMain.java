package ddit.view;

import ddit.util.Util;

/**
 * 
 *첫 홈화면에서 회원을 선택하면 나오는 페이지입니다. 
 * 
 * 
 */
public class MemberMain {
	private static MemberMain instance = null;
	
	/**
	 * 
	 *  @document 회원 페이지의 초기화면입니다. 로그인/회원가입 중 선택할 수 있습니다.
	 * 
	 */
	
	public MemberMain() {	}
	
	public static MemberMain getInstance() {
		if(instance == null) instance = new MemberMain();
		return instance;
	}
	

	public void memberMain(){
		
		boolean loop = true;
		while (loop) {
			
			Util.clearScreen();
			System.out.println("\n\n\n\n");
			System.out.println("\t\t\t ▒▒▒▒▒[ 회원화면!! ]▒▒▒▒▒ ");
			System.out.println();
			System.out.println("\t1. 로그인");
			System.out.println();
			System.out.println("\t2. 회원가입");
			System.out.println();
			System.out.println("\t[뒤로 가기를 원하면 0번을 눌러주세yo]");
			System.out.println();
			System.out.println("\n");
			System.out.print("\t이동할 화면 입력(숫자) : ");
			String input = Util.sc.nextLine();
	
			// 뒤로 가기
			if (input.equals("0")) {
				loop = false;
			// 로그인 페이지로 이동
			} else if (input.equals("1")) {
				Login lUi = Login.getInstance();
				lUi.Memberloginmain();
				return;			// memberMain 메서드 종료
			// 회원가입 페이지로 이동
			} else if (input.equals("2")) {
				NewMember newUi = NewMember.getInstance();
				newUi.NewMembermain();
				return;			// memberMain 메서드 종료
			}
	
		} // loop

	}// main

}
