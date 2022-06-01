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

package de.metas.ui.web.window.descriptor.sql;

import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Field's SQL expression to be used in ORDER BY constructions
 */
@EqualsAndHashCode
@ToString
public class SqlOrderByValue
{
	public static SqlOrderByValue ofColumnName(@NonNull final String joinOnTableNameOrAlias, @NonNull final String columnName)
	{
		return SqlOrderByValue.builder()
				.joinOnTableNameOrAlias(joinOnTableNameOrAlias)
				.columnName(columnName)
				.build();
	}

	private final SqlSelectDisplayValue sqlSelectDisplayValue;
	private final SqlSelectValue sqlSelectValue;
	private final String columnName;
	private final String joinOnTableNameOrAlias;

	//
	// Computed values
	private final String sqlOrderByColumnNameFQ;
	private final IStringExpression sourceSqlExpression;

	@Builder(toBuilder = true)
	private SqlOrderByValue(
			@Nullable final SqlSelectDisplayValue sqlSelectDisplayValue,
			@Nullable final SqlSelectValue sqlSelectValue,
			@Nullable final String columnName,
			@Nullable final String joinOnTableNameOrAlias)
	{
		this.joinOnTableNameOrAlias = StringUtils.trimBlankToNull(joinOnTableNameOrAlias);

		if (sqlSelectDisplayValue != null)
		{
			final SqlSelectDisplayValue sqlSelectDisplayValueEffective = sqlSelectDisplayValue.withJoinOnTableNameOrAlias(joinOnTableNameOrAlias);

			this.sqlSelectDisplayValue = sqlSelectDisplayValueEffective;
			this.sqlSelectValue = null;
			this.columnName = null;

			// computed values:
			this.sqlOrderByColumnNameFQ = sqlSelectDisplayValueEffective.toSqlOrderByUsingColumnNameAlias();
			this.sourceSqlExpression = sqlSelectDisplayValueEffective.toOrderByStringExpression();
		}
		else if (sqlSelectValue != null)
		{
			final SqlSelectValue sqlSelectValueEffective = sqlSelectValue.withJoinOnTableNameOrAlias(joinOnTableNameOrAlias);

			this.sqlSelectDisplayValue = null;
			this.sqlSelectValue = sqlSelectValueEffective;
			this.columnName = null;

			// computed values:
			this.sqlOrderByColumnNameFQ = computeColumnNameFQ(this.joinOnTableNameOrAlias, sqlSelectValueEffective.getColumnNameAlias());
			this.sourceSqlExpression = ConstantStringExpression.of(sqlSelectValueEffective.toSqlString());
		}
		else if (columnName != null && Check.isNotBlank(columnName))
		{
			this.sqlSelectDisplayValue = null;
			this.sqlSelectValue = null;
			this.columnName = columnName;

			// computed values:
			final String columnNameFQ = computeColumnNameFQ(this.joinOnTableNameOrAlias, this.columnName);
			this.sqlOrderByColumnNameFQ = columnNameFQ;
			this.sourceSqlExpression = ConstantStringExpression.of(columnNameFQ);
		}
		else
		{
			this.sqlSelectDisplayValue = null;
			this.sqlSelectValue = null;
			this.columnName = null;

			// computed values:
			this.sqlOrderByColumnNameFQ = null;
			this.sourceSqlExpression = IStringExpression.NULL;
		}
	}

	private static String computeColumnNameFQ(@Nullable final String tableName, @NonNull final String columnName)
	{
		return tableName != null ? tableName + "." + columnName : columnName;
	}

	public SqlOrderByValue withJoinOnTableNameOrAlias(final String joinOnTableNameOrAlias)
	{
		final String joinOnTableNameOrAliasEffective = StringUtils.trimBlankToNull(joinOnTableNameOrAlias);
		return !Objects.equals(this.joinOnTableNameOrAlias, joinOnTableNameOrAliasEffective)
				? toBuilder().joinOnTableNameOrAlias(joinOnTableNameOrAliasEffective).build()
				: this;
	}

	public boolean isNull()
	{
		return sourceSqlExpression.isNullExpression();
	}

	public String toSqlUsingColumnNameAlias()
	{
		return sqlOrderByColumnNameFQ;
	}

	/**
	 * @return (source sql expression)
	 */
	public IStringExpression toSourceSqlExpression() {return sourceSqlExpression;}
}
