package com.pzen.server.controller;


import cn.hutool.json.JSONObject;
import com.pzen.dto.EncryptDTO;
import com.pzen.server.annotation.DecryptRequest;
import com.pzen.server.utils.XssSqlUtils;
import com.pzen.utils.JsonUtils;
import com.pzen.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/encrypt")
public class EncryptController {

    Logger logger = LoggerFactory.getLogger(EncryptController.class);

    //{
    //  "data" : "045edf71652bde449346f476fa3e9ddaba53fde310325b0f97b81404d92affc9dd3990d738fb50f992f69c8e60907058c8dbc043e12a40c3fc08b4fea607019fc09c1a1d699da3c48bd22a3fcf9f03de4bd051cf23a3cb1065b141bb5b700820a89c1efac18c16af18bb3d0cd08b6782e9"
    //}
    @PostMapping("/user")
    @DecryptRequest
//    @EncryptResponse
    public Result<Object> getUser(@RequestBody JSONObject j ) {
        // xxs sql 拦截
        if (XssSqlUtils.object(j)){
            return Result.error748();
        }
        // 获取解密后的 JSON 转换 DTO
        EncryptDTO dto = JsonUtils.fromJson(j.toString(), EncryptDTO.class);
        return Result.success(dto);
    }


}
