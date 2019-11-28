package de.metas.edi.api.impl;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static de.metas.esb.edi.model.I_EDI_DesadvLine_SSCC.*;
import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_SSCC;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBL;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2019 metas GmbH
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

class DesadvBLTest
{
	private int sscc18SerialNo = 0;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		sscc18SerialNo = 0;
		final SSCC18CodeBL sscc18CodeBL = new SSCC18CodeBL(() -> ++sscc18SerialNo);
		Services.registerService(ISSCC18CodeBL.class, sscc18CodeBL);

		Services.get(ISysConfigBL.class).setValue(SSCC18CodeBL.SYSCONFIG_ManufacturerCode, "111111", OrgId.ANY.getRepoId());
	}

	@Test
	void setQty_isUOMForTUs()
	{
		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		uomRecord.setX12DE355(IUOMDAO.X12DE355_COLI);
		saveRecord(uomRecord);

		final I_EDI_DesadvLine desadvLineRecord = newInstance(I_EDI_DesadvLine.class);
		desadvLineRecord.setQtyItemCapacity(new BigDecimal("9"));
		desadvLineRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		desadvLineRecord.setQtyEntered(new BigDecimal("99"));
		saveRecord(desadvLineRecord);

		// invoke the method under test
		new DesadvBL().setQty(desadvLineRecord, new BigDecimal("20.5"));

		assertThat(desadvLineRecord)
				.extracting(
						"QtyEntered",
						"MovementQty",
						"QtyDeliveredInUOM")
				.containsExactly(
						new BigDecimal("99"),
						new BigDecimal("20.5"),
						new BigDecimal("3"));
	}

	@Test
	void addInOutLine()
	{
		final HUTestHelper huTestHelper = new HUTestHelper(false/* don't init all */).setInitAdempiere(false).init();

		final I_M_HU_PI huDefPalet = huTestHelper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_PackingMaterial(huDefPalet, huTestHelper.pmPalet);

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);

		final I_M_HU_PI huDefIFCO = huTestHelper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item pmItemIFCO = huTestHelper.createHU_PI_Item_PackingMaterial(huDefIFCO, huTestHelper.pmIFCO);
		final I_M_HU_PI_Item maItemIFCO = huTestHelper.createHU_PI_Item_Material(huDefIFCO);
		final I_M_HU_PI_Item huItemIFCO = huTestHelper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, TEN); // one pallet can hold 10 IFCOs

		huTestHelper.assignProduct(maItemIFCO, ProductId.ofRepoId(productRecord.getM_Product_ID()), new BigDecimal("5"), uomRecord); // one IFCO can hold 5 products

		final I_EDI_DesadvLine desadvLine = newInstance(I_EDI_DesadvLine.class);
		desadvLine.setC_UOM_ID(uomRecord.getC_UOM_ID());
		desadvLine.setM_Product_ID(productRecord.getM_Product_ID());
		desadvLine.setMovementQty(new BigDecimal("2")); // initial quantity..we don't care from where..
		saveRecord(desadvLine);

		final I_C_Order orderRecord = newInstance(I_C_Order.class);
		orderRecord.setC_BPartner_ID(20);
		saveRecord(orderRecord);

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setC_Order_ID(orderRecord.getC_Order_ID());
		orderLineRecord.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
		orderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		saveRecord(orderLineRecord);

		final I_M_InOutLine inOutLineRecord = newInstance(I_M_InOutLine.class);
		inOutLineRecord.setC_OrderLine_ID(orderLineRecord.getC_OrderLine_ID());
		inOutLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		inOutLineRecord.setMovementQty(new BigDecimal("80")); // this won't fit onto one pallet
		saveRecord(inOutLineRecord);

		// invoke the method under test
		new DesadvBL().addInOutLine(inOutLineRecord);
		refresh(desadvLine);

		assertThat(inOutLineRecord.getEDI_DesadvLine_ID()).isEqualTo(desadvLine.getEDI_DesadvLine_ID());
		assertThat(desadvLine.getMovementQty()).isEqualByComparingTo("82");

		final List<I_EDI_DesadvLine_SSCC> ssccRecords = POJOLookupMap.get().getRecords(I_EDI_DesadvLine_SSCC.class);
		assertThat(ssccRecords)
				.extracting("Manual_IPA_SSCC18", COLUMNNAME_IPA_SSCC18, COLUMNNAME_QtyCUsPerLU, COLUMNNAME_QtyCU, COLUMNNAME_QtyTU)
				.containsExactlyInAnyOrder(
						tuple(true, "001111110000000015", new BigDecimal("50"), new BigDecimal("5"), new BigDecimal("5")),
						tuple(true, "001111110000000022", new BigDecimal("30"), new BigDecimal("5"), new BigDecimal("3"))//
				);
	}
}
