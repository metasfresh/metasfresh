/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.references.zoom_into;

import com.google.common.collect.ImmutableList;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.POInfo;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DefaultGenericZoomIntoTableInfoRepository implements GenericZoomIntoTableInfoRepository
{
	@Override
	public GenericZoomIntoTableInfo retrieveTableInfo(@NonNull final String tableName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);

		final @NonNull List<GenericZoomIntoTableWindow> windows = DB.retrieveRows(
				"SELECT * FROM ad_table_windows_v where TableName=?",
				ImmutableList.of(tableName),
				this::retrieveTableWindow);

		return GenericZoomIntoTableInfo.builder()
				.tableName(tableName)
				.keyColumnName(poInfo.getKeyColumnName())
				.hasIsSOTrxColumn(poInfo.hasColumnName("IsSOTrx"))
				.windows(windows)
				.build();
	}

	private GenericZoomIntoTableWindow retrieveTableWindow(final ResultSet rs) throws SQLException
	{
		return GenericZoomIntoTableWindow.builder()
				.priority(rs.getInt("priority"))
				.adWindowId(AdWindowId.ofRepoId(rs.getInt("AD_Window_ID")))
				.isDefaultSOWindow(StringUtils.toBoolean(rs.getString("IsDefaultSOWindow")))
				.isDefaultPOWindow(StringUtils.toBoolean(rs.getString("IsDefaultPOWindow")))
				.tabSqlWhereClause(StringUtils.trimBlankToNull(rs.getString("tab_whereClause")))
				.build();
	}
}
