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

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_Carrier_Config;

/**
 * Which nShift shipment endpoint a carrier uses, configured via {@code Carrier_Config.ShipType}:
 * {@link #SHIP} → {@code /ShipServer/{ID}/Shipments}, {@link #ORDER} (default) → {@code /ShipServer/{ID}/OrderAdvice} with Submit=1.
 */
@RequiredArgsConstructor
public enum ShipType implements ReferenceListAwareEnum
{
	SHIP(X_Carrier_Config.SHIPTYPE_Ship),
	ORDER(X_Carrier_Config.SHIPTYPE_Order);

	private static final ReferenceListAwareEnums.ValuesIndex<ShipType> index = ReferenceListAwareEnums.index(values());

	@NonNull @Getter private final String code;

	@NonNull
	public static ShipType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
}
