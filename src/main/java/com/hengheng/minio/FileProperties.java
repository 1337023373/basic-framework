package com.hengheng.minio;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author lkj
 * @Date 2025/6/27 13:55
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "file")
@Component
@Data
public class FileProperties {
    private String uploadPathWindows;
    private String uploadPathLinux;

    public String getUploadPath() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return uploadPathWindows;
        } else {
            return uploadPathLinux;
        }
    }
}
