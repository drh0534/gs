package com.gs.pp.common.mongo.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.gs.pp.common.mongo.base.MongoService;

/**
 * MongoDB id自增服务
 * Created by Administrator on 2016/1/6.
 */
@Component
public class MongoIdSequenceService {


    @Autowired
    MongoService<MongoIdSequence> mongoIdSequenceMongoService;


    public long getNextSequence(String name) {
        Query query = new Query(Criteria.where("_id").in(name));
        query.limit(1);
        query.with(new Sort(Sort.Direction.ASC, "value"));

        Update update = new Update();
        update.inc("value", 1);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        MongoIdSequence mongoIdSequence = mongoIdSequenceMongoService.queryOne(query, update,options, MongoIdSequence.class);
        if (mongoIdSequence == null) {
            saveMongoIdSequence(name);
            return 1;
        }else{
            return mongoIdSequence.getValue();
        }

    }

    public void saveMongoIdSequence(String name) {
        MongoIdSequence sequence = new MongoIdSequence(name);
        mongoIdSequenceMongoService.save(sequence);
    }

}
