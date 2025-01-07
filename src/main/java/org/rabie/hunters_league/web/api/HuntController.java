package org.rabie.hunters_league.web.api;

import jakarta.validation.Valid;
import org.rabie.hunters_league.domain.Hunt;
import org.rabie.hunters_league.domain.Participation;
import org.rabie.hunters_league.domain.Species;
import org.rabie.hunters_league.service.HuntService;
import org.rabie.hunters_league.service.ParticipationService;
import org.rabie.hunters_league.service.SpeciesService;
import org.rabie.hunters_league.web.vm.mapper.HuntMapper;
import org.rabie.hunters_league.web.vm.request.CreateHuntVm;
import org.rabie.hunters_league.web.vm.request.UpdateHuntVm;
import org.rabie.hunters_league.web.vm.response.HuntResponseVm;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/hunt")
public class HuntController {

    private final HuntService huntService;
    private final SpeciesService speciesService;
    private final ParticipationService participationService;
    private final HuntMapper huntMapper;

    public HuntController(HuntService huntService, HuntMapper huntMapper, SpeciesService speciesService, ParticipationService participationService) {
        this.huntService = huntService;
        this.huntMapper = huntMapper;
        this.speciesService = speciesService;
        this.participationService = participationService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('JURY')")
    public Page<HuntResponseVm> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        return huntService.getAll(page, size).map(huntMapper::toHuntResponseVm);
    }




    @PostMapping("/create")
    @PreAuthorize("hasRole('JURY')")
    public ResponseEntity<HuntResponseVm> createHunt(@Valid @RequestBody CreateHuntVm createHuntVm){
        Species species = speciesService.getById(createHuntVm.getSpeciesId());
        Participation participation = participationService.getById(createHuntVm.getParticipationId());
        Hunt hunt = new Hunt();
        hunt.setSpecies(species);
        hunt.setParticipation(participation);
        hunt.setWeight(createHuntVm.getWeight());
        Hunt createdHunt = huntService.createHunt(hunt);
        HuntResponseVm huntResponseVm = huntMapper.toHuntResponseVm(createdHunt);
        return ResponseEntity.ok(huntResponseVm);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('JURY')")
    public ResponseEntity<HuntResponseVm> updateHunt(@Valid @RequestBody UpdateHuntVm huntUpdateVm){
        Species species = speciesService.getById(huntUpdateVm.getSpeciesId());
        Participation participation = participationService.getById(huntUpdateVm.getParticipationId());
        Hunt hunt = new Hunt();
        hunt.setId(huntUpdateVm.getId());
        hunt.setSpecies(species);
        hunt.setParticipation(participation);
        hunt.setWeight(huntUpdateVm.getWeight());
        Hunt updatedHunt = huntService.updateHunt(hunt);
        HuntResponseVm huntResponseVm = huntMapper.toHuntResponseVm(updatedHunt);
        return ResponseEntity.ok(huntResponseVm);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('JURY')")
    public ResponseEntity<Void> deleteHunt(@PathVariable UUID id){
        huntService.deleteHunt(id);
        return ResponseEntity.ok().build();
    }
}
