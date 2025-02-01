package com.pzen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "test")
@Data
public class Test extends BaseModel{

    @Id
    private Long videoId;
    private String videoName;
    private String name;

}
