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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.akunagroup.uk.postcode.AddressInterface;

import de.metas.adempiere.model.I_C_Postal;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.adempiere.service.ILocationBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.dpd.model.I_DPD_Route;
import de.metas.logging.LogManager;

public class LocationBL implements ILocationBL
{
	private final Logger log = LogManager.getLogger(getClass());

	private static final String SQL_WhereClause_C_Postal_Postal =
			"LPAD(" + I_C_Postal.COLUMNNAME_Postal + ", 20, '0')=LPAD(?, 20, '0')";
	private static final String SQL_WhereClause_C_Postal_ByPostal = I_C_Postal.COLUMNNAME_C_Country_ID + "=?"
			+ " AND " + SQL_WhereClause_C_Postal_Postal;
	private static final String SQL_WhereClause_C_Postal_ByPostalOrCity =
			"(" + I_C_Postal.COLUMNNAME_C_Country_ID + "=? OR ? <= 0)"
					+ " AND ( (" + SQL_WhereClause_C_Postal_Postal + ") OR " + " UPPER(" + I_C_Postal.COLUMNNAME_City + ") like UPPER(?))";
	private static final String SQL_WhereClause_C_Postal_ByPostalAndCity =
			"(" + I_C_Postal.COLUMNNAME_C_Country_ID + "=? OR ? <= 0)"
					+ " AND ( (" + SQL_WhereClause_C_Postal_Postal + ") AND " + " UPPER(" + I_C_Postal.COLUMNNAME_City + ") like UPPER(?))";

	private static final String SQL_WhereClause_DPD_Route_ByCountryAndPostal =
			I_DPD_Route.COLUMNNAME_CountryCode + "=?"
					+ " AND " + I_DPD_Route.COLUMNNAME_BeginPostCode + " IS NOT NULL"
					+ " AND LPAD(?, 20, '0') BETWEEN LPAD(" + I_DPD_Route.COLUMNNAME_BeginPostCode + ", 20, '0')"
					+ " AND LPAD(COALESCE(" + I_DPD_Route.COLUMNNAME_EndPostCode + ", " + I_DPD_Route.COLUMNNAME_BeginPostCode + "), 20, '0')";

