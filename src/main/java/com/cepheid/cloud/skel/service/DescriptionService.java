package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.dto.req.DescriptionRequest;
import com.cepheid.cloud.skel.dto.resp.DescriptionResponse;
import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.repository.DescriptionRepository;
import com.cepheid.cloud.skel.util.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DescriptionService {

    private final DescriptionRepository descriptionRepository;

    @Autowired
    DescriptionService(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    public DescriptionResponse findById(Long id) {
        Optional<Description> description = descriptionRepository.findById(id);
        return description.map(Transformer::transformDescriptionToDescriptionResponse).orElseGet(() -> new DescriptionResponse(Boolean.TRUE, "No matching description found with the given id"));
    }

    @Transactional
    public DescriptionResponse save(DescriptionRequest descriptionRequest) {
        descriptionRequest.validate();
        Description description = Transformer.transformDescriptionRequestToDescription(descriptionRequest);
        Description savedDescription = descriptionRepository.save(description);
        return Transformer.transformDescriptionToDescriptionResponse(savedDescription);
    }

    @Transactional
    public DescriptionResponse updateDescriptionById(Long descriptionId, DescriptionRequest description) {
        description.validate();
        Optional<Description> descriptionById = descriptionRepository.findById(descriptionId);
        if (descriptionById.isPresent()) {
            descriptionRepository.updateById(description.getDescriptionComment(), descriptionId);
        } else {
            return new DescriptionResponse(Boolean.TRUE, "Description with id " + descriptionId + " not found");
        }
        return new DescriptionResponse(Boolean.FALSE, null, description.getDescriptionComment());
    }

    public List<DescriptionResponse> findAll() {
        List<Description> descriptions = descriptionRepository.findAll();
        if (descriptions.isEmpty()) {
            return Collections.singletonList(new DescriptionResponse(Boolean.TRUE, "No description data present in database"));
        }
        return descriptions.stream().map(Transformer::transformDescriptionToDescriptionResponse).collect(Collectors.toList());
    }
}
