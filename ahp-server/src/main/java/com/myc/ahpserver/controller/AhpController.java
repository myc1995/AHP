package com.myc.ahpserver.controller;

import com.myc.ahpserver.common.PostData;
import com.myc.ahpserver.common.ResultData;
import com.myc.ahpserver.service.AhpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/getAhp")
public class AhpController {
    @Autowired
    AhpService ahpService;

    @PostMapping(value = "/calculate")
    public List<ResultData> getTree(@RequestBody PostData postData) {
        return ahpService.handleAhpRequest(postData);
    }
}
