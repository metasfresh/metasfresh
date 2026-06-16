package de.metas.distribution.mobileui.external_services.warehouse;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseRepository;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link LocatorValueRoundRobinResolver#resolveNext}.
 *
 * <p>Uses the full in-memory POJO stack (AdempiereTestHelper + HUTestHelper) so that
 * {@link WarehouseRepository} and {@link de.metas.handlingunits.storage.ProductAvailableStockPerLocator}
 * both operate against real in-memory records — no mocks needed for the data layer.</p>
 */
@ExtendWith(AdempiereTestWatcher.class)
class LocatorValueRoundRobinResolverTest
{
	private HUTestHelper huHelper;
	private LocatorValueRoundRobinResolver resolver;

	private I_C_UOM uomKg;
	private ProductId productId;

	/** Warehouse that owns all locators created in each test. */
	private I_M_Warehouse warehouse;

	@BeforeEach
	void beforeEach()
	{
		// HUTestHelper.newInstanceOutOfTrx() calls AdempiereTestHelper.get().init() internally
		huHelper = HUTestHelper.newInstanceOutOfTrx();

		uomKg = BusinessTestHelper.createUOM("Kg", 3, 3);
		productId = BusinessTestHelper.createProductId("Product", uomKg);

		warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setName("TestWH");
		warehouse.setValue("TW");
		InterfaceWrapperHelper.saveRecord(warehouse);

		resolver = new LocatorValueRoundRobinResolver(new WarehouseRepository());
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	/** Creates a locator with the given ground-floor flag and priority. */
	private LocatorId createLocator(final String value, final boolean isGround, final int priorityNo)
	{
		final I_M_Locator loc = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		loc.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		loc.setValue(value);
		loc.setPriorityNo(priorityNo);
		loc.setIsGroundLocator(isGround);
		loc.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(loc);
		return LocatorId.ofRepoId(warehouse.getM_Warehouse_ID(), loc.getM_Locator_ID());
	}

	/** Creates an active VHU holding {@code qty} of {@link #productId} at {@code locatorId}. */
	private void addStock(final LocatorId locatorId, final String qty)
	{
		huHelper.newVHU()
				.productId(productId)
				.qty(Quantity.of(qty, uomKg))
				.huStatus(X_M_HU.HUSTATUS_Active)
				.locatorId(locatorId)
				.build();
	}

	// -----------------------------------------------------------------------
	// Test cases (AC5 + AC6)
	// -----------------------------------------------------------------------

	/**
	 * Case 1 (AC5): non-ground locator is skipped even when it holds stock.
	 * Ground locator G has stock; non-ground N also has stock. Current is G → next is G again? No, G is current.
	 * Setup: current=G (ground, stock), N=non-ground (stock), G2=ground (stock). Result must be G2, not N.
	 */
	@Test
	void skipsNonGroundLocator_evenWithStock()
	{
		final LocatorId ground1 = createLocator("G1", true, 10);
		final LocatorId nonGround = createLocator("N1", false, 5); // non-ground — must never be returned
		final LocatorId ground2 = createLocator("G2", true, 20);

		addStock(nonGround, "50");  // has stock, but not ground
		addStock(ground2, "5");

		// current = ground1 (no stock needed for current), next eligible ground with stock = ground2
		final LocatorId result = resolver.resolveNext(ground1, productId);
		assertThat(result).isEqualTo(ground2);
	}

	/**
	 * Case 2 (AC5): ground locator with zero stock for the product is skipped.
	 */
	@Test
	void skipsGroundLocator_withZeroStock()
	{
		final LocatorId ground1 = createLocator("G1", true, 10); // current — no stock needed
		final LocatorId ground2 = createLocator("G2", true, 20); // zero stock → skip
		final LocatorId ground3 = createLocator("G3", true, 30); // has stock → return this

		// ground2: no VHU → zero on-hand
		addStock(ground3, "3");

		final LocatorId result = resolver.resolveNext(ground1, productId);
		assertThat(result).isEqualTo(ground3);
	}

	/**
	 * Case 3 (AC5): ground locator with partial (less than any demand) stock is accepted — eligibility
	 * requires only "on-hand > 0", not "on-hand >= demand".
	 */
	@Test
	void acceptsGroundLocator_withPartialStock()
	{
		final LocatorId current = createLocator("G1", true, 10);
		final LocatorId partialStock = createLocator("G2", true, 20);

		addStock(partialStock, "0.001"); // very small but positive → eligible

		final LocatorId result = resolver.resolveNext(current, productId);
		assertThat(result).isEqualTo(partialStock);
	}

	/**
	 * Case 4: priority order is respected — prio10 locator is offered before prio20 locator.
	 */
	@Test
	void priorityOrder_prio10BeforePrio20()
	{
		// Start from a locator not in the ground list so there are no ordering side-effects
		// from the current position. Use prio10 as current, expect prio20 last; but with
		// wrap-around the next after prio10 (current) must be prio10-wrap... simpler:
		// Put current at prio30, ground list = [prio10, prio20]; next after prio30 should
		// wrap to prio10 first (since ground list is ordered prio10, prio20).
		//
		// Actually the ground list does not contain prio30 (non-ground). To test priority order:
		// current = G_prio20, both G_prio10 and G_prio20 are in list. Ground list order by prio:
		// [G_prio10, G_prio20]. Starting from G_prio20 (last), wrap-around gives G_prio10 first.
		final LocatorId gPrio10 = createLocator("GP10", true, 10);
		final LocatorId gPrio20 = createLocator("GP20", true, 20);

		addStock(gPrio10, "5");
		addStock(gPrio20, "5");

		// current = gPrio20 (last in priority order) → next = gPrio10 (wrap around)
		final LocatorId result = resolver.resolveNext(gPrio20, productId);
		assertThat(result).isEqualTo(gPrio10);
	}

	/**
	 * Case 5: the current locator is never returned, even if it is the only one with stock.
	 */
	@Test
	void currentLocator_neverReturned()
	{
		final LocatorId current = createLocator("G1", true, 10);
		final LocatorId other = createLocator("G2", true, 20); // no stock

		addStock(current, "10"); // current has stock, but must be skipped

		// other has no stock → no eligible candidate → MSG_NO_ALTERNATIVE
		assertThatThrownBy(() -> resolver.resolveNext(current, productId))
				.isInstanceOf(AdempiereException.class)
				.extracting(e -> ((AdempiereException) e).getErrorCode())
				.isEqualTo(NextPickFromLocatorResolver.MSG_NO_ALTERNATIVE.toAD_Message());
	}

	/**
	 * Case 6 (AC6): current locator is NOT in the ground list → MSG_NO_ALTERNATIVE.
	 */
	@Test
	void currentNotInGroundList_throwsMsgNoAlternative()
	{
		// current = non-ground locator; ground list is non-empty but doesn't contain current
		final LocatorId nonGroundCurrent = createLocator("NG1", false, 10);
		final LocatorId ground = createLocator("G1", true, 10);
		addStock(ground, "5");

		assertThatThrownBy(() -> resolver.resolveNext(nonGroundCurrent, productId))
				.isInstanceOf(AdempiereException.class)
				.extracting(e -> ((AdempiereException) e).getErrorCode())
				.isEqualTo(NextPickFromLocatorResolver.MSG_NO_ALTERNATIVE.toAD_Message());
	}

	/**
	 * Case 7 (AC6): empty ground list → MSG_NO_ALTERNATIVE.
	 */
	@Test
	void emptyGroundList_throwsMsgNoAlternative()
	{
		// Warehouse has only non-ground locators
		final LocatorId nonGround = createLocator("NG1", false, 10);
		addStock(nonGround, "5");

		assertThatThrownBy(() -> resolver.resolveNext(nonGround, productId))
				.isInstanceOf(AdempiereException.class)
				.extracting(e -> ((AdempiereException) e).getErrorCode())
				.isEqualTo(NextPickFromLocatorResolver.MSG_NO_ALTERNATIVE.toAD_Message());
	}
}
