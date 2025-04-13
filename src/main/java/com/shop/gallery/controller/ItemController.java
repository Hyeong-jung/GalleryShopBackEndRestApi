package com.shop.gallery.controller;

import com.shop.gallery.entity.Item;
import com.shop.gallery.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/api/items")
    public List<Item> getItems() {
        List<Item> items = itemRepository.findAll();
        return items;
    }


    @GetMapping("/api/testitems")
    public List<String> getTestItems() {
        List<String> items = new ArrayList<>();

        items.add("data1");
        items.add("data2");
        items.add("data3");
        items.add("data4");
        items.add("data5");


        return items;
    }
}