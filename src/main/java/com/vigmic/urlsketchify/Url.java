package com.vigmic.urlsketchify;


import org.springframework.data.annotation.Id;

public class Url {
    @Id
    private Long id;

    private String ipAddress;

    private String originalUrl;

    private String bijectedUrl;

    public void setId(Long id) {
        this.id = id;
    }

    public void setBijectedUrl(String bijectedUrl) {
        this.bijectedUrl = bijectedUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public long getId() {
        return id;
    }

    public String getBijectedUrl() {
        return bijectedUrl;
    }


    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
