package com.gs.pp.common.mongo.auto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * mongodb 自增id集合,应用于特殊场景
 * Created by Administrator on 2016/1/6.
 */
@Document
public class MongoIdSequence {

    @Id
    private String name;
    private long value = 1;//默认从1开始计数

    public MongoIdSequence() {
    }

    public MongoIdSequence(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}