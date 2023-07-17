package de.metas.freighcost;

import de.metas.location.CountryAreaId;
import de.metas.location.CountryId;
import de.metas.location.ICountryAreaBL;
import de.metas.money.Money;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

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
@Builder
public class FreightCostBreak
{
	ICountryAreaBL countryAreaBL = Services.get(ICountryAreaBL.class);
	@NonNull
	FreightCostShipperId freightCostShipperId;

	@Nullable
	@Getter(AccessLevel.NONE)
	CountryId countryId;

	@Nullable
	@Getter(AccessLevel.NONE)
	CountryAreaId countryAreaId;

	@NonNull
	Money shipmentValueAmtMax;

	@NonNull
	Money freightRate;

	@Nullable
	Integer seqNo;

	public FreightCostBreak(final @NonNull FreightCostShipperId freightCostShipperId,
			@Nullable final CountryId countryId,
			@Nullable final CountryAreaId countryAreaId,
			final @NonNull Money shipmentValueAmtMax,
			final @NonNull Money freightRate,
			@Nullable final Integer seqNo)
	{
		this.freightCostShipperId = freightCostShipperId;
		this.countryId = countryId;
		this.countryAreaId = countryAreaId;
		this.shipmentValueAmtMax = shipmentValueAmtMax;
		this.freightRate = freightRate;
		this.seqNo = seqNo != null && seqNo > 0 ? seqNo : null;
	}

	public boolean isMatching(@NonNull final CountryId countryId, @NonNull final Money shipmentValueAmt)
	{
		return isCountryMatching(countryId)
				&& shipmentValueAmt.isLessThanOrEqualTo(getShipmentValueAmtMax());
	}

	public boolean isCountryMatching(@NonNull final CountryId countryId)
	{
		return Objects.equals(this.countryId, countryId) || isCountryAreaMatching(countryId);
	}

	private boolean isCountryAreaMatching(final CountryId countryId)
	{
		return countryAreaId != null && countryAreaBL.isMemberOf(countryAreaId, countryId);
	}
}
