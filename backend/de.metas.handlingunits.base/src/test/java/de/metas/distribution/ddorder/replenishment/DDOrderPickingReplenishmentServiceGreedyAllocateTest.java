package de.metas.distribution.ddorder.replenishment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.handlingunits.storage.ProductQtyOnHandByLocator;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService.AllocationResult;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributor;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributorRepository;
import de.metas.distribution.ddorder.replenishment.event.DDOrderReplenishmentEventPublisher;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.inout.PriorityRule;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.NoUOMConversionException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseRepository;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the per-locator greedy allocation, the contributor attribution built on top of it, and the order the
 * contributors are attributed in. The UOM conversion is injected because a non-stocking assignment UOM cannot be
 * reached through the real warehouse workflow.
 */
@ExtendWith(AdempiereTestWatcher.class)
class DDOrderPickingReplenishmentServiceGreedyAllocateTest
{
	private int warehouseId;

	private DDOrderPickingReplenishmentService service;
	private DDOrderLineContributorRepository contributorRepository;
	private DDOrderMoveScheduleService ddOrderMoveScheduleService;

	/** The lines each test froze via {@link #createDDOrderLine}; the source of truth both freeze stubs answer from. */
	private final Set<DDOrderLineId> linesWithMoveInProgress = new HashSet<>();

	private I_C_UOM uomEach;   // product stocking UOM (e.g. PCE)
	private I_C_UOM uomCase;   // assignment / demand UOM (e.g. a 6-pack case)

	/**
	 * 1 case == 6 each.
	 */
	private static final BigDecimal CASE_TO_EACH = new BigDecimal("6");

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		contributorRepository = mock(DDOrderLineContributorRepository.class);
		ddOrderMoveScheduleService = mock(DDOrderMoveScheduleService.class);

		uomEach = BusinessTestHelper.createUOM("Each", 0, 0);
		uomCase = BusinessTestHelper.createUOM("Case", 0, 0);

		// Create a real M_Warehouse record so WarehouseRepository can resolve the locators created below.
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setName("TestWarehouse");
		warehouse.setValue("TW");
		InterfaceWrapperHelper.saveRecord(warehouse);
		warehouseId = warehouse.getM_Warehouse_ID();

		// greedyAllocate only uses warehouseBL + uomConversionBL (both Services.get, in-memory);
		// the constructor-injected collaborators are unused here, so plain mocks suffice.
		service = new DDOrderPickingReplenishmentService(
				mock(PickingJobRepository.class),
				mock(DDOrderLowLevelDAO.class),
				mock(DDOrderService.class),
				mock(DistributionNetworkRepository.class),
				mock(ITrxManager.class),
				mock(DDOrderReplenishmentEventPublisher.class),
				mock(PickingJobScheduleService.class),
				mock(WorkplaceService.class),
				ddOrderMoveScheduleService,
				new WarehouseRepository(),
				contributorRepository
		);

