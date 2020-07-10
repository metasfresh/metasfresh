package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOLookupMapRestorePoint;
import org.adempiere.test.MemoryInfoSnapshot;
import org.adempiere.test.MemoryTestHelper;
import org.compiere.model.I_C_BPartner;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled // skip it because this test is kind of not reliable atm
public class HandlingUnits_MemoryLeaks_Test extends AbstractHUTest
{
	// NOTE: in most of the cases i got an increase <=0.05 but to be sure this test is not failing randomly
	private final BigDecimal memoryUsagePercentIncreaseTolerance = new BigDecimal("1");

	/** TU */
	private I_M_HU_PI huDefIFCO;
	/** LU */
	private I_M_HU_PI huDefPalet;

	private static final int COUNT_IFCOs_Per_Palet = 5;
	private static final int COUNT_Tomatoes_Per_IFCO = 10;

	@Override
	protected HUTestHelper createHUTestHelper()
	{
		return HUTestHelper.newInstanceOutOfTrx();
	}

	@Override
	protected void initialize()
	{
		//
		// Handling Units Definition
		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, pTomatoId, BigDecimal.valueOf(COUNT_Tomatoes_Per_IFCO), uomEach);
		}
		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			final I_C_BPartner bpartner = null;
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, BigDecimal.valueOf(COUNT_IFCOs_Per_Palet), bpartner);
		}
	}

	/**
	 * Makes sure {@link #runTestNow()} does not increase the memory more then {@link #memoryUsagePercentIncreaseTolerance}.
	 */
	@Test
	public void test()
	{
		final MemoryTestHelper memory = new MemoryTestHelper();
		final POJOLookupMapRestorePoint dbRestorePoint = POJOLookupMap.get().createRestorePoint();

		//
		// Warm-up
		// Aim:
		// * all cached values were loaded
		// * we will expect no memory usage variances after this point
		for (int runNo = 1; runNo < 3; runNo++)
		{
			runTestNow();
			dbRestorePoint.restore();
		}

		//
		// Now we are actually running it and we assert the memory usage is not increasing
		for (int runNo = 1; runNo < 5; runNo++)
		{
			runTestNow();

			dbRestorePoint.restore();
			memory.runGarbageCollector();

			//
			// We use the first runs for memory/cache warming-up
			if (runNo <= 3)
			{
				memory.newMemorySnapshotAndSetAsCurrent();
				continue;
			}

			//
			// Test memory usage
			final MemoryInfoSnapshot memorySnapshot = memory.newMemorySnapshot();
			// System.out.println("Run #" + runNo);
			// System.out.println("Now: " + memorySnapshot);
			// System.out.println("Ref: " + memory.currentMemorySnapshot());
			// System.out.println("Usage variance: " + memorySnapshot.calculateDeltaUsageFrom(memory.currentMemorySnapshot()));
			memory.assertMemoryUsageNotIncreased("Run #" + runNo, memorySnapshot, memoryUsagePercentIncreaseTolerance);
		}
	}

	private final void runTestNow()
	{
		final IHUContext huContext = helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None);
		final BigDecimal qtyToLoadBD = BigDecimal.valueOf(1 * COUNT_IFCOs_Per_Palet * COUNT_Tomatoes_Per_IFCO); // 1palet
		helper.createHUs(huContext, huDefPalet, pTomatoId, qtyToLoadBD, uomEach);
	}
}
