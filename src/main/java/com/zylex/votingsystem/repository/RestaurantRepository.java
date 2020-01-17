package com.zylex.votingsystem.repository;

import com.zylex.votingsystem.model.Restaurant;
import org.springframework.data.domain.Sort;
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
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Override
    Optional<Restaurant> findById(Integer id);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN r.menus m WHERE m.date=?1 ORDER BY r.id")
    List<Restaurant> findByDate(LocalDate date);

    Restaurant findByName(String name);

    @Override
    List<Restaurant> findAll(Sort sort);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menus m " +
            "LEFT JOIN FETCH m.dish " +
            "ORDER BY r.id")
    List<Restaurant> findAllWithMenus();

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH v.user " +
            "ORDER BY r.id")
    List<Restaurant> findAllWithVotes();

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menus m " +
            "LEFT JOIN FETCH m.dish " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH v.user " +
            "ORDER BY r.id")
    List<Restaurant> findAllWithMenusAndVotes();

    @Transactional
    @Query("SELECT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menus m " +
            "LEFT JOIN FETCH m.dish d " +
            "WHERE r.id=?1 " +
            "ORDER BY r.id")
    Restaurant findByIdWithMenus(int id);

    @Transactional
    @Query("SELECT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH v.user " +
            "WHERE r.id=?1 " +
            "ORDER BY r.id")
    Restaurant findByIdWithVotes(int id);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menus m " +
            "LEFT JOIN FETCH m.dish " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH v.user WHERE r.id=?1 " +
            "ORDER BY r.id")
    Restaurant findByIdWithMenusAndVotes(int id);
}
