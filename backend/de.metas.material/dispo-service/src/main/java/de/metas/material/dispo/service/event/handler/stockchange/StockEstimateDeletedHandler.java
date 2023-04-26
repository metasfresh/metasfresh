/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.dispo.service.event.handler.stockchange;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.StockChangeDetail;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.repohelpers.RepositoryCommons;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class StockEstimateDeletedHandler implements MaterialEventHandler<AbstractStockEstimateEvent>
{
	private static final transient Logger logger = LogManager.getLogger(StockEstimateDeletedHandler.class);

	private final StockEstimateEventService stockEstimateEventService;
	private final CandidateChangeService candidateChangeHandler;
	private final StockChangeDetailRepo stockChangeDetailRepo;

	public StockEstimateDeletedHandler(
			@NonNull final StockEstimateEventService stockEstimateEventService,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final StockChangeDetailRepo stockChangeDetailRepo)
	{
		this.stockEstimateEventService = stockEstimateEventService;
		this.candidateChangeHandler = candidateChangeHandler;
		this.stockChangeDetailRepo = stockChangeDetailRepo;
	}

	@Override
	public Collection<Class<? extends AbstractStockEstimateEvent>> getHandledEventType()
	{
		return ImmutableList.of(StockEstimateDeletedEvent.class);
	}

	@Override
	public void handleEvent(final AbstractStockEstimateEvent event)
	{
		final Candidate candidateToDelete = stockEstimateEventService.retrieveExistingStockEstimateCandidateOrNull(event);

		if (candidateToDelete == null)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("Nothing to do for event, record was already deleted", event);
			return;
		}

		final BusinessCaseDetail businessCaseDetail = candidateToDelete.getBusinessCaseDetail();
		if (businessCaseDetail == null)
		{
			throw new AdempiereException("No business case detail found for candidate")
					.appendParametersToMessage()
					.setParameter("candidateId: ", candidateToDelete.getId());
		}

		final StockChangeDetail existingStockChangeDetail = StockChangeDetail.cast(businessCaseDetail)
				.toBuilder()
				.isReverted(true)
				.build();

		final CandidatesQuery query = CandidatesQuery.fromCandidate(candidateToDelete, false);

		final I_MD_Candidate candidateRecord = RepositoryCommons
				.mkQueryBuilder(query)
				.create()
				.firstOnly(I_MD_Candidate.class);

		if (candidateRecord == null)
		{
			throw new AdempiereException("No MD_Candidate found for candidate")
					.appendParametersToMessage()
					.setParameter("candidateId: ", candidateToDelete.getId());
		}

		stockChangeDetailRepo.saveOrUpdate(existingStockChangeDetail, candidateRecord);

		candidateChangeHandler.onCandidateDelete(candidateToDelete);
	}
}
