package com.pzen.server.service;


import com.pzen.entity.Video;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideoService extends EbeanRepository<Video, String>{

    List<Video> findByVideoBatchId(String batchId);

    Optional<Video> findByVideoId(String paymentId);

    List<Video> findAllByBatchIdAndStatus(@Param("batchId") String batchId,
                                               @Param("statuses") List<String> statuses);
}
