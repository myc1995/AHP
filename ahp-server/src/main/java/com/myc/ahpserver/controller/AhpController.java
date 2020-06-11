package com.myc.ahpserver.controller;

import com.myc.ahpserver.common.ResultData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("getAhp")
public class AhpController {

    @GetMapping(value = "/hello")
    public ResultData getTree() {
        ResultData resultData = new ResultData();
        resultData.setData("hello,myc");
        return resultData;
    }
}
