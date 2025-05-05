package de.metas.ui.web.pporder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import de.metas.ad_reference.ADReferenceService;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.product.IProductBL;
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
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewHeaderPropertiesGroup;
import de.metas.ui.web.view.ViewHeaderProperty;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeSourceDocument;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.groups.WarehouseGroupAssignmentType;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderPlanningStatus;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

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

class PPOrderLinesViewDataLoader
{
	public static PPOrderLinesViewDataLoaderBuilder builder(final WindowId viewWindowId)
	{
		return new PPOrderLinesViewDataLoaderBuilder().viewWindowId(viewWindowId);
	}

	//
	// Services
	private static final Logger logger = LogManager.getLogger(PPOrderLinesViewDataLoader.class);
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IHUPPOrderQtyDAO ppOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ADReferenceService adReferenceService;
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	//
	private final transient HUEditorViewRepository huEditorRepo;
	private final ASIViewRowAttributesProvider asiAttributesProvider;

	@Builder
	public PPOrderLinesViewDataLoader(
			final WindowId viewWindowId,
			final ASIViewRowAttributesProvider asiAttributesProvider,
			@NonNull final SqlViewBinding huSQLViewBinding,
			@NonNull final HUReservationService huReservationService,
			@NonNull final ADReferenceService adReferenceService,
			final boolean serialNoFromSequence)
	{
		huEditorRepo = SqlHUEditorViewRepository.builder()
				.windowId(viewWindowId)
				.attributesProvider(HUEditorRowAttributesProvider.builder()
											.readonly(false)
											.serialNoFromSequence(serialNoFromSequence)
											.attributeSourceDocument(AttributeSourceDocument.ManufacturingOrder)
											.build())
				.sqlViewBinding(huSQLViewBinding)
				.huReservationService(huReservationService)
				.adReferenceService(adReferenceService)
				.build();

		this.adReferenceService = adReferenceService;
		this.asiAttributesProvider = asiAttributesProvider;
	}

	public PPOrderLinesViewData retrieveData(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId, I_PP_Order.class);

		final int finishedGoodProductBOMLineId = 0;
		final ListMultimap<Integer, I_PP_Order_Qty> ppOrderQtysByBOMLineId = ppOrderQtyDAO.streamOrderQtys(ppOrderId)
				.collect(GuavaCollectors.toImmutableListMultimap(ppOrderQty -> CoalesceUtil.firstGreaterThanZero(ppOrderQty.getPP_Order_BOMLine_ID(), finishedGoodProductBOMLineId)));

		final PPOrderLineRow finishedGoodRow = createRowForFinishedGoodProduct(ppOrder, ppOrderQtysByBOMLineId.get(finishedGoodProductBOMLineId));
		final List<PPOrderLineRow> bomLineRows = createRowsForBomLines(ppOrder, ppOrderQtysByBOMLineId);
		final ImmutableSet<WarehouseId> issueFromWarehouseIds = getIssueFromWarehouseIds(ppOrder);
		final List<PPOrderLineRow> sourceHuRowsForIssueProducts = createRowsForIssueProductSourceHUs(issueFromWarehouseIds, bomLineRows);

