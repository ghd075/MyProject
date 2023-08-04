package ddit.util;

public class RangeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int start;
	private int end;

	public RangeException() { }

	public RangeException(int start, int end) {
		super(String.format("값의 범위를 %d ~ %d로 지정하세요.", start, end));
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
