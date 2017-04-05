package de.metas.ui.web.pporder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.model.X_PP_Order_BOMLine;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.ui.web.handlingunits.HUDocumentView;
import de.metas.ui.web.handlingunits.HUDocumentViewLoader;
import de.metas.ui.web.view.DocumentView;
import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.view.json.JSONDocumentViewCreateRequest;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PPOrderLinesLoader
{
	public static final PPOrderLinesLoader of(final JSONDocumentViewCreateRequest request)
	{
		return new PPOrderLinesLoader(request);
	}

	private final transient IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final transient IHUPPOrderQtyDAO ppOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final transient HUDocumentViewLoader _huViewRecordLoader;

	private final int _adWindowId;
	private int _ppOrderId;

	public PPOrderLinesLoader(final JSONDocumentViewCreateRequest request)
	{
		_adWindowId = request.getAD_Window_ID();

		_huViewRecordLoader = HUDocumentViewLoader.of(request, I_PP_Order.Table_Name);

		final Set<Integer> filterOnlyIds = request.getFilterOnlyIds();
		final int ppOrderId = ListUtils.singleElement(filterOnlyIds);
		Preconditions.checkArgument(ppOrderId > 0, "No manufacturing order ID found in %s", request);
		this._ppOrderId = ppOrderId;
	}

	private int getAD_Window_ID()
	{
		return _adWindowId;
	}

	int getPP_Order_ID()
	{
		return _ppOrderId;
	}

	private HUDocumentViewLoader getHUViewRecordLoader()
	{
		return _huViewRecordLoader;
	}

	public List<PPOrderLineRow> retrieveRecords()
	{
		final int ppOrderId = getPP_Order_ID();
		final I_PP_Order ppOrder = InterfaceWrapperHelper.create(Env.getCtx(), ppOrderId, I_PP_Order.class, ITrx.TRXNAME_None);

		final ListMultimap<Integer, I_PP_Order_Qty> ppOrderQtysByBOMLineId = ppOrderQtyDAO.streamOrderQtys(ppOrderId)
				.collect(GuavaCollectors.toImmutableListMultimap(ppOrderQty -> Util.firstGreaterThanZero(ppOrderQty.getPP_Order_BOMLine_ID(), 0)));

		final ImmutableList.Builder<PPOrderLineRow> records = ImmutableList.builder();

		// Main product
		records.add(createForMainProduct(ppOrder, ppOrderQtysByBOMLineId.get(0)));

		//
		// BOM lines
		ppOrderBOMDAO.retrieveOrderBOMLines(ppOrder, I_PP_Order_BOMLine.class)
				.stream()
				.map(ppOrderBOMLine -> createForBOMLine(ppOrderBOMLine, ppOrderQtysByBOMLineId.get(ppOrderBOMLine.getPP_Order_BOMLine_ID())))
				.forEach(records::add);

		return records.build();
	}

	private PPOrderLineRow createForMainProduct(final I_PP_Order ppOrder, final List<I_PP_Order_Qty> ppOrderQtys)
	{
		final DocumentId documentId = DocumentId.of(I_PP_Order.Table_Name + "_" + ppOrder.getPP_Order_ID());

		final BigDecimal qty = ppOrder.getQtyDelivered();
		final BigDecimal qtyPlanTotal = ppOrder.getQtyOrdered();
		final BigDecimal qtyPlan = qtyPlanTotal.subtract(qty);
		
		final DocumentView.Builder builder = DocumentView.builder(getAD_Window_ID())
				.setDocumentId(documentId)
				.setType(PPOrderLineType.MainProduct)
				//
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_Value, null)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_BOMType, null)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_HUType, null)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_M_Product_ID, createProductLookupValue(ppOrder.getM_Product()))
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_PackingInfo, null)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_C_UOM_ID, createUOMLookupValue(ppOrder.getC_UOM()))
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_Qty, qty)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_QtyPlan, qtyPlan)
		//
		;

		ppOrderQtys.stream()
				.map(ppOrderQty -> createForQty(ppOrderQty))
				.forEach(huViewRecord -> builder.addIncludedDocument(huViewRecord));

		return PPOrderLineRow.builder(builder.build())
				.ppOrder(ppOrder.getPP_Order_ID())
				.build();
	}

	private PPOrderLineRow createForBOMLine(final I_PP_Order_BOMLine ppOrderBOMLine, final List<I_PP_Order_Qty> ppOrderQtys)
	{
		final DocumentId documentId = DocumentId.of(I_PP_Order_BOMLine.Table_Name + "_" + ppOrderBOMLine.getPP_Order_BOMLine_ID());

		final PPOrderLineType lineType;
		final String componentType = ppOrderBOMLine.getComponentType();
		if (X_PP_Order_BOMLine.COMPONENTTYPE_By_Product.equals(componentType)
				|| X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product.equals(componentType))
		{
			lineType = PPOrderLineType.BOMLine_ByCoProduct;
		}
		else
		{
			lineType = PPOrderLineType.BOMLine_Component;
		}

		final BigDecimal qty = ppOrderBOMLine.getQtyDeliveredActual();
		final BigDecimal qtyPlanTotal = ppOrderBOMLine.getQtyRequiered();
		final BigDecimal qtyPlan = qtyPlanTotal.subtract(qty);
		
		final DocumentView.Builder builder = DocumentView.builder(getAD_Window_ID())
				.setDocumentId(documentId)
				.setType(lineType)
				//
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_Value, null)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_BOMType, ppOrderBOMLine.getComponentType())
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_HUType, null)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_M_Product_ID, createProductLookupValue(ppOrderBOMLine.getM_Product()))
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_PackingInfo, null)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_C_UOM_ID, createUOMLookupValue(ppOrderBOMLine.getC_UOM()))
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_Qty, qty)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_QtyPlan, qtyPlan)
		//
		;
		ppOrderQtys.stream()
				.map(ppOrderQty -> createForQty(ppOrderQty))
				.forEach(huViewRecord -> builder.addIncludedDocument(huViewRecord));

		return PPOrderLineRow.builder(builder.build())
				.ppOrderBOMLineId(ppOrderBOMLine.getPP_Order_ID(), ppOrderBOMLine.getPP_Order_BOMLine_ID())
				.build();
	}

	private IDocumentView createForQty(final I_PP_Order_Qty ppOrderQty)
	{
		final HUDocumentView huViewRecord = getHUViewRecordLoader().retrieveForHUId(ppOrderQty.getM_HU_ID());
		return createForHUViewRecordRecursivelly(ppOrderQty, huViewRecord);
	}

	private PPOrderLineRow createForHUViewRecordRecursivelly(final I_PP_Order_Qty ppOrderQty, final HUDocumentView huViewRecord)
	{
		final PPOrderLineType type = PPOrderLineType.ofHUDocumentViewType(huViewRecord.getType());

		final BigDecimal qty;
		final BigDecimal qtyPlan;
		if (ppOrderQty.isProcessed())
		{
			qtyPlan = BigDecimal.ZERO;
			qty = huViewRecord.getQtyCU();
		}
		else
		{
			qtyPlan = huViewRecord.getQtyCU();
			qty = BigDecimal.ZERO;
		}

		final DocumentView.Builder builder = DocumentView.builder(getAD_Window_ID())
				.setDocumentId(huViewRecord.getDocumentId())
				.setType(type)
				//
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_Value, huViewRecord.getValue())
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_BOMType, null)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_HUType, huViewRecord.getType())
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_M_Product_ID, huViewRecord.getProduct())
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_PackingInfo, huViewRecord.getPackingInfo())
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_C_UOM_ID, huViewRecord.getUOM())
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_Qty, qty)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_QtyPlan, qtyPlan)
				.putFieldValue(IPPOrderBOMLine.COLUMNNAME_StatusInfo, huViewRecord.getHUStatusDisplayName())
		//
		;

		huViewRecord.getIncludedDocuments()
				.stream()
				.map(includedHUViewRecord -> createForHUViewRecordRecursivelly(ppOrderQty, includedHUViewRecord))
				.forEach(builder::addIncludedDocument);

		return PPOrderLineRow.builder(builder.build())
				.ppOrderQtyId(ppOrderQty.getPP_Order_Qty_ID())
				.processed(ppOrderQty.isProcessed())
				.build();
	}

	private static JSONLookupValue createProductLookupValue(final I_M_Product product)
	{
		if (product == null)
		{
			return null;
		}

		final String displayName = product.getValue() + "_" + product.getName();
		return JSONLookupValue.of(product.getM_Product_ID(), displayName);
	}

	private static JSONLookupValue createUOMLookupValue(final I_C_UOM uom)
	{
		if (uom == null)
		{
			return null;
		}

		return JSONLookupValue.of(uom.getC_UOM_ID(), uom.getUOMSymbol());
	}

}
