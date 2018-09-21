/**
 * 
 */
package de.metas.adempiere.gui.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
import java.util.List;

import javax.swing.text.JTextComponent;

import org.compiere.apps.search.FieldAutoCompleter;
import org.compiere.util.Env;

import de.metas.util.StringUtils;

/**
 * @author teo.sarca@gmail.com
 *
 */
public class GeodbAutoCompleter extends FieldAutoCompleter
{

	public GeodbAutoCompleter(JTextComponent comp)
	{
		super(comp);
	}

	@Override
	protected String getSelectSQL(String search, int caretPosition, List<Object> params)
	{
		if (caretPosition > 0 && caretPosition < search.length())
		{
			search = new StringBuffer(search).insert(caretPosition, "%").toString();
		}
		String searchSQL = StringUtils.stripDiacritics(search.toUpperCase());
		if (!searchSQL.endsWith("%"))
		{
			searchSQL += "%";
		}
		
		final String sql_strRep = "co.city||', '||co.zip||' - '||COALESCE(c_trl.Name, c.Name)"; 
		
		final String sql =
			"SELECT co.geodb_loc_id, co.city, co.city_7bitlc, co.zip, co.lon, co.lat"
			+", co.c_country_id"
			+", COALESCE(c_trl.Name, c.Name) as CountryName"
			+", "+sql_strRep+" as string_rep"
			+" FROM geodb_coordinates co"
			+" LEFT OUTER JOIN C_Country c ON (c.c_country_id=co.c_country_id)"
			+" LEFT OUTER JOIN C_Country_Trl c_trl ON (c_trl.c_country_id=co.c_country_id AND c_trl.AD_Language=?)"
			+" WHERE co.city_7bitlc LIKE ?"
			+" OR UPPER(co.city) LIKE ?"
			+" OR UPPER(co.zip) LIKE ?"
			+" OR UPPER("+sql_strRep+") LIKE ?"
			+" ORDER BY co.city"
			;
		params.add(Env.getAD_Language(Env.getCtx()));
		params.add(searchSQL); // city_7bitlc
		params.add(searchSQL); // city
		params.add(searchSQL); // zip
		params.add(searchSQL); // string representation
		//
		return sql;
	}
	
	@Override
	protected Object fetchUserObject(ResultSet rs) throws SQLException
	{
		int geodb_loc_id = rs.getInt("geodb_loc_id");
		String city = rs.getString("city");
		String zip = rs.getString("zip");
		double lon = rs.getDouble("lon");
		double lat = rs.getDouble("lat");
		GeodbObject o = new GeodbObject(geodb_loc_id, city, zip, lon, lat);
		o.setC_Country_ID(rs.getInt("c_country_id"));
		o.setCountryName(rs.getString("CountryName"));
		o.setCity_7bitlc(rs.getString("city_7bitlc"));
		o.setStringRepresentation(rs.getString("string_rep"));
		return o;
	}
	
	/*
	protected String convertUserObjectForTextField(Object userObject)
	{
		if (! (userObject instanceof GeodbObject))
			return super.convertUserObjectForTextField(userObject);
		//
		GeodbObject geodb = (GeodbObject)userObject;
		String matchedField = geodb.getMatchedField();
		if ("city".equals(matchedField))
		{
			return geodb.getCity();
		}
		else if ("zip".equals(matchedField))
		{
			return geodb.getZip();
		}
		else
		{
			return userObject == null ? "" : userObject.toString();
		}
	}
	*/

	@Override
	protected boolean isMatching(Object userObject, String search)
	{
		if (! (userObject instanceof GeodbObject))
			return super.isMatching(userObject, search);
		
		final String searchFixed = StringUtils.stripDiacritics(search.trim());
		final GeodbObject go = (GeodbObject)userObject;
		if (StringUtils.stripDiacritics(go.getCity()).equals(searchFixed))
			return true;
		if (StringUtils.stripDiacritics(go.getCity_7bitlc()).equals(searchFixed))
			return true;
		if (StringUtils.stripDiacritics(go.getZip()).equals(searchFixed))
			return true;
		if (StringUtils.stripDiacritics(go.toString()).equals(searchFixed))
			return true;
		//
		return super.isMatching(userObject, search);
	}

	
}
