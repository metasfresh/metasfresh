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
import de.metas.inout.ShipmentScheduleId;
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
import org.eevolution.model.I_DD_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Focused unit tests for the per-locator greedy allocation, covering both the locator pick order
 * (by {@code M_Locator.PriorityNo} then {@code M_Locator.Value}) and the cross-UOM case: the on-hand
 * quantities arrive in the product STOCKING UOM while the demand is in the assignment UOM, so each
 * locator's available qty must be converted into the demand UOM before it is compared/subtracted —
 * otherwise {@link de.metas.quantity.Quantity}'s arithmetic throws because the UOMs do not match.
 *
 * <p>Ordering is exercised against the real {@code IWarehouseBL.getLocatorById} path using real
 * in-memory {@code M_Locator} records (it works in the {@link AdempiereTestHelper} harness — no direct
 * SQL). The UOM conversion is injected into {@code greedyAllocate} because driving a non-stocking
 * assignment UOM end-to-end through the order → shipment-schedule → assignment chain requires a
 * contrived, fractional-case product setup (priced + stocked + ordered in different UOMs) that the real
 * warehouse workflow would never produce; the conversion + greedy logic is the thing under test and is
 * proven deterministically here. The existing 16 cucumber scenarios (assignment UOM == stocking UOM)
 * continue to cover the common end-to-end path.</p>
 */
@ExtendWith(AdempiereTestWatcher.class)
class DDOrderPickingReplenishmentServiceGreedyAllocateTest
{
	private int warehouseId;

