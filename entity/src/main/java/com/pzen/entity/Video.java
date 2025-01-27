package com.pzen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "video")
@Data
public class Video extends BaseModel {

    public Video(String videoId) {
        super();
        this.videoId = videoId;
    }

    @Id
    private String videoId;


}
