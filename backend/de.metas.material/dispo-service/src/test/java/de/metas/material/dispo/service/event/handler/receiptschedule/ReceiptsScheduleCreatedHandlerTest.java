package de.metas.material.dispo.service.event.handler.receiptschedule;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Purchase_Detail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
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

public class ReceiptsScheduleCreatedHandlerTest
{
	static final int RECEIPT_SCHEDULE_ID = 70;

	private ReceiptsScheduleCreatedHandler receiptsScheduleCreatedHandler;
	private DimensionService dimensionService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new MDCandidateDimensionFactory());
		dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo);
		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepositoryRetrieval, candidateRepositoryWriteService);
		final Collection<CandidateHandler> candidateChangeHandlers = ImmutableList.of(new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService));
		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(candidateChangeHandlers);

		receiptsScheduleCreatedHandler = new ReceiptsScheduleCreatedHandler(candidateChangeHandler, candidateRepositoryRetrieval);
	}

	@Test
	public void handleEvent_ReceiptScheduleCreatedEvent()
	{
		handleEvent_ReceiptScheduleCreatedEvent_performTest(receiptsScheduleCreatedHandler);
	}

	static void handleEvent_ReceiptScheduleCreatedEvent_performTest(@NonNull final ReceiptsScheduleCreatedHandler receiptsScheduleCreatedHandler)
	{
		final MaterialDescriptor eventMaterialDescriptor = createMaterialDescriptor().withDate(NOW);

		final ReceiptScheduleCreatedEvent receiptScheduleCreatedEvent = ReceiptScheduleCreatedEvent
				.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.materialDescriptor(eventMaterialDescriptor)
				.orderLineDescriptor(OrderLineDescriptor.builder()
						.orderId(30)
						.orderLineId(40)
						.docTypeId(50)
						.orderBPartnerId(60)
						.build())
				.receiptScheduleId(RECEIPT_SCHEDULE_ID)
				.vendorId(80)
				.reservedQuantity(new BigDecimal("10"))
				.build()
				.validate();

		receiptsScheduleCreatedHandler.handleEvent(receiptScheduleCreatedEvent);

		//
		// Check DEMAND candidates
		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).isEmpty();

		//
		// Check SUPPLY candidates
		final List<I_MD_Candidate> supplyCandidates = DispoTestUtils.filter(CandidateType.SUPPLY);
		assertThat(supplyCandidates).hasSize(1);
		final I_MD_Candidate supplyCandidate = supplyCandidates.get(0);

		assertThat(supplyCandidate.getM_Product_ID()).isEqualTo(eventMaterialDescriptor.getProductId());
		assertThat(supplyCandidate.getQty()).isEqualByComparingTo(eventMaterialDescriptor.getQuantity());
		assertThat(supplyCandidate.getDateProjected()).isEqualTo(TimeUtil.asTimestamp(eventMaterialDescriptor.getDate()));

		//
		// Check PURCHASE DETAILS
		final List<I_MD_Candidate_Purchase_Detail> purchaseDetails = POJOLookupMap.get().getRecords(I_MD_Candidate_Purchase_Detail.class);
		assertThat(purchaseDetails).hasSize(1);
		final I_MD_Candidate_Purchase_Detail purchaseDetail = purchaseDetails.get(0);
		assertThat(purchaseDetail.getMD_Candidate_ID()).isEqualTo(supplyCandidate.getMD_Candidate_ID());
		assertThat(purchaseDetail.getC_OrderLinePO_ID()).isEqualTo(40);
		assertThat(purchaseDetail.getM_ReceiptSchedule_ID()).isEqualTo(70);
		assertThat(purchaseDetail.getC_BPartner_Vendor_ID()).isEqualTo(80);
		assertThat(purchaseDetail.getQtyOrdered()).isEqualByComparingTo("10");

		//
		// Check STOCK candidates
		final List<I_MD_Candidate> stockCandidates = DispoTestUtils.filter(CandidateType.STOCK);
		final I_MD_Candidate stockCandidate = stockCandidates.get(0);
		assertThat(stockCandidate.getM_Product_ID()).isEqualTo(eventMaterialDescriptor.getProductId());
		assertThat(stockCandidate.getQty()).isEqualByComparingTo(eventMaterialDescriptor.getQuantity());
		assertThat(stockCandidate.getDateProjected()).isEqualTo(TimeUtil.asTimestamp(eventMaterialDescriptor.getDate()));
		assertThat(stockCandidate.getSeqNo()).isEqualTo(supplyCandidate.getSeqNo());
		assertThat(supplyCandidate.getMD_Candidate_Parent_ID()).isEqualTo(stockCandidate.getMD_Candidate_ID());
	}
}
