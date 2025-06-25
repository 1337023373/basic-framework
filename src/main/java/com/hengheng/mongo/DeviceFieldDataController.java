package com.hengheng.mongo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/device-data")
@Tag(name = "mongo测试")
@RequiredArgsConstructor
public class DeviceFieldDataController {

    private final MongoQueryService service;

    @GetMapping("/by-createTime-all")
    public List<MongoDbDeviceFieldDataEntity> getByCreatedAll(@RequestParam Integer deviceId, @RequestParam Integer fieldId,               // fieldId
                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,        // new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-12-09 00:00:00")
                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        return service.findByConditions(deviceId, fieldId, startTime, endTime);
    }
}
