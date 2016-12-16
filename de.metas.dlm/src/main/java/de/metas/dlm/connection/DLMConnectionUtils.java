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

/**
 * This utils class makes the actual DB calls for getting and setting DLM parameters from the DB.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */ class DLMConnectionUtils
{
	private static final String FUNCTION_DLM_LEVEL = "dlm.get_dlm_level()";
	private static final String FUNCTION_DLM_COALESCE_LEVEL = "dlm.get_dlm_coalesce_level()";

	private static final String SETTING_DLM_LEVEL = "metasfresh.DLM_Level";
	private static final String SETTING_DLM_COALESCE_LEVEL = "metasfresh.DLM_Coalesce_Level";

	private DLMConnectionUtils()
	{
	}

	/**
	 * Sets <code>search_path</code> to <code>"$user", dlm, public</code> to give views in the <code>dlm</code> schema precedence over equally named tables in the <code>public</code> schema.
	 *
	 * @param c
	 * @throws SQLException
	 */
	public static void setSearchPathForDLM(final Connection c) throws SQLException
	{
		changeSetting(c, "search_path", "\"$user\", dlm, public");
	}

	public static void changeDLMCoalesceLevel(final Connection c, final int dlmCoalesceLevel) throws SQLException
	{
		changeSetting(c, SETTING_DLM_COALESCE_LEVEL, dlmCoalesceLevel);
	}

	public static void changeDLMLevel(final Connection c, final int dlmLevel) throws SQLException
	{
		changeSetting(c, SETTING_DLM_LEVEL, dlmLevel);
	}

	public static int retrieveCurrentDLMLevel(final Connection c) throws SQLException
	{
		return restrieveSetting(c, FUNCTION_DLM_LEVEL);

	}

	public static int retrieveCurrentDLMCoalesceLevel(final Connection c) throws SQLException
	{
		return restrieveSetting(c, FUNCTION_DLM_COALESCE_LEVEL);
	}

	private static int restrieveSetting(final Connection c, final String functionName) throws SQLException
	{
		final ResultSet rs = c.prepareStatement("SELECT " + functionName).executeQuery();
		final Integer dlmLevel = rs.next() ? rs.getInt(1) : null;

		Check.errorIf(dlmLevel == null, "Unable to retrieve the current setting for {} from the DB", SETTING_DLM_COALESCE_LEVEL);
		return dlmLevel;

	}

	private static void changeSetting(final Connection c, final String setting, final int value) throws SQLException
	{
		final String valueStr = Integer.toString(value);
		changeSetting(c, setting, valueStr);
	}

	private static void changeSetting(final Connection c, final String setting, final String valueStr) throws SQLException
	{
		final PreparedStatement ps = c.prepareStatement("select set_config('" + setting + "'::text, ?::text, ?::boolean)");
		ps.setString(1, valueStr);
		ps.setBoolean(2, false);
		ps.execute();
	}
}
