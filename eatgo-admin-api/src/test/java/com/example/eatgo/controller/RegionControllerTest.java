package com.example.eatgo.controller;

import com.example.eatgo.domain.Region;
import com.example.eatgo.service.RegionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegionController.class)
class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegionService regionService;

    @BeforeEach
    public void setup(){
        mockRegionService();
    }

    private void mockRegionService() {
        List<Region> regions = new ArrayList<>();
        regions.add(Region.builder().name("Seoul").build());

        given(regionService.getRegions()).willReturn(regions);
        given(regionService.addRegion(any())).will(invocation -> {
            Region region = invocation.getArgument(0);
            return Region.builder().id(1L).name(region.getName()).build();
        });
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/api/regions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Seoul")));

        verify(regionService).getRegions();
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/api/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Seoul\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/api/regions/1"))
                .andExpect(content().string("{}"));

        verify(regionService).addRegion(any());
    }
}