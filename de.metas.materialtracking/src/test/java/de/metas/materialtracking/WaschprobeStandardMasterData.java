package de.metas.materialtracking;

/*
 * #%L
 * de.metas.materialtracking
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.X_PP_Cost_Collector;
import org.junit.Assert;

import de.metas.inout.model.I_M_InOut;
import de.metas.materialtracking.ch.lagerkonf.impl.HardCodedQualityBasedConfig;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.model.I_PP_Order_BOMLine;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial;
import de.metas.materialtracking.qualityBasedInvoicing.ProductionMaterialComparator;
import de.metas.pricing.rules.MockedPricingRule;

/**
 * Defines mater-data and standard helpers for testing the WaschProbe use-case.
 *
 * @author tsa
 *
 * @task http://dewiki908/mediawiki/index.php/07371_Beleg_Waschprobe_%28109323219023%29
 */
public class WaschprobeStandardMasterData
{
	private final IContextAware context;

	public I_C_UOM uomFee;
	public I_C_UOM uomScrap;
	/** default UOM which is used in BOM, BOM Lines etc */
	public I_C_UOM uom;

	public I_C_Country materialReceiptOrigin;
	public Timestamp materialReceiptDate;

	public I_M_Product pCarrot_Washed;
	public I_M_Product pCarrot_Unwashed;
	public I_M_Product pCarrot_Unwashed_Alternative01;
	public I_M_Product pCarrot_Big;
	public I_M_Product pCarrot_AnimalFood;
	public I_M_Product pCarrot_Scrap;
	public I_M_Product pFeeBasicLine;
	public I_M_Product pFeePromotion;
	public I_M_Product pRegularOrder;
	public I_M_Product pWithholding;

