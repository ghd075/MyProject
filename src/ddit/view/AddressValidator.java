package ddit.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 주소 유효성검사 테스트 클래스
 * */
public class AddressValidator {
    private static final String COMPLEX_ADDRESS_PATTERN =
            "^(?:[가-힣]+(?:시|도))\\s*(?:[가-힣]+(?:구|군|시))\\s*(?:[가-힣0-9\\-]+(?:동|읍|면))\\s*(?:\\d+[가-힣]*\\s*)?\\d+(?:\\-\\d+)?$";

    public static boolean isValidComplexAddress(String address) {
        Pattern pattern = Pattern.compile(COMPLEX_ADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String[] addresses = {
            "대전시 서구 복수동 109-12",
            "경상북도 구미시 진평동 102",
            "서울특별시 강남구 역삼동 123-45 789호",
            "부산광역시 해운대구 우동 456-78"
        };

        for (String address : addresses) {
            boolean isValid = isValidComplexAddress(address);
            System.out.println(address + " is valid: " + isValid);
        }
    }
}