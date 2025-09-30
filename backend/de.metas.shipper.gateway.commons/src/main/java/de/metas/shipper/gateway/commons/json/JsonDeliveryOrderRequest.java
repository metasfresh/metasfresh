/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.json;

import com.google.common.collect.ImmutableList;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryOrderRequest
{
	@Nullable DeliveryOrderId id;
	@NonNull Address pickupAddress;
	@NonNull PickupDate pickupDate;
	@Nullable String pickupNote;
	@NonNull Address deliveryAddress;
	@Nullable ContactPerson deliveryContact;
	@Nullable DeliveryDate deliveryDate;
	@Nullable String deliveryNote;
	@Nullable String customerReference;
	@NonNull @Singular ImmutableList<JsonDeliveryOrderLine> deliveryOrderLines;
	@Nullable String shipperProduct;
	@Nullable String shipperEORI;
	@Nullable String receiverEORI;

}
