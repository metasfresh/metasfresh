package de.metas.material.dispo.service.event.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.UnrelatedTransactionCandidateHandler;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.TransactionEvent;
import de.metas.material.event.TransactionEvent.TransactionEventBuilder;

/*
 * #%L
 * metasfresh-material-dispo-service
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

public class TransactionEventHandlerTest
{
	private TransactionEventHandler transactionEventHandler;

	private MockedUnrelatedTransactionCandidateHandler mockedUnrelatedTransactionCandidateHandler;

	private static final class MockedUnrelatedTransactionCandidateHandler extends UnrelatedTransactionCandidateHandler
	{
		private Candidate candidateParameter;

		@Override
		public Candidate onCandidateNewOrChange(Candidate candidate)
		{
			candidateParameter = candidate;
			return candidate;
		}
	}

	private static final class MockedDemandCandidateHandler extends DemandCandiateHandler
	{
		public MockedDemandCandidateHandler(CandidateRepository candidateRepository, MaterialEventService materialEventService, StockCandidateService stockCandidateService)
		{
			super(candidateRepository, materialEventService, stockCandidateService);
		}

		private Candidate candidateParameter;

		@Override
		public Candidate onCandidateNewOrChange(Candidate candidate)
		{
			candidateParameter = candidate;
			return candidate;
		}
	}
	
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepository candidateRepository = new CandidateRepository();
		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepository);

		mockedUnrelatedTransactionCandidateHandler = new MockedUnrelatedTransactionCandidateHandler();

		transactionEventHandler = new TransactionEventHandler(
				stockCandidateService,
				new CandidateChangeService(ImmutableList.of(mockedUnrelatedTransactionCandidateHandler)));
	}

	@Test
	public void unrelatedTransaction()
	{
		final TransactionEvent unrelatedEvent = createTransactionEventBuilder().build();

		transactionEventHandler.handleTransactionEvent(unrelatedEvent);

		final Candidate candidateParameter = mockedUnrelatedTransactionCandidateHandler.candidateParameter;
		assertThat(candidateParameter).isNotNull();
		assertThat(candidateParameter.getType()).isEqualTo(Type.UNRELATED_TRANSACTION);
		assertThat(candidateParameter.getMaterialDescr()).isNotNull();
		assertThat(candidateParameter.getQuantity()).isEqualByComparingTo("10");
		assertThat(candidateParameter.getProductId()).isEqualByComparingTo(40);
		assertThat(candidateParameter.getWarehouseId()).isEqualByComparingTo(50);

		assertThat(candidateParameter.getDemandDetail()).isNull();
		assertThat(candidateParameter.getDistributionDetail()).isNull();
		assertThat(candidateParameter.getProductionDetail()).isNull();
	}

	@Test
	public void relatedTransaction()
	{
		createTransactionEventBuilder().shipmentScheduleId(40);
	}

	private TransactionEventBuilder createTransactionEventBuilder()
	{
		return TransactionEvent.builder()
				.eventDescr(new EventDescr(10, 20))
				.materialDescr(MaterialDescriptor.builder()
						.date(TimeUtil.parseTimestamp("2017-10-15"))
						.productId(40)
						.quantity(BigDecimal.TEN)
						.warehouseId(50)
						.build());

	}
}
