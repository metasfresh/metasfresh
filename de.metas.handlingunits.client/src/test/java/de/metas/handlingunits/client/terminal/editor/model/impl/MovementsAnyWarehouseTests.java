package de.metas.handlingunits.client.terminal.editor.model.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;

/*
 * #%L
 * de.metas.handlingunits.client
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

public class MovementsAnyWarehouseTests
{
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	
	
	@Rule
	public AdempiereTestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testRetrieveWarehousesWhichContainNoneOf()
	{

		final I_M_Warehouse warehouse1 = createWarehouse("Warehouse1");
		final I_M_Warehouse warehouse2 = createWarehouse("Warehouse2");
		final I_M_Warehouse warehouse3 = createWarehouse("Warehouse3");

		final I_M_Locator locator1 = createLocator(warehouse1, "Locator1");
		final I_M_Locator locator2 = createLocator(warehouse2, "Locator2");
		final I_M_Locator locator3 = createLocator(warehouse3, "Locator3");

		final I_M_HU hu1 = createHU(locator1);
		final I_M_HU hu2 = createHU(locator2);
		final I_M_HU hu3 = createHU(locator3);

		List<I_M_HU> hus = new ArrayList<>();
		hus.add(hu1);

		final List<I_M_Warehouse> warehousesUnlessOfHUs1 = handlingUnitsDAO.retrieveWarehousesWhichContainNoneOf(hus);

		assertThat(warehousesUnlessOfHUs1).containsOnly(warehouse2, warehouse3);

		hus.add(hu2);

		final List<I_M_Warehouse> warehousesUnlessOfHUs2 = handlingUnitsDAO.retrieveWarehousesWhichContainNoneOf(hus);

		assertThat(warehousesUnlessOfHUs2).containsOnly(warehouse3);

		hus.add(hu3);

		final List<I_M_Warehouse> warehousesUnlessOfHUs3 = handlingUnitsDAO.retrieveWarehousesWhichContainNoneOf(hus);

		assertThat(warehousesUnlessOfHUs3).isEmpty();

	}

	private I_M_HU createHU(I_M_Locator locator)
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		hu.setM_Locator(locator);
		InterfaceWrapperHelper.save(hu);

		return hu;
	}

	private I_M_Locator createLocator(final I_M_Warehouse warehouse1, final String value)
	{
		final I_M_Locator locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		locator.setM_Warehouse(warehouse1);
		locator.setValue(value);

		InterfaceWrapperHelper.save(locator);
		return locator;
	}

	private I_M_Warehouse createWarehouse(final String name)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setName(name);

		InterfaceWrapperHelper.save(warehouse);
		return warehouse;
	}
}