		// Default: no line is under movement. Each test that needs a frozen line states its own freeze set via
		// createDDOrderLine(..., hasMoveInProgress=true), which records the line id in linesWithMoveInProgress. Both the
		// single-id form (the in-place write site) and the batched form (computeFrozenSplit's pre-loop resolution) answer
		// from that same set.
		when(ddOrderMoveScheduleService.hasInProgressSchedules(any(DDOrderLineId.class)))
				.thenAnswer(invocation -> linesWithMoveInProgress.contains(invocation.<DDOrderLineId>getArgument(0)));
		when(ddOrderMoveScheduleService.retrieveLineIdsWithInProgressSchedules(any()))
				.thenAnswer(invocation -> invocation.<Set<DDOrderLineId>>getArgument(0).stream()
						.filter(linesWithMoveInProgress::contains)
						.collect(Collectors.collectingAndThen(Collectors.toSet(), ImmutableSet::copyOf)));
	}

	private PickingJobSchedule contributor(final int jobScheduleRepoId, final String qtyToPickEach)
	{
		return PickingJobSchedule.builder()
				.id(PickingJobScheduleId.ofRepoId(jobScheduleRepoId))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1000000, 1000000))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(jobScheduleRepoId))
				.workplaceId(WorkplaceId.ofRepoId(1000000))
				.qtyToPick(each(qtyToPickEach))
				.active(true)
				.processed(false)
				.build();
	}

	/**
	 * The contributor's shipment schedule, carrying the two effective values the attribution order reads off it.
	 * Not saved: the comparator resolves the schedule through the map it is handed, never through the record's id.
	 */
	private static I_M_ShipmentSchedule shipmentSchedule(
			@NonNull final PriorityRule priorityRule,
			@NonNull final String preparationDate)
	{
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setPriorityRule(priorityRule.getCode());
		schedule.setPreparationDate(TimeUtil.asTimestamp(Instant.parse(preparationDate)));
		return schedule;
	}

	private static BigDecimal sumOf(@NonNull final Map<LocatorId, ImmutableList<DDOrderLineContributor>> attribution)
	{
		return attribution.values().stream()
				.map(DDOrderPickingReplenishmentServiceGreedyAllocateTest::sumOf)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal sumOf(@NonNull final List<DDOrderLineContributor> rows)
	{
		return rows.stream()
				.map(row -> row.getQty().toBigDecimal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal sumForContributor(
			@NonNull final Map<LocatorId, ImmutableList<DDOrderLineContributor>> attribution,
			@NonNull final PickingJobSchedule contributor)
	{
		return attribution.values().stream()
				.flatMap(List::stream)
				.filter(row -> row.getPickingJobScheduleId().equals(contributor.getId()))
				.map(row -> row.getQty().toBigDecimal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private Quantity each(final String qty) {return Quantity.of(qty, uomEach);}

	private Quantity cases(final String qty) {return Quantity.of(qty, uomCase);}

	/**
	 * Creates a real in-memory {@code M_Locator} so the production {@code warehouseBL.getLocatorById} ordering path is exercised.
	 */
	private LocatorId createLocator(final String value, final int priorityNo)
	{
		final I_M_Locator loc = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		loc.setM_Warehouse_ID(warehouseId);
		loc.setValue(value);
		loc.setPriorityNo(priorityNo);
		loc.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(loc);
		return LocatorId.ofRepoId(warehouseId, loc.getM_Locator_ID());
	}

	/**
	 * @param hasMoveInProgress the REAL "the mover has picked and not yet dropped" signal — an IN_PROGRESS
	 *                          DD_Order_MoveSchedule. {@code QtyDelivered} is deliberately NOT set: no production flow
	 *                          writes it, so a fixture that freezes through it proves nothing about production.
	 */
	private I_DD_OrderLine createDDOrderLine(final int lineRepoId, final String qtyOrdered, final boolean hasMoveInProgress)
	{
		final I_DD_OrderLine line = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		line.setDD_OrderLine_ID(lineRepoId);
		line.setQtyEntered(new BigDecimal(qtyOrdered));
		line.setQtyOrdered(new BigDecimal(qtyOrdered));
		line.setTargetQty(new BigDecimal(qtyOrdered));
		InterfaceWrapperHelper.saveRecord(line);

		if (hasMoveInProgress)
		{
			linesWithMoveInProgress.add(DDOrderLineId.ofRepoId(lineRepoId));
		}
		return line;
	}

	/**
	 * Converts an on-hand qty in EACH into CASE (demand UOM). Throws if the source UOM is not EACH.
	 */
	private Quantity convertEachToCase(final Quantity availableStockingUom)
	{
		if (!availableStockingUom.getUomId().equals(UomId.ofRepoId(uomEach.getC_UOM_ID())))
		{
			throw new NoUOMConversionException(null, availableStockingUom.getUomId(), UomId.ofRepoId(uomCase.getC_UOM_ID()));
		}
		
		//noinspection BigDecimalMethodWithoutRoundingCalled
		final BigDecimal inCases = availableStockingUom.toBigDecimal().divide(CASE_TO_EACH);
		return Quantity.of(inCases, uomCase);
	}

	@Test
	void crossUom_convertsAvailableIntoDemandUom_thenSplitsGreedily()
	{
		// On-hand in the STOCKING UOM (each): 12 in A (=2 cases), 18 in B (=3 cases). Demand 4 cases.
		// Equal priority -> ordered by Value: A (lower Value) gives its full 2 cases, B covers the remaining 2 cases.
		final LocatorId locatorA = createLocator("10-A", 50);
		final LocatorId locatorB = createLocator("20-B", 50);

		final ProductQtyOnHandByLocator onHand = ProductQtyOnHandByLocator.ofMap(ImmutableMap.of(
				locatorA, each("12"),
				locatorB, each("18")));

		final AllocationResult result = service.greedyAllocate(
				cases("4"),
				onHand,
				this::convertEachToCase,
				(locatorId, qty) -> {throw new AssertionError("no locator should be skipped, got " + locatorId);});

		assertThat(result.getUncovered()).isEqualTo(cases("0"));
		assertThat(result.getAllocation())
				.containsOnlyKeys(locatorA, locatorB)
				// allocated quantities are in the DEMAND (case) UOM
				.containsEntry(locatorA, cases("2"))
				.containsEntry(locatorB, cases("2"));
	}

	@Test
	void crossUom_partialCoverage_leavesRemainderUncovered_inDemandUom()
	{
		// On-hand: 12 each (=2 cases) in A only. Demand 5 cases -> only 2 covered, 3 uncovered.
		final LocatorId locatorA = createLocator("10-A", 50);

		final ProductQtyOnHandByLocator onHand = ProductQtyOnHandByLocator.ofMap(ImmutableMap.of(locatorA, each("12")));

		final AllocationResult result = service.greedyAllocate(
				cases("5"),
				onHand,
				this::convertEachToCase,
				(locatorId, qty) -> {throw new AssertionError("no locator should be skipped, got " + locatorId);});

		assertThat(result.getAllocation()).containsOnlyKeys(locatorA)
				.containsEntry(locatorA, cases("2"));
		assertThat(result.getUncovered()).isEqualTo(cases("3"));
	}

	@Test
	void sameUom_isANoOpConversion_splitsGreedily()
	{
		// Common case: demand UOM == stocking UOM (each). The conversion is identity.
		final LocatorId locatorA = createLocator("10-A", 50);
		final LocatorId locatorB = createLocator("20-B", 50);

		final ProductQtyOnHandByLocator onHand = ProductQtyOnHandByLocator.ofMap(ImmutableMap.of(
				locatorA, each("10"),
				locatorB, each("7")));

		final AllocationResult result = service.greedyAllocate(
				each("15"),
				onHand,
				availableStockingUom -> availableStockingUom, // same UOM -> identity conversion
				(locatorId, qty) -> {throw new AssertionError("no locator should be skipped, got " + locatorId);});

		assertThat(result.getAllocation()).containsEntry(locatorA, each("10"))
				.containsEntry(locatorB, each("5"));
		assertThat(result.getUncovered()).isEqualTo(each("0"));
	}

	@Test
	void locatorWithUnconvertibleQty_isSkipped_andReported()
	{
		// locatorA's on-hand is in an UOM the conversion rejects -> skipped; locatorB covers the demand.
		final LocatorId locatorA = createLocator("10-A", 50);
		final LocatorId locatorB = createLocator("20-B", 50);

		final ProductQtyOnHandByLocator onHand = ProductQtyOnHandByLocator.ofMap(ImmutableMap.of(
				locatorA, cases("99"), // not EACH -> convertEachToCase throws -> skipped
				locatorB, each("18")));  // 18 each = 3 cases

		final Map<LocatorId, Quantity> skipped = new HashMap<>();

		final AllocationResult result = service.greedyAllocate(
				cases("2"),
				onHand,
				this::convertEachToCase,
				skipped::put);

		assertThat(skipped).containsOnlyKeys(locatorA);
		assertThat(result.getAllocation()).containsOnlyKeys(locatorB)
				.containsEntry(locatorB, cases("2"));
		assertThat(result.getUncovered()).isEqualTo(cases("0"));
	}

	@Test
	void buildLocatorSortKey_format_and_ordering()
	{
		// The composite key zero-pads PriorityNo to 10 digits, then appends "|" + Value.
		assertThat(DDOrderPickingReplenishmentService.buildLocatorSortKey(50, "10-A")).isEqualTo("0000000050|10-A");
		// A lower PriorityNo sorts first regardless of Value (the padded prefix dominates the lexicographic compare).
		assertThat(DDOrderPickingReplenishmentService.buildLocatorSortKey(10, "20-B"))
				.isLessThan(DDOrderPickingReplenishmentService.buildLocatorSortKey(50, "10-A"));
	}

	@Test
	void priorityWins_lowerPriorityNoConsumedFirst_regardlessOfValue()
	{
		final LocatorId locatorA = createLocator("10-A", 50);
		final LocatorId locatorB = createLocator("20-B", 10);

		final ProductQtyOnHandByLocator onHand = ProductQtyOnHandByLocator.ofMap(ImmutableMap.of(
				locatorA, each("10"),
				locatorB, each("10")));

		// Demand 4 each is fully covered by the first-consumed locator -> only that locator is allocated.
		final AllocationResult result = service.greedyAllocate(
				each("4"),
				onHand,
				availableStockingUom -> availableStockingUom,
				(locatorId, qty) -> {throw new AssertionError("no locator should be skipped, got " + locatorId);});

		assertThat(result.getAllocation()).containsOnlyKeys(locatorB)
				.containsEntry(locatorB, each("4"));
		assertThat(result.getUncovered()).isEqualTo(each("0"));
	}

	@Test
	void samePriority_tieBreaksByValue()
	{
		final LocatorId locatorA = createLocator("10-A", 50);
		final LocatorId locatorB = createLocator("20-B", 50);

		final ProductQtyOnHandByLocator onHand = ProductQtyOnHandByLocator.ofMap(ImmutableMap.of(
				locatorA, each("10"),
				locatorB, each("10")));

		final AllocationResult result = service.greedyAllocate(
				each("4"),
				onHand,
				availableStockingUom -> availableStockingUom,
				(locatorId, qty) -> {throw new AssertionError("no locator should be skipped, got " + locatorId);});

		assertThat(result.getAllocation()).containsOnlyKeys(locatorA)
				.containsEntry(locatorA, each("4"));
		assertThat(result.getUncovered()).isEqualTo(each("0"));
	}

	/**
	 * When the source warehouse has no ground-floor locators (or all ground-floor locators have zero on-hand),
	 * {@code greedyAllocate} must return an empty allocation with no exception — no NPE, no fallback to non-ground locators.
	 */
	@Test
	void noGroundLocators_returnsEmptyAllocation()
	{
		// No ground-floor locators → empty on-hand map (mirrors computeRequiredAllocation returning early)
		final AllocationResult result = service.greedyAllocate(
				each("10"),
				ProductQtyOnHandByLocator.EMPTY,
				availableStockingUom -> availableStockingUom,
				(locatorId, qty) -> {throw new AssertionError("nothing to skip when on-hand is empty, got " + locatorId);});

		assertThat(result.getAllocation()).isEmpty();
		assertThat(result.getUncovered()).isEqualTo(each("10")); // demand is entirely uncovered (no stock)
	}

	/**
	 * Ground-floor locators exist but have zero on-hand for the product → allocation is empty.
	 */
	@Test
	void groundLocatorsExist_butZeroOnHand_returnsEmptyAllocation()
	{
		// Ground locators created, but on-hand map is empty (zero stock for the product)
		createLocator("10-A", 50); // ground locator with no stock — must NOT appear in allocation
		createLocator("20-B", 50);

		final AllocationResult result = service.greedyAllocate(
				each("5"),
				ProductQtyOnHandByLocator.EMPTY,
				availableStockingUom -> availableStockingUom,
				(locatorId, qty) -> {throw new AssertionError("nothing to skip when on-hand is empty, got " + locatorId);});

		assertThat(result.getAllocation()).isEmpty();
		assertThat(result.getUncovered()).isEqualTo(each("5"));
	}

	@Test
	void attribution_splitsChunksAcrossContributorsSequentially()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final LocatorId l2 = createLocator("20-B", 50);
		final PickingJobSchedule p1 = contributor(1, "10");
		final PickingJobSchedule p2 = contributor(2, "5");

		final Map<LocatorId, Quantity> allocation = ImmutableMap.of(l1, each("12"), l2, each("3"));

		final Map<LocatorId, ImmutableList<DDOrderLineContributor>> actual =
				service.attribute(ImmutableList.of(p1, p2), allocation);

		assertThat(actual.get(l1))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(p1.getId(), 10), tuple(p2.getId(), 2));
		assertThat(actual.get(l2))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(p2.getId(), 3));
	}

	@Test
	void attribution_whenStockIsShort_theLastContributorInOrderBearsTheShortfall()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final PickingJobSchedule p1 = contributor(1, "10");
		final PickingJobSchedule p2 = contributor(2, "5");

		final Map<LocatorId, Quantity> allocation = ImmutableMap.of(l1, each("12"));

		final Map<LocatorId, ImmutableList<DDOrderLineContributor>> actual =
				service.attribute(ImmutableList.of(p1, p2), allocation);

		assertThat(actual.get(l1))
				.extracting(c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(10, 2);
		assertThat(sumOf(actual)).isEqualByComparingTo("12");
	}

	@Test
	void attribution_perLineSumEqualsTheChunk_andPerContributorSumNeverExceedsDemand()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final LocatorId l2 = createLocator("20-B", 50);
		final PickingJobSchedule p1 = contributor(1, "10");
		final PickingJobSchedule p2 = contributor(2, "5");

		final Map<LocatorId, Quantity> allocation = ImmutableMap.of(l1, each("12"), l2, each("3"));

		final Map<LocatorId, ImmutableList<DDOrderLineContributor>> actual =
				service.attribute(ImmutableList.of(p1, p2), allocation);

		assertThat(sumOf(actual.get(l1))).isEqualByComparingTo("12");
		assertThat(sumOf(actual.get(l2))).isEqualByComparingTo("3");
		assertThat(sumForContributor(actual, p1)).isLessThanOrEqualTo(p1.getQtyToPick().toBigDecimal());
		assertThat(sumForContributor(actual, p2)).isLessThanOrEqualTo(p2.getQtyToPick().toBigDecimal());
	}

	@Test
	void attribution_contributorWithNoShare_getsNoRowAtAll()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final PickingJobSchedule p1 = contributor(1, "10");
		final PickingJobSchedule p2 = contributor(2, "5");

		final Map<LocatorId, ImmutableList<DDOrderLineContributor>> actual =
				service.attribute(ImmutableList.of(p1, p2), ImmutableMap.of(l1, each("10")));

		assertThat(actual.get(l1))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId)
				.containsExactly(p1.getId());
		assertThat(sumForContributor(actual, p2)).isEqualByComparingTo("0");
	}

	@Test
	void attribution_aFrozenLineDoesNotShiftItsChunkOntoTheNextLine()
	{
		createLocator("10-A", 50); // L1 — frozen, therefore NOT part of the attribution input
		final LocatorId l2 = createLocator("20-B", 50);
		final PickingJobSchedule p1 = contributor(1, "10");

		final Map<LocatorId, ImmutableList<DDOrderLineContributor>> actual = service.attribute(
				ImmutableList.of(p1),
				ImmutableMap.of(l2, each("4")),
				ImmutableMap.of(p1.getId(), each("10")));

		assertThat(actual.get(l2)).isEmpty();
		assertThat(sumForContributor(actual, p1)).isEqualByComparingTo("0");
	}

	@Test
	void frozenSplit_freezesALineThatOnlyTurnsIntoAShrinkAfterAnotherFrozenLineIsNettedOff()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final LocatorId l2 = createLocator("20-B", 50);
		final PickingJobSchedule p1 = contributor(1, "10");
		final PickingJobSchedule p2 = contributor(2, "10");

		final I_DD_OrderLine lineL1 = createDDOrderLine(101, "10", true);
		final I_DD_OrderLine lineL2 = createDDOrderLine(102, "12", true);
		final ImmutableList<DDOrderLineContributor> sharesOfL1 = ImmutableList.of(
				DDOrderLineContributor.of(p1.getId(), each("6")),
				DDOrderLineContributor.of(p2.getId(), each("4")));
		when(contributorRepository.getByLineIds(ImmutableSet.of(DDOrderLineId.ofRepoId(101))))
				.thenReturn(sharesOfL1);
		when(contributorRepository.getByLineIds(ImmutableSet.of(DDOrderLineId.ofRepoId(101), DDOrderLineId.ofRepoId(102))))
				.thenReturn(sharesOfL1);

		final DDOrderPickingReplenishmentService.FrozenSplit split = service.computeFrozenSplit(
				ImmutableList.of(p1, p2),
				ImmutableMap.of(l1, each("5"), l2, each("15")),
				ImmutableMap.of(l1, lineL1, l2, lineL2),
				ImmutableMap.of());

		assertThat(split.getRefusedQtyByLocator()).containsOnlyKeys(l1, l2);
		assertThat(split.getAttribution()).isEmpty();
	}

	/**
	 * The reason the two served maps are merged by addition: P1 is served 4 by a disconnected duplicate AND 6 by the
	 * line frozen in the first iteration, so only 2 of its 12 are left to attribute. Under a last-wins merge it would
	 * be attributed 6.
	 */
	@Test
	void frozenSplit_sumsWhatADisconnectedDuplicateAndAFrozenLineEachServeTheSameContributor()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final LocatorId l2 = createLocator("20-B", 50);
		final PickingJobSchedule p1 = contributor(1, "12");

		final I_DD_OrderLine lineL1 = createDDOrderLine(101, "10", true);
		when(contributorRepository.getByLineIds(ImmutableSet.of(DDOrderLineId.ofRepoId(101))))
				.thenReturn(ImmutableList.of(DDOrderLineContributor.of(p1.getId(), each("6"))));

		final DDOrderPickingReplenishmentService.FrozenSplit split = service.computeFrozenSplit(
				ImmutableList.of(p1),
				ImmutableMap.of(l1, each("3"), l2, each("8")),
				ImmutableMap.of(l1, lineL1),
				ImmutableMap.of(p1.getId(), each("4")));

		assertThat(split.getRefusedQtyByLocator()).containsOnlyKeys(l1);
		assertThat(split.getRefusedQtyByLocator().get(l1).toBigDecimal()).isEqualByComparingTo("3");
		assertThat(split.getAttribution()).containsOnlyKeys(l2);
		assertThat(split.getAttribution().get(l2))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(p1.getId(), 2));
	}

	@Test
	void frozenSplit_aContributorServedOnlyByADisconnectedDuplicateIsNettedByThatAlone()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final LocatorId l2 = createLocator("20-B", 50);
		final PickingJobSchedule p1 = contributor(1, "10");
		final PickingJobSchedule p2 = contributor(2, "5");

		final DDOrderPickingReplenishmentService.FrozenSplit split = service.computeFrozenSplit(
				ImmutableList.of(p1, p2),
				ImmutableMap.of(l1, each("6"), l2, each("5")),
				ImmutableMap.of(),
				ImmutableMap.of(p1.getId(), each("4")));

		assertThat(split.getRefusedQtyByLocator()).isEmpty();
		assertThat(split.getAttribution().get(l1))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(p1.getId(), 6));
		assertThat(split.getAttribution().get(l2))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(p2.getId(), 5));
	}

	@Test
	void frozenSplit_aContributorServedOnlyByAFrozenLineIsNettedByThatAlone()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final LocatorId l2 = createLocator("20-B", 50);
		final PickingJobSchedule p1 = contributor(1, "12");

		final I_DD_OrderLine lineL1 = createDDOrderLine(101, "10", true);
		when(contributorRepository.getByLineIds(ImmutableSet.of(DDOrderLineId.ofRepoId(101))))
				.thenReturn(ImmutableList.of(DDOrderLineContributor.of(p1.getId(), each("6"))));

		final DDOrderPickingReplenishmentService.FrozenSplit split = service.computeFrozenSplit(
				ImmutableList.of(p1),
				ImmutableMap.of(l1, each("3"), l2, each("9")),
				ImmutableMap.of(l1, lineL1),
				ImmutableMap.of());

		assertThat(split.getRefusedQtyByLocator()).containsOnlyKeys(l1);
		assertThat(split.getRefusedQtyByLocator().get(l1).toBigDecimal()).isEqualByComparingTo("3");
		assertThat(split.getAttribution()).containsOnlyKeys(l2);
		assertThat(split.getAttribution().get(l2))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(p1.getId(), 6));
	}

	/**
	 * The freeze is symmetric in the batched fixed-point path too: the {@code frozenLineIds} form of the guard must
	 * refuse a GROW to a line under movement, not only a shrink. The frozen locator's newly-required qty (15) is
	 * HIGHER than the line's QtyOrdered (10), yet the frozen line stays put — the grown qty lands in the refused map
	 * and only the remainder flows on to the idle locator. Mirrors {@code aFrozenLine_isNotGrown}, which pins the same
	 * grow-refusal for the single-line {@code updateDDOrderLineQtyInPlace} overload.
	 */
	@Test
	void frozenSplit_doesNotGrowAFrozenLineUnderMovement()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final LocatorId l2 = createLocator("20-B", 50);
		final PickingJobSchedule p1 = contributor(1, "20");

		final I_DD_OrderLine lineL1 = createDDOrderLine(101, "10", true); // frozen; currently carries QtyOrdered 10
		when(contributorRepository.getByLineIds(ImmutableSet.of(DDOrderLineId.ofRepoId(101))))
				.thenReturn(ImmutableList.of(DDOrderLineContributor.of(p1.getId(), each("6"))));

		// Required at the frozen locator (15) EXCEEDS its QtyOrdered (10) -> a grow, which must be refused just like a shrink.
		final DDOrderPickingReplenishmentService.FrozenSplit split = service.computeFrozenSplit(
				ImmutableList.of(p1),
				ImmutableMap.of(l1, each("15"), l2, each("5")),
				ImmutableMap.of(l1, lineL1),
				ImmutableMap.of());

		assertThat(split.getRefusedQtyByLocator()).containsOnlyKeys(l1);
		assertThat(split.getRefusedQtyByLocator().get(l1).toBigDecimal()).isEqualByComparingTo("15");
		assertThat(split.getAttribution()).containsOnlyKeys(l2);
		assertThat(split.getAttribution().get(l2))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(p1.getId(), 5));
	}

	@Test
	void attribution_aPartiallyFrozenContributorIsOnlyAttributedItsRemainingDemand()
	{
		createLocator("10-A", 50); // L1 — frozen at 6 of P1's 10
		final LocatorId l2 = createLocator("20-B", 50);
		final PickingJobSchedule p1 = contributor(1, "10");
		final PickingJobSchedule p2 = contributor(2, "5");

		final Map<LocatorId, ImmutableList<DDOrderLineContributor>> actual = service.attribute(
				ImmutableList.of(p1, p2),
				ImmutableMap.of(l2, each("9")),
				ImmutableMap.of(p1.getId(), each("6")));

		assertThat(actual.get(l2))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(p1.getId(), 4), tuple(p2.getId(), 5));
	}

	/**
	 * The order the group's demand — and therefore its shortfall — is attributed in: priority rule first, then the
	 * earliest preparation date, then the older assignment.
	 * <p>
	 * Each of the three keys decides exactly one adjacent pair here, and each pair's two OTHER keys point the other
	 * way, so dropping any one key re-orders the result:
	 * <ul>
	 *     <li>priority: {@code urgentButLatest} has the latest date AND the highest id, so only its priority can put it first;</li>
	 *     <li>preparation date: {@code highAndEarliest} shares its priority with the two behind it and has the higher id, so only its date can;</li>
	 *     <li>assignment id: {@code highSameDayOlder}/{@code highSameDayYounger} agree on priority AND date, and are fed in
	 *     the wrong order — a stable sort would keep them that way without the id key.</li>
	 * </ul>
	 */
	@Test
	void attributionOrder_ranksByPriorityRule_thenPreparationDate_thenOlderAssignment()
	{
		final PickingJobSchedule urgentButLatest = contributor(40, "1");
		final PickingJobSchedule highAndEarliest = contributor(30, "1");
		final PickingJobSchedule highSameDayOlder = contributor(10, "1");
		final PickingJobSchedule highSameDayYounger = contributor(20, "1");

		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> schedules = ImmutableMap.of(
				urgentButLatest.getShipmentScheduleId(), shipmentSchedule(PriorityRule.Urgent, "2022-05-20T08:00:00Z"),
				highAndEarliest.getShipmentScheduleId(), shipmentSchedule(PriorityRule.High, "2022-05-18T08:00:00Z"),
				highSameDayOlder.getShipmentScheduleId(), shipmentSchedule(PriorityRule.High, "2022-05-19T08:00:00Z"),
				highSameDayYounger.getShipmentScheduleId(), shipmentSchedule(PriorityRule.High, "2022-05-19T08:00:00Z"));

		// Fed in the exact REVERSE of the expected order, so no key can look right by accident of the input order.
		final List<PickingJobSchedule> actual = Stream
				.of(highSameDayYounger, highSameDayOlder, highAndEarliest, urgentButLatest)
				.sorted(service.attributionOrder(schedules))
				.collect(ImmutableList.toImmutableList());

		assertThat(actual)
				.extracting(PickingJobSchedule::getId)
				.containsExactly(
						urgentButLatest.getId(),
						highAndEarliest.getId(),
						highSameDayOlder.getId(),
						highSameDayYounger.getId());
	}
}
