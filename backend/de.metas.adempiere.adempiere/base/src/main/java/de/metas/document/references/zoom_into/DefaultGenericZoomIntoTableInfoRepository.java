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
import de.metas.common.util.CoalesceUtil;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.POInfo;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DefaultGenericZoomIntoTableInfoRepository implements GenericZoomIntoTableInfoRepository
{
	private static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	@Override
	public GenericZoomIntoTableInfo retrieveTableInfo(
			@NonNull final String tableName,
			final boolean ignoreExcludeFromZoomTargetsFlag)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		if(poInfo == null)
		{
			throw new AdempiereException("No table info found for "+tableName);
		}

		final List<GenericZoomIntoTableWindow> windows = retrieveTableWindows(tableName, ignoreExcludeFromZoomTargetsFlag);

		final GenericZoomIntoTableInfo.GenericZoomIntoTableInfoBuilder builder = GenericZoomIntoTableInfo.builder()
				.tableName(tableName)
				.keyColumnNames(poInfo.getKeyColumnNames())
				.hasIsSOTrxColumn(poInfo.hasColumnName(COLUMNNAME_IsSOTrx))
				.windows(windows);

		final ParentLink parentLink = CoalesceUtil.coalesceSuppliers(
				() -> getParentLink_HeaderForLine(poInfo),
				() -> getParentLink_SingleParentColumn(poInfo));
		if (parentLink != null)
		{
			builder.parentTableName(parentLink.getParentTableName());
			builder.parentLinkColumnName(parentLink.getLinkColumnName());
		}

		return builder.build();
	}

	@NonNull
	private List<GenericZoomIntoTableWindow> retrieveTableWindows(
			final @NonNull String tableName,
			final boolean ignoreExcludeFromZoomTargetsFlag)
	{
		String sql = "SELECT * FROM ad_table_windows_v where TableName=?";
		if(!ignoreExcludeFromZoomTargetsFlag)
		{
			sql += " AND IsExcludeFromZoomTargets='N'";
		}

		final @NonNull List<GenericZoomIntoTableWindow> windows = DB.retrieveRows(
				sql,
				ImmutableList.of(tableName),
				this::retrieveTableWindow);
		return windows;
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

	@Value
	@Builder
	private static class ParentLink
	{
		@NonNull String linkColumnName;
		@NonNull String parentTableName;
	}

	@Nullable
	private ParentLink getParentLink_HeaderForLine(@NonNull final POInfo poInfo)
	{
		final String tableName = poInfo.getTableName();
		if (!tableName.endsWith("Line"))
		{
			return null;
		}

		final String parentTableName = tableName.substring(0, tableName.length() - "Line".length());
		final String parentLinkColumnName = parentTableName + "_ID";
		if (!poInfo.hasColumnName(parentLinkColumnName))
		{
			return null;
		}

		// virtual column parent link is not supported
		if (poInfo.isVirtualColumn(parentLinkColumnName))
		{
			return null;
		}

		return ParentLink.builder()
				.linkColumnName(parentLinkColumnName)
				.parentTableName(parentTableName)
				.build();
	}

	@Nullable
	private ParentLink getParentLink_SingleParentColumn(@NonNull final POInfo poInfo)
	{
		final String parentLinkColumnName = poInfo.getSingleParentColumnName().orElse(null);
		if (parentLinkColumnName == null)
		{
			return null;
		}

		// virtual column parent link is not supported
		if (poInfo.isVirtualColumn(parentLinkColumnName))
		{
			return null;
		}

		final String parentTableName = poInfo.getReferencedTableNameOrNull(parentLinkColumnName);
		if (parentTableName == null)
		{
			return null;
		}

		return ParentLink.builder()
				.linkColumnName(parentLinkColumnName)
				.parentTableName(parentTableName)
				.build();
	}

}
