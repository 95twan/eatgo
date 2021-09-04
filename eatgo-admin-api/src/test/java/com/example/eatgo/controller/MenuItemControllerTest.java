package com.example.eatgo.controller;

import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.service.MenuItemService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuItemService menuItemService;

    @BeforeEach
    public void setup(){
        mockMenuItemService();
    }

    private void mockMenuItemService() {
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = MenuItem.builder().restaurantId(1L).name("Kimchi").build();
        menuItems.add(menuItem);
        given(menuItemService.getMenuItems(1L)).willReturn(menuItems);
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/api/restaurants/1/menuitems"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Kimchi")));
    }

    @Test
    public void bulkUpdate() throws Exception {
        mockMvc.perform(patch("/api/restaurants/1/menuitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(menuItemService).bulkUpdate(eq(1L), any());
    }
}