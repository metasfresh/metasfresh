/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          *
 * http://www.adempiere.org                                           *
 *                                                                    *
 * Copyright (C) Akuna Group Ltd.                                     *
 * Copyright (C) Contributors                                         *
 *                                                                    *
 * This program is free software; you can redistribute it and/or      *
 * modify it under the terms of the GNU General Public License        *
 * as published by the Free Software Foundation; either version 2     *
 * of the License, or (at your option) any later version.             *
 *                                                                    *
 * This program is distributed in the hope that it will be useful,    *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       *
 * GNU General Public License for more details.                       *
 *                                                                    *
 * You should have received a copy of the GNU General Public License  *
 * along with this program; if not, write to the Free Software        *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         *
 * MA 02110-1301, USA.                                                *
 *                                                                    *
 * Contributors:                                                      *
 *  - Michael Judd (michael.judd@akunagroup.com)                      *
 **********************************************************************/

package com.akunagroup.uk.postcode;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class Postcode implements AddressInterface
{

	// required implementation by interface
	private String Street1;
	private String Street2;
	private String Street3;
	private String Street4;
	private String City;
	private String Region;
	private String Postcode;
	private String Country;

	// UK Postcode specific
	private String Addr;	// Full address in one variable
	private String CountryCode;	// Two Letter ISO Country Code
	private String TradCounty;	// Traditional County (Region)
	private String AdminCounty;	// Administrative County
	private String LonLocation;	// London Location

	@Override
	public int size()
	{
		return 1;
	}

	public String getAddr()
	{
		return Addr;
	}

	public void setAddr(final String newAddr)
	{
		Addr = newAddr;
	}

	@Override
	public String getStreet1()
	{
		return Street1;
	}

	@Override
	public void setStreet1(final String newStreet1)
	{
		Street1 = newStreet1;
	}

	@Override
	public String getStreet2()
	{
		return Street2;
	}

	@Override
	public void setStreet2(final String newStreet2)
	{
		Street2 = newStreet2;
	}

	@Override
	public String getStreet3()
	{
		return Street3;
	}

	@Override
	public void setStreet3(final String newStreet3)
	{
		Street3 = newStreet3;
	}

	@Override
	public String getStreet4()
	{
		return Street4;
	}

	@Override
	public void setStreet4(final String newStreet4)
	{
		Street4 = newStreet4;
	}

	@Override
	public String getCity()
	{
		return City;
	}

	@Override
	public void setCity(final String newCity)
	{
		City = newCity;
	}

	@Override
	public String getRegion()
	{
		return Region;
	}

	@Override
	public void setRegion(final String newRegion)
	{
		Region = newRegion;
	}

	@Override
	public String getPostcode()
	{
		return Postcode;
	}

	@Override
	public void setPostcode(final String newPostcode)
	{
		Postcode = newPostcode;
	}

	@Override
	public String getCountry()
	{
		return Country;
	}

	@Override
	public void setCountry(final String newCountry)
	{
		Country = newCountry;
	}

	@Override
	public String getCountryCode()
	{
		return CountryCode;
	}

	@Override
	public void setCountryCode(final String newCountryCode)
	{
		CountryCode = newCountryCode;
	}

	public String getTradCounty()
	{
		return TradCounty;
	}

	public void setTradCounty(final String newTradCounty)
	{
		TradCounty = newTradCounty;
	}

	public String getAdminCounty()
	{
		return AdminCounty;
	}

	public void setAdminCounty(final String newAdminCounty)
	{
		AdminCounty = newAdminCounty;
	}

	public String getLonLocation()
	{
		return LonLocation;
	}

	public void setLonLocation(final String newLonLocation)
	{
		LonLocation = newLonLocation;
	}

}
