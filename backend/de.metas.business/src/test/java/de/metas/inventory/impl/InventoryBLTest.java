package de.metas.inventory.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Locator;
import org.junit.Before;
import org.junit.Test;

import de.metas.inventory.IInventoryBL;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class InventoryBLTest
{
	private IInventoryBL inventoryBL = Services.get(IInventoryBL.class);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void assignToInventoryCounters_4Locators_4Counters()
	{
		final int numberOfCounters = 4;

		List<I_M_InventoryLine> inventoryLines = new ArrayList<>();

		final I_M_Locator locator1 = createLocator("locator1", 1000000);
		final I_M_InventoryLine inventoryLine1 = createInventoryLine(locator1);
		inventoryLines.add(inventoryLine1);

		final I_M_Locator locator2 = createLocator("locator2", 1000001);
		final I_M_InventoryLine inventoryLine2 = createInventoryLine(locator2);
		inventoryLines.add(inventoryLine2);

		final I_M_Locator locator3 = createLocator("locator3", 1000002);
		final I_M_InventoryLine inventoryLine3 = createInventoryLine(locator3);
		inventoryLines.add(inventoryLine3);

		final I_M_Locator locator4 = createLocator("locator4", 1000003);
		final I_M_InventoryLine inventoryLine4 = createInventoryLine(locator4);
		inventoryLines.add(inventoryLine4);

		inventoryBL.assignToInventoryCounters(inventoryLines, numberOfCounters);

		refresh(inventoryLine1);

		assertTrue("A".equals(inventoryLine1.getAssignedTo()));

		refresh(inventoryLine2);

		assertTrue("B".equals(inventoryLine2.getAssignedTo()));

		refresh(inventoryLine3);

		assertTrue("C".equals(inventoryLine3.getAssignedTo()));

		refresh(inventoryLine4);

		assertTrue("D".equals(inventoryLine4.getAssignedTo()));

	}

	@Test
	public void assignToInventoryCounters_3Locators_4Counters()
	{
		final int numberOfCounters = 4;

		List<I_M_InventoryLine> inventoryLines = new ArrayList<>();

		final I_M_Locator locator1 = createLocator("locator1", 1000000);
		final I_M_InventoryLine inventoryLine1 = createInventoryLine(locator1);
		inventoryLines.add(inventoryLine1);

		final I_M_Locator locator2 = createLocator("locator2", 1000001);
		final I_M_InventoryLine inventoryLine2 = createInventoryLine(locator2);
		inventoryLines.add(inventoryLine2);

		final I_M_Locator locator3 = createLocator("locator3", 1000002);
		final I_M_InventoryLine inventoryLine3 = createInventoryLine(locator3);
		inventoryLines.add(inventoryLine3);

		inventoryBL.assignToInventoryCounters(inventoryLines, numberOfCounters);

		refresh(inventoryLine1);

		assertTrue("A".equals(inventoryLine1.getAssignedTo()));

		refresh(inventoryLine2);

		assertTrue("B".equals(inventoryLine2.getAssignedTo()));

		refresh(inventoryLine3);

		assertTrue("C".equals(inventoryLine3.getAssignedTo()));

	}

	@Test
	public void assignToInventoryCounters_6Locators_5Counters()
	{
		final int numberOfCounters = 5;

		List<I_M_InventoryLine> inventoryLines = new ArrayList<>();

		final I_M_Locator locator1 = createLocator("locator1", 1000000);
		final I_M_InventoryLine inventoryLine1 = createInventoryLine(locator1);
		inventoryLines.add(inventoryLine1);

		final I_M_Locator locator2 = createLocator("locator2", 1000001);
		final I_M_InventoryLine inventoryLine2 = createInventoryLine(locator2);
		inventoryLines.add(inventoryLine2);

		final I_M_Locator locator3 = createLocator("locator3", 1000002);
		final I_M_InventoryLine inventoryLine3 = createInventoryLine(locator3);
		inventoryLines.add(inventoryLine3);

		final I_M_Locator locator4 = createLocator("locator4", 1000003);
		final I_M_InventoryLine inventoryLine4 = createInventoryLine(locator4);
		inventoryLines.add(inventoryLine4);

		final I_M_Locator locator5 = createLocator("locator5", 1000004);
		final I_M_InventoryLine inventoryLine5 = createInventoryLine(locator5);
		inventoryLines.add(inventoryLine5);

		final I_M_Locator locator6 = createLocator("locator6", 1000005);
		final I_M_InventoryLine inventoryLine6 = createInventoryLine(locator6);
		inventoryLines.add(inventoryLine6);



		inventoryBL.assignToInventoryCounters(inventoryLines, numberOfCounters);

		refresh(inventoryLine1);

		assertTrue("A".equals(inventoryLine1.getAssignedTo()));

		refresh(inventoryLine2);

		assertTrue("B".equals(inventoryLine2.getAssignedTo()));

		refresh(inventoryLine3);

		assertTrue("C".equals(inventoryLine3.getAssignedTo()));

		refresh(inventoryLine4);

		assertTrue("D".equals(inventoryLine4.getAssignedTo()));

		refresh(inventoryLine5);

		assertTrue("E".equals(inventoryLine5.getAssignedTo()));

		refresh(inventoryLine6);
		assertTrue("A".equals(inventoryLine6.getAssignedTo()));



	}

	private I_M_InventoryLine createInventoryLine(final I_M_Locator locator)
	{
		final I_M_InventoryLine inventoryLine = newInstance(I_M_InventoryLine.class);

		inventoryLine.setM_Locator_ID(locator.getM_Locator_ID());

		save(inventoryLine);

		return inventoryLine;
	}

	private I_M_Locator createLocator(final String value, final int id)
	{
		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Locator_ID(id);
		locator.setValue(value);
		save(locator);

		return locator;
	}

	@Test
	public void assignToInventoryCounters_4Locators_1Counter()
	{
		final int numberOfCounters = 1;

		List<I_M_InventoryLine> inventoryLines = new ArrayList<>();

		final I_M_Locator locator1 = createLocator("locator1", 1000000);
		final I_M_InventoryLine inventoryLine1 = createInventoryLine(locator1);
		inventoryLines.add(inventoryLine1);

		final I_M_Locator locator2 = createLocator("locator2", 1000001);
		final I_M_InventoryLine inventoryLine2 = createInventoryLine(locator2);
		inventoryLines.add(inventoryLine2);

		final I_M_Locator locator3 = createLocator("locator3", 1000002);
		final I_M_InventoryLine inventoryLine3 = createInventoryLine(locator3);
		inventoryLines.add(inventoryLine3);

		final I_M_Locator locator4 = createLocator("locator4", 1000003);
		final I_M_InventoryLine inventoryLine4 = createInventoryLine(locator4);
		inventoryLines.add(inventoryLine4);

		inventoryBL.assignToInventoryCounters(inventoryLines, numberOfCounters);

		refresh(inventoryLine1);

		assertTrue("A".equals(inventoryLine1.getAssignedTo()));

		refresh(inventoryLine2);

		assertTrue("A".equals(inventoryLine2.getAssignedTo()));

		refresh(inventoryLine3);

		assertTrue("A".equals(inventoryLine3.getAssignedTo()));

		refresh(inventoryLine4);

		assertTrue("A".equals(inventoryLine4.getAssignedTo()));

	}

	@Test
	public void assignToInventoryCounters_3Locators_2Counters_MultipleLines()
	{
		final int numberOfCounters = 2;

		List<I_M_InventoryLine> inventoryLines = new ArrayList<>();

		final I_M_Locator locator1 = createLocator("locator1", 1000000);
		final I_M_Locator locator2 = createLocator("locator2", 1000001);
		final I_M_Locator locator3 = createLocator("locator3", 1000002);

		final I_M_InventoryLine inventoryLine1 = createInventoryLine(locator1);

		inventoryLines.add(inventoryLine1);

		final I_M_InventoryLine inventoryLine4 = createInventoryLine(locator1);
		inventoryLines.add(inventoryLine4);

		final I_M_InventoryLine inventoryLine5 = createInventoryLine(locator1);
		inventoryLines.add(inventoryLine5);

		final I_M_InventoryLine inventoryLine6 = createInventoryLine(locator1);
		inventoryLines.add(inventoryLine6);

		final I_M_InventoryLine inventoryLine2 = createInventoryLine(locator2);
		inventoryLines.add(inventoryLine2);

		final I_M_InventoryLine inventoryLine7 = createInventoryLine(locator2);
		inventoryLines.add(inventoryLine7);

		final I_M_InventoryLine inventoryLine8 = createInventoryLine(locator2);
		inventoryLines.add(inventoryLine8);

		final I_M_InventoryLine inventoryLine3 = createInventoryLine(locator3);
		inventoryLines.add(inventoryLine3);

		final I_M_InventoryLine inventoryLine9 = createInventoryLine(locator3);
		inventoryLines.add(inventoryLine9);

		final I_M_InventoryLine inventoryLine10 = createInventoryLine(locator3);
		inventoryLines.add(inventoryLine10);

		inventoryBL.assignToInventoryCounters(inventoryLines, numberOfCounters);

		refresh(inventoryLine1);

		assertTrue("A".equals(inventoryLine1.getAssignedTo()));

		refresh(inventoryLine4);

		assertTrue("A".equals(inventoryLine4.getAssignedTo()));

		refresh(inventoryLine5);

		assertTrue("A".equals(inventoryLine5.getAssignedTo()));

		refresh(inventoryLine6);

		assertTrue("A".equals(inventoryLine6.getAssignedTo()));

		refresh(inventoryLine2);

		assertTrue("B".equals(inventoryLine2.getAssignedTo()));

		refresh(inventoryLine7);

		assertTrue("B".equals(inventoryLine7.getAssignedTo()));

		refresh(inventoryLine8);

		assertTrue("B".equals(inventoryLine8.getAssignedTo()));

		refresh(inventoryLine3);

		assertTrue("A".equals(inventoryLine3.getAssignedTo()));

		refresh(inventoryLine9);

		assertTrue("A".equals(inventoryLine9.getAssignedTo()));

		refresh(inventoryLine10);

		assertTrue("A".equals(inventoryLine10.getAssignedTo()));

	}

}
