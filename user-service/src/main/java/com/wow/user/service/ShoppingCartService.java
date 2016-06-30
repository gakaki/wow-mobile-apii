package com.wow.user.service;

import com.wow.user.model.ShoppingCart;

import java.util.List;

/**
 * Created by zhengzhiqing on 16/6/30.
 */
public interface ShoppingCartService {
    /**
     * 添加商品到购物车
     * @param shoppingCart
     * @return
     */
    int addProductIntoCart(ShoppingCart shoppingCart);

    /**
     * 修改购物车里的产品
     * @param shoppingCart
     * @return
     */
    int updateProductInCart(ShoppingCart shoppingCart);

    /**
     * 从购物车里删除产品
     * @param shoppingCartIds
     * @return
     */
    int removeProductsFromCart(List<Integer> shoppingCartIds);

    /**
     * 查询购物车信息
     * @param endUserId
     * @return
     */
    List<ShoppingCart> queryShoppingCart(int endUserId);
}
