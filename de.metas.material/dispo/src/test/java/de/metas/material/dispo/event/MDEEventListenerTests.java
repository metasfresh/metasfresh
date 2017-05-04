package de.metas.material.dispo.event;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateChangeHandler;
import de.metas.material.dispo.CandidateFactory;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidateService;
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.DistributionPlanEvent;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ProductionPlanEvent;
import de.metas.material.event.ShipmentScheduleEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
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

/**
 * This is kind of a bunch of "module tests".
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MDEEventListenerTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	public static final Date t0 = SystemTime.asDate();

	private static final Date t1 = TimeUtil.addMinutes(t0, 10);

	private static final Date t2 = TimeUtil.addMinutes(t0, 20);

	public static final int fromWarehouseId = 10;
	public static final int intermediateWarehouseId = 20;
	public static final int toWarehouseId = 30;

	public static final int productId = 40;

	public static final int rawProduct1Id = 50;

	public static final int rawProduct2Id = 55;

	private MDEventListener mdEventListener;

	@Mocked
	private MaterialEventService materialEventService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepository candidateRepository = new CandidateRepository();
		final SupplyProposalEvaluator supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepository);

		final CandidateChangeHandler candidateChangeHandler = new CandidateChangeHandler(candidateRepository, new CandidateFactory(candidateRepository), materialEventService);

		final CandidateService candidateService = new CandidateService(candidateRepository, new MaterialEventService(de.metas.event.Type.LOCAL));

		mdEventListener = new MDEventListener(
				candidateChangeHandler,
				new DistributionPlanEventHandler(candidateRepository, candidateChangeHandler, supplyProposalEvaluator),
				new ProdcutionPlanEventHandler(candidateChangeHandler, candidateService)
				);
	}

	/**
	 * This test is more for myself, to figure out how the system works :-$
	 */
	@Test
	public void testShipmentScheduleEvent()
	{
		final ShipmentScheduleEvent event = ShipmentScheduleEvent.builder()
				.eventDescr(new EventDescr())
				.materialDescr(MaterialDescriptor.builder()
						.date(t1)
						.orgId(20)
						.productId(productId)
						.qty(BigDecimal.TEN)
						.warehouseId(toWarehouseId)
						.build())
				.reference(TableRecordReference.of("someTable", 4))
				.build();
		mdEventListener.onEvent(event);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords.size(), is(2));

		assertThat(DispoTestUtils.filter(Type.DEMAND).size(), is(1));
		assertThat(DispoTestUtils.filter(Type.STOCK).size(), is(1));

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(Type.DEMAND).get(0);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);

		assertThat(demandRecord.getSeqNo(), is(stockRecord.getSeqNo() - 1)); // the demand record shall be displayed first
		assertThat(stockRecord.getMD_Candidate_Parent_ID(), is(demandRecord.getMD_Candidate_ID()));

		assertThat(demandRecord.getQty(), comparesEqualTo(BigDecimal.TEN));
		assertThat(stockRecord.getQty(), comparesEqualTo(BigDecimal.TEN.negate())); // the stock is unbalanced, because there is no existing stock and no supply
	}





	@Test
	public void testProductionPlanEvent()
	{
		final TableRecordReference reference = TableRecordReference.of("someTable", 4);

		final BigDecimal eleven = BigDecimal.TEN.add(BigDecimal.ONE);

		final ProductionPlanEvent productionPlanEvent = ProductionPlanEvent.builder()
				.eventDescr(new EventDescr())
				.ppOrder(PPOrder.builder()
						.orgId(20)
						.datePromised(t2)
						.dateStartSchedule(t1)
						.productId(productId)
						.quantity(BigDecimal.ONE)
						.warehouseId(intermediateWarehouseId)
						.plantId(120)
						.uomId(130)
						.productPlanningId(140)
						.line(PPOrderLine.builder()
								.description("descr1")
								.productId(rawProduct1Id)
								.qtyRequired(BigDecimal.TEN)
								.productBomLineId(1020)
								.receipt(false)
								.build())
						.line(PPOrderLine.builder()
								.description("descr2")
								.productId(rawProduct2Id)
								.qtyRequired(eleven)
								.productBomLineId(1030)
								.receipt(false)
								.build())
						.build())
				.reference(reference)
				.build();

		mdEventListener.onEvent(productionPlanEvent);

		assertThat(DispoTestUtils.filter(Type.SUPPLY).size(), is(1)); //
		assertThat(DispoTestUtils.filter(Type.DEMAND).size(), is(2)); // we have two different inputs
		assertThat(DispoTestUtils.filter(Type.STOCK).size(), is(3)); // one stock record per supply, one per demand

		final I_MD_Candidate t2Stock = DispoTestUtils.filter(Type.STOCK, t2).get(0);
		assertThat(t2Stock.getQty(), comparesEqualTo(BigDecimal.ONE));
		assertThat(t2Stock.getM_Product_ID(), is(productId));
		assertThat(t2Stock.getMD_Candidate_GroupId(), greaterThan(0)); // stock candidates have their own groupIds too
		assertThat(t2Stock.getMD_Candidate_Parent_ID(), lessThanOrEqualTo(0));

		final I_MD_Candidate t2Supply = DispoTestUtils.filter(Type.SUPPLY, t2).get(0);
		assertThat(t2Supply.getQty(), comparesEqualTo(BigDecimal.ONE));
		assertThat(t2Supply.getM_Product_ID(), is(productId));
		assertThat(t2Supply.getMD_Candidate_Parent_ID(), is(t2Stock.getMD_Candidate_ID()));
		assertThat(t2Supply.getMD_Candidate_GroupId(), not(is(t2Stock.getMD_Candidate_GroupId()))); // stock candidates' groupIds are different from supply/demand groups' groupIds

		final int supplyDemandGroupId = t2Supply.getMD_Candidate_GroupId();
		assertThat(supplyDemandGroupId, greaterThan(0));

		final I_MD_Candidate t1Product1Demand = DispoTestUtils.filter(Type.DEMAND, t1, rawProduct1Id).get(0);
		assertThat(t1Product1Demand.getQty(), comparesEqualTo(BigDecimal.TEN));
		assertThat(t1Product1Demand.getM_Product_ID(), is(rawProduct1Id));
		assertThat(t1Product1Demand.getMD_Candidate_GroupId(), is(supplyDemandGroupId));
		// no parent relationship between production supply and demand because it can be m:n
		// assertThat(t1Product1Demand.getMD_Candidate_Parent_ID(), is(t2Supply.getMD_Candidate_ID()));

		final I_MD_Candidate t1Product1Stock = DispoTestUtils.filter(Type.STOCK, t1, rawProduct1Id).get(0);
		assertThat(t1Product1Stock.getQty(), comparesEqualTo(BigDecimal.TEN.negate()));
		assertThat(t1Product1Stock.getM_Product_ID(), is(rawProduct1Id));
		assertThat(t1Product1Stock.getMD_Candidate_GroupId(), greaterThan(0));  // stock candidates have their own groupIds too
		assertThat(t1Product1Stock.getMD_Candidate_GroupId(), not(is(supplyDemandGroupId)));  // stock candidates' groupIds are different from supply/demand groups' groupIds
		assertThat(t1Product1Stock.getMD_Candidate_GroupId(), not(is(t2Stock.getMD_Candidate_GroupId())));  // stock candidates' groupIds are different if they are about different products or warehouses

		assertThat(t1Product1Stock.getMD_Candidate_Parent_ID(), is(t1Product1Demand.getMD_Candidate_ID()));

		final I_MD_Candidate t1Product2Demand = DispoTestUtils.filter(Type.DEMAND, t1, rawProduct2Id).get(0);
		assertThat(t1Product2Demand.getQty(), comparesEqualTo(eleven));
		assertThat(t1Product2Demand.getM_Product_ID(), is(rawProduct2Id));
		assertThat(t1Product2Demand.getMD_Candidate_GroupId(), is(supplyDemandGroupId));
		// no parent relationship between production supply and demand because it can be m:n
		// assertThat(t1Product2Demand.getMD_Candidate_Parent_ID(), is(t2Supply.getMD_Candidate_ID()));

		final I_MD_Candidate t1Product2Stock = DispoTestUtils.filter(Type.STOCK, t1, rawProduct2Id).get(0);
		assertThat(t1Product2Stock.getQty(), comparesEqualTo(eleven.negate()));
		assertThat(t1Product2Stock.getM_Product_ID(), is(rawProduct2Id));
		assertThat(t1Product2Stock.getMD_Candidate_GroupId(), greaterThan(0)); // stock candidates have their own groupIds too
		assertThat(t1Product2Stock.getMD_Candidate_Parent_ID(), is(t1Product2Demand.getMD_Candidate_ID()));
		assertThat(t1Product2Stock.getMD_Candidate_GroupId(), not(is(t1Product1Stock.getMD_Candidate_GroupId())));  // stock candidates' groupIds are different if they are about different products or warehouses
	}

	/**
	 * This test is more for myself, to figure out how the system works :-$
	 */
	@Test
	public void testShipmentScheduleEven_then_DistributionPlanevent()
	{
		testShipmentScheduleEvent();

		// create a DistributionPlanEvent event which matches the shipmentscheduleEvent that we processed in testShipmentScheduleEvent()
		final TableRecordReference reference = TableRecordReference.of("someTable", 4);
		final DistributionPlanEvent event = DistributionPlanEvent.builder()
				.eventDescr(new EventDescr())
				.distributionStart(t1)
				.fromWarehouseId(fromWarehouseId)
				.materialDescr(MaterialDescriptor.builder()
						.date(t1)
						.orgId(20)
						.productId(productId)
						.qty(BigDecimal.TEN)
						.warehouseId(toWarehouseId)
						.build())
				.reference(reference)
				.build();
		mdEventListener.onEvent(event);

		assertThat(DispoTestUtils.retrieveAllRecords().size(), is(5)); // one for the shipment-schedule demand, two for the distribution demand + supply and 2 stocks (one of them shared between shipment-demand and distr-supply)
		final I_MD_Candidate toWarehouseDemand = DispoTestUtils.filter(Type.DEMAND, toWarehouseId).get(0);
		final I_MD_Candidate toWarehouseSharedStock = DispoTestUtils.filter(Type.STOCK, toWarehouseId).get(0);
		final I_MD_Candidate toWarehouseSupply = DispoTestUtils.filter(Type.SUPPLY, toWarehouseId).get(0);

		assertThat(toWarehouseDemand.getSeqNo(), is(toWarehouseSharedStock.getSeqNo() - 1));
		assertThat(toWarehouseSharedStock.getSeqNo(), is(toWarehouseSupply.getSeqNo() - 1));

		assertThat(toWarehouseDemand.getQty(), comparesEqualTo(BigDecimal.TEN));
		assertThat(toWarehouseSharedStock.getQty(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(toWarehouseSupply.getQty(), comparesEqualTo(BigDecimal.TEN));
	}
}
