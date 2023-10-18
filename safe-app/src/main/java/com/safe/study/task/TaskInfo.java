package com.safe.study.task;

public class TaskInfo {
    public String imgUrl;
    public long timestamp;
    public String timeStr;
    public String des;

    public TaskInfo(String imgUrl, long timestamp, String timeStr, String des) {
        this.imgUrl = imgUrl;
        this.timestamp = timestamp;
        this.timeStr = timeStr;
        this.des = des;
    }
}
