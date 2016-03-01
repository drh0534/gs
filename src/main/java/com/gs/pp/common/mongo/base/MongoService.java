package com.gs.pp.common.mongo.base;

import com.gs.pp.common.utils.ReflectionUtils;
import com.mongodb.CommandResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MongoService<T> {

    private Logger logger = LoggerFactory.getLogger(MongoService.class);


    @Autowired
    protected MongoTemplate mongoTemplate;

	/*private Class<T> entityClass;

	public MongoService() {
		Type genType = getClass().getGenericSuperclass();  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments(); 
        entityClass = (Class) params[0];
	}*/

    protected Class<T> getEntityClass() {
        return ReflectionUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 保存一个实体
     *
     * @param t
     */
    public void save(T obj) {
        logger.info("[Mongo Dao ]save:" + obj);
        this.mongoTemplate.save(obj);
    }

    public void delete(T obj) {
        logger.info("[Mongo Dao ]delete:" + obj);
        this.mongoTemplate.remove(obj);
    }


    public T queryOne(Query query, Class<T> clazz) {
        logger.info("[Mongo Dao ]queryOne:" + query);
        return this.mongoTemplate.findOne(query, clazz);
    }

    /**
     * 分页查询
     *
     * @param query
     * @param start
     * @param size
     * @return
     */
    public List<T> getPageList(Query query, int start, int size, Class<T> clazz) {
        query.skip(start);
        query.limit(size);
        logger.info("[Mongo Dao ]queryPage:" + query + "(" + (start) + "," + (size) + ")");
        List<T> list = this.mongoTemplate.find(query, clazz);
        return list;
    }


    /**
     * 分页查询
     *
     * @param query
     * @param clazz
     * @return
     */
    public List<T> getList(Query query, Class<T> clazz) {
        logger.info("[Mongo Dao ]queryPage:" + query);
        List<T> list = this.mongoTemplate.find(query, clazz);
        return list;
    }

    /**
     * 记录条数
     *
     * @param query
     * @return
     */
    public Long getPageCount(Query query, Class<T> clazz) {
        logger.info("[Mongo Dao ]queryPageCount:" + query);
        return this.mongoTemplate.count(query, clazz);
    }

    /**
     * 按id删除
     *
     * @param id
     * @param clazz
     */
    public void deleteById(String id, Class<T> clazz) {
        Criteria criteria = Criteria.where("_id").in(id);
        if (null != criteria) {
            Query query = new Query(criteria);
            logger.info("===============[Mongo Dao ]deleteById:" + query);
            if (null != query && this.queryOne(query, clazz) != null) {
                mongoTemplate.remove(query);
            }
        }
    }

    public void batchDelete(Query query, Class<T> clazz) {
        mongoTemplate.findAllAndRemove(query, clazz);
    }


    /**
     * 按id删除
     */
    public CommandResult executeCommand(String command) {
        logger.info("===============[Mongo Dao ]executeCommand:" + command);
        CommandResult result = mongoTemplate.executeCommand(command);
        return result;
    }

    /**
     * 修改第一个匹配的
     *
     * @param query
     * @param update
     * @param clazz
     */
    public void updateFirst(Query query, Update update, Class<T> clazz) {
        logger.info("[Mongo Dao ]updateFirst:query(" + query + "'),update(" + update + ")");
        mongoTemplate.updateFirst(query, update, clazz);
    }

    /**
     * 更新满足条件的所有记录
     *
     * @param query
     * @param update
     * @param clazz
     */
    public void updateMulti(Query query, Update update, Class<T> clazz) {
        logger.info("[Mongo Dao ]updateMulti:query(" + query + "'),update(" + update + ")");
        mongoTemplate.updateMulti(query, update, clazz);
    }

    /**
     * 更新的时候如果不存在则添加
     *
     * @param query
     * @param update
     * @param clazz
     */
    public void saveOrUpdate(Query query, Update update, Class<T> clazz) {
        logger.info("[Mongo Dao ]saveOrUpdate:query(" + query + "'),update(" + update + ")");
        mongoTemplate.upsert(query, update, clazz);
    }

    public T queryOne(Query query, Update update, FindAndModifyOptions options, Class<T> clazz) {
        logger.info("[Mongo Dao ]queryOne:query(" + query + "'),update(" + update + ")");
        return mongoTemplate.findAndModify(query, update, options, clazz);
    }

    /**
     * 删除文档
     */
    public <T> void dropTables(Class<T> clazz) {
        mongoTemplate.dropCollection(clazz);
    }
}
