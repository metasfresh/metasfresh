package de.metas.materialtracking.ait.helpers;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_M_Product;
import de.metas.document.engine.IDocument;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.model.validator.PP_Order;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;
import de.metas.materialtracking.spi.impl.PlainPPOrderMInOutLineRetrievalService;

/*
 * #%L
 * de.metas.materialtracking.ait
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PPOrderDriver
{
	public static void setupPPOrderHeader(
			final String nameDocType,
			final String documentNo,
			final String lotMaterialTracking,
			final String strDate) throws Throwable
	{
		final I_C_DocType docType = Helper.retrieveExisting(nameDocType, I_C_DocType.class);

		final I_M_Material_Tracking materialTracking = Helper.retrieveExisting(lotMaterialTracking, I_M_Material_Tracking.class);

		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, Helper.context);
		ppOrder.setDateDelivered(Helper.parseTimestamp(strDate));
		ppOrder.setC_DocType(docType);
		ppOrder.setOrderType(docType.getDocSubType());
		ppOrder.setDocStatus(IDocument.STATUS_Closed);
		ppOrder.setM_Material_Tracking(materialTracking);
		ppOrder.setIsInvoiceCandidate(false);
		ppOrder.setDocumentNo(documentNo);

		InterfaceWrapperHelper.save(ppOrder);
		Helper.storeFirstTime(documentNo, ppOrder);

		Helper.link(materialTracking, ppOrder);
	}

	public static String setupPPOrderItems(
			final String documentNo,
			final String strType,
			final String valueProduct,
			final String strQty,
			final String nameReceipt)
	{

		final I_PP_Order ppOrder = Helper.retrieveExisting(documentNo, I_PP_Order.class);

		final I_PP_Order.Type type = I_PP_Order.Type.valueOf(strType);
		final BigDecimal qty = new BigDecimal(strQty);
		final I_M_Product product = Helper.retrieveExisting(valueProduct, I_M_Product.class);

		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class, Helper.context);
		cc.setPP_Order(ppOrder);
		cc.setM_Product(product);
		cc.setCostCollectorType(type.getCostCollectorType());
		cc.setMovementQty(qty.multiply(type.getFactor()));
		cc.setDocStatus(IDocument.STATUS_Completed); // DocStatus is needed, because as of recently, we ignore PP_Orders that don't have a completed/closed issue-cc with qty>0
		InterfaceWrapperHelper.save(cc);

		if (type == I_PP_Order.Type.MainProduct)
		{
			ppOrder.setM_Product(product);
			ppOrder.setC_UOM(product.getC_UOM());
			ppOrder.setQtyDelivered(qty);
			InterfaceWrapperHelper.save(ppOrder);
		}
		else
		{
			final org.eevolution.model.I_PP_Order_BOMLine existingBomLine = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLine(ppOrder, product);
			if (existingBomLine == null)
			{
				final I_PP_Order_BOMLine bomLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class, Helper.context);
				bomLine.setPP_Order(ppOrder);
				bomLine.setComponentType(type.getBomLineComponentType());
				bomLine.setM_Product(product);
				bomLine.setC_UOM(product.getC_UOM());
				bomLine.setQtyDeliveredActual(qty.multiply(type.getFactor()));
				InterfaceWrapperHelper.save(bomLine);
			}
			else
			{
				existingBomLine.setQtyDeliveredActual(existingBomLine.getQtyDeliveredActual().add(qty.multiply(type.getFactor())));
				InterfaceWrapperHelper.save(existingBomLine);
			}
		}

		if (Check.isEmpty(nameReceipt, true))
		{
			return "OK"; // nothing more to be done
		}
		final I_M_InOutLine iol = Helper.retrieveExisting(nameReceipt, I_M_InOutLine.class);
		final PlainPPOrderMInOutLineRetrievalService plainPPOrderMInOutLineRetrievalService =
				(PlainPPOrderMInOutLineRetrievalService)Services.get(IPPOrderMInOutLineRetrievalService.class);
		plainPPOrderMInOutLineRetrievalService.addAssociation(cc, iol);
		return "OK";
	}

	public static void updatePPOrderQualityFields(final String documentNo)
	{
		final I_PP_Order ppOrder = Helper.retrieveExisting(documentNo, I_PP_Order.class);
		PP_Order.instance.updateQualityFields(ppOrder, ModelValidator.TIMING_AFTER_CLOSE);

		Helper.storeOverride(documentNo, ppOrder);
	}
}
