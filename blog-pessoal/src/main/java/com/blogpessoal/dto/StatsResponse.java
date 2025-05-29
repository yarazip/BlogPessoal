package com.blogpessoal.dto;

import java.util.Objects;

public class StatsResponse {

    private String day;
    private Long count;

    public StatsResponse() {
    }

    public StatsResponse(String day, Long count) {
        this.day = day;
        this.count = count;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "StatsResponse{" +
                "day='" + day + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatsResponse)) return false;
        StatsResponse that = (StatsResponse) o;
        return Objects.equals(day, that.day) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, count);
    }
}
