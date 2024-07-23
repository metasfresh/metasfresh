package de.metas.distribution.ddordercandidate.material_dispo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderCandidateUpdatedEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class PPOrderCandidateListeners
{
	@Service
	@Profile(Profiles.PROFILE_MaterialDispo)
	@RequiredArgsConstructor
	public static class PPOrderCandidateUpdatedEventHandler implements MaterialEventHandler<PPOrderCandidateUpdatedEvent>
	{
		@NonNull private final PPOrderCandidateDAO ppOrderCandidateDAO;
		@NonNull private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
		@NonNull private final CandidateRepositoryWriteService candidateRepositoryWriteService;

		@Override
		public Collection<Class<? extends PPOrderCandidateUpdatedEvent>> getHandledEventType() {return ImmutableList.of(PPOrderCandidateUpdatedEvent.class);}

		@Override
		public void handleEvent(final PPOrderCandidateUpdatedEvent event)
		{
			final PPOrderCandidateId ppOrderCandidateId = PPOrderCandidateId.ofRepoId(event.getPpOrderCandidateId());

			final ImmutableSet<PPOrderId> ppOrderIds = ppOrderCandidateDAO.getPPOrderIds(ppOrderCandidateId);
			final PPOrderId ppOrderId = ppOrderIds.size() == 1 ? ppOrderIds.iterator().next() : null;

			updateProductionDetailsByPPOrderCandidateId(ppOrderCandidateId, ppOrderId);
			updateDistributionDetailsByPPOrderCandidateId(ppOrderCandidateId, ppOrderId);
		}

		private void updateProductionDetailsByPPOrderCandidateId(@NonNull final PPOrderCandidateId ppOrderCandidateId, @Nullable final PPOrderId newPPOrderId)
		{
			updateCandidatesByQuery(
					CandidatesQuery.builder()
							.businessCase(CandidateBusinessCase.PRODUCTION)
							.productionDetailsQuery(ProductionDetailsQuery.builder().ppOrderCandidateId(ppOrderCandidateId.getRepoId()).build())
							.build(),
					candidate -> {
						final ProductionDetail productionDetail = candidate.getBusinessCaseDetail(ProductionDetail.class)
								.orElseThrow(() -> new AdempiereException("No ProductionDetail found for " + candidate));

						return candidate.withBusinessCaseDetail(productionDetail.withPPOrderId(newPPOrderId));
					});
		}

		private void updateDistributionDetailsByPPOrderCandidateId(@NonNull final PPOrderCandidateId ppOrderCandidateId, @Nullable final PPOrderId newPPOrderId)
		{
			updateCandidatesByQuery(
					CandidatesQuery.builder()
							.businessCase(CandidateBusinessCase.DISTRIBUTION)
							.distributionDetailsQuery(DistributionDetailsQuery.builder().ppOrderCandidateId(ppOrderCandidateId.getRepoId()).build())
							.build(),
					candidate -> {
						final DistributionDetail distributionDetail = candidate.getBusinessCaseDetail(DistributionDetail.class)
								.orElseThrow(() -> new AdempiereException("No DistributionDetail found for " + candidate));

						return candidate.withBusinessCaseDetail(distributionDetail.withPPOrderId(newPPOrderId));
					});
		}

		private void updateCandidatesByQuery(@NonNull final CandidatesQuery query, @NonNull final UnaryOperator<Candidate> updater)
		{
			final List<Candidate> candidates = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);

			for (final Candidate candidate : candidates)
			{
				final Candidate changedCandidate = updater.apply(candidate);
				if (!Objects.equals(candidate, changedCandidate))
				{
					candidateRepositoryWriteService.updateCandidateById(changedCandidate);
				}
			}
		}
	}

}
