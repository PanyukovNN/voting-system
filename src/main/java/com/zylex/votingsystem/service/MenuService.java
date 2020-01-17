package com.zylex.votingsystem.service;

import com.zylex.votingsystem.model.Dish;
import com.zylex.votingsystem.model.Menu;
import com.zylex.votingsystem.model.Restaurant;
import com.zylex.votingsystem.repository.DishRepository;
import com.zylex.votingsystem.repository.MenuRepository;
import com.zylex.votingsystem.repository.RestaurantRepository;
import com.zylex.votingsystem.to.MenuTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.zylex.votingsystem.util.ValidationUtil.checkNew;
import static com.zylex.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @Transactional
    public Menu createFromTo(MenuTo menuTo) {
        checkNew(menuTo);
        Dish dish = dishRepository.getOne(menuTo.getDishId());
        Restaurant restaurant = restaurantRepository.getOne(menuTo.getRestaurantId());
        return menuRepository.save(new Menu(menuTo.getDate(), dish, restaurant));
    }

    @Transactional
    public Menu create(Menu menu, int dishId, int restaurantId) {
        checkNew(menu);
        Dish dish = dishRepository.getOne(dishId);
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        return menuRepository.save(new Menu(menu.getDate(), dish, restaurant));
    }

    @Transactional
    public void update(Menu m) {
        Assert.notNull(m, "MenuTo must be not null");
        Integer id = m.getId();
        checkNotFoundWithId(get(id), id);
        menuRepository.save(m);
    }

    @Transactional
    public void updateFromTo(MenuTo m) {
        Assert.notNull(m, "MenuTo must be not null");
        Integer id = m.getId();
        Menu newMenu = checkNotFoundWithId(get(id), id);
        Dish dish = dishRepository.getOne(m.getDishId());
        Restaurant restaurant = restaurantRepository.getOne(m.getRestaurantId());
        newMenu.setDate(m.getDate());
        newMenu.setRestaurant(restaurant);
        newMenu.setDish(dish);
        menuRepository.save(newMenu);
    }

    public void delete(int id) {
        checkNotFoundWithId(menuRepository.delete(id) != 0, id);
    }

    public List<Menu> getAll(Sort sort) {
        return menuRepository.findAll(sort);
    }

    public List<Menu> getByDate(LocalDate date) {
        return menuRepository.findAllByDate(date);
    }

    public List<Menu> getByRestaurantId(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId);
    }

    public List<Menu> getByDateAndRestaurantId(LocalDate date, int restaurantId) {
        return menuRepository.findAllByDateAndRestaurantId(date, restaurantId);
    }

    public Menu get(int id) {
        return checkNotFoundWithId(menuRepository.findById(id).orElse(null), id);
    }
}