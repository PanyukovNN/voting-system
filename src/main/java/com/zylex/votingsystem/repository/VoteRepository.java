package com.zylex.votingsystem.repository;

import com.zylex.votingsystem.model.Vote;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    @Override
    Optional<Vote> findById(Integer id);

    List<Vote> findAll(Sort sort);

    List<Vote> findAllByUserId(int userId);

    List<Vote> findByUserIdAndDateBetween(int userId, @NotNull LocalDate dateFrom, @NotNull LocalDate dateTo);

    List<Vote> findByRestaurantId(int restaurantId);

    List<Vote> findByRestaurantIdAndDateBetween(int restaurantId, LocalDate dateFrom, LocalDate dateTo);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);
}
