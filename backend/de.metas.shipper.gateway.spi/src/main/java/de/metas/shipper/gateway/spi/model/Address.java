package de.metas.shipper.gateway.spi.model;

import de.metas.bpartner.BPartnerId;
import de.metas.location.CountryCode;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.shipper.gateway.api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class Address
{
	@NonNull String companyName1;
	@Nullable String companyName2;
	@Nullable String companyDepartment;

	@Nullable String street1;
	@Nullable String street2;
	@Nullable String houseNo;
	@NonNull CountryCode country;
	@NonNull String zipCode;
	@NonNull String city;

	//
	// External partner
	// (partner location id was removed in f429b78d5b0102037ba119347cc3de723756df17)
	/**
	 * Only used for logging in {@link de.metas.shipper.gateway.commons.async.DeliveryOrderWorkpackageProcessor#printLabel} and nothing more.
	 */
	@SuppressWarnings("JavadocReference")
	@Nullable BPartnerId bpartnerId;

	@Builder
	@Jacksonized
	private Address(
			@NonNull final String companyName1,
			@Nullable final String companyName2,
			@Nullable final String companyDepartment,
			@Nullable final String street1,
			@Nullable final String street2,
			@Nullable final String houseNo,
			@NonNull final CountryCode country,
			@NonNull final String zipCode,
			@NonNull final String city,
			//
			@Nullable final BPartnerId bpartnerId)
	{
		Check.assumeNotEmpty(companyName1, "companyName1 is not empty");
		Check.assumeNotEmpty(street1, "street1 is not empty");
		Check.assumeNotNull(country, "Parameter country is not null");
		Check.assumeNotEmpty(zipCode, "zipCode is not empty");
		Check.assumeNotEmpty(city, "city is not empty");

		this.companyName1 = companyName1;
		this.companyName2 = companyName2;
		this.companyDepartment = companyDepartment;
		this.street1 = street1;
		this.street2 = street2;
		this.houseNo = houseNo;
		this.country = country;
		this.zipCode = zipCode;
		this.city = city;

		this.bpartnerId = bpartnerId;
	}
}
