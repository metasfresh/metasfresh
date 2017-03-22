package de.metas.handlingunits.attributes.impl.split;

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

import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attributes.impl.AbstractWeightAttributeTest;
import de.metas.handlingunits.expectations.HUWeightsExpectation;
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
public class SplitWeightAttributePropagationTest extends AbstractWeightAttributeTest
{
	/**
	 * Tests in this class use a gross weight of 100, considered the user's original input.
	 */
	private static final BigDecimal INPUT_GROSS_100 = BigDecimal.valueOf(100);

	/**
	 * S010: Split 2 x TU from source LU on an additional LU of the same type as the source
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_NoPI()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.117", "62.117", "8", "0"));

		// split off 2x10
		final List<I_M_HU> splitTUs = splitLU(paletToSplit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		Assert.assertEquals("Invalid amount of TUs were split", 2, splitTUs.size());

		//
		// Assert data integrity on SOURCE LU
		//
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 7,
				newHUWeightsExpectation("82.470", "50.470", "32", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("52.588", "46.588", "6", "0"));

		//
		// Assert data integrity on TARGET TUs
		//
		final I_M_HU splitTU1 = splitTUs.get(0);
		final I_M_HU splitTU2 = splitTUs.get(1);

		final IAttributeStorage attributeStorageTU1 = attributeStorageFactory.getAttributeStorage(splitTU1);
		assertSingleHandlingUnitWeights(attributeStorageTU1, newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		final IAttributeStorage attributeStorageTU2 = attributeStorageFactory.getAttributeStorage(splitTU2);
		assertSingleHandlingUnitWeights(attributeStorageTU2, newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}

	/**
	 * S020: Split 2 x TU from source LU on an additional LU of the same type as the source
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_Another_SameType_LU_2TU()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		final List<I_M_HU> splitLUs = splitLU(paletToSplit,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs Per LU
				BigDecimal.ONE); // split on ONE additional LU

		Assert.assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU
		//
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 7,
				newHUWeightsExpectation("82.470", "50.470", "32", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("52.588", "46.588", "6", "0"));
		//
		// Assert data integrity on TARGET LU
		//
		final I_M_HU splitLU = splitLUs.get(0);
		Assert.assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 2,
				newHUWeightsExpectation("42.530", "15.530", "27", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}

	/**
	 * S021: Split 3 x TU from source LU on an additional LU of the same type as the source
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_Another_SameType_LU_3TU()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		final List<I_M_HU> splitLUs = splitLU(paletToSplit,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(3), // TUs Per LU
				BigDecimal.ONE); // split on ONE additional LU

		Assert.assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU
		//
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 6,
				newHUWeightsExpectation("73.706", "42.706", "31", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("43.824", "38.824", "5", "0"));

		//
		// Assert data integrity on TARGET LU
		//
		final I_M_HU splitLU = splitLUs.get(0);
		Assert.assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 3,
				newHUWeightsExpectation("51.295", "23.295", "28", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}

	/**
	 * S030: Split 1 x TU from source LU on an additional LU of the same type as the source
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_NoPI_1TU_7CU()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		final List<I_M_HU> splitTUs = splitLU(paletToSplit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				BigDecimal.valueOf(7), // 7, split the 7 CUs of the TU
				BigDecimal.valueOf(1), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		Assert.assertEquals("Invalid amount of TUs were split", 1, splitTUs.size());

		//
		// Assert data integrity on SOURCE LU
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 8, // #490 the original pre-aggregate test had "9", but imho 8 is better :-)
				newHUWeightsExpectation("93.565", "60.565", "33", "0"),
				newHUWeightsExpectation("7.212", "6.212", "1", "0"),
				newHUWeightsExpectation("61.353", "54.353", "7", "0"));

		//
		// Assert data integrity on TARGET TU
		//
		final I_M_HU splitTU = splitTUs.get(0);
		Assert.assertTrue("The target TU we just split to shall be a top-level handling unit", splitTU.getM_HU_Item_Parent_ID() <= 0);

		final IAttributeStorage attributeStorageTU = attributeStorageFactory.getAttributeStorage(splitTU);
		assertSingleHandlingUnitWeights(attributeStorageTU, newHUWeightsExpectation("6.435", "5.435", "1", "0"));
	}

	/**
	 * S040: Split 1 x TU from source LU on an additional LU of the same type as the source
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_Another_SameType_LU_1TU_7CU()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		final List<I_M_HU> splitLUs = splitLU(paletToSplit,
				huItemIFCO_10, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				BigDecimal.valueOf(7), // 7, split the 7 CUs of the TU
				BigDecimal.valueOf(1), // TUs per LU
				BigDecimal.ONE); // split on ONE additional LU

		Assert.assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 8, // #490 the original pre-aggregate test had "9", but imho 8 is better :-)
				newHUWeightsExpectation("93.565", "60.565", "33", "0"),
				newHUWeightsExpectation("7.212", "6.212", "1", "0"),
				newHUWeightsExpectation("61.353", "54.353", "7", "0"));

		//
		// Assert data integrity on TARGET LU
		//
		final I_M_HU splitLU = splitLUs.get(0);
		Assert.assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 1,
				newHUWeightsExpectation("31.436", "5.436", "26", "0"),
				newHUWeightsExpectation("6.436", "5.436", "1", "0"));
	}

	/**
	 * S050: Split 1 x TU from source LU on the SAME source LU
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_Another_SameSource_LU()
	{
		// TODO (scenario not yet implemented)
	}

	/**
	 * S060: Split 2 x TU from source LU on 2 x LU of the same type as the source, each with 1 x TU
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_2_SameType_LUs()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		final List<I_M_HU> splitLUs = splitLU(paletToSplit,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.ONE, // TUs per LU
				BigDecimal.valueOf(2)); // split on TWO additional LUs

		Assert.assertEquals("Invalid amount of LUs were split", 2, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU
		//
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 7,
				newHUWeightsExpectation("82.470", "50.470", "32", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("52.588", "46.588", "6", "0"));

		//
		// Assert data integrity on TARGET LUs
		//
		final I_M_HU splitLU1 = splitLUs.get(0);
		Assert.assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU1.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU1, huItemIFCO_10, 1,
				newHUWeightsExpectation("33.765", "7.765", "26", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		final I_M_HU splitLU2 = splitLUs.get(0);
		Assert.assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU2.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU2, huItemIFCO_10, 1,
				newHUWeightsExpectation("33.765", "7.765", "26", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}

	/**
	 * S070: Split 3 x TU from source LU on NoPI, each with 5 x CUs
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_NoPI_3TU_5CU()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		final List<I_M_HU> splitTUs = splitLU(paletToSplit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				BigDecimal.valueOf(5), // 5, split the 5 CUs of the TU
				BigDecimal.valueOf(3), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		Assert.assertEquals("Invalid amount of LUs were split", 3, splitTUs.size());

		//
		// Assert data integrity on SOURCE LU
		//
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 7,
				newHUWeightsExpectation("86.353", "54.353", "32", "0"),
				newHUWeightsExpectation("61.353", "54.353", "7", "0"));

		//
		// Assert data integrity on TARGET TUs
		//
		final I_M_HU splitTU1 = splitTUs.get(0);
		Assert.assertTrue("The target TU we just split to shall be a top-level handling unit", splitTU1.getM_HU_Item_Parent_ID() <= 0);

		final IAttributeStorage attributeStorageTU1 = attributeStorageFactory.getAttributeStorage(splitTU1);
		assertSingleHandlingUnitWeights(attributeStorageTU1, newHUWeightsExpectation("4.883", "3.883", "1", "0"));

		final I_M_HU splitTU2 = splitTUs.get(1);
		Assert.assertTrue("The target TU we just split to shall be a top-level handling unit", splitTU2.getM_HU_Item_Parent_ID() <= 0);

		final IAttributeStorage attributeStorageTU2 = attributeStorageFactory.getAttributeStorage(splitTU2);
		assertSingleHandlingUnitWeights(attributeStorageTU2, newHUWeightsExpectation("4.882", "3.882", "1", "0"));

		final I_M_HU splitTU3 = splitTUs.get(2);
		Assert.assertTrue("The target TU we just split to shall be a top-level handling unit", splitTU3.getM_HU_Item_Parent_ID() <= 0);

		final IAttributeStorage attributeStorageTU3 = attributeStorageFactory.getAttributeStorage(splitTU3);
		assertSingleHandlingUnitWeights(attributeStorageTU3, newHUWeightsExpectation("4.883", "3.883", "1", "0"));
	}

	/**
	 * S080: Split 7 x TU from source LU on another LU, but with different TU-PI, each with 5 x CUs
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_1LU_7TU_Different_PI_5CU()
	{
		//46 tomato: 2 per IFCO => 23 IFCOs;  
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_2, materialItemProductTomato_2, CU_QTY_46, INPUT_GROSS_100); // 46 x Tomato
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_2, 23,
				newHUWeightsExpectation("100", "52"/*net*/, "48"/*tare:1x25+23x1*/, "0"),
				newHUWeightsExpectation("75", "52", "23", "0"));

		// split onto 7xIFCOs with 5kg each => 35kg
		final List<I_M_HU> splitLUs = splitLU(paletToSplit,
				huItemIFCO_5, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_5, // TU item x 5
				CU_QTY_46, // total qty to split
				materialItemProductTomato_5.getQty(), // 5, split the 5 CUs of the TU
				BigDecimal.valueOf(7), // TUs per LU
				BigDecimal.ONE); // split on ONE additional LU

		Assert.assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU
		//
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_2, 6,
				newHUWeightsExpectation("43.434", "12.434", "31", "0"),
				newHUWeightsExpectation("2.130", "1.130", "1", "0"),
				newHUWeightsExpectation("16.304", "11.304", "5", "0"));

		//
		// Assert data integrity on TARGET LUs
		//
		final I_M_HU splitLU = splitLUs.get(0);
		Assert.assertTrue("The target TU we just split to shall be a top-level handling unit", splitLU.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_5, 7,
				newHUWeightsExpectation("71.565", "39.565", "32", "0"),
				newHUWeightsExpectation("46.565", "39.565", "7", "0"));
	}

	/**
	 * S090: Split 25kg off Paloxe on another Paloxe
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_SameType_TU_Partial()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(505) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("505", "430", "75", "0"));

		final BigDecimal amountToSplit = BigDecimal.valueOf(25); // split off 25kg off the Paloxe x 430kg
		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				amountToSplit,
				BigDecimal.ONE, // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_430, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 1, tradingUnitsTarget.size());
		final I_M_HU paloxeTarget = tradingUnitsTarget.get(0);

		//
		// Assert data integrity on SOURCE Paloxe
		//
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("480", "405", "75", "0"));

		//
		// Assert data integrity on TARGET Paloxe
		//
		final IAttributeStorage attributeStoragePaloxeTarget = attributeStorageFactory.getAttributeStorage(paloxeTarget);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeTarget, newHUWeightsExpectation("100", "25", "75", "0"));
	}

	/**
	 * S090.1: Split 25kg off Paloxe on another Paloxe, but weight is under the expected one
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_SameType_TU_Partial_DifferWeight()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(500) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("500", "425", "75", "0"));

		final BigDecimal amountToSplit = BigDecimal.valueOf(25); // split off 25kg off the Paloxe x 430kg
		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				amountToSplit,
				BigDecimal.ONE, // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_430, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 1, tradingUnitsTarget.size());
		final I_M_HU paloxeTarget = tradingUnitsTarget.get(0);

		//
		// Assert data integrity on SOURCE Paloxe
		//
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("475.29", "400.29", "75", "0"));

		//
		// Assert data integrity on TARGET Paloxe
		//
		final IAttributeStorage attributeStoragePaloxeTarget = attributeStorageFactory.getAttributeStorage(paloxeTarget);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeTarget, newHUWeightsExpectation("99.71", "24.71", "75", "0"));
	}

	/**
	 * S091: Split 30kg off Paloxe on multiple 10 x CU capacity TU
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_10CU_TU_Partial()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(505) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("505", "430", "75", "0"));

		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(30), // CU Qty
				uomKg,
				BigDecimal.TEN, // cuPerTU
				BigDecimal.valueOf(3), // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_10, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 3, tradingUnitsTarget.size());

		//
		// Assert data integrity on SOURCE Paloxe
		//
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("475", "400", "75", "0"));

		//
		// Assert data integrity on TARGET TUs
		//
		final HUWeightsExpectation<Object> defaultWeightExpectation = newHUWeightsExpectation("11", "10", "1", "0");
		assertTradingUnitsWeightExpectations(tradingUnitsTarget, defaultWeightExpectation);
	}

	/**
	 * S091.1: Split 30kg off Paloxe on multiple 10 x CU capacity TU, but weight is under the expected one
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_10CU_TU_Partial_DifferWeight()
	{

		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(500) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("500", "425", "75", "0"));

		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(30), // CU Qty
				uomKg,
				BigDecimal.TEN, // cuPerTU
				BigDecimal.valueOf(3), // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_10, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 3, tradingUnitsTarget.size());

		//
		// Assert data integrity on SOURCE Paloxe
		//
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("470.348", "395.348", "75", "0"));

		//
		// Assert data integrity on TARGET TUs
		//
		final HUWeightsExpectation<Object> defaultWeightExpectation = newHUWeightsExpectation("10.884", "9.884", "1", "0");
		assertTradingUnitsWeightExpectations(tradingUnitsTarget, defaultWeightExpectation);
	}

	/**
	 * S092: Split full qty off Paloxe on another Paloxe
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_SameType_TU_Full()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(505) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("505", "430", "75", "0"));

		final BigDecimal amountToSplit = BigDecimal.valueOf(430); // split off 25kg off the Paloxe x 430kg
		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				amountToSplit,
				BigDecimal.ONE, // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_430, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 1, tradingUnitsTarget.size());
		final I_M_HU paloxeTarget = tradingUnitsTarget.get(0);

		//
		// Assert data integrity on SOURCE Paloxe
		//
		Assert.assertEquals("Source TU is destroyed", X_M_HU.HUSTATUS_Destroyed, paloxeSource.getHUStatus());

		//
		// Assert data integrity on TARGET Paloxe
		//
		Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, paloxeTarget.getHUStatus());

		final IAttributeStorage attributeStoragePaloxeTarget = attributeStorageFactory.getAttributeStorage(paloxeTarget);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeTarget, newHUWeightsExpectation("505", "430", "75", "0"));
	}

	/**
	 * S092.1: Split full qty off Paloxe on another Paloxe, but weight is under the expected one
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_SameType_TU_Full_DifferWeight()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(500) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("500", "425", "75", "0"));

		final BigDecimal amountToSplit = BigDecimal.valueOf(430); // split off 25kg off the Paloxe x 430kg
		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				amountToSplit,
				BigDecimal.ONE, // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_430, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 1, tradingUnitsTarget.size());
		final I_M_HU paloxeTarget = tradingUnitsTarget.get(0);

		//
		// Assert data integrity on SOURCE Paloxe
		//
		Assert.assertEquals("Source TU is destroyed", X_M_HU.HUSTATUS_Destroyed, paloxeSource.getHUStatus());

		//
		// Assert data integrity on TARGET Paloxe
		//
		Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, paloxeTarget.getHUStatus());

		final IAttributeStorage attributeStoragePaloxeTarget = attributeStorageFactory.getAttributeStorage(paloxeTarget);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeTarget, newHUWeightsExpectation("500", "425", "75", "0"));
	}

	/**
	 * S093: Split full qty off Paloxe on multiple 10 x CU capacity TU
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_10CU_TU_Full()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(505) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("505", "430", "75", "0"));

		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				BigDecimal.TEN, // cuPerTU
				BigDecimal.valueOf(43), // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_10, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 43, tradingUnitsTarget.size());

		//
		// Assert data integrity on SOURCE Paloxe
		//
		Assert.assertEquals("Source TU is destroyed", X_M_HU.HUSTATUS_Destroyed, paloxeSource.getHUStatus());

		//
		// Assert data integrity on TARGET TUs
		//
		final HUWeightsExpectation<Object> defaultWeightExpectation = newHUWeightsExpectation("11", "10", "1", "0");
		for (final I_M_HU tradingUnitTarget : tradingUnitsTarget)
		{
			Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, tradingUnitTarget.getHUStatus());

			final IAttributeStorage attributeStorageTradingUnitTarget = attributeStorageFactory.getAttributeStorage(tradingUnitTarget);
			assertSingleHandlingUnitWeights(attributeStorageTradingUnitTarget, defaultWeightExpectation);
		}
	}

	/**
	 * S093.1: Split full qty off Paloxe on multiple 10 x CU capacity TU, but weight is under the expected one
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_10CU_TU_Full_DifferWeight()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(500) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("500", "425", "75", "0"));

		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				BigDecimal.TEN, // cuPerTU
				BigDecimal.valueOf(43), // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_10, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 43, tradingUnitsTarget.size());

		//
		// Assert data integrity on SOURCE Paloxe
		//
		Assert.assertEquals("Source TU is destroyed", X_M_HU.HUSTATUS_Destroyed, paloxeSource.getHUStatus());

		//
		// Assert data integrity on TARGET TUs
		//
		final HUWeightsExpectation<Object> defaultWeightExpectation = newHUWeightsExpectation("10.884", "9.884", "1", "0");
		for (final I_M_HU tradingUnitTarget : tradingUnitsTarget)
		{
			Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, tradingUnitTarget.getHUStatus());

			final IAttributeStorage attributeStorageTradingUnitTarget = attributeStorageFactory.getAttributeStorage(tradingUnitTarget);
			assertSingleHandlingUnitWeights(attributeStorageTradingUnitTarget, defaultWeightExpectation);
		}
	}

	/**
	 * S094: Split 3 x 25kg off Paloxe on multiple Paloxes
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_multiple_SameType_TU_Partial()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(505) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("505", "430", "75", "0"));

		final BigDecimal amountToSplit = BigDecimal.valueOf(25); // split off 25kg off the Paloxe x 430kg
		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				amountToSplit,
				BigDecimal.valueOf(3), // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_430, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 3, tradingUnitsTarget.size());

		//
		// Assert data integrity on SOURCE Paloxe
		//
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("430", "355", "75", "0"));

		//
		// Assert data integrity on TARGET TUs
		//
		final HUWeightsExpectation<Object> defaultWeightExpectation = newHUWeightsExpectation("100", "25", "75", "0");
		for (final I_M_HU tradingUnitTarget : tradingUnitsTarget)
		{
			final IAttributeStorage attributeStorageTradingUnitTarget = attributeStorageFactory.getAttributeStorage(tradingUnitTarget);
			assertSingleHandlingUnitWeights(attributeStorageTradingUnitTarget, defaultWeightExpectation);
		}
	}

	/**
	 * S094.1: Split 3 x 25kg off Paloxe on multiple Paloxes, but weight is under the expected one
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_multiple_SameType_TU_Partial_DifferWeight()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(500) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("500", "425", "75", "0"));

		final BigDecimal amountToSplit = BigDecimal.valueOf(25); // split off 25kg off the Paloxe x 430kg
		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				amountToSplit,
				BigDecimal.valueOf(3), // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_430, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 3, tradingUnitsTarget.size());

		//
		// Assert data integrity on SOURCE Paloxe
		//
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("425.873", "350.873", "75", "0"));

		//
		// Assert data integrity on TARGET TUs
		//
		final HUWeightsExpectation<Object> defaultWeightExpectation = newHUWeightsExpectation("99.709", "24.709", "75", "0");
		for (final I_M_HU tradingUnitTarget : tradingUnitsTarget)
		{
			final IAttributeStorage attributeStorageTradingUnitTarget = attributeStorageFactory.getAttributeStorage(tradingUnitTarget);
			assertSingleHandlingUnitWeights(attributeStorageTradingUnitTarget, defaultWeightExpectation);
		}
	}

	/**
	 * S095: Split full qty off Paloxe on multiple Paloxes
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_multiple_SameType_TU_Full()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(505) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("505", "430", "75", "0"));

		final BigDecimal amountToSplit = BigDecimal.valueOf(25); // split off 25kg off the Paloxe x 430kg
		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				amountToSplit,
				BigDecimal.valueOf(18), // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_430, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 18, tradingUnitsTarget.size());

		//
		// Assert data integrity on SOURCE Paloxe
		//
		Assert.assertEquals("Source TU is destroyed", X_M_HU.HUSTATUS_Destroyed, paloxeSource.getHUStatus());

		//
		// Assert data integrity on TARGET Paloxes
		//
		for (final I_M_HU tradingUnitTarget : tradingUnitsTarget)
		{
			Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, tradingUnitTarget.getHUStatus());
		}
		final HUWeightsExpectation<Object> defaultWeightExpectation = newHUWeightsExpectation("100", "25", "75", "0");
		assertTradingUnitsWeightExpectations(tradingUnitsTarget, defaultWeightExpectation,
				defaultWeightExpectation, // 1
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation, // 6
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation, // 11
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation, // 16
				defaultWeightExpectation,
				newHUWeightsExpectation("80", "5", "75", "0")); // 18th Paloxe only has 5kg
	}

	/**
	 * S095.1: Split full qty off Paloxe on multiple Paloxes, but weight is under the expected one
	 */
	@Test
	public void testSplitWeightTransfer_TU_on_multiple_SameType_TU_Full_DifferWeight()
	{
		final List<I_M_HU> tradingUnitsSource = createIncomingTradingUnits(materialItemTomato_430, // Paloxe x 430kg
				materialItemProductTomato_430,
				BigDecimal.valueOf(430), // Qty
				BigDecimal.valueOf(500) // WeightGross (430Net + 75Tara)
		);

		Assert.assertEquals("Invalid amount of TUs were created", 1, tradingUnitsSource.size());
		final I_M_HU paloxeSource = tradingUnitsSource.get(0);

		final IAttributeStorage attributeStoragePaloxeSource = attributeStorageFactory.getAttributeStorage(paloxeSource);
		assertSingleHandlingUnitWeights(attributeStoragePaloxeSource, newHUWeightsExpectation("500", "425", "75", "0"));

		final BigDecimal amountToSplit = BigDecimal.valueOf(25); // split off 25kg off the Paloxe x 430kg
		final List<I_M_HU> tradingUnitsTarget = helper.splitHUs(huContext,
				paloxeSource,
				pTomato,
				BigDecimal.valueOf(430), // CU Qty
				uomKg,
				amountToSplit,
				BigDecimal.valueOf(18), // tuPerLU
				BigDecimal.ZERO, // maxLUToAllocate
				materialItemTomato_430, // TU item x 430
				helper.huDefItemNone // split on NoPI (TUs which are split will not be on an LU)
				);

		Assert.assertEquals("Invalid amount of TUs were split", 18, tradingUnitsTarget.size());

		//
		// Assert data integrity on SOURCE Paloxe
		//
		Assert.assertEquals("Source TU is destroyed", X_M_HU.HUSTATUS_Destroyed, paloxeSource.getHUStatus());

		//
		// Assert data integrity on TARGET Paloxes
		//
		for (final I_M_HU tradingUnitTarget : tradingUnitsTarget)
		{
			Assert.assertEquals("Target TU is planning", X_M_HU.HUSTATUS_Planning, tradingUnitTarget.getHUStatus());
		}
		final HUWeightsExpectation<Object> defaultWeightExpectation = newHUWeightsExpectation("99.709", "24.709", "75", "0");
		assertTradingUnitsWeightExpectations(tradingUnitsTarget, defaultWeightExpectation,
				defaultWeightExpectation, // 1
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation, // 6
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation, // 11
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation,
				defaultWeightExpectation, // 16
				defaultWeightExpectation,
				newHUWeightsExpectation("79.942", "4.942", "75", "0")); // 18th Paloxe only has 4.942kg
	}
}
