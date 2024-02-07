package de.metas.ui.web.pporder;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.PPOrderQtyId;
import de.metas.handlingunits.pporder.api.PPOrderQtyStatus;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.handlingunits.report.HUReportAwareViewRow;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowAttributes;
import de.metas.ui.web.view.IViewRowAttributesProvider;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.ColorValue;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentIssueMethod;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

@ToString
public class PPOrderLineRow implements IViewRow, HUReportAwareViewRow
{
	@Getter
	private final DocumentPath documentPath;

	@Getter
	private final PPOrderLineRowId rowId;

	@Nullable
	private final Supplier<? extends IViewRowAttributes> attributesSupplier;

	@Getter
	private final ImmutableList<PPOrderLineRow> includedRows;

	@Getter
	private final boolean processed;
	@Getter
	@Nullable
	private final PPOrderQtyStatus issueOrReceiveCandidateStatus;
	@Getter
	@Nullable
	private final PPOrderId orderId;
	@Getter
	@Nullable
	private final PPOrderBOMLineId orderBOMLineId;
	@Getter
	@Nullable
	private final PPOrderQtyId ppOrderQtyId;

	@Getter
	private final HuId huId;
	@Nullable private final String huUnitType;
	@Nullable private final BPartnerId huBPartnerId;
	@Getter
	private final boolean sourceHU;
	@Getter
	private final boolean topLevelHU;

