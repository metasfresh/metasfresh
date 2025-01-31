package de.metas.ui.web.view.descriptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecorator;
import de.metas.ui.web.view.DefaultViewInvalidationAdvisor;
import de.metas.ui.web.view.IViewInvalidationAdvisor;
import de.metas.ui.web.view.SqlViewCustomizer;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.sql.DocumentFieldValueLoader;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class SqlViewBindingFactory
{
	private static final Logger logger = LogManager.getLogger(SqlViewBindingFactory.class);

	private final DocumentDescriptorFactory documentDescriptorFactory;
	private final ImmutableList<SqlDocumentFilterConverter> filterConverters;
	private final ImmutableMap<WindowId, SqlDocumentFilterConverterDecorator> filterConvertorDecorators;
	private final ImmutableMap<WindowId, IViewInvalidationAdvisor> viewInvalidationAdvisorsByWindowId;
	private final SqlViewCustomizerMap viewCustomizers;

	private final CCache<SqlViewBindingKey, SqlViewBinding> cache = CCache.newCache("SqlViewBindings", 20, 0);

	@Builder
	private SqlViewBindingFactory(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final SqlViewCustomizerMap viewCustomizers,
			@NonNull final List<SqlDocumentFilterConverter> filterConverters,
			@NonNull final List<SqlDocumentFilterConverterDecorator> filterConverterDecorators,
			@NonNull final List<IViewInvalidationAdvisor> viewInvalidationAdvisors)
	{
		this.documentDescriptorFactory = documentDescriptorFactory;

		this.filterConverters = ImmutableList.copyOf(filterConverters);
		logger.info("Filter converters: {}", filterConverters);

		this.filterConvertorDecorators = makeDecoratorsMapAndHandleDuplicates(filterConverterDecorators);
		logger.info("Filter converter decorators: {}", filterConvertorDecorators);

		this.viewInvalidationAdvisorsByWindowId = makeViewInvalidationAdvisorsMap(viewInvalidationAdvisors);
		logger.info("view invalidation advisors: {}", this.viewInvalidationAdvisorsByWindowId);

		this.viewCustomizers = viewCustomizers;
	}

	private static ImmutableMap<WindowId, SqlDocumentFilterConverterDecorator> makeDecoratorsMapAndHandleDuplicates(
			@NonNull final Collection<SqlDocumentFilterConverterDecorator> providers)
	{
		try
		{
			return Maps.uniqueIndex(providers, SqlDocumentFilterConverterDecorator::getWindowId);
		}
		catch (final IllegalArgumentException e)
		{
			final String message = "The given collection of SqlDocumentFilterConverterDecoratorProvider implementors contains more than one element with the same window-id";
			throw new AdempiereException(message, e)
					.setParameter("sqlDocumentFilterConverterDecoratorProviders", providers)
					.appendParametersToMessage();
		}
	}

	private static ImmutableMap<WindowId, IViewInvalidationAdvisor> makeViewInvalidationAdvisorsMap(final List<IViewInvalidationAdvisor> viewInvalidationAdvisors)
	{
		try
		{
			return Maps.uniqueIndex(viewInvalidationAdvisors, advisor -> {
				final WindowId windowId = advisor.getWindowId();
				Check.assumeNotNull(windowId, "windowId shall not be null for {}", advisor);
				return windowId;
			});
		}
		catch (final IllegalArgumentException e)
		{
			final String message = "The given collection of " + IViewInvalidationAdvisor.class + " implementors contains more than one element with the same window-id";
			throw new AdempiereException(message, e)
					.setParameter("viewInvalidationAdvisors", viewInvalidationAdvisors)
					.appendParametersToMessage();
		}
	}

	SqlViewBinding getViewBinding(
			@NonNull final WindowId windowId,
			@Nullable final Characteristic requiredFieldCharacteristic,
			@Nullable final ViewProfileId profileId)
	{
		final SqlViewBindingKey key = new SqlViewBindingKey(windowId, requiredFieldCharacteristic, profileId);
		return cache.getOrLoad(key, this::createViewBinding);
	}

	private SqlViewBinding createViewBinding(@NonNull final SqlViewBindingKey key)
	{
		final WindowId windowId = key.getWindowId();
		final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(windowId);
		final Set<String> displayFieldNames = entityDescriptor.getFieldNamesWithCharacteristic(key.getRequiredFieldCharacteristic());
		final SqlDocumentEntityDataBindingDescriptor entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());
		final DocumentFilterDescriptorsProvider filterDescriptors = entityDescriptor.getFilterDescriptors();
		final ImmutableMap<DetailId, SqlDocumentEntityDataBindingDescriptor> includedEntitiesDescriptors = entityDescriptor.getIncludedEntities()
				.stream()
				.filter(includedEntityDescriptor -> SqlDocumentEntityDataBindingDescriptor.isAssignableFrom(includedEntityDescriptor.getDataBinding()))
				.collect(ImmutableMap.toImmutableMap(
						DocumentEntityDescriptor::getDetailId,
						includedEntityDescriptor -> SqlDocumentEntityDataBindingDescriptor.cast(includedEntityDescriptor.getDataBinding())
				));

		final SqlViewBinding.Builder builder = prepareSqlViewBinding(entityBinding)
				.includedEntitiesDescriptors(includedEntitiesDescriptors)
				.fields(createViewFieldBindings(entityBinding, displayFieldNames))
				.displayFieldNames(displayFieldNames)
				.filterDescriptors(filterDescriptors)
				.refreshViewOnChangeEvents(entityDescriptor.isRefreshViewOnChangeEvents())
				.viewInvalidationAdvisor(getViewInvalidationAdvisor(windowId))
				.queryIfNoFilters(entityDescriptor.isQueryIfNoFilters());

		builder.filterConverters(filterConverters);

		if (filterConvertorDecorators.containsKey(windowId))
		{
			builder.filterConverterDecorator(filterConvertorDecorators.get(windowId));
		}

		final SqlViewCustomizer sqlViewCustomizer = viewCustomizers.getOrNull(windowId, key.getProfileId());
		if (sqlViewCustomizer != null)
		{
			builder.rowCustomizer(sqlViewCustomizer);

			sqlViewCustomizer.customizeSqlViewBinding(builder);
		}

		return builder.build();
	}

	private static SqlViewBinding.Builder prepareSqlViewBinding(@NonNull final SqlDocumentEntityDataBindingDescriptor entityBinding)
	{
		return SqlViewBinding.builder()
				.tableName(entityBinding.getTableName())
				.tableAlias(entityBinding.getTableAlias())
				.sqlWhereClause(entityBinding.getSqlWhereClause())
				.defaultOrderBys(entityBinding.getDefaultOrderBys());
	}

	public static ImmutableList<SqlViewRowFieldBinding> createViewFieldBindings(
			@NonNull final SqlDocumentEntityDataBindingDescriptor entityBinding,
			@NonNull final Collection<String> availableDisplayColumnNames)
	{
		return entityBinding.getFields()
				.stream()
				.map(documentField -> createViewFieldBinding(documentField, availableDisplayColumnNames))
				.collect(ImmutableList.toImmutableList());
	}

	public static SqlViewRowFieldBinding createViewFieldBinding(
			@NonNull final SqlDocumentFieldDataBindingDescriptor documentField,
			@NonNull final Collection<String> availableDisplayColumnNames)
	{
		final String fieldName = documentField.getFieldName();
		final boolean isDisplayColumnAvailable = documentField.getSqlSelectDisplayValue() != null && availableDisplayColumnNames.contains(fieldName);

		return SqlViewRowFieldBinding.builder()
				.fieldName(fieldName)
				.columnName(documentField.getColumnName())
				.keyColumn(documentField.isKeyColumn())
				.widgetType(documentField.getWidgetType())
				.virtualColumn(documentField.isVirtualColumn())
				.mandatory(documentField.isMandatory())
				//
				.sqlValueClass(documentField.getSqlValueClass())
				.sqlSelectValue(documentField.getSqlSelectValue())
				.sqlSelectDisplayValue(isDisplayColumnAvailable ? documentField.getSqlSelectDisplayValue() : null)
				//
				.sqlOrderBy(documentField.getSqlOrderBy())
				//
				.fieldLoader(createSqlViewRowFieldLoader(documentField, isDisplayColumnAvailable))
				//
				.build();
	}

	private static SqlViewRowFieldLoader createSqlViewRowFieldLoader(
			@NonNull final SqlDocumentFieldDataBindingDescriptor documentField,
			final boolean isDisplayColumnAvailable)
	{
		return new DocumentFieldValueLoaderAsSqlViewRowFieldLoader(
				documentField.getDocumentFieldValueLoader(),
				documentField.getLookupDescriptor(),
				isDisplayColumnAvailable);
	}

	private IViewInvalidationAdvisor getViewInvalidationAdvisor(final WindowId windowId)
	{
		return viewInvalidationAdvisorsByWindowId.getOrDefault(windowId, DefaultViewInvalidationAdvisor.instance);
	}

	@Value
	private static class DocumentFieldValueLoaderAsSqlViewRowFieldLoader implements SqlViewRowFieldLoader
	{
		@NonNull DocumentFieldValueLoader fieldValueLoader;
		@Nullable LookupDescriptor lookupDescriptor;
		boolean isDisplayColumnAvailable;

		@Override
		@Nullable
		public Object retrieveValue(@NonNull final ResultSet rs, final String adLanguage) throws SQLException
		{
			return fieldValueLoader.retrieveFieldValue(
					rs,
					isDisplayColumnAvailable,
					adLanguage,
					lookupDescriptor);
		}
	}

	@Value
	private static class SqlViewBindingKey
	{
		@NonNull WindowId windowId;
		@Nullable Characteristic requiredFieldCharacteristic;
		@Nullable ViewProfileId profileId;
	}

}
