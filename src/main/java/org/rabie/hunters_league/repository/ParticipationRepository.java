package org.rabie.hunters_league.repository;

import org.rabie.hunters_league.domain.AppUser;
import org.rabie.hunters_league.domain.Competition;
import org.rabie.hunters_league.domain.Participation;
import org.rabie.hunters_league.repository.dto.ParticipationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, UUID> {
    Participation findByAppUserAndCompetition(AppUser appUser, Competition competition);


    List<Participation> findByAppUserId(UUID userId, PageRequest pageRequest);

    @Query(value = """
    SELECT p1.id, p1.app_user_id, p1.competition_id, p1.score
    FROM participation p1
    JOIN (
        SELECT app_user_id, MAX(score) AS max_score
        FROM participation
        GROUP BY app_user_id
    ) p2 ON p1.app_user_id = p2.app_user_id AND p1.score = p2.max_score
    ORDER BY p1.score DESC
""", nativeQuery = true)
    Page<Participation> getTop3ParticipationOrderByScoreDesc(PageRequest pageRequest);


    @Query(value = """
    WITH RankedParticipations AS (
        SELECT app_user_id, RANK() OVER (ORDER BY score DESC) AS `rank`
        FROM participation
        WHERE competition_id = :competitionId
    )
    SELECT `rank`
    FROM RankedParticipations
    WHERE app_user_id = :userId
    """, nativeQuery = true)
    Integer getAppUserRank(@Param("competitionId") UUID competitionId, @Param("userId") UUID userId);


    @Query("SELECT new org.rabie.hunters_league.repository.dto.ParticipationDTO(p.id, p.score) FROM Participation p")
    List<ParticipationDTO> findAllParticipationDto(PageRequest pageRequest);

    @Query(value = "SELECT * FROM participation WHERE score = 0 LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Participation> findAllWithLimit(@Param("offset") long offset, @Param("limit") int limit);

}
