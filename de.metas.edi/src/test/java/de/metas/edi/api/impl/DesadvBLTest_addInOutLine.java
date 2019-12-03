package de.metas.edi.api.impl;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.*;
import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBL;
import de.metas.handlingunits.generichumodel.HURepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
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

class DesadvBLTest_addInOutLine
{
	private int sscc18SerialNo = 0;
	private I_M_HU_PI_Item_Product huPIItemProductRecord;
	private I_C_UOM uomRecord;
	private I_M_HU_PI_Item huPIItemPallet;
	private HUTestHelper huTestHelper;
	private I_M_InOutLine inOutLineRecord;
	private SSCC18CodeBL sscc18CodeBL;
	private DesadvBL desadvBL;
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private I_EDI_DesadvLine desadvLine;

	@BeforeEach
	void beforeEach()
	{
		// AdempiereTestHelper.get().init(); // this is done by huTestHelper
		huTestHelper = HUTestHelper.newInstanceOutOfTrx(); // we need to do this before registering our custom SSCC18CodeBL

		sscc18SerialNo = 0;
		sscc18CodeBL = new SSCC18CodeBL(() -> ++sscc18SerialNo);
		Services.registerService(ISSCC18CodeBL.class, sscc18CodeBL);

		Services.get(ISysConfigBL.class).setValue(SSCC18CodeBL.SYSCONFIG_ManufacturerCode, "111111", OrgId.ANY.getRepoId());

		// setup HU packing instructions
		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);

		final I_M_HU_PI huDefPalet = huTestHelper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_PackingMaterial(huDefPalet, huTestHelper.pmPalet);

