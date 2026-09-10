package de.metas.material.dispo.service.event.handler.stockchange;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.newMaterialDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Mirrors {@link de.metas.material.dispo.service.event.handler.StockChangedEventHandlerTest}'s
 * unfulfilled-planned-position coverage - see that class for why re-baselining onto a bare counted qty is unsafe.
 */
public class StockEstimateCreatedHandlerTest
{
	private StockEstimateCreatedHandler stockEstimateCreatedHandler;
	private CandidateChangeService candidateChangeService;
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		candidateRepositoryRetrieval = Mockito.mock(CandidateRepositoryRetrieval.class);
		candidateChangeService = Mockito.mock(CandidateChangeService.class);

		final StockEstimateEventService stockEstimateEventService = new StockEstimateEventService(candidateRepositoryRetrieval);
		stockEstimateCreatedHandler = new StockEstimateCreatedHandler(candidateChangeService, stockEstimateEventService);
	}

	/**
	 * No existing stock candidate, no planned positions: counted qty 10 -> INVENTORY_UP of 10, unchanged behaviour.
	 */
	@Test
	public void handleEvent_noPlannedPositions_reBaselinesOntoTheCountedQty()
	{
		final StockEstimateCreatedEvent event = createEvent(new BigDecimal("10"));

		// retrieveLatestMatchOrNull defaults to null for every query (both the "existing candidate for this
		// event" check and the "previous STOCK candidate" lookup) - no stubbing needed for this scenario.
		// no stubbing of hasUnfulfilledPlannedPositions either: the chain carries none

		stockEstimateCreatedHandler.handleEvent(event);

		final ArgumentCaptor<Candidate> candidateCaptor = ArgumentCaptor.forClass(Candidate.class);
		verify(candidateChangeService).onCandidateNewOrChange(candidateCaptor.capture());
		final Candidate candidate = candidateCaptor.getValue();
		assertThat(candidate.getType()).isEqualTo(CandidateType.INVENTORY_UP);
		assertThat(candidate.getQuantity()).isEqualByComparingTo("10");
	}

	/**
	 * A chain whose running balance (170) carries an unfulfilled planned position of 30 on top of a physical 140.
	 * A Zählbestand count of 140 (the correct physical qty) must NOT re-baseline the chain onto 140 - that would
	 * silently absorb the still-open 30. Unlike {@code StockChangedEvent}, this event carries no separate
	 * old/new pair to fall back to a pure physical-movement delta, so the safe behaviour is to write nothing at
	 * all and leave the correction to the ATP reconciliation process.
	 */
	@Test
	public void handleEvent_unfulfilledPlannedPositions_writesNoCandidate()
	{
		final StockEstimateCreatedEvent event = createEvent(new BigDecimal("140"));

		// only the "previous STOCK candidate" query returns a match; the "existing candidate for this event"
		// check must still see null, or the handler's own guard against a duplicate candidate fires instead.
		when(candidateRepositoryRetrieval.retrieveLatestMatchOrNull(argThat(q -> q != null && q.getType() == CandidateType.STOCK)))
				.thenReturn(stockCandidateWithQuantity("170"));
		when(candidateRepositoryRetrieval.hasUnfulfilledPlannedPositions(any()))
				.thenReturn(true);

		stockEstimateCreatedHandler.handleEvent(event);

		verify(candidateChangeService, never()).onCandidateNewOrChange(any());
	}

	private static Candidate stockCandidateWithQuantity(@NonNull final String quantity)
	{
		return Candidate.builder()
				.type(CandidateType.STOCK)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(newMaterialDescriptor().withQuantity(new BigDecimal(quantity)))
				.build();
	}

	private StockEstimateCreatedEvent createEvent(@NonNull final BigDecimal countedQty)
	{
		return StockEstimateCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.materialDescriptor(newMaterialDescriptor().withQuantity(countedQty))
				.date(Instant.parse("2018-11-19T10:15:30.00Z"))
				.plantId(0)
				.freshQtyOnHandId(60)
				.freshQtyOnHandLineId(70)
				.qtyStockEstimateSeqNo(null)
				.eventDate(Instant.parse("2018-11-19T10:15:30.00Z"))
				.build();
	}
}
