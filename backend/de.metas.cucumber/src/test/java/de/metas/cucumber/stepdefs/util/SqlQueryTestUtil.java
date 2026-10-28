/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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
package de.metas.cucumber.stepdefs.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Runs a plain SELECT (e.g. calling a SQL report function) outside any transaction and hands every result row to a consumer.
 */
@UtilityClass
public class SqlQueryTestUtil
{
	@FunctionalInterface
	public interface RowConsumer
	{
		void accept(ResultSet rs) throws SQLException;
	}

	public static void queryRows(@NonNull final String sql, @NonNull final RowConsumer consumer)
	{
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				final ResultSet rs = pstmt.executeQuery())
		{
			while (rs.next())
			{
				consumer.accept(rs);
			}
		}
		catch (final SQLException e)
		{
			throw new AdempiereException("Failed running: " + sql, e);
		}
	}
}
