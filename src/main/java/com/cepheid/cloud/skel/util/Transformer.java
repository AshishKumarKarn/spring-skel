package com.cepheid.cloud.skel.util;

import com.cepheid.cloud.skel.dto.req.DescriptionRequest;
import com.cepheid.cloud.skel.dto.req.ItemRequest;
import com.cepheid.cloud.skel.dto.resp.DescriptionResponse;
import com.cepheid.cloud.skel.dto.resp.ItemResponse;
import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;

import java.util.stream.Collectors;

public class Transformer {

    public static Item transformItemRequestToItem(ItemRequest itemRequest) {
        Item item = new Item();
        if (itemRequest != null) {
            item.setState(itemRequest.getState());
            item.setName(itemRequest.getName());
            itemRequest.getDescriptions().forEach(
                    descriptionRequest -> {
                        Description description = new Description();
                        description.setDescriptionComment(descriptionRequest.getDescriptionComment());
                        item.addDescription(description);
                    }
            );
        }
        return item;
    }

    public static Description transformDescriptionRequestToDescription(DescriptionRequest descriptionRequest) {
        Description description = new Description();
        description.setDescriptionComment(descriptionRequest.getDescriptionComment());
        description.setItem(transformItemRequestToItem(descriptionRequest.getItemRequest()));
        return description;
    }

    public static ItemResponse transformItemToItemResponse(Item item) {
        return new ItemResponse(Boolean.FALSE, item.getName(), item.getState(), item.getDescriptions().stream().map(Transformer::transformDescriptionToDescriptionResponse).collect(Collectors.toList()), item.getId());
    }

    public static DescriptionResponse transformDescriptionToDescriptionResponse(Description description) {
        return new DescriptionResponse(Boolean.FALSE, null, description.getDescriptionComment(), description.getId());
    }
}
