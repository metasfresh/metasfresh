package de.schaeffer.compiere.tools;

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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_C_Country;
import org.compiere.util.Env;

import de.metas.adempiere.service.ICountryAreaBL;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

public class CountryHelper {
	public static List<I_C_Country> countries = new ArrayList<I_C_Country>();

	/**
	 * Returns countryCode for given countryId.
	 * 
	 * @param countryId
	 * @return
	 */
	static public String getCountryCode(int countryId) {
		if (countries.isEmpty())
		{
			countries = Services.get(ICountryDAO.class).getCountries(Env.getCtx());
		}
		for (I_C_Country country : countries)
		{
			if (country.getC_Country_ID() == countryId)
			{
				return country.getCountryCode();
			}
		}
		return "";
	}

	/**
	 * Returns countryName for given countryCode.
	 * 
	 * @param isoCountryCode
	 * @return
	 */
	static public String getCountryName(String isoCountryCode) {
		if (countries.isEmpty())
		{
			countries = Services.get(ICountryDAO.class).getCountries(Env.getCtx());
		}
		for (I_C_Country country : countries)
		{
			if (isoCountryCode.equalsIgnoreCase(country.getCountryCode()))
			{
				return country.getName();
			}
		}
		return "";
	}

	/**
	 * Returns id for given countryCode.
	 * 
	 * @param isoCountryCode
	 * @return
	 */
	static public int getCountryId(String isoCountryCode) {
		if (countries.isEmpty())
		{
			countries = Services.get(ICountryDAO.class).getCountries(Env.getCtx());
		}
		for (I_C_Country country : countries)
		{
			if (isoCountryCode.equalsIgnoreCase(country.getCountryCode()))
			{
				return country.getC_Country_ID();
			}
		}
		return 0;
	}

	/**
	 * Returns countryCode for given countryName.
	 * 
	 * @param countryName
	 * @return
	 */
	static public String getCountryCode(String countryName)
	{
		if (countryName == null || countryName.equals(""))
		{
			return "";
		}
		if (countries.isEmpty())
		{
			countries = Services.get(ICountryDAO.class).getCountries(Env.getCtx());
		}
		for (I_C_Country country : countries)
		{
			if (countryName.equalsIgnoreCase(country.getName()))
			{
				return country.getCountryCode();
			}
		}
		return "";
	}
	
//	 /** contains iso code of all EU countries */
//	static private Vector<String> euCountryCodes = new Vector<String>();
//	
//	static {
//		// init EU countries
//		euCountryCodes.add("BE");// belgium
//		euCountryCodes.add("BG");// bulgaria
//		euCountryCodes.add("DK");// denmark
//		euCountryCodes.add("DE");// germany
//		euCountryCodes.add("EE");// estonia
//		euCountryCodes.add("FI");// finland
//		euCountryCodes.add("FR");// france
//		euCountryCodes.add("GR");// greece
//		euCountryCodes.add("GB");// great britain
//		euCountryCodes.add("IE");// ireland
//		euCountryCodes.add("IT");// italy
//		euCountryCodes.add("LV");// Latvia
//		euCountryCodes.add("LT");// Lithuania
//		euCountryCodes.add("LU");// Luxembourg
//		euCountryCodes.add("MT");// Malta
//		euCountryCodes.add("NL");// Netherlands
//		euCountryCodes.add("AT");// Austria
//		euCountryCodes.add("PL");// Poland
//		euCountryCodes.add("PT");// Portugal
//		euCountryCodes.add("RO");// Romania
//		euCountryCodes.add("SE");// Sweden
//		euCountryCodes.add("SI");// Slovenia
//		euCountryCodes.add("SK");// Slovak Republic
//		euCountryCodes.add("ES");// Spain
//		euCountryCodes.add("CZ");// Czech Republic
//		euCountryCodes.add("HU");// Hungary
//		euCountryCodes.add("UK");// United Kingdom
//		euCountryCodes.add("CY");// Cyprus
//		// steuerrechtlich auch..
//		euCountryCodes.add("FL");// Liechtenstein
//	}
	
	/**
	 * 
	 * @param countryCode
	 * @return
	 * @deprecated Please use {@link ICountryAreaBL#isMemberOf(java.util.Properties, String, String, java.sql.Timestamp)}
	 */
	@Deprecated
	static public boolean isEUMember(String countryCode)
	{
		final Properties ctx = Env.getCtx();
		Timestamp date = SystemTime.asTimestamp();
		return Services.get(ICountryAreaBL.class).isMemberOf(ctx, ICountryAreaBL.COUNTRYAREAKEY_EU, countryCode, date);
	}

}
