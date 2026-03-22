package com.vuong.api_platform.service;

import com.vuong.api_platform.domain.Apis;
import com.vuong.api_platform.dto.ItemResult;
import com.vuong.api_platform.dto.request.ApisDTO;
import com.vuong.api_platform.dto.response.BulkResponse;
import com.vuong.api_platform.exception.ResourceNotFoundException;
import com.vuong.api_platform.repository.ApisRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApisService {

    private final ApisRepository apisRepository;

    public ApisService(ApisRepository apisRepository) {
        this.apisRepository = apisRepository;
    }

    public List<Apis> getAllApis() {
        List<Apis> result = apisRepository.findAll();
        return result;
    }

    public Apis getApiById(String id) {
        return apisRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not found the API with id: " + id));
    }

    public BulkResponse<Apis> saveAll(List<ApisDTO> apis) {

        int successCount = 0, failCount = 0;
        List<ItemResult<Apis>> itemResults = new ArrayList<>();

        for (ApisDTO api : apis) {
            ItemResult<Apis> itemResult = saveOne(api);
            itemResults.add(itemResult);

            if ("SUCCESS".equals(itemResult.getStatus())) {
                successCount++;
            } else {
                failCount++;
            }
        }

        BulkResponse<Apis> bulkResponse = new BulkResponse<>();
        bulkResponse.setTotal(apis.size());
        bulkResponse.setSuccessCount(successCount);
        bulkResponse.setFailCount(failCount);
        bulkResponse.setResults(itemResults);

        return bulkResponse;
    }

    public ItemResult<Apis> saveOne(ApisDTO apisDTO) {

        Apis apis = apisDTO.toApis();
        try {
            Apis savedApi = apisRepository.save(apis);
            return ItemResult.success(savedApi);
        } catch (DataIntegrityViolationException e) {
            return ItemResult.fail(apis, "Duplicate name-version");
        } catch (Exception e) {
            return ItemResult.fail(apis, e.getMessage());
        }
    }


}
