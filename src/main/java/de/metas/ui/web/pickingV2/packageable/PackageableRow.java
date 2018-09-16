package de.metas.ui.web.pickingV2.packageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.warehouse.WarehouseTypeId;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.order.OrderId;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
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
public final class PackageableRow implements IViewRow
{
	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = I_M_Packageable_V.COLUMNNAME_C_OrderSO_ID, seqNo = 10)
	private final String orderDocumentNo;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID, seqNo = 20)
	private final LookupValue customer;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = I_M_Packageable_V.COLUMNNAME_M_Warehouse_Type_ID, seqNo = 30)
	private final ITranslatableString warehouseTypeName;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Integer, captionKey = "Lines", seqNo = 40)
	private final int lines;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = "AD_User_ID", seqNo = 50)
	private final String assignedToUserName;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_Packageable_V.COLUMNNAME_M_Shipper_ID, seqNo = 60)
	private final LookupValue shipper;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Date, captionKey = I_M_Packageable_V.COLUMNNAME_DeliveryDate, seqNo = 70)
	private final LocalDate deliveryDate;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Amount, captionKey = "LineNetAmt", seqNo = 80)
	private final BigDecimal lineNetAmt;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Date, captionKey = I_M_Packageable_V.COLUMNNAME_PreparationDate, seqNo = 80)
	private final LocalDateTime preparationDate;

	//
	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy
	private final PackageableRowId rowId;
	@Getter
	private final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;

	@Builder
	private PackageableRow(
			@NonNull final OrderId orderId,
			@NonNull final String orderDocumentNo,
			@NonNull final LookupValue customer,
			final WarehouseTypeId warehouseTypeId,
			final ITranslatableString warehouseTypeName,
			final int lines,
			final String assignedToUserName,
			final LookupValue shipper,
			final LocalDate deliveryDate,
			final LocalDateTime preparationDate,
			final BigDecimal lineNetAmt,
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		Check.assumeNotEmpty(shipmentScheduleIds, "shipmentScheduleIds is not empty");

		this.rowId = PackageableRowId.of(orderId, warehouseTypeId);
		this.orderDocumentNo = orderDocumentNo;
		this.customer = customer;
		this.warehouseTypeName = warehouseTypeName;
		this.lines = lines;
		this.assignedToUserName = assignedToUserName;
		this.shipper = shipper;
		this.deliveryDate = deliveryDate;
		this.lineNetAmt = lineNetAmt;
		this.preparationDate = preparationDate;
		this.shipmentScheduleIds = ImmutableSet.copyOf(shipmentScheduleIds);
	}

	@Override
	public DocumentId getId()
	{
		return rowId.getDocumentId();
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

	// public TableRecordReference getTableRecordReference()
	// {
	// return createTableRecordReferenceFromShipmentScheduleId(getShipmentScheduleId());
	// }
	//
	// public static TableRecordReference createTableRecordReferenceFromShipmentScheduleId(final ShipmentScheduleId shipmentScheduleId)
	// {
	// return TableRecordReference.of(I_M_Packageable_V.Table_Name, shipmentScheduleId);
	// }

}
