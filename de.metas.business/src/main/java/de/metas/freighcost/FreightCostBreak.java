package de.metas.freighcost;

import javax.annotation.Nullable;

import de.metas.location.CountryId;
import de.metas.money.Money;
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
@Builder
public class FreightCostBreak
{
	@NonNull
	FreightCostShipperId freightCostShipperId;

	@Nullable
	@Getter(AccessLevel.NONE)
	CountryId countryId;

	@NonNull
	Money shipmentValueAmtMax;

	@NonNull
	Money freightRate;

	boolean isMatching(@NonNull final CountryId countryId, @NonNull final Money shipmentValueAmt)
	{
		return isCountryMatching(countryId)
				&& shipmentValueAmt.isLessThanOrEqualTo(getShipmentValueAmtMax());
	}

	boolean isCountryMatching(@NonNull final CountryId countryId)
	{
		return this.countryId == null || this.countryId.equals(countryId);
	}
}
