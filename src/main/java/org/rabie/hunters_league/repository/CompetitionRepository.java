package org.rabie.hunters_league.repository;

import org.rabie.hunters_league.domain.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.rabie.hunters_league.domain.Competition;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
    Competition findTopByOrderByDateDesc();

    @Query(value = "SELECT * FROM competition ORDER BY date DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Competition> findAllWithLimit(@Param("offset") long offset, @Param("limit") int limit);

    @Query(value = "SELECT * FROM competition WHERE date > NOW() ORDER BY date ASC LIMIT 1", nativeQuery = true)
    Competition findNextCompetition();


    @Query(value = "SELECT * FROM competition WHERE code LIKE %:code% ORDER BY date DESC", nativeQuery = true)
    Page<Competition> findByCodeLike(@Param("code") String code, PageRequest pageRequest);


}
