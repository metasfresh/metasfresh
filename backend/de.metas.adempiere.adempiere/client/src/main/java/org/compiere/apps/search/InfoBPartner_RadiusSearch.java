/**
 *
 */
package org.compiere.apps.search;

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

import de.metas.adempiere.gui.search.GeodbAutoCompleter;
import de.metas.adempiere.gui.search.GeodbObject;
import de.metas.i18n.Msg;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.grid.ed.VNumber;
import org.compiere.swing.CLabel;
import org.compiere.swing.CTextField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import java.awt.*;

/**
 * @author teo.sarca@gmail.com
 */
@Deprecated
public class InfoBPartner_RadiusSearch
{
	public static final String SYSCONFIG_DefaultRadius = "de.metas.radiussearch.DefaultRadius";

	private static final String PROP_FieldCityZip = "de.metas.radiussearch.FieldCityZip";
	private static final String PROP_FieldRadius = "de.metas.radiussearch.FieldRadius";

	public static void customize(InfoBPartner info)
	{
		final int defaultRadius = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_DefaultRadius, 0, Env.getAD_Client_ID(Env.getCtx()));

		final CTextField fieldCityZip = new CTextField();
		fieldCityZip.setPreferredSize(new Dimension(200, (int)fieldCityZip.getPreferredSize().getHeight()));
		final GeodbAutoCompleter fieldCityZipAutocompleter = new GeodbAutoCompleter(fieldCityZip);
		fieldCityZipAutocompleter.setUserObject(null);
		//
		final VNumber fieldRadius = new VNumber("RadiusKM", false, false, true, DisplayType.Integer, "");
		fieldRadius.setValue(defaultRadius);
		fieldRadius.setRange(0.0, 999999.0);
		//
		info.parameterPanel.putClientProperty(PROP_FieldCityZip, fieldCityZipAutocompleter);
		info.parameterPanel.putClientProperty(PROP_FieldRadius, fieldRadius);
		//
		info.parameterPanel.add(new CLabel(Msg.translate(Env.getCtx(), "NearCity")), new ALayoutConstraint(2, 0));
		info.parameterPanel.add(fieldCityZip, null);
		info.parameterPanel.add(new CLabel(Msg.translate(Env.getCtx(), "RadiusKM")), null);
		info.parameterPanel.add(fieldRadius, null);
	}

	public static String getSQLWhere(InfoBPartner info)
	{
		final GeodbObject go = getGeodbObject(info);
		final String searchText = getSearchText(info);

		if (go == null && !Check.isEmpty(searchText, true))
			return "1=2";
		if (go == null)
			return "1=1";

		final int radius = getRadius(info);
		//
		final String whereClause = "EXISTS (SELECT 1 FROM geodb_coordinates co WHERE "
				+ " co.zip=a.Postal" // join to C_Location.Postal
				+ " AND co.c_country_id=a.c_country_id"
				+ " AND " + getSQLDistanceFormula("co", go.getLat(), go.getLon()) + " <= " + radius
				+ ")";
		return whereClause;
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

	private static GeodbObject getGeodbObject(InfoBPartner info)
	{
		GeodbAutoCompleter field = (GeodbAutoCompleter)info.parameterPanel.getClientProperty(PROP_FieldCityZip);
		if (field != null)
			return (GeodbObject)field.getUserOject();
		else
			return null;
	}

	private static String getSearchText(InfoBPartner info)
	{
		GeodbAutoCompleter field = (GeodbAutoCompleter)info.parameterPanel.getClientProperty(PROP_FieldCityZip);
		if (field == null)
			return null;
		return field.getText();
	}

	private static int getRadius(InfoBPartner info)
	{
		VNumber field = (VNumber)info.parameterPanel.getClientProperty(PROP_FieldRadius);
		if (field == null)
			return 0;
		Object o = field.getValue();
		if (o instanceof Number)
			return ((Number)o).intValue();
		return 0;
	}
}
