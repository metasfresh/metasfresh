/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.deliveryplanning;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_Delivery_Planning;

import javax.annotation.Nullable;

public enum TransportDirection implements ReferenceListAwareEnum
{
	Incoming(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming),
	Outgoing(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing),
	Dropship(X_M_Delivery_Planning.TRANSPORTDIRECTION_Dropship);

	private static final ReferenceListAwareEnums.ValuesIndex<TransportDirection> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	TransportDirection(@NonNull final String code)
	{
		this.code = code;
	}

	public static TransportDirection ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	@Nullable
	public static TransportDirection ofNullableCode(@Nullable final String code)
	{
		return ofNullableCode(code, null);
	}

	@Nullable
	public static TransportDirection ofNullableCode(@Nullable final String code, @Nullable final TransportDirection fallbackValue)
	{
		return code != null ? ofCode(code) : fallbackValue;
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final TransportDirection type)
	{
		return type != null ? type.getCode() : null;
	}

	public boolean hasReceipt()
	{
		return this == Incoming || this == Dropship;
	}

	/** Also true for {@link #Dropship}, whose shipment is generated together with the receipt but is carried by the paired sales-side planning. */
	public boolean hasShipment()
	{
		return this == Outgoing || this == Dropship;
	}

	public boolean isDropship()
	{
		return this == Dropship;
	}

}
