package de.metas.handlingunits.client.terminal.aggregate.model;

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
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.IHULockBL;
import de.metas.handlingunits.client.terminal.inventory.model.InventoryHUSelectModel;
import de.metas.handlingunits.client.terminal.inventory.model.InventoryHUSelectModelTestTemplate;
import de.metas.handlingunits.client.terminal.inventory.view.BPartnerLocationKeyLayoutRenderer;
import de.metas.handlingunits.client.terminal.select.model.BPartnerLocationKey;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.lock.api.LockOwner;

/**
 * Test {@link AggregateHUSelectModel}
 *
 * @author tsa
 *
 */
public class AggregateHUSelectModelTest extends InventoryHUSelectModelTestTemplate
{
	private IHULockBL huLockBL;
	//
	private AggregateHUSelectModel huSelectModel;
	private Integer huSelectModel_SelectedWarehouse_ID = null;
	private Integer huSelectModel_SelectedBPartner_ID = null;
	private Integer huSelectModel_SelectedBPartnerLocation_ID = null;

	@Override
	protected void afterInit()
	{
		huLockBL = Services.get(IHULockBL.class);
		
		huSelectModel = createHUSelectModel();
	}

	/** @return Object under test */
	protected AggregateHUSelectModel createHUSelectModel()
	{
		final AggregateHUSelectModel aggregateHUSelectModel = new AggregateHUSelectModel(helper.getTerminalContext())
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
				if (huSelectModel_SelectedBPartner_ID != null)
				{
					return huSelectModel_SelectedBPartner_ID;
				}
				return super.getC_BPartner_ID();
			}

