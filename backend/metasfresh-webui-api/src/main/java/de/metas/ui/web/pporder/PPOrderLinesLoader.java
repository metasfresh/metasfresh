package de.metas.ui.web.pporder;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderPlanningStatus;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowAttributesProvider;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;
import de.metas.ui.web.handlingunits.SqlHUEditorViewRepository;
import de.metas.ui.web.handlingunits.util.HUPackingInfoFormatter;
import de.metas.ui.web.handlingunits.util.HUPackingInfos;
import de.metas.ui.web.handlingunits.util.IHUPackingInfo;
import de.metas.ui.web.view.ASIViewRowAttributesProvider;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
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

class PPOrderLinesLoader
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
	private final transient HUEditorViewRepository huEditorRepo;
	private final ASIViewRowAttributesProvider asiAttributesProvider;

	@Builder
	public PPOrderLinesLoader(
			final WindowId viewWindowId,
			final ASIViewRowAttributesProvider asiAttributesProvider,
			@NonNull final SqlViewBinding huSQLViewBinding,
			@NonNull final HUReservationService huReservationService)
	{
		huEditorRepo = SqlHUEditorViewRepository.builder()
				.windowId(viewWindowId)
				.attributesProvider(HUEditorRowAttributesProvider.builder().readonly(false).build())
				.sqlViewBinding(huSQLViewBinding)
				.huReservationService(huReservationService)
				.build();

		this.asiAttributesProvider = asiAttributesProvider;
	}

	/**
	 * Loads {@link PPOrderLinesViewData}s.
	 *
	 * @param viewId viewId to be set to newly created {@link PPOrderLineRow}s.
	 */
	public PPOrderLinesViewData retrieveData(final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = Services.get(IPPOrderDAO.class).getById(ppOrderId, I_PP_Order.class);

		final int mainProductBOMLineId = 0;
		final ListMultimap<Integer, I_PP_Order_Qty> ppOrderQtysByBOMLineId = ppOrderQtyDAO.streamOrderQtys(ppOrderId)
				.collect(GuavaCollectors.toImmutableListMultimap(ppOrderQty -> CoalesceUtil.firstGreaterThanZero(ppOrderQty.getPP_Order_BOMLine_ID(), mainProductBOMLineId)));

		final ImmutableList.Builder<PPOrderLineRow> records = ImmutableList.builder();

		// Main product
		final PPOrderLineRow rowForMainProduct = createRowForMainProduct(ppOrder, ppOrderQtysByBOMLineId.get(mainProductBOMLineId));
		records.add(rowForMainProduct);

		// BOM lines
		final List<PPOrderLineRow> bomLineRows = createRowsForBomLines(ppOrder, ppOrderQtysByBOMLineId);
		records.addAll(bomLineRows);

		// Source HUs
		final WarehouseId warehouseId = WarehouseId.ofRepoId(ppOrder.getM_Warehouse_ID());
		final List<PPOrderLineRow> sourceHuRowsForIssueProducts = createRowsForIssueProductSourceHUs(warehouseId, bomLineRows);
		records.addAll(sourceHuRowsForIssueProducts);

		final PPOrderPlanningStatus planningStatus = PPOrderPlanningStatus.ofCode(ppOrder.getPlanningStatus());
		return new PPOrderLinesViewData(extractDescription(ppOrder), planningStatus, records.build());
	}

	private static boolean isReadOnly(@NonNull final I_PP_Order ppOrder)
	{
		final PPOrderPlanningStatus ppOrder_planningStatus = PPOrderPlanningStatus.ofCode(ppOrder.getPlanningStatus());
		final boolean readonly = PPOrderPlanningStatus.COMPLETE.equals(ppOrder_planningStatus);
		return readonly;
	}

	private List<PPOrderLineRow> createRowsForBomLines(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final ListMultimap<Integer, I_PP_Order_Qty> ppOrderQtysByBOMLineId)
	{
		final Comparator<PPOrderLineRow> ppOrderBomLineRowSorter = //
				Comparator.<PPOrderLineRow> comparingInt(row -> row.isReceipt() ? 0 : 1) // receipt lines first
						.thenComparing(row -> row.getOrderBOMLineId());  // BOM lines order

		final Function<? super I_PP_Order_BOMLine, ? extends PPOrderLineRow> ppOrderBomLineRowCreator = //
				ppOrderBOMLine -> createRowForBOMLine(
						ppOrderBOMLine,
						isReadOnly(ppOrder),
						ppOrderQtysByBOMLineId.get(ppOrderBOMLine.getPP_Order_BOMLine_ID()));

		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		final ImmutableList<PPOrderLineRow> bomLineRows = ppOrderBOMDAO.retrieveOrderBOMLines(ppOrderId, I_PP_Order_BOMLine.class)
				.stream()
				.map(ppOrderBomLineRowCreator)
				.sorted(ppOrderBomLineRowSorter)
				.collect(ImmutableList.toImmutableList());
		return bomLineRows;
	}

	private List<PPOrderLineRow> createRowsForIssueProductSourceHUs(WarehouseId warehouseId, @NonNull final List<PPOrderLineRow> bomLineRows)
	{
		final ImmutableSet<ProductId> issueProductIds = bomLineRows.stream()
				.filter(PPOrderLineRow::isIssue)
				.map(PPOrderLineRow::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableList.Builder<PPOrderLineRow> result = ImmutableList.builder();

		final MatchingSourceHusQuery sourceHusQuery = MatchingSourceHusQuery.builder()
				.productIds(issueProductIds)
				.warehouseId(warehouseId).build();

		for (final HuId sourceHUId : SourceHUsService.get().retrieveMatchingSourceHUIds(sourceHusQuery))
		{
			final HUEditorRow huEditorRow = huEditorRepo.retrieveForHUId(sourceHUId);
			result.add(createRowForSourceHU(huEditorRow));
		}

		return result.build();
	}

	private static final ITranslatableString extractDescription(final I_PP_Order ppOrder)
	{
		return TranslatableStrings.join(" ",
				extractDocTypeName(ppOrder),
				ppOrder.getDocumentNo());
	}

	private static ITranslatableString extractDocTypeName(final I_PP_Order ppOrder)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(ppOrder.getC_DocType_ID());
		final I_C_DocType docType = docTypeId != null
				? Services.get(IDocTypeDAO.class).getById(docTypeId)
				: null;
		if (docType != null)
		{
			final IModelTranslationMap docTypeTrlMap = InterfaceWrapperHelper.getModelTranslationMap(docType);
			return docTypeTrlMap.getColumnTrl(I_C_DocType.COLUMNNAME_Name, docType.getName());
		}
		else
		{
			return TranslatableStrings.empty();
		}
	}

	private PPOrderLineRow createRowForMainProduct(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final List<I_PP_Order_Qty> ppOrderQtysforMainProduct)
	{
		final boolean readOnly = isReadOnly(ppOrder);

		final String packingInfoOrNull = computePackingInfo(ppOrder);

		final ImmutableList<PPOrderLineRow> includedRows = createIncludedRowsForPPOrderQtys(
				ppOrderQtysforMainProduct,
				readOnly);

		return PPOrderLineRow.builderForPPOrder()
				.ppOrder(ppOrder)
				.packingInfoOrNull(packingInfoOrNull)
				.processed(readOnly)
				.attributesProvider(asiAttributesProvider)
				.includedRows(includedRows)
				.build();
	}

	private String computePackingInfo(@NonNull final I_PP_Order ppOrder)
	{
		final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
		final I_M_HU_LUTU_Configuration lutuConfig = huPPOrderBL
				.createReceiptLUTUConfigurationManager(ppOrder)
				.getCreateLUTUConfiguration();

		return extractPackingInfoString(lutuConfig);
	}

	private PPOrderLineRow createRowForBOMLine(
			final I_PP_Order_BOMLine ppOrderBOMLine,
			final boolean readOnly,
			final List<I_PP_Order_Qty> ppOrderQtys)
	{
		final PPOrderLineType lineType;
		final String packingInfo;
		final BigDecimal qtyPlan;
		final BOMComponentType componentType = BOMComponentType.ofCode(ppOrderBOMLine.getComponentType());
		if (componentType.isByOrCoProduct())
		{
			lineType = PPOrderLineType.BOMLine_ByCoProduct;
			packingInfo = computePackingInfo(ppOrderBOMLine);

			qtyPlan = ppOrderBOMBL.adjustCoProductQty(ppOrderBOMLine.getQtyRequiered());
		}
		else
		{
			lineType = PPOrderLineType.BOMLine_Component;
			packingInfo = null; // we don't know the packing info for what will be issued.

			qtyPlan = ppOrderBOMLine.getQtyRequiered();
		}

		final ImmutableList<PPOrderLineRow> includedRows = createIncludedRowsForPPOrderQtys(
				ppOrderQtys,
				readOnly);

		return PPOrderLineRow.builderForPPOrderBomLine()
				.ppOrderBomLine(ppOrderBOMLine)
				.type(lineType)
				.packingInfoOrNull(packingInfo)
				.qtyPlan(qtyPlan)
				.attributesProvider(asiAttributesProvider)
				.processed(readOnly)
				.includedRows(includedRows)
				.build();
	}

	private String computePackingInfo(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
		final I_M_HU_LUTU_Configuration lutuConfig = huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrderBOMLine).getCreateLUTUConfiguration();

		return extractPackingInfoString(lutuConfig);
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

	private ImmutableList<PPOrderLineRow> createIncludedRowsForPPOrderQtys(
			@NonNull final List<I_PP_Order_Qty> ppOrderQtys,
			final boolean readOnly)
	{
		final ImmutableList<PPOrderLineRow> includedRows = ppOrderQtys.stream()
				.map(ppOrderQty -> createForPPOrderQty(ppOrderQty, readOnly))
				.collect(ImmutableList.toImmutableList());
		return includedRows;
	}

	private PPOrderLineRow createForPPOrderQty(final I_PP_Order_Qty ppOrderQty, final boolean readonly)
	{
		final HUEditorRow huEditorRow = huEditorRepo.retrieveForHUId(HuId.ofRepoId(ppOrderQty.getM_HU_ID()));
		final HUEditorRow parentHUViewRecord = null;
		return createForHUViewRecordRecursively(ppOrderQty, huEditorRow, parentHUViewRecord, readonly);
	}

	private PPOrderLineRow createForHUViewRecordRecursively(
			@NonNull final I_PP_Order_Qty ppOrderQty,
			@NonNull final HUEditorRow huEditorRow,
			@Nullable final HUEditorRow parentHUEditorRow,
			final boolean readonly)
	{
		final Quantity quantity = computeQuantityForHuPPOrderLineRow(ppOrderQty, huEditorRow, parentHUEditorRow);

		final ImmutableList<PPOrderLineRow> includedRows = huEditorRow.getIncludedRows().stream()
				.map(includedHUEditorRow -> createForHUViewRecordRecursively(
						ppOrderQty,
						includedHUEditorRow,
						huEditorRow,
						readonly))
				.collect(ImmutableList.toImmutableList());

		final PPOrderLineRowId rowId = PPOrderLineRowId.ofIssuedOrReceivedHU(parentHUEditorRow != null ? parentHUEditorRow.getId() : null, huEditorRow.getHuId());

		return PPOrderLineRow.builderForIssuedOrReceivedHU()
				.rowId(rowId)
				.type(PPOrderLineType.ofHUEditorRowType(huEditorRow.getType()))
				.ppOrderQty(ppOrderQty)
				.processed(readonly || ppOrderQty.isProcessed())
				.attributesSupplier(huEditorRow.getAttributesSupplier()
						.map(supplier -> supplier.changeRowId(rowId.toDocumentId()))
						.orElse(null))
				.code(huEditorRow.getValue())
				.product(huEditorRow.getProduct())
				.packingInfo(huEditorRow.getPackingInfo())
				.topLevelHU(huEditorRow.isTopLevel())
				.huStatus(huEditorRow.getHUStatusDisplay())
				.quantity(quantity)
				.includedRows(includedRows)
				.build();
	}

	private Quantity computeQuantityForHuPPOrderLineRow(
			@NonNull final I_PP_Order_Qty ppOrderQty,
			@NonNull final HUEditorRow huEditorRow,
			@Nullable final HUEditorRow parentHUEditorRow)
	{
		final Quantity quantity;
		if (huEditorRow.isHUStatusDestroyed())
		{
			// Top level HU which was already destroyed (i.e. it was already issued & processed)
			// => get the Qty/UOM from PP_Order_Qty because on HU level, for sure it will be ZERO.
			if (parentHUEditorRow == null)
			{
				quantity = Quantity.of(ppOrderQty.getQty(), IHUPPOrderQtyBL.extractUOM(ppOrderQty));
			}
			// Included HU which was already destroyed
			// => we don't know the Qty
			else
			{
				quantity = Quantity.zero(huEditorRow.getC_UOM());
			}
		}
		else
		{
			if (huEditorRow.getQtyCU() == null && huEditorRow.getC_UOM() == null)
			{
				final I_C_UOM uom = IHUPPOrderQtyBL.extractUOM(ppOrderQty);
				quantity = Quantity.zero(uom);
			}
			else
			{
				quantity = Quantity.of(huEditorRow.getQtyCU(), huEditorRow.getC_UOM());
			}
		}
		return quantity;
	}

	private PPOrderLineRow createRowForSourceHU(@NonNull final HUEditorRow huEditorRow)
	{
		final PPOrderLineRowId rowId = PPOrderLineRowId.ofSourceHU(huEditorRow.getId(), huEditorRow.getHuId());

		return PPOrderLineRow.builderForSourceHU()
				.rowId(rowId)
				.type(PPOrderLineType.ofHUEditorRowType(huEditorRow.getType()))
				.huId(huEditorRow.getHuId())
				.attributesSupplier(huEditorRow.getAttributesSupplier()
						.map(supplier -> supplier.changeRowId(rowId.toDocumentId()))
						.orElse(null))
				.code(huEditorRow.getValue())
				.product(huEditorRow.getProduct())
				.packingInfo(huEditorRow.getPackingInfo())
				.uom(huEditorRow.getUOM())
				.qty(huEditorRow.getQtyCU())
				.huStatus(huEditorRow.getHUStatusDisplay())
				.topLevelHU(huEditorRow.isTopLevel())
				.build();
	}
}
