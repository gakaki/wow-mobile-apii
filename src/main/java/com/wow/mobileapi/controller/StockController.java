package com.wow.mobileapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wow.common.request.ApiRequest;
import com.wow.common.response.CommonResponse;
import com.wow.common.util.JsonUtil;
import com.wow.common.util.StringUtil;
import com.wow.common.util.ValidatorUtil;
import com.wow.mobileapi.request.stock.StockBatchQueryRequest;
import com.wow.mobileapi.request.stock.StockQueryRequest;
import com.wow.mobileapi.request.user.UserQueryRequest;
import com.wow.stock.vo.AvailableStockVo;
import com.wow.stock.vo.response.AvailableStockResponse;
import com.wow.stock.vo.response.AvailableStocksResponse;
import com.wow.user.vo.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wow.common.response.ApiResponse;
import com.wow.mobileapi.util.ResponseUtil;
import com.wow.stock.service.StockService;

/**
 * Created by zhengzhiqing on 16/6/23.
 */
@RestController
public class StockController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;
    @Autowired
    private ResponseUtil responseUtil;

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ApiResponse getProductWarehouses(@PathVariable Integer productId) {
        logger.info("start to get warehouses in which product stored");
        ApiResponse apiResponse = new ApiResponse();
        List<Integer> warehouseList = stockService.selectWarehouseByProductId(productId);
        responseUtil.setResponse(apiResponse,"0");
        apiResponse.setData(warehouseList);
        return apiResponse;
    }

    /**
     * 根据productId查找可用库存
     * @param apiRequest
     * @return
     */
    @RequestMapping(value = "/v1/stock/available", method = RequestMethod.GET)
    public ApiResponse getAvailableStock(ApiRequest apiRequest) {
        ApiResponse apiResponse = new ApiResponse();
        StockQueryRequest stockQueryRequest = JsonUtil.fromJSON(apiRequest.getParamJson(), StockQueryRequest.class);
        //判断json格式参数是否有误
        if (stockQueryRequest == null) {
            setParamJsonParseErrorResponse(apiResponse);
            return apiResponse;
        }

        String errorMsg = ValidatorUtil.getError(stockQueryRequest);
        //如果校验错误 则返回
        if (StringUtil.isNotEmpty(errorMsg)) {
            setInvalidParameterResponse(apiResponse, errorMsg);
            return apiResponse;
        }

        int productId = stockQueryRequest.getProductId();

        try {
            AvailableStockResponse availableStockResponse = stockService.getAvailableStock(productId);
            //如果处理失败 则返回错误信息
            if (!isServiceCallSuccess(availableStockResponse.getResCode())) {
                setServiceErrorResponse(apiResponse, availableStockResponse);
            } else {
                AvailableStockVo availableStockVo = availableStockResponse.getAvailableStockVo();
                removeDuplicateResponse(availableStockVo);
                apiResponse.setData(availableStockVo);
            }
        } catch (Exception e) {
            logger.error("查找可用库存发生错误---" + e);
            e.printStackTrace();
            setInternalErrorResponse(apiResponse);
        }
        return apiResponse;
    }

    /**
     * 根据productId查找可用库存
     * @param apiRequest
     * @return
     */
    @RequestMapping(value = "/v1/stocks/available", method = RequestMethod.GET)
    public ApiResponse getAvailableStocks(ApiRequest apiRequest) {
        ApiResponse apiResponse = new ApiResponse();
        StockBatchQueryRequest stockBatchQueryRequest = JsonUtil.fromJSON(apiRequest.getParamJson(), StockBatchQueryRequest.class);
        //判断json格式参数是否有误
        if (stockBatchQueryRequest == null) {
            setParamJsonParseErrorResponse(apiResponse);
            return apiResponse;
        }

        String errorMsg = ValidatorUtil.getError(stockBatchQueryRequest);
        //如果校验错误 则返回
        if (StringUtil.isNotEmpty(errorMsg)) {
            setInvalidParameterResponse(apiResponse, errorMsg);
            return apiResponse;
        }

        List<Integer> productIdList = stockBatchQueryRequest.getProductIdList();

        try {
            AvailableStocksResponse availableStocksResponse = stockService.batchGetAvailableStock(productIdList);
            //如果处理失败 则返回错误信息
            if (!isServiceCallSuccess(availableStocksResponse.getResCode())) {
                setServiceErrorResponse(apiResponse, availableStocksResponse);
            } else {
                Map<Integer, AvailableStockVo> availableStockVoMap = availableStocksResponse.getAvailableStockVoMap();
                for (Map.Entry<Integer, AvailableStockVo> entry: availableStockVoMap.entrySet()) {
                    AvailableStockVo availableStockVo = entry.getValue();
                    removeDuplicateResponse(availableStockVo);
                }
                apiResponse.setData(availableStockVoMap);
            }
        } catch (Exception e) {
            logger.error("批量查找可用库存发生错误---" + e);
            e.printStackTrace();
            setInternalErrorResponse(apiResponse);
        }
        return apiResponse;
    }
}
