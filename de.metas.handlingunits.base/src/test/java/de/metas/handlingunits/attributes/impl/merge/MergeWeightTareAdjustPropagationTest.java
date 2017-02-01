package de.metas.handlingunits.attributes.impl.merge;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attributes.impl.AbstractWeightAttributeTest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;

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
				newHUWeightsExpectation("6.435", "5.435", "1", "0"), // the merge target which now has 7xCU
				newHUWeightsExpectation("7.212", "6.212", "1", "0"), // the merge source which now has 8xCU
				newHUWeightsExpectation("52.588", "46.588", "6", "0"));
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
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.118", "62.118", "8", "0"));

		// split off 2x10kg
		final List<I_M_HU> splitTradingUnits = splitLU(loadingUnit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		Assert.assertEquals("Invalid amount of TUs were split", 2, splitTradingUnits.size());
		// verify the split source
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 7,
				newHUWeightsExpectation("83.471", "50.471", "32", "1"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("52.588", "46.588", "6", "0"));

		//
		// Simulate - take off one of the TUKeys to bind it to the new LU
		// i.e. rejoin one of the 10kg IFOCs we split
		final I_M_HU tradingUnitToJoin = splitTradingUnits.remove(1);
		helper.joinHUs(huContext, loadingUnit, tradingUnitToJoin);
		// verify the join target
		// commitAndDumpHU(loadingUnit);
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 8,
				newHUWeightsExpectation("92.235", "58.235", "33", "1"), // 75xCU
				newHUWeightsExpectation("4.882", "3.882", "1", "0"), // 5xCU
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // 10xCU that was first split and is now joined back
				newHUWeightsExpectation("52.588", "46.588", "6", "0") // 60xCU
		);

		// merge 2xCU onto the existing IFCO that has 5xCU
		// note that splitTradingUnits only contains one IFCO (we removed the other one before we joned it)
		final I_M_HU sourceTUFromOutside = splitTradingUnits.get(0);
		final I_M_HU targetTUInLoadingUnit = findTUInLUWithQty(loadingUnit, 5); // find a TU with 5 x CU

		// we "offer" both TUs to the merge method, but expect it not to touch the second TU since only 2 CU shall be transferred
		helper.mergeTUs(huContext, splitTradingUnits, targetTUInLoadingUnit, getCUProduct(),
				BigDecimal.valueOf(2),
				getCUUOM());

		//
		// Assert data integrity on SOURCE TUs
		// sourceTUFromOutside is the one we merged from, i.e. it now contains not 10 but 8xCU
		assertThat("After we merged 2, there shall still be something left on the source IFCO", splitTradingUnits.get(0).getHUStatus(), is(X_M_HU.HUSTATUS_Planning));
		final IAttributeStorage attributeStorageTU = attributeStorageFactory.getAttributeStorage(sourceTUFromOutside);
		assertSingleHandlingUnitWeights(attributeStorageTU, newHUWeightsExpectation("7.212", "6.212", "1", "0"));

		//
		// Assert data integrity on TARGET LU
		//
		// commitAndDumpHU(loadingUnit);
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 8,
				newHUWeightsExpectation("93.788", "59.788", "33", "1"), // 77xCU
				newHUWeightsExpectation("6.435", "5.435", "1", "0"), // the merge target which now has 7xCU
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // 10xCU that was first split and was later joined back
				newHUWeightsExpectation("52.588", "46.588", "6", "0"));
	}

}
