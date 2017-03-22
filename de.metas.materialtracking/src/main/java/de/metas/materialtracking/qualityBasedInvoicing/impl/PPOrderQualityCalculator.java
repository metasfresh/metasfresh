package de.metas.materialtracking.qualityBasedInvoicing.impl;

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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.document.engine.IDocActionBL;
import de.metas.logging.LogManager;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfig;

/**
 * Calculates and updates Quality related fields of a {@link I_PP_Order} (and it's BOM Lines).
 *
 * @author tsa
 *
 */
public class PPOrderQualityCalculator
{
	private List<Object> modelsToBeSaved = null;

	private static final transient Logger logger = LogManager.getLogger(PPOrderQualityCalculator.class);

	public PPOrderQualityCalculator()
	{
		super();
	}

	public void update(final IMaterialTrackingDocuments documents)
	{
		modelsToBeSaved = new ArrayList<>();

		//
		// Iterate quality orders and update their quality related fields
		IQualityInspectionOrder qiOrderPrevious = null;
		for (final IQualityInspectionOrder qiOrder : documents.getQualityInspectionOrders())
		{
			if(!qiOrder.isQualityInspection())
			{
				continue;
			}

			Loggables.get().addLog("Processing PP_Order {0}", qiOrder.getPP_Order());

			//
			// Update QM_QtyDeliveredPercOfRaw
			updateQM_QtyDeliveredPercOfRaw(qiOrder);

			//
			// Update QM_QtyDeliveredAvg
			updateQM_QtyDeliveredAvg(qiOrder, qiOrderPrevious);

			//
			// Create PP_Order_Report lines
			createPP_Order_Report_Lines(qiOrder);

			//
			// Set previous QI Order as current one (to be used in next iteration)
			qiOrderPrevious = qiOrder;
		}

		//
		// Save everything that needs to be saved
		for (final Object model : modelsToBeSaved)
		{
			InterfaceWrapperHelper.save(model);
		}
		modelsToBeSaved = null;
	}

	private void createPP_Order_Report_Lines(final IQualityInspectionOrder qiOrder)
	{
		//
		// Create Quality Inspection Lines
		final IVendorReceipt<Object> receiptFromVendor = createVendorReceipt(qiOrder);

		final boolean buildWithAveragedValues = false; // the averaged values are only required when we invoice
		final QualityInspectionLinesBuilder qualityInspectionLinesBuilder = new QualityInspectionLinesBuilder(qiOrder, buildWithAveragedValues);

		qualityInspectionLinesBuilder.setReceiptFromVendor(receiptFromVendor);
		qualityInspectionLinesBuilder.create();

		//
		// Save Report Lines
		final IQualityBasedConfig config = qiOrder.getQualityBasedConfig();
		final PPOrderReportWriter writer = new PPOrderReportWriter(qiOrder.getPP_Order());
		writer.setLineTypes(config.getPPOrderReportLineTypes());

		writer.deleteReportLines(); // discard old lines, if any
		writer.save(qualityInspectionLinesBuilder.getCreatedQualityInspectionLines());
	}

	private IVendorReceipt<Object> createVendorReceipt(final IQualityInspectionOrder qiOrder)
	{
		final I_M_Material_Tracking materialTracking = qiOrder.getM_Material_Tracking();

		final IProductionMaterial rawProductionMaterial = qiOrder.getRawProductionMaterial();
		final I_C_UOM qtyReceivedFromVendorUOM = rawProductionMaterial.getC_UOM();

		//
		// Find out which is our main raw product
		// I_M_Product rawMainProduct = rawProductionMaterial.getMainComponentProduct();
		// if (rawMainProduct == null)
		// {
		// rawMainProduct = rawProductionMaterial.getM_Product();
		// }
		// TODO: notes, todos, thoughts
		// * we need to make sure rawMainProduct is same as the one from M_MaterialTracking.M_Product_ID; maybe we would like to use that one
		// * there is no need to check for the main component, even if this is a variant product...
		final I_M_Product rawMainProduct = rawProductionMaterial.getM_Product();

		//
		// Retrieve Qty Received from Vendor
		final BigDecimal qtyReceivedFromVendor = retrieveQtyReceived(materialTracking, rawMainProduct, qtyReceivedFromVendorUOM);

		//
		// Create a plain IVendorReceipt and return it
		final PlainVendorReceipt plainReceiptFromVendor = new PlainVendorReceipt();
		plainReceiptFromVendor.setM_Product(rawMainProduct);
		plainReceiptFromVendor.setQtyReceived(qtyReceivedFromVendor);
		plainReceiptFromVendor.setQtyReceivedUOM(qtyReceivedFromVendorUOM);
		return plainReceiptFromVendor;
	}

	/**
	 *
	 * @param materialTracking
	 * @param product
	 * @param uomTo
	 * @return quantity received (in <code>uomTo</code>) from linked material receipt lines
	 */
	private final BigDecimal retrieveQtyReceived(final I_M_Material_Tracking materialTracking, final I_M_Product product, final I_C_UOM uomTo)
	{
		// Services
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);

		Check.assumeNotNull(product, "product not null");
		final int receivedProductId = product.getM_Product_ID();
		final I_C_UOM productUOM = product.getC_UOM();
		final IUOMConversionContext uomConversionCtx = uomConversionBL.createConversionContext(product);

