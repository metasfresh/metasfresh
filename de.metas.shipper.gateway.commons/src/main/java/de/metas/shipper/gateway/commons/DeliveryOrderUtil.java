package de.metas.shipper.gateway.commons;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.lang.IPair;
import org.compiere.model.I_C_Location;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.Address.AddressBuilder;
import de.metas.shipper.gateway.spi.model.CountryCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.commons
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

public final class DeliveryOrderUtil
{
	private DeliveryOrderUtil()
	{
	}

	public static Address.AddressBuilder prepareAddressFromLocation(@NonNull final I_C_Location location)
	{
		final AddressBuilder addressBuilder = Address.builder();

		final IPair<String, String> splitStreetAndHouseNumber1 = StringUtils
				.splitStreetAndHouseNumberOrNull(location.getAddress1());
		if (splitStreetAndHouseNumber1 != null
				&& !Check.isEmpty(splitStreetAndHouseNumber1.getLeft())
				&& !Check.isEmpty(splitStreetAndHouseNumber1.getRight()))
		{
			addressBuilder.street1(splitStreetAndHouseNumber1.getLeft());
			addressBuilder.houseNo(splitStreetAndHouseNumber1.getRight());
		}
		else
		{
			addressBuilder.street1(location.getAddress1());
			addressBuilder.houseNo("0");
		}
		return addressBuilder
				.street2(location.getAddress2())
				.zipCode(location.getPostal())
				.city(location.getCity())
				.country(createShipperCountryCode(location.getC_Country_ID()));
	}

	public static CountryCode createShipperCountryCode(final int countryId)
	{
		final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
		return CountryCode.builder()
				.alpha2(countryDAO.retrieveCountryCode2ByCountryId(countryId))
				.alpha3(countryDAO.retrieveCountryCode3ByCountryId(countryId))
				.build();
	}
}
