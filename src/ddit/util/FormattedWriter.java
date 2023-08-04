package ddit.util;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * 문자열 가운데 정렬 클래스
 *
 */
public class FormattedWriter extends PrintWriter {
	  public final static int LEFT_JUSTIFIED  = 1;
	  public final static int RIGHT_JUSTIFIED = 2;
	  public final static int CENTER_JUSTIFIED = 3;
	  private int justification = RIGHT_JUSTIFIED;

	  private int width = 0;                						//  width 선언

	  // width, autoflush, justification 을 생성 하고 정의
	  public FormattedWriter(Writer output, boolean autoflush, int width, int justification) {
		  super(output, autoflush);     //  PrintWriter 호출
		  if(width>0)
			  this.width = width;           //  field width 저장
		  if(justification == LEFT_JUSTIFIED || justification == RIGHT_JUSTIFIED || justification == CENTER_JUSTIFIED )
			  this.justification = justification;
	  }

	  // width와 justification을 생성.정의
	  public FormattedWriter(Writer output, int width, int justification) {
	    this(output, true, width, justification);       
	  }
	 
	  private String pad(String str) {
		  if (width == 0) {
			  return str;
		  }
		  int blanks = width - str.length();         // 공백의 필요한 숫자
		  StringBuffer result = new StringBuffer(); 

		  if(blanks<0) {                             
			  for(int i = 0 ; i<width ; i++)
				  result.append('X');                   
			  return result.toString();                //수치가 맞지 않으면append('X') 그리고 결과를 반환     
		  }

		  if(blanks>0)
			  result.append(' ');                  

		  if(justification == LEFT_JUSTIFIED) {
			  result.insert(0, str);
		  } else if(justification == RIGHT_JUSTIFIED) {
			  result.insert(result.length(), str);
		  } else if(justification == CENTER_JUSTIFIED) {
			  result.insert(result.length()/2, str);
		  }
		  return result.toString();
	  } //가운데 정렬을 해줄수 있도록 한다.
	  
	  public void print(String str) {
		  super.print(pad(str));  
	  }
	 
	  public void println(String str) {
		  super.println(pad(str));  
	  }
}
