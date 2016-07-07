package com.wow.product.service.impl;

import com.wow.product.mapper.ProductShortListInTopicMapper;
import com.wow.product.mapper.TopicMapper;
import com.wow.product.model.ProductShortListInTopic;
import com.wow.product.model.ProductShortListInTopicExample;
import com.wow.product.model.Topic;
import com.wow.product.model.TopicExample;
import com.wow.product.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fangying@wowdsgn on 2016/7/7.
 */
@Service
@Transactional(value = "productTransactionManager")
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private ProductShortListInTopicMapper productShortListInTopicMapper;

    @Override
    public int createTopic(Topic topic) {
        return topicMapper.insertSelective(topic);
    }

    @Override
    public int updateTopic(Topic topic) {
        return topicMapper.updateByPrimaryKeySelective(topic);
    }

    @Override
    public int deleteTopic(int topicId) {
        Topic topic= getTopicById(topicId);
        if(topic!=null) {
            topic.setIsDeleted(true);
            return updateTopic(topic);
        }
        return 0;
    }
    /**
     * 将产品分组绑定到专题
     *
     * @param topic
     * @return
     */
    @Override
    public int bindGroupToTopic(Topic topic) {
        return 0;
    }

    @Override
    @Transactional(propagation= Propagation.SUPPORTS)
    public Topic getTopicById(int topicId) {
        return topicMapper.selectByPrimaryKey(topicId);
    }

    @Override
    @Transactional(propagation= Propagation.SUPPORTS)
    public Topic getTopicByName(String topicName) throws Exception{

            TopicExample topicExample=new TopicExample();
            topicExample.or().andTopicNameEqualTo(topicName).andIsDeletedEqualTo(false);
            return topicMapper.selectByExample(topicExample).get(0);
    }

    @Override
    @Transactional(propagation= Propagation.SUPPORTS)
    public List<Topic> getAllTopics() {
        return topicMapper.selectAll();
    }

    @Override
    public int createProductShortListInTopic(List<ProductShortListInTopic> productShortListInTopics) {
        if(!productShortListInTopics.isEmpty())
            productShortListInTopics.forEach(o-> productShortListInTopicMapper.insertSelective(o));
        return 0;
    }

    @Override
    public int updateProductShortListInTopic(List<ProductShortListInTopic> productShortListInTopics) {
        if(!productShortListInTopics.isEmpty())
            productShortListInTopics.forEach(o-> productShortListInTopicMapper.updateByPrimaryKeySelective(o));
        return 0;
    }

    @Override
    public int deleteProductShortListInTopic(List<ProductShortListInTopic> productShortListInTopics) {
        if(!productShortListInTopics.isEmpty()) {
            productShortListInTopics.forEach(o -> o.setIsDeleted(true));
            updateProductShortListInTopic(productShortListInTopics);
        }
        return 0;
    }

    @Override
    @Transactional(propagation= Propagation.SUPPORTS)
    public List<ProductShortListInTopic> getProductShortListInTopic(int topicId) {
        ProductShortListInTopicExample productShortListInTopicExample=new ProductShortListInTopicExample();
        productShortListInTopicExample.or().andIsDeletedEqualTo(false).andTopicIdEqualTo(topicId);
        return productShortListInTopicMapper.selectByExample(productShortListInTopicExample);
    }
}
