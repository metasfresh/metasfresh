package de.metas.ui.web.window.descriptor.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.TranslatableParameterizedStringExpression;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.model.I_T_Query_Selection;
import org.compiere.model.POInfo;
import org.compiere.util.CtxName;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.TranslatableParameterizedString;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
public final class SqlDocumentEntityDataBindingDescriptor implements DocumentEntityDataBindingDescriptor, Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final SqlDocumentEntityDataBindingDescriptor cast(final DocumentEntityDataBindingDescriptor descriptor)
	{
		return (SqlDocumentEntityDataBindingDescriptor)descriptor;
	}

	private static final String TABLEALIAS_Master = "master";

	//
	// Paging constants
	public static final String COLUMNNAME_Paging_UUID = "_sel_UUID";
	public static final String COLUMNNAME_Paging_SeqNo = "_sel_SeqNo";
	public static final String COLUMNNAME_Paging_Record_ID = "_sel_Record_ID";

	@JsonProperty("sqlTableName")
	private final String sqlTableName;
	@JsonProperty("sqlTableAlias")
	private final String sqlTableAlias;
	@JsonIgnore
	private final String sqlKeyColumnName;
	@JsonProperty("sqlParentLinkColumnName")
	private final String sqlParentLinkColumnName;

	@JsonIgnore
	private final TranslatableParameterizedString sqlSelectAllFrom;

	@JsonIgnore
	private final TranslatableParameterizedString sqlPagedSelectAllFrom;

	@JsonProperty("sqlWhereClause")
	private final IStringExpression sqlWhereClause;
	@JsonProperty("sqlOrderBy")
	private final String sqlOrderBy;

	@JsonProperty("fields")
	private final Map<String, SqlDocumentFieldDataBindingDescriptor> fieldsByFieldName;

	// legacy
	@JsonProperty("AD_Table_ID")
	private final int AD_Table_ID;

	private SqlDocumentEntityDataBindingDescriptor(final Builder builder)
	{
		super();
		sqlTableName = builder.getSqlTableName();
		Check.assumeNotEmpty(sqlTableName, "sqlTableName is not empty");

		sqlTableAlias = builder.getSqlTableAlias();
		Check.assumeNotEmpty(sqlTableAlias, "sqlTableAlias is not empty");

		sqlKeyColumnName = builder.getSqlKeyColumnName();

		sqlParentLinkColumnName = builder.getSqlParentLinkColumnName();

		fieldsByFieldName = ImmutableMap.copyOf(builder.getFieldsByFieldName());

		sqlSelectAllFrom = builder.getSqlSelectAll();
		sqlPagedSelectAllFrom = builder.getSqlPagedSelectAll();
		sqlWhereClause = builder.getSqlWhereClauseExpression();
		sqlOrderBy = builder.buildSqlOrderBy();

		// legacy
		AD_Table_ID = builder.AD_Table_ID;
	}

	@JsonCreator
	private SqlDocumentEntityDataBindingDescriptor(
			@JsonProperty("sqlTableName") final String sqlTableName//
			, @JsonProperty("sqlTableAlias") final String sqlTableAlias//
			, @JsonProperty("sqlParentLinkColumnName") final String sqlParentLinkColumnName//
			, @JsonProperty("sqlWhereClause") final IStringExpression sqlWhereClause//
			, @JsonProperty("sqlOrderBy") final String sqlOrderBy//
			, @JsonProperty("fields") final List<SqlDocumentFieldDataBindingDescriptor> fields //
			, @JsonProperty("AD_Table_ID") final int AD_Table_ID)
	{
		this(new Builder()
				.setSqlTableName(sqlTableName)
				.setSqlTableAlias(sqlTableAlias)
				// key
				.setSqlParentLinkColumnName(sqlParentLinkColumnName)
				.setSqlWhereClauseExpression(sqlWhereClause)
				.setSqlOrderBy(sqlOrderBy)
				.addFields(fields)
				.setAD_Table_ID(AD_Table_ID));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("sqlTableName", sqlTableName)
				.add("sqlTableAlias", sqlTableAlias)
				.add("sqlKeyColumnName", sqlKeyColumnName)
				.add("sqlParentLinkColumnName", sqlParentLinkColumnName)
				.add("sqlOrderBy", sqlOrderBy)
				.add("fields", fieldsByFieldName.isEmpty() ? null : fieldsByFieldName.values())
				.toString();
	}

	public String getSqlTableName()
	{
		return sqlTableName;
	}

	@Override
	@JsonIgnore
	public String getTableName()
	{
		return sqlTableName;
	}

	public String getSqlTableAlias()
	{
		return sqlTableAlias;
	}

	@Override
	@JsonIgnore
	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	@JsonIgnore
	public POInfo getPOInfo()
	{
		// NOTE: don't cache it here because it might change dynamically and it would be so nice to support that case...
		return POInfo.getPOInfo(sqlTableName);
	}

	public String getSqlKeyColumnName()
	{
		return sqlKeyColumnName;
	}

	public String getSqlParentLinkColumnName()
	{
		return sqlParentLinkColumnName;
	}

	public String getSqlSelectAllFrom(final String adLanguage)
	{
		return sqlSelectAllFrom.translate(adLanguage);
	}

	public String getSqlPagedSelectAllFrom(final String adLanguage)
	{
		return sqlPagedSelectAllFrom.translate(adLanguage);
	}

	public IStringExpression getSqlWhereClause()
	{
		return sqlWhereClause;
	}

	public String getSqlOrderBy()
	{
		return sqlOrderBy;
	}

	public SqlDocumentFieldDataBindingDescriptor getFieldByFieldName(final String fieldName)
	{
		final SqlDocumentFieldDataBindingDescriptor field = fieldsByFieldName.get(fieldName);
		if (field == null)
		{
			throw new IllegalArgumentException("No field found for fieldName=" + fieldName + " in " + this);
		}
		return field;
	}

	public static final class Builder implements DocumentEntityDataBindingDescriptorBuilder
	{
		private SqlDocumentEntityDataBindingDescriptor _built = null;

		private String _sqlTableName;
		private String _sqlTableAlias;
		private String _sqlParentLinkColumnName;
		private String _sqlOrderBy;
		private String _sqlWhereClause = null;
		private IStringExpression _sqlWhereClauseExpression;

		//
		private static final Joiner JOINER_SqlSelectFields = Joiner.on("\n, ");
		private TranslatableParameterizedString _sqlSelectAll; // will be built
		private TranslatableParameterizedString _sqlPagedSelectAll; // will be built

		private final LinkedHashMap<String, SqlDocumentFieldDataBindingDescriptor> _fieldsByFieldName = new LinkedHashMap<>();
		private SqlDocumentFieldDataBindingDescriptor _keyField;

		// legacy
		private Integer AD_Table_ID;

		private Builder()
		{
			super();
		}

		@Override
		public SqlDocumentEntityDataBindingDescriptor getOrBuild()
		{
			if (_built == null)
			{
				_built = new SqlDocumentEntityDataBindingDescriptor(this);
			}
			return _built;
		}

		private final void assertNotBuilt()
		{
			if (_built != null)
			{
				throw new IllegalStateException("Already built: " + this);
			}
		}

		private TranslatableParameterizedString getSqlSelectAll()
		{
			if (_sqlSelectAll == null)
			{
				buildSqlSelects();
			}
			return _sqlSelectAll;
		}

		private TranslatableParameterizedString getSqlPagedSelectAll()
		{
			if (_sqlPagedSelectAll == null)
			{
				buildSqlSelects();
			}
			return _sqlPagedSelectAll;
		}

		/**
		 * @return SELECT ... FROM ....
		 */
		private void buildSqlSelects()
		{
			final Collection<SqlDocumentFieldDataBindingDescriptor> fields = getFieldsByFieldName().values();
			if (fields.isEmpty())
			{
				throw new IllegalStateException("No SQL fields found");
			}

			final List<String> sqlSelectValuesList = new ArrayList<>(fields.size());
			final List<String> sqlSelectDisplayNamesList_BaseLang = new ArrayList<>();
			final List<String> sqlSelectDisplayNamesList_Trl = new ArrayList<>();
			final Set<String> adLanguageParamNames = new HashSet<>();
			for (final SqlDocumentFieldDataBindingDescriptor sqlField : fields)
			{
				//
				// Value column
				final String sqlSelectValue = buildSqlSelectValue(sqlField);
				sqlSelectValuesList.add(sqlSelectValue);

				//
				// Display column, if any
				if (sqlField.isUsingDisplayColumn())
				{
					final String displayColumnName = sqlField.getDisplayColumnName();
					final IStringExpression displayColumnSqlExpression = sqlField.getDisplayColumnSqlExpression();

					{
						final String adLanguageParamName = TranslatableParameterizedStringExpression.getAD_LanguageParamName(displayColumnSqlExpression);
						if (!Check.isEmpty(adLanguageParamName))
						{
							adLanguageParamNames.add(adLanguageParamName);
						}
					}

					{
						final String displayColumnSql_BaseLang = TranslatableParameterizedStringExpression.getExpressionBaseLang(displayColumnSqlExpression).getExpressionString();
						final String sqlSelectDisplayName_BaseLang = "(" + displayColumnSql_BaseLang + ") AS " + displayColumnName;
						sqlSelectDisplayNamesList_BaseLang.add(sqlSelectDisplayName_BaseLang);
					}

					{
						final String displayColumnSql_Trl = TranslatableParameterizedStringExpression.getExpressionTrl(displayColumnSqlExpression).getExpressionString();
						final String sqlSelectDisplayName_Trl = "(" + displayColumnSql_Trl + ") AS " + displayColumnName;
						sqlSelectDisplayNamesList_Trl.add(sqlSelectDisplayName_Trl);
					}
				}
			}

			//
			//
			final CtxName adLanguageParamName = buildAD_LanguageParamName(adLanguageParamNames);
			final String sqlSelectValues = JOINER_SqlSelectFields.join(sqlSelectValuesList);
			final String sqlSelectDisplayNames_BaseLang = JOINER_SqlSelectFields.join(sqlSelectDisplayNamesList_BaseLang);
			final String sqlSelectDisplayNames_Trl = JOINER_SqlSelectFields.join(sqlSelectDisplayNamesList_Trl);
			_sqlSelectAll = TranslatableParameterizedString.of(
					adLanguageParamName // Language param
					, buildSqlSelect(sqlSelectValues, sqlSelectDisplayNames_BaseLang) // Base Lang
					, buildSqlSelect(sqlSelectValues, sqlSelectDisplayNames_Trl) // Trl
			);
			_sqlPagedSelectAll = TranslatableParameterizedString.of(
					adLanguageParamName // Language param
					, buildSqlPagedSelect(sqlSelectValues, sqlSelectDisplayNames_BaseLang) // Base Lang
					, buildSqlPagedSelect(sqlSelectValues, sqlSelectDisplayNames_Trl) // Trl
			);
		}

		private final String buildSqlSelectValue(final SqlDocumentFieldDataBindingDescriptor sqlField)
		{
			final String columnSql = sqlField.getSqlColumnSql();
			final String columnName = sqlField.getSqlColumnName();

			final boolean isVirtualColumn = !columnName.equals(columnSql);
			if (isVirtualColumn)
			{
				return columnSql + " AS " + columnName;
			}
			else
			{
				return getSqlTableName() + "." + columnSql + " AS " + columnName;

			}
		}

		private final String buildSqlSelect(final String sqlSelectValues, final String sqlSelectDisplayNames)
		{
			final String sqlTableName = getSqlTableName();
			final String sqlTableAlias = getSqlTableAlias();

			if (!sqlSelectDisplayNames.isEmpty())
			{
				return new StringBuilder()
						.append("SELECT ")
						.append("\n").append(sqlTableAlias).append(".*") // Value fields
						.append(", \n").append(sqlSelectDisplayNames) // DisplayName fields
						.append("\n FROM (SELECT ").append(sqlSelectValues).append(" FROM ").append(sqlTableName).append(") ").append(sqlTableAlias) // FROM
						.toString();
			}
			else
			{
				return new StringBuilder()
						.append("SELECT ")
						.append("\n").append(sqlSelectValues)
						.append("\n").append(" FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
						.toString();
			}
		}

		private final String buildSqlPagedSelect(final String sqlSelectValues, final String sqlSelectDisplayNames)
		{
			final String sqlTableName = getSqlTableName();
			final String sqlTableAlias = getSqlTableAlias();
			final String sqlKeyColumnName = getSqlKeyColumnName();

			if (!sqlSelectDisplayNames.isEmpty())
			{
				return "SELECT "
						+ "\n" + sqlTableAlias + ".*" // Value fields
						+ "\n , " + sqlSelectDisplayNames // DisplayName fields
						+ "\n FROM ("
						+ "\n   SELECT "
						+ "\n   " + sqlSelectValues
						+ "\n , sel." + I_T_Query_Selection.COLUMNNAME_Line + " AS " + COLUMNNAME_Paging_SeqNo
						+ "\n , sel." + I_T_Query_Selection.COLUMNNAME_UUID + " AS " + COLUMNNAME_Paging_UUID
						+ "\n , sel." + I_T_Query_Selection.COLUMNNAME_Record_ID + " AS " + COLUMNNAME_Paging_Record_ID
						+ "\n   FROM " + I_T_Query_Selection.Table_Name + " sel"
						+ "\n   LEFT OUTER JOIN " + sqlTableName + " ON (" + sqlTableName + "." + sqlKeyColumnName + " = sel." + I_T_Query_Selection.COLUMNNAME_Record_ID + ")"
						+ "\n ) " + sqlTableAlias // FROM
						;
			}
			else
			{
				return "SELECT "
						+ "\n " + sqlSelectValues
						+ "\n , sel." + I_T_Query_Selection.COLUMNNAME_Line + " AS " + COLUMNNAME_Paging_SeqNo
						+ "\n , sel." + I_T_Query_Selection.COLUMNNAME_UUID + " AS " + COLUMNNAME_Paging_UUID
						+ "\n , sel." + I_T_Query_Selection.COLUMNNAME_Record_ID + " AS " + COLUMNNAME_Paging_Record_ID
						+ "\n FROM " + I_T_Query_Selection.Table_Name + " sel"
						+ "\n LEFT OUTER JOIN " + sqlTableName + " " + sqlTableAlias + " ON (" + sqlTableAlias + "." + sqlKeyColumnName + " = sel." + I_T_Query_Selection.COLUMNNAME_Record_ID + ")";
			}
		}

		private static final CtxName buildAD_LanguageParamName(final Set<String> adLanguageParamNames)
		{
			if (adLanguageParamNames.isEmpty())
			{
				return null;
			}

			final String adLanguageParamName = ListUtils.singleElement(adLanguageParamNames);
			return CtxName.parse(adLanguageParamName);
		}

		private IStringExpression getSqlWhereClauseExpression()
		{
			if (_sqlWhereClauseExpression == null)
			{
				_sqlWhereClauseExpression = buildSqlWhereClause();
			}
			return _sqlWhereClauseExpression;
		}

		private IStringExpression buildSqlWhereClause()
		{
			if (Check.isEmpty(_sqlWhereClause, true))
			{
				return IStringExpression.NULL;
			}

			final String sqlWhereClausePrepared = _sqlWhereClause.trim()
					// NOTE: because current AD_Tab.WhereClause contain fully qualified TableNames, we shall replace them with our table alias
					// (e.g. "R_Request.SalesRep_ID=@#AD_User_ID@" shall become ""tableAlias.SalesRep_ID=@#AD_User_ID@"
					.replace(getSqlTableName() + ".", getSqlTableAlias() + ".") //
					;

			final IStringExpression sqlWhereClauseExpr = Services.get(IExpressionFactory.class).compileOrDefault(sqlWhereClausePrepared, IStringExpression.NULL, IStringExpression.class);
			return sqlWhereClauseExpr;
		}

		private String buildSqlOrderBy()
		{
			// Explicit ORDER BY
			if (_sqlOrderBy != null)
			{
				return _sqlOrderBy;
			}

			//
			// Build the ORDER BY from fields
			final String sqlOrderByBuilt = getFieldsByFieldName()
					.values()
					.stream()
					.filter(field -> field.isOrderBy())
					.sorted((field1, field2) -> field1.getOrderByPriority() - field2.getOrderByPriority())
					.map(field -> field.getSqlOrderBy())
					.collect(Collectors.joining(", "));
			if (!sqlOrderByBuilt.isEmpty())
			{
				return sqlOrderByBuilt;
			}

			return null;
		}

		public Builder setSqlTableName(final String sqlTableName)
		{
			assertNotBuilt();
			_sqlTableName = sqlTableName;
			return this;
		}

		public String getSqlTableName()
		{
			return _sqlTableName;
		}

		private Builder setSqlTableAlias(final String sqlTableAlias)
		{
			assertNotBuilt();
			_sqlTableAlias = sqlTableAlias;
			return this;
		}

		public Builder setSqlTableAliasFromDetailId(final String detailId)
		{
			if (detailId == null)
			{
				setSqlTableAlias(TABLEALIAS_Master);
			}
			else
			{
				setSqlTableAlias("d" + detailId.trim());
			}

			return this;
		}

		public String getSqlTableAlias()
		{
			return _sqlTableAlias;
		}

		public Builder setAD_Table_ID(final int AD_Table_ID)
		{
			assertNotBuilt();
			this.AD_Table_ID = AD_Table_ID;
			return this;
		}

		public Builder setSqlParentLinkColumnName(final String sqlParentLinkColumnName)
		{
			assertNotBuilt();
			_sqlParentLinkColumnName = sqlParentLinkColumnName;
			return this;
		}

		public String getSqlParentLinkColumnName()
		{
			return _sqlParentLinkColumnName;
		}

		public Builder setSqlWhereClause(final String sqlWhereClause)
		{
			assertNotBuilt();
			Check.assumeNotNull(sqlWhereClause, "Parameter sqlWhereClause is not null");
			_sqlWhereClause = sqlWhereClause;
			return this;
		}

		private Builder setSqlWhereClauseExpression(final IStringExpression sqlWhereClauseExpression)
		{
			assertNotBuilt();
			_sqlWhereClauseExpression = sqlWhereClauseExpression;
			return this;
		}

		public Builder setSqlOrderBy(final String sqlOrderBy)
		{
			assertNotBuilt();
			_sqlOrderBy = Check.isEmpty(sqlOrderBy, true) ? null : sqlOrderBy.trim();
			return this;
		}

		public Builder addField(final DocumentFieldDataBindingDescriptor field)
		{
			assertNotBuilt();
			Preconditions.checkNotNull(field, "field");

			final SqlDocumentFieldDataBindingDescriptor sqlField = (SqlDocumentFieldDataBindingDescriptor)field;
			_fieldsByFieldName.put(sqlField.getFieldName(), sqlField);

			if (sqlField.isKeyColumn())
			{
				Check.assumeNull(_keyField, "More than one key field is not allowed: {}, {}", _keyField, field);
				_keyField = sqlField;
			}

			return this;
		}

		private Builder addFields(final List<SqlDocumentFieldDataBindingDescriptor> fields)
		{
			if (fields == null || fields.isEmpty())
			{
				return this;
			}

			fields.stream().forEach(field -> addField(field));
			return this;
		}

		private Map<String, SqlDocumentFieldDataBindingDescriptor> getFieldsByFieldName()
		{
			return _fieldsByFieldName;
		}

		private String getSqlKeyColumnName()
		{
			return _keyField == null ? null : _keyField.getSqlColumnName();
		}
	}
}
