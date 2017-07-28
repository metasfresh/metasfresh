package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConvertersList;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class SqlViewBinding implements SqlEntityBinding
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String _tableName;
	private final String _tableAlias;

	private final ImmutableMap<String, SqlViewRowFieldBinding> _fieldsByFieldName;
	private final SqlViewRowFieldBinding _keyField;

	private final IStringExpression sqlWhereClause;
	private final IStringExpression sqlSelectByPage;
	private final IStringExpression sqlSelectRowIdsByPage;
	private final IStringExpression sqlSelectById;
	private final IStringExpression sqlSelectLinesByRowIds;
	private final List<SqlViewRowFieldLoader> rowFieldLoaders;

	private final ImmutableList<DocumentQueryOrderBy> defaultOrderBys;
	private final DocumentFilterDescriptorsProvider viewFilterDescriptors;
	private final SqlDocumentFilterConvertersList viewFilterConverters;

	private final SqlViewRowIdsConverter rowIdsConverter;

	private final SqlViewGroupingBinding groupingBinding;

	private SqlViewBinding(final Builder builder)
	{
		super();
		_tableName = builder.getTableName();
		_tableAlias = builder.getTableAlias();

		_fieldsByFieldName = ImmutableMap.copyOf(builder.getFieldsByFieldName());
		_keyField = builder.getKeyField();

		final Collection<String> displayFieldNames = builder.getDisplayFieldNames();

		final Collection<SqlViewRowFieldBinding> allFields = _fieldsByFieldName.values();
		this.groupingBinding = builder.groupingBinding;
		final IStringExpression sqlSelect = SqlViewSelectionQueryBuilder.buildSqlSelect(_tableName, _tableAlias, _keyField.getColumnName(), displayFieldNames, allFields, groupingBinding);

		sqlWhereClause = builder.getSqlWhereClause();
		sqlSelectByPage = sqlSelect.toComposer()
				.append("\n WHERE ")
				// .append("\n " + SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_UUID + "=?") // already filtered above
				.append("\n " + SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_SeqNo + " BETWEEN ? AND ?")
				.append("\n ORDER BY " + SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_SeqNo)
				.build();

		sqlSelectRowIdsByPage = SqlViewSelectionQueryBuilder.buildSqlSelect(_tableName, _tableAlias,
				_keyField.getColumnName(),
				ImmutableList.of(), // displayFieldNames
				ImmutableList.of(_keyField), // allFields
				groupingBinding)
				//
				.toComposer()
				.append("\n WHERE ")
				// .append("\n " + SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_UUID + "=?") // already filtered above
				.append("\n " + SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_SeqNo + " BETWEEN ? AND ?")
				.append("\n ORDER BY " + SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_SeqNo)
				.build();

		sqlSelectById = sqlSelect.toComposer()
				.append("\n WHERE ")
				// .append("\n " + SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_UUID + "=?") // already filtered above
				.append("\n " + SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_Record_ID + "=?")
				.build();

		sqlSelectLinesByRowIds = SqlViewSelectionQueryBuilder.buildSqlSelectLines(_tableName, _tableAlias, _keyField.getColumnName(), displayFieldNames, allFields)
				.toComposer()
				.append("\n WHERE ")
				// .append("\n " + SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_UUID + "=?") // already filtered above
				.append("\n " + SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_Record_ID + " IN ").append(SqlViewSelectionQueryBuilder.Paging_Record_IDsPlaceholder)
				.build();

		final List<SqlViewRowFieldLoader> rowFieldLoaders = new ArrayList<>(allFields.size());
		for (final SqlViewRowFieldBinding field : allFields)
		{
			final boolean keyColumn = field.isKeyColumn();
			final SqlViewRowFieldLoader rowFieldLoader = field.getFieldLoader();

			if (keyColumn)
			{
				// If it's key column, add it first, because in case the record is missing, we want to fail fast
				rowFieldLoaders.add(0, rowFieldLoader);
			}
			else
			{
				rowFieldLoaders.add(rowFieldLoader);
			}
		}
		this.rowFieldLoaders = ImmutableList.copyOf(rowFieldLoaders);

		defaultOrderBys = ImmutableList.copyOf(builder.getDefaultOrderBys());
		viewFilterDescriptors = builder.getViewFilterDescriptors();
		viewFilterConverters = builder.buildViewFilterConverters();
		
		rowIdsConverter = builder.getRowIdsConverter();
	}

	@Override
	public String toString()
	{
		// NOTE: keep it short
		return MoreObjects.toStringHelper(this)
				.add("tableName", _tableName)
				.toString();
	}

	@Override
	public String getTableName()
	{
		return _tableName;
	}

	@Override
	public String getTableAlias()
	{
		return _tableAlias;
	}

	private SqlViewRowFieldBinding getKeyField()
	{
		Preconditions.checkNotNull(_keyField, "View %s does not have a key column defined", this);
		return _keyField;
	}

	@Override
	public String getKeyColumnName()
	{
		return getKeyField().getColumnName();
	}

	public boolean isKeyFieldName(final String fieldName)
	{
		if (_keyField == null)
		{
			return false;
		}
		return Objects.equal(fieldName, _keyField.getFieldName());
	}

	public Collection<SqlViewRowFieldBinding> getFields()
	{
		return _fieldsByFieldName.values();
	}

	@Override
	public SqlViewRowFieldBinding getFieldByFieldName(final String fieldName)
	{
		final SqlViewRowFieldBinding field = _fieldsByFieldName.get(fieldName);
		if (field == null)
		{
			throw new IllegalArgumentException("No field found for '" + fieldName + "' in " + this);
		}
		return field;
	}

	@Override
	public IStringExpression getSqlWhereClause()
	{
		return sqlWhereClause;
	}

	public IStringExpression getSqlSelectByPage()
	{
		return sqlSelectByPage;
	}

	public IStringExpression getSqlSelectRowIdsByPage()
	{
		return sqlSelectRowIdsByPage;
	}

	public IStringExpression getSqlSelectById()
	{
		return sqlSelectById;
	}

	public IStringExpression getSqlSelectLinesByRowIds()
	{
		return sqlSelectLinesByRowIds;
	}

	public List<SqlViewRowFieldLoader> getRowFieldLoaders()
	{
		return rowFieldLoaders;
	}

	public DocumentFilterDescriptorsProvider getViewFilterDescriptors()
	{
		return viewFilterDescriptors;
	}

	@Override
	public SqlDocumentFilterConvertersList getFilterConverters()
	{
		return viewFilterConverters;
	}
	
	public SqlViewRowIdsConverter getRowIdsConverter()
	{
		return rowIdsConverter;
	}

	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return defaultOrderBys;
	}

	public Map<String, String> getSqlOrderBysIndexedByFieldName(final ViewEvaluationCtx viewEvalCtx)
	{
		final ImmutableMap.Builder<String, String> sqlOrderBysIndexedByFieldName = ImmutableMap.builder();
		for (final SqlViewRowFieldBinding fieldBinding : getFields())
		{
			final String fieldOrderBy = fieldBinding.getSqlOrderBy().evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);
			if (Check.isEmpty(fieldOrderBy, true))
			{
				continue;
			}

			final String fieldName = fieldBinding.getFieldName();
			sqlOrderBysIndexedByFieldName.put(fieldName, fieldOrderBy);
		}

		return sqlOrderBysIndexedByFieldName.build();
	}

	public Set<String> getGroupByFieldNames()
	{
		if (groupingBinding == null)
		{
			return ImmutableSet.of();
		}
		return groupingBinding.getGroupByFieldNames();
	}

	public boolean hasGroupingFields()
	{
		return !getGroupByFieldNames().isEmpty();
	}

	public boolean isGroupBy(final String fieldName)
	{
		return getGroupByFieldNames().contains(fieldName);
	}

	@Nullable
	public String getSqlAggregatedColumn(final String fieldName)
	{
		if (groupingBinding == null)
		{
			return null;
		}
		return groupingBinding.getColumnSqlByFieldName(fieldName);
	}

	public boolean isAggregated(final String fieldName)
	{
		if (groupingBinding == null)
		{
			return false;
		}
		return groupingBinding.isAggregated(fieldName);
	}

	//
	//
	//
	//
	//

	public static final class Builder
	{
		private String _sqlTableName;
		private String _tableAlias;
		private IStringExpression sqlWhereClause = IStringExpression.NULL;

		private Collection<String> displayFieldNames;
		private final Map<String, SqlViewRowFieldBinding> _fieldsByFieldName = new LinkedHashMap<>();
		private SqlViewRowFieldBinding _keyField;

		private List<DocumentQueryOrderBy> defaultOrderBys;
		private DocumentFilterDescriptorsProvider viewFilterDescriptors = NullDocumentFilterDescriptorsProvider.instance;
		private SqlDocumentFilterConvertersList.Builder viewFilterConverters = null;
		
		private SqlViewRowIdsConverter rowIdsConverter = DefaultSqlViewRowIdsConverter.instance;


		private SqlViewGroupingBinding groupingBinding;

		private Builder()
		{
			super();
		}

		public SqlViewBinding build()
		{
			return new SqlViewBinding(this);
		}

		public Builder setTableName(final String sqlTableName)
		{
			_sqlTableName = sqlTableName;
			return this;
		}

		private String getTableName()
		{
			return _sqlTableName;
		}

		public Builder setTableAlias(final String sqlTableAlias)
		{
			_tableAlias = sqlTableAlias;
			return this;
		}

		private String getTableAlias()
		{
			if (_tableAlias == null)
			{
				return getTableName();
			}
			return _tableAlias;
		}

		public Builder setSqlWhereClause(final IStringExpression sqlWhereClause)
		{
			this.sqlWhereClause = sqlWhereClause == null ? IStringExpression.NULL : sqlWhereClause;
			return this;
		}

		public Builder setSqlWhereClause(final String sqlWhereClause)
		{
			this.sqlWhereClause = ConstantStringExpression.ofNullable(sqlWhereClause);
			return this;
		}

		private IStringExpression getSqlWhereClause()
		{
			return sqlWhereClause;
		}

		private SqlViewRowFieldBinding getKeyField()
		{
			if (_keyField == null)
			{
				throw new IllegalStateException("No key field was configured for " + this);
			}
			return _keyField;
		}

		public Builder setDisplayFieldNames(final Collection<String> displayFieldNames)
		{
			this.displayFieldNames = displayFieldNames;
			return this;
		}

		public Builder setDisplayFieldNames(final String... displayFieldNames)
		{
			this.displayFieldNames = ImmutableSet.copyOf(displayFieldNames);
			return this;
		}

		private Collection<String> getDisplayFieldNames()
		{
			if (displayFieldNames == null || displayFieldNames.isEmpty())
			{
				throw new IllegalStateException("No display field names configured for " + this);
			}
			return displayFieldNames;
		}

		private Map<String, SqlViewRowFieldBinding> getFieldsByFieldName()
		{
			return _fieldsByFieldName;
		}

		public final Builder addField(final SqlViewRowFieldBinding field)
		{
			Check.assumeNotNull(field, "Parameter field is not null");

			_fieldsByFieldName.put(field.getFieldName(), field);
			if (field.isKeyColumn())
			{
				_keyField = field;
			}
			return this;
		}

		public Builder setOrderBys(final List<DocumentQueryOrderBy> defaultOrderBys)
		{
			this.defaultOrderBys = defaultOrderBys;
			return this;
		}

		private List<DocumentQueryOrderBy> getDefaultOrderBys()
		{
			return defaultOrderBys == null ? ImmutableList.of() : defaultOrderBys;
		}

		public Builder setViewFilterDescriptors(@NonNull final DocumentFilterDescriptorsProvider viewFilterDescriptors)
		{
			this.viewFilterDescriptors = viewFilterDescriptors;
			return this;
		}

		private DocumentFilterDescriptorsProvider getViewFilterDescriptors()
		{
			return viewFilterDescriptors;
		}

		public Builder addViewFilterConverter(final String filterId, final SqlDocumentFilterConverter converter)
		{
			if (viewFilterConverters == null)
			{
				viewFilterConverters = SqlDocumentFilterConverters.listBuilder();
			}
			viewFilterConverters.addConverter(filterId, converter);
			return this;
		}

		private SqlDocumentFilterConvertersList buildViewFilterConverters()
		{
			if (viewFilterConverters == null)
			{
				return SqlDocumentFilterConverters.emptyList();
			}
			return viewFilterConverters.build();
		}
		
		public Builder setRowIdsConverter(@NonNull SqlViewRowIdsConverter rowIdsConverter)
		{
			this.rowIdsConverter = rowIdsConverter;
			return this;
		}
		
		private SqlViewRowIdsConverter getRowIdsConverter()
		{
			return rowIdsConverter;
		}

		public Builder setGroupingBinding(SqlViewGroupingBinding groupingBinding)
		{
			this.groupingBinding = groupingBinding;
			return this;
		}
	}
}
