package com.wow.product.service.impl;


import com.wow.attribute.model.Attribute;
import com.wow.attribute.model.Category;
import com.wow.attribute.service.AttributeService;
import com.wow.attribute.service.CategoryService;
import com.wow.attribute.vo.response.CategoryResponse;
import com.wow.product.mapper.*;
import com.wow.product.model.*;
import com.wow.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;


/**
 * 产品服务
 * Created by zhengzhiqing on 16/6/16.
 */
@Service
@Transactional(value = "productTransactionManager")
public class ProductServiceImpl implements ProductService {

    //table: product
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    AttributeService attributeService;

    @Autowired
    private ProductAttributeMapper productAttributeMapper;

    @Autowired
    private  ProductMaterialMapper productMaterialMapper;

    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 创建产品(注意要调用生码接口)
     *
     * @param product
     */
    public int createProduct(Product product) {
        if(product==null)
            return -1;
        String productCode = generateProductCode();
        product.setProductCode(productCode.substring(0,7));
        return productMapper.insertSelective(product);
    }

    /**
     * 生成产品编码
     * @return
     */
    private String generateProductCode() {

        return java.util.UUID.randomUUID().toString();
    }


    /**
     * 根据ID查询产品
     *
     * @param productId
     * @return
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public Product getProductById(int productId) {
        return productMapper.selectByPrimaryKey(productId);
    }

    public List<Product> getProductById(List<Integer> productIds)
    {
        ProductExample productExample=new ProductExample();
        productExample.or().andBrandIdIn(productIds).andIsDeletedEqualTo(false);
        return productMapper.selectByExample(productExample);
    }
    @Override
    public List<Product> getProductByBrandId(int brandId) {
        ProductExample productExample=new ProductExample();
        productExample.or().andBrandIdEqualTo(brandId).andIsDeletedEqualTo(false);
        return productMapper.selectByExample(productExample);
    }

    /**
     * 更新产品
     *
     * @param product
     * @return
     */
    public int updateProduct(Product product) {
        return productMapper.updateByPrimaryKeySelective(product);
    }

    //table: product_image

    /**
     * 添加产品图片
     *
     * @param productImage
     * @return
     */
    public int addProductImage(ProductImage productImage) {
        return productImageMapper.insertSelective(productImage);
    }

    /**
     * 批量添加产品图片
     *
     * @param productImages
     * @return
     */
    public int addProductImages(List<ProductImage> productImages) {
        if(!productImages.isEmpty())
            for(ProductImage productImage:productImages)
            {
                addProductImage(productImage);
            }
        return 0;
    }

    /**
     * 查找产品图片
     *
     * @param productId
     * @return
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public List<ProductImage> getProductImages(int productId) {
        ProductImageExample productImageExample=new ProductImageExample();
        ProductImageExample.Criteria criteria=productImageExample.createCriteria();
        criteria.andProductIdEqualTo(productId);
        criteria.andIsDeletedEqualTo(false);
        return  productImageMapper.selectByExample(productImageExample);
    }

    /**
     * 修改产品图片
     *
     * @param productImage
     * @return
     */
    public int updateProductImage(ProductImage productImage) {
        return productImageMapper.updateByPrimaryKeySelective(productImage);
    }

    /**
     * 删除产品图片
     *
     * @param productImages
     * @return
     */
    public int deleteProductImages(List<ProductImage> productImages) {
        if(!productImages.isEmpty()) {
            for(ProductImage productImage:productImages)
            {
                productImage.setIsDeleted(true);
                updateProductImage(productImage);
            }

        }
        return 0;
    }

    //table: product_attribute

    /**
     * 查询产品的属性:根据product找category,根据category找category_attributes
     *
     * @param product
     * @return
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public List<Attribute> getAttributesInProduct(Product product){
        if(product==null)
            return null;
        CategoryResponse categoryResponse= categoryService.getCategoryById(product.getCategoryId());
        Category category = categoryResponse.getCategory();
        if(category!=null)
           return attributeService.getAttributesInCategory(category.getId());

        return null;
    }

    /**
     * 批量设置产品的属性值
     *
     * @param productAttributes
     * @return
     */
    public int createProductAttribute(List<ProductAttribute> productAttributes) {
        if(!productAttributes.isEmpty()) {
            for(ProductAttribute productAttribute:productAttributes)
            {
                productAttributeMapper.insertSelective(productAttribute);
            }
        }
        return 0;
    }

    /**
     * 批量更新产品的属性值
     *
     * @param productAttributes
     * @return
     */
    public int updateProductAttribute(List<ProductAttribute> productAttributes) {
        if(!productAttributes.isEmpty()) {
            for(ProductAttribute productAttribute:productAttributes)
            {
                productAttributeMapper.updateByPrimaryKeySelective(productAttribute);
            }
        }
        return 0;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public List<Material> getMaterialInProduct(Integer productId) {

            ProductMaterialExample productMaterialExample=new ProductMaterialExample();
            productMaterialExample.or().andIsDeletedEqualTo(false).andProductIdEqualTo(productId);
           List<ProductMaterial> productMaterials=productMaterialMapper.selectByExample(productMaterialExample);
         if(!productMaterials.isEmpty())
         {
             HashSet<Integer> materialIds=new HashSet<>();
             for(ProductMaterial productMaterial:productMaterials)
             {
                 materialIds.add(productMaterial.getMaterialId());
             }
             return getMaterialById(new ArrayList<>(materialIds));
         }

        return null;
    }

    private  List<Material> getMaterialById(List<Integer> ids)
    {
        MaterialExample example=new MaterialExample();
        example.or().andIdIn(ids).andIsDeletedEqualTo(false);
        return materialMapper.selectByExample(example);
    }
    @Override
    public int createProductMaterial(List<ProductMaterial> productMaterials) {
        if(!productMaterials.isEmpty())
            for(ProductMaterial productMaterial:productMaterials)
            {
                productMaterialMapper.insertSelective(productMaterial);
            }
        return 0;
    }

    @Override
    public int updateProductMaterial(List<ProductMaterial> productMaterials) {
        if(!productMaterials.isEmpty())
            for(ProductMaterial productMaterial:productMaterials)
            {
                productMaterialMapper.updateByPrimaryKeySelective(productMaterial);
            }
        return 0;
    }



}
