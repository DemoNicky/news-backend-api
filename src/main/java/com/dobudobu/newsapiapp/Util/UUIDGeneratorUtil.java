package com.dobudobu.newsapiapp.Util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGeneratorUtil {

    public String getUUIDCode() {
        UUID uuid = UUID.randomUUID();
        String kode = uuid.toString().substring(0, 6);
        return kode;
    }

}
