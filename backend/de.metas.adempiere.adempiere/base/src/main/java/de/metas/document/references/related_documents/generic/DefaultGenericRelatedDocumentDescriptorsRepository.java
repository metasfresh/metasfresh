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

package de.metas.document.references.related_documents.generic;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.MQuery;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultGenericRelatedDocumentDescriptorsRepository implements GenericRelatedDocumentDescriptorsRepository
{
	private static final Logger logger = LogManager.getLogger(DefaultGenericRelatedDocumentDescriptorsRepository.class);

	@Override
	public List<GenericRelatedDocumentDescriptor> getRelatedDocumentDescriptors(@NonNull final String sourceKeyColumnName)
	{
		final String sourceTableName = MQuery.getZoomTableName(sourceKeyColumnName);

		final ImmutableListMultimap<GenericTargetWindowInfo, GenericTargetColumnInfo> windowAndColumns = DB.retrieveRows(
				"SELECT * FROM ad_table_related_windows_v WHERE source_tableName=?",
				ImmutableList.of(sourceTableName),
				this::retrieveRows)
				.stream()
				.flatMap(List::stream)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						TargetWindowAndColumn::getWindow,
						TargetWindowAndColumn::getColumn));

		return windowAndColumns.keySet()
				.stream()
				.map(window -> GenericRelatedDocumentDescriptor.builder()
						.targetWindow(window)
						.targetColumns(windowAndColumns.get(window))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private List<TargetWindowAndColumn> retrieveRows(final ResultSet rs) throws SQLException
	{
		final ImmutableTranslatableString name = retrieveTranslatableString(rs, "target_window_name", "target_window_name_trls");
		final AdWindowId targetWindowId = AdWindowId.ofRepoId(rs.getInt("target_window_id"));
		final boolean targetHasIsSOTrxColumn = StringUtils.toBoolean(rs.getString("target_has_issotrx_column"));
		final boolean isDefaultSOWindow = StringUtils.toBoolean(rs.getString("IsDefaultSOWindow"));
		final boolean isDefaultPOWindow = StringUtils.toBoolean(rs.getString("IsDefaultPOWindow"));

		String tabSqlWhereClause = rs.getString("tab_whereclause");
		// SQL where clauses with context variables are not supported
		if (tabSqlWhereClause != null && tabSqlWhereClause.contains("@"))
		{
			// In case of default windows, we cannot exclude them, but we just take out the where clause
			if (isDefaultSOWindow || isDefaultPOWindow)
			{
				tabSqlWhereClause = null;
			}
			else
			{
				logger.info("Excluding zoom to {}/{} because SQL where clause contains context variables which are not supported atm: {}", targetWindowId, name, tabSqlWhereClause);
				return ImmutableList.of();
			}
		}

		final GenericTargetColumnInfo targetColumn = GenericTargetColumnInfo.builder()
				.caption(retrieveTranslatableString(rs, "target_columnDisplayName", "target_columnDisplayName_trls"))
				.columnName(rs.getString("target_columnname"))
				.virtualColumnSql(rs.getString("target_columnsql"))
				.build();

		final GenericTargetWindowInfo.GenericTargetWindowInfoBuilder windowInfoBuilder = GenericTargetWindowInfo.builder()
				.name(name)
				.targetTableName(rs.getString("target_tablename"))
				.targetWindowId(targetWindowId)
				.targetWindowInternalName(rs.getString("target_window_internalname"))
				//.soTrx(...) // see below
				.targetHasIsSOTrxColumn(targetHasIsSOTrxColumn)
				.tabSqlWhereClause(tabSqlWhereClause);

		final ArrayList<TargetWindowAndColumn> result = new ArrayList<>();
		if (targetHasIsSOTrxColumn && (isDefaultSOWindow || isDefaultPOWindow))
		{
			if (isDefaultSOWindow)
			{
				result.add(TargetWindowAndColumn.of(windowInfoBuilder.soTrx(SOTrx.SALES).build(), targetColumn));
			}
			if (isDefaultPOWindow)
			{
				result.add(TargetWindowAndColumn.of(windowInfoBuilder.soTrx(SOTrx.PURCHASE).build(), targetColumn));
			}
		}
		else
		{
			result.add(TargetWindowAndColumn.of(windowInfoBuilder.soTrx(null).build(), targetColumn));
		}

		return result;
	}

	private static ImmutableTranslatableString retrieveTranslatableString(
			final ResultSet rs,
			final String nameColumn,
			final String trlColumn) throws SQLException
	{
		final ImmutableTranslatableString.ImmutableTranslatableStringBuilder result = ImmutableTranslatableString.builder()
				.defaultValue(rs.getString(nameColumn));

		final Array sqlArray = rs.getArray(trlColumn);
		if (sqlArray != null)
		{
			final String[][] trls = (String[][])sqlArray.getArray();
			for (final String[] languageAndName : trls)
			{
				final String adLanguage = languageAndName[0];
				final String name = languageAndName[1];

				result.trl(adLanguage, name);
			}
		}

		return result.build();
	}

	@Value(staticConstructor = "of")
	private static class TargetWindowAndColumn
	{
		@NonNull GenericTargetWindowInfo window;
		@NonNull GenericTargetColumnInfo column;
	}
}
