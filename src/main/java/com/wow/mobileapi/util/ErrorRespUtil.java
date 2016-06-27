package com.wow.mobileapi.util;

import com.wow.mobileapi.dto.ApiResponse;
import com.wow.mobileapi.exception.ApiErrorCode;

/**
 * Created by zhengzhiqing on 16/6/27.
 */
public class ErrorRespUtil {
    public static void setError(ApiResponse apiResponse, String resCode) {
        apiResponse.setResCode(resCode);
        apiResponse.setResMsg(ApiErrorCode.getErrorMsg(resCode));
    }
}

