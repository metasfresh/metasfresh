package de.metas.distribution.ddordercandidate.material_dispo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddordercandidate.DDOrderCandidateAllocRepository;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
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
import java.util.HashSet;
import java.util.Set;

public class PPOrderCandidateListeners
{
	@Service
	@Profile(Profiles.PROFILE_MaterialDispo)
	@RequiredArgsConstructor
	public static class PPOrderCandidateUpdatedEventHandler implements MaterialEventHandler<PPOrderCandidateUpdatedEvent>
	{
		@NonNull private final PPOrderCandidateDAO ppOrderCandidateDAO;
		@NonNull private final CandidateRepositoryWriteService candidateRepositoryWriteService;
		@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository;
		@NonNull private final DDOrderCandidateAllocRepository ddOrderCandidateAllocRepository;
		@NonNull private final DDOrderLowLevelDAO ddOrderLowLevelDAO;

		@Override
		public Collection<Class<? extends PPOrderCandidateUpdatedEvent>> getHandledEventType() {return ImmutableList.of(PPOrderCandidateUpdatedEvent.class);}

		@Override
		public void handleEvent(final PPOrderCandidateUpdatedEvent event)
		{
			final PPOrderCandidateId ppOrderCandidateId = event.getPpOrderCandidateId();

			final ImmutableSet<PPOrderId> ppOrderIds = ppOrderCandidateDAO.getPPOrderIds(ppOrderCandidateId);
			final PPOrderId ppOrderId = ppOrderIds.size() == 1 ? ppOrderIds.iterator().next() : null;

			updateDistributionDetailsByPPOrderCandidateId(ppOrderCandidateId, ppOrderId);
			final Set<DDOrderCandidateId> updatedDDOrderCandidateIds = updateDistributionCandidatesByPPOrderCandidateId(ppOrderCandidateId, ppOrderId);
			updateDistributionOrdersByDDOrderCandidateIds(updatedDDOrderCandidateIds, ppOrderId);
		}

		private void updateDistributionDetailsByPPOrderCandidateId(@NonNull final PPOrderCandidateId ppOrderCandidateId, @Nullable final PPOrderId newPPOrderId)
		{
			candidateRepositoryWriteService.updateCandidatesByQuery(
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

		private Set<DDOrderCandidateId> updateDistributionCandidatesByPPOrderCandidateId(@NonNull final PPOrderCandidateId ppOrderCandidateId, @Nullable final PPOrderId newPPOrderId)
		{
			final HashSet<DDOrderCandidateId> ddOrderCandidateIds = new HashSet<>();
			ddOrderCandidateRepository.updateByQuery(
					DDOrderCandidateQuery.builder().ppOrderCandidateId(ppOrderCandidateId).excludePPOrderId(newPPOrderId).build(),
					candidate -> {
						ddOrderCandidateIds.add(candidate.getId());
						return candidate.withForwardPPOrderId(newPPOrderId);
					}
			);
			return ddOrderCandidateIds;
		}

		private void updateDistributionOrdersByDDOrderCandidateIds(@NonNull final Set<DDOrderCandidateId> ddOrderCandidateIds, @Nullable final PPOrderId newPPOrderId)
		{
			final Set<DDOrderId> ddOrderIds = ddOrderCandidateAllocRepository.getByCandidateIds(ddOrderCandidateIds).getDDOrderIds();
			ddOrderLowLevelDAO.updateForwardPPOrderByIds(ddOrderIds, newPPOrderId);
		}
	}
}
