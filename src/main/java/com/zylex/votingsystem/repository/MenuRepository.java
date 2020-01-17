package com.zylex.votingsystem.repository;

import com.zylex.votingsystem.model.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Override
    Optional<Menu> findById(Integer integer);

    @Override
    List<Menu> findAll(Sort sort);

    List<Menu> findAllByDate(LocalDate date);

    @EntityGraph(attributePaths = {"dish"})
    List<Menu> findAllByRestaurantId(int restaurant_id);

    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<Menu> findAllByDateAndRestaurantId(LocalDate date, int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);
}
