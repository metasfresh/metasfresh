package de.metas.ui.web.shipment_candidates_editor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.annotation.Nullable;

import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
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

public class ShipmentCandidateRow implements IViewRow
{
	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = "C_OrderSO_ID", seqNo = 10)
	private final String salesOrderDocumentNo;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = "C_BPartner_Customer_ID", seqNo = 20)
	private final LookupValue customer;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_Warehouse_ID", seqNo = 30)
	private final LookupValue warehouse;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = "M_Product_ID", seqNo = 50)
	private final LookupValue product;

	@ViewColumn(widgetType = DocumentFieldWidgetType.ZonedDateTime, captionKey = "PreparationDate", seqNo = 80)
	private final ZonedDateTime preparationDate;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, captionKey = "QtyToDeliver", seqNo = 90, editor = ViewEditorRenderMode.ALWAYS)
	private final BigDecimal qtyToDeliverBD;

	private final ViewRowFieldNameAndJsonValuesHolder<ShipmentCandidateRow> values;
	private final DocumentId rowId;
	private final Quantity qtyToDeliverInitial;

	@Builder
	private ShipmentCandidateRow(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final String salesOrderDocumentNo,
			@NonNull final LookupValue customer,
			@NonNull final LookupValue warehouse,
			@NonNull final LookupValue product,
			@NonNull final ZonedDateTime preparationDate,
			@NonNull final Quantity qtyToDeliver)
	{
		this.salesOrderDocumentNo = salesOrderDocumentNo;
		this.customer = customer;
		this.warehouse = warehouse;
		this.product = product;
		this.preparationDate = preparationDate;

		this.qtyToDeliverInitial = qtyToDeliver;
		this.qtyToDeliverBD = qtyToDeliver.getAsBigDecimal();

		rowId = DocumentId.of(shipmentScheduleId);

		values = ViewRowFieldNameAndJsonValuesHolder.newInstance(ShipmentCandidateRow.class);
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	@Override
	public boolean isProcessed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		// TODO Auto-generated method stub
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

}
