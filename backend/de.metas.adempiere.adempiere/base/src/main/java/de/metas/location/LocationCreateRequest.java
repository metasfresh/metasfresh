package de.metas.location;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
@Builder(toBuilder = true)
public class LocationCreateRequest
{
	@Nullable LocationId existingLocationId;

	@Nullable String address1;
	@Nullable String address2;
	@Nullable String address3;
	@Nullable String address4;

	@Nullable @With PostalId postalId;
	@Nullable String postal;
	@Nullable String postalAdd;

	@Nullable String city;

	int regionId;
	@Nullable String regionName;

	@NonNull CountryId countryId;

	@Nullable String poBox;

	public static boolean equals(@Nullable LocationCreateRequest request1, @Nullable LocationCreateRequest request2) {return Objects.equals(request1, request2);}
}
