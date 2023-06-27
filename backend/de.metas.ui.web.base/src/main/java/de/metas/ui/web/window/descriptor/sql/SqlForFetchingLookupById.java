package de.metas.ui.web.window.descriptor.sql;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.IdsToFilter;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.TranslatableParameterizedStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.table.api.ColumnNameFQ;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.MLookupInfo;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.DB;
import org.compiere.util.ValueNamePairValidationInformation;

import javax.annotation.Nullable;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

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
public class SqlForFetchingLookupById
{
	private static final int INDEX_Name = 2; // in SQL indices are 1-based

	private final ColumnNameFQ keyColumnNameFQ;
	@Getter private final boolean numericKey;
	private final String additionalWhereClause;

	private final IStringExpression sqlSelectFrom;
	private final IStringExpression sqlOrderBySelectFrom;
	@Getter private final ImmutableSet<CtxName> parameters;
	private static final ConstantStringExpression SQL_NULL = ConstantStringExpression.of("NULL");

	@Builder
	private SqlForFetchingLookupById(
			@NonNull final ColumnNameFQ keyColumnNameFQ,
			@NonNull final Boolean numericKey,
			@NonNull final IStringExpression displayColumn,
			@Nullable final IStringExpression descriptionColumn,
			@Nullable final ColumnNameFQ activeColumn,
			@Nullable final IStringExpression validationMsgColumn,
			@NonNull final IStringExpression sqlFrom,
			@Nullable final String additionalWhereClause)
	{
		this.keyColumnNameFQ = keyColumnNameFQ;
		this.numericKey = numericKey;
		this.additionalWhereClause = StringUtils.trimBlankToNull(additionalWhereClause);
		this.sqlSelectFrom = IStringExpression
				.composer()
				.append("SELECT ")
				.append("\n ARRAY[")
				.append(keyColumnNameFQ.getAsString()).append("::text") // 1
				.append(", ").append(displayColumn) // 2 = INDEX_Name
				.append(", ").append(descriptionColumn != null ? descriptionColumn : SQL_NULL) // 3
				.append(",").append(activeColumn != null ? activeColumn.getAsString() : "NULL") // 4
				.append(", ").append(validationMsgColumn != null ? validationMsgColumn : SQL_NULL) // 5
				.append("] ")
				.append("\n FROM ").append(sqlFrom)
				.build();
		this.sqlOrderBySelectFrom = IStringExpression
				.composer()
				.append("SELECT \n ")
				.append(displayColumn)
				.append("\n FROM ").append(sqlFrom)
				.build();

		this.parameters = ImmutableSet.copyOf(sqlSelectFrom.getParameters());
	}

	public static SqlForFetchingLookupById ofLookupInfoSqlFrom(@NonNull final MLookupInfo.SqlQuery lookupInfoSqlQuery)
	{
		final IStringExpression displayColumnSQL = TranslatableParameterizedStringExpression.of(lookupInfoSqlQuery.getDisplayColumnSql());

		final IStringExpression descriptionColumnSqlOrNull = TranslatableParameterizedStringExpression.of(lookupInfoSqlQuery.getDescriptionColumnSql());
		final IStringExpression descriptionColumnSQL;
		if (descriptionColumnSqlOrNull == null || descriptionColumnSqlOrNull.isNullExpression())
		{
			descriptionColumnSQL = ConstantStringExpression.of("NULL");
		}
		else
		{
			descriptionColumnSQL = descriptionColumnSqlOrNull;
		}

		final IStringExpression validationMsgColumnSQL = StringUtils.trimBlankToOptional(lookupInfoSqlQuery.getValidationMsgColumnSQL())
				.map(ConstantStringExpression::of)
				.orElseGet(() -> ConstantStringExpression.of("NULL"));

		final IStringExpression fromSqlPart = lookupInfoSqlQuery.getSqlFromPart().toStringExpression();
		final ColumnNameFQ keyColumnFQ = lookupInfoSqlQuery.getKeyColumn();
		final boolean isRefList = lookupInfoSqlQuery.getTableName().equalsIgnoreCase(I_AD_Ref_List.Table_Name);

		return builder()
				.keyColumnNameFQ(keyColumnFQ)
				.numericKey(lookupInfoSqlQuery.isNumericKey())
				.displayColumn(displayColumnSQL)
				.descriptionColumn(descriptionColumnSQL)
				.activeColumn(lookupInfoSqlQuery.getActiveColumnSQL())
				.validationMsgColumn(validationMsgColumnSQL)
				.sqlFrom(fromSqlPart)
				.additionalWhereClause(isRefList ? lookupInfoSqlQuery.getSqlWhereClauseStatic() : null) // this is actually adding the AD_Ref_List.AD_Reference_ID=....
				.build();
	}


	public int getNameSqlArrayIndex()
	{
		return INDEX_Name;
	}

	public IStringExpression toStringExpression(@NonNull final String joinOnColumnNameFQ)
	{
		return buildSql(keyColumnNameFQ + "=" + joinOnColumnNameFQ);
	}

	public IStringExpression toOrderByStringExpression(@NonNull final String joinOnColumnNameFQ)
	{
		return buildOrderBySql(keyColumnNameFQ + "=" + joinOnColumnNameFQ);
	}

	public boolean requiresParameter(@NonNull final String parameterName)
	{
		return CtxNames.containsName(parameters, parameterName);
	}