			@Override
			public int getC_BPartner_Location_ID()
			{
				if (huSelectModel_SelectedBPartnerLocation_ID != null)
				{
					return huSelectModel_SelectedBPartnerLocation_ID;
				}
				return super.getC_BPartner_Location_ID();
			}

		};

		// NOTE: without DisplayVendorKeys makes no sense to test it
		Assert.assertEquals("Invalid DisplayVendorKeys for " + aggregateHUSelectModel, true, aggregateHUSelectModel.isDisplayVendorKeys());

		return aggregateHUSelectModel;

	}

	/**
	 * Configure after picking locators
	 *
	 * NOTE: we expect only the HUs from after-picking locators to be considered
	 *
	 * @param isAfterPickingLocators
	 */
	protected void setAfterPickingLocators(final boolean isAfterPickingLocators)
	{
		warehouse01_loc01.setIsAfterPickingLocator(isAfterPickingLocators);
		InterfaceWrapperHelper.save(warehouse01_loc01);
		//
		warehouse02_loc01.setIsAfterPickingLocator(isAfterPickingLocators);
		InterfaceWrapperHelper.save(warehouse02_loc01);

		warehouse03_loc01.setIsAfterPickingLocator(isAfterPickingLocators);
		InterfaceWrapperHelper.save(warehouse03_loc01);
	}

	@Test
	public void test_createHUQueryBuilder_WithAfterPickingLocators_Warehouse01_BPartner01()
	{
		setAfterPickingLocators(true);
		huSelectModel_SelectedWarehouse_ID = warehouse01.getM_Warehouse_ID();
		huSelectModel_SelectedBPartner_ID = bpartner01.getC_BPartner_ID();
		huSelectModel_SelectedBPartnerLocation_ID = bpartner01_loc01.getC_BPartner_Location_ID();
		Assert.assertEquals("Invalid HUs retrieved",
				// Expected HUs:
				Arrays.asList(
						hu_bp01_wh01_active,
						hu_bp01_wh01_picked,
						hu_bp01_wh01_shipped
						),
				// Actual HUs:
				huSelectModel.createHUQueryBuilder().list()
				//
				);
	}

	@Test
	public void test_createHUQueryBuilder_WithAfterPickingLocators_Warehouse01_BPartner02()
	{
		setAfterPickingLocators(true);
		huSelectModel_SelectedWarehouse_ID = warehouse01.getM_Warehouse_ID();
		huSelectModel_SelectedBPartner_ID = bpartner02.getC_BPartner_ID();
		huSelectModel_SelectedBPartnerLocation_ID = bpartner02_loc01.getC_BPartner_Location_ID();
		Assert.assertEquals("Invalid HUs retrieved",
				// Expected HUs:
				Arrays.asList(
						hu_bp02_wh01_active
						),
				// Actual HUs:
				huSelectModel.createHUQueryBuilder().list()
				//
				);
	}

	@Test
	public void test_createHUQueryBuilder_WithAfterPickingLocators_Warehouse01_BPartner03_BPLocation01()
	{
		setAfterPickingLocators(true);
		huSelectModel_SelectedWarehouse_ID = warehouse01.getM_Warehouse_ID();
		huSelectModel_SelectedBPartner_ID = bpartner03.getC_BPartner_ID();
		huSelectModel_SelectedBPartnerLocation_ID = bpartner03_loc01.getC_BPartner_Location_ID();
		Assert.assertEquals("Invalid HUs retrieved",
				// Expected HUs:
				Arrays.asList(
						hu_bp03loc01_wh01_active
						),
				// Actual HUs:
				huSelectModel.createHUQueryBuilder().list()
				//
				);
	}

	@Test
	public void test_createHUQueryBuilder_WithAfterPickingLocators_Warehouse01_BPartner03_BPLocation02()
	{
		setAfterPickingLocators(true);
		huSelectModel_SelectedWarehouse_ID = warehouse01.getM_Warehouse_ID();
		huSelectModel_SelectedBPartner_ID = bpartner03.getC_BPartner_ID();
		huSelectModel_SelectedBPartnerLocation_ID = bpartner03_loc02.getC_BPartner_Location_ID();
		Assert.assertEquals("Invalid HUs retrieved",
				// Expected HUs:
				Arrays.asList(
						hu_bp03loc02_wh01_active
						),
				// Actual HUs:
				huSelectModel.createHUQueryBuilder().list()
				//
				);
	}

	@Test(expected = AdempiereException.class)
	public void test_createHUQueryBuilder_WithAfterPickingLocators_Warehouse01_BPartner03_NoBPLocation()
	{
		setAfterPickingLocators(true);
		huSelectModel_SelectedWarehouse_ID = warehouse01.getM_Warehouse_ID();
		huSelectModel_SelectedBPartner_ID = bpartner03.getC_BPartner_ID();
		huSelectModel_SelectedBPartnerLocation_ID = -1; // no location

		// this one shall fail because no location was defined
		huSelectModel.createHUQueryBuilder();
	}

	@Test
	public void test_createHUQueryBuilder_WithAfterPickingLocators_Warehouse02_BPartner01()
	{
		setAfterPickingLocators(true);
		huSelectModel_SelectedWarehouse_ID = warehouse02.getM_Warehouse_ID();
		huSelectModel_SelectedBPartner_ID = bpartner01.getC_BPartner_ID();
		huSelectModel_SelectedBPartnerLocation_ID = bpartner01_loc01.getC_BPartner_Location_ID();
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

	@Test
	public void test_createHUQueryBuilder_NoAfterPickingLocators_Warehouse01_BPartner01()
	{
		setAfterPickingLocators(false);
		huSelectModel_SelectedWarehouse_ID = warehouse01.getM_Warehouse_ID();
		huSelectModel_SelectedBPartner_ID = bpartner01.getC_BPartner_ID();
		huSelectModel_SelectedBPartnerLocation_ID = bpartner01_loc01.getC_BPartner_Location_ID();
		Assert.assertEquals("Invalid HUs retrieved",
				// Expected HUs: none because there are no after picking locators
				Collections.emptyList(),
				// Actual HUs:
				huSelectModel.createHUQueryBuilder()
						.setErrorIfNoHUs(false, null) // we expect no HUs, so don't fail
						.list()
				//
				);
	}

	@Test
	public void test_createHUQueryBuilder_NoAfterPickingLocators_Warehouse01_BPartner02()
	{
		setAfterPickingLocators(false);
		huSelectModel_SelectedWarehouse_ID = warehouse01.getM_Warehouse_ID();
		huSelectModel_SelectedBPartner_ID = bpartner02.getC_BPartner_ID();
		huSelectModel_SelectedBPartnerLocation_ID = bpartner02_loc01.getC_BPartner_Location_ID();
		Assert.assertEquals("Invalid HUs retrieved",
				// Expected HUs: none because there are no after picking locators
				Collections.emptyList(),
				// Actual HUs:
				huSelectModel.createHUQueryBuilder()
						.setErrorIfNoHUs(false, null) // we expect no HUs, so don't fail
						.list()
				//
				);
	}

	@Test
	public void test_createHUQueryBuilder_NoAfterPickingLocators_Warehouse02_BPartner01()
	{
		setAfterPickingLocators(false);
		huSelectModel_SelectedWarehouse_ID = warehouse02.getM_Warehouse_ID();
		huSelectModel_SelectedBPartner_ID = bpartner01.getC_BPartner_ID();
		huSelectModel_SelectedBPartnerLocation_ID = bpartner01_loc01.getC_BPartner_Location_ID();
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

	/**
	 * Test if {@link InventoryHUSelectModel#DYNATTR_CountLockedHUs} is set correctly.
	 *
	 * NOTE: in real life, the aggregated values will be calculated directly on SQL level but at least we make sure the logic is OK
	 */
	@Test
	public void test_refreshBPartnerLocationKeysIfNeeded_OneLockedHU()
	{
		setAfterPickingLocators(true); // just to make sure; if the HUs are not on after-picking locators, we will get no result

		// Create our locked HUs
		{
			final LockOwner lockOwner = LockOwner.forOwnerName("test");
			
			final I_M_HU hu_bp03loc02_wh01_activeButLocked01 = createHU(bpartner03, bpartner03_loc02, warehouse01_loc01, X_M_HU.HUSTATUS_Active);
			huLockBL.lock(hu_bp03loc02_wh01_activeButLocked01, lockOwner);
			//
			final I_M_HU hu_bp03loc02_wh01_activeButLocked02 = createHU(bpartner03, bpartner03_loc02, warehouse01_loc01, X_M_HU.HUSTATUS_Active);
			huLockBL.lock(hu_bp03loc02_wh01_activeButLocked02, lockOwner);
		}

		// Configure HU Select Panel
		huSelectModel_SelectedWarehouse_ID = warehouse01.getM_Warehouse_ID();
		huSelectModel_SelectedBPartner_ID = bpartner03.getC_BPartner_ID();
		huSelectModel_SelectedBPartnerLocation_ID = null; // no location selected

		//
		// Load the BPartnerLocationKeys for current selected partner (bpartner03)
		huSelectModel.refreshBPartnerLocationKeysIfNeeded();
		final List<BPartnerLocationKey> loadedBPartnerLocationKeys = huSelectModel.getBPartnerLocationKeyLayout().getKeys(BPartnerLocationKey.class);
		Assert.assertEquals("Invalid BPartnerLocations loaded count", 2, loadedBPartnerLocationKeys.size());

		//
		// Test BPartner03 Location 1 (no locked HUs here)
		{
			final BPartnerLocationKey loaded_bpartner03_loc01_key = getBPartnerLocationKeyByBPartnerLocationId(loadedBPartnerLocationKeys, bpartner03_loc01.getC_BPartner_Location_ID());
			final I_C_BPartner_Location loaded_bpartner03_loc01 = loaded_bpartner03_loc01_key.getC_BPartner_Location();

			// Check the CountLockedHUs
			final Integer countLockedHUs = InventoryHUSelectModel.DYNATTR_CountLockedHUs.getValue(loaded_bpartner03_loc01);
			Assert.assertEquals("Invalid CountLockedHUs", (Integer)0, countLockedHUs);

			// Test Renderer
			Assert.assertEquals("Invalid rendered background color",
					BPartnerLocationKeyLayoutRenderer.COLOR_DEFAULT,
					BPartnerLocationKeyLayoutRenderer.instance.getBackgroundColor(loaded_bpartner03_loc01_key));
		}

		//
		// Test BPartner03 Location02 (where we have our locked HUs)
		{
			final BPartnerLocationKey loaded_bpartner03_loc02_key = getBPartnerLocationKeyByBPartnerLocationId(loadedBPartnerLocationKeys, bpartner03_loc02.getC_BPartner_Location_ID());
			final I_C_BPartner_Location loaded_bpartner03_loc02 = loaded_bpartner03_loc02_key.getC_BPartner_Location();

			// Check the CountLockedHUs
			final Integer countLockedHUs = InventoryHUSelectModel.DYNATTR_CountLockedHUs.getValue(loaded_bpartner03_loc02);
			Assert.assertEquals("Invalid CountLockedHUs", (Integer)2, countLockedHUs);

			// Test Renderer
			Assert.assertEquals("Invalid rendered background color",
					BPartnerLocationKeyLayoutRenderer.COLOR_HaveLockedHUs,
					BPartnerLocationKeyLayoutRenderer.instance.getBackgroundColor(loaded_bpartner03_loc02_key));
		}
	}

	private static final BPartnerLocationKey getBPartnerLocationKeyByBPartnerLocationId(final List<BPartnerLocationKey> bpartnerLocationKeys, final int bpartnerLocationId)
	{
		return ListUtils.singleElement(bpartnerLocationKeys, new Predicate<BPartnerLocationKey>()
		{
			@Override
			public boolean evaluate(final BPartnerLocationKey value)
			{
				return value.getC_BPartner_Location_ID() == bpartnerLocationId;
			}
		});
	}

}
