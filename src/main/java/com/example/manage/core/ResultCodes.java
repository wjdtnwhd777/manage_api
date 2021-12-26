package com.example.manage.core;

import org.springframework.lang.Nullable;

public enum ResultCodes {
    OK(1000, "요청 성공"),

    UNAUTHORIZED(401, "인증이 필요한 요청입니다."),
    FORBIDDEN(403, "해당 요청에 대한 권한이 없습니다."),
    BAD_REQUEST_WTIH_INAVLID_PARAMS(400, "잘못된 요청입니다."),
    BAD_REQUEST(405, "허용되지 않는 호출 방식입니다."),

    METHOD_NOT_ALLOWED(405, "허용되지 않는 호출 방식입니다."),

    ROLE_NOT_FOUND_EXCEPTION(1001, "해당 권한을 찾을 수 없습니다."),

    USER_EXISTS_EXCEPTION(2001, "이미 등록된 이메일 입니다."),
    USER_NOT_FOUND_EXCEPTION(2002, "사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCHED_EXCEPTION(2101, "비밀번호가 일치하지 않습니다."),
    EMAIL_NOT_MATCHED_EXCEPTION(2102, "이메일 정보가 일치하지 않습니다."),
    SESSION_NOT_FOUND_EXCEPTION(2201, "세션 정보를 찾을 수 없습니다."),
    SESSION_EXPIRED_EXCEPTION(2202, "유효한 세션이 아닙니다."),

    GOODS_NOT_FOUND_EXCEPTION(3001, "등록된 상품이 없습니다."),
    GOODS_NOT_REGISTER_EXCEPTION(3101, "상품 등록에 실패했습니다."),


    ORDER_NOT_FOUND_EXCEPTION(4001, "주문한 상품이 없습니다."),
    ORDER_NOT_REGISTER_EXCEPTION(4101, "상품 주문을 실패하였습니다."),

    INTERNAL_SERVER_ERROR(9999, "서버 내부 오류");


    private final int value;
    private final String reasonPhrase;

    private ResultCodes(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public String toString() {
        return this.value + " " + this.name();
    }

    public static ResultCodes valueOf(int statusCode) {
        ResultCodes codes = resolve(statusCode);
        if(codes == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        } else {
            return codes;
        }
    }

    @Nullable
    public static ResultCodes resolve(int resultCode) {
        ResultCodes[] codes = values();
        int length = codes.length;

        for(int index = 0; index < length; ++index) {
            ResultCodes code = codes[index];
            if (code.value == resultCode) {
                return code;
            }
        }

        return null;
    }
}
