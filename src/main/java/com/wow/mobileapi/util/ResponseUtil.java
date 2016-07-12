package com.wow.mobileapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wow.common.error.ErrorRepositoryManager;
import com.wow.common.response.ApiResponse;

/**
 * Created by zhengzhiqing on 16/6/27.
 */
@Component
public class ResponseUtil {

    @Autowired
    private ErrorRepositoryManager errorRepositoryManager;

    public void setResponse(ApiResponse apiResponse, String resCode) {
        apiResponse.setResCode(resCode);
        apiResponse.setResMsg(errorRepositoryManager.getErrorMsg(resCode));
    }
}