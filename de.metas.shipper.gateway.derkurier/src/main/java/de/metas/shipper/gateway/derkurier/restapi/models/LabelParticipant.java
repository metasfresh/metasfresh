package de.metas.shipper.gateway.derkurier.restapi.models;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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
public class LabelParticipant
{

	String stationNo;
	String name1;
	String name2;
	String name3;
	String street;
	String streetNo;
	String city;
	String zipCode;
	String country;
	String phone;
	String notice;

	@Builder
	private LabelParticipant(
			String stationNo,
			String name1,
			String name2,
			String name3,
			String street,
			String streetNo,
			String city,
			String zipCode,
			String country,
			String phone,
			String notice)
	{
		this.stationNo = Check.assumeNotEmpty(stationNo, "Parameter stationNo may not be null");
		this.name1 = Check.assumeNotEmpty(name1, "Parameter name1 may not be null");
		this.name2 = name2;
		this.name3 = name3;
		this.street = Check.assumeNotEmpty(street, "Parameter street may not be null");
		this.streetNo = Check.assumeNotEmpty(streetNo, "Parameter streetNo may not be null");
		this.city = Check.assumeNotEmpty(city, "Parameter city may not be null");
		this.zipCode = Check.assumeNotEmpty(zipCode, "Parameter street may not be null");
		this.country = Check.assumeNotEmpty(country, "Parameter country may not be null");
		this.phone = phone;
		this.notice = notice;
	}

}
