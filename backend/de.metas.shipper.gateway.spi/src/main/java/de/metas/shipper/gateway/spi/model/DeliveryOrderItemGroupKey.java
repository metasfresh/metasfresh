/*
 * #%L
 * de.metas.shipper.gateway.spi
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

package de.metas.shipper.gateway.spi.model;

import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Captures the customs-relevant attributes of a {@link DeliveryOrderItem} that determine
 * which items can share the same {@link DeliveryOrderParcel}.
 * Items with the same key are placed in the same parcel; items with different keys must
 * be separated into distinct parcels (e.g. for customs declarations).
 * <p>
 * Analogous to {@link de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.DeliveryOrderKey}
 * which groups packages into delivery orders.
 */
@Value
public class DeliveryOrderItemGroupKey
{
	/**
	 * ISO 3166-1 alpha-2 country of origin (e.g. {@code "IT"}, {@code "DE"}).
	 * {@code null} means no country is set on this item.
	 */
	@Nullable String countryOfOrigin;

	public static DeliveryOrderItemGroupKey of(@NonNull final DeliveryOrderItem item)
	{
		return new DeliveryOrderItemGroupKey(item.getCountryOfOrigin());
	}
}
