package de.metas.ui.web.window.descriptor.sql;

import de.metas.i18n.TranslatableParameterizedString;
import de.metas.security.IUserRolePermissions;
import de.metas.security.impl.AccessSqlStringExpression;
import de.metas.security.permissions.Access;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.TranslatableParameterizedStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.table.api.ColumnNameFQ;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.db.DBConstants;
import org.compiere.model.MLookupInfo;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
@ToString
public class SqlForFetchingLookups
{
	public static final String SQL_PARAM_VALUE_ShowInactive_Yes = "Y"; // i.e. show all
	public static final String SQL_PARAM_VALUE_ShowInactive_No = "N";
	public static final CtxName SQL_PARAM_ShowInactive = CtxNames.ofNameAndDefaultValue("SqlShowInactive", SQL_PARAM_VALUE_ShowInactive_No);

	public static final CtxName PARAM_Offset = CtxNames.ofNameAndDefaultValue("Offset", "0");
	public static final CtxName PARAM_Limit = CtxNames.ofNameAndDefaultValue("Limit", "1000");

	@NonNull private final TableName tableName;
	@NonNull private final IStringExpression sqlSelectFromPart;
	@NonNull private final IStringExpression sqlWhere_Main;
	@Nullable private final IStringExpression sqlWhere_ValidationRules;
	@NonNull private final IStringExpression sqlOrderBy;
	@Nullable private final Access requiredAccess;
	@Getter private final int entityTypeIndex;

	private final IStringExpression sql; // computed

	@Builder(toBuilder = true)
	private SqlForFetchingLookups(
			@NonNull final TableName tableName,
			@NonNull final IStringExpression sqlSelectFromPart,
			@NonNull final IStringExpression sqlWhere_Main,
			@Nullable final IStringExpression sqlWhere_ValidationRules,
			@NonNull final IStringExpression sqlOrderBy,
			@Nullable final Access requiredAccess,
			@Nullable final Integer entityTypeIndex)
	{
		this.tableName = tableName;
		this.sqlSelectFromPart = sqlSelectFromPart;
		this.sqlWhere_Main = sqlWhere_Main;
		this.sqlWhere_ValidationRules = sqlWhere_ValidationRules;
		this.sqlOrderBy = sqlOrderBy;
		this.requiredAccess = requiredAccess;
		this.entityTypeIndex = entityTypeIndex != null && entityTypeIndex >= 0 ? entityTypeIndex : -1;

		this.sql = IStringExpression.composer()
				.append(sqlSelectFromPart) // SELECT .. FROM ...
				.append("\n WHERE \n").append(joinSqlWhereClauses(sqlWhere_Main, sqlWhere_ValidationRules)) // WHERE
				.append("\n ORDER BY ").append(sqlOrderBy) // ORDER BY
				.append("\n OFFSET ").append(SqlForFetchingLookups.PARAM_Offset) // OFFSET
				.append("\n LIMIT ").append(SqlForFetchingLookups.PARAM_Limit) // LIMIT
				.wrapIfTrue(requiredAccess != null, AccessSqlStringExpression.wrapper(tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, requiredAccess)) // security
				.build()
				.caching();
	}

	private static IStringExpression joinSqlWhereClauses(
			@NonNull final IStringExpression sqlWhere_Main,
			@Nullable final IStringExpression sqlWhere_ValidationRules)
	{
		final CompositeStringExpression.Builder builder = sqlWhere_Main.toComposer();

		if (sqlWhere_ValidationRules != null && !sqlWhere_ValidationRules.isNullExpression())
		{
			builder.appendIfNotEmpty("\n AND ");
			builder.append(" /* validation rule */ ").append("(\n").append(sqlWhere_ValidationRules).append("\n)\n");
		}

		return builder.build();
	}

