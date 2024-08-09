package de.metas.distribution.ddordercandidate.material_dispo;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
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
import de.metas.material.event.ddordercandidate.DDOrderCandidateRequestedEvent;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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

	private static DDOrderCandidate toDDOrderCandidate(@NonNull final DDOrderCandidateRequestedEvent event)
	{
		return DDOrderCandidate.from(event.getDdOrderCandidateData())
				.dateOrdered(event.getDateOrdered())
				.traceId(event.getTraceId())
				.build();
	}
}
