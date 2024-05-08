/**
 *
 */
package org.compiere.apps.search;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.compiere.swing.CLabel;
import org.compiere.swing.CTextField;
import org.compiere.util.Env;

import de.metas.adempiere.gui.search.SponsorNoAutoCompleter;
import de.metas.adempiere.gui.search.SponsorNoObject;
import de.metas.i18n.Msg;
import de.metas.util.Check;

/**
 * @author metas GmbH, m.ostermann@metas.de
 */
@Deprecated
public class InfoBPartner_SponsorSearch
{
	private static final String PROP_FieldSponsorNo = "de.metas.sponsorsearch.FieldSponsorNo";

	public static void customize(InfoBPartner info)
	{

		final CTextField fieldSponsorNo = new CTextField();
		fieldSponsorNo.setPreferredSize(new Dimension(200, (int)fieldSponsorNo.getPreferredSize().getHeight()));

		final SponsorNoAutoCompleter fieldSponsorNoAutocompleter = new SponsorNoAutoCompleter(fieldSponsorNo);
		fieldSponsorNoAutocompleter.setUserObject(null);

		//
		info.parameterPanel.putClientProperty(PROP_FieldSponsorNo, fieldSponsorNoAutocompleter);
		//
		info.parameterPanel.add(new CLabel(Msg.translate(Env.getCtx(), "SponsorNo")), null);
		info.parameterPanel.add(fieldSponsorNo, null);
	}

	public static String getSQLWhere(InfoBPartner info)
	{
		final SponsorNoObject sno = getSponsorNoObject(info);
		final String searchText = getSearchText(info);

		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		if (sno == null && !Check.isEmpty(searchText, true))
			return "1=2";
		if (sno == null)
			return "1=1";

		//
		final String whereClause = "EXISTS (SELECT 1 FROM C_Sponsor_Salesrep ssr"
				+ " JOIN C_Sponsor sp ON (ssr.C_Sponsor_ID = sp.C_Sponsor_ID)"
				+ " WHERE C_BPartner.C_BPartner_ID = sp.C_BPartner_ID"
				+ " AND ssr.C_Sponsor_Parent_ID = " + sno.getSponsorID()
				+ ")"
				+ " AND '"
				+ df.format(Env.getContextAsDate(Env.getCtx(), "#Date"))
				+ "' BETWEEN ssr.Validfrom AND Validto"
				+ " AND ssr.C_BPartner_ID > 0";

		return whereClause;
	}

	private static String getSearchText(InfoBPartner info)
	{
		SponsorNoAutoCompleter field = (SponsorNoAutoCompleter)info.parameterPanel.getClientProperty(PROP_FieldSponsorNo);
		if (field == null)
			return null;
		return field.getText();
	}

	private static SponsorNoObject getSponsorNoObject(InfoBPartner info)
	{
		SponsorNoAutoCompleter field = (SponsorNoAutoCompleter)info.parameterPanel.getClientProperty(PROP_FieldSponsorNo);
		if (field != null)
			return (SponsorNoObject)field.getUserOject();
		else
			return null;
	}
}
