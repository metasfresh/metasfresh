package de.metas.ui.web.shipment_candidates_editor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.util.ASIEditingInfo;
import org.adempiere.mm.attributes.util.ASIEditingInfo.WindowType;
import com.google.common.collect.ImmutableMap;

import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequest;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequest.ShipmentScheduleUserChangeRequestBuilder;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.pattribute.WebuiASIEditingInfo;
import de.metas.ui.web.pattribute.WebuiASIEditingInfoAware;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public final class ShipmentCandidateRow implements IViewRow, WebuiASIEditingInfoAware
{
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, captionKey = "C_OrderSO_ID")
	private final LookupValue salesOrder;

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, captionKey = "C_BPartner_Customer_ID")
	private final LookupValue customer;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, captionKey = "M_Warehouse_ID")
	private final LookupValue warehouse;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.ExtraLarge, captionKey = "M_Product_ID")
	private final LookupValue product;

	public static final String FIELD_asi = "asi";
	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.ProductAttributes, fieldName = FIELD_asi, captionKey = "M_AttributeSetInstance_ID", editor = ViewEditorRenderMode.ALWAYS)
	private final LookupValue asi;

	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Medium, captionKey = "PackDescription")
	private final String packingDescription;

	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.ZonedDateTime, widgetSize = WidgetSize.Small, captionKey = "PreparationDate")
	private final ZonedDateTime preparationDate;

	@ViewColumn(seqNo = 80, widgetType = DocumentFieldWidgetType.Quantity, widgetSize = WidgetSize.Small, captionKey = "QtyOrdered")
	private final BigDecimal qtyOrdered;

	@ViewColumn(seqNo = 90, widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, captionKey = "C_UOM_ID")
	private final LookupValue uom;

	public static final String FIELD_qtyToDeliverStockOverride = "qtyToDeliverStockOverride";
	@ViewColumn(seqNo = 100, widgetType = DocumentFieldWidgetType.Quantity, fieldName = FIELD_qtyToDeliverStockOverride, widgetSize = WidgetSize.Small, captionKey = "QtyToDeliver_Override", editor = ViewEditorRenderMode.ALWAYS)
	private final BigDecimal qtyToDeliverStockOverride;

	public static final String FIELD_qtyToDeliverCatchOverride = "qtyToDeliverCatchOverride";
	@ViewColumn(seqNo = 110, widgetType = DocumentFieldWidgetType.Quantity, fieldName = FIELD_qtyToDeliverCatchOverride, widgetSize = WidgetSize.Small, captionKey = "QtyToDeliverCatch_Override", editor = ViewEditorRenderMode.ALWAYS)
	private final BigDecimal qtyToDeliverCatchOverride;

	@ViewColumn(seqNo = 120, widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, captionKey = "Catch_UOM_ID")
	private final LookupValue catchUOM;

	private final ShipmentScheduleId shipmentScheduleId;
	private final DocumentId rowId;
	private final int salesOrderLineNo;

	private final ViewRowFieldNameAndJsonValuesHolder<ShipmentCandidateRow> values;
	private final ImmutableMap<String, ViewEditorRenderMode> fieldNameAndJsonValues;

	private boolean catchWeight;

	// also store the editable value's initial values, so we know what was changed and can be persisted
	private final Quantity qtyToDeliverStockInitial;
	private final BigDecimal qtyToDeliverCatchOverrideInitial;
	private final AttributeSetInstanceId asiIdInitial;

	/**
	 * If {@code catchUOM} is null, then the user is not supposed to enter a catch weight override quantity.
	 */
	@Builder(toBuilder = true)
	private ShipmentCandidateRow(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final LookupValue salesOrder,
			final int salesOrderLineNo,
			@NonNull final LookupValue customer,
			@NonNull final LookupValue warehouse,
			@NonNull final LookupValue product,
			@Nullable final String packingDescription,
			@NonNull final ZonedDateTime preparationDate,
			@NonNull final BigDecimal qtyOrdered,
			@NonNull final LookupValue uom,
			@NonNull final Quantity qtyToDeliverStockInitial,
			@NonNull final BigDecimal qtyToDeliverStockOverride,
			//
			final boolean catchWeight,
			@Nullable final BigDecimal qtyToDeliverCatchOverrideInitial,
			@Nullable final BigDecimal qtyToDeliverCatchOverride,
			@Nullable final LookupValue catchUOM,
			//
			@NonNull final AttributeSetInstanceId asiIdInitial,
			@NonNull final LookupValue asi)
	{
		this.salesOrder = salesOrder;
		this.salesOrderLineNo = salesOrderLineNo;
		this.customer = customer;
		this.warehouse = warehouse;
		this.product = product;
		this.packingDescription = !Check.isEmpty(packingDescription, true) ? packingDescription.trim() : null;
		this.preparationDate = preparationDate;

		this.qtyOrdered = qtyOrdered;
		this.uom = uom;

		this.qtyToDeliverStockInitial = qtyToDeliverStockInitial;
		this.qtyToDeliverStockOverride = qtyToDeliverStockOverride;

		this.catchWeight = catchWeight;
		this.qtyToDeliverCatchOverrideInitial = qtyToDeliverCatchOverrideInitial;
		this.qtyToDeliverCatchOverride = qtyToDeliverCatchOverride;
		this.catchUOM = catchUOM;

		this.asiIdInitial = asiIdInitial;
		this.asi = asi;

		this.shipmentScheduleId = shipmentScheduleId;
		rowId = DocumentId.of(shipmentScheduleId);

		values = ViewRowFieldNameAndJsonValuesHolder.newInstance(ShipmentCandidateRow.class);
		fieldNameAndJsonValues = buildFieldNameAndJsonValues(catchWeight);
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return null;
	}

	@Override
	public Set<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}

	@Override
	public Map<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		return fieldNameAndJsonValues;
	}

	private static ImmutableMap<String, ViewEditorRenderMode> buildFieldNameAndJsonValues(final boolean catchWeight)
	{
		final ImmutableMap.Builder<String, ViewEditorRenderMode> result = ImmutableMap.builder();
		result.put(FIELD_qtyToDeliverCatchOverride, catchWeight ? ViewEditorRenderMode.ALWAYS : ViewEditorRenderMode.NEVER);

		return result.build();
	}

	public String getSalesOrderDisplayNameOrEmpty()
	{
		return salesOrder != null ? salesOrder.getDisplayName() : "";
	}

	public int getSalesOrderLineNo()
	{
		return salesOrderLineNo;
	}

	public ShipmentCandidateRow withChanges(@NonNull final ShipmentCandidateRowUserChangeRequest userChanges)
	{
		final ShipmentCandidateRowBuilder builder = toBuilder();

		if (userChanges.getQtyToDeliverStockOverride() != null)
		{
			builder.qtyToDeliverStockOverride(userChanges.getQtyToDeliverStockOverride());
		}
		if (userChanges.getQtyToDeliverCatchOverride() != null)
		{
			builder.qtyToDeliverCatchOverride(userChanges.getQtyToDeliverCatchOverride());
		}
		if (userChanges.getAsi() != null)
		{
			builder.asi(userChanges.getAsi());
		}
		return builder.build();
	}

	@Override
	public WebuiASIEditingInfo getWebuiASIEditingInfo(@NonNull final AttributeSetInstanceId asiId)
	{
		final ProductId productId = product.getIdAs(ProductId::ofRepoIdOrNull);

		final ASIEditingInfo info = ASIEditingInfo.builder()
				.type(WindowType.Regular)
				.productId(productId)
				.attributeSetInstanceId(asiId)
				.callerTableName(null)
				.callerColumnId(-1)
				.soTrx(SOTrx.SALES)
				.build();

		return WebuiASIEditingInfo.builder(info).build();
	}

	Optional<ShipmentScheduleUserChangeRequest> createShipmentScheduleUserChangeRequest()
	{
		final ShipmentScheduleUserChangeRequestBuilder builder = ShipmentScheduleUserChangeRequest.builder()
				.shipmentScheduleId(shipmentScheduleId);

		boolean changes = false;

		if (qtyToDeliverStockInitial.toBigDecimal().compareTo(qtyToDeliverStockOverride) != 0)
		{
			builder.qtyToDeliverStockOverride(qtyToDeliverStockOverride);
			changes = true;
		}

		if (qtyToDeliverCatchOverrideIsChanged())
		{
			builder.qtyToDeliverCatchOverride(qtyToDeliverCatchOverride);
			changes = true;
		}

		final AttributeSetInstanceId asiId = asi.getIdAs(AttributeSetInstanceId::ofRepoIdOrNone);
		if (!Objects.equals(asiIdInitial, asiId))
		{
			builder.asiId(asiId);
			changes = true;
		}

		return changes
				? Optional.of(builder.build())
				: Optional.empty();
	}

	private boolean qtyToDeliverCatchOverrideIsChanged()
	{
		final Optional<Boolean> nullValuechanged = isNullValuesChanged(qtyToDeliverCatchOverrideInitial, qtyToDeliverCatchOverride);
		return nullValuechanged.orElseGet(() -> qtyToDeliverCatchOverrideInitial.compareTo(qtyToDeliverCatchOverride) != 0);
	}

	private Optional<Boolean> isNullValuesChanged(
			@Nullable final Object initital,
			@Nullable final Object current)
	{
		final boolean wasNull = initital == null;
		final boolean isNull = current == null;

		if (wasNull)
		{
			if (isNull)
			{
				return Optional.of(false);
			}
			return Optional.of(true); // was null and is not null anymore
		}

		if (isNull)
		{
			return Optional.of(true); // was not null and is now
		}

		return Optional.empty(); // was not null and still is not null; will need to compare the current values
	}
}
