package de.metas.ui.web.pickingV2.productsToPick.rows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateApprovalStatus;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidatePickStatus;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.ui.web.pickingV2.productsToPick.rows.factory.ProductsToPickRowsDataFactory;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.TranslationSource;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.util.lang.CoalesceUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ToString(exclude = "values")
public class ProductsToPickRow implements IViewRow
{
	public static ProductsToPickRow cast(final IViewRow row)
	{
		return (ProductsToPickRow)row;
	}

	public static final String FIELD_ProductValue = "productValue";
	@ViewColumn(fieldName = FIELD_ProductValue, widgetType = DocumentFieldWidgetType.Text, captionKey = "ProductValue",
			// captionKeyIsSysConfig=true, // TODO
			widgetSize = WidgetSize.Small)
	private final String productValue;

	public static final String FIELD_ProductName = "productName";
	@ViewColumn(fieldName = FIELD_ProductName, widgetType = DocumentFieldWidgetType.Text, captionKey = "ProductName", widgetSize = WidgetSize.Medium)
	@Getter
	private final ITranslatableString productName;

	public static final String FIELD_ProductPackageSize = "productPackageSize";
	@ViewColumn(fieldName = FIELD_ProductPackageSize, widgetType = DocumentFieldWidgetType.Text, captionKey = "PackageSize", widgetSize = WidgetSize.Small)
	private final String productPackageSize;

	public static final String FIELD_ProductPackageSizeUOM = "productPackageSizeUOM";
	@ViewColumn(fieldName = FIELD_ProductPackageSizeUOM, widgetType = DocumentFieldWidgetType.Text, captionKey = "Package_UOM_ID", widgetSize = WidgetSize.Small)
	private final String productPackageSizeUOM;

