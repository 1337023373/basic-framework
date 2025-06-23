package com.hengheng.common.utils;


import com.hengheng.common.utils.id.SeqIdGenerator;
import com.hengheng.common.utils.id.SnowflakeIdGenerator;
import com.hengheng.common.utils.id.TimestampIdGenerator;
import com.hengheng.common.utils.id.UUIDGenerator;

public class IdUtils {
    public static String getUUId(boolean Is32) {
        UUIDGenerator uuidGenerator = new UUIDGenerator();
        return Is32 ? uuidGenerator.get(32) : uuidGenerator.get(64);
    }

    public static String getTSId() {
        TimestampIdGenerator timestampIdGenerator = new TimestampIdGenerator();
        return timestampIdGenerator.get();
    }

    public static long getSnowId() {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1);
        long id = snowflakeIdGenerator.get();
        return id;
    }

    public static int getSeqId() {
        return SeqIdGenerator.get();
    }
}
