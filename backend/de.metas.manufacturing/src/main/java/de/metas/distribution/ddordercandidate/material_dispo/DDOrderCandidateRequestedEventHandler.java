package de.metas.distribution.ddordercandidate.material_dispo;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.event.ddordercandidate.DDOrderCandidateRequestedEvent;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eevolution.api.PPOrderId;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.dao.IPPOrderCandidateDAO;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_App) // only one handler should bother itself with these events
@RequiredArgsConstructor
public class DDOrderCandidateRequestedEventHandler
		implements MaterialEventHandler<DDOrderCandidateRequestedEvent>
{
	@NonNull private static final Logger logger = LogManager.getLogger(DDOrderCandidateRequestedEventHandler.class);
	@NonNull private final DDOrderCandidateService ddOrderCandidateService;
	@NonNull private final CandidateRepositoryWriteService candidateRepositoryWriteService;
	@NonNull private final IPPOrderCandidateDAO ppOrderCandidateDAO = Services.get(IPPOrderCandidateDAO.class);

	@Override
	public Collection<Class<? extends DDOrderCandidateRequestedEvent>> getHandledEventType()
	{
		return ImmutableList.of(DDOrderCandidateRequestedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final DDOrderCandidateRequestedEvent event)
	{
		final DDOrderCandidate ddOrderCandidate = createAndSaveDDOrderCandidate(event);
		updateMDCandidatesFromDDOrderCandidate(ddOrderCandidate);

		if (event.isCreateDDOrder())
		{
			ddOrderCandidateService.enqueueToProcess(ddOrderCandidate.getIdNotNull());
		}
		else
		{
			Loggables.addLog("Skip creating DD_Order because isCreateDDOrder=false (maybe because PP_Product_Planning.IsCreatePlan=false?)");
		}
	}

	private void updateMDCandidatesFromDDOrderCandidate(final DDOrderCandidate ddOrderCandidate)
	{
		final DDOrderCandidateId ddOrderCandidateId = ddOrderCandidate.getIdNotNull();

		if (ddOrderCandidate.getMaterialDispoGroupId() != null)
		{
			candidateRepositoryWriteService.updateCandidatesByQuery(
					CandidatesQuery.builder()
							.groupId(ddOrderCandidate.getMaterialDispoGroupId())
							.businessCase(CandidateBusinessCase.DISTRIBUTION)
							.build(),
					candidate -> candidate.withBusinessCaseDetail(DistributionDetail.class, dispoDetail -> dispoDetail.withDdOrderCandidateId(ddOrderCandidateId.getRepoId()))
			);
		}
	}

	private DDOrderCandidate createAndSaveDDOrderCandidate(@NonNull final DDOrderCandidateRequestedEvent event)
	{
		final DDOrderCandidate ddOrderCandidate = toDDOrderCandidate(event);
		ddOrderCandidateService.save(ddOrderCandidate);
		Loggables.withLogger(logger, Level.DEBUG).addLog("Created DD Order candidate: {}", ddOrderCandidate);
		return ddOrderCandidate;
	}

	private DDOrderCandidate toDDOrderCandidate(@NonNull final DDOrderCandidateRequestedEvent event)
	{
		final PPOrderId forwardPPOrderId = findForwardPPOrderId(event);
		final DDOrderCandidateData ddOrderCandidateData = event.getDdOrderCandidateData().withPPOrderId(forwardPPOrderId);

		return DDOrderCandidate.from(ddOrderCandidateData)
				.dateOrdered(event.getDateOrdered())
				.traceId(event.getTraceId())
				.build();
	}

	@Nullable
	private PPOrderId findForwardPPOrderId(@NonNull final DDOrderCandidateRequestedEvent event)
	{
		return findForwardPPOrderId(event.getDdOrderCandidateData().getForwardPPOrderRef());
	}

	@Nullable
	private PPOrderId findForwardPPOrderId(@Nullable final PPOrderRef forwardPPOrderRef)
	{
		if (forwardPPOrderRef == null)
		{
			return null;
		}

		if (forwardPPOrderRef.getPpOrderId() != null)
		{
			return forwardPPOrderRef.getPpOrderId();
		}

		if (forwardPPOrderRef.getPpOrderCandidateId() > 0)
		{
			final PPOrderCandidateId ppOrderCandidateId = PPOrderCandidateId.ofRepoId(forwardPPOrderRef.getPpOrderCandidateId());
			final ImmutableSet<PPOrderId> forwardPPOrderIds = ppOrderCandidateDAO.getPPOrderIds(ppOrderCandidateId);
			return forwardPPOrderIds.size() == 1 ? forwardPPOrderIds.iterator().next() : null;
		}

		return null;
	}
}
