package de.metas.material.dispo.service.event.handler;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

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
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.StockRepository;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandiateHandler;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedOrCreatedEvent;
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

	private final BigDecimal eleven = BigDecimal.TEN.add(BigDecimal.ONE);

	@Mocked
	private MaterialEventService materialEventService;

	private PPOrderAdvisedOrCreatedHandler ppOrderAdvisedOrCreatedHandler;

	private StockRepository stockRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval();
		final CandidateRepositoryWriteService candidateRepositoryCommands = new CandidateRepositoryWriteService();
		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepository,
				candidateRepositoryCommands);

		stockRepository = new StockRepository();

		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(ImmutableList.of(
				new SupplyCandiateHandler(candidateRepository, candidateRepositoryCommands, stockCandidateService),
				new DemandCandiateHandler(
						candidateRepository,
						candidateRepositoryCommands,
						materialEventService,
						stockRepository,
						stockCandidateService)));

		final RequestMaterialOrderService candidateService = new RequestMaterialOrderService(
				candidateRepository,
				MaterialEventService.createLocalServiceThatIsReadyToUse());

		ppOrderAdvisedOrCreatedHandler = new PPOrderAdvisedOrCreatedHandler(candidateChangeHandler, candidateService);
	}

	@Test
	public void handlePPOrderAdvisedOrCreatedEvent()
	{
		perform_testPPOrderAdvisedOrCreatedEvent(createPPOrderEventWithPpOrderId(0));
	}

	@Test
	public void handlePPOrderAdvisedOrCreatedEvent_invoke_twice()
	{
		perform_testPPOrderAdvisedOrCreatedEvent(createPPOrderEventWithPpOrderId(0));
		perform_testPPOrderAdvisedOrCreatedEvent(createPPOrderEventWithPpOrderId(30));
	}

	private void perform_testPPOrderAdvisedOrCreatedEvent(final PPOrderAdvisedOrCreatedEvent ppOrderAdvisedOrCreatedEvent)
	{
		ppOrderAdvisedOrCreatedHandler.handlePPOrderAdvisedOrCreatedEvent(ppOrderAdvisedOrCreatedEvent);

		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY)).hasSize(1); //
		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(2); // we have two different inputs
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(3); // one stock record per supply, one per demand

		final I_MD_Candidate t2Stock = DispoTestUtils.filter(CandidateType.STOCK, AFTER_NOW).get(0);
		assertThat(t2Stock.getQty()).isEqualByComparingTo(BigDecimal.ONE);
		assertThat(t2Stock.getM_Product_ID()).isEqualTo(PRODUCT_ID);
		assertThat(t2Stock.getMD_Candidate_GroupId()).isGreaterThan(0); // stock candidates have their own groupIds too
		assertThat(t2Stock.getMD_Candidate_Parent_ID()).isLessThanOrEqualTo(0);

		final I_MD_Candidate t2Supply = DispoTestUtils.filter(CandidateType.SUPPLY, AFTER_NOW).get(0);
		assertThat(t2Supply.getQty()).isEqualByComparingTo(BigDecimal.ONE);
		assertThat(t2Supply.getM_Product_ID()).isEqualTo(PRODUCT_ID);
		assertThat(t2Supply.getMD_Candidate_Parent_ID()).isEqualTo(t2Stock.getMD_Candidate_ID());
		assertThat(t2Supply.getMD_Candidate_GroupId()).isNotEqualTo(t2Stock.getMD_Candidate_GroupId()); // stock candidates' groupIds are different from supply/demand groups' groupIds

		final int supplyDemandGroupId = t2Supply.getMD_Candidate_GroupId();
		assertThat(supplyDemandGroupId).isGreaterThan(0);

		final I_MD_Candidate t1Product1Demand = DispoTestUtils.filter(CandidateType.DEMAND, NOW, rawProduct1Id).get(0);
		assertThat(t1Product1Demand.getQty()).isEqualByComparingTo(BigDecimal.TEN);
		assertThat(t1Product1Demand.getM_Product_ID()).isEqualTo(rawProduct1Id);
		assertThat(t1Product1Demand.getMD_Candidate_GroupId()).isEqualTo(supplyDemandGroupId);
		// no parent relationship between production supply and demand because it can be m:n
		// assertThat(t1Product1Demand.getMD_Candidate_Parent_ID()).isEqualTo(t2Supply.getMD_Candidate_ID());

		final I_MD_Candidate t1Product1Stock = DispoTestUtils.filter(CandidateType.STOCK, NOW, rawProduct1Id).get(0);
		assertThat(t1Product1Stock.getQty()).isEqualByComparingTo(BigDecimal.TEN.negate());
		assertThat(t1Product1Stock.getM_Product_ID()).isEqualTo(rawProduct1Id);
		assertThat(t1Product1Stock.getMD_Candidate_GroupId()).isGreaterThan(0);  // stock candidates have their own groupIds too
		assertThat(t1Product1Stock.getMD_Candidate_GroupId()).isNotEqualTo(supplyDemandGroupId);  // stock candidates' groupIds are different from supply/demand groups' groupIds
		assertThat(t1Product1Stock.getMD_Candidate_GroupId()).isNotEqualTo(t2Stock.getMD_Candidate_GroupId());  // stock candidates' groupIds are different if they are about different products or warehouses

		assertThat(t1Product1Stock.getMD_Candidate_Parent_ID()).isEqualTo(t1Product1Demand.getMD_Candidate_ID());

		final I_MD_Candidate t1Product2Demand = DispoTestUtils.filter(CandidateType.DEMAND, NOW, rawProduct2Id).get(0);
		assertThat(t1Product2Demand.getQty()).isEqualByComparingTo(eleven);
		assertThat(t1Product2Demand.getM_Product_ID()).isEqualTo(rawProduct2Id);
		assertThat(t1Product2Demand.getMD_Candidate_GroupId()).isEqualTo(supplyDemandGroupId);
		// no parent relationship between production supply and demand because it can be m:n
		// assertThat(t1Product2Demand.getMD_Candidate_Parent_ID()).isEqualTo(t2Supply.getMD_Candidate_ID());

		final I_MD_Candidate t1Product2Stock = DispoTestUtils.filter(CandidateType.STOCK, NOW, rawProduct2Id).get(0);
		assertThat(t1Product2Stock.getQty()).isEqualByComparingTo(eleven.negate());
		assertThat(t1Product2Stock.getM_Product_ID()).isEqualTo(rawProduct2Id);
		assertThat(t1Product2Stock.getMD_Candidate_GroupId()).isGreaterThan(0); // stock candidates have their own groupIds too
		assertThat(t1Product2Stock.getMD_Candidate_Parent_ID()).isEqualTo(t1Product2Demand.getMD_Candidate_ID());
		assertThat(t1Product2Stock.getMD_Candidate_GroupId()).isNotEqualTo(t1Product1Stock.getMD_Candidate_GroupId());  // stock candidates' groupIds are different if they are about different products or warehouses

		final int ppOrderId = ppOrderAdvisedOrCreatedEvent.getPpOrder().getPpOrderId();
		assertThat(DispoTestUtils.filterExclStock()).allSatisfy(r -> assertCandidateRecordHasPpOorderId(r, ppOrderId));
		assertThat(DispoTestUtils.retrieveAllRecords()).allSatisfy(r -> assertThat(r.getC_BPartner_ID()).isEqualTo(BPARTNER_ID));
	}

	private PPOrderAdvisedOrCreatedEvent createPPOrderEventWithPpOrderId(final int ppOrderId)
	{
		final PPOrder ppOrder = createPpOrderWithPpOrderId(ppOrderId);

		final PPOrderAdvisedOrCreatedEvent productionAdvisedEvent = PPOrderAdvisedOrCreatedEvent.builder()
				.eventDescriptor(new EventDescriptor(CLIENT_ID, ORG_ID))
				.supplyRequiredDescriptor(null)
				.ppOrder(ppOrder)
				.build();

		assertThat(productionAdvisedEvent.getSupplyRequiredDescriptor()).isNull();
		assertThat(ppOrder.getPpOrderId()).isEqualTo(ppOrderId);

		return productionAdvisedEvent;
	}

	private PPOrder createPpOrderWithPpOrderId(final int ppOrderId)
	{
		final ProductDescriptor rawProductDescriptor1 = ProductDescriptor.completeForProductIdAndEmptyAttribute(rawProduct1Id);
		final ProductDescriptor rawProductDescriptor2 = ProductDescriptor.completeForProductIdAndEmptyAttribute(rawProduct2Id);

		final PPOrder ppOrder = PPOrder.builder()
				.ppOrderId(ppOrderId)
				.orgId(ORG_ID)
				.datePromised(AFTER_NOW)
				.dateStartSchedule(NOW)
				.productDescriptor(createProductDescriptor())
				.quantity(BigDecimal.ONE)
				.warehouseId(intermediateWarehouseId)
				.bPartnerId(BPARTNER_ID)
				.plantId(120)
				.uomId(130)
				.productPlanningId(140)
				.line(PPOrderLine.builder()
						.description("descr1")
						.productDescriptor(rawProductDescriptor1)
						.qtyRequired(BigDecimal.TEN)
						.productBomLineId(1020)
						.receipt(false)
						.build())
				.line(PPOrderLine.builder()
						.description("descr2")
						.productDescriptor(rawProductDescriptor2)
						.qtyRequired(eleven)
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
