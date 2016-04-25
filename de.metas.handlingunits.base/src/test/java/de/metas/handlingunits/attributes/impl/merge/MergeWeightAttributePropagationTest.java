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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attributes.impl.AbstractWeightAttributeTest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;

/**
 * NOTE: Tests propagation WITH TareAdjust CONSTANT ZERO.
 *
 * Test <i>propagation</i> of the <code>Weight</code> attributes (Gross, Net, Tare, Tare Adjust) and their behavior together.<br>
 * Test <i>HU operations</i> (i.e split, join, merge) and how these attributes are handled/re-propagated together.
 *
 * @author al
 */
public class MergeWeightAttributePropagationTest extends AbstractWeightAttributeTest
{
	/**
	 * Tests in this class use a gross weight of 100, considered the user's original input.
	 */
	private static final BigDecimal INPUT_GROSS_100 = BigDecimal.valueOf(100);

	/**
	 * M010: Join - start from J010, then Merge TU on an LU with another TU on the SAME LU
	 */
	@Test
	public void testMergeWeightTransfer_TUsOnSameLU()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
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

		final I_M_HU sourceTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 10); // find a TU with 10 x CU
		final List<I_M_HU> sourceTUsInLoadingUnit = Collections.singletonList(sourceTUInLoadingUnit);

		final I_M_HU targetTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 5); // find a TU with 5 x CU
		helper.mergeTUs(huContext, sourceTUsInLoadingUnit, targetTUInLoadingUnit, getCUProduct(), BigDecimal.valueOf(2), getCUUOM());

		//
		// Assert data integrity on TARGET LU for both SOURCE TU and TARGET TU
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 8,
				newHUWeightsExpectation("91.235", "58.235", "33", "0"),
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
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
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
				newHUWeightsExpectation("92.788", "59.788", "33", "0"),
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
		final I_M_HU targetLU = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(targetLU, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
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
				newHUWeightsExpectation("91.235", "58.235", "33", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}

	/**
	 * M040: Join - Merge TU on an LU with another TU on the ANOTHER LU, but FULLY - old TU shall be destroyed
	 */
	@Test
	public void testMergeFullWeightTransfer_TUsOnAnotherLU()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		//
		// Take 2 x TU from the palette and move them to a new LU
		final List<I_M_HU> splitLUs = splitLU(loadingUnit,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs Per LU
				BigDecimal.ONE); // split on ONE additional LU

		Assert.assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());
		final I_M_HU splitLU = splitLUs.get(0);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 2,
				newHUWeightsExpectation("42.530", "15.530", "27", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		//
		// Take 1 x TU x 10 CU from the newly split LU, split in 2 x 5 CU
		final List<I_M_HU> halvedTUs = splitLU(splitLU,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				BigDecimal.valueOf(10), // total qty to split
				BigDecimal.valueOf(5), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		//
		// Add the 2 x 5 CUs back on the palette which was split off from the main one
		helper.joinHUs(huContext, splitLU, halvedTUs);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 3,
				newHUWeightsExpectation("43.530", "15.530", "28", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.883", "3.883", "1", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"));

		//
		// Finally, merge 2 x TUs from both palettes at the same time
		final I_M_HU sourceTUInLoadingUnit = findTUInLUWithQty(splitLU, 5); // find a TU with 5 x CU in the LU TO which CUs were split
		final I_M_HU targetTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 5); // find a TU with 5 x CU in the LU FROM which we split
		helper.mergeTUs(huContext, Collections.singletonList(sourceTUInLoadingUnit), targetTUInLoadingUnit, getCUProduct(), BigDecimal.valueOf(5), getCUUOM());

		Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, targetTUInLoadingUnit.getHUStatus());
		Assert.assertEquals("Source TU is destroyed", X_M_HU.HUSTATUS_Destroyed, sourceTUInLoadingUnit.getHUStatus());
		Assert.assertEquals("Source LU is planning", X_M_HU.HUSTATUS_Planning, splitLU.getHUStatus());

		//
		// Assert data integrity for both SOURCE LU and TARGET LU
		// Source TU will be destroyed
		//
		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 2,
				newHUWeightsExpectation("38.647", "11.647", "27", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"));

		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 7,
				newHUWeightsExpectation("86.353", "54.353", "32", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.763", "7.763", "1", "0"));
	}

	/**
	 * M041: Join - Merge TU on an LU with another TU on the SAME LU, but PARTIALLY - old TU shall NOT be destroyed
	 */
	@Test
	public void testMergeFullWeightTransfer_TUsOnSameLU()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.880", "3.880", "1", "0"));

		//
		// Merge the 2 trading units within the palette
		final I_M_HU sourceTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 10);
		final I_M_HU targetTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 5);

		helper.mergeTUs(huContext, Collections.singletonList(sourceTUInLoadingUnit), targetTUInLoadingUnit, getCUProduct(), BigDecimal.valueOf(5), getCUUOM());

		Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, targetTUInLoadingUnit.getHUStatus());
		Assert.assertEquals("Source TU is planning", X_M_HU.HUSTATUS_Planning, sourceTUInLoadingUnit.getHUStatus());

		//
		// Assert data integrity on TARGET LU for both SOURCE TU and TARGET TU
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.763", "7.763", "1", "0"));

		//
		// REVERSE merge order and assert weights again
		//
		helper.mergeTUs(huContext, Collections.singletonList(targetTUInLoadingUnit), sourceTUInLoadingUnit, getCUProduct(), BigDecimal.valueOf(5), getCUUOM());

		Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, targetTUInLoadingUnit.getHUStatus());
		Assert.assertEquals("Source TU is planning", X_M_HU.HUSTATUS_Planning, sourceTUInLoadingUnit.getHUStatus());

		//
		// Assert data integrity on TARGET LU for both SOURCE TU and TARGET TU
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("8.762", "7.762", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.883", "3.883", "1", "0"));
	}

	/**
	 * M042: Join - Merge TU on an LU with another TU on the SAME LU, with split before (have partial qty from the generated one), but FULLY - old TU shall be destroyed
	 */
	@Test
	public void testMergeFullWeightTransfer_TUsOnSameLUWithSplitBefore()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		final List<I_M_HU> splitTradingUnits = splitLU(loadingUnit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				BigDecimal.valueOf(5), // 5 TUs each shall be split off
				BigDecimal.valueOf(2), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		Assert.assertEquals("Invalid amount of TUs were split", 2, splitTradingUnits.size());

		//
		// Make sure that we've correctly split off half (5 CU) out of each of the 2 x TU
		// Note that one TU with 10 x TU will be completely destroyed, resulting in the 2 new ones
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 8,
				newHUWeightsExpectation("91.235", "58.235", "33", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.880", "3.880", "1", "0"));

		//
		// Simulate - take off 2 x TUKeys to bind it to the new LU
		helper.joinHUs(huContext, loadingUnit, splitTradingUnits);

		//
		// Make sure that the TUs were jointed back correctly (we will have 2 x 5 CU new TUs instead of 1 x 10 CU, so the TARA will increase)
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 10,
				newHUWeightsExpectation("101", "66", "35", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.880", "3.880", "1", "0"),
				newHUWeightsExpectation("4.883", "3.883", "1", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"));

		//
		// Merge the 2 trading units within the palette
		final I_M_HU sourceTUInLoadingUnit = splitTradingUnits.get(0);
		final I_M_HU targetTUInLoadingUnit = splitTradingUnits.get(1);
		helper.mergeTUs(huContext, Collections.singletonList(sourceTUInLoadingUnit), targetTUInLoadingUnit, getCUProduct(), BigDecimal.valueOf(5), getCUUOM());

		Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, targetTUInLoadingUnit.getHUStatus());
		Assert.assertEquals("Source TU is destroyed", X_M_HU.HUSTATUS_Destroyed, sourceTUInLoadingUnit.getHUStatus());

		//
		// Assert data integrity on TARGET LU for both SOURCE TU and TARGET TU
		// Source TU will be destroyed, so make sure that everything is back to the way it was before
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.880", "3.880", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}

	/**
	 * M043: Merge TU on an LU with another TU on ANOTHER LU, but FULLY - old TU shall be destroyed and old LU shall also be destroyed
	 */
	@Test
	public void testMergeFullWeightTransfer_TUsOnAnotherLU_DestroyLU()
	{
		// final HUTracerInstance tracer = new HUTracerInstance(huContext);

		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(
				loadingUnit, // LU
				huItemIFCO_10 // LU's Item PI
				, 9 // TUs count
				, newHUWeightsExpectation("100", "66", "34", "0") // LU expectation
				, newHUWeightsExpectation("8.765", "7.765", "1", "0") // TU1 expectation
				, newHUWeightsExpectation("8.765", "7.765", "1", "0") // TU2 expectation
				, newHUWeightsExpectation("8.765", "7.765", "1", "0") // TU3 expectation
				, newHUWeightsExpectation("8.765", "7.765", "1", "0") // TU4 expectation
				, newHUWeightsExpectation("8.765", "7.765", "1", "0") // TU5 expectation
				, newHUWeightsExpectation("8.765", "7.765", "1", "0") // TU6 expectation
				, newHUWeightsExpectation("8.765", "7.765", "1", "0") // TU7 expectation
				, newHUWeightsExpectation("8.765", "7.765", "1", "0") // TU8 expectation
				, newHUWeightsExpectation("4.880", "3.880", "1", "0") // TU9 expectation
		);
		// tracer.dump("LU", loadingUnit);

		//
		// Take 2 x TU from the palette and move them to a new LU
		final List<I_M_HU> splitLUs = splitLU(loadingUnit,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				BigDecimal.valueOf(5), // 5, split the full TU off the source LU
				BigDecimal.ONE, // TUs Per LU
				BigDecimal.ONE); // split on ONE additional LU
		// tracer.dump("LU after split", loadingUnit);

		Assert.assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());
		final I_M_HU splitLU = splitLUs.get(0);
		// tracer.dump("splitLU", splitLU);

		assertLoadingUnitStorageWeights(
				splitLU,
				huItemIFCO_10,
				1,// TUs count
				newHUWeightsExpectation("29.883", "3.883", "26", "0"), // LU expectation
				newHUWeightsExpectation("4.883", "3.883", "1", "0") // TU expectation
		);

		//
		// Finally, merge 2 x TUs from both palettes at the same time
		final I_M_HU sourceTUInLoadingUnit = findTUInLUWithQty(splitLU, 5); // find a TU with 5 x CU in the LU TO which CUs were split
		final I_M_HU targetTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 5); // find a TU with 5 x CU in the LU FROM which we split
		helper.mergeTUs(huContext,
				Collections.singletonList(sourceTUInLoadingUnit), // source TUs
				targetTUInLoadingUnit, // target TU
				getCUProduct(), // CU/Product
				BigDecimal.valueOf(5), // Qty CU
				getCUUOM() // UOM
		);

		Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, targetTUInLoadingUnit.getHUStatus());
		Assert.assertEquals("Source TU is destroyed", X_M_HU.HUSTATUS_Destroyed, sourceTUInLoadingUnit.getHUStatus());

		//
		// Assumption: because this was our last (and the only one) TU from Palet, check if the Palet was also destroyed
		{
			// because splitLU is updated elsewhere, we need to refresh out instance.
			// FIXME: do this somehow auto-magically
			InterfaceWrapperHelper.refresh(splitLU);
			// tracer.dump("splitLU after merge", splitLU);
			Assert.assertEquals("Source LU is destroyed", X_M_HU.HUSTATUS_Destroyed, splitLU.getHUStatus());
		}

		//
		// Assert data integrity for TARGET LU
		// Source LU and TU will be destroyed
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.880", "3.880", "1", "0"));
	}

	/**
	 * M050 (similar to M041): Merge TU (no parent) with a TU which is on an LU, but FULLY - old TU shall be destroyed
	 */
	@Test
	public void testMergeFullWeightTransfer_TUsNoPIOnLU()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		final List<I_M_HU> splitTradingUnits = splitLU(loadingUnit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				BigDecimal.valueOf(5), // 5 TUs each shall be split off
				BigDecimal.ONE, // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		Assert.assertEquals("Invalid amount of TUs were split", 1, splitTradingUnits.size());

		//
		// Make sure that we've correctly split off half (5 CU) out of each of the 2 x TU
		// Note that one TU with 10 x TU will be completely destroyed, resulting in the 2 new ones
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("96.117", "62.117", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		//
		// Merge the 2 trading units within the palette
		final I_M_HU sourceTUInLoadingUnit = splitTradingUnits.get(0);
		final I_M_HU targetTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 5); // find a TU with 5 x CU
		helper.mergeTUs(huContext, Collections.singletonList(sourceTUInLoadingUnit), targetTUInLoadingUnit, getCUProduct(), BigDecimal.valueOf(5), getCUUOM());

		Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, targetTUInLoadingUnit.getHUStatus());
		Assert.assertEquals("Source TU is destroyed", X_M_HU.HUSTATUS_Destroyed, sourceTUInLoadingUnit.getHUStatus());

		//
		// Assert data integrity on TARGET LU for both SOURCE TU and TARGET TU
		// Source TU will be destroyed, so make sure that everything is back to the way it was before
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.880", "3.880", "1", "0"));
	}

	/**
	 * M060: Merge LUs, but FULLY - old LU shall be destroyed
	 */
	@Test
	public void testMergeFullWeightTransfer_LUs()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		//
		// Take 2 x TU from the palette and move them to a new LU
		final List<I_M_HU> splitLUs = splitLU(loadingUnit,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs Per LU
				BigDecimal.ONE); // split on ONE additional LU

		Assert.assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());
		final I_M_HU splitLU = splitLUs.get(0);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 2,
				newHUWeightsExpectation("42.530", "15.530", "27", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		//
		// Finally, move the 2 split TUs back to the original palette and make sure that their LU was destroyed
		final List<I_M_HU> tradingUnitsInSplitLU = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(splitLU);
		Assert.assertEquals("Invalid tradingUnitsInSplitLU count", 2, tradingUnitsInSplitLU.size());

		helper.mergeLUs(huContext, loadingUnit, tradingUnitsInSplitLU);

		// NOTE: because splitLU was changed outside, we need to refresh it first
		// FIXME: do this somehow auto-magically
		InterfaceWrapperHelper.refresh(splitLU);
		Assert.assertEquals("Source LU is destroyed", X_M_HU.HUSTATUS_Destroyed, splitLU.getHUStatus());

		//
		// Assert data integrity for TARGET LU
		// Source LU and TU will be destroyed
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.880", "3.880", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}
}
