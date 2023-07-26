package de.metas.freighcost;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import de.metas.location.CountryId;
import de.metas.money.Money;
import de.metas.shipping.ShipperId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class FreightCostShipper
{
	FreightCostShipperId id;
	ShipperId shipperId;
	LocalDate validFrom;
	@Getter(AccessLevel.NONE)
	ImmutableList<FreightCostBreak> breaks;

	@Builder
	private FreightCostShipper(
			@NonNull final FreightCostShipperId id,
			@NonNull final ShipperId shipperId,
			@NonNull final LocalDate validFrom,
			@NonNull final Collection<FreightCostBreak> breaks)
	{
		this.id = id;
		this.shipperId = shipperId;
		this.validFrom = validFrom;
		this.breaks = breaks.stream()
				.sorted(Comparator.comparing(freightCostBreak -> freightCostBreak.getShipmentValueAmtMax().toBigDecimal()))
				.collect(ImmutableList.toImmutableList());

	}

	boolean isMatching(@NonNull final ShipperId shipperId, @NonNull final LocalDate date)
	{
		return this.shipperId.equals(shipperId)
				&& this.validFrom.compareTo(date) <= 0;
	}

	public boolean isShipToCountry(@NonNull final CountryId countryId)
	{
		return breaks.stream().anyMatch(freightCostBreak -> freightCostBreak.isCountryMatching(countryId));
	}

	Optional<FreightCostBreak> getBreak(@NonNull final CountryId countryId, @NonNull final Money shipmentValueAmt)
	{
		// assumes that the breaks are ordered by getShipmentValueAmt ascending
		for (final FreightCostBreak freightCostBreak : breaks)
		{
			if (freightCostBreak.isMatching(countryId, shipmentValueAmt))
			{
				return Optional.of(freightCostBreak);
			}
		}

		return Optional.empty();
	}
}
