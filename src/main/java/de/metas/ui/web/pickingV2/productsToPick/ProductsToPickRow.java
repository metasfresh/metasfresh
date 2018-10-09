package de.metas.ui.web.pickingV2.productsToPick;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
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
	@ViewColumn(fieldName = FIELD_Product, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_Product_ID", seqNo = 10)
	private final LookupValue product;

	static final String FIELD_Locator = "locator";
	@ViewColumn(fieldName = FIELD_Locator, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_Locator_ID", seqNo = 20)
	private final LookupValue locator;

	static final String FIELD_LotNumber = "lotNumber";
	@ViewColumn(fieldName = FIELD_LotNumber, widgetType = DocumentFieldWidgetType.Text, captionKey = "LotNumber", seqNo = 30)
	private final String lotNumber;

	static final String FIELD_ExpiringDate = "expiringDate";
	@ViewColumn(fieldName = FIELD_ExpiringDate, widgetType = DocumentFieldWidgetType.Date, captionKey = "ExpiringDate", seqNo = 40)
	@Getter
	private final LocalDate expiringDate;

	static final String FIELD_RepackNumber = "repackNumber";
	@ViewColumn(fieldName = FIELD_RepackNumber, widgetType = DocumentFieldWidgetType.Text, captionKey = "RepackNumber", seqNo = 50)
	private final String repackNumber;

	static final String FIELD_Damaged = "damaged";
	@ViewColumn(fieldName = FIELD_Damaged, widgetType = DocumentFieldWidgetType.YesNo, captionKey = "Bruch", seqNo = 60) // Damaged
	private final Boolean damaged;

	static final String FIELD_Qty = "qty";
	@ViewColumn(fieldName = FIELD_Qty, widgetType = DocumentFieldWidgetType.Quantity, captionKey = "Qty", seqNo = 70)
	@Getter
	private final Quantity qty;

	@ViewColumn(widgetType = DocumentFieldWidgetType.YesNo, captionKey = "Processed", seqNo = 80)
	private final boolean processed;

	//
	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy
	private final ProductsToPickRowId rowId;
	@Getter
	private final ShipmentScheduleId shipmentScheduleId;
	@Getter
	@Nullable
	private final PickingCandidateId pickingCandidateId;

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
		this.shipmentScheduleId = shipmentScheduleId;
		this.pickingCandidateId = pickingCandidateId;
		this.processed = pickingCandidateId != null;
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

	public ProductsToPickRow withQty(@NonNull final Quantity qty)
	{
		if (Objects.equals(this.qty, qty))
		{
			return this;
		}

		return toBuilder().qty(qty).build();
	}

	public ProductsToPickRow withPickingCandidateId(@NonNull final PickingCandidateId pickingCandidateId)
	{
		if (PickingCandidateId.equals(this.pickingCandidateId, pickingCandidateId))
		{
			return this;
		}

		return toBuilder().pickingCandidateId(pickingCandidateId).build();
	}

	public boolean isToBePicked()
	{
		return getPickingCandidateId() == null;
	}

	public boolean isPrepared()
	{
		return getPickingCandidateId() != null;
	}

	public boolean isEligibleForReview()
	{
		return isPrepared();
	}
}
