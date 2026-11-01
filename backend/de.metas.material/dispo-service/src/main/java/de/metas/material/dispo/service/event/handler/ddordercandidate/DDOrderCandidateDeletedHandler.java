package de.metas.material.dispo.service.event.handler.ddordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.ddordercandidate.DDOrderCandidateDeletedEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
@RequiredArgsConstructor
public class DDOrderCandidateDeletedHandler implements MaterialEventHandler<DDOrderCandidateDeletedEvent>
{
	@NonNull private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	@NonNull private final CandidateChangeService candidateChangeHandler;


	@Override
	public Collection<Class<? extends DDOrderCandidateDeletedEvent>> getHandledEventType()
	{
		return ImmutableList.of(DDOrderCandidateDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final DDOrderCandidateDeletedEvent event)
	{
		deleteCandidates(event);
	}

	private void deleteCandidates(@NonNull final DDOrderCandidateDeletedEvent event)
	{
		final int ddOrderCandidateId = event.getExistingDDOrderCandidateId();
		if (ddOrderCandidateId <= 0)
		{
			throw new AdempiereException("existing DD_Order_Candidate_ID should be provided")
					.setParameter("event", event)
					.appendParametersToMessage();
		}

		final CandidatesQuery query = CandidatesQuery
				.builder()
				.distributionDetailsQuery(DistributionDetailsQuery.builder()
												  .ddOrderCandidateId(ddOrderCandidateId)
												  .build())
				.build();

		for (final Candidate candidate : candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query))
		{
			// deleting a candidate also deletes its linked supply or demand candidate (both directions); the list is ordered by date and seqNo, so either side may already be gone
			if (candidateRepositoryRetrieval.retrieveLatestMatchOrNull(CandidatesQuery.fromId(candidate.getId())) == null)
			{
				continue;
			}
			candidateChangeHandler.onCandidateDelete(candidate);
		}
	}
}
