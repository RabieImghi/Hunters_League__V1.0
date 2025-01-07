package org.rabie.hunters_league.web.vm.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateHuntVm {
    @NotNull
    private UUID id;
    @NotNull
    private UUID speciesId;
    @NotNull
    private Double weight;
    @NotNull
    private UUID participationId;
}
