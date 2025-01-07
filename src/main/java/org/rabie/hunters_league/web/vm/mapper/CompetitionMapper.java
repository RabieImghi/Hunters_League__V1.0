package org.rabie.hunters_league.web.vm.mapper;

import org.mapstruct.Mapper;
import org.rabie.hunters_league.domain.Competition;
import org.rabie.hunters_league.web.vm.request.CreateCompetitionVm;
import org.rabie.hunters_league.web.vm.request.UpdateCompetitionVm;
import org.rabie.hunters_league.web.vm.response.CompetitionResponseVm;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {
    Competition toCompetition(CreateCompetitionVm createCompetitionVm);
    Competition toCompetitionFromUpdate(UpdateCompetitionVm updateCompetitionVm);
    CompetitionResponseVm toCompetitionResponseVm(Competition competition);


}
