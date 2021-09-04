package com.example.eatgo.service;

import com.example.eatgo.domain.Region;
import com.example.eatgo.domain.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @MockBean
    private RegionRepository regionRepository;

    @BeforeEach
    public void setup(){
        mockRegionRepository();
    }

    private void mockRegionRepository() {
        List<Region> regions = new ArrayList<>();
        regions.add(Region.builder().name("Seoul").build());

        given(regionRepository.findAll()).willReturn(regions);
    }

    @Test
    public void getRegions(){
        List<Region> regions = regionService.getRegions();
        Region region = regions.get(0);
        assertThat(region.getName(), is("Seoul"));

        verify(regionRepository).findAll();
    }
}