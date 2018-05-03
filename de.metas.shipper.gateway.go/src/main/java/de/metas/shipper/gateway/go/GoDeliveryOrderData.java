package de.metas.shipper.gateway.go;

import javax.annotation.Nullable;

import de.metas.shipper.gateway.spi.model.CustomDeliveryOrderData;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.HWBNumber;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder
public class GoDeliveryOrderData implements CustomDeliveryOrderData
{
	@Nullable
	HWBNumber hwbNumber;

	@Nullable // might cost money
	private String receiptConfirmationPhoneNumber;

	public static GoDeliveryOrderData ofDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder)
	{
		return cast(deliveryOrder.getCustomDeliveryOrderData());
	}

	public static GoDeliveryOrderData cast(@Nullable final CustomDeliveryOrderData customDeliveryOrderData)
	{
		return (GoDeliveryOrderData)customDeliveryOrderData;
	}
}
