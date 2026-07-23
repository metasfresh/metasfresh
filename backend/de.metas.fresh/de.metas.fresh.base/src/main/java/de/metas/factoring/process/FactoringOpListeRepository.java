/*
 * #%L
 * de.metas.fresh.base
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

package de.metas.factoring.process;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Repository for the {@code report_factoring_op_liste} PostgreSQL set-returning function.
 *
 * <p>Encapsulates the SQL call so that {@link Factoring_OP_Liste_Export} can be unit-tested
 * by mocking this repository without a live database connection.
 */
@Repository
public class FactoringOpListeRepository
{
	/**
	 * Calls the PostgreSQL set-returning function {@code report_factoring_op_liste} and returns
	 * all rows as a list of {@code String[12]} arrays.
	 *
	 * <p>Each array is indexed as follows:
	 * <ul>
	 *   <li>index 0 — {@code row_type} (trimmed; "01" for header, "02" for detail)</li>
	 *   <li>index 1..11 — {@code col_1} through {@code col_11} (null replaced by "")</li>
	 * </ul>
	 *
	 * @param currencyId C_Currency_ID (Währung parameter)
	 * @param orgId      AD_Org_ID of the current org
	 * @param clientId   AD_Client_ID
	 * @return rows returned by the SQL function (1 header row + 0..n detail rows)
	 */
	public List<String[]> loadOpListRows(final int currencyId, final int orgId, final int clientId)
	{
		final String sql = "SELECT row_type, col_1, col_2, col_3, col_4, col_5,"
				+ " col_6, col_7, col_8, col_9, col_10, col_11"
				+ " FROM report_factoring_op_liste(?, ?, ?)";

		final ImmutableList<String[]> rows = DB.retrieveRows(
				sql,
				Arrays.asList(currencyId, orgId, clientId),
				rs -> {
					final String[] row = new String[12];
					row[0] = nullToEmpty(rs.getString("row_type")).trim();
					for (int i = 1; i <= 11; i++)
					{
						row[i] = nullToEmpty(rs.getString("col_" + i));
					}
					return row;
				});
		return rows;
	}

	@NonNull
	private static String nullToEmpty(final String s)
	{
		return s != null ? s : "";
	}
}
