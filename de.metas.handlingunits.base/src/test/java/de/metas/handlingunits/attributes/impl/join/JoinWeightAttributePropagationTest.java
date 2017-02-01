package de.metas.handlingunits.attributes.impl.join;

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

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.junit.Assert;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

import de.metas.handlingunits.IHandlingUnitsBL;
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
public class JoinWeightAttributePropagationTest extends AbstractWeightAttributeTest
{
	/**
	 * Tests in this class use a gross weight of 100, considered the user's original input.
	 */
	private static final BigDecimal INPUT_GROSS_100 = BigDecimal.valueOf(100);

	/**
	 * J010, J020: Split - start from S010, then join 1 x TU back on the LU; finally, add the last TU back too and make sure the palette looks just like before the split
	 */
	@Test
	public void testJoinWeightTransfer_1TU_AtOnce()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.117", "62.117", "8", "0"));

		// split off 2x10kg IFCOs
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
		final I_M_HU tradingUnitToJoin1 = splitTradingUnits.remove(1);

		// join 1x10kg IFCO
		helper.joinHUs(huContext, loadingUnit, tradingUnitToJoin1);

		//
		// Assert data integrity on loadingUnit after the join
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 8,
				newHUWeightsExpectation("91.235", "58.235", "33", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // that's the 1x10kg IFCO we joined
				newHUWeightsExpectation("52.589", "46.589", "6", "0"));

		//
		// check out the HU which we just joined
		Assert.assertEquals("Invalid amount of remaining TUs after join", 1, splitTradingUnits.size());
		final I_M_HU splitTU1 = splitTradingUnits.get(0);
		final IAttributeStorage attributeStorageTU1 = attributeStorageFactory.getAttributeStorage(splitTU1);
		assertSingleHandlingUnitWeights(attributeStorageTU1, newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		//
		// Simulate - take off one of the TUKeys to bind it to the new LU
		final I_M_HU tradingUnitToJoin2 = splitTradingUnits.remove(0);

		// join another 1x10kg
		helper.joinHUs(huContext, loadingUnit, tradingUnitToJoin2);

		//
		// Assert data integrity on loadingUnit after the 2nd join
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // that's the 1x10kg IFCO we joined
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // that's the second 1x10kg IFCO we joined
				newHUWeightsExpectation("52.589", "46.589", "6", "0") // that's the remainder of the original HU aggregate
		);

		//
		// Assert data integrity on TARGET TUs
		//
		Assert.assertTrue("There shall be no more remaining TUs", splitTradingUnits.isEmpty());
	}

	/**
	 * J030: Split - start from S010, then join 2 x TU back on the LU;
	 */
	@Test
	public void testJoinWeightTransfer_2TUs_AtOnce()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.117", "62.117", "8", "0"));

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
		final I_M_HU tradingUnitToJoin1 = splitTradingUnits.remove(1);
		final I_M_HU tradingUnitToJoin2 = splitTradingUnits.remove(0);

		helper.joinHUs(huContext, loadingUnit, tradingUnitToJoin1, tradingUnitToJoin2);

		//
		// Assert data integrity on loadingUnit after the join
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // that's the 1x10kg IFCO we joined
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // that's the second 1x10kg IFCO we joined
				newHUWeightsExpectation("52.589", "46.589", "6", "0") // that's the remainder of the original HU aggregate
		);

		//
		// Assert data integrity on TARGET TUs
		//
		Assert.assertTrue("There shall be no more remaining TUs", splitTradingUnits.isEmpty());
	}

	/**
	 * Merge LUs, but FULLY - old LU shall be destroyed
	 */
	@Test
	public void testJoinFullWeightTransfer_LUs()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		//
		// Take 2 x TU from the palette and move them to a new LU
		final List<I_M_HU> splitLUs = splitLU(loadingUnit,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs Per LU
				BigDecimal.ONE); // split on ONE additional LU

		assertThat("Invalid amount of LUs were split", splitLUs.size(), is(1));
		final I_M_HU splitLU = splitLUs.get(0);
		// helper.commitAndDumpHU(splitLU);

		assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 2,
				newHUWeightsExpectation("42.530", "15.530", "27", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		//
		// move the 2 split TUs back to the original palette and make sure that their LU was destroyed
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final List<I_M_HU> tradingUnitsInSplitLU = handlingUnitsDAO.retrieveIncludedHUs(splitLU).stream()
				.filter(hu -> !handlingUnitsBL.isAggregateHU(hu)).collect(Collectors.toList());
		
		assertThat(tradingUnitsInSplitLU.size(), is(2));
		helper.joinHUs(huContext, loadingUnit, tradingUnitsInSplitLU);

		// because splitLU is updated elsewhere, we need to refresh out instance.
		InterfaceWrapperHelper.refresh(splitLU);
		Assert.assertEquals("Source LU is destroyed", splitLU.getHUStatus(), X_M_HU.HUSTATUS_Destroyed);

		//
		// Assert data integrity for TARGET LU
		// Source LU and TU will be destroyed
		//
		// helper.commitAndDumpHU(loadingUnit);
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"), // 5xCU
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // 10xCU that was split and then joined back
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // 10xCU that was split and then joined back
				newHUWeightsExpectation("52.588", "46.588", "6", "0")); // 60xCU
	}

	/**
	 * Start from S020, then Merge TU on an LU with another TU from ANOTHER LU
	 */
	@Test
	public void testMergeWeightTransfer_LUs()
	{
		//
		// Target LU
		final I_M_HU targetLU = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_100); // 85 x Tomato
		assertLoadingUnitStorageWeights(targetLU, huItemIFCO_10, 9,
				newHUWeightsExpectation("100", "66", "34", "0"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.118", "62.118", "8", "0"));

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
		helper.joinHUs(huContext, targetLU, sourceTradingUnitWith10);

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
				newHUWeightsExpectation("4.882", "3.882", "1", "0"), // the original and unchanged partion IFCO with 5xCU
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // the 10xCU which we merged
				newHUWeightsExpectation("52.588", "46.588", "6", "0") // the remainder of the original aggregate HU
		);
	}
}
