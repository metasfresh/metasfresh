package de.metas.handlingunits.receiptschedule.impl;

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
import java.util.Arrays;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.junit.Test;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.quantity.Quantity;

/**
 * Asserts correct behavior of NET Weight when adjusting ReceiptSchedule Quantity
 *
 * @author al
 */
public class HUReceiptScheduleWeightNetAdjusterTest extends AbstractRSAllocationWithWeightAttributeTest
{
	@Override
	protected void afterInitialize()
	{
		super.afterInitialize();

		//
		// Make sure everything is OK configured:
		// i.e. setting the Product Weight is needed for org.adempiere.mm.attributes.spi.impl.WeightGenerateHUTrxListener
		// NOTE: we need this configuration to catch the issue from http://dewiki908/mediawiki/index.php/08728_HU_Weight_Net_changes_after_Material_Receipt_%28107972107210%29
		pTomato.setC_UOM(uomKg);
		pTomato.setWeight(new BigDecimal("1"));
		InterfaceWrapperHelper.save(pTomato);
	}

	/**
	 * Utopic use-case: all weights are received as expected/promised for each Paloxe
	 */
	@Test
	public void testWeightExactDelivery()
	{
		final List<I_M_HU> paloxes = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430 kg
				materialItemProductTomato_430,
				receiptSchedule.getQtyOrdered(), // Qty
				weightGrossPaloxe);
		initReceiptScheduleAllocations(paloxes);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation);

		rsNetWeightAdjuster.addReceiptSchedule(receiptSchedule);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation);
	}

	/**
	 * Under-delivery (weight-wise) on one Paloxe
	 */
	@Test
	public void testWeightUnderDelivery_1Paloxe()
	{
		final List<I_M_HU> paloxes = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430 kg
				materialItemProductTomato_430,
				receiptSchedule.getQtyOrdered(), // Qty
				weightGrossPaloxe);
		initReceiptScheduleAllocations(paloxes);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation);

		//
		// Alter weight on the first Paloxe -> make it 500 instead of 505 (under-delivery of 5)
		final I_M_HU paloxe1 = paloxes.get(0);
		final IAttributeStorage attributeStorage1 = attributeStorageFactory.getAttributeStorage(paloxe1);
		attributeStorage1.setValue(attr_WeightGross, BigDecimal.valueOf(500));

		rsNetWeightAdjuster.addReceiptSchedule(receiptSchedule);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation, newHUWeightsExpectation("500", "425", "75", "0"));
	}

	/**
	 * Over-delivery (weight-wise) on one Paloxe
	 */
	@Test
	public void testWeightOverDelivery_1Paloxe()
	{
		final List<I_M_HU> paloxes = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430 kg
				materialItemProductTomato_430,
				receiptSchedule.getQtyOrdered(), // Qty
				weightGrossPaloxe);
		initReceiptScheduleAllocations(paloxes);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation);

		//
		// Alter weight on the first Paloxe -> make it 510 instead of 505 (over-delivery of 5)
		final I_M_HU paloxe1 = paloxes.get(0);
		final IAttributeStorage attributeStorage1 = attributeStorageFactory.getAttributeStorage(paloxe1);
		attributeStorage1.setValue(attr_WeightGross, BigDecimal.valueOf(510));

		rsNetWeightAdjuster.addReceiptSchedule(receiptSchedule);

		assertTradingUnitsWeightExpectations(paloxes, standardPaloxeWeightExpectation, newHUWeightsExpectation("510", "435", "75", "0"));
	}

	/**
	 * Over-delivery (weight-wise) on one Paloxe
	 */
	@Test
	public void testWeightOverDelivery_1Paloxe_MultipleReceiptSchedules()
	{
		// make sure we are not using the predefined receipt schedule
		InterfaceWrapperHelper.delete(receiptSchedule);
		receiptSchedule = null;

		final List<I_M_ReceiptSchedule> receiptSchedules = Arrays.asList(
				createReceiptSchedule(BigDecimal.valueOf(230)),
				createReceiptSchedule(BigDecimal.valueOf(4300))
				);

		final I_C_UOM cuUOM = receiptSchedules.get(0).getC_UOM();

		final ReceiptScheduleHUGenerator huGenerator = ReceiptScheduleHUGenerator.newInstance(huContext)
				.setQtyToAllocateTarget(new Quantity(BigDecimal.valueOf(4300), cuUOM)) // i.e. 10xPaloxe
				.addM_ReceiptSchedules(receiptSchedules);
		huGenerator.getLUTUConfigurationManager()
				.setCurrentLUTUConfigurationAndSave(createM_HU_LUTU_Configuration_ForTU(materialItemProductTomato_430));
		final List<I_M_HU> paloxes = huGenerator.generateWithinOwnTransaction();

		//
		// Change WeightGross to our generated HUs.
		// Make sure their are set as expected.
		setWeightGrossToStandardAndTest(paloxes);

		//
		// Alter weight on the first Paloxe -> make it 510 instead of 505 (over-delivery of 5)
		final I_M_HU paloxe1 = paloxes.get(0);
		final IAttributeStorage paloxe1_attributes = attributeStorageFactory.getAttributeStorage(paloxe1);
		paloxe1_attributes.setValue(attr_WeightGross, BigDecimal.valueOf(510));

		//
		// Call the NetWeight adjuster (i.e. service under test)
		for (final I_M_ReceiptSchedule receiptSchedule : receiptSchedules)
		{
			rsNetWeightAdjuster.addReceiptSchedule(receiptSchedule);
		}

		//
		// Make sure Weights were correctly calculated.
		// Also make sure the weight adjustment for our first HU was correctly distributed between our receipt schedules
		//@formatter:off
		newTUWeightsExpectations()
				.countTUs(10)
				// Paloxe 1
				.newTUExpectation()
					.gross("510").net("435").tare("75").tareAdjust("0")
					.newHUReceiptScheduleAllocExpectations()
						.receiptSchedule(receiptSchedules.get(0))
						.huQtyAllocated("232.674") // = 230 + (5 * 230/430)
						.endExpectation()
					.newHUReceiptScheduleAllocExpectations()
						.receiptSchedule(receiptSchedules.get(1))
						.huQtyAllocated("202.326") // = (430-230) + (5 * (430-230)/430)
						.endExpectation()
					.endExpectation()
				// Paloxe 2-10 expectations
				.setDefaultTUExpectation(standardPaloxeWeightExpectation)
				//
				// Assert
				.assertExpected(paloxes);
		//@formatter:on
	}
}