	private DDOrderPickingReplenishmentService service;
	private DDOrderLineContributorRepository contributorRepository;

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
				mock(DDOrderMoveScheduleService.class),
				new WarehouseRepository(),
				contributorRepository
		);
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
	 * A real in-memory {@code DD_OrderLine} under the given id, so the already-delivered guard runs against actual
	 * record state rather than a stub of itself.
	 */
	private static I_DD_OrderLine createDDOrderLine(final int lineRepoId, final String qtyOrdered, final String qtyDelivered)
	{
		final I_DD_OrderLine line = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		line.setDD_OrderLine_ID(lineRepoId);
		line.setQtyEntered(new BigDecimal(qtyOrdered));
		line.setQtyOrdered(new BigDecimal(qtyOrdered));
		line.setTargetQty(new BigDecimal(qtyOrdered));
		line.setQtyDelivered(new BigDecimal(qtyDelivered));
		InterfaceWrapperHelper.saveRecord(line);
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
		// B has the lower PriorityNo (10 < 50) but the HIGHER Value -> B must be consumed first.
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
		// Equal PriorityNo -> the lower Value (A "10-A") is consumed first.
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

	// -----------------------------------------------------------------------------------------------------------------
	// Attribution: splitting the group's per-locator chunks back across the contributing assignments.
	//
	// The greedy above answers "which source locators cover the GROUP's summed demand"; attribution answers "whose
	// demand does each of those chunks serve". It is tested here rather than end-to-end because the combinatorial
	// cases (N contributors x M locators, exhausted stock, a contributor spanning two locators) cannot be enumerated
	// economically through the order -> shipment-schedule -> assignment chain — the cucumber asserts the resulting
	// document, this asserts the pure split function.
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Worked example: two customer deliveries in one group, P1 wants 10 and P2 wants 5; the greedy
	 * covered them from L1 (12) and L2 (3). The first line's 12 therefore serves P1 in full and P2 partially, and P2's
	 * remaining 3 come from the second line — i.e. one contributor legitimately spans two lines.
	 */
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

	/**
	 * Contributors ahead in the attribution order are covered in full, so the shortfall falls on those last in it.
	 * The shortfall itself stays DERIVED — no row carries the missing quantity.
	 */
	@Test
	void attribution_whenStockIsShort_theLastContributorInOrderBearsTheShortfall()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final PickingJobSchedule p1 = contributor(1, "10");
		final PickingJobSchedule p2 = contributor(2, "5");

		// P1 wants 10, P2 wants 5, only 12 available -> P1 gets 10, P2 gets 2 and is short 3.
		final Map<LocatorId, Quantity> allocation = ImmutableMap.of(l1, each("12"));

		final Map<LocatorId, ImmutableList<DDOrderLineContributor>> actual =
				service.attribute(ImmutableList.of(p1, p2), allocation);

		assertThat(actual.get(l1))
				.extracting(c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(10, 2);
		assertThat(sumOf(actual)).isEqualByComparingTo("12");
	}

	/**
	 * The two invariants of the split: EQUALITY per line (a line's quantity is exactly the sum of its
	 * contributors' shares — otherwise the mover would carry quantity nobody asked for, or a contributor's share
	 * would vanish), and INEQUALITY per contributor (partial coverage is allowed today, so a contributor may get
	 * less than it demanded but never more).
	 */
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

	/**
	 * A contributor whose demand is already fully covered by the chunks ahead of it gets NO row at all — never a
	 * {@code Qty=0} row. A zero row would make the alloc table claim the line
	 * serves a delivery it does not, and the group's own settlement would then never resolve it.
	 */
	@Test
	void attribution_contributorWithNoShare_getsNoRowAtAll()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final PickingJobSchedule p1 = contributor(1, "10");
		final PickingJobSchedule p2 = contributor(2, "5");

		// Only 10 available: P1 takes all of it, P2 gets nothing.
		final Map<LocatorId, ImmutableList<DDOrderLineContributor>> actual =
				service.attribute(ImmutableList.of(p1, p2), ImmutableMap.of(l1, each("10")));

		assertThat(actual.get(l1))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId)
				.containsExactly(p1.getId());
		assertThat(sumForContributor(actual, p2)).isEqualByComparingTo("0");
	}

	/**
	 * A line the already-delivered guard FROZE keeps its old quantity and its old shares, so the reconcile drops that
	 * locator's chunk from the attribution input and subtracts the frozen shares from their contributors' demand.
	 * Without the subtraction the frozen chunk is silently attributed a second time on the next locator.
	 *
	 * <p>Here P1 demands 10; an earlier reconcile put all 10 on L1 and the mover has partially delivered it, so L1 is
	 * frozen at 10. Stock shifted and the new allocation is {@code L1=6, L2=4}: L1's 6 is excluded, and P1's demand is
	 * already met by what the frozen line carries, so L2 must attribute NOTHING. Attributing its 4 to P1 would leave
	 * P1 carrying 14 against a demand of 10, with no exception and no log line.
	 */
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

	/**
	 * The freeze set is a FIXED POINT, not a single pre-pass.
	 *
	 * <p>L1 is refused on the first look (its chunk 5 is a shrink against an ordered 10 that is part-delivered). L2's
	 * chunk 15 is a GROWTH against its ordered 12, so probing the raw chunk clears it — but once L1's shares are netted
	 * off, only 10 of demand is left to attribute, so L2 would be written with 10 and refused after all. L2 must
	 * therefore end up frozen too: attributing 10 to a line that then refuses them discards those shares, and the
	 * demand walked through L2 is lost to every locator behind it in the pick order, on every future pass alike.
	 */
	@Test
	void frozenSplit_freezesALineThatOnlyTurnsIntoAShrinkAfterAnotherFrozenLineIsNettedOff()
	{
		final LocatorId l1 = createLocator("10-A", 50);
		final LocatorId l2 = createLocator("20-B", 50);
		final PickingJobSchedule p1 = contributor(1, "10");
		final PickingJobSchedule p2 = contributor(2, "10");

		final I_DD_OrderLine lineL1 = createDDOrderLine(101, "10", "4");  // ordered 10, delivered 4
		final I_DD_OrderLine lineL2 = createDDOrderLine(102, "12", "3");  // ordered 12, delivered 3
		// L1 historically serves 6 of P1 and 4 of P2 — more than the 5 its fresh chunk would give it.
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
				ImmutableMap.of(l1, lineL1, l2, lineL2));

		assertThat(split.getRefusedQtyByLocator()).containsOnlyKeys(l1, l2);
		assertThat(split.getAttribution()).isEmpty();
	}

	/**
	 * The partial-freeze case of the test above: the frozen line covers only part of the contributor's demand, so the
	 * remaining locators attribute exactly the rest — never the full demand again.
	 */
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
}
