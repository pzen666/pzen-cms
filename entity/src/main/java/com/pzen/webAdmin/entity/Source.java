package com.pzen.webAdmin.entity;


import com.pzen.entity.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_source")
@Data
public class Source  extends BaseModel {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
}
