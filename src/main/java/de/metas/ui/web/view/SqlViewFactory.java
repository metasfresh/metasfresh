package de.metas.ui.web.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.NullStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.CCache;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilter.Builder;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.DocumentFiltersList;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecorator;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.sql.DocumentFieldValueLoader;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.ui.web.window.model.DocumentReferencesService;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * View factory which is based on {@link DocumentEntityDescriptor} having SQL repository.<br>
 * Creates {@link DefaultView}s with are backed by a {@link SqlViewBinding}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class SqlViewFactory implements IViewFactory
{
	private final DocumentDescriptorFactory documentDescriptorFactory;
	private final DocumentReferencesService documentReferencesService;

	private final ImmutableMap<WindowId, SqlDocumentFilterConverterDecorator> windowId2SqlDocumentFilterConverterDecorator;

	@Value
	private static final class SqlViewBindingKey
	{
		@NonNull
		private final WindowId windowId;
		@Nullable
		private final Characteristic requiredFieldCharacteristic;
		@Nullable
		private final ViewProfileId profileId;
	}

	private final transient CCache<SqlViewBindingKey, ViewLayout> viewLayouts = CCache.newCache("SqlViewLayouts", 20, 0);
	private final transient CCache<SqlViewBindingKey, SqlViewBinding> viewBindings = CCache.newCache("SqlViewBindings", 20, 0);

	private final ImmutableListMultimap<WindowId, ViewProfile> viewProfiles;
	private final ImmutableMap<WindowId, ImmutableMap<ViewProfileId, SqlViewCustomizer>> viewCustomizers;
	private final CompositeDefaultViewProfileIdProvider defaultProfileIdProvider;

	public SqlViewFactory(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final DocumentReferencesService documentReferencesService,
			@NonNull final List<SqlViewCustomizer> viewCustomizers,
			@NonNull final List<DefaultViewProfileIdProvider> defaultViewProfileIdProviders,
			@NonNull final List<SqlDocumentFilterConverterDecorator> converterDecorators)
	{
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.documentReferencesService = documentReferencesService;

		this.windowId2SqlDocumentFilterConverterDecorator = makeDecoratorsMapAndHandleDuplicates(converterDecorators);

		this.viewProfiles = makeViewProfilesMap(viewCustomizers);
		this.viewCustomizers = makeViewCustomizersMap(viewCustomizers);
		this.defaultProfileIdProvider = makeDefaultProfileIdProvider(defaultViewProfileIdProviders, viewCustomizers);
	}

	private static ImmutableListMultimap<WindowId, ViewProfile> makeViewProfilesMap(Collection<SqlViewCustomizer> viewCustomizers)
	{
		return viewCustomizers
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(viewCustomizer -> viewCustomizer.getWindowId(), viewCustomizer -> viewCustomizer.getProfile()));
	}

	private static ImmutableMap<WindowId, ImmutableMap<ViewProfileId, SqlViewCustomizer>> makeViewCustomizersMap(Collection<SqlViewCustomizer> viewCustomizers)
	{
		final Map<WindowId, ImmutableMap<ViewProfileId, SqlViewCustomizer>> map = viewCustomizers
				.stream()
				.sorted(SqlViewCustomizerUtils.ORDERED_COMPARATOR)
				.collect(Collectors.groupingBy(
						SqlViewCustomizer::getWindowId,
						ImmutableMap.toImmutableMap(viewCustomizer -> viewCustomizer.getProfile().getProfileId(), viewCustomizer -> viewCustomizer)));
		return ImmutableMap.copyOf(map);
	}

	private CompositeDefaultViewProfileIdProvider makeDefaultProfileIdProvider(
			final List<DefaultViewProfileIdProvider> providers,
			final List<SqlViewCustomizer> viewCustomizersToExtractFallbacks)
	{
		final CompositeDefaultViewProfileIdProvider result = CompositeDefaultViewProfileIdProvider.of(providers);

		viewCustomizersToExtractFallbacks.stream()
				.sorted(SqlViewCustomizerUtils.ORDERED_COMPARATOR)
				.forEach(viewCustomizer -> result.setDefaultProfileIdFallbackIfAbsent(viewCustomizer.getWindowId(), viewCustomizer.getProfile().getProfileId()));

		return result;
	}

	private static ImmutableMap<WindowId, SqlDocumentFilterConverterDecorator> makeDecoratorsMapAndHandleDuplicates(
			@NonNull final Collection<SqlDocumentFilterConverterDecorator> providers)
	{
		try
		{
			return Maps.uniqueIndex(providers, SqlDocumentFilterConverterDecorator::getWindowId);
		}
		catch (IllegalArgumentException e)
		{
			final String message = "The given collection of SqlDocumentFilterConverterDecoratorProvider implementors contains more than one element with the same window-id";
			throw new AdempiereException(message, e)
					.setParameter("sqlDocumentFilterConverterDecoratorProviders", providers)
					.appendParametersToMessage();
		}
	}

	@Override
	public List<ViewProfile> getAvailableProfiles(final WindowId windowId)
	{
		return viewProfiles.get(windowId);
	}

	private ViewProfileId getDefaultProfileIdByWindowId(final WindowId windowId)
	{
		return defaultProfileIdProvider.getDefaultProfileIdByWindowId(windowId);
	}

	public void setDefaultProfileId(@NonNull final WindowId windowId, final ViewProfileId profileId)
	{
		defaultProfileIdProvider.setDefaultProfileIdOverride(windowId, profileId);
	}

	private SqlViewCustomizer getSqlViewCustomizer(@NonNull final WindowId windowId, final ViewProfileId profileId)
	{
		if (ViewProfileId.isNull(profileId))
		{
			return null;
		}

		final ImmutableMap<ViewProfileId, SqlViewCustomizer> viewCustomizersByProfileId = viewCustomizers.get(windowId);
		if (viewCustomizersByProfileId == null)
		{
			return null;
		}

		return viewCustomizersByProfileId.get(profileId);
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
	{
		final ViewProfileId profileIdEffective = !ViewProfileId.isNull(profileId) ? profileId : getDefaultProfileIdByWindowId(windowId);
		final SqlViewBindingKey sqlViewBindingKey = new SqlViewBindingKey(windowId, viewDataType.getRequiredFieldCharacteristic(), profileIdEffective);
		return viewLayouts.getOrLoad(sqlViewBindingKey, () -> createViewLayout(sqlViewBindingKey, viewDataType));
	}

	private ViewLayout createViewLayout(final SqlViewBindingKey sqlViewBindingKey, final JSONViewDataType viewDataType)
	{
		final ViewLayout viewLayoutOrig = documentDescriptorFactory.getDocumentDescriptor(sqlViewBindingKey.getWindowId())
				.getViewLayout(viewDataType);

		final SqlViewBinding sqlViewBinding = getViewBinding(sqlViewBindingKey);
		final Collection<DocumentFilterDescriptor> filters = sqlViewBinding.getViewFilterDescriptors().getAll();
		final boolean hasTreeSupport = sqlViewBinding.hasGroupingFields();

		final ViewLayout.ChangeBuilder viewLayoutBuilder = viewLayoutOrig.toBuilder()
				.profileId(sqlViewBindingKey.getProfileId())
				.filters(filters)
				.treeSupport(hasTreeSupport, true/* treeCollapsible */, ViewLayout.TreeExpandedDepth_AllCollapsed);

		final SqlViewCustomizer sqlViewCustomizer = getSqlViewCustomizer(sqlViewBindingKey.getWindowId(), sqlViewBindingKey.getProfileId());
		if (sqlViewCustomizer != null)
		{
			sqlViewCustomizer.customizeViewLayout(viewLayoutBuilder);
		}

		return viewLayoutBuilder.build();
	}

	@Override
	public IView createView(final CreateViewRequest request)
	{
		final WindowId windowId = request.getViewId().getWindowId();

		final JSONViewDataType viewType = request.getViewType();
		final ViewProfileId profileId = !ViewProfileId.isNull(request.getProfileId()) ? request.getProfileId() : getDefaultProfileIdByWindowId(windowId);
		final SqlViewBindingKey sqlViewBindingKey = new SqlViewBindingKey(windowId, viewType.getRequiredFieldCharacteristic(), profileId);

		final SqlViewBinding sqlViewBinding = getViewBinding(sqlViewBindingKey);
		final IViewDataRepository viewDataRepository = new SqlViewDataRepository(sqlViewBinding);

		final DefaultView.Builder viewBuilder = DefaultView.builder(viewDataRepository)
				.setViewId(request.getViewId())
				.setViewType(viewType)
				.setProfileId(profileId)
				.setReferencingDocumentPaths(request.getReferencingDocumentPaths())
				.setParentViewId(request.getParentViewId())
				.setParentRowId(request.getParentRowId())
				.addStickyFilters(request.getStickyFilters())
				.addStickyFilter(extractReferencedDocumentFilter(windowId, request.getSingleReferencingDocumentPathOrNull()));

		final DocumentFiltersList filters = request.getFilters();
		if (filters.isJson())
		{
			viewBuilder.setFiltersFromJSON(filters.getJsonFilters());
		}
		else
		{
			viewBuilder.setFilters(filters.getFilters());
		}

		if (request.isUseAutoFilters())
		{
			final List<DocumentFilter> autoFilters = createAutoFilters(sqlViewBindingKey);
			viewBuilder.addFiltersIfAbsent(autoFilters);
		}

		if (!request.getFilterOnlyIds().isEmpty())
		{
			viewBuilder.addStickyFilter(DocumentFilter.inArrayFilter(sqlViewBinding.getKeyColumnName(), sqlViewBinding.getKeyColumnName(), request.getFilterOnlyIds()));
		}

		return viewBuilder.build();
	}

	private final DocumentFilter extractReferencedDocumentFilter(final WindowId targetWindowId, final DocumentPath referencedDocumentPath)
	{
		if (referencedDocumentPath == null)
		{
			return null;
		}
		else
		{
			final DocumentReference reference = documentReferencesService.getDocumentReference(referencedDocumentPath, targetWindowId);
			return reference.getFilter();
		}
	}

	private List<DocumentFilter> createAutoFilters(final SqlViewBindingKey sqlViewBindingKey)
	{
		final SqlViewBinding sqlViewBinding = getViewBinding(sqlViewBindingKey);
		final Collection<DocumentFilterDescriptor> filters = sqlViewBinding.getViewFilterDescriptors().getAll();

		return filters
				.stream()
				.filter(DocumentFilterDescriptor::isAutoFilter)
				.map(SqlViewFactory::createAutoFilter)
				.collect(ImmutableList.toImmutableList());
	}

	private static DocumentFilter createAutoFilter(final DocumentFilterDescriptor filterDescriptor)
	{
		if (!filterDescriptor.isAutoFilter())
		{
			throw new AdempiereException("Not an auto filter: " + filterDescriptor);
		}

		final Builder filterBuilder = DocumentFilter.builder()
				.setFilterId(filterDescriptor.getFilterId());

		filterDescriptor.getParameters()
				.stream()
				.filter(DocumentFilterParamDescriptor::isAutoFilter)
				.map(SqlViewFactory::createAutoFilterParam)
				.forEach(filterBuilder::addParameter);

		return filterBuilder.build();
	}

	private static final DocumentFilterParam createAutoFilterParam(final DocumentFilterParamDescriptor filterParamDescriptor)
	{
		final Object value;
		if (filterParamDescriptor.isAutoFilterInitialValueIsDateNow())
		{
			final DocumentFieldWidgetType widgetType = filterParamDescriptor.getWidgetType();
			if (widgetType == DocumentFieldWidgetType.Date)
			{
				value = SystemTime.asDayTimestamp();
			}
			else
			{
				value = SystemTime.asTimestamp();
			}
		}
		else
		{
			value = filterParamDescriptor.getAutoFilterInitialValue();
		}

		return DocumentFilterParam.builder()
				.setFieldName(filterParamDescriptor.getFieldName())
				.setOperator(Operator.EQUAL)
				.setValue(value)
				.build();
	}

	private SqlViewBinding getViewBinding(@NonNull final SqlViewBindingKey key)
	{
		return viewBindings.getOrLoad(key, () -> createViewBinding(key));
	}

	private SqlViewBinding createViewBinding(@NonNull final SqlViewBindingKey key)
	{
		final WindowId windowId = key.getWindowId();
		final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(windowId);
		final Set<String> displayFieldNames = entityDescriptor.getFieldNamesWithCharacteristic(key.getRequiredFieldCharacteristic());
		final SqlDocumentEntityDataBindingDescriptor entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());
		final DocumentFilterDescriptorsProvider filterDescriptors = entityDescriptor.getFilterDescriptors();

		final SqlViewBinding.Builder builder = createBuilderForEntityBindingAndFieldNames(entityBinding, displayFieldNames)
				.filterDescriptors(filterDescriptors);

		if (windowId2SqlDocumentFilterConverterDecorator.containsKey(windowId))
		{
			builder.filterConverterDecorator(windowId2SqlDocumentFilterConverterDecorator.get(windowId));
		}

		final SqlViewCustomizer sqlViewCustomizer = getSqlViewCustomizer(windowId, key.getProfileId());
		if (sqlViewCustomizer != null)
		{
			final ViewRowCustomizer rowCustomizer = sqlViewCustomizer;
			builder.rowCustomizer(rowCustomizer);

			sqlViewCustomizer.customizeSqlViewBinding(builder);
		}

		return builder.build();
	}

	private SqlViewBinding.Builder createBuilderForEntityBindingAndFieldNames(
			@NonNull final SqlDocumentEntityDataBindingDescriptor entityBinding,
			@NonNull final Set<String> displayFieldNames)
	{
		final SqlViewBinding.Builder builder = createBuilderForEntityBinding(entityBinding);

		entityBinding.getFields()
				.stream()
				.map(documentField -> createViewFieldBinding(documentField, displayFieldNames))
				.forEach(builder::field);
		builder.displayFieldNames(displayFieldNames);
		return builder;
	}

	private SqlViewBinding.Builder createBuilderForEntityBinding(@NonNull final SqlDocumentEntityDataBindingDescriptor entityBinding)
	{
		final SqlViewBinding.Builder builder = SqlViewBinding.builder()
				.tableName(entityBinding.getTableName())
				.tableAlias(entityBinding.getTableAlias())
				.sqlWhereClause(entityBinding.getSqlWhereClause())
				.defaultOrderBys(entityBinding.getDefaultOrderBys());
		return builder;
	}

	private static final SqlViewRowFieldBinding createViewFieldBinding(final SqlDocumentFieldDataBindingDescriptor documentField, final Collection<String> availableDisplayColumnNames)
	{
		return createViewFieldBindingBuilder(documentField, availableDisplayColumnNames).build();
	}

	public static final SqlViewRowFieldBinding.SqlViewRowFieldBindingBuilder createViewFieldBindingBuilder(final SqlDocumentFieldDataBindingDescriptor documentField, final Collection<String> availableDisplayColumnNames)
	{
		final String fieldName = documentField.getFieldName();
		final boolean isDisplayColumnAvailable = documentField.isUsingDisplayColumn() && availableDisplayColumnNames.contains(fieldName);

		return SqlViewRowFieldBinding.builder()
				.fieldName(fieldName)
				.columnName(documentField.getColumnName())
				.columnSql(documentField.getColumnSql())
				.keyColumn(documentField.isKeyColumn())
				.widgetType(documentField.getWidgetType())
				//
				.sqlValueClass(documentField.getSqlValueClass())
				.sqlSelectValue(documentField.getSqlSelectValue())
				.usingDisplayColumn(isDisplayColumnAvailable)
				.sqlSelectDisplayValue(isDisplayColumnAvailable ? documentField.getSqlSelectDisplayValue() : NullStringExpression.instance)
				//
				.sqlOrderBy(documentField.getSqlOrderBy())
				//
				.fieldLoader(new DocumentFieldValueLoaderAsSqlViewRowFieldLoader(documentField.getDocumentFieldValueLoader(), isDisplayColumnAvailable))
		//
		;

	}

	@Value
	private static final class DocumentFieldValueLoaderAsSqlViewRowFieldLoader implements SqlViewRowFieldLoader
	{
		private final @NonNull DocumentFieldValueLoader fieldValueLoader;
		private final boolean isDisplayColumnAvailable;

		@Override
		public Object retrieveValueAsJson(final ResultSet rs, final String adLanguage) throws SQLException
		{
			final Object fieldValue = fieldValueLoader.retrieveFieldValue(rs, isDisplayColumnAvailable, adLanguage, (LookupDescriptor)null);
			return Values.valueToJsonObject(fieldValue);
		}

	}
}
