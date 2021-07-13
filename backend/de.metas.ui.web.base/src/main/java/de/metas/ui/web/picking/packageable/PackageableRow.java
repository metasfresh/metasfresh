package de.metas.ui.web.picking.packageable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.picking.PickingConstants;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewsIndexStorage;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowAttributes;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.WidgetSize;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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

/**
 * Rows shown in {@link PackageableView}. Each row basically represents one {@link I_M_Packageable_V}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString(exclude = "values")
public final class PackageableRow implements IViewRow
{
	private final ViewId viewId;
	private final DocumentId id;
	private final DocumentPath documentPath;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small,
			captionKey = I_M_Packageable_V.COLUMNNAME_C_OrderSO_ID, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10)
	})
	private final LookupValue order;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_Packageable_V.COLUMNNAME_M_Product_ID, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20)
	})
	private final LookupValue product;

	@ViewColumn(widgetType = DocumentFieldWidgetType.YesNo, captionKey = "Picked", layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 25)
	})
	private final boolean picked;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, captionKey = I_M_Packageable_V.COLUMNNAME_QtyOrdered, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30)
	})
	private final Quantity qtyOrdered;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, captionKey = "QtyPicked", layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 35)
	})
	private final Quantity qtyPicked;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40)
	})
	private final LookupValue bpartner;

	@ViewColumn(widgetType = DocumentFieldWidgetType.ZonedDateTime, captionKey = I_M_Packageable_V.COLUMNNAME_PreparationDate, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50)
	})
	private final ZonedDateTime preparationDate;

	private final ShipmentScheduleId shipmentScheduleId;

	private final Optional<OrderLineId> salesOrderLineId;

	private final ViewId includedViewId;

	private final ViewRowFieldNameAndJsonValuesHolder<PackageableRow> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(PackageableRow.class);

	public static PackageableRow cast(final IViewRow row)
	{
		return (PackageableRow)row;
	}

	public static DocumentId createRowIdFromShipmentScheduleId(final ShipmentScheduleId shipmentScheduleId)
	{
		return DocumentId.of(shipmentScheduleId.getRepoId());
	}

	public static TableRecordReference createTableRecordReferenceFromShipmentScheduleId(final ShipmentScheduleId shipmentScheduleId)
	{
		return TableRecordReference.of(I_M_Packageable_V.Table_Name, shipmentScheduleId);
	}

	@Builder
	private PackageableRow(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final Optional<OrderLineId> salesOrderLineId,
			@NonNull final ViewId viewId,
			final LookupValue order,
			final LookupValue product,
			@NonNull final Quantity qtyOrdered,
			final Quantity qtyPicked,
			final LookupValue bpartner,
			final ZonedDateTime preparationDate)
	{
		this.viewId = viewId;
		this.id = createRowIdFromShipmentScheduleId(shipmentScheduleId);
		this.documentPath = DocumentPath.rootDocumentPath(PickingConstants.WINDOWID_PickingView, id);

		this.order = order;
		this.product = product;
		this.qtyOrdered = qtyOrdered;
		this.qtyPicked = qtyPicked;
		this.picked = qtyPicked != null && qtyPicked.compareTo(qtyOrdered) >= 0;
		this.bpartner = bpartner;
		this.preparationDate = preparationDate;
		this.shipmentScheduleId = shipmentScheduleId;
		this.salesOrderLineId = salesOrderLineId;

		// create the included view's ID
		// note that despite all our packageable-rows have the same picking slots, the IDs still need to be individual per-row,
		// because we need to notify the picking slot view of this packageable-rows is selected at a given point in time
		this.includedViewId = PickingSlotViewsIndexStorage.createViewId(viewId, id);
	}

	@Override
	public DocumentId getId()
	{
		return id;
	}

	@Override
	public IViewRowType getType()
	{
		return DefaultRowType.Row;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	public TableRecordReference getTableRecordReference()
	{
		return createTableRecordReferenceFromShipmentScheduleId(getShipmentScheduleId());
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
	public List<? extends IViewRow> getIncludedRows()
	{
		return ImmutableList.of();
	}

	@Override
	public boolean hasAttributes()
	{
		return false;
	}

	@Override
	public IViewRowAttributes getAttributes() throws EntityNotFoundException
	{
		throw new EntityNotFoundException("Row does not support attributes");
	}

	@Override
	public ViewId getIncludedViewId()
	{
		return includedViewId;
	}

	public ShipmentScheduleId getShipmentScheduleId()
	{
		return shipmentScheduleId;
	}

	public Optional<OrderLineId> getSalesOrderLineId()
	{
		return salesOrderLineId;
	}

	public ProductId getProductId()
	{
		return product != null ? ProductId.ofRepoIdOrNull(product.getIdAsInt()) : null;
	}

	public Quantity getQtyOrderedWithoutPicked()
	{
		return qtyOrdered.subtract(qtyPicked).toZeroIfNegative();
	}
}
