package com.zylex.votingsystem.repository;

import com.zylex.votingsystem.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
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
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    @Transactional
    User save(User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Override
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findById(Integer id);

    @Transactional
    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.votes v " +
            "LEFT JOIN FETCH v.restaurant " +
            "WHERE u.id=?1")
    User findByIdWithVotes(int id);

    @Override
    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll(Sort sort);

    User getByEmail(String email);
}
