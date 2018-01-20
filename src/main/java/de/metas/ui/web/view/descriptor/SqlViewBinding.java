package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecorator;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConvertersList;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewRowCustomizer;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import lombok.NonNull;
import lombok.Singular;

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
	private final String _tableName;
	private final String _tableAlias;

	private final ImmutableMap<String, SqlViewRowFieldBinding> _fieldsByFieldName;
	private final SqlViewRowFieldBinding _keyField;
	private final ImmutableMap<String, DocumentFieldWidgetType> widgetTypesByFieldName;

	private final SqlViewSelectData sqlViewSelect;
	private final IStringExpression sqlWhereClause;
	private final List<SqlViewRowFieldLoader> rowFieldLoaders;
	private final ViewRowCustomizer rowCustomizer;

	private final ImmutableList<DocumentQueryOrderBy> defaultOrderBys;
	private final OrderByFieldNameAliasMap orderByFieldNameAliasMap;

	private final DocumentFilterDescriptorsProvider filterDescriptors;
	private final SqlDocumentFilterConvertersList filterConverters;

	private final SqlViewRowIdsConverter rowIdsConverter;

	private final SqlViewGroupingBinding groupingBinding;
	private final SqlDocumentFilterConverterDecorator filterConverterDecorator;

	public static final Builder builder()
	{
		return new Builder();
	}

	private SqlViewBinding(final Builder builder)
	{
		_tableName = builder.getTableName();
		_tableAlias = builder.getTableAlias();

		_fieldsByFieldName = ImmutableMap.copyOf(builder.getFieldsByFieldName());
		_keyField = builder.getKeyField();
		widgetTypesByFieldName = _fieldsByFieldName.values()
				.stream()
				.collect(ImmutableMap.toImmutableMap(SqlViewRowFieldBinding::getFieldName, SqlViewRowFieldBinding::getWidgetType));

		final Collection<String> displayFieldNames = builder.getDisplayFieldNames();

		final Collection<SqlViewRowFieldBinding> allFields = _fieldsByFieldName.values();
		this.groupingBinding = builder.groupingBinding;
		sqlViewSelect = SqlViewSelectData.builder()
				.sqlTableName(_tableName)
				.sqlTableAlias(_tableAlias)
				.keyField(_keyField)
				.displayFieldNames(displayFieldNames)
				.allFields(allFields)
				.groupingBinding(groupingBinding)
				.build();
		sqlWhereClause = builder.getSqlWhereClause();

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
		this.rowCustomizer = builder.getRowCustomizer();

		orderByFieldNameAliasMap = builder.buildOrderByFieldNameAliasMap();
		defaultOrderBys = ImmutableList.copyOf(builder.getDefaultOrderBys());

		filterDescriptors = builder.getViewFilterDescriptors();
		filterConverters = builder.buildViewFilterConverters();

		filterConverterDecorator = builder.sqlDocumentFilterConverterDecorator;

		rowIdsConverter = builder.getRowIdsConverter();
	}

	@Override
	public String toString() // NOTE: keep it short
	{
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
		return Preconditions.checkNotNull(_keyField, "View %s does not have a key column defined", this);
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

	public Map<String, DocumentFieldWidgetType> getWidgetTypesByFieldName()
	{
		return widgetTypesByFieldName;
	}

	public SqlViewSelectData getSqlViewSelect()
	{
		return sqlViewSelect;
	}

	@Override
	public IStringExpression getSqlWhereClause()
	{
		return sqlWhereClause;
	}

	public List<SqlViewRowFieldLoader> getRowFieldLoaders()
	{
		return rowFieldLoaders;
	}

	public ViewRowCustomizer getRowCustomizer()
	{
		return rowCustomizer;
	}

	@Override
	public DocumentFilterDescriptorsProvider getFilterDescriptors()
	{
		return getViewFilterDescriptors();
	}

	public DocumentFilterDescriptorsProvider getViewFilterDescriptors()
	{
		return filterDescriptors;
	}

	@Override
	public SqlDocumentFilterConvertersList getFilterConverters()
	{
		return filterConverters;
	}

	@Override
	public SqlDocumentFilterConverterDecorator getFilterConverterDecoratorOrNull()
	{
		return filterConverterDecorator;
	}

	public SqlViewRowIdsConverter getRowIdsConverter()
	{
		return rowIdsConverter;
	}

	public ImmutableList<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return defaultOrderBys;
	}

	public final Stream<DocumentQueryOrderBy> flatMapEffectiveFieldNames(final DocumentQueryOrderBy orderBy)
	{
		return orderByFieldNameAliasMap.flatMapEffectiveFieldNames(orderBy);
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

	@lombok.Value
	private static final class OrderByFieldNameAliasMap
	{
		private final ImmutableMap<String, ImmutableList<String>> orderByAliasFieldNames;

		@lombok.Builder
		private OrderByFieldNameAliasMap(@NonNull @Singular final Map<String, List<String>> orderByAliasFieldNames)
		{
			this.orderByAliasFieldNames = orderByAliasFieldNames.entrySet()
					.stream()
					.map(e -> GuavaCollectors.entry(e.getKey(), ImmutableList.copyOf(e.getValue())))
					.collect(GuavaCollectors.toImmutableMap());
		}

		public Stream<DocumentQueryOrderBy> flatMapEffectiveFieldNames(@NonNull final DocumentQueryOrderBy orderBy)
		{
			final List<String> aliasFieldNames = orderByAliasFieldNames.get(orderBy.getFieldName());
			if (aliasFieldNames == null || aliasFieldNames.isEmpty())
			{
				return Stream.of(orderBy);
			}

			return aliasFieldNames.stream().map(orderBy::copyOverridingFieldName);
		}
	}

	//
	//
	// -----------
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
		private ViewRowCustomizer rowCustomizer;

		private ArrayList<DocumentQueryOrderBy> defaultOrderBys;
		private OrderByFieldNameAliasMap.OrderByFieldNameAliasMapBuilder orderByFieldNameAliasMap = OrderByFieldNameAliasMap.builder();
		private DocumentFilterDescriptorsProvider filterDescriptors = NullDocumentFilterDescriptorsProvider.instance;
		private SqlDocumentFilterConvertersList.Builder filterConverters = null;

		private SqlViewRowIdsConverter rowIdsConverter = null;

		private SqlViewGroupingBinding groupingBinding;
		private SqlDocumentFilterConverterDecorator sqlDocumentFilterConverterDecorator = null;

		private Builder()
		{
		}

		public SqlViewBinding build()
		{
			return new SqlViewBinding(this);
		}

		public Builder tableName(final String sqlTableName)
		{
			_sqlTableName = sqlTableName;
			return this;
		}

		private String getTableName()
		{
			return _sqlTableName;
		}

		public Builder tableAlias(final String sqlTableAlias)
		{
			_tableAlias = sqlTableAlias;
			return this;
		}

		public String getTableAlias()
		{
			if (_tableAlias == null)
			{
				return getTableName();
			}
			return _tableAlias;
		}

		public Builder sqlWhereClause(final IStringExpression sqlWhereClause)
		{
			this.sqlWhereClause = sqlWhereClause == null ? IStringExpression.NULL : sqlWhereClause;
			return this;
		}

		public Builder sqlWhereClause(final String sqlWhereClause)
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

		public Builder displayFieldNames(final Collection<String> displayFieldNames)
		{
			this.displayFieldNames = displayFieldNames;
			return this;
		}

		public Builder displayFieldNames(final String... displayFieldNames)
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

		public final Builder field(final SqlViewRowFieldBinding field)
		{
			Check.assumeNotNull(field, "Parameter field is not null");

			_fieldsByFieldName.put(field.getFieldName(), field);
			if (field.isKeyColumn())
			{
				_keyField = field;
			}
			return this;
		}

		public Builder clearDefaultOrderBys()
		{
			defaultOrderBys = null;
			return this;
		}

		public Builder defaultOrderBys(final List<DocumentQueryOrderBy> defaultOrderBys)
		{
			this.defaultOrderBys = defaultOrderBys != null ? new ArrayList<>(defaultOrderBys) : null;
			return this;
		}

		public Builder defaultOrderBy(@NonNull final DocumentQueryOrderBy defaultOrderBy)
		{
			if (defaultOrderBys == null)
			{
				defaultOrderBys = new ArrayList<>();
			}
			defaultOrderBys.add(defaultOrderBy);
			return this;
		}

		private List<DocumentQueryOrderBy> getDefaultOrderBys()
		{
			return defaultOrderBys == null ? ImmutableList.of() : defaultOrderBys;
		}

		private OrderByFieldNameAliasMap buildOrderByFieldNameAliasMap()
		{
			return orderByFieldNameAliasMap.build();
		}

		public Builder orderByAliasFieldNames(@NonNull final String fieldName, @NonNull final String... aliasFieldNames)
		{
			Check.assumeNotEmpty(aliasFieldNames, "aliasFieldNames is not empty");
			orderByFieldNameAliasMap.orderByAliasFieldName(fieldName, ImmutableList.copyOf(aliasFieldNames));
			return this;
		}

		public Builder filterDescriptors(@NonNull final DocumentFilterDescriptorsProvider filterDescriptors)
		{
			this.filterDescriptors = filterDescriptors;
			return this;
		}

		private DocumentFilterDescriptorsProvider getViewFilterDescriptors()
		{
			return filterDescriptors;
		}

		public Builder filterConverter(
				@NonNull final String filterId,
				@NonNull final SqlDocumentFilterConverter converter)
		{
			if (filterConverters == null)
			{
				filterConverters = SqlDocumentFilterConverters.listBuilder();
			}
			filterConverters.addConverter(filterId, converter);
			return this;
		}

		private SqlDocumentFilterConvertersList buildViewFilterConverters()
		{
			if (filterConverters == null)
			{
				return SqlDocumentFilterConverters.emptyList();
			}
			return filterConverters.build();
		}

		public Builder rowIdsConverter(@NonNull SqlViewRowIdsConverter rowIdsConverter)
		{
			this.rowIdsConverter = rowIdsConverter;
			return this;
		}

		private SqlViewRowIdsConverter getRowIdsConverter()
		{
			if (rowIdsConverter != null)
			{
				return rowIdsConverter;
			}
			if (groupingBinding != null)
			{
				return groupingBinding.getRowIdsConverter();
			}
			return SqlViewRowIdsConverters.TO_INT_STRICT;
		}

		public Builder groupingBinding(SqlViewGroupingBinding groupingBinding)
		{
			this.groupingBinding = groupingBinding;
			return this;
		}

		public Builder filterConverterDecorator(@NonNull final SqlDocumentFilterConverterDecorator sqlDocumentFilterConverterDecorator)
		{
			this.sqlDocumentFilterConverterDecorator = sqlDocumentFilterConverterDecorator;
			return this;
		}

		public Builder rowCustomizer(ViewRowCustomizer rowCustomizer)
		{
			this.rowCustomizer = rowCustomizer;
			return this;
		}

		private ViewRowCustomizer getRowCustomizer()
		{
			return rowCustomizer;
		}
	}
}