	@Getter
	@Nullable
	private final BOMComponentIssueMethod issueMethod;

	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10))
	private final JSONLookupValue product;

	@ViewColumn(captionKey = "Code", widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20))
	private final String code;

	@ViewColumn(captionKey = "Type", widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30))
	@Getter
	private final PPOrderLineType type;

	@ViewColumn(captionKey = "PackingInfo", widgetType = DocumentFieldWidgetType.Text, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40))
	@Getter
	private final String packingInfo;

	@ViewColumn(captionKey = "QtyPlan", widgetType = DocumentFieldWidgetType.Quantity, widgetSize = WidgetSize.Small, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50))
	@Getter
	@Nullable
	private final Quantity qtyPlan;

	@ViewColumn(captionKey = "Qty", widgetType = DocumentFieldWidgetType.Quantity, widgetSize = WidgetSize.Small, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60))
	@Getter
	private final Quantity qty;

	@ViewColumn(captionKey = "C_UOM_ID", widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70))
	private final JSONLookupValue uom;

	@ViewColumn(captionKey = "HUStatus", widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 80))
	private final JSONLookupValue huStatus;

	@ViewColumn(captionKey = "HUClearanceStatus", widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 90))
	private final JSONLookupValue clearanceStatus;

	@ViewColumn(captionKey = "Status", widgetType = DocumentFieldWidgetType.Color, widgetSize = WidgetSize.Small, layouts = @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100))
	private ColorValue lineStatusColor;

	private final ViewRowFieldNameAndJsonValuesHolder<PPOrderLineRow> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(PPOrderLineRow.class);

	public static PPOrderLineRow cast(final IViewRow viewRecord)
	{
		return (PPOrderLineRow)viewRecord;
	}

	@lombok.Builder(builderMethodName = "builderForIssuedOrReceivedHU", builderClassName = "BuilderForIssuedOrReceivedHU")
	private PPOrderLineRow(
			@NonNull final PPOrderLineRowId rowId,
			@NonNull final HUEditorRowType type,
			@NonNull final I_PP_Order_Qty ppOrderQty,
			final boolean parentRowReadonly,
			@Nullable final Supplier<? extends IViewRowAttributes> attributesSupplier,
			@Nullable final String code, // can be null if type=HU_Storage
			@Nullable final BPartnerId huBPartnerId,
			@Nullable final JSONLookupValue product,
			@Nullable final String packingInfo,  // can be null if type=HU_Storage
			@NonNull final Quantity quantity,
			@NonNull final List<PPOrderLineRow> includedRows,
			@NonNull final Boolean topLevelHU,
			@NonNull final JSONLookupValue huStatus,
			@Nullable final JSONLookupValue clearanceStatus)
	{
		this.rowId = rowId;
		this.type = PPOrderLineType.ofHUEditorRowType(type);

		this.orderId = PPOrderId.ofRepoId(ppOrderQty.getPP_Order_ID());
		this.orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(ppOrderQty.getPP_Order_BOMLine_ID());
		this.huId = HuId.ofRepoId(ppOrderQty.getM_HU_ID());
		this.huUnitType = type.toHUUnitTypeOrNull();
		this.huBPartnerId = huBPartnerId;
		this.ppOrderQtyId = PPOrderQtyId.ofRepoId(ppOrderQty.getPP_Order_Qty_ID());

		this.issueOrReceiveCandidateStatus = PPOrderQtyStatus.ofProcessedFlag(ppOrderQty.isProcessed());
		this.processed = parentRowReadonly || PPOrderQtyStatus.isProcessed(issueOrReceiveCandidateStatus);

		// Values
		this.product = product;
		this.uom = JSONLookupValueTool.createUOMLookupValue(quantity.getUOM());
		this.packingInfo = packingInfo;
		this.code = code;
		this.clearanceStatus = clearanceStatus;

		this.sourceHU = false;
		this.topLevelHU = topLevelHU;
		this.huStatus = huStatus;

		this.qtyPlan = null;

		this.attributesSupplier = attributesSupplier;

		this.includedRows = ImmutableList.copyOf(includedRows);

		this.qty = quantity;

		this.documentPath = computeDocumentPath();

		this.issueMethod = ppOrderQty.getPP_Order_BOMLine() == null
				? null
				: BOMComponentIssueMethod.ofNullableCode(ppOrderQty.getPP_Order_BOMLine().getIssueMethod());
	}

	@lombok.Builder(builderMethodName = "builderForPPOrder", builderClassName = "BuilderForPPOrder")
	private PPOrderLineRow(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final Boolean processed,
			@Nullable final String packingInfoOrNull,
			@NonNull final IViewRowAttributesProvider attributesProvider,
			@NonNull final List<PPOrderLineRow> includedRows)
	{
		this.rowId = PPOrderLineRowId.ofPPOrderId(ppOrder.getPP_Order_ID());
		this.type = PPOrderLineType.MainProduct;

		this.orderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		this.orderBOMLineId = null;
		this.huId = null;
		this.huUnitType = null;
		this.huBPartnerId = null;
		this.ppOrderQtyId = null;

		this.processed = processed;
		this.issueOrReceiveCandidateStatus = null;

		final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		this.product = JSONLookupValueTool.createProductLookupValue(Services.get(IProductDAO.class).getById(productId));
		final int uomId = ppOrder.getC_UOM_ID();
		this.uom = JSONLookupValueTool.createUOMLookupValue(Services.get(IUOMDAO.class).getById(uomId));
		this.packingInfo = packingInfoOrNull;
		this.code = null;

		this.sourceHU = false;
		this.topLevelHU = false;
		this.huStatus = null;

		this.qtyPlan = Services.get(IPPOrderBOMBL.class)
				.getQuantities(ppOrder)
				.getQtyRequiredToProduce();

		this.attributesSupplier = createASIAttributesSupplier(attributesProvider,
				rowId.toDocumentId(),
				ppOrder.getM_AttributeSetInstance_ID());

		this.includedRows = ImmutableList.copyOf(includedRows);

		final UOMConversionContext uomConversionCtx = UOMConversionContext.of(productId);

		this.qty = includedRows.stream()
				.map(PPOrderLineRow::getQty)
				.reduce(Quantity.zero(getUom()), (existingQty, newRowQty) -> Quantitys.add(uomConversionCtx, existingQty, newRowQty));

		this.documentPath = computeDocumentPath();

		this.issueMethod = null;

		this.clearanceStatus = includedRows.stream()
				.map(PPOrderLineRow::getClearanceStatus)
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
	}

	@lombok.Builder(builderMethodName = "builderForPPOrderBomLine", builderClassName = "BuilderForPPOrderBomLine")
	private PPOrderLineRow(
			@NonNull final I_PP_Order_BOMLine ppOrderBomLine,
			@NonNull final PPOrderLineType type,
			@Nullable final String packingInfoOrNull,
			@NonNull final Boolean processed,
			@NonNull final Quantity qtyPlan,
			@NonNull final Quantity qtyProcessedIssuedOrReceived,
			@NonNull final IViewRowAttributesProvider attributesProvider,
			@NonNull final List<PPOrderLineRow> includedRows)
	{
		this.rowId = PPOrderLineRowId.ofPPOrderBomLineId(ppOrderBomLine.getPP_Order_BOMLine_ID());

		this.type = type;

		this.orderId = PPOrderId.ofRepoId(ppOrderBomLine.getPP_Order_ID());
		this.orderBOMLineId = PPOrderBOMLineId.ofRepoId(ppOrderBomLine.getPP_Order_BOMLine_ID());
		this.huId = null;
		this.huUnitType = null;
		this.huBPartnerId = null;
		this.ppOrderQtyId = null;

		this.processed = processed;
		this.issueOrReceiveCandidateStatus = null;

		final ProductId productId = ProductId.ofRepoId(ppOrderBomLine.getM_Product_ID());
		this.product = JSONLookupValueTool.createProductLookupValue(Services.get(IProductDAO.class).getById(productId));
		this.uom = JSONLookupValueTool.createUOMLookupValue(qtyPlan.getUOM());

		this.packingInfo = packingInfoOrNull;
		this.code = null;

		this.sourceHU = false;
		this.topLevelHU = false;
		this.huStatus = null;

		this.qtyPlan = qtyPlan;

		this.attributesSupplier = createASIAttributesSupplier(
				attributesProvider,
				rowId.toDocumentId(),
				ppOrderBomLine.getM_AttributeSetInstance_ID());

		this.includedRows = ImmutableList.copyOf(includedRows);

		final UOMConversionContext uomConversionCtx = UOMConversionContext.of(ProductId.ofRepoIdOrNull(ppOrderBomLine.getM_Product_ID()));

		final Quantity qtyDraftIssuedOrReceived = includedRows.stream()
				.filter(row -> PPOrderQtyStatus.isDraft(row.getIssueOrReceiveCandidateStatus()))
				.map(PPOrderLineRow::getQty)
				.reduce(Quantity.zero(getUom()), (partialQty, lineQty) -> Quantitys.add(uomConversionCtx, partialQty, lineQty));

		this.qty = qtyProcessedIssuedOrReceived.add(qtyDraftIssuedOrReceived);

		this.documentPath = computeDocumentPath();

		this.issueMethod = BOMComponentIssueMethod.ofNullableCode(ppOrderBomLine.getIssueMethod());

		this.lineStatusColor = computeLineStatusColor(this.qtyPlan, this.qty);
		this.clearanceStatus = null;
	}

	@lombok.Builder(builderMethodName = "builderForSourceHU", builderClassName = "BuilderForSourceHU")
	private PPOrderLineRow(
			@NonNull final PPOrderLineRowId rowId,
			@NonNull final PPOrderLineType type,
			@NonNull final HuId huId,
			@Nullable final Supplier<? extends IViewRowAttributes> attributesSupplier,
			@NonNull final String code,
			@NonNull final JSONLookupValue product,
			@NonNull final String packingInfo,
			@NonNull final JSONLookupValue uom,
			@NonNull final Quantity qty,
			@NonNull final Boolean topLevelHU,
			@NonNull final JSONLookupValue huStatus,
			@Nullable final JSONLookupValue clearanceStatus)
	{
		this.rowId = rowId;
		this.type = type;

		this.orderId = null;
		this.orderBOMLineId = null;
		this.huId = huId;
		this.huUnitType = null; // N/A
		this.huBPartnerId = null; // N/A
		this.ppOrderQtyId = null;

		this.processed = true;
		this.issueOrReceiveCandidateStatus = null;

		// Values
		this.product = product;
		this.uom = uom;
		this.packingInfo = packingInfo;
		this.code = code;

		this.sourceHU = true;
		this.topLevelHU = topLevelHU;
		this.huStatus = huStatus;

		this.qtyPlan = null;

		this.attributesSupplier = attributesSupplier;

		this.includedRows = ImmutableList.of();

		this.qty = qty;

		this.documentPath = computeDocumentPath();

		this.issueMethod = null;
		this.clearanceStatus = clearanceStatus;
	}

	@VisibleForTesting
	static ColorValue computeLineStatusColor(@NonNull final Quantity qtyPlan, @NonNull final Quantity qtyIssued)
	{
		final boolean issued;
		if (qtyPlan.signum() >= 0)
		{
			issued = qtyPlan.compareTo(qtyIssued) <= 0;
		}
		else
		{
			issued = qtyPlan.compareTo(qtyIssued) >= 0;
		}

		return issued ? ColorValue.GREEN : ColorValue.RED;
	}

	@Nullable
	private DocumentPath computeDocumentPath()
	{
		if (type == PPOrderLineType.MainProduct)
		{
			return DocumentPath.rootDocumentPath(PPOrderConstants.AD_WINDOW_ID_PP_Order, DocumentId.of(orderId));
		}
		else if (type.isBOMLine())
		{
			return DocumentPath.includedDocumentPath(PPOrderConstants.AD_WINDOW_ID_PP_Order, DocumentId.of(orderId), PPOrderConstants.TABID_ID_PP_Order_BOMLine, DocumentId.of(orderBOMLineId));
		}
		else if (type.isHUOrHUStorage())
		{
			// Better return null because we don't want to have here all processes which are related to HUs.
			// More, in case the HU is destroyed, that HU will not be found in the standard HU Editor View so no process will be executed.
			// see https://github.com/metasfresh/metasfresh-webui-api/issues/1097#issuecomment-436944470, problem 2.
			// return DocumentPath.rootDocumentPath(WEBUI_HU_Constants.WEBUI_HU_Window_ID, DocumentId.of(huId));
			return null;
		}
		else
		{
			throw new IllegalStateException("Unknown type: " + type);
		}
	}

	@Nullable
	private static Supplier<IViewRowAttributes> createASIAttributesSupplier(
			@NonNull final IViewRowAttributesProvider asiAttributesProvider,
			@NonNull final DocumentId documentId,
			final int asiId)
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

	@Override
	public DocumentId getId()
	{
		return rowId.toDocumentId();
	}

	@Override
	public ImmutableSet<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}

	@Nullable
	public ProductId getProductId()
	{
		return product != null ? ProductId.ofRepoIdOrNull(product.getKeyAsInt()) : null;
	}

	@Nullable
	private UomId getUomId()
	{
		return uom == null ? null : UomId.ofRepoIdOrNull(uom.getKeyAsInt());
	}

	private JSONLookupValue getClearanceStatus()
	{
		return clearanceStatus;
	}

	@Nullable
	public I_C_UOM getUom()
	{
		final UomId uomId = getUomId();
		if (uomId == null)
		{
			return null;
		}

		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		return uomDAO.getById(uomId);
	}

	public boolean isReceipt()
	{
		return getType().canReceive();
	}

	public boolean isIssue()
	{
		return getType().canIssue();
	}

	public boolean isHUStatusActive()
	{
		return huStatus != null && X_M_HU.HUSTATUS_Active.equals(huStatus.getKey());
	}

	@Override
	public boolean hasAttributes()
	{
		return attributesSupplier != null;
	}

	@Override
	public IViewRowAttributes getAttributes() throws EntityNotFoundException
	{
		if (attributesSupplier == null)
		{
			throw new EntityNotFoundException("This PPOrderLineRow does not support attributes; this=" + this);
		}

		final IViewRowAttributes attributes = attributesSupplier.get();
		if (attributes == null)
		{
			throw new EntityNotFoundException("This PPOrderLineRow does not support attributes; this=" + this);
		}
		return attributes;
	}

	@Override
	public String getHUUnitTypeOrNull() {return huUnitType;}

	@Override
	public BPartnerId getBpartnerId() {return huBPartnerId;}

	@Override
	public boolean isTopLevel() {return topLevelHU;}

	@Override
	public Stream<HUReportAwareViewRow> streamIncludedHUReportAwareRows() {return getIncludedRows().stream().map(PPOrderLineRow::toHUReportAwareViewRow);}

	private HUReportAwareViewRow toHUReportAwareViewRow() {return this;}
}
