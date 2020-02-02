package com.krupoderovmikhail.github.notfound.repository;

import com.krupoderovmikhail.github.notfound.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("from Game g " +
            "where concat(g.name, ' ', g.description, ' ', g.platform, ' ', g.additionally) " +
            "   like concat('%', :name, '%') ")
    List<Game> findByName(@Param("name") String name);
}
