package com.wow.mobileapi.request.user;

import com.wow.common.request.ApiRequest;

import javax.validation.constraints.NotNull;

/**
 * Created by zhengzhiqing on 16/6/28.
 */
public class LoginByWechatRequest extends ApiRequest {

    /**  */
    private static final long serialVersionUID = 1L;
    @NotNull
    private String openId;
    @NotNull
    private Byte loginChannel;

    public Byte getLoginChannel() {
        return loginChannel;
    }

    public void setLoginChannel(Byte loginChannel) {
        this.loginChannel = loginChannel;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
