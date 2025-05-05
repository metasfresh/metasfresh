/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner.composite;

import de.metas.location.LocationId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
public class BPartnerLocationAddressPart
{
	@Nullable @With LocationId existingLocationId;
	@Nullable String address1;
	@Nullable String address2;
	@Nullable String address3;
	@Nullable String address4;
	@Nullable String poBox;
	@Nullable String postal;
	@Nullable String city;
	@Nullable String region;
	@Nullable String district;
	@Nullable String countryCode;
	@Nullable String countryName;

	public static boolean equals(@Nullable final BPartnerLocationAddressPart o1, @Nullable final BPartnerLocationAddressPart o2)
	{
		return Objects.equals(o1, o2);
	}

	public boolean isOnlyExistingLocationIdSet()
	{
		return existingLocationId != null
				&& Check.isBlank(address1)
				&& Check.isBlank(address2)
				&& Check.isBlank(address3)
				&& Check.isBlank(address4)
				&& Check.isBlank(poBox)
				&& Check.isBlank(postal)
				&& Check.isBlank(city)
				&& Check.isBlank(region)
				&& Check.isBlank(district)
				&& Check.isBlank(countryCode)
				&& Check.isBlank(countryName);
	}
}
