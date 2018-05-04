package de.metas.shipper.gateway.go;

import javax.annotation.Nullable;

import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.HWBNumber;
import de.metas.shipper.gateway.spi.model.PaidMode;
import de.metas.shipper.gateway.spi.model.SelfDelivery;
import de.metas.shipper.gateway.spi.model.SelfPickup;
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
public class GoDeliveryOrderData implements CustomDeliveryData
{
	@Nullable
	HWBNumber hwbNumber;

	@Nullable // might cost money
	private String receiptConfirmationPhoneNumber;

	@NonNull
	private PaidMode paidMode;

	@NonNull
	private SelfDelivery selfDelivery;

	@NonNull
	private SelfPickup selfPickup;

	public static GoDeliveryOrderData ofDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder)
	{
		return cast(deliveryOrder.getCustomDeliveryData());
	}

	public static GoDeliveryOrderData cast(@Nullable final CustomDeliveryData customDeliveryOrderData)
	{
		return (GoDeliveryOrderData)customDeliveryOrderData;
	}
}
