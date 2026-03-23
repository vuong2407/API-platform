package com.vuong.api_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResult<T> {
    private T item;
    private String status;
    private String error;

    public static <T> ItemResult<T> success(T item) {
        ItemResult<T> r = new ItemResult<>();
        r.setItem(item);
        r.setStatus("SUCCESS");
        return r;
    }

    public static <T> ItemResult<T> fail(T item, String error) {
        ItemResult<T> r = new ItemResult<>();
        r.setItem(item);
        r.setStatus("FAILED");
        r.setError(error);
        return r;
    }
}
