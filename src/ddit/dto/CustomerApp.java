package ddit.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerApp {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "your_username";
        String password = "your_password";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("고객 구분을 입력하세요 (1: 회원, 2: 비회원)");
            String selNum = scanner.nextLine();

            // 회원
            if (selNum.equals("1")) {
                System.out.println("아이디를 입력하세요:");
                String id = scanner.nextLine();
                System.out.println("비밀번호를 입력하세요:");
                String password1 = scanner.nextLine();

                boolean registered = registerAsMember(connection, id, password1);
                if (registered) {
                    System.out.println("회원 가입 성공");
                } else {
                    System.out.println("회원 가입 실패");
                }

            // 비회원
            } else if (selNum.equals("2")) {
                boolean registered = registerAsNonMember(connection);
                if (registered) {
                    System.out.println("비회원 가입 성공");
                } else {
                    System.out.println("비회원 가입 실패");
                }

            // 종료
            } else {
                System.out.println("잘못된 선택입니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerAsMember(Connection connection, String id, String password) throws SQLException {
        String customerNumber = generateCustomerNumber();
        String insertQuery = "INSERT INTO 고객테이블 (고객번호, 고객구분, 이름, 전화번호, 주소) VALUES (?, 1, NULL, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, customerNumber);
            pstmt.setString(2, "123-456-7890"); // 전화번호
            pstmt.setString(3, "Sample Address"); // 주소
            pstmt.executeUpdate();

            String insertMemberQuery = "INSERT INTO 회원테이블 (아이디, 패스워드, 포인트, 고객번호) VALUES (?, ?, 500, ?)";
            try (PreparedStatement pstmtMember = connection.prepareStatement(insertMemberQuery)) {
                pstmtMember.setString(1, id);
                pstmtMember.setString(2, password);
                pstmtMember.setString(3, customerNumber);
                pstmtMember.executeUpdate();
            }

            return true;
        }
    }

    public static boolean registerAsNonMember(Connection connection) throws SQLException {
        String customerNumber = generateCustomerNumber();
        String insertQuery = "INSERT INTO 고객테이블 (고객번호, 고객구분, 이름, 전화번호, 주소) VALUES (?, 2, NULL, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, customerNumber);
            pstmt.setString(2, "123-456-7890"); // 전화번호
            pstmt.setString(3, "Sample Address"); // 주소
            pstmt.executeUpdate();

            String insertNonMemberQuery = "INSERT INTO 비회원테이블 (고객번호) VALUES (?)";
            try (PreparedStatement pstmtNonMember = connection.prepareStatement(insertNonMemberQuery)) {
                pstmtNonMember.setString(1, customerNumber);
                pstmtNonMember.executeUpdate();
            }

            return true;
        }
    }

    // 실제로는 고객번호를 생성하는 로직을 구현해야 합니다.
    private static String generateCustomerNumber() {
        // 간단한 예시로 랜덤한 번호를 생성하도록 합니다.
        return "CST" + (int) (Math.random() * 1000);
    }
}