		final I_M_HU_PI huDefIFCO = huTestHelper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		huTestHelper.createHU_PI_Item_PackingMaterial(huDefIFCO, huTestHelper.pmIFCO);
		final I_M_HU_PI_Item maItemIFCO = huTestHelper.createHU_PI_Item_Material(huDefIFCO);
		huPIItemPallet = huTestHelper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, TEN);

		huPIItemProductRecord = huTestHelper.assignProduct(maItemIFCO, ProductId.ofRepoId(productRecord.getM_Product_ID()), new BigDecimal("5"), uomRecord);

		desadvLine = newInstance(I_EDI_DesadvLine.class);
		desadvLine.setM_Product_ID(huPIItemProductRecord.getM_Product_ID());
		desadvLine.setC_UOM_ID(uomRecord.getC_UOM_ID());
		desadvLine.setMovementQty(new BigDecimal("2")); // initial quantity in stock-UOM..we don't care from where it came..
		saveRecord(desadvLine);

		final I_C_Order orderRecord = newInstance(I_C_Order.class);
		orderRecord.setC_BPartner_ID(20);
		saveRecord(orderRecord);

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setC_Order_ID(orderRecord.getC_Order_ID());
		orderLineRecord.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
		orderLineRecord.setM_Product_ID(huPIItemProductRecord.getM_Product_ID());
		orderLineRecord.setM_HU_PI_Item_Product_ID(huPIItemProductRecord.getM_HU_PI_Item_Product_ID());
		saveRecord(orderLineRecord);

		inOutLineRecord = newInstance(I_M_InOutLine.class);
		inOutLineRecord.setC_OrderLine_ID(orderLineRecord.getC_OrderLine_ID());
		inOutLineRecord.setM_Product_ID(huPIItemProductRecord.getM_Product_ID());
		inOutLineRecord.setMovementQty(new BigDecimal("81")); // this won't fit onto one pallet
		saveRecord(inOutLineRecord);

		desadvBL = new DesadvBL(new HURepository());
	}

	@Test
	void addInOutLine_no_HU()
	{
		// invoke the method under test
		desadvBL.addInOutLine(inOutLineRecord);

		final I_EDI_DesadvLine desadvLine = inOutLineRecord.getEDI_DesadvLine();
		assertThat(inOutLineRecord.getEDI_DesadvLine_ID()).isEqualTo(desadvLine.getEDI_DesadvLine_ID());
		assertThat(desadvLine.getMovementQty()).isEqualByComparingTo("83");

		final List<I_EDI_DesadvLine_Pack> ssccRecords = POJOLookupMap.get().getRecords(I_EDI_DesadvLine_Pack.class);
		assertThat(ssccRecords)
				.extracting("Manual_IPA_SSCC18", COLUMNNAME_IPA_SSCC18, COLUMNNAME_QtyTU, COLUMNNAME_QtyCU, COLUMNNAME_QtyCUsPerLU)
				.containsOnly(
						tuple(true, "001111110000000015", 10, new BigDecimal("5"), new BigDecimal("50")),
						tuple(true, "001111110000000022", 7, new BigDecimal("5"), new BigDecimal("31"))//
				);
	}

	@Test
	void addInOutLine_no_HU_desadvLineWithCOLIasUOM()
	{
		changeDesadvLineToCOLIasUOM();

		// invoke the method under test
		desadvBL.addInOutLine(inOutLineRecord);

		final I_EDI_DesadvLine resultDesadvLine = inOutLineRecord.getEDI_DesadvLine();
		assertThat(inOutLineRecord.getEDI_DesadvLine_ID()).isEqualTo(resultDesadvLine.getEDI_DesadvLine_ID());
		assertThat(resultDesadvLine.getMovementQty()).isEqualByComparingTo("83");

		final List<I_EDI_DesadvLine_Pack> packRecords = POJOLookupMap.get().getRecords(I_EDI_DesadvLine_Pack.class);
		assertThat(packRecords)
				.extracting("Manual_IPA_SSCC18", COLUMNNAME_IPA_SSCC18, COLUMNNAME_QtyTU, COLUMNNAME_QtyCU, COLUMNNAME_QtyCUsPerLU)
				.containsOnly(
						tuple(true, "001111110000000015", 10, new BigDecimal("1"), new BigDecimal("10")),
						tuple(true, "001111110000000022", 7, new BigDecimal("1"), new BigDecimal("7"))//
				);
	}

	@Test
	void addInoutLine_HU()
	{
		setupHandlingUnit();

		// invoke the method under test
		desadvBL.addInOutLine(inOutLineRecord);

		final List<I_EDI_DesadvLine_Pack> packRecords = POJOLookupMap.get().getRecords(I_EDI_DesadvLine_Pack.class);
		assertThat(packRecords)
				.extracting("Manual_IPA_SSCC18", COLUMNNAME_IPA_SSCC18, COLUMNNAME_BestBeforeDate, COLUMNNAME_QtyTU, COLUMNNAME_QtyCU, COLUMNNAME_QtyCUsPerLU)
				.containsOnly(
						tuple(false, "001111110000000015", TimeUtil.parseTimestamp("2019-12-02"), 10, new BigDecimal("5"), new BigDecimal("49")),
						tuple(true, "001111110000000022", null, 7, new BigDecimal("5"), new BigDecimal("32"))//
				);
	}

	@Test
	void addInoutLine_HU_desadvLineWithCOLIasUOM()
	{
		changeDesadvLineToCOLIasUOM();
		setupHandlingUnit();

		// invoke the method under test
		desadvBL.addInOutLine(inOutLineRecord);

		final List<I_EDI_DesadvLine_Pack> packRecords = POJOLookupMap.get().getRecords(I_EDI_DesadvLine_Pack.class);
		assertThat(packRecords)
				.extracting("Manual_IPA_SSCC18", COLUMNNAME_IPA_SSCC18, COLUMNNAME_BestBeforeDate, COLUMNNAME_QtyTU, COLUMNNAME_QtyCU, COLUMNNAME_QtyCUsPerLU)
				.containsOnly(
						tuple(false, "001111110000000015", TimeUtil.parseTimestamp("2019-12-02"), 10, new BigDecimal("1"), new BigDecimal("10")),
						tuple(true, "001111110000000022", null, 7, new BigDecimal("1"), new BigDecimal("7"))//
				);
	}

	private void changeDesadvLineToCOLIasUOM()
	{
		final I_C_UOM coliUomRecord = newInstance(I_C_UOM.class);
		coliUomRecord.setX12DE355(IUOMDAO.X12DE355_COLI);
		saveRecord(coliUomRecord);

		desadvLine.setC_UOM_ID(coliUomRecord.getC_UOM_ID());
		saveRecord(desadvLine);
	}

	/** sets up a handling unit and assigns it to {@link #inOutLineRecord}. */
	private void setupHandlingUnit()
	{
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(Env.getCtx());

		final I_M_Attribute sscc18AttrRecord = newInstance(I_M_Attribute.class);
		sscc18AttrRecord.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		sscc18AttrRecord.setValue(HUAttributeConstants.ATTR_SSCC18_Value);
		saveRecord(sscc18AttrRecord);

		final I_M_HU_PI_Attribute sscc18HUPIAttributeRecord = huTestHelper
				.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(sscc18AttrRecord)
						.setM_HU_PI(huPIItemPallet.getIncluded_HU_PI()));
		final I_M_HU lu = huTestHelper.createLU(
				huContext,
				huPIItemPallet,
				huPIItemProductRecord,
				new BigDecimal("49"));
		final I_M_HU_Attribute huAttrRecord = newInstance(I_M_HU_Attribute.class);
		huAttrRecord.setM_Attribute_ID(sscc18HUPIAttributeRecord.getM_Attribute_ID());
		huAttrRecord.setM_HU_ID(lu.getM_HU_ID());
		huAttrRecord.setValue(sscc18CodeBL.toString(sscc18CodeBL.generate(), false));
		huAttrRecord.setM_HU_PI_Attribute_ID(sscc18HUPIAttributeRecord.getM_HU_PI_Attribute_ID());
		saveRecord(huAttrRecord);

		final I_M_Attribute bestBeforeAttrRecord = newInstance(I_M_Attribute.class);
		bestBeforeAttrRecord.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_Date);
		bestBeforeAttrRecord.setValue(AttributeConstants.ATTR_BestBeforeDate);
		saveRecord(bestBeforeAttrRecord);

		final I_M_HU_PI_Attribute bestBeforeHUPIAttributeRecord = huTestHelper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(bestBeforeAttrRecord)
				.setM_HU_PI(huPIItemPallet.getIncluded_HU_PI()));
		for (final I_M_HU childHU : Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(lu))
		{
			final I_M_HU_Attribute childHUAttrRecord = newInstance(I_M_HU_Attribute.class);
			childHUAttrRecord.setM_Attribute_ID(bestBeforeHUPIAttributeRecord.getM_Attribute_ID());
			childHUAttrRecord.setM_HU_ID(childHU.getM_HU_ID());
			childHUAttrRecord.setValueDate(TimeUtil.parseTimestamp("2019-12-02"));
			childHUAttrRecord.setM_HU_PI_Attribute_ID(bestBeforeHUPIAttributeRecord.getM_HU_PI_Attribute_ID());
			saveRecord(childHUAttrRecord);
		}

		huAssignmentBL.createHUAssignmentBuilder()
				.initializeAssignment(Env.getCtx(), ITrx.TRXNAME_None)
				.setModel(inOutLineRecord)
				.setTopLevelHU(lu)
				.setM_LU_HU(lu)
				.build();
		assertThat(inOutLineRecord.getMovementQty()).isEqualByComparingTo("81"); // guard
	}
}
