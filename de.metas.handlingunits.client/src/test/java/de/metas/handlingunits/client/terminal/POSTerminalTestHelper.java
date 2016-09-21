package de.metas.handlingunits.client.terminal;

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


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.TerminalContextFactory;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.service.IPOSAccessBL;
import de.metas.adempiere.service.impl.MockedPOSAccessBL;
import de.metas.handlingunits.model.I_M_Locator;

/**
 * Helper class used when testing HU POS terminals.
 *
 * @author tsa
 *
 */
public class POSTerminalTestHelper
{
	private boolean initAdempiere = true;
	private boolean initialized = false;
	private ITerminalContext terminalContext;
	private MockedPOSAccessBL posAccessBL;

	public POSTerminalTestHelper()
	{
		super();
	}

	public void setInitAdempiere(final boolean initAdempiere)
	{
		Check.assume(!initialized, "helper not initialized");
		this.initAdempiere = initAdempiere;
	}

	public void init()
	{
		if (initialized)
		{
			return;
		}

		if (initAdempiere)
		{
			AdempiereTestHelper.get().init();
		}

		posAccessBL = new MockedPOSAccessBL();
		Services.registerService(IPOSAccessBL.class, posAccessBL);


		terminalContext = TerminalContextFactory.get().createContextAndRefs().getLeft();

		//
		initialized = true;
	}

	public ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	public MockedPOSAccessBL getPOSAccessBL()
	{
		return posAccessBL;
	}

	public I_M_Warehouse createWarehouse(final String name)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class, terminalContext);
		warehouse.setValue(name);
		warehouse.setName(name);
		InterfaceWrapperHelper.save(warehouse);
		return warehouse;
	}

	public I_M_Locator createLocator(final I_M_Warehouse warehouse, final String name)
	{
		final I_M_Locator locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class, terminalContext);
		locator.setM_Warehouse(warehouse);
		locator.setAD_Org_ID(warehouse.getAD_Org_ID());
		locator.setValue(warehouse.getValue() + "_" + name);
		InterfaceWrapperHelper.save(locator);
		return locator;
	}

	public I_C_BPartner createBPartner(final String name)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, terminalContext);
		bpartner.setValue(name);
		bpartner.setName(name);
		InterfaceWrapperHelper.save(bpartner);
		return bpartner;
	}

	public I_C_BPartner_Location createBPartnerLocation(final I_C_BPartner bpartner)
	{
		final I_C_BPartner_Location bpartnerLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, terminalContext);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		InterfaceWrapperHelper.save(bpartnerLocation);
		return bpartnerLocation;
	}

}
