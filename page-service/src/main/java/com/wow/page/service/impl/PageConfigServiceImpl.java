package com.wow.page.service.impl;

import com.wow.common.util.CollectionUtil;
import com.wow.common.util.ErrorCodeUtil;
import com.wow.page.mapper.*;
import com.wow.page.model.*;
import com.wow.page.model.PageSceneConfig;
import com.wow.page.service.PageConfigService;
import com.wow.page.vo.PageCategoryVo;
import com.wow.page.vo.PageProductVo;
import com.wow.page.vo.PageTopicVo;
import com.wow.page.vo.ProductImageVo;
import com.wow.page.vo.response.*;
import com.wow.price.model.ProductPrice;
import com.wow.price.service.PriceService;
import com.wow.price.vo.ProductPriceResponse;
import com.wow.product.model.*;
import com.wow.product.service.ProductService;
import com.wow.product.service.SceneService;
import com.wow.product.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;


/**
 * Created by zhengzhiqing on 16/6/23.
 */
@Service
@Transactional("pageTransactionManager")
public class PageConfigServiceImpl implements PageConfigService {

    private static final Logger logger = LoggerFactory.getLogger(PageConfigServiceImpl.class);


    @Autowired
    private PageBannerConfigMapper pageBannerConfigMapper;
    @Autowired
    private PageSceneConfigMapper pageSceneConfigMapper;
    @Autowired
    private PageTopicConfigMapper pageTopicConfigMapper;
    @Autowired
    private PageProductConfigMapper pageProductConfigMapper;
    @Autowired
    private PageCategoryConfigMapper pageCategoryConfigMapper;

    @Autowired
    private TopicService topicService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private PriceService priceService;

