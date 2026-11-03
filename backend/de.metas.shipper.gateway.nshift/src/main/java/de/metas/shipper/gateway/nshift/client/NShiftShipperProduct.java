/*
 * #%L
 * de.metas.shipper.gateway.nshift
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

package de.metas.shipper.gateway.nshift.client;

import de.metas.shipper.gateway.spi.model.ShipperProduct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NShiftShipperProduct implements ShipperProduct
{
	// TODO next iteration consider replacing with String.intern so it's more flexible and new shipper doesn't imply code changes
	DHL_NATIONAL("DHL - Domestic"),
	DHL_INTERNATIONAL("DHL - Euroconnect"),
	DHL_DHLPAKET("Deutsche Post - DHL Paket"),
	DHL_DEPICKUP("Deutsche Post - Parcel DE Pickup"),
	DHL_WARENPOST("Deutsche Post - Warenpost"),
	DHL_NIGHTSTAR("Night Star Express"),

	;
	@Getter
	private final String code;
	}
