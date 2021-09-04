package com.example.eatgo.service;

import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.domain.MenuItemRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MenuItemServiceTest {

    @Autowired
    private MenuItemService menuItemService;

    @MockBean
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setup(){
        mockMenuItemRepository();
    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = MenuItem.builder().restaurantId(1004L).name("Kimchi").build();
        menuItems.add(menuItem);
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    @Test
    public void getMenuItems(){
        List<MenuItem> menuItems = menuItemService.getMenuItems(1004L);
        MenuItem menuItem = menuItems.get(0);

        assertThat(menuItem.getName(), is("Kimchi"));
    }

    @Test
    public void bulkUpdate(){
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(MenuItem.builder().name("Kimchi").build());
        menuItems.add(MenuItem.builder().name("Gukbob").build());
        menuItems.add(MenuItem.builder().id(2L).destroy(true).build());


        menuItemService.bulkUpdate(1L, menuItems);
        
        verify(menuItemRepository, times(2)).save(any());
        verify(menuItemRepository, times(1)).deleteById(2L);
    }

}