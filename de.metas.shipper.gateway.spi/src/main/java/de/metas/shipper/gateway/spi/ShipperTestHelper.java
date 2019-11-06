/*
 * #%L
 * de.metas.shipper.gateway.spi
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

package de.metas.shipper.gateway.spi;

import de.metas.shipper.gateway.spi.model.Address;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;

public class ShipperTestHelper
{
	@NonNull
	public static I_C_Location createLocation(@NonNull final Address pickupAddress)
	{
		final I_C_Location pickupFromLocation = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		pickupFromLocation.setAddress1(pickupAddress.getStreet1() + " " + pickupAddress.getHouseNo());
		pickupFromLocation.setAddress2(pickupAddress.getStreet2());
		pickupFromLocation.setPostal(pickupAddress.getZipCode());
		pickupFromLocation.setCity(pickupAddress.getCity());
		final I_C_Country i_c_country = InterfaceWrapperHelper.newInstance(I_C_Country.class);
		i_c_country.setCountryCode(pickupAddress.getCountry().getAlpha2());
		InterfaceWrapperHelper.save(i_c_country);
		pickupFromLocation.setC_Country(i_c_country);

		return pickupFromLocation;
	}

	@NonNull
	public static I_C_BPartner createBPartner(@NonNull final Address pickupAddress)
	{
		final I_C_BPartner pickupFromBPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		pickupFromBPartner.setName(pickupAddress.getCompanyName1());
		pickupFromBPartner.setName2(pickupAddress.getCompanyName2());
		return pickupFromBPartner;
	}
}
