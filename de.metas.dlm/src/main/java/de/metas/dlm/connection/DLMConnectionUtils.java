package de.metas.dlm.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.util.Check;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DLMConnectionUtils
{
	private DLMConnectionUtils()
	{
	}

	public static void changeDLMCoalesceLevel(final Connection c, final int dlmCoalesceLevel) throws SQLException
	{
		final PreparedStatement ps = c.prepareStatement("select set_config('metasfresh.DLM_Coalesce_Level'::text, ?::text, ?::boolean)");
		ps.setString(1, dlmCoalesceLevelStr(dlmCoalesceLevel));
		ps.setBoolean(2, false);
		ps.execute();
	}

	public static void changeDLMLevel(final Connection c, final int dlmLevel) throws SQLException
	{
		final PreparedStatement ps = c.prepareStatement("select set_config('metasfresh.DLM_Level'::text, ?::text, ?::boolean)");
		ps.setString(1, dlmLevelStr(dlmLevel));
		ps.setBoolean(2, false);
		ps.execute();
	}

	private static String dlmCoalesceLevelStr(final int dlmCoalesceLevel)
	{
		return Integer.toString(dlmCoalesceLevel);
	}

	private static String dlmLevelStr(final int dlmLevel)
	{
		return Integer.toString(dlmLevel);
	}

	public static int retrieveCurrentDLMLevel(final Connection c) throws SQLException
	{
		final ResultSet rs = c.prepareStatement("select current_setting('metasfresh.DLM_Level')").executeQuery();
		final String dlmLevelStr = rs.next() ? rs.getString(1) : null;
		Check.errorIf(dlmLevelStr == null, "Unable to retrieve the current setting for metasfresh.DLM_Level from the DB");
		return Integer.parseInt(dlmLevelStr);
	}

	public static int retrieveCurrentDLMCoalesceLevel(final Connection c) throws SQLException
	{
		final ResultSet rs = c.prepareStatement("select current_setting('metasfresh.DLM_Coalesce_Level')").executeQuery();
		final String dlmCoalesceLevelStr = rs.next() ? rs.getString(1) : null;
		Check.errorIf(dlmCoalesceLevelStr == null, "Unable to retrieve the current setting for metasfresh.DLM_Coalesce_Level from the DB");
		return Integer.parseInt(dlmCoalesceLevelStr);
	}
}
