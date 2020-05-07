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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_Country;
import org.compiere.model.Query;

import de.metas.adempiere.model.I_C_CountryArea;
import de.metas.adempiere.model.I_C_CountryArea_Assign;
import de.metas.adempiere.service.ICountryAreaBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

public class CountryAreaBL implements ICountryAreaBL
{
	@Override
	public boolean isMemberOf(Properties ctx, String countryAreaKey, String countryCode, Timestamp date)
	{
		final String trxName = ITrx.TRXNAME_None;

		Check.assume(date != null, "date not null");

		final I_C_Country country = retrieveCountryByCode(ctx, countryCode, trxName);
		if (country == null)
		{
			throw new AdempiereException("@NotFound@ @C_Country_ID@ (@CountryCode@: " + countryCode + ")");
		}

		final I_C_CountryArea countryArea = retrieveCountryAreaByValue(ctx, countryAreaKey, trxName);
		if (countryArea == null)
		{
			throw new AdempiereException("@NotFound@ @C_CountryArea_ID@ (@Value@: " + countryAreaKey + ")");
		}

		for (final I_C_CountryArea_Assign assignment : retrieveCountryAreaAssignments(ctx, countryArea.getC_CountryArea_ID(), trxName))
		{
			if (assignment.getC_Country_ID() != country.getC_Country_ID())
			{
				continue;
			}

			if (assignment.getValidFrom().compareTo(date) > 0)
			{
				continue;
			}

			if (assignment.getValidTo() != null && assignment.getValidTo().compareTo(date) < 0)
			{
				continue;
			}

			return true;
		}

		return false;
	}

	/**
	 * @return country area based on a search key
	 */
	@Cached(cacheName = I_C_CountryArea.Table_Name)
	public I_C_CountryArea retrieveCountryAreaByValue(@CacheCtx Properties ctx, String countryAreaKey, @CacheTrx String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_CountryArea.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_CountryArea.COLUMNNAME_Value, countryAreaKey)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumnDescending(I_C_CountryArea.COLUMNNAME_AD_Client_ID)
				.endOrderBy()
				.create()
				.first(I_C_CountryArea.class);

	}

	@Cached(cacheName = I_C_CountryArea_Assign.Table_Name + "_ByCountryArea")
	/* package */ List<I_C_CountryArea_Assign> retrieveCountryAreaAssignments(@CacheCtx final Properties ctx, final int countryAreaId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_CountryArea_Assign.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_CountryArea_Assign.COLUMNNAME_C_CountryArea_ID, countryAreaId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_CountryArea_Assign.class);
	}
	
	@Override
	public List<I_C_CountryArea_Assign> retrieveCountryAreaAssignments(I_C_CountryArea countryArea, int countryId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(countryArea);
		final String trxName = InterfaceWrapperHelper.getTrxName(countryArea);
		final int countryAreaId = countryArea.getC_CountryArea_ID();

		final List<I_C_CountryArea_Assign> result = new ArrayList<I_C_CountryArea_Assign>();
		for (I_C_CountryArea_Assign assignment : retrieveCountryAreaAssignments(ctx, countryAreaId, trxName))
		{
			if (assignment.getC_Country_ID() == countryId)
			{
				result.add(assignment);
			}
		}
		
		return result;
	}

	/**
	 * Returns the country based on the ISO code.
	 */
	@Cached(cacheName = I_C_Country.Table_Name)
	public I_C_Country retrieveCountryByCode(@CacheCtx Properties ctx, String countryCode, @CacheTrx String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Country.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Country.COLUMNNAME_CountryCode, countryCode)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_C_Country.class);

	}

	@Override
	public void validate(I_C_CountryArea_Assign assignment)
	{
		final I_C_CountryArea countryArea = assignment.getC_CountryArea();
		for (I_C_CountryArea_Assign instance : retrieveCountryAreaAssignments(countryArea, assignment.getC_Country_ID()))
		{
			if (isTimeConflict(assignment, instance))
			{
				throw new AdempiereException("Period overlaps with existing period: " + instance.getValidFrom()
						+ ((instance.getValidTo() == null) ? "" : (" to " + instance.getValidTo()))
						+ " for the country " + assignment.getC_Country() + " in area " + countryArea.getName());
			}
		}
	}
	
	/**
	 * 
	 * @param newEntry
	 * @param oldEntry
	 * @return true if <code>newEntry</code>'s and <code>oldEntry</code>'s date/time intervals are overlapping
	 */
	protected boolean isTimeConflict(I_C_CountryArea_Assign newEntry, I_C_CountryArea_Assign oldEntry)
	{
		if (newEntry.equals(oldEntry))
		{
			return false;
		}
		if (newEntry.getValidFrom().compareTo(oldEntry.getValidFrom()) <= 0)
		{
			if ((newEntry.getValidTo() == null) || (newEntry.getValidTo().compareTo(oldEntry.getValidFrom()) > 0))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if ((oldEntry.getValidTo() == null) || (oldEntry.getValidTo().compareTo(newEntry.getValidFrom()) > 0))
			{
				return true;
			}
			else
			{
				return false;
			}
		}

	}

}
