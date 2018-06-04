package de.metas.material.dispo.service.event.handler.pporder;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.SHIPMENT_SCHEDULE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.material.event.EventTestHelper.createSupplyRequiredDescriptor;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandiateHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.AbstractPPOrderEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedEvent;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.event.pporder.PPOrderLine;
import lombok.NonNull;
import mockit.Mocked;

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

public class PPOrderAdvisedOrCreatedHandlerTests
{
	@Rule
	public final AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	public static final int rawProduct1Id = 50;

	public static final int rawProduct2Id = 55;

	public static final int intermediateWarehouseId = 20;

	private static final BigDecimal NINE = TEN.subtract(ONE);

	private static final BigDecimal ELEVEN = TEN.add(ONE);

	@Mocked
	private PostMaterialEventService postMaterialEventService;

	private PPOrderAdvisedHandler ppOrderAdvisedHandler;

	private AvailableToPromiseRepository stockRepository;

	private PPOrderCreatedHandler ppOrderCreatedHandler;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();
		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService();
		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryWriteService);

		stockRepository = new AvailableToPromiseRepository();

		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(ImmutableList.of(
				new SupplyCandiateHandler(candidateRepositoryRetrieval, candidateRepositoryWriteService, stockCandidateService),
				new DemandCandiateHandler(
						candidateRepositoryRetrieval,
						candidateRepositoryWriteService,
						postMaterialEventService,
						stockRepository,
						stockCandidateService)));

		final RequestMaterialOrderService requestMaterialOrderService = new RequestMaterialOrderService(
				candidateRepositoryRetrieval,
				postMaterialEventService);

		ppOrderAdvisedHandler = new PPOrderAdvisedHandler(
				candidateChangeHandler,
				candidateRepositoryRetrieval,
				requestMaterialOrderService);

		ppOrderCreatedHandler = new PPOrderCreatedHandler(
				candidateChangeHandler,
				candidateRepositoryRetrieval);
	}

	@Test
	public void handlePPOrderAdvisedEvent()
	{
		final PPOrderAdvisedEvent ppOrderAdvisedEvent = createPPOrderAdvisedEvent(true);
		ppOrderAdvisedHandler.validateEvent(ppOrderAdvisedEvent);
		ppOrderAdvisedHandler.handleEvent(ppOrderAdvisedEvent);

		assert_data_after_ppOrderEvent(ppOrderAdvisedEvent);
	}

	@Test
	public void handle_PPOrder_AdvisedEvent_then_CreatedEvent_with_groupId()
	{
		final PPOrderAdvisedEvent ppOrderAdvisedEvent = createPPOrderAdvisedEvent(true);
		ppOrderAdvisedHandler.validateEvent(ppOrderAdvisedEvent);
		ppOrderAdvisedHandler.handleEvent(ppOrderAdvisedEvent);

		final int createdGroupId = assert_data_after_ppOrderEvent(ppOrderAdvisedEvent);

		final PPOrderCreatedEvent ppOrderCreatedEvent = createPPOrderCreatedEvent(30, createdGroupId);
		ppOrderCreatedHandler.validateEvent(ppOrderCreatedEvent);
		ppOrderCreatedHandler.handleEvent(ppOrderCreatedEvent);

		assert_data_after_ppOrderEvent(ppOrderCreatedEvent);
	}

	private int assert_data_after_ppOrderEvent(
			@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY)).hasSize(1); //
		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(2); // the ppOrder has two different input/issue lines
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(3); // one stock record per supply, one per demand

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

		final int supplyDemandGroupId = t2Supply.getMD_Candidate_GroupId();
		assertThat(supplyDemandGroupId).isGreaterThan(0);

		final I_MD_Candidate t1Product1Demand = DispoTestUtils.filter(CandidateType.DEMAND, NOW, rawProduct1Id).get(0);
		assertThat(t1Product1Demand.getQty()).isEqualByComparingTo(NINE);
		assertThat(t1Product1Demand.getM_Product_ID()).isEqualTo(rawProduct1Id);
		assertThat(t1Product1Demand.getMD_Candidate_GroupId()).isEqualTo(supplyDemandGroupId);
		// no parent relationship between production supply and demand because it can be m:n
		// assertThat(t1Product1Demand.getMD_Candidate_Parent_ID()).isEqualTo(t2Supply.getMD_Candidate_ID());

		final I_MD_Candidate t1Product1Stock = DispoTestUtils.filter(CandidateType.STOCK, NOW, rawProduct1Id).get(0);
		assertThat(t1Product1Stock.getQty()).isEqualByComparingTo(NINE.negate());
		assertThat(t1Product1Stock.getM_Product_ID()).isEqualTo(rawProduct1Id);
		assertThat(t1Product1Stock.getMD_Candidate_GroupId()).isGreaterThan(0);  // stock candidates have their own groupIds too
		assertThat(t1Product1Stock.getMD_Candidate_GroupId()).isNotEqualTo(supplyDemandGroupId);  // stock candidates' groupIds are different from supply/demand groups' groupIds
		assertThat(t1Product1Stock.getMD_Candidate_GroupId()).isNotEqualTo(t2Stock.getMD_Candidate_GroupId());  // stock candidates' groupIds are different if they are about different products or warehouses

		assertThat(t1Product1Stock.getMD_Candidate_Parent_ID()).isEqualTo(t1Product1Demand.getMD_Candidate_ID());

		final I_MD_Candidate t1Product2Demand = DispoTestUtils.filter(CandidateType.DEMAND, NOW, rawProduct2Id).get(0);
		assertThat(t1Product2Demand.getQty()).isEqualByComparingTo(TEN);
		assertThat(t1Product2Demand.getM_Product_ID()).isEqualTo(rawProduct2Id);
		assertThat(t1Product2Demand.getMD_Candidate_GroupId()).isEqualTo(supplyDemandGroupId);
		// no parent relationship between production supply and demand because it can be m:n
		// assertThat(t1Product2Demand.getMD_Candidate_Parent_ID()).isEqualTo(t2Supply.getMD_Candidate_ID());

		final I_MD_Candidate t1Product2Stock = DispoTestUtils.filter(CandidateType.STOCK, NOW, rawProduct2Id).get(0);
		assertThat(t1Product2Stock.getQty()).isEqualByComparingTo(TEN.negate());
		assertThat(t1Product2Stock.getM_Product_ID()).isEqualTo(rawProduct2Id);
		assertThat(t1Product2Stock.getMD_Candidate_GroupId()).isGreaterThan(0); // stock candidates have their own groupIds too
		assertThat(t1Product2Stock.getMD_Candidate_Parent_ID()).isEqualTo(t1Product2Demand.getMD_Candidate_ID());
		assertThat(t1Product2Stock.getMD_Candidate_GroupId()).isNotEqualTo(t1Product1Stock.getMD_Candidate_GroupId());  // stock candidates' groupIds are different if they are about different products or warehouses

		final int ppOrderId = ppOrderEvent.getPpOrder().getPpOrderId();
		assertThat(DispoTestUtils.filterExclStock()).allSatisfy(r -> assertCandidateRecordHasPpOorderId(r, ppOrderId));
		assertThat(DispoTestUtils.retrieveAllRecords()).allSatisfy(r -> assertThat(r.getC_BPartner_ID()).isEqualTo(BPARTNER_ID));

		// verify the production details' isPickDirectlyIfFeasible flag
		final List<I_MD_Candidate_Prod_Detail> allProductionDetails = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate_Prod_Detail.class)
				.create().list();
		assertThat(allProductionDetails).as("each (non-stock) candidate shall have one production detail").hasSize(3);

		assertThat(allProductionDetails)
				.allSatisfy(d -> assertThat(d.isPickDirectlyIfFeasible()).isTrue());

		// verify the demand details
		final List<I_MD_Candidate_Demand_Detail> allDemandDetails = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate_Demand_Detail.class)
				.create().list();

		assertThat(allDemandDetails).as("each (non-stock) candidate shall have one demand detail").hasSize(3);

		// note: SHIPMENT_SCHEDULE_ID is the constant used when the event's demand detail was created
		assertThat(allDemandDetails)
				.allSatisfy(d -> assertThat(d.getM_ShipmentSchedule_ID()).isEqualTo(SHIPMENT_SCHEDULE_ID));

		return supplyDemandGroupId;
	}

	@Test
	public void handle_PPOrder_AdvisedEvent_then_CreatedEvent_without_groupId()
	{
		final PPOrderAdvisedEvent ppOrderAdvisedEvent = createPPOrderAdvisedEvent(true);
		ppOrderAdvisedHandler.validateEvent(ppOrderAdvisedEvent);
		ppOrderAdvisedHandler.handleEvent(ppOrderAdvisedEvent);

		assert_data_after_ppOrderEvent(ppOrderAdvisedEvent); // guard

		final PPOrderCreatedEvent ppOrderCreatedEvent = createPPOrderCreatedEvent(30, 0);
		ppOrderCreatedHandler.validateEvent(ppOrderCreatedEvent);
		ppOrderCreatedHandler.handleEvent(ppOrderCreatedEvent);

		// the system did not map the ppOrderCreatedEvent to the existing candidates because it did not have their groupId
		// without the groupId we can't assume that a give event maps to any existing candidate
		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY)).hasSize(2);
		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(4);
	}

	private PPOrderAdvisedEvent createPPOrderAdvisedEvent(final boolean directlyPickSupply)
	{
		final PPOrder ppOrder = createPpOrderWithPpOrderId(0, 0);

		final PPOrderAdvisedEvent event = PPOrderAdvisedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.directlyPickSupply(directlyPickSupply)
				.supplyRequiredDescriptor(createSupplyRequiredDescriptor())
				.ppOrder(ppOrder)
				.build();

		return event;
	}

	private PPOrderCreatedEvent createPPOrderCreatedEvent(final int ppOrderId, final int groupId)
	{
		final PPOrder ppOrder = createPpOrderWithPpOrderId(ppOrderId, groupId);

		final PPOrderCreatedEvent event = PPOrderCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.ppOrder(ppOrder)
				.build();

		assertThat(ppOrder.getPpOrderId()).isEqualTo(ppOrderId);
		return event;
	}

	private PPOrder createPpOrderWithPpOrderId(
			final int ppOrderId,
			final int groupId)
	{
		final ProductDescriptor rawProductDescriptor1 = ProductDescriptor.completeForProductIdAndEmptyAttribute(rawProduct1Id);
		final ProductDescriptor rawProductDescriptor2 = ProductDescriptor.completeForProductIdAndEmptyAttribute(rawProduct2Id);

		final PPOrder ppOrder = PPOrder.builder()
				.ppOrderId(ppOrderId)
				.orgId(ORG_ID)
				.datePromised(AFTER_NOW)
				.dateStartSchedule(NOW)
				.productDescriptor(createProductDescriptor())
				.qtyRequired(TEN)
				.qtyDelivered(ONE)
				.warehouseId(intermediateWarehouseId)
				.bPartnerId(BPARTNER_ID)
				.plantId(120)
				.productPlanningId(140)
				.docStatus("IP")
				.materialDispoGroupId(groupId)
				.line(PPOrderLine.builder()
						.ppOrderLineId(ppOrderId * 5)
						.description("descr1")
						.productDescriptor(rawProductDescriptor1)
						.issueOrReceiveDate(NOW)
						.qtyRequired(TEN)
						.qtyDelivered(ONE)
						.productBomLineId(1020)
						.receipt(false)
						.build())
				.line(PPOrderLine.builder()
						.ppOrderLineId(ppOrderId * 6)
						.description("descr2")
						.productDescriptor(rawProductDescriptor2)
						.issueOrReceiveDate(NOW)
						.qtyRequired(ELEVEN)
						.qtyDelivered(ONE)
						.productBomLineId(1030)
						.receipt(false)
						.build())
				.build();
		return ppOrder;
	}

	private void assertCandidateRecordHasPpOorderId(
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
