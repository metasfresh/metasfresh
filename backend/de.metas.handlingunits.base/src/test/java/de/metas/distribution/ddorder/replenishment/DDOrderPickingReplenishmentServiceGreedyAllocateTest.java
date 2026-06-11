package de.metas.distribution.ddorder.replenishment;

import com.google.common.collect.ImmutableMap;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService.AllocationResult;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import org.adempiere.exceptions.NoUOMConversionException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Focused unit tests for the pure greedy per-locator allocation, with emphasis on the cross-UOM case
 * (HIGH-1): the on-hand quantities arrive in the product STOCKING UOM while the demand is in the assignment UOM,
 * so each locator's available qty must be converted into the demand UOM before it is compared/subtracted —
 * otherwise {@link de.metas.quantity.Quantity}'s arithmetic throws because the UOMs do not match.
 *
 * <p>A focused JUnit (rather than a cross-UOM cucumber scenario) is used here because driving a non-stocking
 * assignment UOM end-to-end through the order → shipment-schedule → assignment chain requires a contrived,
 * fractional-case product setup (priced + stocked + ordered in different UOMs) that the real warehouse workflow
 * would never produce; the conversion + greedy logic is the thing under test and is proven deterministically here.
 * The existing 16 cucumber scenarios (assignment UOM == stocking UOM) continue to cover the common end-to-end path.</p>
 */
@ExtendWith(AdempiereTestWatcher.class)
class DDOrderPickingReplenishmentServiceGreedyAllocateTest
{
	private I_C_UOM uomEach;   // product stocking UOM (e.g. PCE)
	private I_C_UOM uomCase;   // assignment / demand UOM (e.g. a 6-pack case)

	private LocatorId locatorA; // Value "10-A" -> consumed first
	private LocatorId locatorB; // Value "20-B" -> consumed second

	/** 1 case == 6 each. */
	private static final BigDecimal CASE_TO_EACH = new BigDecimal("6");

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uomEach = BusinessTestHelper.createUOM("Each", 0, 0);
		uomCase = BusinessTestHelper.createUOM("Case", 0, 0);

		locatorA = LocatorId.ofRepoId(1, 11);
		locatorB = LocatorId.ofRepoId(1, 12);
	}

	private Quantity each(final String qty) {return Quantity.of(qty, uomEach);}

	private Quantity cases(final String qty) {return Quantity.of(qty, uomCase);}

	private final Map<LocatorId, String> locatorValueByLocator = new HashMap<>();

	private String locatorValue(final LocatorId locatorId) {return locatorValueByLocator.get(locatorId);}

	/** Converts an on-hand qty in EACH into CASE (demand UOM). Throws if the source UOM is not EACH. */
	private Quantity convertEachToCase(final Quantity availableStockingUom)
	{
		if (!availableStockingUom.getUomId().equals(UomId.ofRepoId(uomEach.getC_UOM_ID())))
		{
			throw new NoUOMConversionException(null, availableStockingUom.getUomId(), UomId.ofRepoId(uomCase.getC_UOM_ID()));
		}
		final BigDecimal inCases = availableStockingUom.toBigDecimal().divide(CASE_TO_EACH);
		return Quantity.of(inCases, uomCase);
	}

	@Test
	void crossUom_convertsAvailableIntoDemandUom_thenSplitsGreedily()
	{
		// On-hand in the STOCKING UOM (each): 12 in A (=2 cases), 18 in B (=3 cases). Demand 4 cases.
		// Greedy by locator Value: A (lower Value) gives its full 2 cases, B covers the remaining 2 cases.
		locatorValueByLocator.put(locatorA, "10-A");
		locatorValueByLocator.put(locatorB, "20-B");

		final Map<LocatorId, Quantity> onHand = ImmutableMap.of(
				locatorA, each("12"),
				locatorB, each("18"));

		final AllocationResult result = DDOrderPickingReplenishmentService.greedyAllocate(
				cases("4"),
				onHand,
				(Function<LocatorId, String>) this::locatorValue,
				this::convertEachToCase,
				(locatorId, qty) -> {throw new AssertionError("no locator should be skipped, got " + locatorId);});

		assertThat(result.getUncovered()).isEqualTo(cases("0"));
		assertThat(result.getAllocation()).containsOnlyKeys(locatorA, locatorB);
		// allocated quantities are in the DEMAND (case) UOM
		assertThat(result.getAllocation().get(locatorA)).isEqualTo(cases("2"));
		assertThat(result.getAllocation().get(locatorB)).isEqualTo(cases("2"));
	}

	@Test
	void crossUom_partialCoverage_leavesRemainderUncovered_inDemandUom()
	{
		// On-hand: 12 each (=2 cases) in A only. Demand 5 cases -> only 2 covered, 3 uncovered.
		locatorValueByLocator.put(locatorA, "10-A");

		final Map<LocatorId, Quantity> onHand = ImmutableMap.of(locatorA, each("12"));

		final AllocationResult result = DDOrderPickingReplenishmentService.greedyAllocate(
				cases("5"),
				onHand,
				(Function<LocatorId, String>) this::locatorValue,
				this::convertEachToCase,
				(locatorId, qty) -> {throw new AssertionError("no locator should be skipped, got " + locatorId);});

		assertThat(result.getAllocation()).containsOnlyKeys(locatorA);
		assertThat(result.getAllocation().get(locatorA)).isEqualTo(cases("2"));
		assertThat(result.getUncovered()).isEqualTo(cases("3"));
	}

	@Test
	void sameUom_isANoOpConversion_splitsGreedily()
	{
		// Common case: demand UOM == stocking UOM (each). The conversion is identity.
		locatorValueByLocator.put(locatorA, "10-A");
		locatorValueByLocator.put(locatorB, "20-B");

		final Map<LocatorId, Quantity> onHand = ImmutableMap.of(
				locatorA, each("10"),
				locatorB, each("7"));

		final AllocationResult result = DDOrderPickingReplenishmentService.greedyAllocate(
				each("15"),
				onHand,
				(Function<LocatorId, String>) this::locatorValue,
				availableStockingUom -> availableStockingUom, // same UOM -> identity conversion
				(locatorId, qty) -> {throw new AssertionError("no locator should be skipped, got " + locatorId);});

		assertThat(result.getAllocation().get(locatorA)).isEqualTo(each("10"));
		assertThat(result.getAllocation().get(locatorB)).isEqualTo(each("5"));
		assertThat(result.getUncovered()).isEqualTo(each("0"));
	}

	@Test
	void locatorWithUnconvertibleQty_isSkipped_andReported()
	{
		// locatorA's on-hand is in an UOM the conversion rejects -> skipped; locatorB covers the demand.
		locatorValueByLocator.put(locatorA, "10-A");
		locatorValueByLocator.put(locatorB, "20-B");

		final Map<LocatorId, Quantity> onHand = ImmutableMap.of(
				locatorA, cases("99"), // not EACH -> convertEachToCase throws -> skipped
				locatorB, each("18"));  // 18 each = 3 cases

		final Map<LocatorId, Quantity> skipped = new HashMap<>();

		final AllocationResult result = DDOrderPickingReplenishmentService.greedyAllocate(
				cases("2"),
				onHand,
				(Function<LocatorId, String>) this::locatorValue,
				this::convertEachToCase,
				skipped::put);

		assertThat(skipped).containsOnlyKeys(locatorA);
		assertThat(result.getAllocation()).containsOnlyKeys(locatorB);
		assertThat(result.getAllocation().get(locatorB)).isEqualTo(cases("2"));
		assertThat(result.getUncovered()).isEqualTo(cases("0"));
	}
}
