package api.exam.io.read.info.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /**
     * COMMON
     */
    INVALID_INPUT_VALUE(400, "C003", "잘못된 입력입니다."),

    /**
     * MEMBER
     */
    ALREADY_PAUSED_ACCOUNT(410, "M001", "이미 탈퇴된 계정입니다."),
    MEMBER_NOT_FOUND(400, "M002", "찾을 수 없는 멤버입니다."),

    /**
     * AUTH
     */
    AUTH_INFO_MISMATCHED(421, "A001", "로그인 정보가 일치하지 않습니다."),
    ALREADY_EXPIRED_TOKEN(403, "A002", "이미 만료된 토큰입니다."),

    /**
     * CATEGORY
     */
    CATEGORY_NOT_FOUND(400, "CA001", "찾을 수 없는 카테고리입니다."),

    /**
     * PRODUCT
     */
    PRODUCT_NOT_FOUND(400, "P001", "찾을 수 없는 상품입니다."),

    /**
     * MULTIPART
     */
    CAN_NOT_SAVE_FILE(501, "F001", "파일을 저장할 수 없습니다."),
    DATA_NOT_FOUND(400, "F002", "파일의 메타데이터가 존재하지 않습니다.");
    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
