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
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;

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
