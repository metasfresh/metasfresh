package de.metas.shipper.gateway.go;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.OrderId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.shipper.gateway.go
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

@UtilityClass
public class GOUtils
{
	public static final OrderId createOrderId(final String orderIdStr)
	{
		return OrderId.of(GOConstants.SHIPPER_GATEWAY_ID, orderIdStr);
	}

	public static final OrderId createOrderIdOrNull(@Nullable final String orderIdStr)
	{
		if (Check.isEmpty(orderIdStr, true))
		{
			return null;
		}
		return createOrderId(orderIdStr);
	}

	public static DeliveryPosition getSingleDeliveryPosition(@NonNull final DeliveryOrder deliveryOrder)
	{
		Check.errorIf(deliveryOrder.getDeliveryPositions().size() != 1,
				"The GO! implementation needs to always create DeliveryOrders with exactly 1 DeliveryPosition; deliveryOrder={}",
				deliveryOrder);

		return deliveryOrder.getDeliveryPositions().get(0);
	}
}
