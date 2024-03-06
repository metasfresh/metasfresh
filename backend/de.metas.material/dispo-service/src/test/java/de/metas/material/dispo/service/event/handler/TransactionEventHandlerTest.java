package de.metas.material.dispo.service.event.handler;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.*;

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

public class TransactionEventHandlerTest
{
	private DimensionService dimensionService = Mockito.mock(DimensionService.class);

	@Test
	public void createOneOrTwoCandidatesWithChangedTransactionDetailAndQuantity()
	{
		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(ImmutableList.of());
		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();
		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);

		final TransactionEventHandler transactionEventHandler = new TransactionEventHandler(
				candidateChangeHandler,
				candidateRepository,
				Mockito.mock(PostMaterialEventService.class));

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("23"))
				.date(AFTER_NOW)
				.build();

		final TransactionCreatedEvent transactionCreatedEvent = TransactionCreatedEvent.builder()
				.transactionId(30)
				.materialDescriptor(materialDescriptor)
				.build();

		final Candidate candidate = Candidate.builder()
				.id(CandidateId.ofRepoId(100))
				.parentId(CandidateId.ofRepoId(200))
				.type(CandidateType.SUPPLY)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescriptor)
				.businessCase(CandidateBusinessCase.PURCHASE)
				.businessCaseDetail(PurchaseDetail.builder()
											.qty(new BigDecimal("23"))
											.advised(Flag.FALSE_DONT_UPDATE)
											.build())
				.build();

		final TransactionDetail transactionDetail = TransactionDetail.builder()
				.quantity(new BigDecimal("23"))
				.storageAttributesKey(AttributesKey.ofAttributeValueIds(10))
				.attributeSetInstanceId(20)
				.transactionId(30)
				.transactionDate(AFTER_NOW)
				.complete(true)
				.build();

		// invoke the method under test
		final List<Candidate> result = transactionEventHandler.createOneOrTwoCandidatesWithChangedTransactionDetailAndQuantity(candidate, transactionDetail, transactionCreatedEvent);
		assertThat(result).hasSize(2);

		assertThat(result.get(1).getId()).isEqualTo(candidate.getId());
		assertThat(result.get(1).getParentId()).isEqualTo(candidate.getParentId());
		assertThat(result.get(1).getQuantity()).isEqualTo(ZERO);

		assertThat(result.get(0).getId()).isEqualTo(CandidateId.NULL);
		assertThat(result.get(0).getParentId()).isEqualTo(CandidateId.NULL);
		assertThat(result.get(0).getQuantity()).isEqualByComparingTo("23");
	}
}
