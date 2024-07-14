package com.patika.bloghubservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String USER_NOT_FOUND = "kullanıcı bulunamadı";
    public static final String USER_ALREADY_DEFINED = "bu email ile kayıtlı kullanıcı bulundu";
    public static final String USER_EMAIL_CAN_NOT_BE_EMPTY = "email alanı boş olamaz";

}
