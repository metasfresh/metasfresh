package de.metas.distribution.mobileui.external_services.warehouse;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocatorValueRoundRobinResolverTest
{
	private LocatorValueRoundRobinResolver resolver;
	private WarehouseId warehouseId;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		resolver = new LocatorValueRoundRobinResolver();

		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setName("WH");
		save(warehouse);
		warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	private LocatorId locator(final String value, final boolean active)
	{
		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(warehouseId.getRepoId());
		locator.setValue(value);
		locator.setIsActive(active);
		save(locator);
		return LocatorId.ofRepoId(locator.getM_Warehouse_ID(), locator.getM_Locator_ID());
	}

	@Test
	void advances_mid_list()
	{
		final LocatorId a = locator("A", true);
		final LocatorId b = locator("B", true);
		locator("C", true);

		assertThat(resolver.resolveNext(warehouseId, a)).isEqualTo(b);
	}

	@Test
	void wraps_from_last_to_first()
	{
		final LocatorId a = locator("A", true);
		locator("B", true);
		final LocatorId c = locator("C", true);

		assertThat(resolver.resolveNext(warehouseId, c)).isEqualTo(a);
	}

	@Test
	void orders_by_value_not_by_id()
	{
		// Create Z first (lower ID), then A (higher ID), then M
		// Sorted by Value: A, M, Z
		final LocatorId z = locator("Z", true);
		final LocatorId a = locator("A", true);
		final LocatorId m = locator("M", true);

		// Value ordering: A -> M -> Z -> A
		assertThat(resolver.resolveNext(warehouseId, a)).isEqualTo(m);
		assertThat(resolver.resolveNext(warehouseId, m)).isEqualTo(z);
		assertThat(resolver.resolveNext(warehouseId, z)).isEqualTo(a);
	}

	@Test
	void skips_inactive_locators()
	{
		// A (active), B (INACTIVE), C (active)
		// Expect resolveNext(A) == C (skips B)
		final LocatorId a = locator("A", true);
		locator("B", false);
		final LocatorId c = locator("C", true);

		assertThat(resolver.resolveNext(warehouseId, a)).isEqualTo(c);
	}

	@Test
	void returns_first_when_current_not_in_set()
	{
		final LocatorId a = locator("A", true);
		locator("B", true);

		final LocatorId nonexistent = LocatorId.ofRepoId(warehouseId.getRepoId(), 99_999_999);
		assertThat(resolver.resolveNext(warehouseId, nonexistent)).isEqualTo(a);
	}

	@Test
	void throws_when_only_current_active_locator_exists()
	{
		// A active, B inactive — only A is in the set
		final LocatorId a = locator("A", true);
		locator("B", false);

		assertThatThrownBy(() -> resolver.resolveNext(warehouseId, a))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(LocatorValueRoundRobinResolver.MSG_NO_ALTERNATIVE.toAD_Message());
	}

	@Test
	void throws_when_zero_active_locators()
	{
		// Create only inactive locators
		locator("A", false);
		locator("B", false);

		final LocatorId syntheticId = LocatorId.ofRepoId(warehouseId.getRepoId(), 99_999_999);
		assertThatThrownBy(() -> resolver.resolveNext(warehouseId, syntheticId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(LocatorValueRoundRobinResolver.MSG_NO_ALTERNATIVE.toAD_Message());
	}
}
