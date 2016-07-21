package com.wow.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 订单结算项目vo
 * 
 * @author chenkaiwei
 * @version $Id: V1.0 2016年7月15日 下午6:21:56 Exp $
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrderSettleVo implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;

    //购物车id
    private Integer shoppingCartId;

    //用户购买的产品id
    private Integer productId;

    //用户购买的产品数量 范围为1-127
    private Byte productQty;

    //产品名称
    private String productName;
    
    //产品销售价格 单价
    private BigDecimal sellPrice;
    
    //产品总价 产品销售价乘以数量
    private BigDecimal sellTotalAmount;
    
    //产品库存数目
    private Short productStock;

    //产品颜色
    private String color;

    //产品规格值
    private String spec;

    //产品状态
    private Byte productStatus;

    //产品状态名称
    private String productStatusName;
    
    public BigDecimal getSellTotalAmount() {
        return sellTotalAmount;
    }

    public void setSellTotalAmount(BigDecimal sellTotalAmount) {
        this.sellTotalAmount = sellTotalAmount;
    }

    public Short getProductStock() {
        return productStock;
    }

    public void setProductStock(Short productStock) {
        this.productStock = productStock;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductStatusName() {
        return productStatusName;
    }

    public void setProductStatusName(String productStatusName) {
        this.productStatusName = productStatusName;
    }

    public Byte getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Byte productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Byte getProductQty() {
        return productQty;
    }

    public void setProductQty(Byte productQty) {
        this.productQty = productQty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }
}
