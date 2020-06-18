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
import org.junit.Test;
import org.w3c.dom.Node;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewTUsRequest;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.xmlunit.assertj.XmlAssert.assertThat;

public class SplitLUsWithOddQuantitiesTest extends AbstractWeightAttributeTest
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@Override
	protected I_C_UOM getCUUOM()
	{
		return uomKg;
	}

	/** Generate an aggregate-LU with 500.217 KG tomatoes */
	@Test
	public void testSplit7TuFromLuWithRealData()
	{
		// given
		// the LU contains one homogenous/aggregate HU with 50 TUs each of which has 10kg => 500
		final I_M_HU palletToSplit = Services.get(ITrxManager.class).callInNewTrx(() -> {
			final I_M_HU lu = helper.createLU(
					huContext,
					huItemIFCO_10,
					materialItemProductTomato_10,
					BigDecimal.valueOf(500));

			/*set weight attributes; does not yet affect storage, but is required for the weight-adjuster to work later*/
			final IAttributeStorage attributeStorageLoadingUnit = attributeStorageFactory.getAttributeStorage(lu);
			attributeStorageLoadingUnit.setValue(attr_WeightNet, new BigDecimal("500.217"));
			assertLoadingUnitStorageWeights(lu, huItemIFCO_10, 50,
					newHUWeightsExpectation("575.217", "500.217", "75", "0")
			);

			return lu;
		});

		// note that we use HUReceiptScheduleWeightNetAdjuster because currently this is the only code that AFAIK does weight adjusting
		// this will change very soon, but until then i'm trying not to touch any of the code that is currently worked on, to avoid change conflicts
		final I_M_ReceiptSchedule receiptScheduleRecord = prepareReceiptSchedule(palletToSplit);
		final HUReceiptScheduleWeightNetAdjuster rsNetWeightAdjuster = new HUReceiptScheduleWeightNetAdjuster(helper.getCtx(), helper.trxName);
		rsNetWeightAdjuster.addReceiptSchedule(receiptScheduleRecord);

		// Assert initial data is as expected: 1 LU with 50 TUs and 500.217 CUs
		final Node xmlPalletBeforeSplit = HUXmlConverter.toXml(palletToSplit);
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item[@ItemType='HA'])").as("LU has 1 aggregate item").isEqualTo("1");
		assertThat(xmlPalletBeforeSplit).valueByXPath("string(HU-Palet/Item[@ItemType='HA']/@Qty)").as("LU has 1 aggregate item").isEqualTo("50");
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item[@ItemType='PM'])").as("LU has 1 packing item").isEqualTo("1");
		assertThat(xmlPalletBeforeSplit).valueByXPath("count(HU-Palet/Item)").as("LU has 2 items").isEqualTo("2");
		assertThat(xmlPalletBeforeSplit).valueByXPath("string(HU-Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("500.217");

		//
		// when
		final HUsToNewTUsRequest request = HUsToNewTUsRequest.forSourceHuAndQty(palletToSplit, 1);
		final List<I_M_HU> splitTUs = HUTransformService.newInstance(helper.getHUContext())
				.husToNewTUs(request);

		//
		// then
		//helper.commitAndDumpHU(splitTUs.get(0));
		Assertions.assertThat(splitTUs).hasSize(1); // in this test, we static imported XMLAssert

		final Node xmlSplitTU = HUXmlConverter.toXml(splitTUs.get(0));
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO)").asInt().isEqualTo(1);
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item[@ItemType='MI'])").as("TU has 1 material item").isEqualTo("1");
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item[@ItemType='PM'])").as("TU has 1 packing item").isEqualTo("1");
		assertThat(xmlSplitTU).valueByXPath("count(HU-IFCO/Item)").as("TU has 2 items").isEqualTo("2");
		assertThat(xmlSplitTU).valueByXPath("string(HU-IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("10.004");

		//helper.commitAndDumpHU(palletToSplit);
		final Node xmlPalletAfterSplit = HUXmlConverter.toXml(palletToSplit);
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item[@ItemType='HA'])").as("LU still has 1 aggregate item").isEqualTo("1");
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item[@ItemType='PM'])").as("LU still has 1 packing item").isEqualTo("1");
		assertThat(xmlPalletAfterSplit).valueByXPath("count(HU-Palet/Item)").as("LU still has 2 items").isEqualTo("2");
	}

	@NonNull
	private I_M_ReceiptSchedule prepareReceiptSchedule(final I_M_HU palletToSplit)
	{
		final List<I_M_HU> vhuRecords = POJOLookupMap.get().getRecords(I_M_HU.class, handlingUnitsBL::isVirtual);
		final I_M_HU vhuRecord = CollectionUtils.singleElement(vhuRecords);

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setM_Product_ID(materialItemProductTomato_10.getM_Product_ID());
		orderLineRecord.setQtyOrdered(new BigDecimal("500.217"));
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
		rsaRecord.setHU_QtyAllocated(new BigDecimal("500.217"));
		rsaRecord.setVHU(vhuRecord);
		saveRecord(rsaRecord);
		return receiptScheduleRecord;
	}
}
