/*
 * #%L
 * de.metas.business
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

package de.metas.shipping;

import de.metas.lang.SOTrx;
import de.metas.shipping.model.X_M_ShipperTransportation;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * The three-valued direction of a transport, shared by two columns that live in two different tables:
 * {@code M_ShipperTransportation.TransportDirection} (this module) and
 * {@code M_Delivery_Planning.TransportDirection} ({@code de.metas.deliveryplanning.base}).
 * <p>
 * Both columns are backed by the same {@code AD_Reference_Value_ID}, so the codes taken from
 * {@code X_M_ShipperTransportation} below are valid for either table.
 */
public enum TransportDirection implements ReferenceListAwareEnum
{
	Incoming(X_M_ShipperTransportation.TRANSPORTDIRECTION_Incoming),
	Outgoing(X_M_ShipperTransportation.TRANSPORTDIRECTION_Outgoing),
	Dropship(X_M_ShipperTransportation.TRANSPORTDIRECTION_Dropship);

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

	/**
	 * The direction of a transport moving ONE document whose sales-vs-purchase nature is already settled: a sales
	 * transaction ships {@link #Outgoing}, a purchase transaction is {@link #Incoming}. Cannot yield
	 * {@link #Dropship}; a caller whose document can be a dropship must decide the direction itself.
	 */
	public static TransportDirection ofSOTrx(@NonNull final SOTrx soTrx)
	{
		return soTrx.isSales() ? Outgoing : Incoming;
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

	/**
	 * STRICTLY {@link #Outgoing}: unlike {@link #hasShipment()}, false for {@link #Dropship}, whose goods never
	 * leave our own warehouse because they never enter it.
	 */
	public boolean isOutgoing()
	{
		return this == Outgoing;
	}

}
