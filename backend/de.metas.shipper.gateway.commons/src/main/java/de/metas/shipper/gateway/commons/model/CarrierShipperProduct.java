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

import com.google.common.collect.ImmutableMap;
import de.metas.shipper.gateway.spi.model.ShipperProduct;
import de.metas.util.GuavaCollectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CarrierShipperProduct implements ShipperProduct
{
	// Here, I'm using this as: dear carrier aggregator, please send this order using DHL.
	DHL("DHL"),
	Any("");

	@Getter
	private final String code;

	private static final ImmutableMap<String, CarrierShipperProduct> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(CarrierShipperProduct::getCode));

	@NonNull
	public static CarrierShipperProduct forCode(final String code)
	{
		final CarrierShipperProduct type = code2type.get(code);
		if (type == null)
		{
			throw new AdempiereException("No serviceType for code=" + code + " exists.");
		}
		return type;
	}

}
