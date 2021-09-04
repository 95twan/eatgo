package com.example.eatgo.service;

import com.example.eatgo.domain.Region;
import com.example.eatgo.domain.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> getRegions() {
        return regionRepository.findAll();
    }

    public Region addRegion(Region region) {
        return regionRepository.save(region);
    }
}
