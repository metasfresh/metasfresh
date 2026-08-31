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
 * It lives in {@code de.metas.business} because that is the lowest module both owners can reach:
 * {@code de.metas.deliveryplanning.base} -> {@code de.metas.handlingunits.base} -> {@code de.metas.business}.
 * Keeping it here is what lets both sides pass the direction as a type instead of a raw
 * {@code X_*.TRANSPORTDIRECTION_*} string or - worse - an {@code isSOTrx} boolean, which cannot express
 * {@link #Dropship} at all.
 * <p>
 * Both columns are backed by the SAME {@code AD_Reference_Value_ID = 541689}, so the codes below are
 * valid for either table; {@code X_M_ShipperTransportation} is referenced simply because that is this
 * module's own generated model.
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
	 * The direction of a transport that exists to move ONE document whose sales-vs-purchase nature is
	 * already settled: a sales transaction ships {@link #Outgoing}, a purchase transaction is
	 * {@link #Incoming}.
	 * <p>
	 * Deliberately NOT the general way to obtain a direction: {@link SOTrx} has two values and this type
	 * has three, so {@link #Dropship} is unreachable through here. A caller whose document CAN be a
	 * dropship must decide the direction itself and pass it, rather than reach for this method.
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
	 * STRICTLY {@link #Outgoing} - deliberately NOT the same partition as {@link #hasShipment()}, which is also
	 * true for {@link #Dropship}. Callers that must treat a dropship as a purchase-side transport (its goods never
	 * leave our own warehouse, because they never enter it) need this one.
	 */
	public boolean isOutgoing()
	{
		return this == Outgoing;
	}

}
