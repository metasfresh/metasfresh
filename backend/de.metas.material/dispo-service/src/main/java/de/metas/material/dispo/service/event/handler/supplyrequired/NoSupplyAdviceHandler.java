package de.metas.material.dispo.service.event.handler.supplyrequired;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.NoSupplyAdviceEvent;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Profile(Profiles.PROFILE_MaterialDispo)
@RequiredArgsConstructor
public class NoSupplyAdviceHandler implements MaterialEventHandler<NoSupplyAdviceEvent>
{
	@NonNull private final CandidateRepositoryWriteService candidateRepositoryWriteService;

	@Override
	public Collection<Class<? extends NoSupplyAdviceEvent>> getHandledEventType() {return ImmutableList.of(NoSupplyAdviceEvent.class);}

	@Override
	public void handleEvent(@NonNull final NoSupplyAdviceEvent event)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();
		final CandidateRepositoryWriteService.DeleteResult deleteResult = candidateRepositoryWriteService.deleteCandidateById(CandidateId.ofRepoId(supplyRequiredDescriptor.getSupplyCandidateId()));
		Loggables.addLog("Deleted {} for {}", deleteResult, supplyRequiredDescriptor);
	}
}
