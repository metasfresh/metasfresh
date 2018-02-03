package de.metas.ui.web.picking.packageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.picking.PickingConstants;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewsIndexStorage;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowAttributes;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

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
@ToString(exclude = "_fieldNameAndJsonValues")
public final class PackageableRow implements IViewRow
{
	private final ViewId viewId;
	private final DocumentId id;
	private final DocumentPath documentPath;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_Packageable_V.COLUMNNAME_C_Order_ID, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10)
	})
	private final LookupValue order;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_Packageable_V.COLUMNNAME_M_Product_ID, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20)
	})
	private final LookupValue product;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, captionKey = I_M_Packageable_V.COLUMNNAME_QtyToDeliver, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30)
	})
	private final BigDecimal qtyToDeliver;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, captionKey = I_M_Packageable_V.COLUMNNAME_QtyPickedPlanned, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 35)
	})
	private final BigDecimal qtyPickedPlanned;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_Packageable_V.COLUMNNAME_C_BPartner_ID, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40)
	})
	private final LookupValue bpartner;

	@ViewColumn(widgetType = DocumentFieldWidgetType.DateTime, captionKey = I_M_Packageable_V.COLUMNNAME_PreparationDate, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50)
	})
	private final java.util.Date preparationDate;

	private final int shipmentScheduleId;

	private final ViewId includedViewId;

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues;

	public static PackageableRow cast(final IViewRow row)
	{
		return (PackageableRow)row;
	}

	public static DocumentId createRowIdFromShipmentScheduleId(final int shipmentScheduleId)
	{
		return DocumentId.of(shipmentScheduleId);
	}

	public static TableRecordReference createTableRecordReferenceFromShipmentScheduleId(final int shipmentScheduleId)
	{
		return TableRecordReference.of(I_M_Packageable_V.Table_Name, shipmentScheduleId);
	}

	@Builder
	private PackageableRow(
			final int shipmentScheduleId,
			@NonNull final ViewId viewId,
			final LookupValue order,
			final LookupValue product,
			final BigDecimal qtyToDeliver,
			final BigDecimal qtyPickedPlanned,
			final LookupValue bpartner,
			final Date preparationDate)
	{
		Check.assume(shipmentScheduleId > 0, "shipmentScheduleId > 0");

		this.viewId = viewId;
		this.id = createRowIdFromShipmentScheduleId(shipmentScheduleId);
		this.documentPath = DocumentPath.rootDocumentPath(PickingConstants.WINDOWID_PickingView, id);

		this.order = order;
		this.product = product;
		this.qtyToDeliver = qtyToDeliver;
		this.qtyPickedPlanned = qtyPickedPlanned != null ? qtyPickedPlanned : BigDecimal.ZERO;
		this.bpartner = bpartner;
		this.preparationDate = preparationDate;
		this.shipmentScheduleId = shipmentScheduleId;

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
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		if (_fieldNameAndJsonValues == null)
		{
			_fieldNameAndJsonValues = ViewColumnHelper.extractJsonMap(this);
		}
		return _fieldNameAndJsonValues;
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

	public int getShipmentScheduleId()
	{
		return shipmentScheduleId;
	}
}
