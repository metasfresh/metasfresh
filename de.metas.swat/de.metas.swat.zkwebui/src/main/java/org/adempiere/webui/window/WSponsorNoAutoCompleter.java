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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.StringUtils;
import org.adempiere.webui.component.AbstractAutoCompleter;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.zkoss.zk.ui.event.Event;

import de.metas.adempiere.gui.search.SponsorNoObject;

public class WSponsorNoAutoCompleter extends AbstractAutoCompleter
{

	private static final long serialVersionUID = -3060928126013713992L;

	@Override
	protected String getSelectSQL(final String search, final List<Object> params)
	{

		String searchSQL = StringUtils.stripDiacritics(search);
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
						+ " WHERE (upper(sp.SponsorNo) LIKE upper(?)"
						+ " OR upper(bp.Name) LIKE upper(?))"
						+ " AND '"
						+ df.format(Env.getContextAsDate(Env.getCtx(), "#Date"))
						+ "' BETWEEN ssr.Validfrom AND ssr.Validto"
						+ " ORDER BY sp.SponsorNo";

		params.add(searchSQL); // SponsorNo
		params.add(searchSQL); // Name
		//
		return sql;
	}

	@Override
	protected Object fetchUserObject(final ResultSet rs) throws SQLException
	{
		final int sponsorID = rs.getInt("C_Sponsor_ID");
		final String sponsorNo = rs.getString("SponsorNo");
		final String name = rs.getString("Name");
		final SponsorNoObject o = new SponsorNoObject(sponsorID, sponsorNo, name);
		o.setStringRepresentation(rs.getString("string_rep"));
		return o;
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

		// If the list has only one item, but that item is not equals with
		// m_city
		// return false to not show any popup
		userObject = getUserOject();
		if (!truncated && list.size() == 1 && userObject != null
				&& list.get(0).equals(userObject))
		{
			setStyle("background:red");
			log.finest("nothing to do 1");
			return false;
		}
		//
		final String[] sponsorValues = new String[list.size()];
		final String[] sponsorDesc = new String[list.size()];
		int i = 0;
		for (final Object o : list)
		{
			if (o instanceof SponsorNoObject)
			{
				final SponsorNoObject spo = (SponsorNoObject)o;
				appendItem(spo.getSponsorNo(), o);
				if (Util.isEmpty(spo.getName(), true))
				{
					sponsorValues[i] = spo.getSponsorNo();
				}
				else
				{
					sponsorValues[i] = spo.getSponsorNo() + " - " + spo.getName();
				}
				sponsorDesc[i] = spo.getStringRepresentation();
			}
			else if (AbstractAutoCompleter.ITEM_More.equals(o.toString()))
			{
				appendItem(AbstractAutoCompleter.ITEM_More, o);
				sponsorValues[i] = AbstractAutoCompleter.ITEM_More;
				sponsorDesc[i] = AbstractAutoCompleter.ITEM_More;
			}
			i++;
		}
		setDict(sponsorValues);
		setDescription(sponsorDesc);

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
	public void onEvent(final Event event) throws Exception
	{
		final int index = getSelectedIndex();
		if (index >= 0)
		{
			final Object obj = getItemAtIndex(index).getValue();
			SponsorNoObject spno = null;
			if (obj instanceof SponsorNoObject)
			{
				spno = (SponsorNoObject)obj;
			}

			if (event == null || AbstractAutoCompleter.ITEM_More.equals(obj.toString()))
			{
				return;
			}
			setText(spno.getSponsorNo());
		}
	}

}
