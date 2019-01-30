package de.metas.ui.web.dataentry.window.descriptor.factory;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.OrderedDocumentsList;

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

public class DataEntryGroupBindingDescriptorBuilder implements DocumentEntityDataBindingDescriptorBuilder
{
	public static final transient DataEntryGroupBindingDescriptorBuilder instance = new DataEntryGroupBindingDescriptorBuilder();

	private static final DocumentEntityDataBindingDescriptor dataBinding = new DocumentEntityDataBindingDescriptor()
	{
		@Override
		public DocumentsRepository getDocumentsRepository()
		{
			return new DocumentsRepository()
			{
				@Override
				public OrderedDocumentsList retrieveDocuments(DocumentQuery query, IDocumentChangesCollector changesCollector)
				{
					// actually this repo should not be invoked to start with, because it's for DateEntryGroup which does not have any fields/records of its own
					return OrderedDocumentsList.of(ImmutableList.of(), ImmutableList.of());
				}

				@Override
				public Document retrieveDocument(DocumentQuery query, IDocumentChangesCollector changesCollector)
				{
					throw new UnsupportedOperationException();
				}

				@Override
				public DocumentId retrieveParentDocumentId(DocumentEntityDescriptor parentEntityDescriptor, DocumentQuery childDocumentQuery)
				{
					throw new UnsupportedOperationException();
				}

				@Override
				public Document createNewDocument(DocumentEntityDescriptor entityDescriptor, Document parentDocument, IDocumentChangesCollector changesCollector)
				{
					throw new UnsupportedOperationException();
				}

				@Override
				public void refresh(Document document)
				{
					throw new UnsupportedOperationException();
				}

				@Override
				public SaveResult save(Document document)
				{
					throw new UnsupportedOperationException();
				}

				@Override
				public void delete(Document document)
				{
					throw new UnsupportedOperationException();
				}

				@Override
				public String retrieveVersion(DocumentEntityDescriptor entityDescriptor, int documentIdAsInt)
				{
					throw new UnsupportedOperationException();
				}

				@Override
				public int retrieveLastLineNo(DocumentQuery query)
				{
					throw new UnsupportedOperationException();
				}
			};
		}

	};

	@Override
	public DocumentEntityDataBindingDescriptor getOrBuild()
	{
		return dataBinding;
	}

}
