package ddit.view;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import ddit.util.FormattedWriter;
import ddit.util.LoadingThread;
import ddit.util.Util;

/**프로그램의 시작을 담당하는 Main 클래스*/
public class Main {
	
	/**프로그램의 시작을 담당하는 Main클래스의 main메소드*/
	public static void main(String[] args) throws Exception {
		 FormattedWriter out = new FormattedWriter(
                 new BufferedWriter(
                     new OutputStreamWriter(System.out)), true, 90,//90만큼공간을 준다
                            FormattedWriter.CENTER_JUSTIFIED);
		
		out.println();
		out.print("■■■■■■■■■■■■■■■■■■■■■■ 대 덕 인 재 개 발 원 ■■■■■■■■■■■■■■■■■■■■■■■■■"); // main 흐름
		out.flush();
		out.println();
		 //로그로딩
	    String delivery[] = { // 타이핑 쓰레드 - 익명 클래스
	    		   "\t\t                   sQBRr                \r\n" + 
	               "\t\t                uBBBBQBQ               \r\n" + 
	               "\t\t                BQBQBQBQBB:             \r\n" + 
	               "\t\t               iBBBBXJSbMBr             \r\n" + 
	               "\t\t               .BBBB1bBBBBS             \r\n" + 
	               "\t\t                QBBBBLBBBB              \r\n" + 
	               "\t\t  75K5XXj        1g72MBBR               \r\n" + 
	               "\t\t BBBBBBBBB:      ivrL:          .ID:    \r\n" + 
	               "\t\trBBQBBBBBBd   DBBBBBBBDr       .BBBB    \r\n" + 
	               "\t\t:BBBBBBBBB1 .BBBBBBBBBBBBBQQQBBBBBBB    \r\n" + 
	               "\t\t:QBBBBBBBBY BBBQBBBBBB1vXPEqvrBBY i     \r\n" + 
	               "\t\trBBBBBBBQB7.QBBBQBBBBB         RBX      \r\n" + 
	               "\t\t EBBBBBBBQ  BQBQBBBBBg         iBBB     \r\n" + 
	               "\t\t.iBBBBQBQr :QBBBBBBBBBBB5.     KQBBQ    \r\n" + 
	               "\t\t::       vBg7....iugBBBBBQK  .QBBBBB2r  \r\n" + 
	               "\t\t       Ijii7uISjv:::KBBBBBbMBBBBBBBBBB.\r\n" + 
	               "\t\t      :BBBBBBQBBBQBBBDBBBBBBBBBBBBSs: BB\r\n" + 
	               "\t\t      BBBBBBBBBBBBQBQBQBBBBBBB YQ  BB  B\r\n" + 
	               "\t\t     .BMDdPBBBPXBBBqEdZdEdZEM:  Bd    EB\r\n" + 
	               "\t\t            iEPdd7              .BBIIBB "
		     
	    };
	    
	    for (int i = 0; i < delivery.length; i++) {
		      // 초 간 중지한다
		      Thread.sleep(450);
		      // 메세지를 출력한다
		      System.out.println(delivery[i]);
		}
	    System.out.println();
	    System.out.println("                         Loading...");
	    
		LoadingThread LoadingThread = new LoadingThread("■■■");
		LoadingThread.start(); // 로딩바
		LoadingThread.join();
	    

		
		// 메뉴출력 > 항목선택 > 기능실행
		boolean loop = true;
		while(loop) {
			Util.clearScreen();
			System.out.println("┌--------------------------------------------------------------------------------┐");
			System.out.println("│                                                                                │");
			System.out.println("│                                  1. 회원                                       │");               
			System.out.println("│                                  2. 비회원                                     │");
			System.out.println("│                                                                                │");
			System.out.println("└--------------------------------------------------------------------------------┘");
			System.out.println("\n");
			
			
			System.out.println("\n\n\t   (종료를 원하시면 0을 입력해주세yo)");
			System.out.print("\n\t\t  이동할 화면 입력(숫자): ");
			String selNum = Util.sc.nextLine();
			//회원
			if ( selNum.equals("1")) {
				MemberMain mUi = MemberMain.getInstance();
				mUi.memberMain();
			//비회원
			} else if (selNum.equals("2")) {
			
			//종료
			} else if (selNum.equals("0")) {
				Util.clearScreen();
				System.out.println("\n\n\t\t\t  종료");
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
				loop = false;
			//잘못입력시
			} else {
				Util.clearScreen();
				System.out.println("\t\t올바르지 않은 접근입니다.");
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println("\t   계속하시려면 엔터키를 입력해주세요");
				Util.sc.nextLine();
			}
		}
	}
}
