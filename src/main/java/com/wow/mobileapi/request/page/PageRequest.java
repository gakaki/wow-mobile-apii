package com.wow.mobileapi.request.page;

import com.wow.common.request.ApiRequest;

/**
 * Created by zhengzhiqing on 16/7/13.
 */
public class PageRequest extends ApiRequest {

    private static final long serialVersionUID = 1L;

    private byte pageType;

    public byte getPageType() {
        return pageType;
    }

    public void setPageType(byte pageType) {
        this.pageType = pageType;
    }
}
