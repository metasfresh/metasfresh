package de.metas.handlingunits.client.terminal.inventory.model;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Arrays;
import java.util.Collections;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link InventoryHUSelectModel}.
 *
 * @author tsa
 *
 */
public class InventoryHUSelectModelTest extends InventoryHUSelectModelTestTemplate
{
	//
	private InventoryHUSelectModel huSelectModel;
	private Integer huSelectModel_SelectedWarehouse_ID = null;

	@Override
	protected void afterInit()
	{
		huSelectModel = createHUSelectModel();
	}

	/** @return Object under test */
	protected InventoryHUSelectModel createHUSelectModel()
	{
		final InventoryHUSelectModel inventoryHUSelectModel = new InventoryHUSelectModel(helper.getTerminalContext())
		{
			@Override
			public int getM_Warehouse_ID()
			{
				if (huSelectModel_SelectedWarehouse_ID != null)
				{
					return huSelectModel_SelectedWarehouse_ID;
				}
				return super.getM_Warehouse_ID();
			}

			@Override
			public int getC_BPartner_ID()
			{
				Assert.fail("getC_BPartner_ID() shall not be called");
				return -1;
			}

			@Override
			public int getC_BPartner_Location_ID()
			{
				Assert.fail("getC_BPartner_Location_ID() shall not be called");
				return -1;
			}
		};

		// NOTE: without DisplayVendorKeys=false makes no sense to test it because all our assumptions will be wrong
		Assert.assertEquals("Invalid DisplayVendorKeys for " + inventoryHUSelectModel, false, inventoryHUSelectModel.isDisplayVendorKeys());

		return inventoryHUSelectModel;

	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07732_Don%27t_show_Planning_HU_in_HU_Pos_%28109222775636%29
	 */
	@Test
	public void test_createHUQueryBuilder_Warehouse01()
	{
		huSelectModel_SelectedWarehouse_ID = warehouse01.getM_Warehouse_ID();
		Assert.assertEquals("Invalid HUs retrieved",
				// Expected HUs:
				Arrays.asList(
						hu_bp01_wh01_active
						, hu_bp01_wh01_picked
						//, hu_bp01_wh01_shipped :  #1062 #1327 The shipped HUs shall no longer be displayed in the inventory POS
						, hu_bp02_wh01_active
						, hu_bp03loc01_wh01_active
						, hu_bp03loc02_wh01_active
						),
				// Actual HUs:
				huSelectModel.createHUQueryBuilder().list()
				//
				);
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07732_Don%27t_show_Planning_HU_in_HU_Pos_%28109222775636%29
	 */
	@Test
	public void test_createHUQueryBuilder_Warehouse02()
	{
		huSelectModel_SelectedWarehouse_ID = warehouse02.getM_Warehouse_ID();
		Assert.assertEquals("Invalid HUs retrieved",
				// Expected HUs:
				Collections.emptyList(),
				// Actual HUs:
				huSelectModel.createHUQueryBuilder()
						.setErrorIfNoHUs(false, null) // we expect no HUs, so don't fail
						.list()
				//
				);
	}

	@Test(expected = AdempiereException.class)
	public void test_createHUQueryBuilder_NoWarehouseSelected()
	{
		huSelectModel_SelectedWarehouse_ID = -1;

		// shall throw exception because no warehouse is selected
		huSelectModel.createHUQueryBuilder();
	}

}