	public WaschprobeStandardMasterData()
	{
		this(new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None));
	}

	public WaschprobeStandardMasterData(final IContextAware context)
	{
		super();

		Check.assumeNotNull(context, "context not null");
		this.context = context;

		createMasterData();
	}

	public IContextAware getContext()
	{
		return context;
	}

	private void createMasterData()
	{
		this.materialReceiptDate = TimeUtil.getDay(2015, 12, 06);

		//
		// Create data from HardCodedQualityBasedConfig
		this.materialReceiptOrigin = InterfaceWrapperHelper.newInstance(I_C_Country.class, context);
		this.materialReceiptOrigin.setName("materialReceiptOrigin");
		InterfaceWrapperHelper.save(this.materialReceiptOrigin);

		final int uomPrecision = 1;
		this.uomScrap = createUOMByX12DE355(HardCodedQualityBasedConfig.C_UOM_SCRAP_X12DE355, uomPrecision);
		this.uomFee = createUOMByX12DE355(HardCodedQualityBasedConfig.C_UOM_FEE_X12DE355, uomPrecision);
		this.uom = uomScrap;

		this.pCarrot_Scrap = createProduct(HardCodedQualityBasedConfig.M_PRODUCT_SCRAP_VALUE, uomScrap);
		this.pFeeBasicLine = createProduct(HardCodedQualityBasedConfig.M_PRODUCT_BASICLINE_FEE_VALUE, uomFee);
		this.pFeePromotion = createProduct(HardCodedQualityBasedConfig.M_PRODUCT_PROMOTION_FEE_VALUE, uomFee);
		this.pRegularOrder = createProduct(HardCodedQualityBasedConfig.M_PRODUCT_REGULAR_PP_ORDER_VALUE, uom);
		this.pWithholding = createProduct(HardCodedQualityBasedConfig.M_PRODUCT_WITHHOLDING_VALUE, uomFee);

		this.pCarrot_Washed = createProduct("Carrot_Washed", uom);
		this.pCarrot_Unwashed = createProduct("Carrot_Unwashed", uom);
		this.pCarrot_Unwashed_Alternative01 = createProduct("Carrot_Unwashed_Alternative01", uom);
		this.pCarrot_Big = createProduct("Carrot_Big", uom);
		this.pCarrot_AnimalFood = createProduct(HardCodedQualityBasedConfig.M_PRODUCT_SORTING_OUT_FEE_VALUE, uom);

		//
		// Carrot Invoice DocTypes
		final I_C_DocType invoiceDocTypeDownPayment = InterfaceWrapperHelper.newInstance(I_C_DocType.class, context);
		invoiceDocTypeDownPayment.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		invoiceDocTypeDownPayment.setDocSubType(IMaterialTrackingBL.C_DocType_INVOICE_DOCSUBTYPE_QI_DownPayment);
		InterfaceWrapperHelper.save(invoiceDocTypeDownPayment);

		final I_C_DocType invoiceDocTypeFinalSettlement = InterfaceWrapperHelper.newInstance(I_C_DocType.class, context);
		invoiceDocTypeFinalSettlement.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		invoiceDocTypeFinalSettlement.setDocSubType(IMaterialTrackingBL.C_DocType_INVOICE_DOCSUBTYPE_QI_FinalSettlement);
		InterfaceWrapperHelper.save(invoiceDocTypeFinalSettlement);
	}

	public I_M_Product createProduct(final String value)
	{
		final I_C_UOM uom = null;
		return createProduct(value, uom);
	}

	public I_M_Product createProduct(final String value, final I_C_UOM uom)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, context);
		product.setValue(value);
		product.setName(value);
		if (uom != null)
		{
			product.setC_UOM_ID(uom.getC_UOM_ID());
		}
		InterfaceWrapperHelper.save(product);

		MockedPricingRule.INSTANCE.setC_UOM(product, uom);

		return product;
	}

	public I_C_UOM createUOM(final String value)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class, context);
		uom.setName(value);
		uom.setUOMSymbol(value);
		uom.setStdPrecision(2);
		InterfaceWrapperHelper.save(uom);
		return uom;
	}

	public I_C_UOM createUOMByX12DE355(final String x12de355, final int precision)
	{
		I_C_UOM uom = Services.get(IUOMDAO.class).retrieveByX12DE355(context.getCtx(), x12de355, false); // throwExIfNull == false
		if (uom == null)
		{
			uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class, context);
		}
		uom.setName(x12de355);
		uom.setUOMSymbol(x12de355);
		uom.setStdPrecision(precision);
		uom.setX12DE355(x12de355);
		InterfaceWrapperHelper.save(uom);
		return uom;
	}

	public I_PP_Order createPP_Order(final I_M_Product product,
			final BigDecimal qtyDelivered,
			final I_C_UOM uom,
			final Timestamp productionDate)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, context);
		ppOrder.setDateDelivered(productionDate);
		ppOrder.setM_Product(product);
		ppOrder.setC_UOM(uom);
		ppOrder.setQtyDelivered(qtyDelivered);
		ppOrder.setQM_QtyDeliveredPercOfRaw(BigDecimal.ZERO); // to be set by BL
		ppOrder.setQM_QtyDeliveredAvg(BigDecimal.ZERO); // to be set by BL
		InterfaceWrapperHelper.save(ppOrder);
		return ppOrder;
	}

	public I_PP_Order_BOMLine createPP_Order_BOMLine(final org.eevolution.model.I_PP_Order ppOrder,
			final String componentType,
			final I_M_Product product,
			final BigDecimal qtyDelivered,
			final I_C_UOM uom)
	{
		final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class, context);
		ppOrderBOMLine.setPP_Order(ppOrder);
		ppOrderBOMLine.setComponentType(componentType);
		ppOrderBOMLine.setM_Product(product);
		ppOrderBOMLine.setC_UOM(uom);

		ppOrderBOMLine.setQtyDelivered(qtyDelivered);
		ppOrderBOMLine.setQtyDeliveredActual(qtyDelivered);
		ppOrderBOMLine.setQtyUsageVariance(BigDecimal.ZERO);
		ppOrderBOMLine.setQM_QtyDeliveredPercOfRaw(BigDecimal.ZERO); // to be set by BL
		ppOrderBOMLine.setQM_QtyDeliveredAvg(BigDecimal.ZERO); // to be set by BL

		InterfaceWrapperHelper.save(ppOrderBOMLine);
		return ppOrderBOMLine;
	}

	public static void assertEquals(final List<IProductionMaterial> expected, final List<IProductionMaterial> actual)
	{
		final int expectedSize = expected.size();
		final int actualSize = actual.size();
		if (expectedSize != actualSize)
		{
			Assert.fail("Invalid size"
					+ "\n Expected Items: " + expected
					+ "\n Actual Items: " + actual);
		}

		for (int i = 0; i < expectedSize; i++)
		{
			final IProductionMaterial expectedItem = expected.get(i);
			final IProductionMaterial actualItem = actual.get(i);
			if (!ProductionMaterialComparator.instance.equals(expectedItem, actualItem))
			{
				Assert.fail("Invalid item at index " + i
						+ "\n Expected item: " + expectedItem
						+ "\n Actual item: " + actualItem
						+ "\n Expected Items: " + expected
						+ "\n Actual Items: " + actual);

			}
		}
	}

	public I_M_Material_Tracking createMaterialTracking()
	{
		final BigDecimal qtyReceived = null;
		return createMaterialTracking(qtyReceived);
	}

	public I_M_Material_Tracking createMaterialTracking(final BigDecimal qtyReceived)
	{
		final I_M_Material_Tracking materialTracking = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking.class, context);
		materialTracking.setQtyReceived(qtyReceived);
		InterfaceWrapperHelper.save(materialTracking);
		return materialTracking;
	}

	public I_M_InOutLine createInOutLine(final I_M_Product product,
			final Timestamp materialReceiptDate)
	{
		final I_C_Location l = InterfaceWrapperHelper.newInstance(I_C_Location.class, context);
		l.setC_Country_ID(materialReceiptOrigin.getC_Country_ID());
		InterfaceWrapperHelper.save(l);

		final I_C_BPartner_Location bpl = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, context);
		bpl.setC_Location_ID(l.getC_Location_ID());
		InterfaceWrapperHelper.save(bpl);

		final I_M_InOut io = InterfaceWrapperHelper.newInstance(I_M_InOut.class, context);
		io.setMovementDate(materialReceiptDate);
		io.setC_BPartner_Location(bpl);
		InterfaceWrapperHelper.save(io);

		final I_M_InOutLine iol = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class, context);
		iol.setM_InOut(io);
		iol.setM_Product(product);
		InterfaceWrapperHelper.save(iol);

		return iol;
	}

	public I_PP_Cost_Collector createPP_CostCollector_Issue(final I_PP_Order ppOrder,
			final I_M_Product issuedProduct,
			final BigDecimal issuedQty)
	{
		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class, context);
		cc.setPP_Order(ppOrder);
		cc.setCostCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue);
		cc.setMovementQty(issuedQty);

		InterfaceWrapperHelper.save(cc);

		return cc;
	}
}
