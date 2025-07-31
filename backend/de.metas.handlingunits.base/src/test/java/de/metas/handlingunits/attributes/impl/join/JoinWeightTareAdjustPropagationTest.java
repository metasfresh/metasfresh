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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attributes.impl.AbstractWeightAttributeTest;
import de.metas.handlingunits.model.I_M_HU;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * NOTE: Tests propagation WITH TareAdjust VARIABLE.
 * <p>
 * Test <i>propagation</i> of the <code>Weight</code> attributes (Gross, Net, Tare, Tare Adjust) and their behavior together.<br>
 * Test <i>HU operations</i> (i.e split, join, merge) and how these attributes are handled/re-propagated together.
 * <p>
 * Theses tests generally go as follows:
 * <li>create HU(s)
 * <li>splitt of stuff
 * <li>now do the join which we want to verify here
 * <li>verify
 *
 * @author al
 */
public class JoinWeightTareAdjustPropagationTest extends AbstractWeightAttributeTest
{
	/**
	 * Tests in this class <i>USUALLY</i> use a gross weight of 101, considered the user's original input.
	 */
	private static final BigDecimal INPUT_GROSS_101 = BigDecimal.valueOf(101);

	/**
	 * J010, J020: Split - start from S010, then join 1 x TU back on the LU; finally, add the last TU back too and make sure the palette looks just like before the split
	 */
	@Test
	public void testJoinWeightTransfer_1TU_AtOnce()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(loadingUnit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.117", "62.117", "8", "0"));

		final List<I_M_HU> splitTradingUnits = splitLU(loadingUnit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		Assertions.assertEquals(2, splitTradingUnits.size(), "Invalid amount of TUs were split");

		//
		// Simulate - take off one of the TUKeys to bind it to the new LU
		final I_M_HU tradingUnitToJoin1 = splitTradingUnits.remove(1);

		helper.joinHUs(huContext, loadingUnit, tradingUnitToJoin1);

		//
		// Assert data integrity on SOURCE LU
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 8,
				newHUWeightsExpectation("92.235", "58.235", "33", "1"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // that's the 1x10kg IFCO we joined
				newHUWeightsExpectation("52.589", "46.589", "6", "0"));

		//
		// Assert data integrity on TARGET TUs
		//
		Assertions.assertEquals(1, splitTradingUnits.size(), "Invalid amount of remaining TUs after join");
		final I_M_HU splitTU1 = splitTradingUnits.getFirst();
		final IAttributeStorage attributeStorageTU1 = attributeStorageFactory.getAttributeStorage(splitTU1);
		assertSingleHandlingUnitWeights(attributeStorageTU1, newHUWeightsExpectation("8.765", "7.765", "1", "0"));

		//
		// Simulate - take off one of the TUKeys to bind it to the new LU
		final I_M_HU tradingUnitToJoin2 = splitTradingUnits.removeFirst();

		helper.joinHUs(huContext, loadingUnit, tradingUnitToJoin2);

		//
		// Assert data integrity on SOURCE LU
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // that's the 1x10kg IFCO we joined
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // that's the second 1x10kg IFCO we joined
				newHUWeightsExpectation("52.589", "46.589", "6", "0") // that's the remainder of the original HU aggregate
		);

		//
		// Assert data integrity on TARGET TUs
		//
		Assertions.assertTrue(splitTradingUnits.isEmpty(), "There shall be no more remaining TUs");
	}

	/**
	 * J030: Split - start from S010, then join 2 x TU back on the LU;
	 */
	@Test
	public void testJoinWeightTransfer_2TUs_AtOnce()
	{
		//
		// First split the palette
		final I_M_HU loadingUnit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(loadingUnit, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.117", "62.117", "8", "0"));

		final List<I_M_HU> splitTradingUnits = splitLU(loadingUnit,
				helper.huDefItemNone, // split on NoPI (TUs which are split will not be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs per LU
				BigDecimal.ZERO); // TUs are not going on an LU

		Assertions.assertEquals(2, splitTradingUnits.size(), "Invalid amount of TUs were split");

		//
		// Simulate - take off one of the TUKeys to bind it to the new LU
		final I_M_HU tradingUnitToJoin1 = splitTradingUnits.remove(1);
		final I_M_HU tradingUnitToJoin2 = splitTradingUnits.removeFirst();

		helper.joinHUs(huContext, loadingUnit, tradingUnitToJoin1, tradingUnitToJoin2);

		//
		// Assert data integrity on SOURCE LU
		//
		assertLoadingUnitStorageWeights(loadingUnit, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // that's the 1x10kg IFCO we joined
				newHUWeightsExpectation("8.765", "7.765", "1", "0"), // that's the second 1x10kg IFCO we joined
				newHUWeightsExpectation("52.589", "46.589", "6", "0") // that's the remainder of the original HU aggregate
		);

		//
		// Assert data integrity on TARGET TUs
		//
		Assertions.assertTrue(splitTradingUnits.isEmpty(), "There shall be no more remaining TUs");
	}

	/**
	 * M030: Split - start from S020, then Merge TU on an LU with another TU from ANOTHER LU
	 */
	@Test
	public void testJoinWeightTransfer_LUs()
	{
		//
		// Target LU
		final I_M_HU targetLU = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, INPUT_GROSS_101); // 85 x Tomato
		setWeightTareAdjust(targetLU, BigDecimal.ONE);

		assertLoadingUnitStorageWeights(targetLU, huItemIFCO_10, 9,
				newHUWeightsExpectation("101", "66", "34", "1"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("70.117", "62.117", "8", "0"));

		final List<I_M_HU> splitLUs = splitLU(targetLU,
				huItemIFCO_10, // split on LU (TUs which are split will be on an LU)
				materialItemTomato_10, // TU item x 10
				CU_QTY_85, // total qty to split
				materialItemProductTomato_10.getQty(), // 10, split the full TU off the source LU
				BigDecimal.valueOf(2), // TUs Per LU
				BigDecimal.ONE); // split on ONE additional LU

		Assertions.assertEquals(1, splitLUs.size(), "Invalid amount of LUs were split");

		//
		// Source LU
		final I_M_HU sourceLU = splitLUs.getFirst();
		Assertions.assertTrue(sourceLU.getM_HU_Item_Parent_ID() <= 0, "The target LU we just split to shall be a top-level handling unit");

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
				newHUWeightsExpectation("92.235", "58.235", "33", "1"),
				newHUWeightsExpectation("4.882", "3.882", "1", "0"),
				newHUWeightsExpectation("8.765", "7.765", "1", "0"),
				newHUWeightsExpectation("52.588", "46.588", "6", "0"));
	}
}
