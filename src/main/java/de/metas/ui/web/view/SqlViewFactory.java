package de.metas.ui.web.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

import org.adempiere.ad.expression.api.NullStringExpression;
import org.compiere.util.CCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.picking.PickingConstants;
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
 * View factory which is based on {@link DocumentEntityDescriptor} having SQL repository.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class SqlViewFactory implements IViewFactory
{
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;
	@Autowired
	private DocumentReferencesService documentReferencesService;

	@Value
	private static final class SqlViewBindingKey
	{
		private final WindowId windowId;
		private final Characteristic requiredFieldCharacteristic;
	}

	//
	private final transient CCache<SqlViewBindingKey, SqlViewBinding> viewBindings = CCache.newCache("SqlViewBindings", 20, 0);

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
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
		final SqlViewBindingKey sqlViewBindingKey = new SqlViewBindingKey(request.getWindowId(), request.getViewTypeRequiredFieldCharacteristic());
		final SqlViewBinding sqlViewBinding = getViewBinding(sqlViewBindingKey);
		final SqlViewDataRepository sqlViewDataRepository = new SqlViewDataRepository(sqlViewBinding);

		final DefaultView.Builder viewBuilder = DefaultView.builder(sqlViewDataRepository)
				.setWindowId(request.getWindowId())
				.setViewType(request.getViewType())
				.setReferencingDocumentPaths(request.getReferencingDocumentPaths())
				.setParentViewId(request.getParentViewId())
				.addStickyFilters(request.getStickyFilters())
				.addStickyFilter(extractReferencedDocumentFilter(request.getWindowId(), request.getSingleReferencingDocumentPathOrNull()));
		
		final DocumentFiltersList filters = request.getFilters();
		if(filters.isJson())
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

	private SqlViewBinding getViewBinding(final SqlViewBindingKey key)
	{
		return viewBindings.getOrLoad(key, () -> createViewBinding(key));
	}

	private SqlViewBinding createViewBinding(final SqlViewBindingKey key)
	{
		final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(key.getWindowId());
		final Set<String> displayFieldNames = entityDescriptor.getFieldNamesWithCharacteristic(key.getRequiredFieldCharacteristic());
		final SqlDocumentEntityDataBindingDescriptor entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());
		final DocumentFilterDescriptorsProvider filterDescriptors = entityDescriptor.getFilterDescriptors();

		final SqlViewGroupingBinding groupingBinding;
		if (PickingConstants.WINDOWID_PackageableView.equals(entityDescriptor.getWindowId())) // FIXME: HARDCODED
		{
			groupingBinding = SqlViewGroupingBinding.builder()
					.groupBy(I_M_Packageable_V.COLUMNNAME_M_Warehouse_ID)
					.groupBy(I_M_Packageable_V.COLUMNNAME_M_Product_ID)
					.columnSql(I_M_Packageable_V.COLUMNNAME_QtyToDeliver, "SUM(QtyToDeliver)")
					.columnSql(I_M_Packageable_V.COLUMNNAME_DeliveryDate, "MIN(DeliveryDate)")
					.columnSql(I_M_Packageable_V.COLUMNNAME_PreparationDate, "IF_MIN(DeliveryDate, PreparationDate)")
					.build();
		}
		else
		{
			groupingBinding = null;
		}

		final SqlViewBinding.Builder builder = SqlViewBinding.builder()
				.setTableName(entityBinding.getTableName())
				.setTableAlias(entityBinding.getTableAlias())
				.setDisplayFieldNames(displayFieldNames)
				.setViewFilterDescriptors(filterDescriptors)
				.setSqlWhereClause(entityBinding.getSqlWhereClause())
				.setOrderBys(entityBinding.getDefaultOrderBys())
				.setGroupingBinding(groupingBinding);

		entityBinding.getFields()
				.stream()
				.map(documentField -> createViewFieldBinding(documentField, displayFieldNames))
				.forEach(fieldBinding -> builder.addField(fieldBinding));

		return builder.build();
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
		public Object retrieveValueAsJson(ResultSet rs, String adLanguage) throws SQLException
		{
			final Object fieldValue = fieldValueLoader.retrieveFieldValue(rs, isDisplayColumnAvailable, adLanguage);
			return Values.valueToJsonObject(fieldValue);
		}

	}
}