	@Override
	public void validatePostal(I_C_Location location) throws AdempiereException
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(location);
		validatePostal(ctx, new LocationAddressAdapter(location));
		location.setIsPostalValidated(true);
	}

	@Override
	public void validatePostal(Properties ctx, com.akunagroup.uk.postcode.AddressInterface address)
	{
		if (Check.isEmpty(address.getPostcode(), true))
		{
			log.info("Empty postal code found [IGNORED]");
			return;
		}

		String postal = address.getPostcode().trim();
		log.debug("Checking: " + postal);

		if (checkOnCPostal(ctx, address.getCountryCode(), postal))
		{

			return; // OK
		}

		if (!checkOnDPDRoute(address.getCountryCode(), postal))
		{
			throw new AdempiereException("@NotFound@ @Postal@" + ": '" + postal + "'");
		}

		addToCPostal(ctx, address);
	}

	@Override
	public List<com.akunagroup.uk.postcode.AddressInterface> lookupPostcode(Properties ctx, String countryCode, String city, String postal)
	{
		List<com.akunagroup.uk.postcode.AddressInterface> list = new ArrayList<com.akunagroup.uk.postcode.AddressInterface>();
		for (I_C_Postal cpostal : retrievePostals(ctx, countryCode, city, postal, null))
		{
			list.add(new CPostalAddressAdapter(cpostal));
		}
		return list;
	}
	
	@Override
	public I_C_Postal fetchPostalIfUnique(
			final Properties ctx, 
			final String countryCode, 
			String city, 
			final String postal, 
			final String trxName)
	{
		final I_C_Country country = getCountryByCode(ctx, countryCode);
		// if (country == null)
		// throw new AdempiereException("@NotFound@ @C_Country_ID@ (@CountryCode@:" + countryCode + ")");

		if (city != null)
			city = city.trim();
		if (city != null && city.isEmpty())
			city = null;

		// if the user search for multiple cities, there is no point to try an exact match
		if ( city != null && city.endsWith("%") && Check.isEmpty(postal, true))
		{
			return null;
		}

		// we are interested only by a perfect match:
		if ( country != null && (city != null ||  postal != null))
		{
			final List<Object> params = new ArrayList<Object>();
			params.add(country.getC_Country_ID());
			params.add(country.getC_Country_ID());
			params.add(postal); // don't care about postal now
			params.add(city);

			final List<I_C_Postal> result = new Query(ctx, I_C_Postal.Table_Name, SQL_WhereClause_C_Postal_ByPostalOrCity, trxName)
					.setParameters(params)
					.setOnlyActiveRecords(true)
					.setApplyAccessFilterRW(false)
					.setOrderBy(I_C_Postal.COLUMNNAME_City)
					.list(I_C_Postal.class);
			return (result.isEmpty() || result.size() > 1) ? null : result.get(0);
		}
		return null;
	}

	public List<I_C_Postal> retrievePostals(Properties ctx, String countryCode, String city, String postal, String trxName)
	{
		final I_C_Country country = getCountryByCode(ctx, countryCode);
		// if (country == null)
		// throw new AdempiereException("@NotFound@ @C_Country_ID@ (@CountryCode@:" + countryCode + ")");

		if (city != null)
			city = city.trim();
		if (city != null && city.isEmpty())
			city = null;
		
		if (city != null && !city.endsWith("%"))
		{
			city += "%";
		}

		if (postal != null)
			postal = postal.trim();
		if (postal != null && postal.isEmpty())
			postal = null;

		//
		// First, try a perfect match:
		if (postal != null && city != null)
		{
			final List<Object> params = new ArrayList<Object>();
			params.add(country == null ? -1 : country.getC_Country_ID());
			params.add(country == null ? -1 : country.getC_Country_ID());
			params.add(postal);
			params.add(city);

			final List<I_C_Postal> result = new Query(ctx, I_C_Postal.Table_Name, SQL_WhereClause_C_Postal_ByPostalAndCity, trxName)
					.setParameters(params)
					.setOnlyActiveRecords(true)
					.setApplyAccessFilterRW(false) // rw=false
					.setOrderBy(I_C_Postal.COLUMNNAME_City
							+ ", " + I_C_Postal.COLUMNNAME_Postal)
					.list(I_C_Postal.class);
			if (!result.isEmpty())
			{
				return result;
			}
		}

		//
		// Fetch all records with given postal OR city
		{
			final List<Object> params = new ArrayList<Object>();
			params.add(country == null ? -1 : country.getC_Country_ID());
			params.add(country == null ? -1 : country.getC_Country_ID());
			params.add(postal);
			params.add(city);

			final List<I_C_Postal> result = new Query(ctx, I_C_Postal.Table_Name, SQL_WhereClause_C_Postal_ByPostalOrCity, trxName)
					.setParameters(params)
					.setOnlyActiveRecords(true)
					.setApplyAccessFilterRW(false) // rw=false
					.setOrderBy(I_C_Postal.COLUMNNAME_City
							+ ", " + I_C_Postal.COLUMNNAME_Postal)
					.list(I_C_Postal.class);
			return result;
		}

	}

	private boolean checkOnCPostal(Properties ctx, String countryCode, String postal)
	{
		log.debug("Checking: postal=" + postal + ", countryCode=" + countryCode);

		I_C_Country country = getCountryByCode(ctx, countryCode);
		if (country == null)
			throw new AdempiereException("@NotFound@ @C_Country_ID@ (@CountryCode@:" + countryCode + ")");

		boolean found = new Query(ctx, I_C_Postal.Table_Name, SQL_WhereClause_C_Postal_ByPostal, null)
				.setParameters(country.getC_Country_ID(), postal)
				.setOnlyActiveRecords(true)
				.setApplyAccessFilterRW(false) // rw=false
				.match();
		log.debug("Found: " + found);
		return found;
	}

	@Override
	public boolean checkOnDPDRoute(String countryCode, String postal)
	{
		if (postal == null)
			return false;

		postal = postal.trim();
		log.debug("Checking: postal=" + postal + ", countryCode=" + countryCode);

		boolean found = new Query(Env.getCtx(), I_DPD_Route.Table_Name, SQL_WhereClause_DPD_Route_ByCountryAndPostal, null)
				.setParameters(countryCode, postal)
				.match();
		log.debug("Found: " + found);
		return found;
	}

	private void addToCPostal(Properties ctx, com.akunagroup.uk.postcode.AddressInterface address)
	{
		String trxName = null;
		int countryId = -1;
		int regionId = -1;
		int cityId = -1;
		I_C_Location location = null;
		if (address instanceof LocationAddressAdapter)
		{
			location = ((LocationAddressAdapter)address).getC_Location();
			trxName = InterfaceWrapperHelper.getTrxName(location);
			countryId = location.getC_Country_ID();
			regionId = location.getC_Region_ID();
			cityId = location.getC_City_ID();
		}

		if (countryId <= 0)
		{
			I_C_Country country = getCountryByCode(ctx, address.getCountryCode());
			if (country == null)
			{
				throw new AdempiereException("@NotFound@ @C_Country_ID@ (@CountryCode@:" + address.getCountryCode());
			}
			countryId = country.getC_Country_ID();
		}

		final I_C_Postal postal = InterfaceWrapperHelper.create(ctx, I_C_Postal.class, trxName);
		postal.setC_Country_ID(countryId);
		if (regionId > 0)
			postal.setC_Region_ID(regionId);
		postal.setRegionName(address.getRegion());
		if (cityId > 0)
			postal.setC_City_ID(cityId);
		postal.setCity(address.getCity());
		// postal.setAddress1(address.getStreet1());
		// postal.setAddress2(address.getStreet2());
		// postal.setAddress3(address.getStreet3());
		// postal.setAddress4(address.getStreet4());
		postal.setPostal(address.getPostcode());
		// postal.setPostal_Add(location.getPostal_Add());
		InterfaceWrapperHelper.save(postal);
		log.debug("Created a new C_Postal record for " + address + ": " + postal);

		if (location != null)
		{
			location.setIsPostalValidated(true);
		}
	}

	@Override
	@Cached
	public I_C_Country getCountryByCode(@CacheCtx Properties ctx, String countryCode)
	{
		if (countryCode == null)
			return null;

		for (I_C_Country country : Services.get(ICountryDAO.class).getCountries(ctx))
		{
			if (countryCode.equals(country.getCountryCode()))
			{
				return country;
			}
		}
		return null;
	}

	public static class LocationAddressAdapter implements com.akunagroup.uk.postcode.AddressInterface
	{
		private final Properties ctx;
		private final I_C_Location location;

		public LocationAddressAdapter(I_C_Location location)
		{
			assert location != null;

			this.ctx = InterfaceWrapperHelper.getCtx(location);
			this.location = location;
		}

		public I_C_Location getC_Location()
		{
			return location;
		}

		@Override
		public String getCity()
		{
			return location.getCity();
		}

		@Override
		public String getCountry()
		{
			if (location.getC_Country_ID() <= 0)
				return null;
			return Services.get(ICountryDAO.class).get(ctx, location.getC_Country_ID()).getName();
		}

		@Override
		public String getCountryCode()
		{
			if (location.getC_Country_ID() <= 0)
				return null;
			return Services.get(ICountryDAO.class).get(ctx, location.getC_Country_ID()).getCountryCode();
		}

		@Override
		public String getPostcode()
		{
			return location.getPostal();
		}

		@Override
		public String getRegion()
		{
			return location.getRegionName();
		}

		@Override
		public String getStreet1()
		{
			return location.getAddress1();
		}

		@Override
		public String getStreet2()
		{
			return location.getAddress2();
		}

		@Override
		public String getStreet3()
		{
			return location.getAddress3();
		}

		@Override
		public String getStreet4()
		{
			return location.getAddress4();
		}

		@Override
		public void setCity(String newCity)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setCountry(String newCountry)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setCountryCode(String newCountryCode)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setPostcode(String newPostcode)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setRegion(String newRegion)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setStreet1(String newStreet1)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setStreet2(String newStreet2)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setStreet3(String newStreet3)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setStreet4(String newStreet4)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public int size()
		{
			return 1; // WTF is this method?
		}

		@Override
		public String toString()
		{
			return getClass().getSimpleName() + "[" + location.toString() + "]";
		}
	}

	public static class CPostalAddressAdapter implements com.akunagroup.uk.postcode.AddressInterface
	{
		private final Properties ctx;
		private final I_C_Postal cpostal;

		public CPostalAddressAdapter(I_C_Postal cpostal)
		{
			assert cpostal != null;
			this.ctx = InterfaceWrapperHelper.getCtx(cpostal);
			this.cpostal = cpostal;
		}

		public I_C_Postal getC_Postal()
		{
			return cpostal;
		}

		@Override
		public String getCity()
		{
			return cpostal.getCity();
		}

		@Override
		public String getCountry()
		{
			if (cpostal.getC_Country_ID() <= 0)
				return null;
			return Services.get(ICountryDAO.class).get(ctx, cpostal.getC_Country_ID()).getName();
		}

		@Override
		public String getCountryCode()
		{
			if (cpostal.getC_Country_ID() <= 0)
				return null;
			return Services.get(ICountryDAO.class).get(ctx, cpostal.getC_Country_ID()).getCountryCode();
		}

		@Override
		public String getPostcode()
		{
			return cpostal.getPostal();
		}

		@Override
		public String getRegion()
		{
			return cpostal.getRegionName();
		}

		@Override
		public String getStreet1()
		{
			return null;
		}

		@Override
		public String getStreet2()
		{
			return null;
		}

		@Override
		public String getStreet3()
		{
			return null;
		}

		@Override
		public String getStreet4()
		{
			return null;
		}

		@Override
		public void setCity(String newCity)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setCountry(String newCountry)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setCountryCode(String newCountryCode)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setPostcode(String newPostcode)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setRegion(String newRegion)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setStreet1(String newStreet1)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setStreet2(String newStreet2)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setStreet3(String newStreet3)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public void setStreet4(String newStreet4)
		{
			throw new java.lang.IllegalStateException("Not allowed");
		}

		@Override
		public int size()
		{
			return 1; // WTF is this method?
		}

		@Override
		public String toString()
		{
			return getClass().getSimpleName() + "[" + cpostal.toString() + "]";
		}
	}

	@Override
	public void registerLocal(Properties ctx, String countryCode, String city, String postcode)
	{
		final String trxName = null;

		I_C_Country country = getCountryByCode(ctx, countryCode);
		if (country == null)
		{
			throw new AdempiereException("@NotFound@ @C_Country_ID@ (@CountryCode@:" + countryCode + ")");
		}
		final int countryId = country.getC_Country_ID();

		final I_C_Postal postal = InterfaceWrapperHelper.create(ctx, I_C_Postal.class, trxName);
		postal.setC_Country_ID(countryId);
		postal.setCity(city);
		postal.setPostal(postcode);
		postal.setIsManual(true);
		InterfaceWrapperHelper.save(postal);
		log.debug("Registered manual C_Postal: " + postal);
	}

	@Override
	public String toString(I_C_Location location)
	{
		return location.toString();
	}

	@Override
	public I_C_Postal getC_Postal(AddressInterface address)
	{
		if (address == null)
		{
			return null;
		}
		else if (address instanceof CPostalAddressAdapter)
		{
			final CPostalAddressAdapter postalAddr = (CPostalAddressAdapter)address;
			return postalAddr.getC_Postal();
		}
		else
		{
			log.warn("Cannot determine C_Postal from " + address);
			return null;
		}
	}

	@Override
	public String mkAddress(final I_C_Location location)
	{
		final String bPartnerBlock = null;
		final String userBlock = null;
		return mkAddress(location, bPartnerBlock, userBlock);
	}
	
	@Override
	public String mkAddress(final I_C_Location location, String bPartnerBlock, String userBlock)
	{
		final Properties ctx = Env.getCtx();

		final I_C_Country countryLocal = Services.get(ICountryDAO.class).getDefault(ctx);
		final boolean isLocalAddress = location.getC_Country_ID() == countryLocal.getC_Country_ID();

		final String addr = mkAddress(location, isLocalAddress, bPartnerBlock, userBlock);
		return addr;
	}

	public String mkAddress(I_C_Location location, boolean isLocalAddress, String bPartnerBlock, String userBlock)
	{
		final de.metas.adempiere.model.I_C_Location locationEx = InterfaceWrapperHelper.create(location, de.metas.adempiere.model.I_C_Location.class);
		return new AddressBuilder().buildAddressString(locationEx, isLocalAddress, bPartnerBlock, userBlock);
	}

	@Override
	public I_C_Location duplicate(final org.compiere.model.I_C_Location location)
	{
		Check.assumeNotNull(location, "location not null");
		
		final I_C_Location locationNew = InterfaceWrapperHelper.newInstance(I_C_Location.class, location);
		InterfaceWrapperHelper.copyValues(location, locationNew);
		InterfaceWrapperHelper.save(locationNew);
		return locationNew;
	}
}
