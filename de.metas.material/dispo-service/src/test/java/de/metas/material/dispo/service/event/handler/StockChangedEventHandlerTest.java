package de.metas.material.dispo.service.event.handler;

import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.material.event.stock.StockChangedEvent.StockChangeDetails;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

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
	private static final BigDecimal FIFTEEN = new BigDecimal("15");

	private static final BigDecimal FIVE = new BigDecimal("5");

	@Tested
	private StockChangedEventHandler stockChangedEventHandler;

	@Injectable
	private CandidateChangeService candidateChangeService;

	@Injectable
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * No existing stock record<br>
	 * StockChangedEvent with qty=10;<br>
	 * <p>
	 * => Expect candidateChangeService to be invoked with an "INVENTORY_UP" candidate that has qty 10;<br>
	 * so the Stock is raised from 0 to 10 which is what the StockChangedEvent said
	 */
	@Test
	public void handleEvent_no_existing_records()
	{
		final StockChangedEvent event = createCommonStockChangedEvent();

		// @formatter:off
		new Expectations()
		{{
			candidateRepositoryRetrieval.retrieveLatestMatchOrNull((CandidatesQuery)any); result = null;
		}};	// @formatter:on

		// invoke the method under test
		stockChangedEventHandler.handleEvent(event);

		// @formatter:off
		new Verifications()
		{{
			Candidate candidate;
			candidateChangeService.onCandidateNewOrChange(candidate = withCapture()); times = 1;

			assertInvocationCandidateCommons(candidate);

			assertThat(candidate.getType()).isEqualTo(CandidateType.INVENTORY_UP);
			assertThat(candidate.getQuantity()).isEqualByComparingTo(TEN);
		}};	// @formatter:on
	}

	/**
	 * Existing stock record with qty=15;<br>
	 * StockChangedEvent with qty=10;<br>
	 * <p>
	 * => Expect candidateChangeService to be invoked with an "INVENTORY_DOWN" candidate that has qty 5;<br>
	 * so the Stock is reduced from 15 to 10 which is what the StockChangedEvent said
	 */
	@Test
	public void handleEvent_existing_record()
	{
		final StockChangedEvent event = createCommonStockChangedEvent();

		// @formatter:off
		new Expectations()
		{{
			final Candidate existingCandidate = Candidate.builder()
					.type(CandidateType.STOCK)
					.clientId(CLIENT_ID)
					.orgId(ORG_ID)
					.materialDescriptor(createMaterialDescriptor().withQuantity(FIFTEEN))
					.build();
			candidateRepositoryRetrieval.retrieveLatestMatchOrNull((CandidatesQuery)any); result = existingCandidate;
		}};	// @formatter:on

		// invoke the method under test
		stockChangedEventHandler.handleEvent(event);

		// @formatter:off
		new Verifications()
		{{
			Candidate candidate;
			candidateChangeService.onCandidateNewOrChange(candidate = withCapture()); times = 1;

			assertInvocationCandidateCommons(candidate);

			assertThat(candidate.getType()).isEqualTo(CandidateType.INVENTORY_DOWN);
			assertThat(candidate.getQuantity()).isEqualByComparingTo(FIVE);
		}};	// @formatter:on
	}

	private StockChangedEvent createCommonStockChangedEvent()
	{
		final StockChangedEvent event = StockChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.changeDate(TimeUtil.parseTimestamp("2018-11-19"))
				.productDescriptor(createProductDescriptor())
				.qtyOnHand(TEN)
				.qtyOnHandOld(ZERO)
				.stockChangeDetails(StockChangeDetails.builder()
						.stockId(30)
						.resetStockAdPinstanceId(40)
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
		assertThat(candidate.getClientId()).isEqualTo(10);
		assertThat(candidate.getOrgId()).isEqualTo(20);
		assertThat(candidate.getTransactionDetails()).hasSize(1);
		assertThat(candidate.getTransactionDetails().get(0).getStockId()).isEqualTo(30);
		assertThat(candidate.getTransactionDetails().get(0).getResetStockAdPinstanceId()).isEqualTo(40);
		assertThat(candidate.getTransactionDetails().get(0).getTransactionId()).isEqualTo(50);
	}
}
