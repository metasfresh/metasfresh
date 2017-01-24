package de.metas.handlingunits.impl;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.allocation.spi.impl.PackingMaterialItemTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.X_M_HU_Item;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HandlingUnitsBLTests
{
	private HUTestHelper helper;

	@Before
	public void init()
	{
		helper = new HUTestHelper();
		helper.init();
	}

	/**
	 * Decrease the storage to 0, so not more TUs are required.
	 */
	@Test
	public void testUpdatePackingMaterialQty0()
	{
		final BigDecimal cuQty = BigDecimal.TEN;

		final BigDecimal oldPmItemQty = new BigDecimal("9");
		final BigDecimal oldStorageQty = new BigDecimal("85");

		final BigDecimal newStorageQty = BigDecimal.ZERO;
		final BigDecimal expectedPmItemQty = BigDecimal.ZERO;

		runTest(cuQty, oldPmItemQty, oldStorageQty, newStorageQty, expectedPmItemQty);
	}

	/**
	 * Decrease the storage from 85 to 83, so still the same amount of TUs is required.
	 */
	@Test
	public void testUpdatePackingMaterialQty1()
	{
		final BigDecimal cuQty = BigDecimal.TEN;

		final BigDecimal oldPmItemQty = new BigDecimal("9");
		final BigDecimal oldStorageQty = new BigDecimal("85");

		final BigDecimal newStorageQty = new BigDecimal("83");
		final BigDecimal expectedPmItemQty = new BigDecimal("9");

		runTest(cuQty, oldPmItemQty, oldStorageQty, newStorageQty, expectedPmItemQty);
	}

	/**
	 * Decrease the storage from 81 to 79, so one less TU is required.
	 */
	@Test
	public void testUpdatePackingMaterialQty2()
	{
		final BigDecimal cuQty = BigDecimal.TEN;

		final BigDecimal oldPmItemQty = new BigDecimal("9");
		final BigDecimal oldStorageQty = new BigDecimal("81");

		final BigDecimal newStorageQty = new BigDecimal("79");
		final BigDecimal expectedPmItemQty = new BigDecimal("8");

		runTest(cuQty, oldPmItemQty, oldStorageQty, newStorageQty, expectedPmItemQty);
	}

	private void runTest(final BigDecimal cuQty, final BigDecimal oldPmItemQty, final BigDecimal oldStorageQty, final BigDecimal newStorageQty, final BigDecimal expectedPmItemQty)
	{
		final I_M_HU_LUTU_Configuration lutuConfig = InterfaceWrapperHelper.newInstance(I_M_HU_LUTU_Configuration.class);
		lutuConfig.setQtyCU(cuQty);
		InterfaceWrapperHelper.save(lutuConfig);

		final I_M_HU vhu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		vhu.setM_HU_LUTU_Configuration(lutuConfig);
		InterfaceWrapperHelper.save(vhu);

		final I_M_HU_Item pmItem = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		pmItem.setM_HU(vhu);
		pmItem.setItemType(X_M_HU_Item.ITEMTYPE_PackingMaterial);
		pmItem.setQty(oldPmItemQty);
		InterfaceWrapperHelper.save(pmItem);

		final I_M_HU_Item miItem = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		pmItem.setM_HU(vhu);
		pmItem.setItemType(X_M_HU_Item.ITEMTYPE_Material);
		InterfaceWrapperHelper.save(pmItem);

		final I_M_HU_Item_Storage storage = InterfaceWrapperHelper.newInstance(I_M_HU_Item_Storage.class);
		storage.setM_HU_Item(miItem);
		storage.setQty(oldStorageQty);
		InterfaceWrapperHelper.save(storage);

		storage.setQty(newStorageQty);

//		I_M_HU_Trx_Line trxLine = InterfaceWrapperHelper.newInstance(I_M_HU_Trx_Line.class);
//		trxLine.setVHU_Item(VHU_Item);
//
//		// invoke the method under test
//		final List<I_M_HU_Item> updatedItems = PackingMaterialItemTrxListener.INSTANCE.updatePackingMaterialQty(storage);

//		assertThat("Invalid number of updated items", updatedItems.size(), is(1));
//		assertThat(updatedItems.get(0).getQty(), comparesEqualTo(expectedPmItemQty));
	}

	/**
	 * Verifies that {@link HandlingUnitsBL#isAggregateHU(I_M_HU)} returns {@code false} for a null param. This is a trivial test, but we rely on that behavior of the isAggregateHU() method.
	 */
	@Test
	public void testIsAggregateHUwithNull()
	{
		assertThat(new HandlingUnitsBL().isAggregateHU(null), is(false));
	}
}
