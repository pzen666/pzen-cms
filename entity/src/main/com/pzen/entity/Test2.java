package com.pzen.entity;

import io.ebean.annotation.WhenCreated;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "test2")
@Data
public class Test2 extends BaseModel{

    @Id
    private Long videoId;
    private String videoName;
    private String name;
    @WhenCreated
    private Timestamp createdAt;
    @WhenCreated
    private Timestamp createdAt2;


}
