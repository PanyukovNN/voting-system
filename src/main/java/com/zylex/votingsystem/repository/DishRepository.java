package com.zylex.votingsystem.repository;

import com.zylex.votingsystem.model.Dish;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Override
    @Transactional
    Dish save(Dish dish);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Override
    List<Dish> findAll(Sort sort);

    @Override
    Optional<Dish> findById(Integer id);
}