/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.kpi.descriptor.sql;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.common.util.CoalesceUtil;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.compiere.util.CtxName;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@Value
public class SQLDatasourceDescriptor
{
	@Getter(AccessLevel.NONE)
	ImmutableMap<String, SQLDatasourceFieldDescriptor> fields;

	@NonNull String sourceTableName;
	@NonNull IStringExpression sqlWhereClause;
	@NonNull IStringExpression sqlDetailsWhereClause;
	boolean applySecuritySettings;
	@Nullable WindowId targetWindowId;

	@NonNull IStringExpression sqlSelect;

	private static final ImmutableSet<CtxName> PERMISSION_REQUIRED_PARAMS = ImmutableSet.of(
			KPIDataContext.CTXNAME_AD_User_ID,
			KPIDataContext.CTXNAME_AD_Role_ID,
			KPIDataContext.CTXNAME_AD_Client_ID
	);

	@Builder
	private SQLDatasourceDescriptor(
			@NonNull final List<SQLDatasourceFieldDescriptor> fields,
			final boolean applySecuritySettings,
			@Nullable final WindowId targetWindowId,
			@NonNull final String sourceTableName,
			@Nullable final String sqlFrom,
			@Nullable final String sqlWhereClause,
			@Nullable final String sqlDetailsWhereClause,
			@Nullable final String sqlGroupAndOrderBy)
	{
		Check.assumeNotEmpty(sourceTableName, "sourceTableName shall not be empty");
		Check.assumeNotEmpty(fields, "fields shall not be empty");

		this.fields = Maps.uniqueIndex(fields, SQLDatasourceFieldDescriptor::getFieldName);

		this.targetWindowId = targetWindowId;

		this.sourceTableName = sourceTableName;

		this.sqlWhereClause = StringUtils.trimBlankToOptional(sqlWhereClause)
				.map(StringExpressionCompiler.instance::compile)
				.orElse(IStringExpression.NULL);

		this.sqlDetailsWhereClause = buildSqlDetailsWhereClause(sqlDetailsWhereClause, sqlWhereClause);

		this.applySecuritySettings = applySecuritySettings;

		this.sqlSelect = buildSqlSelect(
				fields,
				sourceTableName,
				sqlFrom,
				sqlWhereClause,
				sqlGroupAndOrderBy);
	}

	public ImmutableCollection<SQLDatasourceFieldDescriptor> getFields()
	{
		return fields.values();
	}

	private static IStringExpression buildSqlSelect(
			@NonNull final List<SQLDatasourceFieldDescriptor> fields,
			@NonNull final String sourceTableName,
			@Nullable final String sqlFrom,
			@Nullable final String sqlWhereClause,
			@Nullable final String sqlGroupAndOrderBy)
	{
		Check.assumeNotEmpty(fields, "fields shall not be empty");

		final StringBuilder sqlFields = new StringBuilder();
		for (final SQLDatasourceFieldDescriptor field : fields)
		{
			final String fieldName = field.getFieldName();
			final String sqlField = field.getSqlSelect();
			if (sqlFields.length() > 0)
			{
				sqlFields.append("\n, ");
			}

			sqlFields.append("(").append(sqlField).append(") AS ").append(fieldName);
		}

		final StringBuilder sql = new StringBuilder();

		sql.append("SELECT \n").append(sqlFields);

		//
		// FROM ....
		sql.append("\n");
		if (sqlFrom != null && !Check.isBlank(sqlFrom))
		{
			if (!sqlFrom.trim().toUpperCase().startsWith("FROM"))
			{
				sql.append("FROM ");
			}

			sql.append(sqlFrom.trim());
		}
		else
		{
			sql.append("FROM ").append(sourceTableName);
		}

		//
		// WHERE
		if (sqlWhereClause != null && !Check.isBlank(sqlWhereClause))
		{
			sql.append("\n");
			if (!sqlWhereClause.trim().toUpperCase().startsWith("WHERE"))
			{
				sql.append("WHERE ");
			}

			sql.append(sqlWhereClause.trim());
		}

		//
		// GROUP BY / ORDER BY
		if (sqlGroupAndOrderBy != null && !Check.isBlank(sqlGroupAndOrderBy))
		{
			sql.append("\n").append(sqlGroupAndOrderBy.trim());
		}

		return StringExpressionCompiler.instance.compile(sql.toString());
	}

	private static IStringExpression buildSqlDetailsWhereClause(
			@Nullable final String sqlDetailsWhereClause,
			@Nullable final String sqlWhereClause)
	{
		String sqlDetailsWhereClauseNorm = CoalesceUtil.firstNotEmptyTrimmed(
				sqlDetailsWhereClause,
				sqlWhereClause);
		if (sqlDetailsWhereClauseNorm == null || Check.isBlank(sqlDetailsWhereClauseNorm))
		{
			return IStringExpression.NULL;
		}

		if (sqlDetailsWhereClauseNorm.toUpperCase().startsWith("WHERE"))
		{
			sqlDetailsWhereClauseNorm = sqlDetailsWhereClauseNorm.substring("WHERE".length()).trim();
		}

		return StringExpressionCompiler.instance.compile(sqlDetailsWhereClauseNorm);
	}

	public Set<CtxName> getRequiredContextParameters()
	{
		return ImmutableSet.<CtxName>builder()
				.addAll(sqlSelect.getParameters())
				.addAll(sqlDetailsWhereClause.getParameters())
				.addAll(isApplySecuritySettings() ? PERMISSION_REQUIRED_PARAMS : ImmutableSet.of())
				.build();
	}
}
