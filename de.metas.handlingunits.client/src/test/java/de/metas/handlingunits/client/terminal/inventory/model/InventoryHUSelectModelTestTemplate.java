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

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;
import org.junit.Before;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.handlingunits.client.terminal.POSTerminalTestHelper;
import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.api.impl.InventoryHUFiltering;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.X_M_HU;

/**
 * Template class for testing {@link InventoryHUSelectModel} related things.
 *
 * @author tsa
 *
 */
public abstract class InventoryHUSelectModelTestTemplate
{
	protected POSTerminalTestHelper helper;

	//
	// Master data
	protected I_C_BPartner bpartner01;
	protected I_C_BPartner_Location bpartner01_loc01;
	protected I_C_BPartner bpartner02;
	protected I_C_BPartner_Location bpartner02_loc01;
	protected I_C_BPartner bpartner03;
	protected I_C_BPartner_Location bpartner03_loc01;
	protected I_C_BPartner_Location bpartner03_loc02;
	protected I_M_Warehouse warehouse01;
	protected I_M_Locator warehouse01_loc01;
	protected I_M_Warehouse warehouse02;
	protected I_M_Locator warehouse02_loc01;
	protected I_M_Warehouse warehouse03;
	protected I_M_Locator warehouse03_loc01;

	protected I_M_HU hu_bp01_wh01_active;
	protected I_M_HU hu_bp01_wh01_destroyed;
	protected I_M_HU hu_bp01_wh01_picked;
	protected I_M_HU hu_bp01_wh01_planning;
	protected I_M_HU hu_bp01_wh01_shipped;
	protected I_M_HU hu_bp02_wh01_active;
	protected I_M_HU hu_bp02_wh01_planning;
	protected I_M_HU hu_bp03loc01_wh01_active;
	protected I_M_HU hu_bp03loc02_wh01_active;

	@Before
	public final void init()
	{
		helper = new POSTerminalTestHelper();
		helper.init();

		final ITerminalContext terminalContext = helper.getTerminalContext();
		terminalContext.registerService(IPOSFiltering.class, new InventoryHUFiltering());

		//
		// Master data
		bpartner01 = helper.createBPartner("BP1");
		bpartner01_loc01 = helper.createBPartnerLocation(bpartner01);
		bpartner02 = helper.createBPartner("BP2");
		bpartner02_loc01 = helper.createBPartnerLocation(bpartner02);
		bpartner03 = helper.createBPartner("BP3");
		bpartner03_loc01 = helper.createBPartnerLocation(bpartner03);
		bpartner03_loc02 = helper.createBPartnerLocation(bpartner03);
		warehouse01 = helper.createWarehouse("WH01");
		warehouse01_loc01 = helper.createLocator(warehouse01, "locator01");
		warehouse02 = helper.createWarehouse("WH01");
		warehouse02_loc01 = helper.createLocator(warehouse02, "locator01");
		warehouse03 = helper.createWarehouse("WH01");
		warehouse03_loc01 = helper.createLocator(warehouse03, "locator01");
		helper.getPOSAccessBL()
				.setAvailableWarehouses(Arrays.asList(warehouse01, warehouse02, warehouse03));

		//
		// Create our HUs
		hu_bp01_wh01_active = createHU(bpartner01, bpartner01_loc01, warehouse01_loc01, X_M_HU.HUSTATUS_Active);
		hu_bp01_wh01_destroyed = createHU(bpartner01, bpartner01_loc01, warehouse01_loc01, X_M_HU.HUSTATUS_Destroyed);
		hu_bp01_wh01_destroyed.setIsActive(false); // NOTE: workaround
		InterfaceWrapperHelper.save(hu_bp01_wh01_destroyed);
		hu_bp01_wh01_picked = createHU(bpartner01, bpartner01_loc01, warehouse01_loc01, X_M_HU.HUSTATUS_Picked);
		hu_bp01_wh01_planning = createHU(bpartner01, bpartner01_loc01, warehouse01_loc01, X_M_HU.HUSTATUS_Planning);
		hu_bp01_wh01_shipped = createHU(bpartner01, bpartner01_loc01, warehouse01_loc01, X_M_HU.HUSTATUS_Shipped);
		hu_bp02_wh01_active = createHU(bpartner02, bpartner02_loc01, warehouse01_loc01, X_M_HU.HUSTATUS_Active);
		hu_bp02_wh01_planning = createHU(bpartner02, bpartner02_loc01, warehouse01_loc01, X_M_HU.HUSTATUS_Planning);

		hu_bp03loc01_wh01_active = createHU(bpartner03, bpartner03_loc01, warehouse01_loc01, X_M_HU.HUSTATUS_Active);
		hu_bp03loc02_wh01_active = createHU(bpartner03, bpartner03_loc02, warehouse01_loc01, X_M_HU.HUSTATUS_Active);

		afterInit();
	}

	protected void afterInit()
	{
		// nothing on this level
	}

	protected I_M_HU createHU(final I_C_BPartner bpartner, final I_C_BPartner_Location bpLocation, final I_M_Locator locator, final String huStatus)
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, helper.getTerminalContext());
		hu.setC_BPartner(bpartner);
		hu.setC_BPartner_Location(bpLocation);
		hu.setM_Locator(locator);
		hu.setHUStatus(huStatus);
		InterfaceWrapperHelper.save(hu);

		return hu;
	}

}