    /**
     * 根据页面类型查询应该显示的Banner
     * @param pageType
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
//    @Cacheable(value = "PageCache",key="'BANNERS_IN_PAGE_TYPE_'+#pageType")
    public PageBannerResponse getBannersByPageType(byte pageType) {
        PageBannerResponse pageBannerResponse = new PageBannerResponse();
        PageBannerConfigExample pageBannerConfigExample = new PageBannerConfigExample();
        pageBannerConfigExample.setOrderByClause("page_module_type asc, sort_order asc");
        PageBannerConfigExample.Criteria criteria = pageBannerConfigExample.createCriteria();
        criteria.andPageTypeEqualTo(pageType);
        criteria.andIsEnabledEqualTo(true);
        Date now = new Date();
        criteria.andActiveFromLessThanOrEqualTo(now);
        criteria.andActiveToGreaterThan(now);

        List<PageBannerConfig> pageBannerConfigList = pageBannerConfigMapper.selectByExample(pageBannerConfigExample);
        if (CollectionUtil.isNotEmpty(pageBannerConfigList)) {
            pageBannerResponse.setPageBannerConfigList(pageBannerConfigList);
        } else {
            pageBannerResponse.setResCode("50301");
            pageBannerResponse.setResMsg(ErrorCodeUtil.getErrorMsg("50301"));
        }
        return pageBannerResponse;
    }

    /**
     * 根据页面类型查询应该显示的场景
     * @param pageType
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Cacheable(value = "PageCache",key="'SCENES_IN_PAGE_TYPE_'+#pageType")
    public PageSceneResponse getScenesByPageType(int pageType) {
        PageSceneResponse pageSceneResponse = new PageSceneResponse();
        List<PageSceneConfig> pageSceneConfigList =  pageSceneConfigMapper.selectByPageType(pageType);
        List<Scene> scenes =new ArrayList<>();
        if (CollectionUtil.isNotEmpty(pageSceneConfigList)) {
            for (PageSceneConfig pageSceneConfig : pageSceneConfigList) {
                Scene scene = sceneService.getSceneById(pageSceneConfig.getSceneId());
                if (scene != null) {
                    scenes.add(scene);
                }
            }
            pageSceneResponse.setSceneList(scenes);
        } else {
            pageSceneResponse.setResCode("50302");
            pageSceneResponse.setResMsg(ErrorCodeUtil.getErrorMsg("50302"));
        }
        return pageSceneResponse;
    }

    /**
     * 根据页面类型查询应该显示的Topic
     * @param pageType
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Cacheable(value = "PageCache",key="'TOPICS_IN_PAGE_TYPE_'+#pageType")
    public PageTopicResponse getTopicsByPageType(int pageType) {
        PageTopicResponse pageTopicResponse = new PageTopicResponse();
        List<PageTopicConfig> topicList = pageTopicConfigMapper.selectByPageType(pageType);
        List<PageTopicVo> pageTopicVos=new ArrayList<>();
        if(!topicList.isEmpty())
            topicList.forEach(new Consumer<PageTopicConfig>() {
                @Override
                public void accept(PageTopicConfig pageTopicConfig) {
                    Topic topic= topicService.getTopicById(pageTopicConfig.getTopicId());
                    if(topic!=null) {
                        PageTopicVo pageTopicVo=new PageTopicVo();
                        pageTopicVo.setGroupId(topic.getGroupId());
                        pageTopicVo.setId(topic.getId());
                        pageTopicVo.setTopicContentDetails(topic.getTopicContentDetails());
                        pageTopicVo.setTopicDesc(topic.getTopicDesc());
                        pageTopicVo.setTopicImg(topic.getTopicImg());
                        pageTopicVo.setTopicImgLink(topic.getTopicImgLink());
                        pageTopicVo.setTopicMainTitle(topic.getTopicMainTitle());
                        pageTopicVo.setTopicName(topic.getTopicName());
                        pageTopicVo.setTopicType(topic.getTopicType());
                        List<ProductImageVo> productImageVos=new ArrayList<ProductImageVo>();
                        List<ProductShortListInTopic> productShortListInTopics=topicService.getProductShortListInTopic(topic.getId());
                        topicService.getProductShortListInTopic(topic.getId()).stream().filter(o->o.getShortListInTopic()==true).toArray();
                        if(!productShortListInTopics.isEmpty()) {
                            productShortListInTopics.forEach(new Consumer<ProductShortListInTopic>() {
                                @Override
                                public void accept(ProductShortListInTopic productShortListInTopic) {
                                    if (productShortListInTopic.getShortListInTopic()) {
                                        List<ProductImage> productImages = productService.getProductImages(productShortListInTopic.getProductId());
                                        if (!productImages.isEmpty()) {
                                            productImages.forEach(new Consumer<ProductImage>() {
                                                @Override
                                                public void accept(ProductImage productImage) {
                                                    ProductImageVo productImageVo = new ProductImageVo();
                                                    productImageVo.setGroupId(productShortListInTopic.getGroupId());
                                                    productImageVo.setSortOrder(productShortListInTopic.getSortOrder());
                                                    productImageVo.setProductId(productImageVo.getProductId());
                                                    productImageVo.setImgDesc(productImage.getImgDesc());
                                                    productImageVo.setImgName(productImage.getImgName());
                                                    productImageVo.setImgUrl(productImage.getImgUrl());
                                                    productImageVo.setViewPlatform(productImage.getViewPlatform());
                                                    productImageVos.add(productImageVo);
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                        pageTopicVo.setImages(productImageVos);
                        pageTopicVos.add(pageTopicVo);
                    }
                }
            });
        pageTopicResponse.setPageTopicVoList(pageTopicVos);
        return pageTopicResponse;
    }

    /**
     * 根据页面类型查询页面上的商品配置
     * 针对page_module_type(本周上新)
     * 按是否有效和顺序展示,最多10个
     * 如果发现不足10个(包括0个),自动获取最新上线的商品,补足
     *
     * 单品推荐的也一并返回
     *
     * @param pageType
     * @param moduleType
     * @return
     */
    @Override
    public PageProductResponse getProductsOnPage(int pageType,List<Byte> moduleType) {
    	PageProductResponse pageProductResponse = new PageProductResponse();
    	List<PageProductConfig> productList = pageProductConfigMapper.selectByPageType(pageType);
    	PageProductVo recommendProduct = new PageProductVo();
    	List<PageProductVo> pageProductNewVoList = new ArrayList<PageProductVo>();
    	for(PageProductConfig productConfig:productList){
			PageProductVo productVo = new PageProductVo();
            ProductPriceResponse priceResponse = priceService.getProductPrice(productConfig.getProductId());
            ProductPrice productPrice = priceResponse.getProductPrice();
            System.out.println("productPrice:"+productPrice);
    		Product product = productService.getProductById(productConfig.getProductId());
    		productVo.setProductId(productConfig.getProductId());
    		productVo.setProductName(product.getProductName());
    		productVo.setProductImg(productConfig.getProductImg());
    		productVo.setDetailDescription(product.getDetailDescription());
    		if(productPrice!=null){
    			productVo.setSellPrice(productPrice.getSellPrice());
        		productVo.setOriginalPrice(productPrice.getOriginalPrice());
    		}    		
    		productVo.setModuleType(productConfig.getPageModuleType());
    		if(productConfig.getPageModuleType() == moduleType.get(0)){
                recommendProduct = productVo;
    		}else if(productConfig.getPageModuleType() == moduleType.get(1)){
    			pageProductNewVoList.add(productVo);
    		}
    	}

    	pageProductResponse.setRecommendProduct(recommendProduct);
        pageProductResponse.setPageNewProductVoList(pageProductNewVoList);
    	
        return pageProductResponse;
    }

    /**
     * 根据页面类型查询页面上的分类配置
     *
     * @param pageType
     * @return
     */
    @Override
    public PageCategoryResponse getCategoriesOnPage(int pageType,int level) {
    	PageCategoryResponse pageCategoryResponse = new PageCategoryResponse();
    	List<PageCategoryVo> categoryList = pageCategoryConfigMapper.selectByParentCategoryId(pageType,level);
    	pageCategoryResponse.setPageCategoryVoList(categoryList);
        return pageCategoryResponse;
    }


}
