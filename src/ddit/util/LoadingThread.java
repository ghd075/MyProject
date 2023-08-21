package ddit.util;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class LoadingThread extends Thread{

    FormattedWriter out = new FormattedWriter(
            new BufferedWriter(
                new OutputStreamWriter(System.out)), true, 90,//150만큼공간을 준다
                       FormattedWriter.CENTER_JUSTIFIED);

    public LoadingThread(){ // 생성자
    }

    public void run(){ // run 메서드는 수행 흐름이 하나 더 생겼을 때의 메서드이다. 
    	String loadingBar = "■■■";

    	for(int i = 0; i <22; i ++){
        	
    		System.out.print(loadingBar);
            try {
                //컴퓨터가 너무 빠르기 때문에 수행결과를 잘 확인 할 수 없어서 
                // Thread.sleep() 메서드를 이용해서 조금씩 쉬었다가 출력할 수 있게한다. 
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace(); // e.printStackTrace(); 메소드 getMessage, toString과는 다르게 printStackTrace는 리턴값이 없다. 이 메소드를 호출하면 메소드가 내부적으로 예외 결과를 화면에 출력한다.
            }
        }   
    	
    	Util.clearScreen();
    	
		System.out.println("\n\n");
	  	String[] strings = {
	    			 "┌--------------------------------------------------------------------------------┐"
	    			 ,	"│                                                                                │"
	    			 ,	"│                   대덕의 민족                                                  │"
	    			 ,	"│                                     Created By 대덕 on 2023.08.10              │"
	    			 ,	"└--------------------------------------------------------------------------------┘"
	    	 };
    	 
 	   for (int i = 0; i < strings.length; i++) {
		   out.println();
		   out.print(strings[i]);
	    }
 	   out.flush();
       
       // 아스키 아트 문자열 생성
       // 아스키 아트 문자열 생성
       String asciiArt = "      _        _  _                           \n" +
                         "     | |      | |(_)                          \n" +
                         "   __| |  ___ | | _ __   __  ___  _ __  _   _ \n" +
                         "  / _` | / _ \\| || |\\ \\ / / / _ \\| '__|| | | |\n" +
                         " | (_| ||  __/| || | \\ V / |  __/| |   | |_| |\n" +
                         "  \\__,_| \\___||_||_|  \\_/   \\___||_|    \\__, |\n" +
                         "                                         __/ |\n" +
                         "                                        |___/ ";

       // 아스키 아트 문자열을 각 줄로 분할
       String[] lines = asciiArt.split("\n");

       // 화면의 가로 중앙 위치 계산
       int screenWidth = 80; // 예시로 화면 가로 길이를 80으로 가정합니다.
       int maxLength = lines[0].length(); // 첫 번째 줄의 길이로 초기화
       for (String line : lines) {
           maxLength = Math.max(maxLength, line.length());
       }
       int paddingCount = (screenWidth - maxLength) / 2;

       System.out.println("\r\n\n\n");
       // 중앙 정렬하여 출력
       for (String line : lines) {
           String padding = new String(new char[paddingCount]).replace('\0', ' ');
           System.out.println(padding + line);
       }
       System.out.println("\r\n\n\n\t\t어서와.. 배달은 처음이지..?\n\n");
       out.flush();
       out.println("\n\n\t   계속하시려면 엔터를 입력해주세요)");
       out.flush();
       Util.sc.nextLine();
       out.println("\t        PRESS ENTER TO CONTUNUE...");
       out.flush();
       Util.sc.nextLine();

    }
	
}

/**2. Runnable 인터페이스를 implements 하는 방법이다.
  다른 클래스를 extends하여 Thread클래스를 상속하지 못 하는 경우에 Runnable을 구현하여 쓰레드를 생성한다.
  extends는 하나의 클래스에 한개만 가능하다.
  인터페이스는 implements를 통하여 다중 상속도 가능하다는 장점이 있습니다 
  하지만 구현부가 없어 아무런 기능도 하지 않습니다 
  또한 내부 함수들을 모두 오버라이딩 해주어야 합니다.
 */