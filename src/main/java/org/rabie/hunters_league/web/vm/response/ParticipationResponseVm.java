package org.rabie.hunters_league.web.vm.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class ParticipationResponseVm {
    private UUID id;
    private UserResponseVm appUser;
    private CompetitionResponseVm competition;
    private Double score;
}
