package de.metas.handlingunits.receiptschedule.impl;

import static org.junit.Assert.assertEquals;

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
import java.util.List;

import de.metas.handlingunits.expectations.HUAssignmentExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import org.junit.jupiter.api.Test;

/**
 * Asserts that RS allocations and deallocations work as intended with HUs and weight attributes
 *
 * @author al
 */
public class ReceiptScheduleHUAllocationsTest extends AbstractRSAllocationWithWeightAttributeTest
{
	@Test
	public void testAllocateAgainWithDeallocation()
	{
		final List<I_M_HU> paloxes = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430 kg
				materialItemProductTomato_430,
				receiptSchedule.getQtyOrdered(), // Qty
				weightGrossPaloxe);
		assertEquals("Invalid amount of paloxes created", 10, paloxes.size());

		initReceiptScheduleAllocations(paloxes);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation);

		rsNetWeightAdjuster.addReceiptSchedule(receiptSchedule);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation);

		//
		// Get old HU allocations
		final List<I_M_ReceiptSchedule_Alloc> oldHUAllocations = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, huContext.getTrxName());
		assertEquals("Invalid amount of Old HU Allocations", 10, oldHUAllocations.size());

		final boolean deleteOldTUAllocations = true;
		recreateAllocationsForTopLevelTUs(paloxes, deleteOldTUAllocations);

		//
		// Get new HU allocations
		final List<I_M_ReceiptSchedule_Alloc> newHUAllocations = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, huContext.getTrxName());
		assertEquals("Invalid amount of New HU Allocations", 10, newHUAllocations.size());
	}

	@Test
	public void testAllocateAgainWithoutDeallocation()
	{
		final List<I_M_HU> paloxes = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430 kg
				materialItemProductTomato_430,
				receiptSchedule.getQtyOrdered(), // Qty
				weightGrossPaloxe);
		assertEquals("Invalid amount of paloxes created", 10, paloxes.size());

		initReceiptScheduleAllocations(paloxes);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation);

		rsNetWeightAdjuster.addReceiptSchedule(receiptSchedule);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation);

		//
		// Get old HU allocations
		final List<I_M_ReceiptSchedule_Alloc> oldHUAllocations = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, huContext.getTrxName());
		assertEquals("Invalid amount of Old HU Allocations", 10, oldHUAllocations.size());

		final boolean deleteOldTUAllocations = false; // do not delete old TU allocations and allocate again
		recreateAllocationsForTopLevelTUs(paloxes, deleteOldTUAllocations);

		//
		// Get new HU allocations
		final List<I_M_ReceiptSchedule_Alloc> newHUAllocations = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, huContext.getTrxName());
		assertEquals("Invalid amount of New HU Allocations", 10 + 10, newHUAllocations.size());
	}

	@Test
	public void testAllocateAgainWithDeallocation_afterSplit()
	{
		final List<I_M_HU> paloxes = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430 kg
				materialItemProductTomato_430,
				receiptSchedule.getQtyOrdered(), // Qty
				weightGrossPaloxe);
		assertEquals("Invalid amount of paloxes created", 10, paloxes.size());

		initReceiptScheduleAllocations(paloxes);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation);

		// Call NetWeightAdjuster to adjust our HUs
		// We assume nothing happens because there is nothing to adjust.
		rsNetWeightAdjuster.addReceiptSchedule(receiptSchedule);
		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation);

		//
		// Get old HU allocations (before split)
		final List<I_M_ReceiptSchedule_Alloc> oldHUAllocations = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, huContext.getTrxName());
		assertEquals("Invalid amount of Old HU Allocations", 10, oldHUAllocations.size());

		//
		// TODO: do we need this?
		final boolean deleteOldTUAllocations = true;
		recreateAllocationsForTopLevelTUs(paloxes, deleteOldTUAllocations);

		//
		// Split first Paloxe
		final I_M_HU paloxe1 = paloxes.get(0);
		final List<I_M_HU> createdHUs = helper.newSplitBuilder(huContext,
				paloxe1,
				pTomatoId,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source TU
				BigDecimal.valueOf(3), // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_10, // TU item x 10
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				)
				.split();

		//
		// Get new HU allocations (after split)
		final List<I_M_ReceiptSchedule_Alloc> newHUAllocations = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, huContext.getTrxName());
		assertEquals("Invalid amount of New HU Allocations", 16, newHUAllocations.size());

		//
		// Assume RSAs have precisely those HU_QtyAllocated values
		final Integer[] huQtyAllocationExpectations = new Integer[] {
				// Before Split: 10x430items
				430, // 1
				430,// 2
				430,// 3
				430,// 4
				430,// 5
				430,// 6
				430,// 7
				430,// 8
				430,// 9
				430,// 10
				// After split:
				-10, // deallocation
				10, // allocation
				-10, // deallocation
				10, // allocation
				-10,// deallocation
				10				// allocation
		};
		assertEquals("Invalid number of RSAs after split", huQtyAllocationExpectations.length, newHUAllocations.size());
		for (int i = 0; i < newHUAllocations.size(); i++)
		{
			final int qtyAllocatedExpected = huQtyAllocationExpectations[i];

			final I_M_ReceiptSchedule_Alloc alloc = newHUAllocations.get(i);
			final int qtyAllocated = alloc.getHU_QtyAllocated().intValueExact();

			//
			// Check between expectations and allocated qty
			assertEquals("Invalid HU-allocated Qty on position " + i, qtyAllocatedExpected, qtyAllocated);

			// final I_M_HU luHU = alloc.getM_LU_HU();
			// final I_M_HU tuHU = alloc.getM_TU_HU();
			final I_M_HU vhu = alloc.getVHU();
			final I_M_HU topLevelHU = handlingUnitsBL.getTopLevelParent(vhu);

			HUAssignmentExpectation.newExpectation()
					.tableAndRecordIdFromModel(receiptSchedule)
					.luHU(null)
					.tuHU(null)// FIXME: atm is not assigning on LU/TU Level
					.hu(topLevelHU)
					.assertExpected("After split HU shall also be assigned to receipt schedule");

		}

		//
		// Check HU Assignments for new HUs
		for (final I_M_HU hu : createdHUs)
		{
			HUAssignmentExpectation.newExpectation()
					.tableAndRecordIdFromModel(receiptSchedule)
					.luHU(null)
					.tuHU(null)// FIXME: atm is not assigning on LU/TU Level
					.hu(hu)
					.assertExpected("After split HU shall also be assigned to receipt schedule");
		}
	}
}
