package de.metas.device.adempiere.impl;

import java.util.List;
import java.util.Set;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.collections.ListUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.device.adempiere.AttributesDevicesHub;
import de.metas.device.adempiere.AttributesDevicesHub.AttributeDeviceAccessor;

/*
 * #%L
 * de.metas.device.adempiere
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

public class SysConfigDeviceConfigPoolTest
{

	@Rule
	public final AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private SysConfigDeviceConfigPoolTestHelper helper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		helper = new SysConfigDeviceConfigPoolTestHelper();
	}

	@Test
	public void testDeviceConfig_NoWarehouse()
	{
		helper.createDeviceConfigAndAssertValid("device1", "WeightGross", ImmutableSet.of());

		//
		// Test device accessor
		{
			final AttributesDevicesHub devicesHub = helper.createDevicesHub();
			final List<AttributeDeviceAccessor> deviceAccessors = devicesHub.getAttributeDeviceAccessors("WeightGross")
					.stream(-1) // any
					.collect(ImmutableList.toImmutableList());

			final AttributeDeviceAccessor deviceAccessor = ListUtils.singleElement(deviceAccessors);

			// shall be available for ANY warehouse
			assertAvailableForWarehouse(deviceAccessor, -1, true);
			assertAvailableForWarehouse(deviceAccessor, 1, true);
			assertAvailableForWarehouse(deviceAccessor, 2, true);
		}
	}

	@Test
	public void testDeviceConfig_WithWarehouse()
	{
		final Set<Integer> warehouseIds = ImmutableSet.of(1000001, 1000002);
		helper.createDeviceConfigAndAssertValid("device1", "WeightGross", warehouseIds);
		// POJOLookupMap.get().dumpStatus(); // dump configuration

		//
		// Test device accessor
		final AttributesDevicesHub devicesHub = helper.createDevicesHub();
		for (final int warehouseId : warehouseIds)
		{
			final List<AttributeDeviceAccessor> deviceAccessors = devicesHub.getAttributeDeviceAccessors("WeightGross")
					.stream(warehouseId)
					.collect(ImmutableList.toImmutableList());

			final AttributeDeviceAccessor deviceAccessor = ListUtils.singleElement(deviceAccessors);

			// shall be available only for configured warehouse
			assertAvailableForWarehouse(deviceAccessor, warehouseId, true);
			assertAvailableForWarehouse(deviceAccessor, -1, false);
			assertAvailableForWarehouse(deviceAccessor, 1, false);
			assertAvailableForWarehouse(deviceAccessor, 2, false);
		}

	}

	private static final void assertAvailableForWarehouse(final AttributeDeviceAccessor deviceAccessor, final int warehouseId, final boolean expectedAvailable)
	{
		final String msg = "Expect available for warehouse"
				+ "\n accessor: " + deviceAccessor
				+ "\n warehoust: " + warehouseId;
		Assert.assertEquals(msg, expectedAvailable, deviceAccessor.isAvailableForWarehouse(warehouseId));
	}
}
