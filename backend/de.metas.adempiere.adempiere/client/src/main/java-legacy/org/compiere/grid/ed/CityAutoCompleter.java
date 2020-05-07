/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.grid.ed;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.JTextComponent;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.db.util.AbstractPreparedStatementBlindIterator;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.compiere.swing.autocomplete.JTextComponentAutoCompleter;
import org.compiere.swing.autocomplete.ResultItem;
import org.compiere.swing.autocomplete.ResultItemSource;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.collect.ImmutableList;

/**
 * 
 * @author Cristina Ghita , www.arhipac.ro
 *
 */
class CityAutoCompleter extends JTextComponentAutoCompleter
{
	private final CitiesSource source = new CitiesSource();

	public CityAutoCompleter(final JTextComponent comp)
	{
		super(comp);

		final int maxResults = Services.get(ISysConfigBL.class).getIntValue("LOCATION_MAX_CITY_ROWS", 7);
		setMaxResults(maxResults);

		setSource(source);
		setCurrentItem(null);
	}

	@Override
	protected final void onCurrentItemChanged(final ResultItem resultItem, final ResultItem resultItemOld)
	{
		final CityVO city = (CityVO)resultItem;
		final CityVO cityOld = (CityVO)resultItemOld;
		onCurrentCityChanged(city, cityOld);
	}
	
	protected void onCurrentCityChanged(final CityVO city, final CityVO cityOld)
	{
		// nothing
	}

	public int getC_City_ID()
	{
		final CityVO city = (CityVO)getCurrentItem();
		return city != null ? city.getC_City_ID() : -1;
	}

	public int getC_Region_ID()
	{
		final CityVO city = (CityVO)getCurrentItem();
		return city != null ? city.getC_Region_ID() : -1;
	}

	public void setC_Country_ID(final int countryId)
	{
		source.setC_Country_ID(countryId);
	}

	public void setC_Region_ID(final int regionId)
	{
		source.setC_Region_ID(regionId);
	}

	/**
	 * Source of {@link CityVO}s.
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	private final class CitiesSource implements ResultItemSource
	{
		private ArrayKey lastQueryKey = null;
		private List<CityVO> lastResult = null;
		private int countryId = -1;
		private int regionId = -1;

		@Override
		public List<CityVO> query(String searchText, int limit)
		{
			final int adClientId = getAD_Client_ID();
			final int countryId = getC_Country_ID();
			final int regionId = getC_Region_ID();
			final ArrayKey queryKey = Util.mkKey(adClientId, countryId, regionId);

			if (lastQueryKey != null && lastQueryKey.equals(queryKey))
			{
				return lastResult;
			}

			final Iterator<CityVO> allCitiesIterator = retrieveAllCities(adClientId, countryId, regionId);
			List<CityVO> result = null;
			try
			{
				result = ImmutableList.copyOf(allCitiesIterator);
			}
			finally
			{
				IteratorUtils.close(allCitiesIterator);
			}

			this.lastResult = result;
			this.lastQueryKey = queryKey;
			return result;
		}

		private Iterator<CityVO> retrieveAllCities(final int adClientId, final int countryId, final int regionId)
		{
			if (countryId <= 0)
			{
				return Collections.emptyIterator();
			}

			final List<Object> sqlParams = new ArrayList<Object>();
			final StringBuilder sql = new StringBuilder(
					"SELECT cy.C_City_ID, cy.Name, cy.C_Region_ID, r.Name"
							+ " FROM C_City cy"
							+ " LEFT OUTER JOIN C_Region r ON (r.C_Region_ID=cy.C_Region_ID)"
							+ " WHERE cy.AD_Client_ID IN (0,?)");
			sqlParams.add(adClientId);
			if (regionId > 0)
			{
				sql.append(" AND cy.C_Region_ID=?");
				sqlParams.add(regionId);
			}
			if (countryId > 0)
			{
				sql.append(" AND COALESCE(cy.C_Country_ID, r.C_Country_ID)=?");
				sqlParams.add(countryId);
			}
			sql.append(" ORDER BY cy.Name, r.Name");

			return IteratorUtils.asIterator(new AbstractPreparedStatementBlindIterator<CityVO>()
			{

				@Override
				protected PreparedStatement createPreparedStatement() throws SQLException
				{
					final PreparedStatement pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
					DB.setParameters(pstmt, sqlParams);
					return pstmt;
				}

				@Override
				protected CityVO fetch(ResultSet rs) throws SQLException
				{
					final CityVO vo = new CityVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
					return vo;
				}
			});
		}

		private int getAD_Client_ID()
		{
			return Env.getAD_Client_ID(Env.getCtx());
		}

		private int getC_Country_ID()
		{
			return countryId;
		}

		public void setC_Country_ID(final int countryId)
		{
			this.countryId = countryId > 0 ? countryId : -1;
		}

		public int getC_Region_ID()
		{
			return regionId;
		}

		public void setC_Region_ID(final int regionId)
		{
			this.regionId = regionId > 0 ? regionId : -1;
		}
	}
}
