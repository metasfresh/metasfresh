package de.metas.ui.web.window.descriptor.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

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

	@JsonProperty("sqlTableName")
	private final String sqlTableName;
	@JsonProperty("sqlTableAlias")
	private final String sqlTableAlias;
	@JsonIgnore
	private final String sqlKeyColumnName;
	@JsonProperty("sqlParentLinkColumnName")
	private final String sqlParentLinkColumnName;

	@JsonIgnore
	private final String sqlSelectAllFrom;
	@JsonProperty("sqlWhereClause")
	private final IStringExpression sqlWhereClause;
	@JsonProperty("sqlOrderBy")
	private final String sqlOrderBy;

	@JsonProperty("fields")
	private final List<SqlDocumentFieldDataBindingDescriptor> fields;

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

		final SqlDocumentFieldDataBindingDescriptor keyField = builder.getKeyField();
		sqlKeyColumnName = keyField == null ? null : keyField.getSqlColumnName();

		sqlParentLinkColumnName = builder.getSqlParentLinkColumnName();

		fields = ImmutableList.copyOf(builder.getFields());

		sqlSelectAllFrom = builder.getSqlSelectAll();
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
				.add("fields", fields.isEmpty() ? null : fields)
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

	public String getSqlKeyColumnName()
	{
		return sqlKeyColumnName;
	}

	public String getSqlParentLinkColumnName()
	{
		return sqlParentLinkColumnName;
	}

	public String getSqlSelectAllFrom()
	{
		return sqlSelectAllFrom;
	}

	public IStringExpression getSqlWhereClause()
	{
		return sqlWhereClause;
	}

	public String getSqlOrderBy()
	{
		return sqlOrderBy;
	}

	public List<SqlDocumentFieldDataBindingDescriptor> getFields()
	{
		return fields;
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
		private String _sqlSelectAll = null; // will be built

		private final List<SqlDocumentFieldDataBindingDescriptor> _fields = new ArrayList<>();
		private SqlDocumentFieldDataBindingDescriptor _keyField;
		private final Set<String> _fieldNamesSideList = new HashSet<>();

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

		private String getSqlSelectAll()
		{
			if (_sqlSelectAll == null)
			{
				buildSqlSelects();
			}
			return _sqlSelectAll;
		}

		/**
		 * @return SELECT ... FROM ....
		 */
		private void buildSqlSelects()
		{
			final List<SqlDocumentFieldDataBindingDescriptor> fields = getFields();
			final Set<String> sideListFieldNames = getSideListFieldNames();

			if (fields.isEmpty())
			{
				throw new IllegalStateException("No SQL fields found");
			}

			final List<String> sqlSelectValues = new ArrayList<>(fields.size());
			final List<String> sqlSelectValues_SideList = new ArrayList<>();
			final List<String> sqlSelectDisplayNames = new ArrayList<>();
			final List<String> sqlSelectDisplayNames_SideList = new ArrayList<>();
			for (final SqlDocumentFieldDataBindingDescriptor sqlField : fields)
			{
				final String columnName = sqlField.getSqlColumnName();

				//
				// Value column
				final String sqlSelectValue = buildSqlSelectValue(sqlField);
				final boolean isSideListColumn = sideListFieldNames.contains(columnName);
				//
				sqlSelectValues.add(sqlSelectValue);
				if (isSideListColumn)
				{
					sqlSelectValues_SideList.add(sqlSelectValue);
				}

				//
				// Display column, if any
				if (sqlField.isUsingDisplayColumn())
				{
					final String displayColumnName = sqlField.getDisplayColumnName();
					final String displayColumnSql = sqlField.getDisplayColumnSql();
					final String sqlSelectDisplayName = "(" + displayColumnSql + ") AS " + displayColumnName;
					//
					sqlSelectDisplayNames.add(sqlSelectDisplayName);
					if (isSideListColumn)
					{
						sqlSelectDisplayNames_SideList.add(sqlSelectDisplayName);
					}
				}
			}

			//
			final String sqlSelectAll = buildSqlSelect(sqlSelectValues, sqlSelectDisplayNames);

			//
			//
			_sqlSelectAll = sqlSelectAll;
		}

		private final String buildSqlSelectValue(final SqlDocumentFieldDataBindingDescriptor sqlField)
		{
			final String columnSql = sqlField.getSqlColumnSql();
			final String columnName = sqlField.getSqlColumnName();
			final String sqlSelectValue = columnSql + " AS " + columnName;
			return sqlSelectValue;
		}

		private final String buildSqlSelect(final List<String> sqlSelectValuesList, final List<String> sqlSelectDisplayNamesList)
		{
			final String sqlTableName = getSqlTableName();
			final String sqlTableAlias = getSqlTableAlias();

			final String sqlSelectValues = JOINER_SqlSelectFields.join(sqlSelectValuesList);

			if (!sqlSelectDisplayNamesList.isEmpty())
			{
				final String sqlSelectDisplayNames = JOINER_SqlSelectFields.join(sqlSelectDisplayNamesList);
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
			final List<SqlDocumentFieldDataBindingDescriptor> fields = getFields();
			final String sqlOrderByBuilt = fields.stream()
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
			_fields.add(sqlField);

			if (sqlField.isKeyColumn())
			{
				Check.assumeNull(_keyField, "More than one key field is not allowed: {}, {}", _keyField, field);
				_keyField = sqlField;

				// Make sure the key column is part of the side list
				addSideListFieldName(sqlField.getSqlColumnName());
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

		private List<SqlDocumentFieldDataBindingDescriptor> getFields()
		{
			return _fields;
		}

		private SqlDocumentFieldDataBindingDescriptor getKeyField()
		{
			return _keyField;
		}

		public Builder addSideListFieldName(final String fieldName)
		{
			Preconditions.checkNotNull(fieldName, "fieldName");
			_fieldNamesSideList.add(fieldName);
			return this;
		}

		public Builder addSideListFieldNames(final Set<String> fieldNames)
		{
			if (fieldNames == null || fieldNames.isEmpty())
			{
				return this;
			}

			_fieldNamesSideList.addAll(fieldNames);
			return this;
		}

		private Set<String> getSideListFieldNames()
		{
			return _fieldNamesSideList;
		}

		public boolean isSideListFieldName(final String fieldName)
		{
			return _fieldNamesSideList.contains(fieldName);
		}
	}
}
