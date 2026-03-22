package com.vuong.api_platform.dto.response;

import com.vuong.api_platform.dto.ItemResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkResponse<T> {

    private int total;
    private int successCount;
    private int failCount;
    private List<ItemResult<T>> results;
}
