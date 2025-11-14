package com.rougeux.projet.auction.api;

import com.rougeux.projet.auction.service.utils.LocaleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ApiResponseFactory {

    private static LocaleHelper localeHelper;

    @Autowired
    private ApiResponseFactory(LocaleHelper localeHelper) {
        ApiResponseFactory.localeHelper = localeHelper;
    }
    public static <T> ApiResponse<T> success(String code, String messageKey, T data) {
        return new ApiResponse<>(code, localeHelper.i18N(messageKey), data);
    }

    public static <T> ApiResponse<T> error(String code, String messageKey) {
        return new ApiResponse<>(code, localeHelper.i18N(messageKey), null);
    }
}