	public static SqlForFetchingLookups ofLookupInfoSqlFrom(
			@NonNull final MLookupInfo.SqlQuery lookupInfoSqlQuery,
			@Nullable final Access requiredAccess,
			boolean orderByLevenshteinDistance)
	{
		return builder()
				.tableName(lookupInfoSqlQuery.getTableName())
				.sqlSelectFromPart(TranslatableParameterizedStringExpression.of(lookupInfoSqlQuery.getSelectSqlPart()))
				.sqlWhere_Main(buildSqlWhere(lookupInfoSqlQuery))
				.sqlOrderBy(buildSqlOrderBy(lookupInfoSqlQuery, orderByLevenshteinDistance))
				.requiredAccess(requiredAccess)
				.entityTypeIndex(lookupInfoSqlQuery.getEntityTypeQueryColumnIndex())
				.build();
	}

	private static IStringExpression buildSqlWhere(@NonNull final MLookupInfo.SqlQuery lookupInfoSqlQuery)
	{
		final String lookupSqlWhereClauseStatic = StringUtils.trimBlankToNull(lookupInfoSqlQuery.getSqlWhereClauseStatic());
		final TranslatableParameterizedString displayColumnSql = lookupInfoSqlQuery.getDisplayColumnSql();
		final boolean showInactiveValues = lookupInfoSqlQuery.isShowInactiveValues();
		final ColumnNameFQ activeColumnSQL = lookupInfoSqlQuery.getActiveColumnSQL();

		final CompositeStringExpression.Builder sqlWhereFinal = IStringExpression.composer();

		// Static lookup's WHERE
		if (lookupSqlWhereClauseStatic != null)
		{
			sqlWhereFinal.append(" /* lookup where clause */ ").append("(").append(lookupSqlWhereClauseStatic).append(")");
		}

		// Filter's WHERE
		sqlWhereFinal.appendIfNotEmpty("\n AND ");
		sqlWhereFinal.append(" /* filter */ ")
				.append(DBConstants.FUNCNAME_unaccent_string).append("(").append(displayColumnSql).append(", 1)")
				.append(" ILIKE ")
				.append(DBConstants.FUNCNAME_unaccent_string).append("(").append(LookupDataSourceContext.PARAM_FilterSql).append(", 1)");

		// IsActive WHERE
		if (!showInactiveValues)
		{
			sqlWhereFinal.appendIfNotEmpty("\n AND ");
			sqlWhereFinal.append(" /* active */ ('").append(SQL_PARAM_ShowInactive).append("'='Y'")
					.append(" OR ").append(activeColumnSQL.getAsString()).append("='Y')");
		}

		return sqlWhereFinal.build();
	}

	private static IStringExpression buildSqlOrderBy(
			@NonNull final MLookupInfo.SqlQuery lookupInfoSqlQuery,
			boolean orderByLevenshteinDistance)
	{
		final CompositeStringExpression.Builder builder = IStringExpression.composer();

		//
		// 1. LEVENSHTEIN distance from user typed string to display name
		if (orderByLevenshteinDistance)
		{
			final TranslatableParameterizedString displayColumnSql = lookupInfoSqlQuery.getDisplayColumnSql();
			builder.append("levenshtein(")
					.append(DBConstants.FUNCNAME_unaccent_string).append("(").append(LookupDataSourceContext.PARAM_FilterSqlWithoutWildcards).append(", 1)")
					.append(", ")
					.append(DBConstants.FUNCNAME_unaccent_string).append("(").append(displayColumnSql).append(", 1)")
					.append(")");
		}

		//
		// 2. Display Name
		if (!builder.isEmpty())
		{
			builder.append(", ");
		}
		builder.append(StringUtils.trimBlankToOptional(lookupInfoSqlQuery.getSqlOrderBy())
				.orElse(String.valueOf(MLookupInfo.SqlQuery.COLUMNINDEX_DisplayName)));

		//
		return builder.build();
	}

	public SqlForFetchingLookups withSqlWhere_ValidationRules(@Nullable IStringExpression sqlWhere_ValidationRules)
	{
		return !Objects.equals(this.sqlWhere_ValidationRules, sqlWhere_ValidationRules)
				? toBuilder().sqlWhere_ValidationRules(sqlWhere_ValidationRules).build()
				: this;
	}

	public String toOneLineString()
	{
		return sql.toOneLineString();
	}

	public Set<CtxName> getParameters()
	{
		return sql.getParameters();
	}

	public String evaluate(final LookupDataSourceContext evalCtx)
	{
		return sql.evaluate(evalCtx, OnVariableNotFound.Fail);
	}
}
