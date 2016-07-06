package com.wow.product.service.impl;

import com.wow.product.mapper.GroupMapper;
import com.wow.product.mapper.ProductGroupMapper;
import com.wow.product.model.*;
import com.wow.product.service.ProductGroupService;
import com.wow.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by fangying@wowdsgn on 2016/7/4.
 */
public class ProductGroupServiceImpl implements ProductGroupService {
    @Autowired
    private ProductGroupMapper productGroupMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private ProductService productService;
    @Override
    public int createGroup(Group group) {
        return   groupMapper.insertSelective(group);
    }

    @Override
    public List<Group> getGroupsByGroupType(int groupType) {
        GroupExample groupExample=new GroupExample();
        GroupExample.Criteria criteria=groupExample.createCriteria();
        criteria.andGroupTypeEqualTo((byte)groupType);
        criteria.andIsActiveEqualTo(true);
        criteria.andIsDeletedEqualTo(false);
        return groupMapper.selectByExample(groupExample);
    }

    @Override
    public int addProductIntoGroup(int productId, int groupId) {
        ProductGroup productGroup=new ProductGroup();
        productGroup.setProductId(productId);
        productGroup.setGroupId(groupId);
        productGroup.setIsDeleted(false);
        productGroup.setCreateTime(new java.util.Date());
        productGroup.setUpdateTime(new java.util.Date());
        productGroup.setCreateBy("");
        productGroup.setUpdateBy("");
        return productGroupMapper.insertSelective(productGroup);
    }

    @Override
    public int addProductsIntoGroup(int[] productId, int groupId) {
        if(productId.length>0)
        {
            for (int item:productId) {
                addProductIntoGroup(item,groupId);
            }
        }
        return 0;
    }

    @Override
    public List<Product> getProductsInGroup(int groupId) {
        List<ProductGroup> list= getProductsGroupByGroupId(groupId);
        if(!list.isEmpty())
        {
            List<Product> listProduct=new ArrayList<>();
            list.forEach( new Consumer<ProductGroup>() {
                @Override
                public void accept(ProductGroup productGroup) {
                    Product product= productService.getProductById(productGroup.getProductId());
                    if(product!=null)
                        listProduct.add(product);
                }
            });
          return listProduct;
        }
        return null;
    }

    private List<ProductGroup> getProductsGroupByGroupId(int groupId) {
        ProductGroupExample productGroupExample=new ProductGroupExample();
        ProductGroupExample.Criteria criteria=productGroupExample.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        criteria.andIsDeletedEqualTo(false);
        return productGroupMapper.selectByExample(productGroupExample);
    }
}
