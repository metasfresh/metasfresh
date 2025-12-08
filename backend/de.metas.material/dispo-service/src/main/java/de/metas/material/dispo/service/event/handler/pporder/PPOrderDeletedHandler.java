package de.metas.material.dispo.service.event.handler.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.SimulatedQueryQualifier;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderDeletedEvent;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.organization.IOrgDAO;
import de.metas.util.InSetPredicate;
import de.metas.util.Services;
import lombok.NonNull;
import org.eevolution.api.PPOrderBOMLineId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/*
 * #%L
 * metasfresh-material-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public final class PPOrderDeletedHandler
		implements MaterialEventHandler<PPOrderDeletedEvent>
{
	private final CandidateChangeService candidateChangeService;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public PPOrderDeletedHandler(
			@NonNull final CandidateChangeService candidateChangeService,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeService = candidateChangeService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@Override
	public Collection<Class<? extends PPOrderDeletedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PPOrderDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PPOrderDeletedEvent event)
	{
		final CandidatesQuery preExistingHeaderSupplyQuery = createPreExistingHeaderQuery(event);

		final Candidate existingCandidateOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(preExistingHeaderSupplyQuery);

		if (existingCandidateOrNull != null)
		{
			deleteLineCandidates(event, existingCandidateOrNull);
			candidateChangeService.onCandidateDelete(existingCandidateOrNull);
		}
	}

	private void deleteLineCandidates(
			@NonNull final PPOrderDeletedEvent event,
			@NonNull final Candidate headerCandidate)
	{
		final List<PPOrderLine> ppOrderLines = event.getPpOrder().getLines();
		final boolean simulated = headerCandidate.isSimulated();

		for (final PPOrderLine ppOrderLine : ppOrderLines)
		{
			final Candidate existingLineCandidate = retrieveExistingLineCandidateOrNull(ppOrderLine, simulated);
			if (existingLineCandidate != null)
			{
				candidateChangeService.onCandidateDelete(existingLineCandidate);
			}
		}
	}

	@Nullable
	private Candidate retrieveExistingLineCandidateOrNull(
			@NonNull final PPOrderLine ppOrderLine,
			final boolean simulated)
	{
		final SimulatedQueryQualifier simulatedQueryQualifier = simulated
				? SimulatedQueryQualifier.ONLY_SIMULATED
				: SimulatedQueryQualifier.EXCLUDE_SIMULATED;

		final ProductionDetailsQuery productionDetailsQuery = ProductionDetailsQuery.builder()
				.ppOrderLineIds(InSetPredicate.onlyOrNone(PPOrderBOMLineId.ofRepoIdOrNull(ppOrderLine.getPpOrderLineId())))
				.build();

		final CandidatesQuery lineCandidateQuery = CandidatesQuery.builder()
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.productionDetailsQuery(productionDetailsQuery)
				.simulatedQueryQualifier(simulatedQueryQualifier)
				.groupId(null)
				.build();

		return candidateRepositoryRetrieval.retrieveLatestMatchOrNull(lineCandidateQuery);
	}

	@NonNull
	private CandidatesQuery createPreExistingHeaderQuery(@NonNull final PPOrderDeletedEvent ppOrderCandidateDeletedEvent)
	{
		final PPOrder ppOrderCandidate = ppOrderCandidateDeletedEvent.getPpOrder();

		return CandidatesQuery
				.builder()
				.productionDetailsQuery(ProductionDetailsQuery.builder()
						.ppOrderId(ppOrderCandidate.getPpOrderId())
						.ppOrderLineIds(InSetPredicate.none()) // to get the header (main-product), we just need to exclude the PPOrderLine-IDs
						.build())
				.build();
	}

}
