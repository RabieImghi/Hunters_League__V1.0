package org.rabie.hunters_league.service;

import org.rabie.hunters_league.domain.Hunt;
import org.rabie.hunters_league.domain.Participation;
import org.rabie.hunters_league.domain.Species;
import org.rabie.hunters_league.exceptions.HuntException;
import org.rabie.hunters_league.repository.HuntRepository;
import org.rabie.hunters_league.repository.dto.HuntDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HuntService {
    private final HuntRepository huntRepository;
    private final ParticipationService participationService;
    public HuntService(HuntRepository huntRepository,@Lazy ParticipationService participationService) {
        this.participationService = participationService;
        this.huntRepository = huntRepository;
    }

    public Page<Hunt> getAll(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return huntRepository.findAll(pageRequest);
    }
    public Hunt updateHunt(Hunt hunt) {
        if (hunt == null)
            throw new HuntException("Hunt is null");
        if (hunt.getSpecies() == null)
            throw new HuntException("Species is null");
        if (hunt.getParticipation() == null)
            throw new HuntException("Participation is null");
        if(hunt.getWeight() < hunt.getSpecies().getMinimumWeight())
            throw new HuntException("Weight is less than minimum weight");
        this.participationService.updateScore(hunt);
        return huntRepository.save(hunt);
    }
    public List<Hunt> getByParticipation(Participation participation, int offset, int limit) {
        if (participation == null)
            throw new HuntException("Participation is null");
        return huntRepository.findByParticipationIdWithLimit(participation.getId(), offset, limit);

    }
    public void deleteHunt(UUID id) {
        if (id == null)
            throw new HuntException("Hunt id is null");
        Hunt hunt = huntRepository.findById(id).orElse(null);
        if (hunt == null)
            throw new HuntException("Hunt not found");
        huntRepository.deleteById(id);
    }
    public long countByParticipation(Participation participation) {
        if (participation == null)
            throw new HuntException("Participation is null");
        return huntRepository.countByParticipationId(participation.getId());
    }

    @Transactional
    public void deleteBySpeciesId(Species species) {
        if (species == null)
            throw new HuntException("Hunt is null");
        int batchSize = 10000;
        int deletedCount;
        do {
            deletedCount = huntRepository.deleteBySpeciesIdBatch(species.getId(), batchSize);
        } while (deletedCount > 0);
    }

    public Hunt createHunt(Hunt hunt) {
        if (hunt == null)
            throw new HuntException("Hunt is null");
        if (hunt.getSpecies() == null)
            throw new HuntException("Species is null");
        if (hunt.getParticipation() == null)
            throw new HuntException("Participation is null");
        if(hunt.getWeight() < hunt.getSpecies().getMinimumWeight())
            throw new HuntException("Weight is less than minimum weight");
        this.participationService.updateScore(hunt);
        return huntRepository.save(hunt);
    }

    public List<HuntDTO> getAllHuntDTOByParticipationId(UUID participationId){
        return huntRepository.getHuntByParticipationId(participationId);
    }

}
