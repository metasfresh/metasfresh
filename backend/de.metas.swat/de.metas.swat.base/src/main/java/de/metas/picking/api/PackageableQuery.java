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
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
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

	@Nullable BPartnerId customerId;
	@Nullable BPartnerLocationId deliveryBPLocationId;
	@Nullable WarehouseTypeId warehouseTypeId;
	@Nullable WarehouseId warehouseId;
	@Nullable LocalDate deliveryDate;
	@Nullable LocalDate preparationDate;
	@Nullable ShipperId shipperId;

	/**
	 * retrieve only those packageables which are created from sales order/lines
	 */
	boolean onlyFromSalesOrder;
	@Nullable OrderId salesOrderId;

	@Nullable UserId lockedBy;
	@Builder.Default
	boolean includeNotLocked = true;

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
	}
}