	public static final String FIELD_Locator = "locator";
	@ViewColumn(fieldName = FIELD_Locator, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_Locator_ID", widgetSize = WidgetSize.Small)
	private final LookupValue locator;

	public static final String FIELD_LotNumber = "lotNumber";
	@ViewColumn(fieldName = FIELD_LotNumber, widgetType = DocumentFieldWidgetType.Text, //
			captionKey = ProductsToPickRowsDataFactory.ATTR_LotNumber_String, captionTranslationSource = TranslationSource.ATTRIBUTE_NAME, //
			widgetSize = WidgetSize.Small)
	private final String lotNumber;

	public static final String FIELD_ExpiringDate = "expiringDate";
	@ViewColumn(fieldName = FIELD_ExpiringDate, widgetType = DocumentFieldWidgetType.LocalDate, //
			captionKey = ProductsToPickRowsDataFactory.ATTR_BestBeforeDate_String, captionTranslationSource = TranslationSource.ATTRIBUTE_NAME, //
			widgetSize = WidgetSize.Small)
	@Getter
	private final LocalDate expiringDate;

	public static final String FIELD_RepackNumber = "repackNumber";
	@ViewColumn(fieldName = FIELD_RepackNumber, widgetType = DocumentFieldWidgetType.Text, //
			captionKey = ProductsToPickRowsDataFactory.ATTR_RepackNumber_String, captionTranslationSource = TranslationSource.ATTRIBUTE_NAME, //
			widgetSize = WidgetSize.Small)
	private final String repackNumber;

	public static final String FIELD_Qty = "qty";
	@ViewColumn(fieldName = FIELD_Qty, widgetType = DocumentFieldWidgetType.Quantity, captionKey = "Qty", widgetSize = WidgetSize.Small)
	private final Quantity qty;

	public static final String FIELD_QtyOverride = "qtyOverride";
	@ViewColumn(fieldName = FIELD_QtyOverride, widgetType = DocumentFieldWidgetType.Quantity, captionKey = "Qty_Override", widgetSize = WidgetSize.Small, editor = ViewEditorRenderMode.ALWAYS)
	private final Quantity qtyOverride;

	public static final String FIELD_QtyReview = "qtyReview";
	@ViewColumn(fieldName = FIELD_QtyReview, widgetType = DocumentFieldWidgetType.Quantity, captionKey = "Qty", widgetSize = WidgetSize.Small, editor = ViewEditorRenderMode.ALWAYS)
	@Getter
	private final BigDecimal qtyReview;

	public static final String FIELD_PickStatus = "pickStatus";
	@ViewColumn(fieldName = FIELD_PickStatus, captionKey = "PickStatus", widgetType = DocumentFieldWidgetType.List, listReferenceId = PickingCandidatePickStatus.AD_REFERENCE_ID, widgetSize = WidgetSize.Small)
	private final PickingCandidatePickStatus pickStatus;

	public static final String FIELD_ApprovalStatus = "approvalStatus";
	@ViewColumn(fieldName = FIELD_ApprovalStatus, captionKey = "ApprovalStatus", widgetType = DocumentFieldWidgetType.List, listReferenceId = PickingCandidateApprovalStatus.AD_REFERENCE_ID, widgetSize = WidgetSize.Small)
	private final PickingCandidateApprovalStatus approvalStatus;

	//
	private final ProductsToPickRowId rowId;
	private final ProductsToPickRowType rowType;
	private final ProductInfo productInfo;
	@Getter
	private final boolean huReservedForThisRow;
	private final boolean processed;
	@Getter
	@Nullable
	private final PickingCandidateId pickingCandidateId;
	@Getter
	@Nullable
	private final ShipperId shipperId;

	private final ImmutableList<ProductsToPickRow> includedRows;

	//
	private final ViewRowFieldNameAndJsonValuesHolder<ProductsToPickRow> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(ProductsToPickRow.class);
	private final ImmutableMap<String, ViewEditorRenderMode> viewEditorRenderModeByFieldName;

	@Builder(toBuilder = true)
	private ProductsToPickRow(
			@NonNull final ProductsToPickRowId rowId,
			@NonNull final ProductsToPickRowType rowType,
			//
			@NonNull final ProductInfo productInfo,
			final boolean huReservedForThisRow,
			//
			final LookupValue locator,
			//
			final String lotNumber,
			final LocalDate expiringDate,
			final String repackNumber,
			//
			@NonNull final Quantity qty,
			@Nullable final Quantity qtyOverride,
			@Nullable final BigDecimal qtyReview,
			//
			final PickingCandidatePickStatus pickStatus,
			final PickingCandidateApprovalStatus approvalStatus,
			final boolean processed,
			//
			final PickingCandidateId pickingCandidateId,
			final ShipperId shipperId,
			//
			@Nullable final List<ProductsToPickRow> includedRows)
	{
		this.rowId = rowId;
		this.rowType = rowType;

		this.productInfo = productInfo;
		productValue = productInfo.getCode();
		productName = productInfo.getName();
		productPackageSize = productInfo.getPackageSize();
		productPackageSizeUOM = productInfo.getPackageSizeUOM();

		this.huReservedForThisRow = huReservedForThisRow;

		this.locator = locator;
		this.lotNumber = lotNumber;
		this.expiringDate = expiringDate;
		this.repackNumber = repackNumber;

		this.qty = qty;
		this.qtyOverride = qtyOverride;
		this.qtyReview = qtyReview;

		if (pickStatus != null)
		{
			this.pickStatus = pickStatus;
		}
		else
		{
			this.pickStatus = rowType.isPickable() ? PickingCandidatePickStatus.TO_BE_PICKED : null;
		}

		if (approvalStatus != null)
		{
			this.approvalStatus = approvalStatus;
		}
		else
		{
			this.approvalStatus = rowType.isPickable() ? PickingCandidateApprovalStatus.TO_BE_APPROVED : null;
		}
		this.processed = processed;

		this.pickingCandidateId = pickingCandidateId;
		this.shipperId = shipperId;

		if (includedRows != null && !includedRows.isEmpty())
		{
			this.includedRows = includedRows.stream()
					.map(includedRow -> includedRow.withFinishedGoodsQtyOverride(qty, qtyOverride))
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			this.includedRows = ImmutableList.of();
		}

		this.viewEditorRenderModeByFieldName = buildViewEditorRenderModeByFieldName(rowType);
	}

	@Override
	public DocumentId getId()
	{
		return rowId.toDocumentId();
	}

	@Override
	public ProductsToPickRowType getType()
	{
		return rowType;
	}

	@Override
	public boolean isProcessed()
	{
		return processed
				|| !rowType.isPickable();
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public ImmutableMap<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		return viewEditorRenderModeByFieldName;
	}

	private static ImmutableMap<String, ViewEditorRenderMode> buildViewEditorRenderModeByFieldName(
			@NonNull final ProductsToPickRowType rowType)
	{
		final ImmutableMap.Builder<String, ViewEditorRenderMode> result = ImmutableMap.builder();
		if (rowType.isPickable())
		{
			result.put(FIELD_QtyOverride, ViewEditorRenderMode.ALWAYS);
		}
		else
		{
			result.put(FIELD_QtyOverride, ViewEditorRenderMode.NEVER);
		}

		return result.build();
	}

	public ShipmentScheduleId getShipmentScheduleId()
	{
		return rowId.getShipmentScheduleId();
	}

	public ProductId getProductId()
	{
		return rowId.getProductId();
	}

	public HuId getPickFromHUId()
	{
		return rowId.getPickFromHUId();
	}

	public PPOrderId getPickFromPickingOrderId()
	{
		return rowId.getPickFromPickingOrderId();
	}

	public PPOrderBOMLineId getIssueToOrderBOMLineId()
	{
		final PPOrderBOMLineId issueToOrderBOMLineId = rowId.getIssueToOrderBOMLineId();
		if (issueToOrderBOMLineId == null)
		{
			throw new AdempiereException("Product " + productName.getDefaultValue() + " is not issueable");
		}
		return issueToOrderBOMLineId;
	}

	public Quantity getQtyEffective()
	{
		return CoalesceUtil.coalesce(qtyOverride, qty);
	}

	public ProductsToPickRow withUpdatesFromPickingCandidateIfNotNull(@Nullable final PickingCandidate pickingCandidate)
	{
		return pickingCandidate != null
				? withUpdatesFromPickingCandidate(pickingCandidate)
				: this;
	}

	public ProductsToPickRow withUpdatesFromPickingCandidate(@NonNull final PickingCandidate pickingCandidate)
	{
		return toBuilder()
				.qtyReview(pickingCandidate.getQtyReview())
				.pickStatus(pickingCandidate.getPickStatus())
				.approvalStatus(pickingCandidate.getApprovalStatus())
				.processed(!pickingCandidate.isDraft())
				.pickingCandidateId(pickingCandidate.getId())
				.build();
	}

	public ProductsToPickRow withQty(@NonNull final Quantity qty)
	{
		return Objects.equals(this.qty, qty)
				? this
				: toBuilder().qty(qty).qtyOverride(null).build();
	}

	public ProductsToPickRow withQtyOverride(@Nullable final BigDecimal qtyOverrideBD)
	{
		final Quantity qtyOverride = qtyOverrideBD != null
				? Quantity.of(qtyOverrideBD, qty.getUOM())
				: null;

		return withQtyOverride(qtyOverride);
	}

	private ProductsToPickRow withQtyOverride(@Nullable final Quantity qtyOverride)
	{
		return Objects.equals(this.qtyOverride, qtyOverride)
				? this
				: toBuilder().qtyOverride(qtyOverride).build();
	}

	private ProductsToPickRow withFinishedGoodsQtyOverride(
			@NonNull final Quantity finishedGoodsQty,
			@Nullable final Quantity finishedGoodsQtyOverride)
	{
		if (finishedGoodsQtyOverride != null)
		{
			Quantity.getCommonUomIdOfAll(finishedGoodsQty, finishedGoodsQtyOverride); // just to make sure

			// qty ............... finishedGoodsQty
			// qtyOverride ....... finishedGoodsQtyOverride
			// => qtyOverride = qty * (finishedGoodsQtyOverride / finishedGoodsQty)

			final BigDecimal multiplier = finishedGoodsQtyOverride.toBigDecimal()
					.divide(finishedGoodsQty.toBigDecimal(), 12, RoundingMode.HALF_UP);

			final Quantity qtyOverride = qty.multiply(multiplier).roundToUOMPrecision();
			return withQtyOverride(qtyOverride);
		}
		else
		{
			return withQtyOverride((Quantity)null);
		}
	}

	public boolean isQtyOverrideEditableByUser()
	{
		return isFieldEditable(FIELD_QtyOverride);
	}

	private boolean isFieldEditable(final String fieldName)
	{
		final ViewEditorRenderMode renderMode = getViewEditorRenderModeByFieldName().get(fieldName);
		return renderMode != null && renderMode.isEditable();
	}

	public ProductsToPickRow withRowType(@Nullable final ProductsToPickRowType rowType)
	{
		return Objects.equals(this.rowType, rowType)
				? this
				: toBuilder().rowType(rowType).build();
	}

	public boolean isApproved()
	{
		return approvalStatus != null && approvalStatus.isApproved();
	}

	private boolean isEligibleForChangingPickStatus()
	{
		return !isProcessed()
				&& getType().isPickable();
	}

	public boolean isEligibleForPicking()
	{
		return isEligibleForChangingPickStatus()
				&& !isApproved()
				&& pickStatus != null
				&& pickStatus.isEligibleForPicking();
	}

	public boolean isEligibleForRejectPicking()
	{
		return isEligibleForChangingPickStatus()
				&& !isApproved()
				&& pickStatus != null
				&& pickStatus.isEligibleForRejectPicking();
	}

	public boolean isEligibleForPacking()
	{
		return isEligibleForChangingPickStatus()
				&& isApproved()
				&& pickStatus != null
				&& pickStatus.isEligibleForPacking();
	}

	public boolean isEligibleForReview()
	{
		return isEligibleForChangingPickStatus()
				&& pickStatus != null
				&& pickStatus.isEligibleForReview();
	}

	public boolean isEligibleForProcessing()
	{
		return isEligibleForChangingPickStatus()
				&& pickStatus != null
				&& pickStatus.isEligibleForProcessing();
	}

	public String getLocatorName()
	{
		return locator != null ? locator.getDisplayName() : "";
	}

	@Override
	public List<ProductsToPickRow> getIncludedRows()
	{
		return includedRows;
	}
}
