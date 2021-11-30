package de.metas.material.dispo.service.event.handler.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.document.engine.DocStatus;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderLineData;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_OrgInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.SHIPMENT_SCHEDULE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.X_AD_OrgInfo.STORECREDITCARDDATA_Speichern;

/*
 * #%L
 * metasfresh-material-dispo
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

@ExtendWith(AdempiereTestWatcher.class)
public class PPOrderCreatedHandlerTests
{
	public static final int rawProduct1Id = 50;

	public static final int rawProduct2Id = 55;

	public static final WarehouseId intermediateWarehouseId = WarehouseId.ofRepoId(20);

	private static final BigDecimal NINE = TEN.subtract(ONE);

	private static final BigDecimal ELEVEN = TEN.add(ONE);

	private AvailableToPromiseRepository availableToPromiseRepository;

	private PPOrderCreatedHandler ppOrderCreatedHandler;
	private DimensionService dimensionService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(20);
		orgInfo.setStoreCreditCardData(STORECREDITCARDDATA_Speichern);
		saveRecord(orgInfo);

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new MDCandidateDimensionFactory());
		dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo);

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryWriteService);

		availableToPromiseRepository = new AvailableToPromiseRepository();

		final SupplyCandidateHandler supplyCandidateHandler = new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService);
		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(ImmutableList.of(
				supplyCandidateHandler,
				new DemandCandiateHandler(
						candidateRepositoryRetrieval,
						candidateRepositoryWriteService,
						postMaterialEventService,
						availableToPromiseRepository,
						stockCandidateService,
						supplyCandidateHandler)));

		ppOrderCreatedHandler = new PPOrderCreatedHandler(
				candidateChangeHandler,
				new MainDataRequestHandler());
	}

	@Test
	public void handle_CreatedEvent_with_groupId()
	{
		final PPOrderCreatedEvent ppOrderCreatedEvent = createPPOrderCreatedEvent(30/* ppOrderId */, MaterialDispoGroupId.ofInt(40));
		ppOrderCreatedHandler.validateEvent(ppOrderCreatedEvent);
		ppOrderCreatedHandler.handleEvent(ppOrderCreatedEvent);

		assert_data_after_ppOrderEvent(ppOrderCreatedEvent);
	}

	private MaterialDispoGroupId assert_data_after_ppOrderEvent(@NonNull final PPOrderCreatedEvent ppOrderEvent)
	{
		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY)).hasSize(1); //
		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(0); // even if the ppOrder has two different input/issue lines, we will generate candidates when the order is completed
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(1); // one stock record per supply, one per demand

		//
		// Check finished goods supply candidates
		final MaterialDispoGroupId supplyDemandGroupId;
		{
			final I_MD_Candidate t2Stock = DispoTestUtils.filter(CandidateType.STOCK, AFTER_NOW).get(0);
			assertThat(t2Stock.getQty()).isEqualByComparingTo(NINE);
			assertThat(t2Stock.getM_Product_ID()).isEqualTo(PRODUCT_ID);
			assertThat(t2Stock.getMD_Candidate_GroupId()).isGreaterThan(0); // stock candidates have their own groupIds too
			assertThat(t2Stock.getMD_Candidate_Parent_ID()).isLessThanOrEqualTo(0);

			final I_MD_Candidate t2Supply = DispoTestUtils.filter(CandidateType.SUPPLY, AFTER_NOW).get(0);
			assertThat(t2Supply.getQty()).isEqualByComparingTo(NINE);
			assertThat(t2Supply.getM_Product_ID()).isEqualTo(PRODUCT_ID);
			assertThat(t2Supply.getMD_Candidate_Parent_ID()).isEqualTo(t2Stock.getMD_Candidate_ID());
			assertThat(t2Supply.getMD_Candidate_GroupId()).isNotEqualTo(t2Stock.getMD_Candidate_GroupId()); // stock candidates' groupIds are different from supply/demand groups' groupIds

			supplyDemandGroupId = MaterialDispoGroupId.ofInt(t2Supply.getMD_Candidate_GroupId());
		}

		//
		// Check bom lines demand candidates
		// => NONE
		// {
		// final I_MD_Candidate t1Product1Demand = DispoTestUtils.filter(CandidateType.DEMAND, NOW, rawProduct1Id).get(0);
		// assertThat(t1Product1Demand.getQty()).isEqualByComparingTo(NINE);
		// assertThat(t1Product1Demand.getM_Product_ID()).isEqualTo(rawProduct1Id);
		// assertThat(t1Product1Demand.getMD_Candidate_GroupId()).isEqualTo(supplyDemandGroupId.toInt());
		// // no parent relationship between production supply and demand because it can be m:n
		// // assertThat(t1Product1Demand.getMD_Candidate_Parent_ID()).isEqualTo(t2Supply.getMD_Candidate_ID());
		//
		// final I_MD_Candidate t1Product1Stock = DispoTestUtils.filter(CandidateType.STOCK, NOW, rawProduct1Id).get(0);
		// assertThat(t1Product1Stock.getQty()).isEqualByComparingTo(NINE.negate());
		// assertThat(t1Product1Stock.getM_Product_ID()).isEqualTo(rawProduct1Id);
		// assertThat(t1Product1Stock.getMD_Candidate_GroupId()).isGreaterThan(0); // stock candidates have their own groupIds too
		// assertThat(t1Product1Stock.getMD_Candidate_GroupId()).isNotEqualTo(supplyDemandGroupId); // stock candidates' groupIds are different from supply/demand groups' groupIds
		// assertThat(t1Product1Stock.getMD_Candidate_GroupId()).isNotEqualTo(t2Stock.getMD_Candidate_GroupId()); // stock candidates' groupIds are different if they are about different products or warehouses
		// assertThat(t1Product1Stock.getMD_Candidate_Parent_ID()).isEqualTo(t1Product1Demand.getMD_Candidate_ID());
		// }
		//
		// {
		// final I_MD_Candidate t1Product2Demand = DispoTestUtils.filter(CandidateType.DEMAND, NOW, rawProduct2Id).get(0);
		// assertThat(t1Product2Demand.getQty()).isEqualByComparingTo(TEN);
		// assertThat(t1Product2Demand.getM_Product_ID()).isEqualTo(rawProduct2Id);
		// assertThat(t1Product2Demand.getMD_Candidate_GroupId()).isEqualTo(supplyDemandGroupId.toInt());
		// // no parent relationship between production supply and demand because it can be m:n
		// // assertThat(t1Product2Demand.getMD_Candidate_Parent_ID()).isEqualTo(t2Supply.getMD_Candidate_ID());
		//
		// final I_MD_Candidate t1Product2Stock = DispoTestUtils.filter(CandidateType.STOCK, NOW, rawProduct2Id).get(0);
		// assertThat(t1Product2Stock.getQty()).isEqualByComparingTo(TEN.negate());
		// assertThat(t1Product2Stock.getM_Product_ID()).isEqualTo(rawProduct2Id);
		// assertThat(t1Product2Stock.getMD_Candidate_GroupId()).isGreaterThan(0); // stock candidates have their own groupIds too
		// assertThat(t1Product2Stock.getMD_Candidate_Parent_ID()).isEqualTo(t1Product2Demand.getMD_Candidate_ID());
		// assertThat(t1Product2Stock.getMD_Candidate_GroupId()).isNotEqualTo(t1Product1Stock.getMD_Candidate_GroupId()); // stock candidates' groupIds are different if they are about different products or warehouses
		// }

		final int ppOrderId = ppOrderEvent.getPpOrder().getPpOrderId();
		assertThat(DispoTestUtils.filterExclStock()).allSatisfy(r -> assertCandidateRecordHasPpOrderId(r, ppOrderId));

		//
		// verify the production details' isPickDirectlyIfFeasible flag
		{
			final List<I_MD_Candidate_Prod_Detail> allProductionDetails = Services.get(IQueryBL.class)
					.createQueryBuilder(I_MD_Candidate_Prod_Detail.class)
					.create()
					.list();
			assertThat(allProductionDetails).as("each (non-stock) candidate shall have one production detail").hasSize(1);

			assertThat(allProductionDetails)
					.allSatisfy(d -> assertThat(d.isPickDirectlyIfFeasible()).isFalse());
		}

		//
		// Verify the demand details
		{
			final List<I_MD_Candidate_Demand_Detail> allDemandDetails = Services.get(IQueryBL.class)
					.createQueryBuilder(I_MD_Candidate_Demand_Detail.class)
					.create()
					.list();

			assertThat(allDemandDetails).as("each (non-stock) candidate shall have one demand detail").hasSize(0);

			// note: SHIPMENT_SCHEDULE_ID is the constant used when the event's demand detail was created
			assertThat(allDemandDetails)
					.allSatisfy(d -> assertThat(d.getM_ShipmentSchedule_ID()).isEqualTo(SHIPMENT_SCHEDULE_ID));
		}

		return supplyDemandGroupId;
	}

	@Test
	public void handle_CreatedEvent_without_groupId()
	{
		final PPOrderCreatedEvent ppOrderCreatedEvent = createPPOrderCreatedEvent(30, (MaterialDispoGroupId)null);
		ppOrderCreatedHandler.validateEvent(ppOrderCreatedEvent);
		ppOrderCreatedHandler.handleEvent(ppOrderCreatedEvent);

		// the system did not map the ppOrderCreatedEvent to the existing candidates because it did not have their groupId
		// without the groupId we can't assume that a give event maps to any existing candidate
		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(0);
	}

	private PPOrderCreatedEvent createPPOrderCreatedEvent(final int ppOrderId, final MaterialDispoGroupId groupId)
	{
		final PPOrder ppOrder = createPpOrderWithPpOrderId(ppOrderId, groupId);

		final PPOrderCreatedEvent event = PPOrderCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.ppOrder(ppOrder)
				.build();

		assertThat(ppOrder.getPpOrderId()).isEqualTo(ppOrderId);
		return event;
	}

	private PPOrder createPpOrderWithPpOrderId(
			final int ppOrderId,
			final MaterialDispoGroupId groupId)
	{
		final ProductDescriptor rawProductDescriptor1 = ProductDescriptor.completeForProductIdAndEmptyAttribute(rawProduct1Id);
		final ProductDescriptor rawProductDescriptor2 = ProductDescriptor.completeForProductIdAndEmptyAttribute(rawProduct2Id);

		return PPOrder.builder()
				.ppOrderId(ppOrderId)
				.docStatus(DocStatus.InProgress)
				.ppOrderData(PPOrderData.builder()
									 .clientAndOrgId(CLIENT_AND_ORG_ID)
									 .datePromised(AFTER_NOW)
									 .dateStartSchedule(NOW)
									 .productDescriptor(createProductDescriptor())
									 .qtyRequired(TEN)
									 .qtyDelivered(ONE)
									 .warehouseId(intermediateWarehouseId)
									 .bpartnerId(BPARTNER_ID)
									 .materialDispoGroupId(groupId)
									 .plantId(ResourceId.ofRepoId(120))
									 .productPlanningId(140)
									 .build())
				.line(PPOrderLine.builder()
							  .ppOrderLineId(ppOrderId * 5)
							  .ppOrderLineData(PPOrderLineData.builder()
													   .description("descr1")
													   .productDescriptor(rawProductDescriptor1)
													   .issueOrReceiveDate(NOW)
													   .qtyRequired(TEN)
													   .qtyDelivered(ONE)
													   .productBomLineId(1020)
													   .receipt(false)
													   .build())
							  .build())
				.line(PPOrderLine.builder()
							  .ppOrderLineId(ppOrderId * 6)
							  .ppOrderLineData(PPOrderLineData.builder()
													   .description("descr2")
													   .productDescriptor(rawProductDescriptor2)
													   .issueOrReceiveDate(NOW)
													   .qtyRequired(ELEVEN)
													   .qtyDelivered(ONE)
													   .productBomLineId(1030)
													   .receipt(false)
													   .build())
							  .build())
				.build();
	}

	private void assertCandidateRecordHasPpOrderId(
			@NonNull final I_MD_Candidate candidate,
			final int ppOrderId)
	{
		final I_MD_Candidate_Prod_Detail productionDetailRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate_Prod_Detail.class)
				.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMN_MD_Candidate_ID, candidate.getMD_Candidate_ID())
				.create()
				.firstOnly(I_MD_Candidate_Prod_Detail.class);

		assertThat(productionDetailRecord).isNotNull();
		assertThat(productionDetailRecord.getPP_Order_ID()).isEqualTo(ppOrderId);
	}
}