package de.metas.handlingunits.reservation;

import java.util.Map;
import java.util.Set;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.HuId;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class HUReservation
{
	@NonNull
	HUReservationDocRef documentRef;

	@Nullable
	BPartnerId customerId;

	@Getter(AccessLevel.PRIVATE)
	ImmutableMap<HuId, Quantity> reservedQtyByVhuIds;

	Quantity reservedQtySum;

	@Builder(toBuilder = true)
	private HUReservation(
			@NonNull final HUReservationDocRef documentRef,
			@Nullable final BPartnerId customerId,
			@NonNull @Singular final Map<HuId, Quantity> reservedQtyByVhuIds)
	{
		Check.assumeNotEmpty(reservedQtyByVhuIds, "reservedQtyByVhuIds is not empty");

		this.documentRef = documentRef;
		this.customerId = customerId;
		this.reservedQtyByVhuIds = ImmutableMap.copyOf(reservedQtyByVhuIds);

		this.reservedQtySum = reservedQtyByVhuIds.values()
				.stream()
				.reduce(Quantity::add)
				.get();
	}

	public Set<HuId> getVhuIds()
	{
		return reservedQtyByVhuIds.keySet();
	}

	public Quantity getReservedQtyByVhuId(@NonNull final HuId vhuId)
	{
		final Quantity reservedQty = reservedQtyByVhuIds.get(vhuId);
		if (reservedQty == null)
		{
			throw new AdempiereException("@NotFound@ @VHU_ID@");
		}
		return reservedQty;
	}
}
