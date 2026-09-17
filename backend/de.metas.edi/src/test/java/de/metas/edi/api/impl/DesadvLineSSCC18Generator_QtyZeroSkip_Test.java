package de.metas.edi.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.edi.api.impl.pack.EDIDesadvPack;
import de.metas.edi.api.impl.pack.EDIDesadvPackService;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.edi.sscc18.DesadvLineSSCC18Generator;
import de.metas.edi.sscc18.IPrintableDesadvLineSSCC18Labels;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.filter.GenerateReceiptScheduleForModelAggregateFilter;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.ProductId;
import de.metas.sscc18.ISSCC18CodeBL;
import de.metas.sscc18.impl.SSCC18CodeBL;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.handlingunits.HUTestHelper.NAME_IFCO_Product;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Tests {@link DesadvLineSSCC18Generator}'s Qty-0 skip guard.
 * <p>
 * Verifies that the generator skips any LU whose breakdown yields {@code qtyCUsPerLU=0}
 * so no orphan {@code EDI_Desadv_Pack_Item} with {@code MovementQty=0} / {@code M_InOutLine_ID=null}
 * is ever persisted, even after a complete → reactivate → re-complete cycle.
 */
@ExtendWith(AdempiereTestWatcher.class)
class DesadvLineSSCC18Generator_QtyZeroSkip_Test
{
	private int sscc18SerialNo = 0;
	private I_M_HU_PI_Item_Product huPIItemProductRecord;
	private UomId stockUomId;
	private final BPartnerId recipientBPartnerId = BPartnerId.ofRepoId(20);
	private I_M_HU_PI_Item huPIItemPallet;
	private HUTestHelper huTestHelper;
	private I_M_InOutLine inOutLineRecord;
	private I_M_InOut inOutRecord;
	private SSCC18CodeBL sscc18CodeBL;
	private DesadvBL desadvBL;
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private I_EDI_DesadvLine desadvLine;
	private I_M_Attribute bestBeforeAttrRecord;

	@BeforeEach
	void beforeEach()
	{
		final ReceiptScheduleProducerFactory receiptScheduleProducerFactory = new ReceiptScheduleProducerFactory(new GenerateReceiptScheduleForModelAggregateFilter(ImmutableList.of()));
		Services.registerService(IReceiptScheduleProducerFactory.class, receiptScheduleProducerFactory);

		huTestHelper = HUTestHelper.newInstanceOutOfTrx();

		sscc18SerialNo = 0;
		sscc18CodeBL = new SSCC18CodeBL();
		sscc18CodeBL.setOverrideNextSerialNumberProvider(orgId -> ++sscc18SerialNo);
		Services.registerService(ISSCC18CodeBL.class, sscc18CodeBL);

		Services.get(ISysConfigBL.class).setValue(SSCC18CodeBL.SYSCONFIG_ManufacturerCode, "111111", ClientId.METASFRESH, OrgId.ANY);

		final I_C_UOM stockUOMRecord = BusinessTestHelper.createUOM("stockUOM", 0, -1);
		final I_C_UOM orderUOMRecord = BusinessTestHelper.createUOM("orderUOM", 3, -1);

		final I_M_Product productRecord = BusinessTestHelper.createProduct("product", stockUOMRecord);
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());
		stockUomId = UomId.ofRepoId(stockUOMRecord.getC_UOM_ID());

		BusinessTestHelper.createUOMConversion(CreateUOMConversionRequest.builder()
													   .productId(productId)
													   .fromUomId(UomId.ofRepoId(orderUOMRecord.getC_UOM_ID()))
													   .toUomId(stockUomId)
													   .fromToMultiplier(new BigDecimal("2"))
													   .build());

		// setup HU packing instructions
		final I_M_HU_PI huDefPalet = huTestHelper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_PackingMaterial(huDefPalet, huTestHelper.pmPalet);

		final I_M_HU_PI huDefIFCO = huTestHelper.createHUDefinition(NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		huTestHelper.createHU_PI_Item_PackingMaterial(huDefIFCO, huTestHelper.pmIFCO);
		final I_M_HU_PI_Item maItemIFCO = huTestHelper.createHU_PI_Item_Material(huDefIFCO);
		huPIItemPallet = huTestHelper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, TEN);

