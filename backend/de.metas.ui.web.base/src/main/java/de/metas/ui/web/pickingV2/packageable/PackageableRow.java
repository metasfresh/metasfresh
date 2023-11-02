/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.pickingV2.packageable;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableList;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout.Displayed;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.warehouse.WarehouseTypeId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

@ToString(exclude = "values")
public final class PackageableRow implements IViewRow
{
	private static final String SYSCFG_PREFIX = "de.metas.ui.web.pickingV2.packageable.PackageableRow.field";

	public static PackageableRow cast(final IViewRow row)
	{
		return (PackageableRow)row;
	}

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = I_M_Packageable_V.COLUMNNAME_C_OrderSO_ID, seqNo = 10)
	@Getter
	private final String orderDocumentNo;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = I_M_Packageable_V.COLUMNNAME_POReference, seqNo = 15,
			displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
	private final String poReference;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID, seqNo = 20)
	@Getter
	private final LookupValue customer;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = I_M_Packageable_V.COLUMNNAME_M_Warehouse_Type_ID,
			seqNo = 30, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX, defaultDisplaySysConfig = true)
	private final ITranslatableString warehouseTypeName;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Integer, captionKey = "Lines", seqNo = 40)
	private final int lines;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = "Picking_User_ID", seqNo = 50)
	private final LookupValue lockedByUser;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = I_M_Packageable_V.COLUMNNAME_M_Shipper_ID, seqNo = 60)
	private final LookupValue shipper;

	@ViewColumn(widgetType = DocumentFieldWidgetType.LocalDate, captionKey = I_M_Packageable_V.COLUMNNAME_DeliveryDate, seqNo = 70)
	private final LocalDate deliveryDate;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = "LineNetAmt", seqNo = 80)
	private final ITranslatableString lineNetAmt;

	@ViewColumn(widgetType = DocumentFieldWidgetType.ZonedDateTime, captionKey = I_M_Packageable_V.COLUMNNAME_PreparationDate, seqNo = 90)
	@Getter
	private final ZonedDateTime preparationDate;

	//
	private final ViewRowFieldNameAndJsonValuesHolder<PackageableRow> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(PackageableRow.class);
	private final PackageableRowId rowId;
	@Getter
	private final PackageableList packageables;
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
			final LookupValue lockedByUser,
			final LookupValue shipper,
			final ITranslatableString lineNetAmt,
			@NonNull final PackageableList packageables,
			@NonNull final ZoneId timeZone,
			@Nullable final String poReference)
	{
		Check.assume(!packageables.isEmpty(), "packageables shall not be empty");

		this.rowId = PackageableRowId.of(orderId, warehouseTypeId);
		this.orderDocumentNo = orderDocumentNo;
		this.poReference = poReference;
		this.customer = customer;
		this.warehouseTypeName = warehouseTypeName;
		this.lines = lines;
		this.lockedByUser = lockedByUser;
		this.shipper = shipper;
		this.deliveryDate = calculateEarliestDeliveryDate(packageables);
		this.lineNetAmt = lineNetAmt;
		this.preparationDate = calculateEarliestPreparationTime(packageables)
				.map(instant -> instant.toZonedDateTime(timeZone))
				.orElse(null);
		this.packageables = packageables;
		this.shipmentScheduleIds = packageables.getShipmentScheduleIds();
	}

	@Nullable
	private static LocalDate calculateEarliestDeliveryDate(final PackageableList packageables)
	{
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		return packageables.stream()
				.map(Packageable::getDeliveryDate)
				.filter(Objects::nonNull)
				.map(date -> date.toZonedDateTime(orgDAO::getTimeZone).toLocalDate())
				.filter(Objects::nonNull)
				.min(LocalDate::compareTo)
				.orElse(null);
	}

	private static Optional<InstantAndOrgId> calculateEarliestPreparationTime(final PackageableList packageables)
	{
		return packageables.stream()
				.map(Packageable::getPreparationDate)
				.filter(Objects::nonNull)
				.min(InstantAndOrgId::compareTo);
	}

	@Override
	public DocumentId getId()
	{
		return rowId.getDocumentId();
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Nullable
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

	public boolean isLocked()
	{
		return lockedByUser != null;
	}

	public boolean isNotLocked()
	{
		return !isLocked();
	}

	public boolean isLockedBy(@NonNull final UserId userId)
	{
		return lockedByUser != null
				&& UserId.equals(userId, lockedByUser.getIdAs(UserId::ofRepoId));
	}
}
