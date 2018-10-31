package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.HashMap;
import java.util.List;

import org.compiere.util.Env;

import com.akunagroup.uk.postcode.AddressInterface;
import com.akunagroup.uk.postcode.AddressLookupInterface;

import de.metas.adempiere.service.ILocationBL;
import de.metas.util.Services;

public class MetasPostalLookup implements AddressLookupInterface
{
	private String countryCode;
	private String city;
	private HashMap<String, Object> addressData;

	@Override
	public AddressLookupInterface newInstance()
	{
		return new MetasPostalLookup();
	}

	@Override
	public int lookupPostcode(String postcode)
	{
		List<AddressInterface> list = Services.get(ILocationBL.class).lookupPostcode(Env.getCtx(), countryCode, city, postcode);
		this.addressData = new HashMap<String, Object>();
		for (AddressInterface address : list)
		{
			addressData.put(address.getPostcode(), address);
		}
		return list.size();
	}

	@Override
	public HashMap<String, Object> getAddressData()
	{
		return addressData;
	}

	@Override
	public void setClientID(String clientID)
	{
	}

	@Override
	public void setPassword(String password)
	{
	}

	@Override
	public void setServerUrl(String serverUrl)
	{
	}

	@Override
	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	@Override
	public void setCity(String city)
	{
		this.city = city;
	}

	@Override
	public boolean isRegisterLocalSupported()
	{
		return true;
	}

	@Override
	public void registerLocal(String postcode)
	{
		Services.get(ILocationBL.class).registerLocal(Env.getCtx(), countryCode, city, postcode);
	}
}