		huPIItemProductRecord = huTestHelper.assignProduct(maItemIFCO, productId, new BigDecimal("5"), stockUOMRecord);

		final I_EDI_Desadv desadv = newInstance(I_EDI_Desadv.class);
		saveRecord(desadv);

		desadvLine = newInstance(I_EDI_DesadvLine.class);
		desadvLine.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		desadvLine.setM_Product_ID(huPIItemProductRecord.getM_Product_ID());
		desadvLine.setC_UOM_ID(orderUOMRecord.getC_UOM_ID());
		desadvLine.setQtyDeliveredInStockingUOM(BigDecimal.ZERO);
		desadvLine.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.NominalWeight.getCode());
		saveRecord(desadvLine);

		final I_C_Order orderRecord = newInstance(I_C_Order.class);
		orderRecord.setC_BPartner_ID(20);
		orderRecord.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		saveRecord(orderRecord);

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setC_Order_ID(orderRecord.getC_Order_ID());
		orderLineRecord.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
		orderLineRecord.setM_Product_ID(huPIItemProductRecord.getM_Product_ID());
		orderLineRecord.setM_HU_PI_Item_Product_ID(huPIItemProductRecord.getM_HU_PI_Item_Product_ID());
		orderLineRecord.setC_UOM_ID(orderUOMRecord.getC_UOM_ID());
		saveRecord(orderLineRecord);

		inOutRecord = newInstance(I_M_InOut.class);
		inOutRecord.setC_Order_ID(orderRecord.getC_Order_ID());
		inOutRecord.setC_BPartner_ID(recipientBPartnerId.getRepoId());
		inOutRecord.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		saveRecord(inOutRecord);

		inOutLineRecord = newInstance(I_M_InOutLine.class);
		inOutLineRecord.setC_OrderLine_ID(orderLineRecord.getC_OrderLine_ID());
		inOutLineRecord.setM_Product_ID(huPIItemProductRecord.getM_Product_ID());
		inOutLineRecord.setMovementQty(new BigDecimal("84"));
		inOutLineRecord.setQtyEntered(new BigDecimal("52"));
		inOutLineRecord.setC_UOM_ID(stockUomId.getRepoId());
		inOutLineRecord.setM_InOut_ID(inOutRecord.getM_InOut_ID());
		saveRecord(inOutLineRecord);

		desadvBL = DesadvBL.newInstanceForUnitTesting();

		bestBeforeAttrRecord = huTestHelper.attr_BestBeforeDate;

		setupHandlingUnit(); // HU with 49 CUs assigned to inOutLineRecord
	}

	/**
	 * Birth-prevention test (fix part B).
	 *
	 * <p>BEFORE the fix: {@link DesadvLineSSCC18Generator} would call
	 * {@code buildCreateEDIDesadvPackItemRequest} even when {@link TotalQtyCUBreakdownCalculator#subtractOneLU()}
	 * returned {@code LUQtys.NULL} (exhausted/misconfigured calculator), producing a
	 * {@code EDI_Desadv_Pack_Item} with {@code MovementQty=0} and {@code M_InOutLine_ID=NULL}.
	 * That item could never be reclaimed after reactivate→re-complete (exact-qty match required,
	 * and reactivation cleanup is keyed on {@code M_InOutLine_ID}).
	 *
	 * <p>AFTER the fix: the generator skips any LU whose breakdown yields {@code qtyCUsPerLU=0},
	 * so no such orphan is ever persisted.  This test drives the REAL generator with an exhausted
	 * calculator (1 label requested, calculator returns NULL for every subtractOneLU call) and
	 * then runs the full complete→reactivate→re-complete cycle, asserting no Qty-0/NULL-inoutline
	 * pack item exists at any point.
	 */
	@Test
	void generatorSkipsQtyZeroLU_andCycleIsClean()
	{
		// 0) SSCC labels "generated" via the real DesadvLineSSCC18Generator with an exhausted
		//    TotalQtyCUBreakdownCalculator (returns LUQtys.NULL for every subtractOneLU call).
		//    This models production: EDI_Desadv_GenerateSSCCLabels invoked when the LU breakdown
		//    cannot supply a qty (e.g. labels printed before the shipment qty was firmed up, or
		//    the calculator is misconfigured).
		//    Pre-fix: one EDI_Desadv_Pack + EDI_Desadv_Pack_Item (MovementQty=0, M_InOutLine_ID=NULL) created.
		//    Post-fix: the generator skips the 0-qty LU → no pack/pack-item created.
		invokeGeneratorWithExhaustedCalculator();

		// Assert birth-prevention: no Qty-0/NULL-inoutline pack item was born.
		assertNoQtyZeroOrphanPackItems("after SSCC generation with exhausted calculator");

		// 1) COMPLETE
		desadvBL.addToDesadvCreateForInOutIfNotExist(inOutRecord);

		// 2) REACTIVATE (production: only a non-qty field such as Tour changed)
		refresh(inOutRecord);
		desadvBL.removeInOutFromDesadv(inOutRecord);

		// 3) RE-COMPLETE
		refresh(inOutRecord);
		inOutRecord.setEDI_Desadv_ID(desadvLine.getEDI_Desadv_ID());
		saveRecord(inOutRecord);
		desadvBL.addToDesadvCreateForInOutIfNotExist(inOutRecord);

		// ASSERT — no orphan pack item survived the full cycle
		assertNoQtyZeroOrphanPackItems("after reactivate→re-complete cycle");
	}

	/**
	 * Invokes {@link DesadvLineSSCC18Generator} with an {@link IPrintableDesadvLineSSCC18Labels} that
	 * requests 1 label but whose {@link TotalQtyCUBreakdownCalculator} is exhausted
	 * ({@link TotalQtyCUBreakdownCalculator#NULL} always returns {@code LUQtys.NULL} from
	 * {@code subtractOneLU}).  Before the fix this created a Qty-0 pack item; after the fix it
	 * produces nothing.
	 */
	private void invokeGeneratorWithExhaustedCalculator()
	{
		final EDIDesadvPackService packService = EDIDesadvPackService.newInstanceForUnitTesting();

		final DesadvLineSSCC18Generator generator = DesadvLineSSCC18Generator.builder()
				.sscc18CodeService(sscc18CodeBL)
				.desadvBL(desadvBL)
				.ediDesadvPackService(packService)
				.printExistingLabels(false)
				.bpartnerId(recipientBPartnerId)
				.build();

		// IPrintableDesadvLineSSCC18Labels with 1 label requested but exhausted (NULL) calculator.
		// subtractOneLU() on TotalQtyCUBreakdownCalculator.NULL returns LUQtys.NULL (qty=0).
		final IPrintableDesadvLineSSCC18Labels labelsSpec = new IPrintableDesadvLineSSCC18Labels()
		{
			@Override
			public I_EDI_DesadvLine getEDI_DesadvLine() { return desadvLine; }

			@Override
			public I_M_HU_PI_Item_Product getTuPIItemProduct() { return huPIItemProductRecord; }

			@Override
			public Integer getLineNo() { return 10; }

			@Override
			public String getProductValue() { return "TESTPROD"; }

			@Override
			public String getProductName() { return "Test Product"; }

			@Override
			public Integer getExistingSSCC18sCount() { return 0; }

			@Override
			public List<EDIDesadvPack> getExistingSSCC18s() { return ImmutableList.of(); }

			@Override
			public BigDecimal getRequiredSSCC18sCount() { return BigDecimal.ONE; }

			@Override
			public void setRequiredSSCC18sCount(final BigDecimal requiredSSCC18sCount) { /* no-op */ }

			@Override
			public TotalQtyCUBreakdownCalculator breakdownTotalQtyCUsToLUs()
			{
				// NULL calculator: every subtractOneLU() call returns LUQtys.NULL (qtyCUsPerLU=0)
				return TotalQtyCUBreakdownCalculator.NULL.copy();
			}
		};

		generator.generateAndEnqueuePrinting(labelsSpec);
	}

	private void assertNoQtyZeroOrphanPackItems(@NonNull final String context)
	{
		final List<I_EDI_Desadv_Pack_Item> orphans = POJOLookupMap.get()
				.getRecords(I_EDI_Desadv_Pack_Item.class)
				.stream()
				.filter(pi -> pi.getMovementQty().signum() == 0) // MovementQty is NOT NULL in EDI_Desadv_Pack_Item
				.filter(pi -> pi.getM_InOutLine_ID() <= 0)
				.collect(Collectors.toList());

		assertThat(orphans)
				.as("orphan Qty-0 pack item with NULL M_InOutLine_ID must not exist " + context)
				.isEmpty();
	}

	private void setupHandlingUnit()
	{
		final Properties ctx = Env.getCtx();
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(ctx, ClientAndOrgId.ofClientAndOrg(Env.getAD_Client_ID(), Env.getAD_Org_ID(ctx)));

		final I_M_Attribute sscc18AttrRecord = newInstance(I_M_Attribute.class);
		sscc18AttrRecord.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		sscc18AttrRecord.setValue(AttributeConstants.ATTR_SSCC18_Value.getCode());
		saveRecord(sscc18AttrRecord);

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final I_M_HU_PI_Attribute sscc18HUPIAttributeRecord = huTestHelper
				.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(sscc18AttrRecord)
						.setM_HU_PI(handlingUnitsDAO.getIncludedPI(huPIItemPallet)));
		final I_M_HU lu = huTestHelper.createLU(
				huContext,
				huPIItemPallet,
				huPIItemProductRecord,
				new BigDecimal("49"));
		final I_M_HU_Attribute huAttrRecord = newInstance(I_M_HU_Attribute.class);
		huAttrRecord.setM_Attribute_ID(sscc18HUPIAttributeRecord.getM_Attribute_ID());
		huAttrRecord.setM_HU_ID(lu.getM_HU_ID());
		huAttrRecord.setValue(sscc18CodeBL.generate(OrgId.ANY).asString());
		huAttrRecord.setM_HU_PI_Attribute_ID(sscc18HUPIAttributeRecord.getM_HU_PI_Attribute_ID());
		saveRecord(huAttrRecord);

		final I_M_HU_PI_Attribute bestBeforeHUPIAttributeRecord = POJOLookupMap.get()
				.getRecords(I_M_HU_PI_Attribute.class,
						record -> record.getM_Attribute_ID() == bestBeforeAttrRecord.getM_Attribute_ID())
				.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No M_HU_PI_Attribute found for BestBeforeDate attribute"));

		for (final I_M_HU tu : handlingUnitsDAO.retrieveIncludedHUs(lu))
		{
			final I_M_HU_Attribute childHUAttrRecord = newInstance(I_M_HU_Attribute.class);
			childHUAttrRecord.setM_Attribute_ID(bestBeforeHUPIAttributeRecord.getM_Attribute_ID());
			childHUAttrRecord.setM_HU_ID(tu.getM_HU_ID());
			childHUAttrRecord.setValueDate(TimeUtil.parseTimestamp("2026-12-02"));
			childHUAttrRecord.setM_HU_PI_Attribute_ID(bestBeforeHUPIAttributeRecord.getM_HU_PI_Attribute_ID());
			saveRecord(childHUAttrRecord);

			huAssignmentBL.createHUAssignmentBuilder()
					.initializeAssignment(ctx, ITrx.TRXNAME_None)
					.setModel(inOutLineRecord)
					.setTopLevelHU(lu)
					.setM_LU_HU(lu)
					.setM_TU_HU(tu)
					.build();

			for (final I_M_HU cu : handlingUnitsDAO.retrieveIncludedHUs(tu))
			{
				huAssignmentBL.createHUAssignmentBuilder()
						.initializeAssignment(ctx, ITrx.TRXNAME_None)
						.setModel(inOutLineRecord)
						.setTopLevelHU(lu)
						.setM_LU_HU(lu)
						.setM_TU_HU(tu)
						.setVHU(cu)
						.build();
			}
		}

		huAssignmentBL.createHUAssignmentBuilder()
				.initializeAssignment(ctx, ITrx.TRXNAME_None)
				.setModel(inOutLineRecord)
				.setTopLevelHU(lu)
				.setM_LU_HU(lu)
				.build();
	}
}
