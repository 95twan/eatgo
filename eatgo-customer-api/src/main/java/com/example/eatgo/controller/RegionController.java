package com.example.eatgo.controller;

import com.example.eatgo.domain.Region;
import com.example.eatgo.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping(value = "/regions")
    public List<Region> list(){
        return regionService.getRegions();
    }
}
