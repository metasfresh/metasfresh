/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.query;

import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.model.I_T_ES_FTS_Search_Result;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class FTSSearchResultRepository
{
	private static final String SQL_INSERT = buildSqlInsert();

	private static String buildSqlInsert()
	{
		final StringBuilder sqlColumns = new StringBuilder(
				I_T_ES_FTS_Search_Result.COLUMNNAME_Search_UUID // 1
						+ "," + I_T_ES_FTS_Search_Result.COLUMNNAME_Line // 2
						+ "," + I_T_ES_FTS_Search_Result.COLUMNNAME_JSON // 3
		);

		final StringBuilder sqlValues = new StringBuilder("?, ?, ?");

		for (final String keyColumnName : I_T_ES_FTS_Search_Result.COLUMNNAME_ALL_Keys)
		{
			sqlColumns.append(", ").append(keyColumnName);
			sqlValues.append(", ?");
		}

		return "INSERT INTO " + I_T_ES_FTS_Search_Result.Table_Name + " (" + sqlColumns + ")" + " VALUES (" + sqlValues + ")";
	}

	public void saveNew(@NonNull final FTSSearchResult result)
	{
		final ImmutableList<FTSSearchResultItem> items = result.getItems();
		if (items.isEmpty())
		{
			return;
		}

		PreparedStatement pstmt = null;
		try
		{
			int nextLineNo = 1;
			pstmt = DB.prepareStatement(SQL_INSERT, ITrx.TRXNAME_None);
			final ArrayList<Object> sqlParams = new ArrayList<>();

			for (final FTSSearchResultItem item : items)
			{
				final int lineNo = nextLineNo++;
				sqlParams.clear();
				sqlParams.add(result.getSearchId());
				sqlParams.add(lineNo);
				sqlParams.add(item.getJson());

				for (final String keyColumnName : I_T_ES_FTS_Search_Result.COLUMNNAME_ALL_Keys)
				{
					final Object value = item.getKey().getValueBySelectionTableColumnName(keyColumnName);
					sqlParams.add(value);
				}

				DB.setParameters(pstmt, sqlParams);
				pstmt.addBatch();
			}

			pstmt.executeBatch();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, SQL_INSERT);
		}
		finally
		{
			DB.close(pstmt);
		}
	}

}
