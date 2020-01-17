package com.zylex.votingsystem.web.restaurant;

import com.zylex.votingsystem.model.Restaurant;
import com.zylex.votingsystem.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        value = UserRestaurantController.REST_URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {

    public static final String REST_URL = "/rest/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService restaurantService;

    public UserRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantService.getAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @GetMapping(value = "/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable int restaurantId) {
        log.info("get restaurant {}", restaurantId);
        return restaurantService.get(restaurantId);
    }
}
