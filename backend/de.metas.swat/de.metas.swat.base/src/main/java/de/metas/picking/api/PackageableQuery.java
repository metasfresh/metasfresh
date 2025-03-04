/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.picking.api;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocumentNoFilter;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Set;

@Value
@Builder
public class PackageableQuery
{
	public static final PackageableQuery ALL = PackageableQuery.builder().build();

	@NonNull @Singular ImmutableSet<BPartnerId> customerIds;
	@NonNull @Singular ImmutableSet<BPartnerLocationId> handoverLocationIds;
	@Nullable BPartnerLocationId deliveryBPLocationId;
	@Nullable WarehouseTypeId warehouseTypeId;
	@Nullable WarehouseId warehouseId;
	@NonNull @Singular ImmutableSet<LocalDate> deliveryDays;
	@Nullable LocalDate preparationDate;
	@Nullable ShipperId shipperId;

	/**
	 * retrieve only those packageables which are created from sales order/lines
	 */
	boolean onlyFromSalesOrder;
	@Nullable OrderId salesOrderId;
	@Nullable DocumentNoFilter salesOrderDocumentNo;

	/**
	 * Consider records which were locked via M_ShipmentSchedule_Lock table.
	 */
	@Nullable UserId lockedBy;
	/**
	 * Considers records which were not locked via M_ShipmentSchedule_Lock table. Applies when {@link #lockedBy} is set.
	 */
	@Builder.Default boolean includeNotLocked = true;

	/**
	 * Excludes records which were locked via T_Lock table.
	 */
	@Builder.Default boolean excludeLockedForProcessing = false; // false by default to be backward-compatibile

	@Nullable Set<ShipmentScheduleId> excludeShipmentScheduleIds;

	@Builder.Default
	@NonNull ImmutableSet<OrderBy> orderBys = ImmutableSet.of(OrderBy.ProductName, OrderBy.PriorityRule, OrderBy.DateOrdered);

	public enum OrderBy
	{
		ProductName,
		PriorityRule,
		DateOrdered,
		PreparationDate,
		SalesOrderId,
		DeliveryBPLocationId,
		WarehouseTypeId,
		SetupPlaceNo_Descending,
	}
}
