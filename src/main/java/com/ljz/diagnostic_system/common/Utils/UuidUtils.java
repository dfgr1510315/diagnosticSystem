package com.ljz.diagnostic_system.common.Utils;

import java.util.UUID;

public class UuidUtils {
    public static String creatUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
