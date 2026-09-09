package de.metas.material.dispo.service.event.handler;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.newMaterialDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.material.event.stock.StockChangedEvent.StockChangeDetails;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class StockChangedEventHandlerTest
{
	private StockChangedEventHandler stockChangedEventHandler;
	private CandidateChangeService candidateChangeService;
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		candidateRepositoryRetrieval = Mockito.mock(CandidateRepositoryRetrieval.class);
		candidateChangeService = Mockito.mock(CandidateChangeService.class);

		stockChangedEventHandler = new StockChangedEventHandler(
				candidateRepositoryRetrieval,
				candidateChangeService);
	}

	/**
	 * No existing stock record<br>
	 * StockChangedEvent with qty=10;<br>
	 * <p>
	 * => Expect candidateChangeService to be invoked with an "INVENTORY_UP" candidate that has qty 10;<br>
	 * so the Stock is raised from 0 to 10 which is what the StockChangedEvent said
	 */
	@Test
	public void handleEvent_inventoryUp_no_existing_records()
	{
		final StockChangedEvent event = createCommonStockChangedEvent();

		when(candidateRepositoryRetrieval.retrieveLatestMatchOrNull(any()))
				.thenReturn(null);

		// invoke the method under test
		stockChangedEventHandler.handleEvent(event);

		final ArgumentCaptor<Candidate> candidateCaptor = ArgumentCaptor.forClass(Candidate.class);
		verify(candidateChangeService)
				.onCandidateNewOrChange(candidateCaptor.capture());
		final Candidate candidate = candidateCaptor.getValue();
		//
		assertInvocationCandidateCommons(candidate);
		assertThat(candidate.getType()).isEqualTo(CandidateType.INVENTORY_UP);
		assertThat(candidate.getQuantity()).isEqualByComparingTo(TEN);
	}

	/**
	 * Existing stock record with qty=15;<br>
	 * StockChangedEvent with qty=10;<br>
	 * <p>
	 * => Expect candidateChangeService to be invoked with an "INVENTORY_DOWN" candidate that has qty 5;<br>
	 * so the Stock is reduced from 15 to 10 which is what the StockChangedEvent said
	 */
	@Test
	public void handleEvent_inventoryDown_existing_record()
	{
		final StockChangedEvent event = createCommonStockChangedEvent();
		assertThat(event.getQtyOnHand()).isEqualByComparingTo(TEN); // guard

		when(candidateRepositoryRetrieval.retrieveLatestMatchOrNull(any()))
				.thenReturn(Candidate.builder()
						.type(CandidateType.STOCK)
						.clientAndOrgId(CLIENT_AND_ORG_ID)
						.materialDescriptor(newMaterialDescriptor().withQuantity(new BigDecimal("15")))
						.build());

		// invoke the method under test
		stockChangedEventHandler.handleEvent(event);

		final ArgumentCaptor<Candidate> candidateCaptor = ArgumentCaptor.forClass(Candidate.class);
		verify(candidateChangeService)
				.onCandidateNewOrChange(candidateCaptor.capture());
		final Candidate candidate = candidateCaptor.getValue();
		//
		assertInvocationCandidateCommons(candidate);
		assertThat(candidate.getType()).isEqualTo(CandidateType.INVENTORY_DOWN);
		assertThat(candidate.getQuantity()).isEqualByComparingTo("5");
	}

	/**
	 * A key that was never reconciled: its chain carries no unfulfilled planned position, but its running
	 * balance (15) has drifted away from the physical quantity.<br>
	 * StockChangedEvent with qtyOnHandOld=20, qtyOnHand=10;<br>
	 * <p>
	 * => Expect an "INVENTORY_DOWN" of 5, i.e. the chain is re-baselined onto the physical 10. Applying the
	 * event's physical movement (-10) instead would have produced an "INVENTORY_DOWN" of 10 and left the chain
	 * at 5. Re-baselining such a chain is the reset-stock process's original purpose, and it has to survive
	 * the change that stops the refresh from discarding still-open positions.
	 */
	@Test
	public void handleEvent_noPlannedPositions_reBaselinesOntoThePhysicalQty()
	{
		final StockChangedEvent event = createStockChangedEvent(new BigDecimal("20"), TEN);

		when(candidateRepositoryRetrieval.retrieveLatestMatchOrNull(any()))
				.thenReturn(stockCandidateWithQuantity("15"));
		// no stubbing of hasUnfulfilledPlannedPositions: the chain carries none

		// invoke the method under test
		stockChangedEventHandler.handleEvent(event);

		final ArgumentCaptor<Candidate> candidateCaptor = ArgumentCaptor.forClass(Candidate.class);
		verify(candidateChangeService)
				.onCandidateNewOrChange(candidateCaptor.capture());
		final Candidate candidate = candidateCaptor.getValue();
		//
		assertThat(candidate.getType()).isEqualTo(CandidateType.INVENTORY_DOWN);
		assertThat(candidate.getQuantity()).isEqualByComparingTo("5");
	}

	/**
	 * A chain whose running balance (170) is deliberately not the physical quantity, because it carries an
	 * unfulfilled planned position of 30.<br>
	 * StockChangedEvent with qtyOnHandOld=200, qtyOnHand=205, i.e. the physical quantity moved by +5;<br>
	 * <p>
	 * => Expect an "INVENTORY_UP" of 5, so the chain lands on 175 and keeps the position. Re-baselining onto
	 * the bare physical 205 would silently absorb the 30 - the reset-stock process is a repeatable batch, so
	 * that used to undo a reconciliation on every run.
	 */
	@Test
	public void handleEvent_unfulfilledPlannedPositions_appliesOnlyThePhysicalMovement()
	{
		final StockChangedEvent event = createStockChangedEvent(new BigDecimal("200"), new BigDecimal("205"));

		when(candidateRepositoryRetrieval.retrieveLatestMatchOrNull(any()))
				.thenReturn(stockCandidateWithQuantity("170"));
		when(candidateRepositoryRetrieval.hasUnfulfilledPlannedPositions(any()))
				.thenReturn(true);

		// invoke the method under test
		stockChangedEventHandler.handleEvent(event);

		final ArgumentCaptor<Candidate> candidateCaptor = ArgumentCaptor.forClass(Candidate.class);
		verify(candidateChangeService)
				.onCandidateNewOrChange(candidateCaptor.capture());
		final Candidate candidate = candidateCaptor.getValue();
		//
		assertThat(candidate.getType()).isEqualTo(CandidateType.INVENTORY_UP);
		assertThat(candidate.getQuantity()).isEqualByComparingTo("5");
	}

	/**
	 * The same chain as above, but the refresh finds the physical quantity unchanged (200 -> 200).<br>
	 * <p>
	 * => Expect no candidate at all: nothing physical happened, so there is nothing to apply. Before the
	 * change, the delta was taken against the running balance and came out as 200 - 170 = +30, which pushed
	 * the projection back onto the bare physical stock.
	 * <p>
	 * <b>Defensive test - no production producer emits this event.</b> The only producer,
	 * {@code StockDataUpdateRequestHandler.fireStockChangedEvent}, returns early when the old and new
	 * quantities are equal, and its reset-stock caller {@code MD_Stock_Update_From_M_HUs.retrieveHuData}
	 * additionally filters on {@code QtyOnHandChange <> 0}. So this pins the handler's own arithmetic at
	 * the zero-movement boundary and nothing more; the production-reachable regression is the sibling
	 * {@link #handleEvent_unfulfilledPlannedPositions_appliesOnlyThePhysicalMovement()}, which is where a
	 * behaviour change would actually surface for a customer.
	 */
	@Test
	public void handleEvent_unfulfilledPlannedPositions_andUnchangedPhysicalQty_createsNoCandidate()
	{
		final BigDecimal twoHundred = new BigDecimal("200");
		final StockChangedEvent event = createStockChangedEvent(twoHundred, twoHundred);

		when(candidateRepositoryRetrieval.retrieveLatestMatchOrNull(any()))
				.thenReturn(stockCandidateWithQuantity("170"));
		when(candidateRepositoryRetrieval.hasUnfulfilledPlannedPositions(any()))
				.thenReturn(true);

		// invoke the method under test
		stockChangedEventHandler.handleEvent(event);

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

	private StockChangedEvent createStockChangedEvent(
			@NonNull final BigDecimal qtyOnHandOld,
			@NonNull final BigDecimal qtyOnHand)
	{
		final StockChangedEvent event = StockChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.changeDate(Instant.parse("2018-11-19T10:15:30.00Z"))
				.productDescriptor(createProductDescriptor())
				.qtyOnHand(qtyOnHand)
				.qtyOnHandOld(qtyOnHandOld)
				.stockChangeDetails(StockChangeDetails.builder()
						.stockId(30)
						.resetStockPInstanceId(ResetStockPInstanceId.ofRepoId(40))
						.transactionId(50)
						.build())
				.warehouseId(WAREHOUSE_ID)
				.build();
		event.validate(); // guard
		return event;
	}

	private StockChangedEvent createCommonStockChangedEvent()
	{
		final StockChangedEvent event = StockChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.changeDate(Instant.parse("2018-11-19T10:15:30.00Z"))
				.productDescriptor(createProductDescriptor())
				.qtyOnHand(TEN)
				.qtyOnHandOld(ZERO)
				.stockChangeDetails(StockChangeDetails.builder()
						.stockId(30)
						.resetStockPInstanceId(ResetStockPInstanceId.ofRepoId(40))
						.transactionId(50)
						.build())
				.warehouseId(WAREHOUSE_ID)
				.build();
		event.validate(); // guard
		return event;
	}

	private void assertInvocationCandidateCommons(final Candidate candidate)
	{
		assertThat(candidate).isNotNull();
		assertThat(candidate.getClientAndOrgId().getClientId().getRepoId()).isEqualTo(10);
		assertThat(candidate.getOrgId().getRepoId()).isEqualTo(20);
		assertThat(candidate.getTransactionDetails()).hasSize(1);
		assertThat(candidate.getTransactionDetails().get(0).getStockId()).isEqualTo(30);
		assertThat(candidate.getTransactionDetails().get(0).getResetStockPInstanceId().getRepoId()).isEqualTo(40);
		assertThat(candidate.getTransactionDetails().get(0).getTransactionId()).isEqualTo(50);
	}
}
