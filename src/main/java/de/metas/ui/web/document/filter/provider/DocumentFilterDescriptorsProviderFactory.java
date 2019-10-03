package de.metas.ui.web.document.filter.provider;

import java.util.Collection;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdTabId;

import de.metas.ui.web.document.filter.provider.fullTextSearch.FullTextSearchDocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.standard.StandardDocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.userQuery.UserQueryDocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.geo_location.LocationAreaSearchDocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;

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

/**
 * {@link DocumentFilterDescriptorsProvider}s factory.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class DocumentFilterDescriptorsProviderFactory
{
	public static final transient DocumentFilterDescriptorsProviderFactory instance = new DocumentFilterDescriptorsProviderFactory();

	private DocumentFilterDescriptorsProviderFactory()
	{
	}

	public DocumentFilterDescriptorsProvider createFiltersProvider(
			@Nullable final AdTabId adTabId,
			@Nullable final String tableName,
			final Collection<DocumentFieldDescriptor> fields)
	{
		return CompositeDocumentFilterDescriptorsProvider.compose(
				UserQueryDocumentFilterDescriptorsProviderFactory.instance.createFiltersProvider(adTabId, tableName, fields),
				StandardDocumentFilterDescriptorsProviderFactory.instance.createFiltersProvider(adTabId, tableName, fields),
				FullTextSearchDocumentFilterDescriptorsProviderFactory.instance.createFiltersProvider(adTabId, tableName, fields),
				LocationAreaSearchDocumentFilterDescriptorsProviderFactory.instance.createFiltersProvider(adTabId, tableName, fields));
	}
}
