package com.zylex.votingsystem.to;

import com.zylex.votingsystem.model.Menu;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class MenuTo extends BaseTo {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotNull
    private Integer restaurantId;

    @NotNull
    private Integer dishId;

    public MenuTo() {
    }

    public MenuTo(LocalDate date) {
        this.date = date;
    }

    public MenuTo(Menu menu) {
        this(menu.getId(), menu.getDate(), menu.getRestaurant().getId(), menu.getDish().getId());
    }

    public MenuTo(Integer id, LocalDate date, Integer restaurantId, Integer dishId) {
        super(id);
        this.date = date;
        this.restaurantId = restaurantId;
        this.dishId = dishId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }
}
