package de.metas.material.dispo.service.event.handler.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderLineData;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptorWithOffSet;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

public class PPOrderChangedHandlerTest
{
	private CandidateChangeService candidateChangeService;
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@BeforeEach
	public void init()
	{
		candidateChangeService = Mockito.mock(CandidateChangeService.class);
		candidateRepositoryRetrieval = Mockito.mock(CandidateRepositoryRetrieval.class);
	}

	@Test
	public void handleEvent()
	{
		// setup a candidate to be updated
		final Candidate candidateToUpdate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
				// .status(CandidateStatus.doc_closed)
				.type(CandidateType.DEMAND)
				.materialDescriptor(createMaterialDescriptor())
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.businessCaseDetail(ProductionDetail.builder()
						.qty(TEN)
						.advised(Flag.FALSE)
						.pickDirectlyIfFeasible(Flag.FALSE)
						.build())
				.build();

		final PPOrder ppOrder = createPPOrder();
		final int ppOrderId = ppOrder.getPpOrderId();

		when(candidateRepositoryRetrieval.retrieveCandidatesForPPOrderId(ppOrderId))
				.thenReturn(ImmutableList.of(candidateToUpdate));

		final PPOrderChangedEvent ppOrderChangedEvent = PPOrderChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.oldDocStatus(DocStatus.Completed)
				.newDocStatus(DocStatus.Completed)
				.oldDatePromised(de.metas.common.util.time.SystemTime.asInstant())
				.newDatePromised(SystemTime.asInstant())
				.newQtyDelivered(ONE)
				.newQtyRequired(TEN)
				.oldQtyDelivered(ONE)
				.oldQtyRequired(TEN)
				// .productDescriptor(materialDescriptor)
				.ppOrderAfterChanges(ppOrder)
				.build();

		final PPOrderChangedHandler ppOrderDocStatusChangedHandler = new PPOrderChangedHandler(
				candidateRepositoryRetrieval,
				candidateChangeService);

		//
		// invoke the method under test
		ppOrderDocStatusChangedHandler.handleEvent(ppOrderChangedEvent);

		//
		// verify the updated candidate created by the handler
		final ArgumentCaptor<Candidate> updatedCandidateCaptor = ArgumentCaptor.forClass(Candidate.class);
		verify(candidateChangeService)
				.onCandidateNewOrChange(updatedCandidateCaptor.capture());
		final Candidate updatedCandidate = updatedCandidateCaptor.getValue();

		assertThat(updatedCandidate.getQuantity()).isEqualByComparingTo(BigDecimal.TEN);

		final ProductionDetail productionDetail = ProductionDetail.castOrNull(updatedCandidate.getBusinessCaseDetail());
		assertThat(productionDetail).isNotNull();
		assertThat(productionDetail.getPpOrderDocStatus()).isEqualTo(DocStatus.Completed);
	}

	private PPOrder createPPOrder()
	{
		return PPOrder.builder()
				.ppOrderData(PPOrderData.builder()
									 .clientAndOrgId(ClientAndOrgId.ofClientAndOrg(100, 100))
									 .datePromised(NOW)
									 .dateStartSchedule(NOW)
									 .plantId(ResourceId.ofRepoId(110))
									 .productDescriptor(createProductDescriptor())
									 .productPlanningId(130)
									 .qtyRequired(TEN)
									 .qtyDelivered(ONE)
									 .warehouseId(WarehouseId.ofRepoId(150))
									 .build())
				.ppOrderId(123)
				.line(PPOrderLine.builder()
							  .ppOrderLineData(PPOrderLineData.builder()
													   .productDescriptor(createProductDescriptorWithOffSet(20))
													   .issueOrReceiveDate(NOW)
													   .description("desc2")
													   .productBomLineId(380)
													   .qtyRequired(valueOf(320))
													   .receipt(false)
													   .build())
							  .build())
				.build();
	}

}
