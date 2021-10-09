/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.api;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import de.metas.inoutcandidate.ShipmentScheduleId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;

import java.util.Objects;
import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.freighcost.FreightCostRule;
import org.eevolution.api.PPOrderId;
import de.metas.money.Money;
import de.metas.order.DeliveryViaRule;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/** Lines which have to be picked and delivered */
@Value
@Builder
public class Packageable
{
	@NonNull
	ShipmentScheduleId shipmentScheduleId;

	@NonNull
	Quantity qtyOrdered;
	@NonNull
	Quantity qtyToDeliver;
	@NonNull
	Quantity qtyDelivered;
	@NonNull
	Quantity qtyPickedAndDelivered;
	@NonNull
	Quantity qtyPickedNotDelivered;
	/** quantity picked planned (i.e. picking candidates not already processed) */
	@NonNull
	Quantity qtyPickedPlanned;

	@NonNull
	BPartnerId customerId;
	String customerBPValue;
	String customerName;

	@NonNull
	BPartnerLocationId customerLocationId;
	String customerBPLocationName;
	String customerAddress;

	@NonNull
	WarehouseId warehouseId;
	String warehouseName;
	WarehouseTypeId warehouseTypeId;

	DeliveryViaRule deliveryViaRule;

	ShipperId shipperId;
	String shipperName;

	boolean displayed;

	ZonedDateTime deliveryDate;
	ZonedDateTime preparationDate;

	@NonNull
	@Default
	Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy = Optional.empty();

	FreightCostRule freightCostRule;

	@NonNull
	ProductId productId;
	String productName;

	@NonNull
	AttributeSetInstanceId asiId;

	@Nullable
	OrderId salesOrderId;
	@Nullable
	String salesOrderDocumentNo;
	@Nullable
	String salesOrderDocSubType;
	@Nullable
	String poReference;

	@Nullable
	OrderLineId salesOrderLineIdOrNull;
	@Nullable
	Money salesOrderLineNetAmt;

	@Nullable
	PPOrderId pickFromOrderId;

	@Nullable
	UserId lockedBy;

	public static <T> Optional<T> extractSingleValue(@NonNull final Collection<Packageable> packageables, @NonNull final Function<Packageable, T> mapper)
	{
		if (packageables.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableList<T> values = packageables.stream()
				.map(mapper)
				.filter(Objects::nonNull)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		if (values.isEmpty())
		{
			return Optional.empty();
		}
		else if (values.size() == 1)
		{
			return Optional.of(values.get(0));
		}
		else
		{
			throw new AdempiereException("More than one value were extracted (" + values + ") from " + packageables);
		}
	}

	public Quantity getQtyPickedOrDelivered()
	{
		// NOTE: keep in sync with M_Packageable_V.QtyPickedOrDelivered
		return getQtyDelivered()
				.add(getQtyPickedNotDelivered())
				.add(getQtyPickedPlanned());

	}
}
