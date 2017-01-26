package de.metas.handlingunits.attributes.impl.merge;

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
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attributes.impl.AbstractWeightAttributeTest;
import de.metas.handlingunits.model.I_M_HU;

/**
 * NOTE: Tests propagation WITH TareAdjsut VARIABLE.
 *
 * Test <i>propagation</i> of the <code>Weight</code> attributes (Gross, Net, Tare, Tare Adjust) and their behavior together.<br>
 * Test <i>HU operations</i> (i.e split, join, merge) and how these attributes are handled/re-propagated together.
 *
 * @author al
 */
public class MergeWeightTareAdjustPropagationTest extends AbstractWeightAttributeTest
{
	/**
	 * Tests in this class <i>USUALLY</i> use a gross weight of 101, considered the user's original input.
	 */
	private static final BigDecimal INPUT_GROSS_101 = BigDecimal.valueOf(101);

	/**
	 * M010: Join - start from J010, then Merge TU on an LU with another TU on the SAME LU
	 */
	@Test
	public void testMergeWeightTransfer_TUsOnSameLU()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(loadingUnit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		final List<I_M_HU> splitTradingUnits = splitLU(loadingUnit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		Assert.assertEquals("Invalid amount of TUs were split", 2, splitTradingUnits.size());

		//
		// Simulate - take off one of the TUKeys to bind it to the new LU
		final I_M_HU tradingUnitToJoin = splitTradingUnits.remove(1);
		helper.joinHUs(huContext, loadingUnit, tradingUnitToJoin);

		final I_M_HU sourceTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 10); // find a TU with 10 x CU
		final List<I_M_HU> sourceTUsInLoadingUnit = Collections.singletonList(sourceTUInLoadingUnit);

		final I_M_HU targetTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 5); // find a TU with 5 x CU
		helper.mergeTUs(huContext, sourceTUsInLoadingUnit, targetTUInLoadingUnit, getCUProduct(), BigDecimal.valueOf(2), getCUUOM());

		//
		// Assert data integrity on TARGET LU for both SOURCE TU and TARGET TU
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 8,
				newHUWeightsExpectation("92.235", "58.235", "33", "1"),
				newHUWeightsExpectation("7.212", "6.212", "1", "0"), // SOURCE
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("6.433", "5.433", "1", "0"), // TARGET
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}

	/**
	 * M020: Join - start from J010, then Merge TU (no parent) with a TU which is on an LU
	 */
	@Test
	public void testMergeWeightTransfer_TUsNoPIOnLU()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(loadingUnit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		final List<I_M_HU> splitTradingUnits = splitLU(loadingUnit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		Assert.assertEquals("Invalid amount of TUs were split", 2, splitTradingUnits.size());

		//
		// Simulate - take off one of the TUKeys to bind it to the new LU
		final I_M_HU tradingUnitToJoin = splitTradingUnits.remove(1);
		helper.joinHUs(huContext, loadingUnit, tradingUnitToJoin);

		final I_M_HU targetTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 5); // find a TU with 5 x CU
		helper.mergeTUs(huContext, splitTradingUnits, targetTUInLoadingUnit, getCUProduct(), BigDecimal.valueOf(2), getCUUOM());

		//
		// Assert data integrity on SOURCE TUs
		//
		Assert.assertEquals("Invalid amount of remaining TUs after merge", 1, splitTradingUnits.size());
		final I_M_HU splitTU = splitTradingUnits.get(0);
		final IAttributeStorage attributeStorageTU = attributeStorageFactory.getAttributeStorage(splitTU);
		assertSingleHandlingUnitWeights(attributeStorageTU, newHUWeightsExpectation("7.212", "6.212", "1", "0"));

		//
		// Assert data integrity on TARGET LU
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 8,
				newHUWeightsExpectation("93.788", "59.788", "33", "1"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("6.433", "5.433", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}

	/**
	 * M030: Split - start from S020, then Merge TU on an LU with another TU from ANOTHER LU
	 */
	@Test
	public void testMergeWeightTransfer_LUs()
	{
		//
		// Target LU
		final I_M_HU targetLU = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(targetLU, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(targetLU, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		final List<I_M_HU> splitLUs = splitLU(targetLU,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs Per LU
				BigDecimal.ONE); // split on ONE additional LU

		Assert.assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());

		//
		// Source LU
		final I_M_HU sourceLU = splitLUs.get(0);
		Assert.assertTrue("The target LU we just split to shall be a top-level handling unit", sourceLU.getM_HU_Item_Parent_ID() <= 0);

		final I_M_HU sourceTradingUnitWith10 = findTUInLUWithQty(sourceLU, 10); // find a TU with 10 x CU
		helper.mergeLUs(huContext, targetLU, sourceTradingUnitWith10);

		//
		// Assert data integrity on SOURCE LU
		//
		assertLoadingUnitStorageWeights(sourceLU, huItemIFCO_10, 1,
				newHUWeightsExpectation("33.765", "7.765", "26", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		//
		// Assert data integrity on TARGET LU
		//
		assertLoadingUnitStorageWeights(targetLU, huItemIFCO_10, 8,
				newHUWeightsExpectation("92.235", "58.235", "33", "1"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}
}
