package de.metas.shipper.gateway.api.model;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.api
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

@Builder
@Value
public class PickupOrderResponse
{
	String orderId;
	String hwbNumber;

	String pickupDate;
	String note;

	@Singular
	ImmutableList<ResponseDeliveryPosition> deliveryPositions;

	@lombok.Builder
	@lombok.Value
	public static class ResponseDeliveryPosition
	{
		String positionNo;
		String numberOfPackages;
		
		@lombok.Singular
		ImmutableList<String> barcodes;
	}
}
