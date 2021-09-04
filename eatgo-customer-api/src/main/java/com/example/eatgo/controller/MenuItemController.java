package com.example.eatgo.controller;

import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PatchMapping("/restaurants/{id}/menuitems")
    public String bulkUpdate(@PathVariable Long id, @RequestBody List<MenuItem> resources){
        menuItemService.bulkUpdate(id, resources);
        return "";
    }
}
