package com.myc.ahpserver.controller;

import com.myc.ahpserver.common.PostData;
import com.myc.ahpserver.common.ResultData;
import com.myc.ahpserver.service.AhpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/getAhp")
public class AhpController {
    @Autowired
    AhpService ahpService;

    @PostMapping(value = "/calculate")
    public ResultData getTree(@RequestBody PostData postData) {
        ahpService.handleData(postData);
        //System.out.println(postData.getDataStructure()[0]);
        int[] data1 = postData.getDataStructure();
        for (int i = 0; i < data1.length; i++) {
            System.out.println(data1[i]);
        }
        ResultData resultData = new ResultData();
        resultData.setData("hello,myc");
        return resultData;
    }
}
