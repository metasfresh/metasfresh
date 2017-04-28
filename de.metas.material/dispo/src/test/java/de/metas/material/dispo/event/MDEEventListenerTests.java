package de.metas.material.dispo.event;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.DistributionPlanEvent;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.ProductionPlanEvent;
import de.metas.material.event.ShipmentScheduleEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;

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

	private static final Date t3 = TimeUtil.addMinutes(t0, 30);

	public static final int fromWarehouseId = 10;
	public static final int intermediateWarehouseId = 20;
	public static final int toWarehouseId = 30;

	public static final int productId = 40;

	public static final int rawProduct1Id = 50;

	public static final int rawProduct2Id = 55;

	private MDEventListener mdEventListener;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepository candidateRepository = new CandidateRepository();
		final SupplyProposalEvaluator supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepository);

		mdEventListener = new MDEventListener(
				new CandidateChangeHandler(candidateRepository, new CandidateFactory(candidateRepository)),
				candidateRepository,
				supplyProposalEvaluator);
	}

	/**
	 * This test is more for myself, to figure out how the system works :-$
	 */
	@Test
	public void testShipmentScheduleEvent()
	{
		final ShipmentScheduleEvent event = ShipmentScheduleEvent.builder()
				.materialDescr(MaterialDescriptor.builder()
						.date(t1)
						.orgId(20)
						.productId(productId)
						.qty(BigDecimal.TEN)
						.warehouseId(toWarehouseId)
						.build())
				.reference(TableRecordReference.of("someTable", 4))
				.when(Instant.now())
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

	/**
	 * Verifies that for a {@link DistributionPlanEvent}, the system shall (unless the event is ignored for different reasons!) create two pairs of candidate records:
	 * <ul>
	 * <li>one supply-pair with a supply candidate and its stock <b>parent</b></li>
	 * <li>one demand-pair with a demand candidate and its stock <b>child</b></li>
	 * </ul>
	 */
	@Test
	public void testSingleDistibutionPlanEvent()
	{
		final TableRecordReference reference = TableRecordReference.of("someTable", 4);
		final DistributionPlanEvent event = DistributionPlanEvent.builder()
				.distributionStart(t1)
				.fromWarehouseId(fromWarehouseId)
				.materialDescr(MaterialDescriptor.builder()
						.date(t2)
						.orgId(20)
						.productId(productId)
						.qty(BigDecimal.TEN)
						.warehouseId(toWarehouseId)
						.build())
				.reference(reference)
				.when(Instant.now())
				.build();
		mdEventListener.onEvent(event);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords.size(), is(4));

		// all four shall have the same product
		allRecords.forEach(r -> {
			assertThat(r.getM_Product_ID(), is(productId));
		});

		// all four shall have the same org
		allRecords.forEach(r -> {
			assertThat(r.getAD_Org_ID(), is(20));
		});

		// all four shall have the same reference
		allRecords.forEach(r -> {
			final TableRecordReference ofReferenced = TableRecordReference.ofReferenced(r);
			assertThat(ofReferenced, is(reference));
		});

		assertThat(DispoTestUtils.filter(Type.SUPPLY).size(), is(1));
		assertThat(DispoTestUtils.filter(Type.STOCK).size(), is(2));
		assertThat(DispoTestUtils.filter(Type.DEMAND).size(), is(1));

		// supplyStockRecord is the parent record of supplyRecord
		final I_MD_Candidate supplyStockRecord = DispoTestUtils.filter(Type.STOCK, t2).get(0);
		assertThat(supplyStockRecord.getMD_Candidate_Type(), is(Type.STOCK.toString()));
		assertThat(supplyStockRecord.getMD_Candidate_Parent_ID(), lessThanOrEqualTo(0)); // supplyStockRecord shall have no parent of its own
		assertThat(supplyStockRecord.getQty(), comparesEqualTo(BigDecimal.TEN));
		assertThat(supplyStockRecord.getDateProjected().getTime(), is(t2.getTime())); // shall have the same time as its supply record
		assertThat(supplyStockRecord.getM_Warehouse_ID(), is(toWarehouseId)); // shall have the same wh as its supply record

		final I_MD_Candidate supplyRecord = DispoTestUtils.filter(Type.SUPPLY).get(0);
		assertThat(supplyRecord.getMD_Candidate_Parent_ID(), is(supplyStockRecord.getMD_Candidate_ID()));
		assertThat(supplyRecord.getDateProjected().getTime(), is(t2.getTime()));
		assertThat(supplyRecord.getM_Warehouse_ID(), is(toWarehouseId));
		assertThat(supplyRecord.getQty(), comparesEqualTo(BigDecimal.TEN));
		assertThat(supplyRecord.getMD_Candidate_Parent_ID() > 0, is(true)); // supplyRecord shall have supplyStockRecord as its parent

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(Type.DEMAND).get(0);
		assertThat(demandRecord.getDateProjected().getTime(), is(t1.getTime()));
		assertThat(demandRecord.getMD_Candidate_Parent_ID(), is(supplyRecord.getMD_Candidate_ID())); // demandRecord shall have supplyRecord as its parent
		assertThat(demandRecord.getM_Warehouse_ID(), is(fromWarehouseId));
		assertThat(demandRecord.getQty(), comparesEqualTo(BigDecimal.TEN));

		// demandStockRecord is "the other" stock record
		final I_MD_Candidate demandStockRecord = DispoTestUtils.filter(Type.STOCK, t1).get(0);
		assertThat(demandStockRecord.getMD_Candidate_Parent_ID(), is(demandRecord.getMD_Candidate_ID()));
		assertThat(demandStockRecord.getDateProjected().getTime(), is(t1.getTime())); // demandStockRecord shall have the same time as its demand record
		assertThat(demandStockRecord.getM_Warehouse_ID(), is(fromWarehouseId));
		assertThat(demandStockRecord.getQty(), comparesEqualTo(BigDecimal.TEN.negate()));

		// for display reasons we expect the MD_Candidate_IDs to have a strict order, i.e. demand - stock - supply - demand etc..
		assertThat(supplyStockRecord.getSeqNo(), is(supplyRecord.getSeqNo() - 1));
		assertThat(supplyRecord.getSeqNo(), is(demandRecord.getSeqNo() - 1));
		assertThat(demandRecord.getSeqNo(), is(demandStockRecord.getSeqNo() - 1));
	}

	/**
	 * Submits two DistributionPlanEvent to the {@link MDEventListener}.
	 */
	@Test
	public void testTwoDistibutionPlanEvents()
	{
		performTestTwoDistibutionPlanEvents(mdEventListener);
	}

	/**
	 * Contains the actual test for {@link #testTwoDistibutionPlanEvents()}. I moved this into a static method because i want to use the code to set the stage for other tests.
	 *
	 * @param mdEventListener
	 */
	public static void performTestTwoDistibutionPlanEvents(final MDEventListener mdEventListener)
	{
		final TableRecordReference reference = TableRecordReference.of("someTable", 4);

		final DistributionPlanEvent event1 = DistributionPlanEvent.builder()
				.distributionStart(t1)
				.fromWarehouseId(fromWarehouseId)
				.materialDescr(MaterialDescriptor.builder()
						.date(t2)
						.orgId(20)
						.productId(productId)
						.qty(BigDecimal.TEN)
						.warehouseId(intermediateWarehouseId)
						.build())
				.reference(reference)
				.when(Instant.now())
				.build();
		mdEventListener.onEvent(event1);

		assertThat(DispoTestUtils.filter(Type.SUPPLY).size(), is(1));
		assertThat(DispoTestUtils.filter(Type.DEMAND).size(), is(1));
		assertThat(DispoTestUtils.filter(Type.STOCK).size(), is(2)); // one stock record per supply/demand record

		final DistributionPlanEvent event2 = DistributionPlanEvent.builder()
				.distributionStart(t2)
				.fromWarehouseId(intermediateWarehouseId)
				.materialDescr(MaterialDescriptor.builder()
						.date(t3)
						.orgId(20)
						.productId(productId)
						.qty(BigDecimal.TEN)
						.warehouseId(toWarehouseId)
						.build())
				.reference(reference)
				.when(Instant.now())
				.build();
		mdEventListener.onEvent(event2);

		assertThat(DispoTestUtils.filter(Type.SUPPLY).size(), is(2)); // one supply record per event
		assertThat(DispoTestUtils.filter(Type.DEMAND).size(), is(2)); // one demand record per event
		assertThat(DispoTestUtils.filter(Type.STOCK).size(), is(3)); // the supply record and the demand record with intermediateWarehouseId and t2 *share* one stock record!

		//
		// we will now verify the records in their chronological (new->old) and child->parent order
		assertThat(DispoTestUtils.filter(Type.STOCK, t3).size(), is(1));
		final I_MD_Candidate t3Stock = DispoTestUtils.filter(Type.STOCK, t3).get(0);
		assertThat(t3Stock.getMD_Candidate_Parent_ID(), lessThanOrEqualTo(0));
		assertThat(t3Stock.getQty(), comparesEqualTo(BigDecimal.TEN));

		assertThat(DispoTestUtils.filter(Type.SUPPLY, t3).size(), is(1));
		final I_MD_Candidate t3Supply = DispoTestUtils.filter(Type.SUPPLY, t3).get(0);
		assertThat(t3Supply.getMD_Candidate_Parent_ID(), is(t3Stock.getMD_Candidate_ID())); // t3Supply => t3Stock
		assertThat(t3Supply.getQty(), comparesEqualTo(BigDecimal.TEN));

		assertThat(DispoTestUtils.filter(Type.DEMAND, t2).size(), is(1));
		final I_MD_Candidate t2Demand = DispoTestUtils.filter(Type.DEMAND, t2).get(0);
		assertThat(t2Demand.getMD_Candidate_Parent_ID(), is(t3Supply.getMD_Candidate_ID())); // t2Demand => t3Supply
		assertThat(t2Demand.getMD_Candidate_GroupId(), is(t3Supply.getMD_Candidate_GroupId())); // t2Demand and t3Suppy belong to the same group
		assertThat(t2Demand.getQty(), comparesEqualTo(BigDecimal.TEN));

		assertThat(DispoTestUtils.filter(Type.STOCK, t2).size(), is(1));
		final I_MD_Candidate t2Stock = DispoTestUtils.filter(Type.STOCK, t2).get(0); // this is the one that is shared!
		assertThat(t2Stock.getMD_Candidate_Parent_ID(), is(t2Demand.getMD_Candidate_ID())); // t2Stock => t2Demand
		assertThat(t2Stock.getQty(), comparesEqualTo(BigDecimal.ZERO)); // it's balanced between t2Demand and t2Supply

		assertThat(DispoTestUtils.filter(Type.SUPPLY, t2).size(), is(1));
		final I_MD_Candidate t2Supply = DispoTestUtils.filter(Type.SUPPLY, t2).get(0);
		assertThat(t2Supply.getMD_Candidate_Parent_ID(), is(t2Stock.getMD_Candidate_ID()));  // t2Supply => t2Stock
		assertThat(t2Supply.getQty(), comparesEqualTo(BigDecimal.TEN));

		assertThat(DispoTestUtils.filter(Type.DEMAND, t1).size(), is(1));
		final I_MD_Candidate t1Demand = DispoTestUtils.filter(Type.DEMAND, t1).get(0);
		assertThat(t1Demand.getMD_Candidate_Parent_ID(), is(t2Supply.getMD_Candidate_ID())); // t1Demand => t2Supply
		assertThat(t1Demand.getMD_Candidate_GroupId(), is(t2Supply.getMD_Candidate_GroupId())); // t2Demand and t3Suppy belong to the same group
		assertThat(t1Demand.getQty(), comparesEqualTo(BigDecimal.TEN));

		assertThat(DispoTestUtils.filter(Type.STOCK, t1).size(), is(1));
		final I_MD_Candidate t1Stock = DispoTestUtils.filter(Type.STOCK, t1).get(0);
		assertThat(t1Stock.getQty(), comparesEqualTo(BigDecimal.TEN.negate()));
		assertThat(t1Stock.getMD_Candidate_Parent_ID(), is(t1Demand.getMD_Candidate_ID()));

		// for display reasons we expect the MD_Candidate_IDs to have a strict order, i.e. demand - stock - supply - demand etc..
		final List<Integer> allRecordSeqNos = DispoTestUtils.retrieveAllRecords().stream().map(r -> r.getSeqNo()).sorted().collect(Collectors.toList());
		assertThat(allRecordSeqNos, contains(
				t3Stock.getSeqNo(),
				t3Supply.getSeqNo(),
				t2Demand.getSeqNo(),
				t2Stock.getSeqNo(),
				t2Supply.getSeqNo(),
				t1Demand.getSeqNo(),
				t1Stock.getSeqNo()));
	}

	@Test
	public void testProductionPlanEvent()
	{
		final TableRecordReference reference = TableRecordReference.of("someTable", 4);

		final BigDecimal eleven = BigDecimal.TEN.add(BigDecimal.ONE);

		final ProductionPlanEvent productionPlanEvent = ProductionPlanEvent.builder()
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
				.when(Instant.now())
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
				.when(Instant.now())
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
