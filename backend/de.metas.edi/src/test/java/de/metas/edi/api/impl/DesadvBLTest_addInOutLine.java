package de.metas.edi.api.impl;

import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.COLUMNNAME_BestBeforeDate;
import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.COLUMNNAME_C_UOM_ID;
import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.COLUMNNAME_IPA_SSCC18;
import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.COLUMNNAME_QtyCU;
import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.COLUMNNAME_QtyCUsPerLU;
import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.COLUMNNAME_QtyTU;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import de.metas.organization.ClientAndOrgId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.business.BusinessTestHelper;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.esb.edi.model.X_EDI_DesadvLine;
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
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
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

@ExtendWith(AdempiereTestWatcher.class)
class DesadvBLTest_addInOutLine
{
	private int sscc18SerialNo = 0;
	private I_M_HU_PI_Item_Product huPIItemProductRecord;

	private I_C_UOM catchUomRecord;
	private UomId orderUomId;

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
		sscc18CodeBL = new SSCC18CodeBL(orgId -> ++sscc18SerialNo);
		Services.registerService(ISSCC18CodeBL.class, sscc18CodeBL);

		Services.get(ISysConfigBL.class).setValue(SSCC18CodeBL.SYSCONFIG_ManufacturerCode, "111111", ClientId.METASFRESH, OrgId.ANY);

		catchUomRecord = BusinessTestHelper.createUOM("catchUom", 3, -1);
		final I_C_UOM stockUOMRecord = BusinessTestHelper.createUOM("stockUOM", 0, -1);
		final I_C_UOM orderUOMRecord = BusinessTestHelper.createUOM("orderUOM", 3, -1);
		orderUomId = UomId.ofRepoId(orderUOMRecord.getC_UOM_ID());

		final I_M_Product productRecord = BusinessTestHelper.createProduct("product", stockUOMRecord);

		BusinessTestHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(orderUOMRecord.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(stockUOMRecord.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal("2"))
				.build());

		// we do need a UOM conversion between catchUomRecord and orderUOMRecord,
		// because we need to convert the catch qtys (might e.g. be in tons) to the ordered UOM (might be in kilos)
		BusinessTestHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(orderUOMRecord.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(catchUomRecord.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal("3"))
				.build());

		// we do need a UOM conversion between catchUomRecord and stockUOM,
		// because we need to convert the catch qtys (might e.g. be in tons) when computing the LUTUconfig's capacity
		BusinessTestHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(stockUOMRecord.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(catchUomRecord.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal("1.5"))
				.build());

		// setup HU packing instructions
		final I_M_HU_PI huDefPalet = huTestHelper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_PackingMaterial(huDefPalet, huTestHelper.pmPalet);

