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


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.text.JTextComponent;

import org.compiere.apps.search.FieldAutoCompleter;
import org.compiere.util.Env;

import de.metas.util.StringUtils;

/**
 * @author metas GmbH, m.ostermann@metas.de
 *
 */
public class SponsorNoAutoCompleter extends FieldAutoCompleter
{

	public SponsorNoAutoCompleter(JTextComponent comp)
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
		
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		final String sql_strRep = "sp.SponsorNo || ' - VP - ' || bp.name"; 
		
		final String sql =
			"SELECT sp.SponsorNo, bp.Name, sp.C_Sponsor_ID, "
			+ sql_strRep + " as string_rep"
			+ " FROM C_Sponsor_Salesrep ssr"
			+ " LEFT JOIN C_Sponsor sp ON (ssr.C_Sponsor_ID = sp.C_Sponsor_ID)"
			+ " LEFT JOIN C_BPartner bp ON (ssr.C_BPartner_ID = bp.C_BPartner_ID)"
			+ " WHERE (sp.SponsorNo LIKE ?"
			+ " OR upper(bp.Name) LIKE ?)"
			+ " AND '" 
			+ df.format(Env.getContextAsDate(Env.getCtx(), "#Date"))
			+ "' BETWEEN ssr.Validfrom AND ssr.Validto"
			+ " ORDER BY sp.SponsorNo"
			;
		
		params.add(searchSQL); // SponsorNo
		params.add(searchSQL); // Name
		//
		return sql;
	}
	
	@Override
	protected Object fetchUserObject(ResultSet rs) throws SQLException
	{
		int sponsorID = rs.getInt("C_Sponsor_ID");
		String sponsorNo = rs.getString("SponsorNo");
		String name = rs.getString("Name");
		SponsorNoObject o = new SponsorNoObject(sponsorID, sponsorNo, name);
		o.setStringRepresentation(rs.getString("string_rep"));
		return o;
	}
	

	@Override
	protected boolean isMatching(Object userObject, String search)
	{	
		//
		return super.isMatching(userObject, search);
	}

	
}
