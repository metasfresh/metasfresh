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

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.StockChangeDetail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class StockEstimateCreatedHandler implements MaterialEventHandler<AbstractStockEstimateEvent>
{
	private final CandidateChangeService candidateChangeHandler;
	private final StockEstimateEventService stockEstimateEventService;

	public StockEstimateCreatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final StockEstimateEventService stockEstimateEventService)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.stockEstimateEventService = stockEstimateEventService;
	}

	@Override
	public Collection<Class<? extends AbstractStockEstimateEvent>> getHandledEventType()
	{
		return ImmutableList.of(StockEstimateCreatedEvent.class);
	}

	@Override
	public void handleEvent(final AbstractStockEstimateEvent event)
	{
		final Candidate existingStockEstimateCandidate = stockEstimateEventService.retrieveExistingStockEstimateCandidateOrNull(event);

		if (existingStockEstimateCandidate != null)
		{
			throw new AdempiereException("No candidate should exist for event, but an actual candidate was returned")
					.appendParametersToMessage()
					.setParameter("StockEstimateCreatedEvent", event);
		}

		final Candidate previousStockOrNull = stockEstimateEventService.retrievePreviousStockCandidateOrNull(event);

		final BigDecimal currentATP = previousStockOrNull != null ? previousStockOrNull.getQuantity() : BigDecimal.ZERO;
		final BigDecimal deltaATP = currentATP.subtract(event.getQuantityDelta());

		final StockChangeDetail stockChangeDetail = StockChangeDetail.builder()
				.eventDate(event.getEventDate())
				.freshQuantityOnHandRepoId(event.getFreshQtyOnHandId())
				.freshQuantityOnHandLineRepoId(event.getFreshQtyOnHandLineId())
				.isReverted(false)
				.build();

		final Candidate.CandidateBuilder supplyCandidateBuilder = Candidate.builder()
				.clientAndOrgId(event.getEventDescriptor().getClientAndOrgId())
				.materialDescriptor(event.getMaterialDescriptor())
				.businessCase(CandidateBusinessCase.STOCK_CHANGE)
				.businessCaseDetail(stockChangeDetail);

		if (deltaATP.signum() > 0)
		{
			supplyCandidateBuilder
					.type(CandidateType.INVENTORY_DOWN)
					.quantity(deltaATP);
		}
		else
		{
			supplyCandidateBuilder
					.type(CandidateType.INVENTORY_UP)
					.quantity(deltaATP.negate());
		}

		candidateChangeHandler.onCandidateNewOrChange(supplyCandidateBuilder.build());
	}
}
