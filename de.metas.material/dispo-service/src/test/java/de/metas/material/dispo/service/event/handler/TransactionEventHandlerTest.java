package de.metas.material.dispo.service.event.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.TransactionEvent;

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

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		
		final CandidateRepository candidateRepository = new CandidateRepository();
		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepository);

		transactionEventHandler = new TransactionEventHandler(
				stockCandidateService,
				new CandidateChangeService(
						candidateRepository,
						stockCandidateService,
						MaterialEventService.createLocalServiceThatIsReadyToUse()));
	}

	@Test
	public void unrelatedTransaction_increase_stock()
	{
		final TransactionEvent event = TransactionEvent.builder()
				.eventDescr(new EventDescr(10, 20))
				.materialDescr(MaterialDescriptor.builder()
						.date(TimeUtil.parseTimestamp("2017-10-15"))
						.productId(40)
						.quantity(BigDecimal.TEN)
						.warehouseId(50)
						.build())
				.build();
		transactionEventHandler.handleTransactionEvent(event);
		
		// TODO: verify that we have:
		// one stock record
		// one unrelated-trx-record as th stock record's child and +10
		
		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);
	}
}