		final I_M_HU_PI huDefIFCO = huTestHelper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		huTestHelper.createHU_PI_Item_PackingMaterial(huDefIFCO, huTestHelper.pmIFCO);
		final I_M_HU_PI_Item maItemIFCO = huTestHelper.createHU_PI_Item_Material(huDefIFCO);
		huPIItemPallet = huTestHelper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, TEN);

		huPIItemProductRecord = huTestHelper.assignProduct(maItemIFCO, ProductId.ofRepoId(productRecord.getM_Product_ID()), new BigDecimal("5"), stockUOMRecord);

		final I_EDI_Desadv desadv = newInstance(I_EDI_Desadv.class);
		saveRecord(desadv);

		desadvLine = newInstance(I_EDI_DesadvLine.class);
		desadvLine.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		desadvLine.setM_Product_ID(huPIItemProductRecord.getM_Product_ID());
		desadvLine.setC_UOM_ID(orderUOMRecord.getC_UOM_ID());
		desadvLine.setQtyDeliveredInStockingUOM(new BigDecimal("2")); // initial quantity in stock-UOM..we don't care from where it came..
		desadvLine.setInvoicableQtyBasedOn(X_EDI_DesadvLine.INVOICABLEQTYBASEDON_CatchWeight); // the code should fall back to "nominal" if the respecive inOutLine doesn't have catch weight data.
		saveRecord(desadvLine);

		final I_C_Order orderRecord = newInstance(I_C_Order.class);
		orderRecord.setC_BPartner_ID(20);
		saveRecord(orderRecord);

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setC_Order_ID(orderRecord.getC_Order_ID());
		orderLineRecord.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
		orderLineRecord.setM_Product_ID(huPIItemProductRecord.getM_Product_ID());
		orderLineRecord.setM_HU_PI_Item_Product_ID(huPIItemProductRecord.getM_HU_PI_Item_Product_ID());
		orderLineRecord.setC_UOM_ID(orderUOMRecord.getC_UOM_ID());
		saveRecord(orderLineRecord);

		inOutLineRecord = newInstance(I_M_InOutLine.class);
		inOutLineRecord.setC_OrderLine_ID(orderLineRecord.getC_OrderLine_ID());
		inOutLineRecord.setM_Product_ID(huPIItemProductRecord.getM_Product_ID());
		inOutLineRecord.setMovementQty(new BigDecimal("84")); // in order-UOM this is 84 / 2 = 42; in catch-UOM this is 84 * 1.5 = 126
		inOutLineRecord.setQtyEntered(new BigDecimal("42"));
		inOutLineRecord.setC_UOM_ID(orderUOMRecord.getC_UOM_ID());
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
		assertThat(desadvLine.getQtyDeliveredInStockingUOM()).isEqualByComparingTo("86");
		assertThat(inOutLineRecord.getQtyEntered()).isEqualByComparingTo("42"); // guard

		final List<I_EDI_DesadvLine_Pack> ssccRecords = POJOLookupMap.get().getRecords(I_EDI_DesadvLine_Pack.class);
		assertThat(ssccRecords)
				.extracting("Manual_IPA_SSCC18", COLUMNNAME_IPA_SSCC18, COLUMNNAME_QtyTU, COLUMNNAME_QtyCU, COLUMNNAME_QtyCUsPerLU)
				.containsOnly(
						tuple(true, "001111110000000015", 10, new BigDecimal("2.500"), new BigDecimal("25.000")),
						tuple(true, "001111110000000022", 7, new BigDecimal("2.500"), new BigDecimal("17.000"))//
				);
	}

	@Test
	void addInOutLine_no_HU_catch_weight()
	{
		// catchqty = "127"
		// 84(qty in stockUOM) * 1.5 = 126 (qty in catchUOM) plus a bit of catch-weighty variance
		// in orderUom this is: 127 / 3 = 42.333
		final BigDecimal qtyDeliveredCatch = new BigDecimal("127");

		// note that
		// one LU can contain 50 items in stock-UOM and therefore 50 *0.5 = 25 in order-UOM
		// one TU can contain 5 items in stock-UOM and therefore 10 *0.5 = 2.5 in order-UOM
		inOutLineRecord.setCatch_UOM_ID(catchUomRecord.getC_UOM_ID());
		inOutLineRecord.setQtyDeliveredCatch(qtyDeliveredCatch);
		saveRecord(inOutLineRecord);

		// invoke the method under test
		desadvBL.addInOutLine(inOutLineRecord);

		final I_EDI_DesadvLine desadvLine = inOutLineRecord.getEDI_DesadvLine();
		assertThat(inOutLineRecord.getEDI_DesadvLine_ID()).isEqualTo(desadvLine.getEDI_DesadvLine_ID());
		assertThat(desadvLine.getQtyDeliveredInStockingUOM()).isEqualByComparingTo("86"); // the initial 2 plus the inoutLineRecord's 84

		final List<I_EDI_DesadvLine_Pack> ssccRecords = POJOLookupMap.get().getRecords(I_EDI_DesadvLine_Pack.class);
		assertThat(ssccRecords)
				.extracting("Manual_IPA_SSCC18", COLUMNNAME_IPA_SSCC18, COLUMNNAME_QtyTU, COLUMNNAME_QtyCU, COLUMNNAME_QtyCUsPerLU, COLUMNNAME_C_UOM_ID)
				.containsOnly(
						tuple(true, "001111110000000015", 10/* TUs */, new BigDecimal("2.500")/* CUsperTU */, new BigDecimal("25.000")/* CUsperLU */, orderUomId.getRepoId()),
						tuple(true, "001111110000000022", 7/* TUs */, new BigDecimal("2.500")/* CUperTU */, new BigDecimal("17.333")/* CUperLU */, orderUomId.getRepoId()) //
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
		assertThat(resultDesadvLine.getQtyDeliveredInStockingUOM()).isEqualByComparingTo("86");

		final List<I_EDI_DesadvLine_Pack> packRecords = POJOLookupMap.get().getRecords(I_EDI_DesadvLine_Pack.class);
		assertThat(packRecords)
				.extracting("Manual_IPA_SSCC18", COLUMNNAME_IPA_SSCC18, COLUMNNAME_QtyTU, COLUMNNAME_QtyCU, COLUMNNAME_QtyCUsPerLU)
				.containsOnly(
						tuple(true, "001111110000000015", 10, new BigDecimal("1"), new BigDecimal("10")),
						tuple(true, "001111110000000022", 7, new BigDecimal("1"), new BigDecimal("7"))//
				);
	}

	/** tests inoutLine whose quantity is partially covered by an HU */
	@Test
	void addInoutLine_HU()
	{
		setupHandlingUnit(); // HU with 49 CUs

		// invoke the method under test
		desadvBL.addInOutLine(inOutLineRecord);

		final List<I_EDI_DesadvLine_Pack> packRecords = POJOLookupMap.get().getRecords(I_EDI_DesadvLine_Pack.class);
		assertThat(packRecords)
				.extracting("Manual_IPA_SSCC18", COLUMNNAME_IPA_SSCC18, COLUMNNAME_BestBeforeDate, COLUMNNAME_QtyTU, COLUMNNAME_QtyCU, COLUMNNAME_QtyCUsPerLU)
				.containsOnly(
						/* the first pack is based on the HU that we added */
						tuple(false, "001111110000000015", TimeUtil.parseTimestamp("2019-12-02"), 10, new BigDecimal("2.500"), new BigDecimal("24.500")/* 49 times 0.5, i.e. the Hus qty converted to the pack's UOM */),

						// the 2nd pack represents "the rest" that is requiered to arrive at the inOutLineRecord's qtyEntered = 42
						tuple(true, "001111110000000022", null, 7, new BigDecimal("2.500"), new BigDecimal("17.500"))//
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
		final I_C_UOM coliUomRecord = BusinessTestHelper.createUOM("coli", IUOMDAO.X12DE355_COLI);

		desadvLine.setC_UOM_ID(coliUomRecord.getC_UOM_ID());
		saveRecord(desadvLine);
	}

	/** sets up a handling unit with 49 items and assigns it to {@link #inOutLineRecord}. */
	private void setupHandlingUnit()
	{
		final Properties ctx = Env.getCtx();
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(ctx, ClientAndOrgId.ofClientAndOrg(Env.getAD_Client_ID(), Env.getAD_Org_ID(ctx)));

		final I_M_Attribute sscc18AttrRecord = newInstance(I_M_Attribute.class);
		sscc18AttrRecord.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		sscc18AttrRecord.setValue(HUAttributeConstants.ATTR_SSCC18_Value.getCode());
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
		huAttrRecord.setValue(sscc18CodeBL.toString(sscc18CodeBL.generate(OrgId.ANY), false));
		huAttrRecord.setM_HU_PI_Attribute_ID(sscc18HUPIAttributeRecord.getM_HU_PI_Attribute_ID());
		saveRecord(huAttrRecord);

		final I_M_Attribute bestBeforeAttrRecord = newInstance(I_M_Attribute.class);
		bestBeforeAttrRecord.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_Date);
		bestBeforeAttrRecord.setValue(AttributeConstants.ATTR_BestBeforeDate.getCode());
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
				.initializeAssignment(ctx, ITrx.TRXNAME_None)
				.setModel(inOutLineRecord)
				.setTopLevelHU(lu)
				.setM_LU_HU(lu)
				.build();
		assertThat(inOutLineRecord.getMovementQty()).isEqualByComparingTo("84"); // guard
	}
}
