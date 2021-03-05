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
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.MQuery;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultGenericZoomInfoDescriptorsRepository implements GenericZoomInfoDescriptorsRepository
{
	private static final Logger logger = LogManager.getLogger(DefaultGenericZoomInfoDescriptorsRepository.class);

	@Override
	public List<GenericZoomInfoDescriptor> getZoomInfoDescriptors(@NonNull final String sourceKeyColumnName)
	{
		final String sourceTableName = MQuery.getZoomTableName(sourceKeyColumnName);

		return DB.retrieveRows("SELECT * FROM ad_table_related_windows_v WHERE source_tableName=?", ImmutableList.of(sourceTableName), this::retrieveRows)
				.stream()
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());
	}

	private List<GenericZoomInfoDescriptor> retrieveRows(final ResultSet rs) throws SQLException
	{
		final ImmutableTranslatableString name = retrieveName(rs);
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

		final GenericZoomInfoDescriptor.GenericZoomInfoDescriptorBuilder infoBuilder = GenericZoomInfoDescriptor.builder()
				.name(name)
				.targetTableName(rs.getString("target_tablename"))
				.targetColumnName(rs.getString("target_columnname"))
				.virtualTargetColumnSql(rs.getString("target_columnsql"))
				.dynamicTargetColumnName(false)
				.targetWindowId(targetWindowId)
				.targetWindowInternalName(rs.getString("target_window_internalname"))
				//.isSOTrx(...)
				.targetHasIsSOTrxColumn(targetHasIsSOTrxColumn)
				.tabSqlWhereClause(tabSqlWhereClause);

		final ArrayList<GenericZoomInfoDescriptor> result = new ArrayList<>();
		if (targetHasIsSOTrxColumn && (isDefaultSOWindow || isDefaultPOWindow))
		{
			if (isDefaultSOWindow)
			{
				result.add(infoBuilder.isSOTrx(Boolean.TRUE).build());
			}
			if (isDefaultPOWindow)
			{
				result.add(infoBuilder.isSOTrx(Boolean.FALSE).build());
			}
		}
		else
		{
			result.add(infoBuilder.isSOTrx(null).build());
		}

		return result;
	}

	private static ImmutableTranslatableString retrieveName(final ResultSet rs) throws SQLException
	{
		final ImmutableTranslatableString.ImmutableTranslatableStringBuilder result = ImmutableTranslatableString.builder()
				.defaultValue(rs.getString("target_window_name"));

		final Array sqlArray = rs.getArray("target_window_name_trls");
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
}
