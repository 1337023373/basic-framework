package com.hengheng.common.filter;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hengheng.common.base.BaseModel;
import com.hengheng.common.base.SecurityUser;
import com.hengheng.common.base.UserDetail;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lyw
 * @version 1.0
 */
@Component
public class MyEntityFilter implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = getCurrentUserId();
        Date now = new Date();

        if (userId != null) {
            this.strictInsertFill(metaObject, "creator", Long.class, userId);
            this.strictInsertFill(metaObject, "updater", Long.class, userId);
        }
        this.strictInsertFill(metaObject, "createTime", Date.class, now);
        this.strictInsertFill(metaObject, "updateTime", Date.class, now);
        this.strictInsertFill(metaObject, "deleted", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = getCurrentUserId();
        Date now = new Date();

        if (userId != null) {
            this.strictUpdateFill(metaObject, "updater", Long.class, userId);
        }
        this.strictUpdateFill(metaObject, "updateTime", Date.class, now);
    }

    private Long getCurrentUserId() {
        // 根据你的项目实际情况获取当前登录用户ID
        // 例如：SecurityUser.getUser().getId()
        return SecurityUser.getUser() != null ? SecurityUser.getUser().getId() : null;
    }
}
//public class MyEntityFilter implements EntityInterceptor {
//    @Override
//    public void configureInsert(Class<?> entityClass, EntityInsertExpressionBuilder entityInsertExpressionBuilder, Object entity) {
//        UserDetail user = SecurityUser.getUser();
//        Date now = new Date();
//        BaseModel baseEntity = (BaseModel) entity;
//        if(user.getId() != null){
//            baseEntity.setCreator(user.getId());
//            baseEntity.setUpdater(user.getId());
//        }
//        baseEntity.setCreateTime(now);
//        baseEntity.setUpdateTime(now);
//        baseEntity.setDeleted(false);
//    }
//
//    @Override
//    public void configureUpdate(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, Object entity) {
//        UserDetail user = SecurityUser.getUser();
//        BaseModel baseEntity = (BaseModel) entity;
//        if(user.getId() != null){
//            baseEntity.setUpdater(user.getId());
//        }
//        baseEntity.setUpdateTime(new Date());
//    }
//
//    @Override
//    public String name() {
//        return "MyEntityFilter";
//    }
//
//    @Override
//    public boolean apply(Class<?> entityClass) {
//        return BaseModel.class.isAssignableFrom(entityClass);
//    }
//}
