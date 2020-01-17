package com.zylex.votingsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "menu", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "menu_unique_date_restaurant_idx")
})
public class Menu extends AbstractBaseEntity {

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dish_id")
    @NotNull
    private Dish dish;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Menu m) {
        this(m.getId(), m.getDate(), m.getDish(), m.getRestaurant());
    }

    public Menu(@NotNull LocalDate date, @NotNull Restaurant restaurant) {
        this.date = date;
        this.restaurant = restaurant;
    }

    public Menu(Integer id, @NotNull LocalDate date) {
        super(id);
        this.date = date;
    }

    public Menu(@NotNull LocalDate date, Dish dish, @NotNull Restaurant restaurant) {
        this.date = date;
        this.dish = dish;
        this.restaurant = restaurant;
    }

    public Menu(Integer id, @NotNull LocalDate date, Dish dish, @NotNull Restaurant restaurant) {
        super(id);
        this.date = date;
        this.dish = dish;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
