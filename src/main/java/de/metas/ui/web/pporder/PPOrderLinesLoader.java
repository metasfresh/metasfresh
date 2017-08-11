package de.metas.ui.web.pporder;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.model.X_PP_Order;
import org.eevolution.model.X_PP_Order_BOMLine;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowAttributesProvider;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;
import de.metas.ui.web.handlingunits.util.HUPackingInfoFormatter;
import de.metas.ui.web.handlingunits.util.HUPackingInfos;
import de.metas.ui.web.handlingunits.util.IHUPackingInfo;
import de.metas.ui.web.view.ASIViewRowAttributesProvider;
import de.metas.ui.web.view.IViewRowAttributes;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.Builder;
import lombok.NonNull;

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
	public static final PPOrderLinesLoaderBuilder builder(final WindowId viewWindowId)
	{
		return new PPOrderLinesLoaderBuilder().viewWindowId(viewWindowId);
	}

	//
	// Services
	private final transient IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final transient IHUPPOrderQtyDAO ppOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	//
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	//
	private final transient HUEditorViewRepository huEditorRepo;
	private final HUEditorRowAttributesProvider huAttributesProvider;
	private final ASIViewRowAttributesProvider asiAttributesProvider;

	@Builder
	public PPOrderLinesLoader(final WindowId viewWindowId, final ASIViewRowAttributesProvider asiAttributesProvider)
	{
		huAttributesProvider = HUEditorRowAttributesProvider.builder()
				.readonly(false)
				.build();

		huEditorRepo = HUEditorViewRepository.builder()
				.windowId(viewWindowId)
				.referencingTableName(I_PP_Order.Table_Name)
				.attributesProvider(huAttributesProvider)
				.build();

		this.asiAttributesProvider = asiAttributesProvider;
	}

	/**
	 * Loads {@link PPOrderLinesViewData}s.
	 *
	 * @param viewId viewId to be set to newly created {@link PPOrderLineRow}s.
	 */
	public PPOrderLinesViewData retrieveData(final int ppOrderId)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.create(Env.getCtx(), ppOrderId, I_PP_Order.class, ITrx.TRXNAME_None);
		final ITranslatableString ppOrder_description = extractDescription(ppOrder);
		final String ppOrder_planningStatus = ppOrder.getPlanningStatus();
		final boolean readonly = X_PP_Order.PLANNINGSTATUS_Complete.equals(ppOrder_planningStatus);

		final int mainProductBOMLineId = 0;
		final ListMultimap<Integer, I_PP_Order_Qty> ppOrderQtysByBOMLineId = ppOrderQtyDAO.streamOrderQtys(ppOrderId)
				.collect(GuavaCollectors.toImmutableListMultimap(ppOrderQty -> Util.firstGreaterThanZero(ppOrderQty.getPP_Order_BOMLine_ID(), mainProductBOMLineId)));

		final ImmutableList.Builder<PPOrderLineRow> records = ImmutableList.builder();

		// Main product
		records.add(createForMainProduct(ppOrder, readonly, ppOrderQtysByBOMLineId.get(mainProductBOMLineId)));

		//
		// BOM lines
		ppOrderBOMDAO.retrieveOrderBOMLines(ppOrder, I_PP_Order_BOMLine.class)
				.stream()
				.map(ppOrderBOMLine -> createForBOMLine(ppOrder, ppOrderBOMLine, readonly, ppOrderQtysByBOMLineId.get(ppOrderBOMLine.getPP_Order_BOMLine_ID())))
				.sorted(Comparator.<PPOrderLineRow> comparingInt(row -> row.isReceipt() ? 0 : 1) // receipt lines first
						.thenComparing(row -> row.getPP_Order_BOMLine_ID())) // BOM lines order
				.forEach(records::add);

		return new PPOrderLinesViewData(ppOrder_description, ppOrder_planningStatus, records.build());
	}

	private static final ITranslatableString extractDescription(final I_PP_Order ppOrder)
	{
		final ITranslatableString docTypeStr;
		final I_C_DocType docType = ppOrder.getC_DocType();
		if (docType != null)
		{
			final IModelTranslationMap docTypeTrlMap = InterfaceWrapperHelper.getModelTranslationMap(docType);
			docTypeStr = docTypeTrlMap.getColumnTrl(I_C_DocType.COLUMNNAME_Name, docType.getName());
		}
		else
		{
			docTypeStr = ImmutableTranslatableString.empty();
		}

		final ITranslatableString documentNoStr = ImmutableTranslatableString.constant(ppOrder.getDocumentNo());

		return ITranslatableString.compose(" ", docTypeStr, documentNoStr);
	}

	private static final String extractPackingInfoString(final I_M_HU_LUTU_Configuration lutuConfig)
	{
		if (lutuConfig == null)
		{
			return null;
		}

		final IHUPackingInfo packingInfo = HUPackingInfos.of(lutuConfig);
		return HUPackingInfoFormatter.newInstance()
				.setShowLU(false)
				.format(packingInfo);
	}

	private final Supplier<IViewRowAttributes> createASIAttributesSupplier(final DocumentId documentId, final int asiId)
	{
		if (asiId > 0)
		{
			return () -> asiAttributesProvider.getAttributes(documentId, DocumentId.of(asiId));
		}
		else
		{
			return null;
		}
	}

	private PPOrderLineRow createForMainProduct(final I_PP_Order ppOrder, final boolean readonly, final List<I_PP_Order_Qty> ppOrderQtys)
	{
		final DocumentId rowId = DocumentId.of(org.eevolution.model.I_PP_Order.Table_Name + "_" + ppOrder.getPP_Order_ID());

		final BigDecimal qtyPlan = ppOrder.getQtyOrdered();
		final I_M_HU_LUTU_Configuration lutuConfig = huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrder).getCreateLUTUConfiguration();

		return PPOrderLineRow.builder(rowId)
				.ppOrder(ppOrder.getPP_Order_ID())
				.setType(PPOrderLineType.MainProduct)
				.setProcessed(readonly)
				//
				.setProduct(createProductLookupValue(ppOrder.getM_Product()))
				.setPackingInfo(extractPackingInfoString(lutuConfig))
				.setUOM(createUOMLookupValue(ppOrder.getC_UOM()))
				.setQtyPlan(qtyPlan)
				.setQtyAsSumOfIncludedQtys()
				.setAttributesSupplier(createASIAttributesSupplier(rowId, ppOrder.getM_AttributeSetInstance_ID()))
				//
				.addIncludedDocumentFrom(ppOrderQtys, ppOrderQty -> createForQty(ppOrderQty, readonly))
				//
				.build();
	}

	private PPOrderLineRow createForBOMLine(final I_PP_Order ppOrder, final I_PP_Order_BOMLine ppOrderBOMLine, final boolean readonly, final List<I_PP_Order_Qty> ppOrderQtys)
	{
		final DocumentId rowId = DocumentId.of(org.eevolution.model.I_PP_Order_BOMLine.Table_Name + "_" + ppOrderBOMLine.getPP_Order_BOMLine_ID());

		final PPOrderLineType lineType;
		final String packingInfo;
		final BigDecimal qtyPlan;
		final String componentType = ppOrderBOMLine.getComponentType();
		if (X_PP_Order_BOMLine.COMPONENTTYPE_By_Product.equals(componentType)
				|| X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product.equals(componentType))
		{
			lineType = PPOrderLineType.BOMLine_ByCoProduct;
			final I_M_HU_LUTU_Configuration lutuConfig = huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrderBOMLine).getCreateLUTUConfiguration();
			packingInfo = extractPackingInfoString(lutuConfig);

			qtyPlan = ppOrderBOMBL.adjustCoProductQty(ppOrderBOMLine.getQtyRequiered());
		}
		else
		{
			lineType = PPOrderLineType.BOMLine_Component;
			packingInfo = null; // we don't know the packing info for what will be issued.

			qtyPlan = ppOrderBOMLine.getQtyRequiered();
		}

		final PPOrderLineRow.Builder builder = PPOrderLineRow.builder(rowId)
				.ppOrderBOMLineId(ppOrderBOMLine.getPP_Order_ID(), ppOrderBOMLine.getPP_Order_BOMLine_ID())
				.setType(lineType)
				.setProcessed(readonly)
				//
				.setProduct(createProductLookupValue(ppOrderBOMLine.getM_Product()))
				.setPackingInfo(packingInfo)
				.setUOM(createUOMLookupValue(ppOrderBOMLine.getC_UOM()))
				.setQtyPlan(qtyPlan)
				.setQtyAsSumOfIncludedQtys()
				.setAttributesSupplier(createASIAttributesSupplier(rowId, ppOrderBOMLine.getM_AttributeSetInstance_ID()));

		ppOrderQtys.stream()
				.map(ppOrderQty -> createForQty(ppOrderQty, readonly))
				.forEach(huViewRecord -> builder.addIncludedDocument(huViewRecord));

		return builder.build();
	}

	private PPOrderLineRow createForQty(final I_PP_Order_Qty ppOrderQty, final boolean readonly)
	{
		final HUEditorRow huEditorRow = huEditorRepo.retrieveForHUId(ppOrderQty.getM_HU_ID());
		final HUEditorRow parentHUViewRecord = null;
		return createForHUViewRecordRecursively(ppOrderQty, huEditorRow, parentHUViewRecord, readonly);
	}

	private PPOrderLineRow createForHUViewRecordRecursively(
			@NonNull final I_PP_Order_Qty ppOrderQty, 
			@NonNull final HUEditorRow huEditorRow, 
			@Nullable final HUEditorRow parentHUEditorRow, 
			final boolean readonly)
	{
		final PPOrderLineType type = PPOrderLineType.ofHUEditorRowType(huEditorRow.getType());

		//
		// Get HU's quantity.
		final BigDecimal qty;
		final JSONLookupValue qtyUOM;
		if (huEditorRow.isHUStatusDestroyed())
		{
			// Top level HU which was already destroyed (i.e. it was already issued & processed)
			// => get the Qty/UOM from PP_Order_Qty because on HU level, for sure it will be ZERO.
			if (parentHUEditorRow == null)
			{
				qty = ppOrderQty.getQty();
				qtyUOM = createUOMLookupValue(ppOrderQty.getC_UOM());
			}
			// Included HU which was already destroyed
			// => we don't know the Qty
			else
			{
				qty = null;
				qtyUOM = huEditorRow.getUOM();
			}
		}
		else
		{
			qty = huEditorRow.getQtyCU();
			qtyUOM = huEditorRow.getUOM();
		}

		//
		return PPOrderLineRow.builder(huEditorRow.getId())
				.huId(huEditorRow.getM_HU_ID())
				.ppOrderQtyId(ppOrderQty.getPP_Order_Qty_ID())
				.processed(readonly || ppOrderQty.isProcessed())
				.setType(type)
				.setAttributesSupplier(huEditorRow.getAttributesSupplier())
				//
				.setCode(huEditorRow.getValue())
				.setProduct(huEditorRow.getProduct())
				.setPackingInfo(huEditorRow.getPackingInfo())
				.setUOM(qtyUOM)
				.setQtyPlan(null) // always null
				.setQty(qty)
				//
				.addIncludedDocumentFrom(huEditorRow.getIncludedRows(), includedHUEditorRow -> createForHUViewRecordRecursively(ppOrderQty, includedHUEditorRow, huEditorRow, readonly))
				//
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
