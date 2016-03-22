/**
 * 
 */
package org.adempiere.webui.window;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.StringUtils;
import org.adempiere.webui.component.AbstractAutoCompleter;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;

import de.metas.adempiere.gui.search.GeodbObject;

/**
 * @author cg
 * 
 */
public class WGeodbAutoCompleter extends AbstractAutoCompleter
{

	private static final long serialVersionUID = -5292616386133728825L;

	@Override
	public void onEvent(final Event event) throws Exception
	{
		// nothing to do
	}

	@Override
	protected boolean updateListData()
	{
		// clearing list
		removeAllItems();
		setDict(null);
		setDescription(null);
		//
		final String search = getSearchText();
		Object userObject = getUserOject();
		if (userObject != null && !isMatching(userObject, search))
		{
			setUserObject(null);
		}
		//
		final ArrayList<Object> list = new ArrayList<Object>();
		boolean truncated = false;

		//
		// Load list from database
		final ArrayList<Object> params = new ArrayList<Object>();
		final String sql = getSelectSQL(search, params);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				if (i > 0 && i > m_maxItems)
				{
					list.add(AbstractAutoCompleter.ITEM_More);
					truncated = true;
					break;
				}
				final Object o = fetchUserObject(rs);
				list.add(o);
				i++;
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// if there is no items on the list return false, to not show the pop-up
		if (list.isEmpty())
		{
			setStyle("background:red");
			return false;
		}

		//
		final String[] geodbValues = new String[list.size()];
		final String[] geodbDesc = new String[list.size()];
		int i = 0;
		for (final Object o : list)
		{
			if (o instanceof GeodbObject)
			{
				final GeodbObject gdbo = (GeodbObject)o;
				appendItem(gdbo.getStringRepresentation(), o);
				geodbValues[i] = gdbo.getStringRepresentation();
				geodbDesc[i] = gdbo.getCountryName();
			}
			else if (AbstractAutoCompleter.ITEM_More.equals(o.toString()))
			{
				appendItem(AbstractAutoCompleter.ITEM_More, o);
				geodbValues[i] = AbstractAutoCompleter.ITEM_More;
				geodbDesc[i] = AbstractAutoCompleter.ITEM_More;
			}
			i++;
		}
		setDict(geodbValues);
		setDescription(geodbDesc);

		// If the list has only one item, but that item is not equals with
		// m_city
		// return false to not show any popup
		userObject = getUserOject();
		if (!truncated && list.size() == 1 && userObject != null
				&& list.get(0).equals(userObject))
		{
			setStyle("background:red");
			log.trace("nothing to do 1");
			return false;
		}

		// if first list item matched then select it
		if (isMatching(list.get(0), search))
		{
			setUserObject(list.get(0));
			return true;
		}

		// List updated, show we need to show the pop-up
		setStyle(defaultStyle);
		return true;
	}

	@Override
	protected String getSelectSQL(final String search, final List<Object> params)
	{
		String searchSQL = StringUtils.stripDiacritics(search);
		if (!searchSQL.endsWith("%"))
		{
			searchSQL += "%";
		}

		final String sql_strRep = "co.city||', '||co.zip||' - '||COALESCE(c_trl.Name, c.Name)";

		final String sql =
				"SELECT co.geodb_loc_id, co.city, co.city_7bitlc, co.zip, co.lon, co.lat"
						+ ", co.c_country_id"
						+ ", COALESCE(c_trl.Name, c.Name) as CountryName"
						+ ", " + sql_strRep + " as string_rep"
						+ " FROM geodb_coordinates co"
						+ " LEFT OUTER JOIN C_Country c ON (c.c_country_id=co.c_country_id)"
						+ " LEFT OUTER JOIN C_Country_Trl c_trl ON (c_trl.c_country_id=co.c_country_id AND c_trl.AD_Language=?)"
						+ " WHERE co.city_7bitlc LIKE UPPER(?)"
						+ " OR UPPER(co.city) LIKE UPPER(?)"
						+ " OR UPPER(co.zip) LIKE UPPER(?)"
						+ " OR UPPER(" + sql_strRep + ") LIKE UPPER(?)"
						+ " ORDER BY co.city";
		params.add(Env.getAD_Language(Env.getCtx()));
		params.add(searchSQL); // city_7bitlc
		params.add(searchSQL); // city
		params.add(searchSQL); // zip
		params.add(searchSQL); // string representation
		//
		return sql;
	}

	@Override
	protected Object fetchUserObject(final ResultSet rs) throws SQLException
	{
		final int geodb_loc_id = rs.getInt("geodb_loc_id");
		final String city = rs.getString("city");
		final String zip = rs.getString("zip");
		final double lon = rs.getDouble("lon");
		final double lat = rs.getDouble("lat");
		final GeodbObject o = new GeodbObject(geodb_loc_id, city, zip, lon, lat);
		o.setC_Country_ID(rs.getInt("c_country_id"));
		o.setCountryName(rs.getString("CountryName"));
		o.setCity_7bitlc(rs.getString("city_7bitlc"));
		o.setStringRepresentation(rs.getString("string_rep"));
		return o;
	}

}
