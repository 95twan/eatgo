package com.example.eatgo.controller;

import com.example.eatgo.domain.Region;
import com.example.eatgo.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/regions")
    public ResponseEntity<?> create(@RequestBody Region resource) throws URISyntaxException {
        Region region = regionService.addRegion(resource);
        URI uri = new URI("/api/regions/" + region.getId());
        return ResponseEntity.created(uri).body("{}");
    }
}
