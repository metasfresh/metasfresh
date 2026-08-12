package de.metas.distribution.ddorder.replenishment;

import com.google.common.collect.ImmutableMap;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.handlingunits.storage.ProductQtyOnHandByLocator;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService.AllocationResult;
import de.metas.distribution.ddorder.replenishment.event.DDOrderReplenishmentEventPublisher;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.workplace.WorkplaceService;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
				new WarehouseRepository()
		);
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
		// AC2: B has the lower PriorityNo (10 < 50) but the HIGHER Value -> B must be consumed first.
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
		// AC3: equal PriorityNo -> the lower Value (A "10-A") is consumed first.
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
}
