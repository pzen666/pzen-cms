package com.pzen.server.service.impl;

import com.pzen.entity.Video;
import com.pzen.server.service.VideoService;
import io.ebean.DB;

import java.util.List;
import java.util.Optional;

public class VideoServiceImpl extends EbeanRepositoryImpl<Video, String>
        implements VideoService {

    private final Class<Video> entityType;

    public VideoServiceImpl(Class<Video> entityType){
        super(Video.class);
        this.entityType = entityType;
    }
    @Override
    public List<Video> findByVideoBatchId(String batchId) {
        return DB.find(Video.class)
                .where()
                .eq("video_id", batchId)
                .findList();
    }

    @Override
    public Optional<Video> findByVideoId(String paymentId) {
        return Optional.ofNullable(DB.find(Video.class)
                .where()
                .eq("paymentId", paymentId)
                .findOne());
    }

    @Override
    public List<Video> findAllByBatchIdAndStatus(String batchId, List<String> statuses) {
        return DB.find(Video.class)
                .where()
                .and()
                .eq("amdBatchPayment.batchId", batchId)
                .in("status", statuses)
                .endAnd()
                .findList();
    }
}
