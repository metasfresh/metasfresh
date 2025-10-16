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

package de.metas.shipper.gateway.commons.model;

import de.metas.shipper.gateway.spi.model.ShipperProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@RequiredArgsConstructor
public class CarrierShipperProduct implements ShipperProduct
{
	@Getter
	@NonNull String code;

	@NonNull String name;

	public static CarrierShipperProduct ofCode(@NonNull final String code)
	{
		return ofCode(code, code);
	}

	public static CarrierShipperProduct ofCode(@NonNull final String code, @NonNull final String name)
	{
		return new CarrierShipperProduct(code, name);
	}

}
