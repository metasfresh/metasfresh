package de.metas.handlingunits.reservation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

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
	@Getter(AccessLevel.PRIVATE)
	@NonNull ImmutableMap<HuId, HUReservationEntry> entriesByVHUId;

	@NonNull HUReservationDocRef documentRef;
	@Nullable BPartnerId customerId;
	@NonNull Quantity reservedQtySum;

	private HUReservation(@NonNull final Collection<HUReservationEntry> entries)
	{
		Check.assumeNotEmpty(entries, "entries is not empty");

		this.entriesByVHUId = Maps.uniqueIndex(entries, HUReservationEntry::getVhuId);
		this.documentRef = extractSingleDocumentRefOrFail(entries);
		customerId = extractSingleCustomerIdOrNull(entries);
		reservedQtySum = computeReservedQtySum(entries);
	}

	public static HUReservation ofEntries(@NonNull final Collection<HUReservationEntry> entries)
	{
		return new HUReservation(entries);
	}

	private static HUReservationDocRef extractSingleDocumentRefOrFail(final Collection<HUReservationEntry> entries)
	{
		//noinspection OptionalGetWithoutIsPresent
		return entries
				.stream()
				.map(HUReservationEntry::getDocumentRef)
				.distinct()
				.reduce((documentRef1, documentRef2) -> {
					throw new AdempiereException("Entries shall be for a single document reference: " + entries);
				})
				.get();
	}

	@Nullable
	private static BPartnerId extractSingleCustomerIdOrNull(final Collection<HUReservationEntry> entries)
	{
		final ImmutableSet<BPartnerId> customerIds = entries
				.stream()
				.map(HUReservationEntry::getCustomerId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		return customerIds.size() == 1 ? customerIds.iterator().next() : null;
	}

	private static Quantity computeReservedQtySum(final Collection<HUReservationEntry> entries)
	{
		//noinspection OptionalGetWithoutIsPresent
		return entries
				.stream()
				.map(HUReservationEntry::getQtyReserved)
				.reduce(Quantity::add)
				.get();
	}

	public ImmutableSet<HuId> getVhuIds()
	{
		return entriesByVHUId.keySet();
	}

	public Quantity getReservedQtyByVhuId(@NonNull final HuId vhuId)
	{
		final HUReservationEntry entry = entriesByVHUId.get(vhuId);
		if (entry == null)
		{
			throw new AdempiereException("@NotFound@ @VHU_ID@");
		}
		return entry.getQtyReserved();
	}
}
