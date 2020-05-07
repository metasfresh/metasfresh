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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Node;

import de.metas.handlingunits.HUXmlConverter;
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
	private static final BigDecimal GROSS_WEIGHT_101 = BigDecimal.valueOf(101);

	/**
	 * Just a minor "guard" test to verify that {@link #setWeightTareAdjust(I_M_HU, BigDecimal)} yields the expected results.
	 */
	@Test
	public void testTareAdjust()
	{
		// create a palet with IFCOs each of which can hold 10 kg of tomatoes
		// load it with "85 tomatoes" => 9 IFCOs
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, GROSS_WEIGHT_101); // 85 x Tomato

		// guard: we expect a tare of 34 because:
		// the palet M_Product is created with M_Product.Weight=25kg and the IFCO M_Product is created with M_Product.Weight=1kg, so: 1*25 + 9*1 = 34
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9/* aggregate VHU and "real" "IFCO */,
				newHUWeightsExpectation("101"/* gross */, "67"/* net */, "34"/* tare */, "0"/* tareAdjust */), // weights on the LU level

				// the real one IFCO HU with the remaining 6xTomato: net = (80/5)*67kg; tare = 1kg; gross=net+tare
				newHUWeightsExpectation("4.941"/* gross */, "3.941"/* net */, "1"/* tare */, "0"/* tareAdj */), //

				// the aggregate VHU that represents 80xTomato: net = (80/85)*67kg; tare = number of represented IFCOs times 1kg; gross=net+tare
				newHUWeightsExpectation("71.059"/* gross */, "63.059"/* net */, "8"/* tare */, "0"/* tareAdj */));

		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9/* aggregate VHU and "real" "IFCO */,
				newHUWeightsExpectation("101"/* gross */, "66"/* net */, "34"/* tare */, "1"/* tareAdjust */),
				newHUWeightsExpectation("4.882"/* gross */, "3.882"/* net */, "1"/* tare */, "0"/* tareAdj */), // the real HU with the remaining 6xTomato
				newHUWeightsExpectation("70.118"/* gross */, "62.118"/* net */, "8"/* tare */, "0"/* tareAdj */) // the aggregate VHU that represents 80xTomato
		);
	}

	/**
	 * S010: Split 2 x TU from source LU on an additional LU of the same type as the source
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_NoPI()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, BigDecimal.valueOf(105)); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.valueOf(5));

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("105", "66", "34", "5"), newHUWeightsExpectation("4.882", "3.882", "1", "0"), newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		final List<I_M_HU> splitTUs = splitLU(paletToSplit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		assertEquals("Invalid amount of TUs were split", 2, splitTUs.size());

		// commitAndDump(paletToSplit);

		//
		// Assert data integrity on SOURCE LU
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 7,
				newHUWeightsExpectation("87.470", "50.470", "32"/* 25+7 */, "5"), newHUWeightsExpectation("4.882", "3.882", "1", "0"), newHUWeightsExpectation("52.588", "46.588", "6", "0"));

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
				newHUWeightsExpectation("103", "66", "34", "3"), newHUWeightsExpectation("4.882", "3.882", "1", "0") // CuQty=5
				, newHUWeightsExpectation("70.118", "62.118", "8", "0") // CuQty=80
		);

		final List<I_M_HU> splitLUs = splitLU(paletToSplit,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs Per LU
				BigDecimal.ONE); // split on ONE additional LU

		assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU:
		// * 5 where removed from the partial IFCO and that IFCO is not empty (and doesn't count anymore in this test)
		// * another 15 were removed from the aggregate HU which temporarily left it with 65, and caused a split
		//
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 7,
				newHUWeightsExpectation("85.470", "50.470", "32", "3")
				// one empty newHUWeightsExpectation
				, newHUWeightsExpectation("4.882", "3.882", "1", "0") // CuQty=5
				, newHUWeightsExpectation("52.588", "46.588", "6", "0") // CuQty=60
		);

		//
		// Assert data integrity on TARGET LU
		//
		final I_M_HU splitLU = splitLUs.get(0);
		assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU.getM_HU_Item_Parent_ID() <= 0);

		// commitAndDump(splitLU);

		// of the 20 we transferred here,
		// 10 were coming
		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 2,
				newHUWeightsExpectation("42.530", "15.530", "27", "0"), newHUWeightsExpectation("8.765", "7.765", "1", "0") // CuQty=10; one "real" IFCO with two VHUs that have 5kg each (5kg from former "real"/partial source IFCO and 5kg from the aggregate VHU)
				, newHUWeightsExpectation("8.765", "7.765", "1", "0") // CuQty=10; another "real" IFCO with one VHU that has 10kg, coming from the aggregate VHU
		);
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
				newHUWeightsExpectation("110", "66", "34", "10"), newHUWeightsExpectation("4.882", "3.882", "1", "0"), newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		final List<I_M_HU> splitLUs = splitLU(paletToSplit,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(3), // TUs Per LU
				BigDecimal.ONE); // split on ONE additional LU

		assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 6,
				newHUWeightsExpectation("83.706", "42.706", "31"/* 25+6 */, "10"), newHUWeightsExpectation("4.882", "3.882", "1", "0"), newHUWeightsExpectation("43.823", "38.823", "5", "0"));

		//
		// Assert data integrity on destination LU
		final I_M_HU splitLU = splitLUs.get(0);
		assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 3,
				newHUWeightsExpectation("51.295", "23.295", "28", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));
	}

	/**
	 * S030: Split 1 x TU with 7 CUs from the source LU onto an additional LU of the same type as the source.
	 * 
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_NoPI_1TU_7CU()
	{
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, GROSS_WEIGHT_101); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9/* tuCount=1 because there shall be one aggregated VHU */,
				newHUWeightsExpectation("101"/* gross */, "66"/* net */, "34"/* tare */, "1"/* tareAdj */) // 35 + 66 = 101; the 35 are consisting of the palets weight (25), the IFCOs' weights (9) and the tare adjust (1)
				, newHUWeightsExpectation("4.882"/* gross */, "3.882"/* net */, "1"/* tare */, "0"/* tareAdj */), newHUWeightsExpectation("70.118"/* gross */, "62.118"/* net */, "8"/* tare */, "0"/* tareAdj */));

		final List<I_M_HU> splitTUs = splitLU(paletToSplit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				BigDecimal.valueOf(7), // split 7 CUs of the TU
				BigDecimal.valueOf(1), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		assertEquals("Invalid amount of TUs were split", 1, splitTUs.size());
		// commitAndDump(paletToSplit);
		//
		// Assert data integrity on SOURCE LU
		// if 85 is 66kg kg, then 7 is 5.436, so the new net shall be 66 - 5.436 = 60.564
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 8,
				newHUWeightsExpectation("94.565", "60.565", "33", "1"),

				newHUWeightsExpectation("7.212", "6.212", "1", "0"),
				newHUWeightsExpectation("61.353", "54.353", "7", "0"));

		//
		// Assert data integrity on TARGET TU
		//
		final I_M_HU splitTU = splitTUs.get(0);
		assertTrue("The target TU we just split to shall be a top-level handling unit", splitTU.getM_HU_Item_Parent_ID() <= 0);

		final IAttributeStorage attributeStorageTU = attributeStorageFactory.getAttributeStorage(splitTU);
		assertSingleHandlingUnitWeights(attributeStorageTU, newHUWeightsExpectation("6.436", "5.436", "1", "0"));
	}

	/**
	 * S040: Split 1 x TU from source LU on an additional LU of the same type as the source
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_Another_SameType_LU_1TU_7CU()
	{
		// create a palet with IFCOs each of which can hold 10 kg of tomatoes
		// load it with "85 tomateos" => 9 IFCOs
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, GROSS_WEIGHT_101); // 85 x Tomato

		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101"/* gross */, "66"/* net */, "34"/* tare */, "1"/* tareAdj */) // 35 + 66 = 101; the 35 are consisting of the palets weight (25), the IFCOs' weights (9) and the tare adjust (1)
				, newHUWeightsExpectation("4.882"/* gross */, "3.882"/* net */, "1"/* tare */, "0"/* tareAdj */), newHUWeightsExpectation("70.118"/* gross */, "62.118"/* net */, "8"/* tare */, "0"/* tareAdj */));

		final List<I_M_HU> splitLUs = splitLU(paletToSplit,
				huItemIFCO_10, // split on IFCO
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total/max qty to split.
				BigDecimal.valueOf(7), // CUs per TU
				BigDecimal.valueOf(1), // TUs per LU
				BigDecimal.ONE); // split on ONE additional LU

		assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU
		// if 85 is 66kg kg, then 7 is 5.436, so the new net shall be 66 - 5.436 = 60.564
		// Note that we didn't remove a TU from the source LU
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 8,
				newHUWeightsExpectation("94.565", "60.565", "33", "1"),

				newHUWeightsExpectation("7.212", "6.212", "1", "0"),
				newHUWeightsExpectation("61.353", "54.353", "7", "0"));

		//
		// Assert data integrity on TARGET LU
		//
		final I_M_HU splitLU = splitLUs.get(0);
		assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 1,
				newHUWeightsExpectation("31.435", "5.435", "26", "0"),
				newHUWeightsExpectation("6.435", "5.435", "1", "0"));
	}

	/**
	 * S050: Split 1 x TU from source LU on the SAME source LU
	 */
	@Test
	@Ignore
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
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, GROSS_WEIGHT_101); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101"/* gross */, "66"/* net */, "34"/* tare */, "1"/* tareAdj */) // 35 + 66 = 101; the 35 are consisting of the palets weight (25), the IFCOs' weights (9) and the tare adjust (1)
				, newHUWeightsExpectation("4.882"/* gross */, "3.882"/* net */, "1"/* tare */, "0"/* tareAdj */), newHUWeightsExpectation("70.118"/* gross */, "62.118"/* net */, "8"/* tare */, "0"/* tareAdj */));

		final List<I_M_HU> splitLUs = splitLU(paletToSplit,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.ONE, // TUs per LU
				BigDecimal.valueOf(2)); // split on TWO additional LUs

		assertEquals("Invalid amount of LUs were split", 2, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 7,
				newHUWeightsExpectation("83.471", "50.471", "32"/* 25+7 */, "1"), newHUWeightsExpectation("4.882", "3.882", "1", "0"), newHUWeightsExpectation("52.588", "46.588", "6", "0"));
		//
		// Assert data integrity on TARGET LUs
		final I_M_HU splitLU1 = splitLUs.get(0);
		assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU1.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU1, huItemIFCO_10, 1,
				newHUWeightsExpectation("33.765", "7.765", "26", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		final I_M_HU splitLU2 = splitLUs.get(0);
		assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU2.getM_HU_Item_Parent_ID() <= 0);

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

		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, GROSS_WEIGHT_101); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		final Node paletToSplitXmlAfterTareAdjust = HUXmlConverter.toXml(paletToSplit);
		System.out.println(HUXmlConverter.toString(paletToSplitXmlAfterTareAdjust));

		//
		// do a bunch of guard tests
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101"/* gross */, "66"/* net */, "34"/* tare */, "1"/* tareAdj */) // 35 + 66 = 101; the 35 are consisting of the palets weight (25), the IFCOs' weights (9) and the tare adjust (1)
				, newHUWeightsExpectation("4.882"/* gross */, "3.882"/* net */, "1"/* tare */, "0"/* tareAdj */), newHUWeightsExpectation("70.118"/* gross */, "62.118"/* net */, "8"/* tare */, "0"/* tareAdj */));

		// commitAndDump(paletToSplit);

		final List<I_M_HU> splitTUs = splitLU(paletToSplit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				BigDecimal.valueOf(5), // 5, split the 5 CUs of the TU
				BigDecimal.valueOf(3), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		assertEquals("Invalid amount of LUs were split", 3, splitTUs.size());

		//
		// Assert data integrity on SOURCE LU
		// Net: if 85 is 66kg, then 15 is 11.647kg, so the new net shall be 66 - 11.647 = 54,353
		// Tare: each TU on paletToSplit holds 10kg; we removed 15kg, so thats one full TU removed => tare decreases by 1kg
		// Note that we didn't remove a TU from the source LU
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_10, 7, newHUWeightsExpectation("87.353", "54.353", "32", "1"), newHUWeightsExpectation("61.353", "54.353", "7", "0"));

		//
		// Assert data integrity on TARGET TUs
		//
		final I_M_HU splitTU1 = splitTUs.get(0);
		assertTrue("The target TU we just split to shall be a top-level handling unit", splitTU1.getM_HU_Item_Parent_ID() <= 0);

		final IAttributeStorage attributeStorageTU1 = attributeStorageFactory.getAttributeStorage(splitTU1);
		assertSingleHandlingUnitWeights(attributeStorageTU1, newHUWeightsExpectation("4.883", "3.883", "1", "0"));

		final I_M_HU splitTU2 = splitTUs.get(1);
		assertTrue("The target TU we just split to shall be a top-level handling unit", splitTU2.getM_HU_Item_Parent_ID() <= 0);

		final IAttributeStorage attributeStorageTU2 = attributeStorageFactory.getAttributeStorage(splitTU2);
		assertSingleHandlingUnitWeights(attributeStorageTU2, newHUWeightsExpectation("4.883", "3.883", "1", "0"));

		final I_M_HU splitTU3 = splitTUs.get(2);
		assertTrue("The target TU we just split to shall be a top-level handling unit", splitTU3.getM_HU_Item_Parent_ID() <= 0);

		final IAttributeStorage attributeStorageTU3 = attributeStorageFactory.getAttributeStorage(splitTU3);
		assertSingleHandlingUnitWeights(attributeStorageTU3, newHUWeightsExpectation("4.883", "3.883", "1", "0"));
	}

	/**
	 * S080: Split 7 x TU from source LU on another LU, but with different TU-PI, each with 5 x CUs
	 */
	@Test
	public void testSplitWeightTransfer_LU_On_1LU_7TU_Different_PI_5CU()
	{
		// materialItemProductTomato_2: 2 x Tomato Per IFCO
		final I_M_HU paletToSplit = createIncomingLoadingUnit(huItemIFCO_2, materialItemProductTomato_2, CU_QTY_46, GROSS_WEIGHT_101); // 85 x Tomato
		setWeightTareAdjust(paletToSplit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_2, 23,
				newHUWeightsExpectation("101", "52", "48", "1"),
				newHUWeightsExpectation("75", "52", "23", "0"));

		// split off towards 7 TUs with 5xTomatoes each => i.e. splitt off 35
		final List<I_M_HU> splitLUs = splitLU(paletToSplit,
				huItemIFCO_5, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_5, // TU item x 5
				CU_QTY_46, // total max qty to split
				materialItemProductTomato_5.getQty(), // 5, split the 5 CUs of the TU
				BigDecimal.valueOf(7), // TUs per LU
				BigDecimal.ONE); // split on ONE additional LU

		assertEquals("Invalid amount of LUs were split", 1, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU
		assertLoadingUnitStorageWeights(paletToSplit, huItemIFCO_2, 6,
				newHUWeightsExpectation("44.435", "12.435", "31", "1"), // not .43 because of the margin of error range (i.e 2 of the rest can be .27)
				newHUWeightsExpectation("2.13", "1.13", "1", "0"),
				newHUWeightsExpectation("16.304", "11.304", "5", "0"));

		//
		// Assert data integrity on TARGET LUs
		// 35

		final I_M_HU splitLU = splitLUs.get(0);
		assertTrue("The target TU we just split to shall be a top-level handling unit", splitLU.getM_HU_Item_Parent_ID() <= 0);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_5, 7,
				newHUWeightsExpectation("71.565", "39.565", "32", "0"),
				newHUWeightsExpectation("46.565", "39.565", "7", "0"));
	}
	
	/**
	 * Commits the current trx and dumps the given {@code hu}'s XML representation.
	 * The idea is do call this from test methods if you what to know the whole picture.
	 * Note that the commit might break tests.
	 * 
	 * @param palet
	 */
	@SuppressWarnings("unused")
	private void commitAndDump(final I_M_HU palet)
	{
		Services.get(ITrxManager.class).commit(helper.trxName);
		System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml(palet)));
	}

}
