package com.hengheng.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoQueryService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<MongoDbDeviceFieldDataEntity> findByConditions(
            Integer deviceId,
            Integer fieldId,
            Date start,
            Date end
    ) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("device_id").is(deviceId);
        criteria.and("field_id").is(fieldId);
        if (start != null || end != null) {
            Criteria created = criteria.and("created");
            if (start != null) {
                created.gt(start);
            }
            if (end != null) {
                //TODO 不包含结束
                created.lt(end);
            }
        }
        query.addCriteria(criteria);
        query.limit(100);
        return mongoTemplate.find(query, MongoDbDeviceFieldDataEntity.class);
    }

}
