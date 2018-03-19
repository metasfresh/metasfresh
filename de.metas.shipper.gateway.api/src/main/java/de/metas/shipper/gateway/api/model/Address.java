package de.metas.shipper.gateway.api.model;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.Value;

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
	String companyName1;
	String companyName2;
	String companyDepartment;

	String street1;
	String street2;
	String houseNo;
	CountryCode country;
	String zipCode;
	String city;

	//
	// External partner and partner location Id
	int bpartnerId;
	int bpartnerLocationId;

	@Builder
	private Address(
			final String companyName1,
			final String companyName2,
			final String companyDepartment,
			final String street1,
			final String street2,
			final String houseNo,
			final CountryCode country,
			final String zipCode,
			final String city,
			//
			int bpartnerId,
			int bpartnerLocationId)
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
		this.bpartnerLocationId = bpartnerLocationId;
	}
}
