package de.metas.ui.web.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.adempiere.ad.expression.api.NullStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CCache;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecorator;
import de.metas.ui.web.view.CreateViewRequest.DocumentFiltersList;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewGroupingBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
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

	public SqlViewFactory(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final DocumentReferencesService documentReferencesService,
			@NonNull final Collection<SqlDocumentFilterConverterDecorator> converterDecorators)
	{
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.documentReferencesService = documentReferencesService;

		this.windowId2SqlDocumentFilterConverterDecorator = makeDecoratorsMapAndHandleDuplicates(converterDecorators);
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

	@Value
	private static final class SqlViewBindingKey
	{
		private final WindowId windowId;
		private final Characteristic requiredFieldCharacteristic;
	}

	private final transient CCache<SqlViewBindingKey, SqlViewBinding> viewBindings = CCache.newCache("SqlViewBindings", 20, 0);

	private final Map<WindowId, Supplier<SqlViewGroupingBinding>> windowId2GroupingSupplier = new HashMap<>();

	/**
	 * Registers a supplier to be used in {@link #createView(CreateViewRequest)}.
	 * 
	 * @param windoId
	 * @param supplier
	 */
	public void registerGroupingSupplier(
			@NonNull final WindowId windoId,
			@NonNull final Supplier<SqlViewGroupingBinding> supplier)
	{
		windowId2GroupingSupplier.put(windoId, supplier);
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType)
	{
		final Collection<DocumentFilterDescriptor> filters = getViewFilterDescriptors(windowId, viewDataType);

		final SqlViewBindingKey sqlViewBindingKey = new SqlViewBindingKey(windowId, viewDataType.getRequiredFieldCharacteristic());
		final SqlViewBinding sqlViewBinding = getViewBinding(sqlViewBindingKey);
		final boolean hasTreeSupport = sqlViewBinding.hasGroupingFields();

		return documentDescriptorFactory.getDocumentDescriptor(windowId)
				.getViewLayout(viewDataType)
				.withFiltersAndTreeSupport(filters, hasTreeSupport, true/* treeCollapsible */, ViewLayout.TreeExpandedDepth_AllCollapsed);
	}

	@Override
	public Collection<DocumentFilterDescriptor> getViewFilterDescriptors(final WindowId windowId, final JSONViewDataType viewType)
	{
		final SqlViewBindingKey sqlViewBindingKey = new SqlViewBindingKey(windowId, viewType.getRequiredFieldCharacteristic());
		return getViewBinding(sqlViewBindingKey).getViewFilterDescriptors().getAll();
	}

	@Override
	public IView createView(final CreateViewRequest request)
	{
		final WindowId windowId = request.getViewId().getWindowId();

		final SqlViewBindingKey sqlViewBindingKey = new SqlViewBindingKey(
				windowId,
				request.getViewTypeRequiredFieldCharacteristic());

		final SqlViewBinding sqlViewBinding = getViewBinding(sqlViewBindingKey);
		final SqlViewDataRepository sqlViewDataRepository = new SqlViewDataRepository(sqlViewBinding);

		final DefaultView.Builder viewBuilder = DefaultView.builder(sqlViewDataRepository)
				.setViewId(request.getViewId())
				.setViewType(request.getViewType())
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

	private SqlViewBinding getViewBinding(@NonNull final SqlViewBindingKey key)
	{
		return viewBindings.getOrLoad(key, () -> createViewBinding(key));
	}

	private SqlViewBinding createViewBinding(@NonNull final SqlViewBindingKey key)
	{
		final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(key.getWindowId());
		final Set<String> displayFieldNames = entityDescriptor.getFieldNamesWithCharacteristic(key.getRequiredFieldCharacteristic());
		final SqlDocumentEntityDataBindingDescriptor entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());
		final DocumentFilterDescriptorsProvider filterDescriptors = entityDescriptor.getFilterDescriptors();

		final SqlViewGroupingBinding groupingBinding;
		if (windowId2GroupingSupplier.containsKey(entityDescriptor.getWindowId()))
		{
			groupingBinding = windowId2GroupingSupplier.get(entityDescriptor.getWindowId()).get();
		}
		else
		{
			groupingBinding = null;
		}

		final SqlViewBinding.Builder builder = createBuilderForEntityBindingAndFieldNames(entityBinding, displayFieldNames);

		builder.setFilterDescriptors(filterDescriptors)
				.setGroupingBinding(groupingBinding);;

		if (windowId2SqlDocumentFilterConverterDecorator.containsKey(key.getWindowId()))
		{
			builder.setFilterConverterDecorator(windowId2SqlDocumentFilterConverterDecorator.get(key.getWindowId()));
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
				.forEach(fieldBinding -> builder.addField(fieldBinding));
		builder.setDisplayFieldNames(displayFieldNames);
		return builder;
	}

	private SqlViewBinding.Builder createBuilderForEntityBinding(@NonNull final SqlDocumentEntityDataBindingDescriptor entityBinding)
	{
		final SqlViewBinding.Builder builder = SqlViewBinding.builder()
				.setTableName(entityBinding.getTableName())
				.setTableAlias(entityBinding.getTableAlias())
				.setSqlWhereClause(entityBinding.getSqlWhereClause())
				.setOrderBys(entityBinding.getDefaultOrderBys());
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
