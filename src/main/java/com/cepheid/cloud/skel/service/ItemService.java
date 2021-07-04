package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.dto.req.ItemRequest;
import com.cepheid.cloud.skel.dto.resp.ItemResponse;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemRepository;
import com.cepheid.cloud.skel.util.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemResponse> findAll() {
        List<Item> itemList = itemRepository.findAll();
        return itemList.stream().map(Transformer::transformItemToItemResponse).collect(Collectors.toList());
    }

    public ItemResponse findItemResponseById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.map(Transformer::transformItemToItemResponse).orElseGet(() -> new ItemResponse(Boolean.TRUE, "Item with given id is not present in database"));
    }

    public Item findItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.orElse(null);
    }

    public ItemResponse save(ItemRequest itemRequest) {
        itemRequest.validate();
        Item item = Transformer.transformItemRequestToItem(itemRequest);
        return saveItem(item);
    }

    public ItemResponse saveItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return Transformer.transformItemToItemResponse(savedItem);
    }

    public ItemResponse deleteById(Long itemId){

        try {
            itemRepository.deleteById(itemId);
        } catch (EmptyResultDataAccessException e) {
            return new ItemResponse(Boolean.TRUE, "Item with given Id was not present in database");
        }
        return new ItemResponse(Boolean.FALSE, "ITEM DELETED SUCCESSFULLY");
    }

    public void deleteAll(){
        itemRepository.deleteAll();
    }

}