	public Optional<SqlAndParams> evaluate(@NonNull final LookupDataSourceContext evalCtx)
	{
		final IdsToFilter idsToFilter = evalCtx.getIdsToFilter();
		if (idsToFilter.isNoValue())
		{
			return Optional.empty();
		}
		else if (idsToFilter.isSingleValue())
		{
			final Object singleId = LookupValue.normalizeId(idsToFilter.getSingleValueAsObject(), numericKey);
			final String keyColumnWhereClause = keyColumnNameFQ + "=?";
			final String sql = buildSql(keyColumnWhereClause).evaluate(evalCtx, OnVariableNotFound.Fail);
			return Optional.of(SqlAndParams.of(sql, singleId));
		}
		else
		{
			final ImmutableList<Object> multipleIds = LookupValue.normalizeIds(idsToFilter.toImmutableList(), numericKey);
			if (multipleIds.isEmpty())
			{
				return Optional.empty();
			}
			else
			{
				final ArrayList<Object> sqlParams = new ArrayList<>();
				final String keyColumnWhereClause = DB.buildSqlList(keyColumnNameFQ.getAsString(), multipleIds, sqlParams);
				final String sql = buildSql(keyColumnWhereClause).evaluate(evalCtx, OnVariableNotFound.Fail);

				return Optional.of(SqlAndParams.of(sql, sqlParams));
			}
		}
	}

	private IStringExpression buildOrderBySql(final String keyColumnWhereClause)
	{
		return IStringExpression.composer()
				.append(sqlOrderBySelectFrom)
				.append("\n WHERE ")
				.append(keyColumnWhereClause)
				.append(additionalWhereClause != null && !Check.isBlank(additionalWhereClause)
						? " AND " + additionalWhereClause
						: "")
				.build();
	}

	private IStringExpression buildSql(final String keyColumnWhereClause)
	{
		return IStringExpression.composer()
				.append(sqlSelectFrom)
				.append("\n WHERE ")
				.append(keyColumnWhereClause)
				.append(additionalWhereClause != null && !Check.isBlank(additionalWhereClause)
						? " AND " + additionalWhereClause
						: "")
				.build();
	}

	@Nullable
	public static LookupValue retrieveLookupValue(
			@NonNull final ResultSet rs,
			final boolean isNumericKey,
			final boolean isTranslatable,
			@Nullable final String adLanguage) throws SQLException
	{
		final Array sqlArray = rs.getArray(1);
		return toLookupValue(sqlArray, isNumericKey, isTranslatable, adLanguage);
	}

	@Nullable
	private static LookupValue toLookupValue(
			@Nullable final Array sqlArray,
			final boolean isNumericKey,
			final boolean isTranslatable,
			@Nullable final String adLanguage) throws SQLException
	{
		if (sqlArray == null)
		{
			return null;
		}

		final String[] array = (String[])sqlArray.getArray();
		if (array == null || array.length == 0)
		{
			return null;
		}

		final String idString = StringUtils.trimBlankToNull(array[0]);
		if (idString == null)
		{
			return null;
		}

		final String displayName = StringUtils.trimBlankToOptional(array[1]).orElse("");
		final String description = StringUtils.trimBlankToNull(array[2]);
		final boolean active = StringUtils.toBoolean(array[3]);

		final ITranslatableString displayNameTrl;
		final ITranslatableString descriptionTrl;
		if (isTranslatable)
		{
			displayNameTrl = TranslatableStrings.singleLanguage(adLanguage, displayName);
			descriptionTrl = TranslatableStrings.singleLanguage(adLanguage, description);
		}
		else
		{
			displayNameTrl = TranslatableStrings.anyLanguage(displayName);
			descriptionTrl = TranslatableStrings.anyLanguage(description);
		}

		if (isNumericKey)
		{
			final int idInt = Integer.parseInt(idString);
			return LookupValue.IntegerLookupValue.builder()
					.id(idInt)
					.displayName(displayNameTrl)
					.description(descriptionTrl)
					.active(active)
					.build();
		}
		else
		{
			final ValueNamePairValidationInformation validationInformation = StringUtils.trimBlankToOptional(array[4])
					.map(questionMsg -> ValueNamePairValidationInformation.builder()
							.question(AdMessageKey.of(questionMsg))
							.build())
					.orElse(null);

			return LookupValue.StringLookupValue.builder()
					.id(idString)
					.displayName(displayNameTrl)
					.description(descriptionTrl)
					.active(active)
					.validationInformation(validationInformation)
					.build();
		}
	}

	@Nullable
	public static LookupValue.IntegerLookupValue retrieveIntegerLookupValue(
			@NonNull final ResultSet rs,
			@NonNull final String columnName,
			@Nullable final String adLanguage) throws SQLException
	{
		final Array sqlArray = rs.getArray(columnName);
		return (LookupValue.IntegerLookupValue)toLookupValue(sqlArray, true, true, adLanguage);
	}

	@Nullable
	public static LookupValue.StringLookupValue retrieveStringLookupValue(
			@NonNull final ResultSet rs,
			@NonNull final String columnName,
			@Nullable final String adLanguage) throws SQLException
	{
		final Array sqlArray = rs.getArray(columnName);
		return (LookupValue.StringLookupValue)toLookupValue(sqlArray, false, true, adLanguage);
	}
}
