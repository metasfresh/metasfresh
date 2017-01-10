package de.metas.ui.web.view;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.view.descriptor.DocumentViewLayout;
import de.metas.ui.web.view.json.JSONCreateDocumentViewRequest;
import de.metas.ui.web.view.json.JSONDocumentViewLayout;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptor;
import de.metas.ui.web.window.model.DocumentReferencesService;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
	@Autowired
	private ProcessDescriptorsFactory processDescriptorsFactory;

	@Override
	public JSONDocumentViewLayout getViewLayout(final int adWindowId, final JSONViewDataType viewDataType, final JSONOptions jsonOpts)
	{
		final DocumentDescriptor descriptor = documentDescriptorFactory.getDocumentDescriptor(adWindowId);

		final DocumentLayoutDescriptor layout = descriptor.getLayout();
		final DocumentEntityDescriptor entityDescriptor = descriptor.getEntityDescriptor();
		final Collection<DocumentFilterDescriptor> filters = entityDescriptor.getFiltersProvider().getAll();

		switch (viewDataType)
		{
			case grid:
			{
				final DocumentViewLayout viewLayout = layout.getGridViewLayout();
				return JSONDocumentViewLayout.of(viewLayout, filters, jsonOpts);
			}
			case list:
			{
				final DocumentViewLayout viewLayout = layout.getSideListViewLayout();
				return JSONDocumentViewLayout.of(viewLayout, filters, jsonOpts);
			}
			default:
			{
				throw new IllegalArgumentException("Invalid viewDataType: " + viewDataType);
			}
		}
	}


	@Override
	public IDocumentViewSelection createView(final JSONCreateDocumentViewRequest jsonRequest)
	{
		final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(jsonRequest.getAD_Window_ID());
		return SqlDocumentViewSelection.builder(entityDescriptor)
				.setServices(processDescriptorsFactory, documentReferencesService)
				//
				.setViewFieldsByCharacteristic(jsonRequest.getViewTypeRequiredFieldCharacteristic())
				//
				.setStickyFilterByReferencedDocument(jsonRequest.getReferencingDocumentPathOrNull())
				.setFiltersFromJSON(jsonRequest.getFilters())
				//
				.build();
	}

}
