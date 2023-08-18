package ddit.util;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ddit.dao.MemberDAO;

/**
 * 공통모듈 클래스
 * */
public class Util {
	// 한국 주소 유효성 검사를 위한 정규표현식
	private static final String KOREAN_ADDRESS_PATTERN =
			"^(?:[가-힣]+(?:시|도))\\s*(?:[가-힣]+(?:구|군|시))\\s*(?:[가-힣0-9\\-]+(?:동|읍|면))\\s*(?:\\d+[가-힣]*\\s*)?\\d+(?:\\-\\d+)?$";
	  		
    // 이름 유효성 검사를 위한 정규표현식
    private static final String KOREAN_NAME_PATTERN = "^[가-힣]+$";
    // 휴대전화 번호 형식을 나타내는 정규표현식
    private static final String PHONE_PATTERN = "^(01[016789])-(\\d{3,4})-(\\d{4})$";
	public static Scanner sc = new Scanner(System.in);
	private static MemberDAO mDao = MemberDAO.getInstance();
	
	/**
	 * 
	 * 콘솔창 클리어
	 * 
	 * */
	/*
	 * public static void clearScreen() { for (int i = 0; i < 32; i++) {
	 * System.out.println(""); } }
	 */
	
	public static void clearScreen() {
        try{
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system
              
            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            } 
        }catch(Exception e){
            System.out.println(e);
        }
    }
	
	/**
	 * 
	 * 
	 * 
	 *비밀번호 유효성 검사 페이지입니다. 조건에 맞는 아이디를 생성하지 않으면 다시 돌아갑니다.
	 * 
	 * 
	 */
	public static String pwCheck() {
		String memberPW = "";
		
		boolean loop = true;
		while (loop) {

			System.out.print("\t비밀 번호 : ");
			memberPW = Util.sc.nextLine();

			int cnt1=0;
			int cnt2=0;
			
			//영문자 대소문자, 숫자 체크
			for(int i=0;i< memberPW.length();i++){
				char ch = memberPW.charAt(i);
				if((ch>='a' && ch<='z') || (ch>='A' && ch<='Z')) {
					cnt1++;
				}else if(ch>='0' && ch<='9') {
					cnt2++;
				}
			}
			
			//PW 길이가 4 ~ 15자 이내의 아이디만 입력할 수 있도록
			if(memberPW.length() < 4 || memberPW.length() > 15) {
				System.out.println("\t4~15자 이내의 아이디만 가능합니다");
			//영문자와 숫자를 혼용해서 입력할 수 있도록
			}else if(cnt1==0 || cnt2==0) {
				System.out.println("\t비밀번호은 영문자와 숫자를 혼용해서 만들어주세요.");
				System.out.println();
			}else {
				loop = false;
			}
			
		}
		return memberPW;
	}
	
	/**
	 * 
	 * 
	 * 
	 *이름 유효성 검사 페이지입니다. 한글을 입력해야 다음 단계로 이동합니다.
	 * 
	 * 
	 */
	public static String nameCheck() {
		String name = "";
		
		boolean loop = true;
		while (loop) {

			System.out.print("\t이름 : ");
			name = sc.nextLine();

			if (!isValidKoreanName(name)) {
				System.out.println("\t이름이 유효하지 않습니다. 한글로 입력해주세요.");
				System.out.println();
			} else {
			loop = false;
			}

		}
		return name;
	}
	
	
	/**
	 * 
	 * 
	 * 
	 *전화번호 유효성 검사 페이지입니다. '-'를 사용하고 13자리를 입력해야 다음 단계로 이동합니다.
	 * 
	 * 
	 */
	public static String phonenumber() {

		String memberPhone = "";
		
		boolean loop = true;
		while (loop) {

			System.out.print("\t전화번호 ('-' 포함) : ");
			memberPhone = sc.nextLine();

			if (!isValidMobilePhoneNumber(memberPhone)) {
				System.out.println("\t전화번호가 유효하지 않습니다. 올바른 전화번호를 입력해주세요.");
				System.out.println("\tex)010-0000-0000형태로 입력해주세요.");
				System.out.println();
			} else {
				loop = false;
			}
		}
		return memberPhone;

	} // phonenumber
	
	/**
	 * 
	 * 
	 * @document 주소입력하는 페이지입니다.
	 * 
	 * 
	 */
	public static String Address() {
		String memberAddress = "";
		
		boolean loop = true;
		while (loop) {
			System.out.print("\t주소 : ");
			memberAddress = sc.nextLine();
			if (!isValidKoreanAddress(memberAddress)) {
				System.out.println("\t주소가 유효하지 않습니다. 올바른 주소를 입력해주세요.");
				System.out.println("\tex)서울특별시 강남구 역삼동 123-45 형태로 입력해주세요.");
				System.out.println();
			} else {
				loop = false;
			}

		}

		return memberAddress;
		
	}//Address
	
	/**
	 * 정규표현식을 활용한 집주소 유효성 검사
	 */
    public static boolean isValidKoreanAddress(String address) {
        Pattern pattern = Pattern.compile(KOREAN_ADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }
    
	/**
	 * 정규표현식을 활용한 이름 유효성 검사
	 */
    public static boolean isValidKoreanName(String name) {
        Pattern pattern = Pattern.compile(KOREAN_NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    
	/**
	 * 정규표현식을 활용한 전화번호 유효성 검사
	 */
    public static boolean isValidMobilePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        
        return matcher.matches();
    }
	
	 /** 
	 * 
	 * @document 아이디 유효성 검사 페이지입니다. 조건에 맞는 아이디를 생성하지 않으면 다시 돌아갑니다.
	 * 
	 * 
	 */
	public static String createid() {
		
		String memberID = "";
		

		boolean loop = true;
		while (loop) {

			System.out.print("\t사용할 아이디 : ");
			memberID = sc.nextLine();
			
			int cnt1=0;
			int cnt2=0;
			
			//영문자 대소문자, 숫자 체크
			for(int i=0;i< memberID.length();i++){
				char ch = memberID.charAt(i);
				if((ch>='a' && ch<='z') || (ch>='A' && ch<='Z')) {
					cnt1++;
				}else if(ch>='0' && ch<='9') {
					cnt2++;
				}
			}
			
			//중복ID 체크
			if(mDao.checkId(memberID)) {
				System.out.println();
				System.out.println("\t중복된 아이디입니다.");
				System.out.println();
			//ID 길이가 5 ~ 15자 이내의 아이디만 입력할 수 있도록
			}else if(memberID.length() < 5 || memberID.length() > 15) {
				System.out.println("\t5~15자 이내의 아이디만 가능합니다");
			//영문자와 숫자를 혼용해서 입력할 수 있도록
			}else if(cnt1==0 || cnt2==0) {
				System.out.println("\t아이디는 영문자와 숫자를 혼용해서 만들어주세요.");
				System.out.println();
			}else {
				loop = false;
			}
		}

		return memberID;

	} // createid
	
	// 전각문자 개수를 세주는 메서드
	private static int getKorCnt(String kor) {
		int cnt = 0;
		for (int i = 0 ; i < kor.length() ; i++) {
			if (kor.charAt(i) >= '가' && kor.charAt(i) <= '힣') {
				cnt++;
			}
		} return cnt;
	}
	
	//전각문자의 개수만큼 문자열을 조정해주는 메서드
	public static String convert(String word, int size) {
		int korCnt = getKorCnt(word);
		int spaceCount = size - korCnt - word.length(); // 공백의 개수 계산
		if(spaceCount >= 0) {
	        String formatter = String.format("%%-%ds", size - korCnt);
	        return String.format(formatter, word);			
		} else {
			// 처리할 수 없는 경우에 대한 예외 처리 또는 기본값 반환 등을 고려해보세요.
			return word; // 일단은 원래 단어 그대로 반환하는 예시입니다.
		}
	}
}
