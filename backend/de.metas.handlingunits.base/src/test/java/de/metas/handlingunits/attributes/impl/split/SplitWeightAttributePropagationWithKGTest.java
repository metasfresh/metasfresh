/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.attributes.impl.split;

import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attributes.impl.AbstractWeightAttributeTest;
import de.metas.handlingunits.expectations.HUWeightsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_UOM;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewTUsRequest;

/**
 * NOTE: Tests propagation WITH TareAdjust CONSTANT ZERO.
 * <p>
 * Test <i>propagation</i> of the <code>Weight</code> attributes (Gross, Net, Tare, Tare Adjust) and their behavior together.<br>
 * Test <i>HU operations</i> (i.e split, join, merge) and how these attributes are handled/re-propagated together.
 *
 * @author al
 */
public class SplitWeightAttributePropagationWithKGTest extends AbstractWeightAttributeTest
{
	/**
	 * Tests in this class use a gross weight of 100, considered the user's original input.
	 */
	private static final BigDecimal INPUT_GROSS_100 = BigDecimal.valueOf(100);

	@Override
	protected I_C_UOM getCUUOM()
	{
		return uomKg;
	}


	@Test
	public void testSplit7TuFromLuWithRealData()
	{
//		final I_M_HU palletToSplit = Services.get(ITrxManager.class).callInNewTrx(() -> {
//					final List<I_M_HU> palletsToSplit = helper.createHUs(huContext, huDefPalet, helper.pTomatoProductId, BigDecimal.valueOf(545.217), helper.uomKg);
//					return palletsToSplit.get(0);
//				});

		final I_M_HU palletToSplit = Services.get(ITrxManager.class).callInNewTrx(() ->
				createIncomingLoadingUnit(
						huItemIFCO_10,
						materialItemProductTomato_10,
						BigDecimal.valueOf(500.217),
						BigDecimal.valueOf(545.217)) // 20 x Tomato TUs with a certain weight. + the weight of the LU+TU
		);

		// dev note: weight per TU is 25.01085 ~= 25.011

		// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa);
		System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml(palletToSplit)));
		// Assert initial data is as expected
		assertLoadingUnitStorageWeights(palletToSplit, huItemIFCO_10, 20,
				newHUWeightsExpectation("545.217", "500.217", "45", "0"),
				newHUWeightsExpectation("520.217", "500.217", "20", "0") // aggregate TU with the whole qty
		);

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		//Assert.assertTrue(handlingUnitsBL.isAggregateHU(palletToSplit)); // the pallet isn'T an aggregated HU, but the oneside

		// // TODO tbp: i don't understand what all these params are and what do they do?
		// // 		i need to move 1 TU. i don't care about the CUs, but the TU.
		// final List<I_M_HU> splitLUs = splitLU(palletToSplit,
		// 		helper.huDefItemNone, // luPIItem
		// 		materialItemTomato_10, // TU item x 10
		// 		BigDecimal.valueOf(200), // cuQty  // TODO tbp: how to say "i want to split 7 TUs?"
		// 		BigDecimal.valueOf(1), // cuPerTu
		// 		BigDecimal.valueOf(1), // tuPerLu
		// 		BigDecimal.ZERO); // maxLuToAllocate

		// do the splits
		final IMutableHUContext huContext = helper.getHUContext().copyAsMutable();
		final HUsToNewTUsRequest request = HUsToNewTUsRequest.forSourceHuAndQty(palletToSplit, 1);
		final List<I_M_HU> splitLUs = HUTransformService.newInstance(huContext)
				.husToNewTUs(request);

		Assert.assertEquals("Invalid amount of LUs were split", 7, splitLUs.size());

		//
		// Assert data integrity on SOURCE LU
		assertLoadingUnitStorageWeights(palletToSplit, huItemIFCO_10, 13,
				newHUWeightsExpectation("363.140", "325.140", "38", "0")
				// ,
				//  // TODO tbp:
				//   - 13 TU with net weight 25.011 (total net 300.132)
				//	 -  1 TU with net weight 25.008 (total net 25.008)

				// newHUWeightsExpectation("494.20615", "325.140", "19", "0") // 19 TUs left (aggregated)
		);

		//
		// Assert data integrity on TARGET LU
		for (final I_M_HU splitLU : splitLUs)
		{
			Assert.assertTrue("The target LU we just split to shall be a top-level handling unit", splitLU.getM_HU_Item_Parent_ID() <= 0);
			Assert.assertTrue(handlingUnitsBL.isTransportUnit(splitLU));

			assertLoadingUnitStorageWeights(splitLU, huItemIFCO_10, 1,
					newHUWeightsExpectation("26.011", "25.011", "1", "0")
			);
		}
	}
}
