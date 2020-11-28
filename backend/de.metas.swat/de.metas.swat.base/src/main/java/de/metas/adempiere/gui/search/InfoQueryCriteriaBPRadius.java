package de.metas.adempiere.gui.search;

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


import java.awt.Dimension;
import java.util.List;

import org.compiere.apps.search.IInfoSimple;
import org.compiere.apps.search.InfoQueryCriteriaBPRadiusAbstract;
import org.compiere.grid.ed.VNumber;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.MSysConfig;
import org.compiere.swing.CTextField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.util.Check;

public class InfoQueryCriteriaBPRadius extends InfoQueryCriteriaBPRadiusAbstract
{
	private CTextField fieldCityZip;
	private GeodbAutoCompleter fieldCityZipAutocompleter;
	private VNumber fieldRadius;

	@Override
	public void init(IInfoSimple parent, I_AD_InfoColumn infoColumn, String searchText)
	{
		final int defaultRadius = MSysConfig.getIntValue(SYSCONFIG_DefaultRadius, 0, Env.getAD_Client_ID(Env.getCtx()));
		//
		fieldCityZip = new CTextField();
		fieldCityZip.setPreferredSize(new Dimension(200, (int)fieldCityZip.getPreferredSize().getHeight()));
		fieldCityZipAutocompleter = new GeodbAutoCompleter(fieldCityZip);
		fieldCityZipAutocompleter.setUserObject(null);
		//
		fieldRadius = new VNumber("RadiusKM", false, false, true, DisplayType.Integer, "");
		fieldRadius.setValue(defaultRadius);
		fieldRadius.setRange(0.0, 999999.0);
	}

	@Override
	public Object getParameterComponent(int index)
	{
		if (index == 0)
			return fieldCityZip;
		else if (index == 1)
			return fieldRadius;
		else
			return null;
	}

	@Override
	public String[] getWhereClauses(List<Object> params)
	{
		final GeodbObject go = getGeodbObject();
		final String searchText = getText();

		if (go == null && !Check.isEmpty(searchText, true))
			return new String[]{"1=2"};
		if (go == null)
			return new String[]{"1=1"};

		final int radius = getRadius();
		//
		final String whereClause = "EXISTS (SELECT 1 FROM geodb_coordinates co WHERE "
				+ " co.zip=" + locationTableAlias + ".Postal" // join to C_Location.Postal
				+ " AND co.c_country_id=" + locationTableAlias + ".C_Country_ID"
				+ " AND " + getSQLDistanceFormula("co", go.getLat(), go.getLon()) + " <= " + radius
				+ ")";
		return new String[]{whereClause};
	}

	private static String getSQLDistanceFormula(String tableAlias, double lat, double lon)
	{
		return "DEGREES("
				+ " (ACOS("
				+ " SIN(RADIANS(" + lat + ")) * SIN(RADIANS(" + tableAlias + ".lat))	"
				+ " + COS(RADIANS(" + lat + "))*COS(RADIANS(" + tableAlias + ".lat))*COS(RADIANS(" + tableAlias + ".lon) - RADIANS(" + lon + "))"
				+ ") * 60 * 1.1515 " // miles
				+ " * 1.609344" // KM factor
				+ " )"
				+ " )";
	}

	private GeodbObject getGeodbObject()
	{
		return (GeodbObject)fieldCityZipAutocompleter.getUserOject();
	}

	@Override
	public String getText()
	{
		return fieldCityZipAutocompleter.getText();
	}

	private int getRadius()
	{
		if (fieldRadius == null)
			return 0;
		Object o = fieldRadius.getValue();
		if (o instanceof Number)
			return ((Number)o).intValue();
		return 0;
	}

}
