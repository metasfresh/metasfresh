package de.metas.material.dispo.service.event.handler;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator;
import de.metas.material.dispo.service.event.handler.ddorder.DDOrderAdvisedHandler;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderAdvisedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.order.OrderLineRepository;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptorWithProductId;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.*;

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
public class DDOrderAdvisedHandlerTests
{
	public static final Instant t0 = NOW;

	private static final Instant t1 = t0.plus(10, ChronoUnit.MINUTES);

	/**
	 * {@link #t1} plus one day, so that we can work/test with {@link DDOrderLine#getDurationDays()}.
	 */
	private static final Instant t2 = t1.plus(1, ChronoUnit.DAYS);

	/**
	 * {@link #t2} plus two days so that we can work/test with {@link DDOrderLine#getDurationDays()}.
	 */
	private static final Instant t3 = t2.plus(2, ChronoUnit.DAYS);

	public static final WarehouseId fromWarehouseId = WarehouseId.ofRepoId(10);
	public static final WarehouseId intermediateWarehouseId = WarehouseId.ofRepoId(20);
	public static final WarehouseId toWarehouseId = WarehouseId.ofRepoId(30);

	public static final int rawProduct1Id = 50;

	public static final int rawProduct2Id = 55;

	public static final int productPlanningId = 65;

	public static final int plantId = 75;

	public static final int networkDistributionLineId = 85;

	public static final int shipperId = 95;

