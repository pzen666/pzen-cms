package com.pzen.server.controller;


import cn.hutool.json.JSONObject;
import com.pzen.server.annotation.DecryptRequest;
import com.pzen.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/encrypt")
public class EncryptController {

    Logger logger = LoggerFactory.getLogger(EncryptController.class);

    @PostMapping("/user")
    @DecryptRequest
//    @EncryptResponse
    public Result<Object> getUser(@RequestBody JSONObject j) {
        // 获取解密后的数据
        String decryptedData = j.getStr("data");
        logger.info("Decrypted JSON Node parameter {}: {}", j, decryptedData);
        // 处理业务逻辑
        String responseBody = "User Data: " + decryptedData;
        return Result.success(responseBody);
    }


}
