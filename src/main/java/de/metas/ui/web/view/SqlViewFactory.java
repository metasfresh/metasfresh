package de.metas.ui.web.view;

import java.util.Collection;
import java.util.Set;

import org.compiere.util.CCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.ui.web.window.model.DocumentReferencesService;
import de.metas.ui.web.window.model.filters.DocumentFilter;
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

@Service
public class SqlViewFactory implements IViewFactory
{
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;
	@Autowired
	private DocumentReferencesService documentReferencesService;

	private final transient CCache<SqlViewBindingKey, SqlViewBinding> viewBindings = CCache.newCache("SqlViewBindings", 20, 0);

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		final DocumentLayoutDescriptor layout = documentDescriptorFactory.getDocumentDescriptor(windowId).getLayout();
		switch (viewDataType)
		{
			case grid:
			{
				return layout.getGridViewLayout();
			}
			case list:
			{
				return layout.getSideListViewLayout();
			}
			default:
			{
				throw new IllegalArgumentException("Invalid viewDataType: " + viewDataType);
			}
		}
	}

	@Override
	public Collection<DocumentFilterDescriptor> getViewFilters(final WindowId windowId, final JSONViewDataType viewType)
	{
		final SqlViewBindingKey sqlViewBindingKey = new SqlViewBindingKey(windowId, viewType.getRequiredFieldCharacteristic());
		return getViewBinding(sqlViewBindingKey).getFilterDescriptors().getAll();
	}

	@Override
	public IView createView(final ViewCreateRequest request)
	{
		if (!request.getFilterOnlyIds().isEmpty())
		{
			throw new IllegalArgumentException("Filtering by Ids are not supported: " + request);
		}

		final SqlViewBindingKey sqlViewBindingKey = new SqlViewBindingKey(request.getWindowId(), request.getViewTypeRequiredFieldCharacteristic());
		return SqlView.builder(getViewBinding(sqlViewBindingKey))
				.setWindowId(request.getWindowId())
				.setParentViewId(request.getParentViewId())
				.setStickyFilter(extractReferencedDocumentFilter(request.getWindowId(), request.getSingleReferencingDocumentPathOrNull()))
				.setFiltersFromJSON(request.getFilters())
				.build();
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

		return SqlViewBinding.builder()
				.setTableName(entityBinding.getTableName())
				.setTableAlias(entityBinding.getTableAlias())
				.setDisplayFieldNames(displayFieldNames)
				.addFields(entityBinding.getFields())
				.setFilterDescriptors(entityDescriptor.getFiltersProvider())
				.setOrderBys(entityBinding.getOrderBys())
				.build();
	}

	@Value
	private static final class SqlViewBindingKey
	{
		private final WindowId windowId;
		private final Characteristic requiredFieldCharacteristic;
	}
}