	private DDOrderAdvisedHandler ddOrderAdvisedHandler;

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

		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);

		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final CandidateRepositoryWriteService candidateRepositoryCommands = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo);
		final SupplyProposalEvaluator supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepository);

		final AvailableToPromiseRepository availableToPromiseRepository = new AvailableToPromiseRepository();
		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepository,
				candidateRepositoryCommands);

		final SupplyCandidateHandler supplyCandiateHandler = new SupplyCandidateHandler(candidateRepositoryCommands, stockCandidateService);

		final DemandCandiateHandler demandCandiateHandler = new DemandCandiateHandler(
				candidateRepository,
				candidateRepositoryCommands,
				postMaterialEventService,
				availableToPromiseRepository,
				stockCandidateService,
				supplyCandiateHandler);
		final CandidateChangeService candidateChangeService = new CandidateChangeService(ImmutableList.of(
				demandCandiateHandler,
				supplyCandiateHandler));

		ddOrderAdvisedHandler = new DDOrderAdvisedHandler(
				candidateRepository,
				candidateRepositoryCommands,
				candidateChangeService,
				supplyProposalEvaluator,
				new RequestMaterialOrderService(candidateRepository, postMaterialEventService, new OrderLineRepository()),
				new DDOrderDetailRequestHandler(),
				new MainDataRequestHandler());
	}

	/**
	 * Verifies that for a {@link DDOrderAdvisedEvent}, the system shall (unless the event is ignored for different reasons!) create two pairs of candidate records:
	 * <ul>
	 * <li>one supply-pair with a supply candidate and its stock <b>parent</b></li>
	 * <li>one demand-pair with a demand candidate and its stock <b>child</b></li>
	 * </ul>
	 */
	@Test
	public void handleDistributionAdvisedEvent_with_one_event()
	{
		final int demandCandidateId = 50;
		final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptor(demandCandidateId);

		final DDOrderAdvisedEvent event = DDOrderAdvisedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.fromWarehouseId(fromWarehouseId)
				.toWarehouseId(toWarehouseId)
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.ddOrder(DDOrder.builder()
								 .orgId(ORG_ID)
								 .datePromised(t2)
								 .plantId(plantId)
								 .productPlanningId(productPlanningId)
								 .shipperId(shipperId)
								 .line(DDOrderLine.builder()
											   .productDescriptor(createProductDescriptor())
											   .bPartnerId(BPARTNER_ID.getRepoId())
											   .qty(BigDecimal.TEN)
											   .durationDays(1)
											   .networkDistributionLineId(networkDistributionLineId)
											   .build())
								 .build())
				.build();
		event.validate();
		ddOrderAdvisedHandler.handleEvent(event);

		final List<I_MD_Candidate> allNonStockRecords = DispoTestUtils.filterExclStock();
		final int groupIdOfFirstRecord = allNonStockRecords.get(0).getMD_Candidate_GroupId();

		assertThat(allNonStockRecords).allSatisfy(r -> {
			assertThat(r.getMD_Candidate_GroupId()).as("all four records shall have the same groupId").isEqualTo(groupIdOfFirstRecord);
		});

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(4);
		assertThat(allRecords).allSatisfy(r -> {
			assertThat(r.getAD_Org_ID()).as("all four records shall have the same org").isEqualTo(ORG_ID.getRepoId());
			assertThat(r.getM_Product_ID()).as("all four records shall have the same product").isEqualTo(PRODUCT_ID);
		});

		// one parent of the supply in toWarehouseId and one child of the demand in fromWarehouseId
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(2);
		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(1);

		// supplyStockRecord is the parent record of supplyRecord
		final I_MD_Candidate supplyStockRecord = DispoTestUtils.filter(CandidateType.STOCK, t2).get(0);
		assertThat(supplyStockRecord.getMD_Candidate_Type()).isEqualTo(CandidateType.STOCK.toString());
		assertThat(supplyStockRecord.getMD_Candidate_Parent_ID()).isLessThanOrEqualTo(0); // supplyStockRecord shall have no parent of its own
		assertThat(supplyStockRecord.getQty()).isEqualByComparingTo(BigDecimal.TEN);
		assertThat(supplyStockRecord.getDateProjected()).isEqualTo(TimeUtil.asTimestamp(t2)); // shall have the same time as its supply record
		assertThat(supplyStockRecord.getM_Warehouse_ID()).isEqualTo(toWarehouseId.getRepoId()); // shall have the same wh as its supply record

		final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);
		assertThat(supplyRecord.getMD_Candidate_Parent_ID()).isEqualTo(supplyStockRecord.getMD_Candidate_ID());
		assertThat(supplyRecord.getDateProjected()).isEqualTo(TimeUtil.asTimestamp(t2));
		assertThat(supplyRecord.getM_Warehouse_ID()).isEqualTo(toWarehouseId.getRepoId());
		assertThat(supplyRecord.getQty()).isEqualByComparingTo(BigDecimal.TEN);
		assertThat(supplyRecord.getMD_Candidate_Parent_ID() > 0).isEqualTo(true); // supplyRecord shall have supplyStockRecord as its parent

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
		assertThat(demandRecord.getDateProjected()).isEqualTo(TimeUtil.asTimestamp(t1));
		// assertThat(demandRecord.getMD_Candidate_Parent_ID()).isEqualTo(supplyRecord.getMD_Candidate_ID()); // demandRecord shall have supplyRecord as its parent
		assertThat(demandRecord.getM_Warehouse_ID()).isEqualTo(fromWarehouseId.getRepoId());
		assertThat(demandRecord.getQty()).isEqualByComparingTo(BigDecimal.TEN);

		// demandStockRecord is "the other" stock record
		final I_MD_Candidate demandStockRecord = DispoTestUtils.filter(CandidateType.STOCK, t1).get(0);
		assertThat(demandStockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());
		assertThat(demandStockRecord.getDateProjected()).isEqualTo(TimeUtil.asTimestamp(t1)); // demandStockRecord shall have the same time as its demand record
		assertThat(demandStockRecord.getM_Warehouse_ID()).isEqualTo(fromWarehouseId.getRepoId());
		assertThat(demandStockRecord.getQty()).isEqualByComparingTo("-10");

		// For display reasons we expect the MD_Candidate_IDs to have a strict order.
		final List<I_MD_Candidate> allRecordsBySeqNo = DispoTestUtils.sortBySeqNo(DispoTestUtils.retrieveAllRecords());
		assertThat(allRecordsBySeqNo).containsSubsequence(
				supplyRecord,
				demandRecord);
		assertThat(allRecordsBySeqNo).containsOnly(
				supplyStockRecord,
				supplyRecord,
				demandRecord,
				demandStockRecord);
	}

	/**
	 * Like {@link #handleDistributionAdvisedEvent_with_one_event()},
	 * but intermediateWarehouseId => toWarehouseId and then fromWarehouseId => intermediateWarehouseId (i.e. first t2 and then t3),
	 * because that's the sequence in which the planner would provide the advise-events to us.
	 */
	@Test
	public void twoAdvisedEvents()
	{
		perform_twoAdvisedEvents(ddOrderAdvisedHandler);
	}

	/**
	 * I moved this into a static method because i want to use the code to set the stage for other tests.
	 */
	public static void perform_twoAdvisedEvents(@NonNull final DDOrderAdvisedHandler ddOrderAdvisedHandler)
	{
		final int durationTwoDays = 2; // => t3 minus 2days = t2 (expected date of the demand candidate)
		adviseDistributionFromToStartDuration(ddOrderAdvisedHandler, intermediateWarehouseId, toWarehouseId,
											  t3, // => expected date of the supply candidate
											  durationTwoDays,
											  50);
		{ // guards
			assertThat(DispoTestUtils.filter(CandidateType.SUPPLY)).hasSize(1);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);
			assertThat(supplyRecord.getM_Warehouse_ID()).isEqualTo(toWarehouseId.getRepoId());
			assertThat(supplyRecord.getDateProjected()).isEqualTo(TimeUtil.asTimestamp(t3));

			assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(1);
			assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(2); // one stock record per supply/demand record
		}

		final List<I_MD_Candidate> demandRecords = DispoTestUtils.filter(CandidateType.DEMAND, intermediateWarehouseId);
		assertThat(demandRecords).hasSize(1);
		final int demandCandidateId = demandRecords.get(0).getMD_Candidate_ID();

		final int durationOneDay = 1; // => t2 minus 1day = t1 (expected date of the demand candidate)
		adviseDistributionFromToStartDuration(ddOrderAdvisedHandler, fromWarehouseId, intermediateWarehouseId,
											  t2, // => expected date of the supply candidate
											  durationOneDay,
											  demandCandidateId);

		assertStateAfterTwoDistributionAdvisedEvents();
	}

	private static void assertStateAfterTwoDistributionAdvisedEvents()
	{
		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY)).hasSize(2); // one supply record per event
		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(2); // one demand record per event

		// the supply record from the first DDOrder and the demand record from the second both have intermediateWarehouseId and t2;
		// yet, they both have their own stock record
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(4);

		//
		// we will now verify the records in their chronological (new->old) and child->parent order
		assertThat(DispoTestUtils.filter(CandidateType.STOCK, t3)).hasSize(1);
		final I_MD_Candidate t3Stock = DispoTestUtils.filter(CandidateType.STOCK, t3).get(0);
		assertThat(t3Stock.getMD_Candidate_Parent_ID()).isLessThanOrEqualTo(0);
		assertThat(t3Stock.getQty()).isEqualByComparingTo(BigDecimal.TEN);

		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY, t3)).hasSize(1);
		final I_MD_Candidate t3Supply = DispoTestUtils.filter(CandidateType.SUPPLY, t3).get(0);
		assertThat(t3Supply.getMD_Candidate_Parent_ID()).isEqualTo(t3Stock.getMD_Candidate_ID()); // t3Supply => t3Stock
		assertThat(t3Supply.getQty()).isEqualByComparingTo(BigDecimal.TEN);

		assertThat(DispoTestUtils.filter(CandidateType.DEMAND, t2)).hasSize(1);
		final I_MD_Candidate t2Demand = DispoTestUtils.filter(CandidateType.DEMAND, t2).get(0);
		// assertThat(t2Demand.getMD_Candidate_Parent_ID()).isEqualTo(t3Supply.getMD_Candidate_ID()); // t2Demand => t3Supply
		assertThat(t2Demand.getMD_Candidate_GroupId()).isEqualTo(t3Supply.getMD_Candidate_GroupId()); // t2Demand and t3Suppy belong to the same group
		assertThat(t2Demand.getQty()).isEqualByComparingTo(BigDecimal.TEN);

		assertThat(DispoTestUtils.filter(CandidateType.STOCK, t2)).hasSize(2);
		final I_MD_Candidate t2DemandStock = DispoTestUtils.filter(CandidateType.STOCK, t2).get(0);
		assertThat(t2DemandStock.getMD_Candidate_Parent_ID()).isEqualTo(t2Demand.getMD_Candidate_ID()); // t2DemandStock => t2Demand
		assertThat(t2DemandStock.getQty()).isEqualByComparingTo(BigDecimal.TEN.negate());
		final I_MD_Candidate t2SupplyStock = DispoTestUtils.filter(CandidateType.STOCK, t2).get(1);
		assertThat(t2SupplyStock.getQty()).isZero();

		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY, t2)).hasSize(1);
		final I_MD_Candidate t2Supply = DispoTestUtils.filter(CandidateType.SUPPLY, t2).get(0);
		assertThat(t2Supply.getMD_Candidate_Parent_ID()).isEqualTo(t2SupplyStock.getMD_Candidate_ID()); // t2Supply => t2SupplyStock
		assertThat(t2Supply.getQty()).isEqualByComparingTo(BigDecimal.TEN);

		assertThat(DispoTestUtils.filter(CandidateType.DEMAND, t1)).hasSize(1);
		final I_MD_Candidate t1Demand = DispoTestUtils.filter(CandidateType.DEMAND, t1).get(0);
		// assertThat(t1Demand.getMD_Candidate_Parent_ID()).isEqualTo(t2Supply.getMD_Candidate_ID()); // t1Demand => t2Supply
		assertThat(t1Demand.getMD_Candidate_GroupId()).isEqualTo(t2Supply.getMD_Candidate_GroupId()); // t2Demand and t3Suppy belong to the same group
		assertThat(t1Demand.getQty()).isEqualByComparingTo(BigDecimal.TEN);

		assertThat(DispoTestUtils.filter(CandidateType.STOCK, t1)).hasSize(1);
		final I_MD_Candidate t1Stock = DispoTestUtils.filter(CandidateType.STOCK, t1).get(0);
		assertThat(t1Stock.getQty()).isEqualByComparingTo(BigDecimal.TEN.negate());
		assertThat(t1Stock.getMD_Candidate_Parent_ID()).isEqualTo(t1Demand.getMD_Candidate_ID());

		// for display reasons we expect the MD_Candidate_IDs to have a strict order, i.e. demand - stock - supply - demand etc..
		final List<Integer> allRecordSeqNos = DispoTestUtils.retrieveAllRecords().stream().map(r -> r.getSeqNo()).sorted().collect(Collectors.toList());
		assertThat(allRecordSeqNos)
				.containsExactly(
						t3Stock.getSeqNo(),
						t3Supply.getSeqNo(),
						t2Demand.getSeqNo(),
						t2DemandStock.getSeqNo(),
						t2SupplyStock.getSeqNo(),
						t2Supply.getSeqNo(),
						t1Demand.getSeqNo(),
						t1Stock.getSeqNo());
	}

	private static void adviseDistributionFromToStartDuration(
			final DDOrderAdvisedHandler ddOrderAdvisedHandler,
			final WarehouseId fromWarehouseId,
			final WarehouseId toWarehouseId,
			final Instant start,
			final int durationDays,
			final int demandCandidateId)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptor(demandCandidateId);

		final DDOrderAdvisedEvent event = DDOrderAdvisedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.fromWarehouseId(fromWarehouseId)
				.toWarehouseId(toWarehouseId)
				.ddOrder(DDOrder.builder()
								 .orgId(ORG_ID)
								 .datePromised(start)
								 .plantId(plantId)
								 .productPlanningId(productPlanningId)
								 .shipperId(shipperId)
								 .line(DDOrderLine.builder()
											   .salesOrderLineId(supplyRequiredDescriptor.getOrderLineId())
											   .productDescriptor(createProductDescriptor())
											   .qty(BigDecimal.TEN)
											   .networkDistributionLineId(networkDistributionLineId)
											   .durationDays(durationDays)
											   .build())
								 .build())
				.build();
		event.validate();
		ddOrderAdvisedHandler.handleEvent(event);
	}

	private static SupplyRequiredDescriptor createSupplyRequiredDescriptor(final int demandCandidateId)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = SupplyRequiredDescriptor.builder()
				.demandCandidateId(demandCandidateId)
				.shipmentScheduleId(EventTestHelper.SHIPMENT_SCHEDULE_ID) // in order to avoid cycles, we need a demand info this info
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.materialDescriptor(createMaterialDescriptorWithProductId(PRODUCT_ID))
				.build();
		return supplyRequiredDescriptor;
	}

}
