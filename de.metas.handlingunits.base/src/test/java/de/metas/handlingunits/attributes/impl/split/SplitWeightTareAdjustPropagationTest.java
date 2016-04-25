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
import de.metas.handlingunits.model.I_M_HU;

/**
 * NOTE: Tests propagation WITH TareAdjsut VARIABLE.
 *
 * Test <i>propagation</i> of the <code>Weight</code> attributes (Gross, Net, Tare, Tare Adjust) and their behavior together.<br>
 * Test <i>HU operations</i> (i.e split, join, merge) and how these attributes are handled/re-propagated together.
 *
 * @author al
 */
public class SplitWeightTareAdjustPropagationTest extends AbstractWeightAttributeTest
{
	/**
	 * Tests in this class <i>USUALLY</i> use a gross weight of 101, considered the user's original input.
	 */
	private static final BigDecimal INPUT_GROSS_101 = BigDecimal.valueOf(101);

	/**
	 * S010: Split 2 x TU from source LU on an additional LU of the same type as the source
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_NoPI()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, BigDecimal.valueOf(105)); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.valueOf(5));

		//@formatter:off
		newLUWeightsExpectations()
			.luPIItem(huItemIFCO_10)
			.tuCount(9)
			.luWeightsExpectation()
				.gross("105").net("66").tare("34").tareAdjust("5")
				.endExpectation()
			.tuExpectations()
				.newTUExpectation()
					.gross("8.765").net("7.765").tare("1").tareAdjust("0")
					.endExpectation()
				.newTUExpectation()
					.gross("8.765").net("7.765").tare("1").tareAdjust("0")
					.endExpectation()
				.endExpectation()
			.assertExpected(paletToSplit);
		//@formatter:on

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
				newHUWeightsExpectation("87.470", "50.470", "32", "5"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, BigDecimal.valueOf(103)); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.valueOf(3));

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("103", "66", "34", "3"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
				newHUWeightsExpectation("85.470", "50.470", "32", "3"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, BigDecimal.valueOf(110)); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.TEN);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("110", "66", "34", "10"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
				newHUWeightsExpectation("83.705", "42.705", "31", "10"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
		//
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("95.564", "60.564", "34", "1"),
				newHUWeightsExpectation("3.329", "2.329", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		//
		// Assert data integrity on TARGET TU
		//
		final I_M_HU splitTU = splitTUs.get(0);
		Assert.assertTrue("The target TU we just split to shall be a top-level handling unit", splitTU.getM_HU_Item_Parent_ID() <= 0);

		final IAttributeStorage attributeStorageTU = attributeStorageFactory.getAttributeStorage(splitTU);
		assertSingleHandlingUnitWeights(attributeStorageTU, newHUWeightsExpectation("6.436", "5.436", "1", "0"));
	}

	/**
	 * S040: Split 1 x TU from source LU on an additional LU of the same type as the source
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_Another_SameType_LU_1TU_7CU()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
		//
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("95.564", "60.564", "34", "1"),
				newHUWeightsExpectation("3.329", "2.329", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("4.880", "3.880", "1", "0"));

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
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
				newHUWeightsExpectation("83.470", "50.470", "32", "1"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 8,
				newHUWeightsExpectation("88.352", "54.352", "33", "1"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

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
		assertSingleHandlingUnitWeights(attributeStorageTU2, newHUWeightsExpectation("4.883", "3.883", "1", "0"));

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
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_2, materialItemProductTomato_2, CU_QTY_46, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_2, 23,
				newHUWeightsExpectation("101", "52", "48", "1"),
				newHUWeightsExpectation("3.261", "2.261", "1", "0"),
				newHUWeightsExpectation("3.261", "2.261", "1", "0"));

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
				newHUWeightsExpectation("44.432", "12.432", "31", "1"), // not .43 because of the margin of error range (i.e 2 of the rest can be .27)
				newHUWeightsExpectation("2.13", "1.13", "1", "0"),
				newHUWeightsExpectation("3.261", "2.261", "1", "0"),
				newHUWeightsExpectation("3.261", "2.261", "1", "0"),
				newHUWeightsExpectation("3.261", "2.261", "1", "0"),
				newHUWeightsExpectation("3.261", "2.261", "1", "0"),
				newHUWeightsExpectation("3.258", "2.258", "1", "0"));

		//
		// Assert data integrity on TARGET LUs
		//
		final I_M_HU splitLU = splitLUs.get(0);
		Assert.assertTrue("The target TU we just split to shall be a top-level handling unit", splitLU.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_5, 7,
				newHUWeightsExpectation("71.568", "39.568", "32", "0"),
				newHUWeightsExpectation("6.653", "5.653", "1", "0"),
				newHUWeightsExpectation("6.653", "5.653", "1", "0"),
				newHUWeightsExpectation("6.653", "5.653", "1", "0"),
				newHUWeightsExpectation("6.653", "5.653", "1", "0"),
				newHUWeightsExpectation("6.653", "5.653", "1", "0"),
				newHUWeightsExpectation("6.653", "5.653", "1", "0"),
				newHUWeightsExpectation("6.653", "5.653", "1", "0"));
	}
}
