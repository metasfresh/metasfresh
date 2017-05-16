package de.metas.material.dispo.service.event;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidateService;
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.CandidateChangeHandler;
import de.metas.material.dispo.service.CandidateFactory;
import de.metas.material.event.DistributionPlanEvent;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
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

public class DistributionPlanEventHandlerTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	public static final Date t0 = SystemTime.asDate();

	private static final Date t1 = TimeUtil.addMinutes(t0, 10);

	/**
	 * {@link #t1} plus one day, so that we can work/test with {@link DDOrderLine#getDurationDays()}.
	 */
	private static final Date t2 = TimeUtil.addDaysExact(t1, 1);

	/**
	 * {@link #t2} plus two days so that we can work/test with {@link DDOrderLine#getDurationDays()}.
	 */
	private static final Date t3 = TimeUtil.addDaysExact(t2, 2);

	public static final int fromWarehouseId = 10;
	public static final int intermediateWarehouseId = 20;
	public static final int toWarehouseId = 30;

	public static final int productId = 40;

	public static final int rawProduct1Id = 50;

	public static final int rawProduct2Id = 55;

	public static final int productPlanningId = 65;
	
	public static final int plantId = 75;

	public static final int networkDistributionLineId = 85;

	public static final int shipperId = 95;
	
	private I_AD_Org org;

	private DistributionPlanEventHandler distributionPlanEventHandler;

	@Mocked
	private MaterialEventService materialEventService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		org = newInstance(I_AD_Org.class);
		save(org);

		final CandidateRepository candidateRepository = new CandidateRepository();
		final SupplyProposalEvaluator supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepository);

		distributionPlanEventHandler = new DistributionPlanEventHandler(
				candidateRepository,
				new CandidateChangeHandler(candidateRepository, new CandidateFactory(candidateRepository), materialEventService),
				supplyProposalEvaluator,
				new CandidateService(candidateRepository, materialEventService));
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
				.eventDescr(new EventDescr(org.getAD_Client_ID(), org.getAD_Org_ID()))
				.fromWarehouseId(fromWarehouseId)
				.toWarehouseId(toWarehouseId)
				.ddOrder(DDOrder.builder()
						.orgId(org.getAD_Org_ID())
						.datePromised(t2)
						.plantId(plantId)
						.productPlanningId(productPlanningId)
						.shipperId(shipperId)
						.line(DDOrderLine.builder()
								.productId(productId)
								.qty(BigDecimal.TEN)
								.durationDays(1)
								.networkDistributionLineId(networkDistributionLineId)
								.build())
						.build())
				.reference(reference)
				.build();
		distributionPlanEventHandler.handleDistributionPlanEvent(event);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords.size(), is(4));

		// all four shall have the same product
		allRecords.forEach(r ->
			{
				assertThat(r.getM_Product_ID(), is(productId));
			});

		// all four shall have the same org
		allRecords.forEach(r ->
			{
				assertThat(r.getAD_Org_ID(), is(org.getAD_Org_ID()));
			});

		// all four shall have the same reference
		allRecords.forEach(r ->
			{
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
		performTestTwoDistibutionPlanEvents(distributionPlanEventHandler, org);
	}

	// TODO: test in reversed order
	
	/**
	 * Contains the actual test for {@link #testTwoDistibutionPlanEvents()}. I moved this into a static method because i want to use the code to set the stage for other tests.
	 *
	 * @param mdEventListener
	 */
	public static void performTestTwoDistibutionPlanEvents(
			@NonNull final DistributionPlanEventHandler distributionPlanEventHandler,
			@NonNull final I_AD_Org org)
	{
		final TableRecordReference reference = TableRecordReference.of("someTable", 4);

		final DistributionPlanEvent event1 = DistributionPlanEvent.builder()
				.eventDescr(new EventDescr(org.getAD_Client_ID(), org.getAD_Org_ID()))
				.fromWarehouseId(fromWarehouseId)
				.toWarehouseId(intermediateWarehouseId)
				.ddOrder(DDOrder.builder()
						.orgId(org.getAD_Org_ID())
						.datePromised(t2) // => expected date of the supply candidate
						.plantId(plantId)
						.productPlanningId(productPlanningId)
						.shipperId(shipperId)
						.line(DDOrderLine.builder()
								.productId(productId)
								.qty(BigDecimal.TEN)
								.networkDistributionLineId(networkDistributionLineId)
								.durationDays(1) // => t2 minus 1day = t1 (expected date of the demand candidate)
								.build())
						.build())
				.reference(reference)
				.build();
		distributionPlanEventHandler.handleDistributionPlanEvent(event1);

		assertThat(DispoTestUtils.filter(Type.SUPPLY).size(), is(1));
		assertThat(DispoTestUtils.filter(Type.DEMAND).size(), is(1));
		assertThat(DispoTestUtils.filter(Type.STOCK).size(), is(2)); // one stock record per supply/demand record

		final DistributionPlanEvent event2 = DistributionPlanEvent.builder()
				.eventDescr(new EventDescr(org.getAD_Client_ID(), org.getAD_Org_ID()))
				.fromWarehouseId(intermediateWarehouseId)
				.toWarehouseId(toWarehouseId)
				.ddOrder(DDOrder.builder()
						.orgId(org.getAD_Org_ID())
						.datePromised(t3) // => expected date of the supply candidate
						.plantId(plantId)
						.productPlanningId(productPlanningId)
						.shipperId(shipperId)
						.line(DDOrderLine.builder()
								.productId(productId)
								.qty(BigDecimal.TEN)
								.durationDays(2) // => t3 minus 2days = t2 (expected date of the demand candidate)
								.networkDistributionLineId(networkDistributionLineId)
								.build())
						.build())
				.reference(reference)
				.build();
		distributionPlanEventHandler.handleDistributionPlanEvent(event2);

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
}