		BigDecimal qtyReceivedTotal = BigDecimal.ZERO;
		final List<I_M_InOutLine> inoutLines = materialTrackingDAO.retrieveReferences(materialTracking, I_M_InOutLine.class);
		for (final I_M_InOutLine inoutLine : inoutLines)
		{
			// skip inactive lines (shall not happen)
			if (!inoutLine.isActive())
			{
				continue;
			}

			// skip if the material receipt is not about our product
			if (inoutLine.getM_Product_ID() != receivedProductId)
			{
				continue;
			}

			// skip shipments
			final I_M_InOut inout = inoutLine.getM_InOut();
			if (inout.isSOTrx())
			{
				continue;
			}

			// skip receipts which are not COmpleted/CLosed
			if (!docActionBL.isStatusCompletedOrClosed(inout))
			{
				continue;
			}

			final BigDecimal qty = inoutLine.getMovementQty();
			final BigDecimal qtyConv = uomConversionBL.convertQty(uomConversionCtx, qty, productUOM, uomTo);
			qtyReceivedTotal = qtyReceivedTotal.add(qtyConv);
		}

		return qtyReceivedTotal;
	}

	private void save(final IProductionMaterial productionMaterial)
	{
		// NOTE: we assume productionMaterial implements IModelWrapper

		// Case: we will save it all together later
		if (modelsToBeSaved != null)
		{
			if (!modelsToBeSaved.contains(productionMaterial))
			{
				modelsToBeSaved.add(productionMaterial);
			}
		}
		// Case: we are saving it directly
		else
		{
			InterfaceWrapperHelper.save(productionMaterial);
		}
	}

	/* package */void updateQM_QtyDeliveredPercOfRaw(final IQualityInspectionOrder qiOrder)
	{
		Check.assumeNotNull(qiOrder, "qiOrder not null");
		final IProductionMaterial rawProductionMaterial = qiOrder.getRawProductionMaterial();
		final BigDecimal rawQty = rawProductionMaterial.getQty();
		final I_C_UOM rawQtyUOM = rawProductionMaterial.getC_UOM();

		//
		// Update production materials
		for (final IProductionMaterial productionMaterial : qiOrder.getProductionMaterials())
		{
			final BigDecimal qtyDeliveredInRawProductUOM = productionMaterial.getQty(rawQtyUOM);

			//
			// QtyDelivered Percentage of Raw Qty = qtyDeliveredInRawProductUOM / rawQty * 100
			final BigDecimal qtyDeliveredPercOfRaw = qtyDeliveredInRawProductUOM
					.divide(rawQty, 12, RoundingMode.HALF_UP)
					.multiply(Env.ONEHUNDRED)
					.setScale(2, RoundingMode.HALF_UP);

			productionMaterial.setQM_QtyDeliveredPercOfRaw(qtyDeliveredPercOfRaw);
			save(productionMaterial);
		}
	}

	private Quantity getQtyDelivered(final IProductionMaterial productionMaterial)
	{
		final BigDecimal qtyDelivered = productionMaterial.getQty();
		final I_C_UOM qtyDeliveredUOM = productionMaterial.getC_UOM();
		return new Quantity(qtyDelivered, qtyDeliveredUOM);
	}

	/* package */void updateQM_QtyDeliveredAvg(final IQualityInspectionOrder qiOrder, final IQualityInspectionOrder qiOrderPrevious)
	{
		//
		// Case of First Quality Inspection Order
		// => we just need to copy QtyDelivered to QM_QtyDeliveredAvg
		if (qiOrderPrevious == null)
		{
			Check.assume(qiOrder.getInspectionNumber() == 1, "qiOrder shall have inspection number 1");

			for (final IProductionMaterial productionMaterial : qiOrder.getProductionMaterials())
			{
				productionMaterial.setQM_QtyDeliveredAvg(productionMaterial.getQty());
				save(productionMaterial);
			}

			return;
		}

		//
		// Get and check Inspection Numbers
		Check.assumeNotNull(qiOrder, "qiOrder not null");
		final int inspectionNumber = qiOrder.getInspectionNumber();
		Check.assume(inspectionNumber >= 1, "inspectionNumber >= 1");
		final int previousInspectionNumber = inspectionNumber - 1;
		Check.assume(previousInspectionNumber == qiOrderPrevious.getInspectionNumber(), "Previous QI order has invalid inspection number");

		//
		// Update production materials
		for (final IProductionMaterial productionMaterial : qiOrder.getProductionMaterials())
		{
			final Quantity qtyDelivered = getQtyDelivered(productionMaterial);

			final IProductionMaterial previousProductionMaterial = qiOrderPrevious.getProductionMaterial(productionMaterial.getM_Product());

			final Quantity previousQtyDeliveredAvg;
			if (previousProductionMaterial == null)
			{
				logger.info("The previous PP_Order {} has no material that matches the material {} of the current PP_Order {}; was the BOM changed?",
						new Object[] { qiOrderPrevious, productionMaterial, qiOrder });
				previousQtyDeliveredAvg = qtyDelivered;
			}
			else
			{
				previousQtyDeliveredAvg = getQtyDeliveredAvg(previousProductionMaterial, qtyDelivered.getUOM());
			}

			final Quantity qtyDeliveredAvg = qtyDelivered.weightedAverage(previousQtyDeliveredAvg.getQty(), previousInspectionNumber);
			productionMaterial.setQM_QtyDeliveredAvg(qtyDeliveredAvg.getQty());

			save(productionMaterial);
		}
	}

	private Quantity getQtyDeliveredAvg(final IProductionMaterial productionMaterial, final I_C_UOM uomTo)
	{
		Check.assumeNotNull(productionMaterial, "Param 'productionMaterial' is not null");

		final BigDecimal qtyDeliveredAvg = productionMaterial.getQM_QtyDeliveredAvg(uomTo);
		return new Quantity(qtyDeliveredAvg, uomTo);
	}

}
