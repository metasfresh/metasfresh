/**
 *
 */
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.language.ILanguageDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Language;
import org.compiere.model.I_AD_User_SaveCustomInfo;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Region;
import org.compiere.model.MCountry;
import org.compiere.model.Query;
import org.compiere.model.X_AD_User_SaveCustomInfo;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.service.ICountryCustomInfo;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.adempiere.util.CacheCtx;
import de.metas.logging.LogManager;

/**
 * @author cg
 *
 */
public class CountryDAO implements ICountryDAO
{

	/** Country Cache */
	private CCache<String, I_C_Country> s_countries = null;
	/** Default Country */
	private I_C_Country s_default = null;
	/** Static Logger */
	private static Logger s_log = LogManager.getLogger(CountryDAO.class);

	@Override
	public ICountryCustomInfo retriveCountryCustomInfo(Properties ctx, String trxName)
	{
		final I_C_Country country = getDefault(ctx);

		I_AD_User_SaveCustomInfo info = new Query(Env.getCtx(), I_AD_User_SaveCustomInfo.Table_Name, I_AD_User_SaveCustomInfo.COLUMNNAME_C_Country_ID + " = ?", trxName)
				.setParameters(country.getC_Country_ID())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(X_AD_User_SaveCustomInfo.COLUMNNAME_Created + " DESC")
				.first(I_AD_User_SaveCustomInfo.class);
		if (info != null)
		{
			return new CountryCustomInfoImpl(info.getCaptureSequence(), info.getC_Country_ID());
		}

		return null;
	}

	@Override
	public I_C_Country getDefault(Properties ctx)
	{
		if (s_countries == null || s_countries.size() == 0)
			loadAllCountries(ctx);
		return s_default;
	} // get

	@Override
	public I_C_Country get(Properties ctx, int C_Country_ID)
	{
		if (s_countries == null || s_countries.size() == 0)
			loadAllCountries(ctx);
		String key = String.valueOf(C_Country_ID);
		I_C_Country c = s_countries.get(key);
		if (c != null)
			return c;
		c = new MCountry(ctx, C_Country_ID, null);
		if (c.getC_Country_ID() == C_Country_ID)
		{
			s_countries.put(key, c);
			return c;
		}
		return null;
	} // get

	@Override
	public List<I_C_Country> getCountries(Properties ctx)
	{
		if (s_countries == null || s_countries.size() == 0)
			loadAllCountries(ctx);

		List<I_C_Country> retValue = new ArrayList<I_C_Country>();
		retValue.addAll(s_countries.values());

		Collections.sort(retValue, new MCountry(ctx, 0, null));

		return retValue;
	} // getCountries

	/**
	 * Load Countries. Set Default Language to Client Language
	 *
	 * @param ctx context
	 */
	private void loadAllCountries(Properties ctx)
	{
		final I_AD_Client client = Services.get(IClientDAO.class).retriveClient(ctx);
		final I_AD_Language lang = Services.get(ILanguageDAO.class).retrieveByAD_Language(ctx, client.getAD_Language());
		MCountry usa = null;
		//
		s_countries = new CCache<String, I_C_Country>("C_Country", 250, 0);
		String sql = "SELECT * FROM C_Country WHERE IsActive='Y'";
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				MCountry c = new MCountry(ctx, rs, null);
				s_countries.put(String.valueOf(c.getC_Country_ID()), c);
				// Country code of Client Language
				if (lang != null && lang.getCountryCode().equals(c.getCountryCode()))
					s_default = c;
				if (c.getC_Country_ID() == 100) // USA
					usa = c;
			}
		}
		catch (SQLException e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
		}
		if (s_default == null)
			s_default = usa;
		
		s_log.debug("#" + s_countries.size() + " - Default=" + s_default);
	} // loadAllCountries

	@Override
	@Cached(cacheName = I_C_Region.Table_Name + "#by#C_Country_ID")
	public List<I_C_Region> retrieveRegions(@CacheCtx final Properties ctx, final int countryId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Region.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Region.COLUMNNAME_C_Country_ID, countryId)
				//
				.orderBy()
				.addColumn(I_C_Region.COLUMNNAME_Name)
				.addColumn(I_C_Region.COLUMNNAME_C_Region_ID)
				.endOrderBy()
				//
				.create()
				.listImmutable(I_C_Region.class);

	}
}
