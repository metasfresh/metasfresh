package de.metas.ui.web.view;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.ui.web.view.descriptor.DocumentViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptor;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.ui.web.window.model.DocumentReferencesService;
import de.metas.ui.web.window.model.filters.DocumentFilter;

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
public class SqlDocumentViewSelectionFactory implements IDocumentViewSelectionFactory
{
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;
	@Autowired
	private DocumentReferencesService documentReferencesService;

	@Override
	public DocumentViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
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
	public Collection<DocumentFilterDescriptor> getViewFilters(final WindowId windowId)
	{
		final DocumentDescriptor descriptor = documentDescriptorFactory.getDocumentDescriptor(windowId);
		final DocumentEntityDescriptor entityDescriptor = descriptor.getEntityDescriptor();
		final Collection<DocumentFilterDescriptor> filters = entityDescriptor.getFiltersProvider().getAll();
		return filters;
	}

	@Override
	public IDocumentViewSelection createView(final DocumentViewCreateRequest request)
	{
		if (!request.getFilterOnlyIds().isEmpty())
		{
			throw new IllegalArgumentException("Filtering by Ids are not supported: " + request);
		}

		final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(request.getWindowId());
		return SqlDocumentViewSelection.builder(entityDescriptor)
				//
				.setParentViewId(request.getParentViewId())
				//
				.setViewFieldsByCharacteristic(request.getViewTypeRequiredFieldCharacteristic())
				//
				.setStickyFilter(extractReferencedDocumentFilter(entityDescriptor.getWindowId(), request.getSingleReferencingDocumentPathOrNull()))
				.setFiltersFromJSON(request.getFilters())
				//
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

}
