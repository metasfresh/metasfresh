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

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attributes.impl.AbstractWeightAttributeTest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.receiptschedule.impl.HUReceiptScheduleWeightNetAdjuster;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewTUsRequest;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.xmlunit.assertj.XmlAssert.assertThat;

/**
 * Misc:
 * LU net weight = 25Kg
 * huItemIFCO_10 weight = 1Kg
 * => Gross weight of 20 TU = 25 + 20 + net LU weight
 */
public class SplitLUsWithOddQuantitiesTest extends AbstractWeightAttributeTest
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@Override
	protected I_C_UOM getCUUOM()
	{
		return uomKg;
	}

	/**
	 * <pre>
	 * LU net weight = 125.457
	 * TUs = 20
	 * => kg/tu = 6.272
	 *
	 * run action with 1 TU param
	 *
	 * results:
	 * 1 TU x 6.272 Kg
	 *
	 * left on pallet: 125.357-6.272 = 119.185 Kg
	 * 19 TU x 119.185 Kg
	 *
	 * We don't care about Kg/TU, we only care that after splitting 1 TU, we're left with the correct amount of Kg and of TUs on the LU.
	 * </pre>
	 */
	@Test
	@DisplayName("1 LU x 20 TU; 125.457 Kg Net Weight; Split 1 TU")
	public void split1TuFromLUx20TUNet125_457()
	{
		final BigDecimal initialNetWeight = BigDecimal.valueOf(125.457);

		// given
		// the LU contains one homogenous/aggregate HU with 20 TUs each of which has 10 kg => 200 CUs
		final I_M_HU palletToSplit = Services.get(ITrxManager.class).callInNewTrx(() -> {
			final I_M_HU lu = helper.createLU(
					huContext,
					huItemIFCO_10,
					materialItemProductTomato_10,
					BigDecimal.valueOf(200));

			/*set weight attributes; does not yet affect storage, but is required for the weight-adjuster to work later*/
			final IAttributeStorage attributeStorageLoadingUnit = attributeStorageFactory.getAttributeStorage(lu);
			attributeStorageLoadingUnit.setValue(attr_WeightNet, initialNetWeight);
			assertLoadingUnitStorageWeights(lu, huItemIFCO_10, 20,
					newHUWeightsExpectation("170.457", initialNetWeight.toString(), "45", "0"), // LU weight
					newHUWeightsExpectation("145.457", initialNetWeight.toString(), "20", "0") // aggregate TU with the whole qty
			);

			return lu;
		});

		// note that we use HUReceiptScheduleWeightNetAdjuster because currently this is the only code that AFAIK does weight adjusting
		// this will change very soon, but until then i'm trying not to touch any of the code that is currently worked on, to avoid change conflicts
		final I_M_ReceiptSchedule receiptScheduleRecord = prepareReceiptSchedule(palletToSplit, initialNetWeight);
		final HUReceiptScheduleWeightNetAdjuster rsNetWeightAdjuster = new HUReceiptScheduleWeightNetAdjuster(helper.getCtx(), helper.trxName);
		rsNetWeightAdjuster.addReceiptSchedule(receiptScheduleRecord);

		// Assert initial data is as expected: 1 LU with 20 TUs and 125.457 CUs
		final Node xmlPalletBeforeSplit = HUXmlConverter.toXml(palletToSplit);
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item[@ItemType='HA'])").as("LU has 1 aggregate item").isEqualTo("1");
		assertThat(xmlPalletBeforeSplit).valueByXPath("string(HU-Palet/Item[@ItemType='HA']/@Qty)").as("LU has 1 aggregate item").isEqualTo(20);
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item[@ItemType='PM'])").as("LU has 1 packing item").isEqualTo("1");
		assertThat(xmlPalletBeforeSplit).valueByXPath("string(HU-Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo(initialNetWeight.toString());
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item)").as("LU has 2 items").isEqualTo("2");

		//
		// when
		final HUsToNewTUsRequest request = HUsToNewTUsRequest.forSourceHuAndQty(palletToSplit, 1);
		final List<I_M_HU> splitTUs = HUTransformService.newInstance(helper.getHUContext()).husToNewTUs(request);

		//
		// then
		Assertions.assertThat(splitTUs).hasSize(1); // in this test, we static imported XMLAssert

		final Node xmlSplitTU = HUXmlConverter.toXml(splitTUs.get(0));
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO)").asInt().isEqualTo(1);
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item[@ItemType='MI'])").as("TU has 1 material item").isEqualTo("1");
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item[@ItemType='PM'])").as("TU has 1 packing item").isEqualTo("1");
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item)").as("TU has 2 items").isEqualTo("2");
		assertThat(xmlSplitTU).valueByXPath("string(HU-IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("6.272");

		final Node xmlPalletAfterSplit = HUXmlConverter.toXml(palletToSplit);
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item[@ItemType='HA'])").as("LU still has 1 aggregate item").isEqualTo("1");
		assertThat(xmlPalletAfterSplit).valueByXPath("string(HU-Palet/Item[@ItemType='HA']/@Qty)").as("LU has less TUs").isEqualTo(19);
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item[@ItemType='PM'])").as("LU still has 1 packing item").isEqualTo("1");
		assertThat(xmlPalletAfterSplit).valueByXPath("string(HU-Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("119.185");
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item)").as("LU still has 2 items").isEqualTo("2");
	}

	/**
	 * <pre>
	 * LU net weight = 500.217 Kg
	 * TUs = 20
	 *  => Kg/TU = 25.010
	 *
	 * run action with 7 TU param
	 *
	 * results
	 * 7 TU x 25.010 Kg = 175.070
	 *
	 * left on pallet = 500.217 - 175.070 = 325.147 Kg
	 * 13 TU x 25.010 Kg
	 *
	 * We don't care about Kg/TU, we only care that after splitting 1 TU, we're left with the correct amount of Kg and of TUs on the LU.
	 * </pre>
	 */
	@Test
	@DisplayName("1 LU x 20 TU; 500.217 Kg Net Weight; Split 7 TU")
	public void split7TuFromLUx20TUNet500_217()
	{
		final BigDecimal initialNetWeight = BigDecimal.valueOf(500.217);

		// given
		// the LU contains one homogenous/aggregate HU with 20 TUs each of which has 10 kg => 200 CUs
		final I_M_HU palletToSplit = Services.get(ITrxManager.class).callInNewTrx(() -> {
			final I_M_HU lu = helper.createLU(
					huContext,
					huItemIFCO_10,
					materialItemProductTomato_10,
					BigDecimal.valueOf(200));

			/*set weight attributes; does not yet affect storage, but is required for the weight-adjuster to work later*/
			final IAttributeStorage attributeStorageLoadingUnit = attributeStorageFactory.getAttributeStorage(lu);
			attributeStorageLoadingUnit.setValue(attr_WeightNet, initialNetWeight);
			assertLoadingUnitStorageWeights(lu, huItemIFCO_10, 20,
					newHUWeightsExpectation("545.217", initialNetWeight.toString(), "45", "0"), // LU weight
					newHUWeightsExpectation("520.217", initialNetWeight.toString(), "20", "0") // aggregate TU with the whole qty
			);

			return lu;
		});

		// note that we use HUReceiptScheduleWeightNetAdjuster because currently this is the only code that AFAIK does weight adjusting
		// this will change very soon, but until then i'm trying not to touch any of the code that is currently worked on, to avoid change conflicts
		final I_M_ReceiptSchedule receiptScheduleRecord = prepareReceiptSchedule(palletToSplit, initialNetWeight);
		final HUReceiptScheduleWeightNetAdjuster rsNetWeightAdjuster = new HUReceiptScheduleWeightNetAdjuster(helper.getCtx(), helper.trxName);
		rsNetWeightAdjuster.addReceiptSchedule(receiptScheduleRecord);

		// Assert initial data is as expected: 1 LU with 20 TUs and 500.217 CUs
		final Node xmlPalletBeforeSplit = HUXmlConverter.toXml(palletToSplit);
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item[@ItemType='HA'])").as("LU has 1 aggregate item").isEqualTo("1");
		assertThat(xmlPalletBeforeSplit).valueByXPath("string(HU-Palet/Item[@ItemType='HA']/@Qty)").as("LU has 1 aggregate item").isEqualTo(20);
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item[@ItemType='PM'])").as("LU has 1 packing item").isEqualTo("1");
		assertThat(xmlPalletBeforeSplit).valueByXPath("string(HU-Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo(initialNetWeight.toString());
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item)").as("LU has 2 items").isEqualTo("2");

		//
		// when
		final HUsToNewTUsRequest request = HUsToNewTUsRequest.forSourceHuAndQty(palletToSplit, 7);
		final List<I_M_HU> splitTUs = HUTransformService.newInstance(helper.getHUContext()).husToNewTUs(request);

		//
		// then
		Assertions.assertThat(splitTUs).hasSize(7); // in this test, we static imported XMLAssert
		splitTUs.forEach(tu -> {
			final Node xmlSplitTU = HUXmlConverter.toXml(tu);
			assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO)").asInt().isEqualTo(1);
			assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item[@ItemType='MI'])").as("TU has 1 material item").isEqualTo("1");
			assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item[@ItemType='PM'])").as("TU has 1 packing item").isEqualTo("1");
			assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item)").as("TU has 2 items").isEqualTo("2");
			assertThat(xmlSplitTU).valueByXPath("string(HU-IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("25.010");
		});

		final Node xmlPalletAfterSplit = HUXmlConverter.toXml(palletToSplit);
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item[@ItemType='HA'])").as("LU still has 1 aggregate item").isEqualTo("1");
		assertThat(xmlPalletAfterSplit).valueByXPath("string(HU-Palet/Item[@ItemType='HA']/@Qty)").as("LU has less TUs").isEqualTo(13);
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item[@ItemType='PM'])").as("LU still has 1 packing item").isEqualTo("1");
		assertThat(xmlPalletAfterSplit).valueByXPath("string(HU-Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("325.147");
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item)").as("LU still has 2 items").isEqualTo("2");
	}

	/**
	 * <pre>
	 * LU net weight = 98.456
	 * TUs = 40
	 * => Kg/TU = 2.461
	 *
	 * run action with 1 TU param
	 *
	 * results
	 * 1 TU x 2.461 Kg
	 *
	 * left on pallet = 98.456-2.461 = 95.995 Kg
	 * 39 TU x 95.995 Kg
	 *
	 * We don't care about Kg/TU, we only care that after splitting 1 TU, we're left with the correct amount of Kg and of TUs on the LU.
	 * </pre>
	 */
	@Test
	@DisplayName("1 LU x 40 TU; 98.456 Kg Net Weight; Split 1 TU")
	public void split1TuFromLUx40TUNet98_456()
	{
		final BigDecimal initialNetWeight = BigDecimal.valueOf(98.456);

		// given
		// the LU contains one homogenous/aggregate HU with 40 TUs each of which has 10 kg => 400 CUs
		final I_M_HU palletToSplit = Services.get(ITrxManager.class).callInNewTrx(() -> {
			final I_M_HU lu = helper.createLU(
					huContext,
					huItemIFCO_10,
					materialItemProductTomato_10,
					BigDecimal.valueOf(400));

			/*set weight attributes; does not yet affect storage, but is required for the weight-adjuster to work later*/
			final IAttributeStorage attributeStorageLoadingUnit = attributeStorageFactory.getAttributeStorage(lu);
			attributeStorageLoadingUnit.setValue(attr_WeightNet, initialNetWeight);
			assertLoadingUnitStorageWeights(lu, huItemIFCO_10, 40,
					newHUWeightsExpectation("163.456", initialNetWeight.toString(), "65", "0"), // LU weight
					newHUWeightsExpectation("138.456", initialNetWeight.toString(), "40", "0") // aggregate TU with the whole qty
			);

			return lu;
		});

		// note that we use HUReceiptScheduleWeightNetAdjuster because currently this is the only code that AFAIK does weight adjusting
		// this will change very soon, but until then i'm trying not to touch any of the code that is currently worked on, to avoid change conflicts
		final I_M_ReceiptSchedule receiptScheduleRecord = prepareReceiptSchedule(palletToSplit, initialNetWeight);
		final HUReceiptScheduleWeightNetAdjuster rsNetWeightAdjuster = new HUReceiptScheduleWeightNetAdjuster(helper.getCtx(), helper.trxName);
		rsNetWeightAdjuster.addReceiptSchedule(receiptScheduleRecord);

		// Assert initial data is as expected: 1 LU with 40 TUs and 98.456 CUs
		final Node xmlPalletBeforeSplit = HUXmlConverter.toXml(palletToSplit);
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item[@ItemType='HA'])").as("LU has 1 aggregate item").isEqualTo("1");
		assertThat(xmlPalletBeforeSplit).valueByXPath("string(HU-Palet/Item[@ItemType='HA']/@Qty)").as("LU has 1 aggregate item").isEqualTo(40);
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item[@ItemType='PM'])").as("LU has 1 packing item").isEqualTo("1");
		assertThat(xmlPalletBeforeSplit).valueByXPath("string(HU-Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo(initialNetWeight.toString());
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item)").as("LU has 2 items").isEqualTo("2");

		//
		// when
		final HUsToNewTUsRequest request = HUsToNewTUsRequest.forSourceHuAndQty(palletToSplit, 1);
		final List<I_M_HU> splitTUs = HUTransformService.newInstance(helper.getHUContext()).husToNewTUs(request);

		//
		// then
		Assertions.assertThat(splitTUs).hasSize(1); // in this test, we static imported XMLAssert

		final Node xmlSplitTU = HUXmlConverter.toXml(splitTUs.get(0));
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO)").asInt().isEqualTo(1);
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item[@ItemType='MI'])").as("TU has 1 material item").isEqualTo("1");
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item[@ItemType='PM'])").as("TU has 1 packing item").isEqualTo("1");
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item)").as("TU has 2 items").isEqualTo("2");
		assertThat(xmlSplitTU).valueByXPath("string(HU-IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("2.461");

		final Node xmlPalletAfterSplit = HUXmlConverter.toXml(palletToSplit);
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item[@ItemType='HA'])").as("LU still has 1 aggregate item").isEqualTo("1");
		assertThat(xmlPalletAfterSplit).valueByXPath("string(HU-Palet/Item[@ItemType='HA']/@Qty)").as("LU has less TUs").isEqualTo(39);
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item[@ItemType='PM'])").as("LU still has 1 packing item").isEqualTo("1");
		assertThat(xmlPalletAfterSplit).valueByXPath("string(HU-Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("95.995");
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item)").as("LU still has 2 items").isEqualTo("2");
	}

	/**
	 * <pre>
	 * LU net weight = 200.119
	 * TUs = 20
	 * => kg/TU = 10.005
	 *
	 * run action with 1 TU param
	 *
	 * results:
	 * 1 TU x 10.005
	 *
	 * left on pallet: 200.119 - 10.005 = 190.114 Kg
	 * => 19 TU 190.114 Kg
	 *
	 * We don't care about Kg/TU, we only care that after splitting 1 TU, we're left with the correct amount of Kg and of TUs on the LU.
	 * </pre>
	 */
	@Test
	@DisplayName("1 LU x 20 TU; 200.119 Kg Net Weight; Split 1 TU")
	public void split1TuFromLUx20TUNet200_119()
	{
		final BigDecimal initialNetWeight = BigDecimal.valueOf(200.119);

		// given
		// the LU contains one homogenous/aggregate HU with 20 TUs each of which has 10 kg => 200 CUs
		final I_M_HU palletToSplit = Services.get(ITrxManager.class).callInNewTrx(() -> {
			final I_M_HU lu = helper.createLU(
					huContext,
					huItemIFCO_10,
					materialItemProductTomato_10,
					BigDecimal.valueOf(200));

			/*set weight attributes; does not yet affect storage, but is required for the weight-adjuster to work later*/
			final IAttributeStorage attributeStorageLoadingUnit = attributeStorageFactory.getAttributeStorage(lu);
			attributeStorageLoadingUnit.setValue(attr_WeightNet, initialNetWeight);
			assertLoadingUnitStorageWeights(lu, huItemIFCO_10, 20,
					newHUWeightsExpectation("245.119", initialNetWeight.toString(), "45", "0"), // LU weight
					newHUWeightsExpectation("220.119", initialNetWeight.toString(), "20", "0") // aggregate TU with the whole qty
			);

			return lu;
		});

		// note that we use HUReceiptScheduleWeightNetAdjuster because currently this is the only code that AFAIK does weight adjusting
		// this will change very soon, but until then i'm trying not to touch any of the code that is currently worked on, to avoid change conflicts
		final I_M_ReceiptSchedule receiptScheduleRecord = prepareReceiptSchedule(palletToSplit, initialNetWeight);
		final HUReceiptScheduleWeightNetAdjuster rsNetWeightAdjuster = new HUReceiptScheduleWeightNetAdjuster(helper.getCtx(), helper.trxName);
		rsNetWeightAdjuster.addReceiptSchedule(receiptScheduleRecord);

		// Assert initial data is as expected: 1 LU with 20 TUs and 200.119 CUs
		final Node xmlPalletBeforeSplit = HUXmlConverter.toXml(palletToSplit);
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item[@ItemType='HA'])").as("LU has 1 aggregate item").isEqualTo("1");
		assertThat(xmlPalletBeforeSplit).valueByXPath("string(HU-Palet/Item[@ItemType='HA']/@Qty)").as("LU has 1 aggregate item").isEqualTo(20);
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item[@ItemType='PM'])").as("LU has 1 packing item").isEqualTo("1");
		assertThat(xmlPalletBeforeSplit).valueByXPath("string(HU-Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo(initialNetWeight.toString());
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item)").as("LU has 2 items").isEqualTo("2");

		//
		// when
		final HUsToNewTUsRequest request = HUsToNewTUsRequest.forSourceHuAndQty(palletToSplit, 1);
		final List<I_M_HU> splitTUs = HUTransformService.newInstance(helper.getHUContext()).husToNewTUs(request);

		//
		// then
		Assertions.assertThat(splitTUs).hasSize(1); // in this test, we static imported XMLAssert

		final Node xmlSplitTU = HUXmlConverter.toXml(splitTUs.get(0));
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO)").asInt().isEqualTo(1);
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item[@ItemType='MI'])").as("TU has 1 material item").isEqualTo("1");
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item[@ItemType='PM'])").as("TU has 1 packing item").isEqualTo("1");
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item)").as("TU has 2 items").isEqualTo("2");
		assertThat(xmlSplitTU).valueByXPath("string(HU-IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("10.005");

		final Node xmlPalletAfterSplit = HUXmlConverter.toXml(palletToSplit);
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item[@ItemType='HA'])").as("LU still has 1 aggregate item").isEqualTo("1");
		assertThat(xmlPalletAfterSplit).valueByXPath("string(HU-Palet/Item[@ItemType='HA']/@Qty)").as("LU has less TUs").isEqualTo(19);
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item[@ItemType='PM'])").as("LU still has 1 packing item").isEqualTo("1");
		assertThat(xmlPalletAfterSplit).valueByXPath("string(HU-Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("190.114");
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item)").as("LU still has 2 items").isEqualTo("2");
	}

	@NonNull
	private I_M_ReceiptSchedule prepareReceiptSchedule(@NonNull final I_M_HU palletToSplit, @NonNull final BigDecimal qtyOrdered)
	{
		final List<I_M_HU> vhuRecords = Objects.requireNonNull(POJOLookupMap.get()).getRecords(I_M_HU.class, handlingUnitsBL::isVirtual);
		final I_M_HU vhuRecord = CollectionUtils.singleElement(vhuRecords);

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setM_Product_ID(materialItemProductTomato_10.getM_Product_ID());
		orderLineRecord.setQtyOrdered(qtyOrdered);
		orderLineRecord.setPriceActual(BigDecimal.ONE);
		saveRecord(orderLineRecord);

		final I_C_BPartner bpartnerRecord = BusinessTestHelper.createBPartner("bpartner");
		final I_M_Warehouse warehouseRecord = BusinessTestHelper.createWarehouse("warehouse");
		final I_M_ReceiptSchedule receiptScheduleRecord = newInstance(I_M_ReceiptSchedule.class);
		receiptScheduleRecord.setM_Product_ID(materialItemProductTomato_10.getM_Product_ID());
		receiptScheduleRecord.setC_UOM_ID(uomKg.getC_UOM_ID());
		receiptScheduleRecord.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		receiptScheduleRecord.setM_Warehouse_ID(warehouseRecord.getM_Warehouse_ID());
		receiptScheduleRecord.setC_OrderLine_ID(orderLineRecord.getC_OrderLine_ID());
		saveRecord(receiptScheduleRecord);

		final I_M_ReceiptSchedule_Alloc rsaRecord = newInstance(I_M_ReceiptSchedule_Alloc.class);
		rsaRecord.setIsActive(true);
		rsaRecord.setM_ReceiptSchedule_ID(receiptScheduleRecord.getM_ReceiptSchedule_ID());
		rsaRecord.setM_LU_HU(palletToSplit);
		rsaRecord.setM_TU_HU(vhuRecord);
		rsaRecord.setHU_QtyAllocated(qtyOrdered);
		rsaRecord.setVHU(vhuRecord);
		saveRecord(rsaRecord);
		return receiptScheduleRecord;
	}
}
