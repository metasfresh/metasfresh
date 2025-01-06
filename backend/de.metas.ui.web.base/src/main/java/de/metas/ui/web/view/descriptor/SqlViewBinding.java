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

package de.metas.ui.web.view.descriptor;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.accounting.filters.FactAcctFilterConverter;
import de.metas.ui.web.bpartner.filter.BPartnerExportFilterConverter;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.fullTextSearch.FTSDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecorator;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConvertersList;
import de.metas.ui.web.document.geo_location.GeoLocationFilterConverter;
import de.metas.ui.web.view.DefaultViewInvalidationAdvisor;
import de.metas.ui.web.view.IViewInvalidationAdvisor;
import de.metas.ui.web.view.ViewRowCustomizer;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SqlViewBinding implements SqlEntityBinding
{
	private final String _tableName;
	private final String _tableAlias;

	private final ImmutableMap<String, SqlViewRowFieldBinding> _fieldsByFieldName;
	@Getter private final ImmutableMap<String, DocumentFieldWidgetType> widgetTypesByFieldName;

	private final SqlViewKeyColumnNamesMap keyColumnNamesMap;

	@Getter private final SqlViewSelectData sqlViewSelect;
	private final IStringExpression sqlWhereClause;
	@Getter private final ViewRowCustomizer rowCustomizer;

	@Getter private final DocumentQueryOrderByList defaultOrderBys;
	private final OrderByFieldNameAliasMap orderByFieldNameAliasMap;

	private final DocumentFilterDescriptorsProvider filterDescriptors;
	private final SqlDocumentFilterConvertersList filterConverters;
	@Getter private final boolean refreshViewOnChangeEvents;

	@Getter private final SqlViewRowIdsConverter rowIdsConverter;

	private final SqlViewGroupingBinding groupingBinding;
	private final Optional<SqlDocumentFilterConverterDecorator> filterConverterDecorator;

	@Getter private final IViewInvalidationAdvisor viewInvalidationAdvisor;

	@NonNull @Getter private final ImmutableMap<DetailId, SqlDocumentEntityDataBindingDescriptor> includedEntitiesDescriptors;

	@Getter
	private final boolean queryIfNoFilters;

	public static Builder builder()
	{
		return new Builder();
	}

	@NonNull
	public static ImmutableMap<DetailId, SqlDocumentEntityDataBindingDescriptor> getIncludedEntitiesDescriptors(@NonNull final SqlEntityBinding binding)
	{
		return binding instanceof SqlViewBinding ? ((SqlViewBinding)binding).getIncludedEntitiesDescriptors() : ImmutableMap.of();
	}

	private SqlViewBinding(final Builder builder)
	{
		_tableName = builder.getTableName();
		_tableAlias = builder.getTableAlias();

		_fieldsByFieldName = ImmutableMap.copyOf(builder.getFieldsByFieldName());
		keyColumnNamesMap = builder.getSqlViewKeyColumnNamesMap();
		widgetTypesByFieldName = _fieldsByFieldName.values()
				.stream()
				.collect(ImmutableMap.toImmutableMap(SqlViewRowFieldBinding::getFieldName, SqlViewRowFieldBinding::getWidgetType));

		final Collection<String> displayFieldNames = builder.getDisplayFieldNames();

		final Collection<SqlViewRowFieldBinding> allFields = _fieldsByFieldName.values();
		this.groupingBinding = builder.groupingBinding;
		sqlViewSelect = SqlViewSelectData.builder()
				.sqlTableName(_tableName)
				.sqlTableAlias(_tableAlias)
				.keyColumnNamesMap(keyColumnNamesMap)
				.displayFieldNames(displayFieldNames)
				.allFields(allFields)
				.groupingBinding(groupingBinding)
				.build();
		sqlWhereClause = builder.getSqlWhereClause();

		this.rowCustomizer = builder.getRowCustomizer();

		orderByFieldNameAliasMap = builder.buildOrderByFieldNameAliasMap();
		defaultOrderBys = builder.getDefaultOrderBys();

		filterDescriptors = builder.getViewFilterDescriptors();
		filterConverters = builder.buildViewFilterConverters();

		filterConverterDecorator = Optional.ofNullable(builder.sqlDocumentFilterConverterDecorator);

		refreshViewOnChangeEvents = builder.refreshViewOnChangeEvents;

		rowIdsConverter = builder.getRowIdsConverter();

		viewInvalidationAdvisor = builder.getViewInvalidationAdvisor();

		queryIfNoFilters = builder.queryIfNoFilters;

		includedEntitiesDescriptors = builder.includedEntitiesDescriptors != null
				? ImmutableMap.copyOf(builder.includedEntitiesDescriptors)
				: ImmutableMap.of();
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

	public SqlViewKeyColumnNamesMap getSqlViewKeyColumnNamesMap()
	{
		return keyColumnNamesMap;
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
	public Optional<SqlDocumentFilterConverterDecorator> getFilterConverterDecorator()
	{
		return filterConverterDecorator;
	}

	public final Stream<DocumentQueryOrderBy> flatMapEffectiveFieldNames(final DocumentQueryOrderBy orderBy)
	{
		return orderByFieldNameAliasMap.flatMapEffectiveFieldNames(orderBy);
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
	public SqlSelectValue getSqlAggregatedColumn(final String fieldName)
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
	private static class OrderByFieldNameAliasMap
	{
		ImmutableMap<String, ImmutableList<String>> orderByAliasFieldNames;

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

	@SuppressWarnings("UnusedReturnValue")
	public static final class Builder
	{
		private String _sqlTableName;
		private String _tableAlias;
		private IStringExpression sqlWhereClause = IStringExpression.NULL;

		private Collection<String> displayFieldNames;
		private final Map<String, SqlViewRowFieldBinding> _fieldsByFieldName = new LinkedHashMap<>();
		private ViewRowCustomizer rowCustomizer;

		@Nullable
		private ArrayList<DocumentQueryOrderBy> defaultOrderBys;
		private final OrderByFieldNameAliasMap.OrderByFieldNameAliasMapBuilder orderByFieldNameAliasMap = OrderByFieldNameAliasMap.builder();
		private DocumentFilterDescriptorsProvider filterDescriptors = NullDocumentFilterDescriptorsProvider.instance;
		private final SqlDocumentFilterConvertersList.Builder filterConverters = SqlDocumentFilterConverters.listBuilder();
		private boolean refreshViewOnChangeEvents;

		private SqlViewRowIdsConverter rowIdsConverter = null;

		private SqlViewGroupingBinding groupingBinding;
		private SqlDocumentFilterConverterDecorator sqlDocumentFilterConverterDecorator = null;

		private IViewInvalidationAdvisor viewInvalidationAdvisor = DefaultViewInvalidationAdvisor.instance;
		private Map<DetailId, SqlDocumentEntityDataBindingDescriptor> includedEntitiesDescriptors;

		private boolean queryIfNoFilters = true;

		private Builder()
		{
			filterConverters.converter(FTSDocumentFilterConverter.instance);
			filterConverters.converter(GeoLocationFilterConverter.instance);
			filterConverters.converter(FactAcctFilterConverter.instance);
			// filterConverters2.whenFilterIdStartsWith(FacetsDocumentFilterDescriptorsProviderFactory.FILTER_ID_PREFIX, converter);
			filterConverters.converter(BPartnerExportFilterConverter.instance);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("tableName", _sqlTableName)
					.toString();
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
			Check.assumeNotEmpty(_sqlTableName, "sqlTableName is not empty");
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

		private SqlViewKeyColumnNamesMap getSqlViewKeyColumnNamesMap()
		{
			final ImmutableList<SqlViewRowFieldBinding> keyFields = getKeyFields();
			if (keyFields.isEmpty())
			{
				throw new AdempiereException("No key columns defined for " + getTableName());
			}
			return SqlViewKeyColumnNamesMap.ofKeyFields(keyFields);
		}

		private ImmutableList<SqlViewRowFieldBinding> getKeyFields()
		{
			return getFieldsByFieldName()
					.values()
					.stream()
					.filter(SqlViewRowFieldBinding::isKeyColumn)
					.collect(ImmutableList.toImmutableList());
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

		public Builder fields(@NonNull final Collection<SqlViewRowFieldBinding> fields)
		{
			fields.forEach(this::field);
			return this;
		}

		public Builder field(@NonNull final SqlViewRowFieldBinding field)
		{
			_fieldsByFieldName.put(field.getFieldName(), field);
			return this;
		}

		public Builder clearDefaultOrderBys()
		{
			defaultOrderBys = null;
			return this;
		}

		public Builder defaultOrderBys(final DocumentQueryOrderByList defaultOrderBys)
		{
			this.defaultOrderBys = defaultOrderBys != null ? new ArrayList<>(defaultOrderBys.toList()) : null;
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

		private DocumentQueryOrderByList getDefaultOrderBys()
		{
			return DocumentQueryOrderByList.ofList(defaultOrderBys);
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

		private SqlDocumentFilterConvertersList buildViewFilterConverters()
		{
			return filterConverters.build();
		}

		public Builder filterConverter(@NonNull final SqlDocumentFilterConverter converter)
		{
			filterConverters.converter(converter);
			return this;
		}

		public Builder filterConverters(@NonNull final List<SqlDocumentFilterConverter> converters)
		{
			filterConverters.converters(converters);
			return this;
		}

		public Builder rowIdsConverter(@NonNull final SqlViewRowIdsConverter rowIdsConverter)
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

		public Builder groupingBinding(final SqlViewGroupingBinding groupingBinding)
		{
			this.groupingBinding = groupingBinding;
			return this;
		}

		public Builder filterConverterDecorator(@NonNull final SqlDocumentFilterConverterDecorator sqlDocumentFilterConverterDecorator)
		{
			this.sqlDocumentFilterConverterDecorator = sqlDocumentFilterConverterDecorator;
			return this;
		}

		public Builder rowCustomizer(final ViewRowCustomizer rowCustomizer)
		{
			this.rowCustomizer = rowCustomizer;
			return this;
		}

		private ViewRowCustomizer getRowCustomizer()
		{
			return rowCustomizer;
		}

		public Builder viewInvalidationAdvisor(@NonNull final IViewInvalidationAdvisor viewInvalidationAdvisor)
		{
			this.viewInvalidationAdvisor = viewInvalidationAdvisor;
			return this;
		}

		private IViewInvalidationAdvisor getViewInvalidationAdvisor()
		{
			return viewInvalidationAdvisor;
		}

		public Builder refreshViewOnChangeEvents(final boolean refreshViewOnChangeEvents)
		{
			this.refreshViewOnChangeEvents = refreshViewOnChangeEvents;
			return this;
		}

		public Builder queryIfNoFilters(final boolean queryIfNoFilters)
		{
			this.queryIfNoFilters = queryIfNoFilters;
			return this;
		}

		public Builder includedEntitiesDescriptors(final Map<DetailId, SqlDocumentEntityDataBindingDescriptor> includedEntitiesDescriptors)
		{
			this.includedEntitiesDescriptors = includedEntitiesDescriptors;
			return this;
		}

	}
}
