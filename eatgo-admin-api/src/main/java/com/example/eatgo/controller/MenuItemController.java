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

    @GetMapping("/restaurants/{restaurantId}/menuitems")
    public List<MenuItem> list(@PathVariable Long restaurantId){
        return menuItemService.getMenuItems(restaurantId);
    }

    @PatchMapping("/restaurants/{restaurantId}/menuitems")
    public String bulkUpdate(@PathVariable Long restaurantId, @RequestBody List<MenuItem> resources){
        menuItemService.bulkUpdate(restaurantId, resources);
        return "";
    }
}
