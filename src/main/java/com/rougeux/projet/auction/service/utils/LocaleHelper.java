package com.rougeux.projet.auction.service.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LocaleHelper {

    private final MessageSource messageSource;

    public LocaleHelper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String i18N(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
