package de.metas.bpartner.impexp;

import org.compiere.model.I_C_Location;
import org.compiere.model.I_I_BPartner;

import lombok.Builder;
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
final class BPartnerLocationMatchingKey
{
	int countryId;
	int regionId;
	String city;
	String address1;
	String address2;
	String postal;
	String postalAdd;

	public static BPartnerLocationMatchingKey of(final I_C_Location location)
	{
		return BPartnerLocationMatchingKey.builder()
				.countryId(location.getC_Country_ID())
				.regionId(location.getC_Region_ID())
				.city(location.getCity())
				.address1(location.getAddress1())
				.address2(location.getAddress2())
				.postal(location.getPostal())
				.postalAdd(location.getPostal_Add())
				.build();
	}

	public static BPartnerLocationMatchingKey of(final I_I_BPartner importRecord)
	{
		return BPartnerLocationMatchingKey.builder()
				.countryId(importRecord.getC_Country_ID())
				.regionId(importRecord.getC_Region_ID())
				.city(importRecord.getCity())
				.address1(importRecord.getAddress1())
				.address2(importRecord.getAddress2())
				.postal(importRecord.getPostal())
				.postalAdd(importRecord.getPostal_Add())
				.build();
	}

}
