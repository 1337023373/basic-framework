package com.hengheng.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author mairun
 */
@Data
@Document("tb_device_field_data")
public class MongoDbDeviceFieldDataEntity {

    @Id
    private String id;

    private Integer value;
    @Field("device_id")
    private Integer deviceId;
    @Field("field_id")
    private Integer fieldId;

    private Date created;
}
