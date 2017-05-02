package com.mayezi.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@ConfigurationProperties(prefix = "site")
@Data
public class SiteConfig {

    private String name;
    private String intro;
    private String baseUrl;
    private String staticUrl;
    private int pageSize;
    private String uploadPath;
    private List<String> sections;
    private String theme;
    private String editor;
    private boolean search;
   }
