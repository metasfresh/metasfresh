package de.metas.ui.web.pickingV2.productsToPick;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateApprovalStatus;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidatePickStatus;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
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

@ToString(exclude = "_fieldNameAndJsonValues")
public class ProductsToPickRow implements IViewRow
{
	static final String FIELD_Product = "product";
	@ViewColumn(fieldName = FIELD_Product, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_Product_ID", widgetSize = WidgetSize.Small)
	private final LookupValue product;

	static final String FIELD_Locator = "locator";
	@ViewColumn(fieldName = FIELD_Locator, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_Locator_ID", widgetSize = WidgetSize.Small)
	private final LookupValue locator;

	static final String FIELD_LotNumber = "lotNumber";
	@ViewColumn(fieldName = FIELD_LotNumber, widgetType = DocumentFieldWidgetType.Text, captionKey = "LotNumber", widgetSize = WidgetSize.Small)
	private final String lotNumber;

	static final String FIELD_ExpiringDate = "expiringDate";
	@ViewColumn(fieldName = FIELD_ExpiringDate, widgetType = DocumentFieldWidgetType.Date, captionKey = "ExpiringDate", widgetSize = WidgetSize.Small)
	@Getter
	private final LocalDate expiringDate;

	static final String FIELD_RepackNumber = "repackNumber";
	@ViewColumn(fieldName = FIELD_RepackNumber, widgetType = DocumentFieldWidgetType.Text, captionKey = "RepackNumber", widgetSize = WidgetSize.Small)
	private final String repackNumber;

	static final String FIELD_Damaged = "damaged";
	@ViewColumn(fieldName = FIELD_Damaged, widgetType = DocumentFieldWidgetType.YesNo, captionKey = "Bruch", widgetSize = WidgetSize.Small)
	private final Boolean damaged;

	static final String FIELD_Qty = "qty";
	@ViewColumn(fieldName = FIELD_Qty, widgetType = DocumentFieldWidgetType.Quantity, captionKey = "Qty", widgetSize = WidgetSize.Small)
	@Getter
	private final Quantity qty;

	static final String FIELD_QtyReview = "qtyReview";
	@ViewColumn(fieldName = FIELD_QtyReview, widgetType = DocumentFieldWidgetType.Quantity, captionKey = "Qty", widgetSize = WidgetSize.Small, editor = ViewEditorRenderMode.ALWAYS)
	@Getter
	private final BigDecimal qtyReview;

	static final String FIELD_PickStatus = "pickStatus";
	@ViewColumn(fieldName = FIELD_PickStatus, captionKey = "PickStatus", widgetType = DocumentFieldWidgetType.List, listReferenceId = PickingCandidatePickStatus.AD_REFERENCE_ID, widgetSize = WidgetSize.Small)
	private final PickingCandidatePickStatus pickStatus;

	static final String FIELD_ApprovalStatus = "approvalStatus";
	@ViewColumn(fieldName = FIELD_ApprovalStatus, captionKey = "ApprovalStatus", widgetType = DocumentFieldWidgetType.List, listReferenceId = PickingCandidateApprovalStatus.AD_REFERENCE_ID, widgetSize = WidgetSize.Small)
	private final PickingCandidateApprovalStatus approvalStatus;

	//
	private final ProductsToPickRowId rowId;
	private boolean processed;
	@Getter
	private final ShipmentScheduleId shipmentScheduleId;
	@Getter
	@Nullable
	private final PickingCandidateId pickingCandidateId;

	//
	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy

	@Builder(toBuilder = true)
	private ProductsToPickRow(
			@NonNull final ProductsToPickRowId rowId,
			@NonNull final LookupValue product,
			@NonNull final LookupValue locator,
			//
			final String lotNumber,
			final LocalDate expiringDate,
			final String repackNumber,
			final Boolean damaged,
			//
			@NonNull final Quantity qty,
			@Nullable final BigDecimal qtyReview,
			//
			final PickingCandidatePickStatus pickStatus,
			final PickingCandidateApprovalStatus approvalStatus,
			final boolean processed,
			//
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			final PickingCandidateId pickingCandidateId)
	{
		this.rowId = rowId;
		this.product = product;
		this.locator = locator;
		this.lotNumber = lotNumber;
		this.expiringDate = expiringDate;
		this.repackNumber = repackNumber;
		this.damaged = damaged;

		this.qty = qty;
		this.qtyReview = qtyReview;

		this.pickStatus = pickStatus != null ? pickStatus : PickingCandidatePickStatus.TO_BE_PICKED;
		this.approvalStatus = approvalStatus != null ? approvalStatus : PickingCandidateApprovalStatus.TO_BE_APPROVED;
		this.processed = processed;

		this.shipmentScheduleId = shipmentScheduleId;
		this.pickingCandidateId = pickingCandidateId;
	}

	@Override
	public DocumentId getId()
	{
		return rowId.toDocumentId();
	}

	@Override
	public boolean isProcessed()
	{
		return processed;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		if (_fieldNameAndJsonValues == null)
		{
			_fieldNameAndJsonValues = ViewColumnHelper.extractJsonMap(this);
		}
		return _fieldNameAndJsonValues;
	}

	public HuId getHuId()
	{
		return rowId.getHuId();
	}

	public ProductsToPickRow withUpdatesFromPickingCandidateIfNotNull(final PickingCandidate pickingCandidate)
	{
		if (pickingCandidate == null)
		{
			return this;
		}

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
		if (Objects.equals(this.qty, qty))
		{
			return this;
		}

		return toBuilder().qty(qty).build();
	}

	public boolean isApproved()
	{
		return approvalStatus.isApproved();
	}

	public boolean isEligibleForPicking()
	{
		return !isProcessed() && !isApproved();
	}

	public boolean isEligibleForPacking()
	{
		return !isProcessed() && !isApproved() && pickStatus.isPickedOrPacked();
	}

	public boolean isEligibleForReview()
	{
		return !isProcessed() && (pickStatus.isPacked() || pickStatus.isPickRejected());
	}

	public boolean isEligibleForProcessing()
	{
		return !isProcessed() && isApproved();
	}
}