		return PPOrderLinesViewData.builder()
				.description(extractDescription(ppOrder))
				.planningStatus(PPOrderPlanningStatus.ofCode(ppOrder.getPlanningStatus()))
				.finishedGoodRow(finishedGoodRow)
				.bomLineRows(bomLineRows)
				.sourceHURows(sourceHuRowsForIssueProducts)
				.headerProperties(extractHeaderProperties(ppOrder))
				.build();
	}

	private ImmutableSet<WarehouseId> getIssueFromWarehouseIds(final I_PP_Order ppOrder)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(ppOrder.getM_Warehouse_ID());
		return warehouseDAO.getWarehouseIdsOfSameGroup(warehouseId, WarehouseGroupAssignmentType.MANUFACTURING);
	}

	public ViewHeaderProperties extractHeaderProperties(final I_PP_Order ppOrder)
	{
		return ViewHeaderProperties.builder()
				.group(ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.caption(msgBL.translatable(I_PP_Order.COLUMNNAME_DocumentNo))
								.value(ppOrder.getDocumentNo())
								.build())
						.entry(ViewHeaderProperty.builder()
								.caption(msgBL.translatable(I_PP_Order.COLUMNNAME_PlanningStatus))
								.value(adReferenceService.retrieveListNameTranslatableString(PPOrderPlanningStatus.AD_REFERENCE_ID, ppOrder.getPlanningStatus()))
								.build())
						.build())
				.build();
	}

	private static boolean isReadOnly(@NonNull final I_PP_Order ppOrder)
	{
		final PPOrderPlanningStatus ppOrderPlanningStatus = PPOrderPlanningStatus.ofCode(ppOrder.getPlanningStatus());
		return PPOrderPlanningStatus.COMPLETE.equals(ppOrderPlanningStatus);
	}

	private List<PPOrderLineRow> createRowsForBomLines(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final ListMultimap<Integer, I_PP_Order_Qty> ppOrderQtysByBOMLineId)
	{
		final Comparator<PPOrderLineRow> ppOrderBomLineRowSorter = //
				Comparator.<PPOrderLineRow>comparingInt(row -> row.isReceipt() ? 0 : 1) // receipt lines first
						.thenComparing(PPOrderLineRow::getOrderBOMLineId);  // BOM lines order

		final Function<? super I_PP_Order_BOMLine, ? extends PPOrderLineRow> ppOrderBomLineRowCreator = //
				ppOrderBOMLine -> createRowForBOMLine(
						ppOrderBOMLine,
						isReadOnly(ppOrder),
						ppOrderQtysByBOMLineId.get(ppOrderBOMLine.getPP_Order_BOMLine_ID()));

		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		return ppOrderBOMDAO.retrieveOrderBOMLines(ppOrderId, I_PP_Order_BOMLine.class)
				.stream()
				.map(ppOrderBomLineRowCreator)
				.sorted(ppOrderBomLineRowSorter)
				.collect(ImmutableList.toImmutableList());
	}

	private List<PPOrderLineRow> createRowsForIssueProductSourceHUs(
			final ImmutableSet<WarehouseId> issueFromWarehouseIds,
			@NonNull final List<PPOrderLineRow> bomLineRows)
	{
		final ImmutableSet<ProductId> issueProductIds = bomLineRows.stream()
				.filter(PPOrderLineRow::isIssue)
				.map(PPOrderLineRow::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableList.Builder<PPOrderLineRow> result = ImmutableList.builder();

		final MatchingSourceHusQuery sourceHusQuery = MatchingSourceHusQuery.builder()
				.productIds(issueProductIds)
				.warehouseIds(issueFromWarehouseIds)
				.build();

		for (final HuId sourceHUId : SourceHUsService.get().retrieveMatchingSourceHUIds(sourceHusQuery))
		{
			final HUEditorRow huEditorRow = huEditorRepo.retrieveForHUId(sourceHUId);
			if (X_M_HU.HUSTATUS_Active.equals(huEditorRow.getHUStatus()))
			{
				// If the HU does not have single product then skip it
				// NOTE: this is a hotfix because before we would get a NPE
				// A proper issue will be needed to solve the problem correctly.
				if (huEditorRow.getProduct() == null)
				{
					logger.warn("Only single product HUs are supported as source HUs: {}", huEditorRow);
					continue;
				}

				result.add(createRowForSourceHU(huEditorRow));
			}
		}

		return result.build();
	}

	private ITranslatableString extractDescription(final I_PP_Order ppOrder)
	{
		return TranslatableStrings.join(" ",
				extractDocTypeName(ppOrder),
				ppOrder.getDocumentNo());
	}

	private ITranslatableString extractDocTypeName(final I_PP_Order ppOrder)
	{
		return DocTypeId.optionalOfRepoId(ppOrder.getC_DocType_ID())
				.map(docTypeBL::getNameById)
				.orElseGet(TranslatableStrings::empty);
	}

	private PPOrderLineRow createRowForFinishedGoodProduct(
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

	@Nullable
	private String computePackingInfo(@NonNull final I_PP_Order ppOrder)
	{
		final I_M_HU_LUTU_Configuration lutuConfig = huPPOrderBL
				.createReceiptLUTUConfigurationManager(ppOrder)
				.getCreateLUTUConfiguration();

		return extractPackingInfoString(lutuConfig);
	}

	private PPOrderLineRow createRowForBOMLine(
			final I_PP_Order_BOMLine bomLine,
			final boolean readOnly,
			final List<I_PP_Order_Qty> ppOrderQtys)
	{
		final PPOrderLineType lineType;
		final String packingInfo;
		final Quantity qtyPlan;
		final Quantity qtyProcessedIssuedOrReceived;
		final BOMComponentType componentType = BOMComponentType.ofCode(bomLine.getComponentType());
		final OrderBOMLineQuantities bomLineQtys = ppOrderBOMBL.getQuantities(bomLine);
		if (componentType.isByOrCoProduct())
		{
			lineType = PPOrderLineType.BOMLine_ByCoProduct;
			packingInfo = computePackingInfo(bomLine);

			qtyPlan = bomLineQtys.getQtyRequired_NegateBecauseIsCOProduct();
			qtyProcessedIssuedOrReceived = bomLineQtys.getQtyIssuedOrReceived_NegateBecauseIsCOProduct();
		}
		else
		{
			final ProductId productId = ProductId.ofRepoId(bomLine.getM_Product_ID());
			lineType = productBL.isStocked(productId)
					? PPOrderLineType.BOMLine_Component
					: PPOrderLineType.BOMLine_Component_Service;

			packingInfo = null; // we don't know the packing info for what will be issued.

			qtyPlan = bomLineQtys.getQtyRequired();
			qtyProcessedIssuedOrReceived = bomLineQtys.getQtyIssuedOrReceived();
		}

		final ImmutableList<PPOrderLineRow> includedRows = createIncludedRowsForPPOrderQtys(ppOrderQtys, readOnly);

		return PPOrderLineRow.builderForPPOrderBomLine()
				.ppOrderBomLine(bomLine)
				.type(lineType)
				.packingInfoOrNull(packingInfo)
				.qtyPlan(qtyPlan)
				.qtyProcessedIssuedOrReceived(qtyProcessedIssuedOrReceived)
				.attributesProvider(asiAttributesProvider)
				.processed(readOnly)
				.includedRows(includedRows)
				.build();
	}

	@Nullable
	private String computePackingInfo(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final I_M_HU_LUTU_Configuration lutuConfig = huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrderBOMLine).getCreateLUTUConfiguration();
		return extractPackingInfoString(lutuConfig);
	}

	@Nullable
	private static String extractPackingInfoString(final I_M_HU_LUTU_Configuration lutuConfig)
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
		return ppOrderQtys.stream()
				.map(ppOrderQty -> createForPPOrderQty(ppOrderQty, readOnly))
				.sorted(Comparator.comparing(PPOrderLineRow::getCode, Comparator.nullsLast(Comparator.naturalOrder())))
				.collect(ImmutableList.toImmutableList());
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
				.sorted(Comparator.comparing(PPOrderLineRow::getCode, Comparator.nullsLast(Comparator.naturalOrder())))
				.collect(ImmutableList.toImmutableList());

		final PPOrderLineRowId rowId = PPOrderLineRowId.ofIssuedOrReceivedHU(parentHUEditorRow != null ? parentHUEditorRow.getId() : null, huEditorRow.getHuId());

		return PPOrderLineRow.builderForIssuedOrReceivedHU()
				.rowId(rowId)
				.type(huEditorRow.getType())
				.ppOrderQty(ppOrderQty)
				.parentRowReadonly(readonly)
				.attributesSupplier(huEditorRow.getAttributesSupplier()
						.map(supplier -> supplier.changeRowId(rowId.toDocumentId()))
						.orElse(null))
				.code(huEditorRow.getValue())
				.huBPartnerId(huEditorRow.getBpartnerId())
				.product(huEditorRow.getProduct())
				.packingInfo(huEditorRow.getPackingInfo())
				.topLevelHU(huEditorRow.isTopLevel())
				.huStatus(huEditorRow.getHUStatusDisplay())
				.quantity(quantity)
				.includedRows(includedRows)
				.clearanceStatus(huEditorRow.getClearanceStatus())
				.build();
	}

	private Quantity computeQuantityForHuPPOrderLineRow(
			@NonNull final I_PP_Order_Qty ppOrderQty,
			@NonNull final HUEditorRow huEditorRow,
			@Nullable final HUEditorRow parentHUEditorRow)
	{
		if (huEditorRow.isHUStatusDestroyed())
		{
			// Top level HU which was already destroyed (i.e. it was already issued & processed)
			// => get the Qty/UOM from PP_Order_Qty because on HU level, for sure it will be ZERO.
			if (parentHUEditorRow == null)
			{
				return Quantity.of(ppOrderQty.getQty(), IHUPPOrderQtyBL.extractUOM(ppOrderQty));
			}
			// Included HU which was already destroyed
			// => we don't know the Qty
			else
			{
				return Quantity.zero(huEditorRow.getC_UOM());
			}
		}
		else
		{
			final Quantity qtyCU = huEditorRow.getQtyCUAsQuantity();
			if (qtyCU == null)
			{
				final I_C_UOM uom = IHUPPOrderQtyBL.extractUOM(ppOrderQty);
				return Quantity.zero(uom);
			}
			else
			{
				return qtyCU;
			}
		}
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
				.qty(huEditorRow.getQtyCUAsQuantity())
				.huStatus(huEditorRow.getHUStatusDisplay())
				.topLevelHU(huEditorRow.isTopLevel())
				.build();
	}
}
