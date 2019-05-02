package de.metas.ui.web.document.filter.provider.fullTextSearch;

import java.util.Collection;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdTabId;
import org.compiere.Adempiere;
import org.elasticsearch.client.Client;

import de.metas.elasticsearch.indexer.IESModelIndexer;
import de.metas.elasticsearch.indexer.IESModelIndexersRegistry;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterInlineRenderMode;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Services;

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

public class FullTextSearchDocumentFilterDescriptorsProviderFactory
{
	public static final transient FullTextSearchDocumentFilterDescriptorsProviderFactory instance = new FullTextSearchDocumentFilterDescriptorsProviderFactory();

	private static final String MSG_FULL_TEXT_SEARCH_CAPTION = "Search";

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private FullTextSearchDocumentFilterDescriptorsProviderFactory()
	{
	}

	public DocumentFilterDescriptorsProvider createFiltersProvider(
			final AdTabId adTabId,
			@Nullable final String tableName,
			final Collection<DocumentFieldDescriptor> fields)
	{
		if (tableName == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		final IESModelIndexersRegistry esModelIndexersRegistry = Services.get(IESModelIndexersRegistry.class);
		final IESModelIndexer modelIndexer = esModelIndexersRegistry.getFullTextSearchModelIndexer(tableName)
				.orElse(null);
		if (modelIndexer == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		final ITranslatableString caption = msgBL.getTranslatableMsgText(MSG_FULL_TEXT_SEARCH_CAPTION);
		final FullTextSearchFilterContext context = createFullTextSearchFilterContext(modelIndexer);

		final DocumentFilterDescriptor filterDescriptor = DocumentFilterDescriptor.builder()
				.setFilterId(FullTextSearchSqlDocumentFilterConverter.FILTER_ID)
				.setDisplayName(caption)
				.setFrequentUsed(true)
				.setInlineRenderMode(DocumentFilterInlineRenderMode.INLINE_PARAMETERS)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(FullTextSearchSqlDocumentFilterConverter.PARAM_SearchText)
						.setDisplayName(caption)
						.setWidgetType(DocumentFieldWidgetType.Text))
				.addInternalParameter(DocumentFilterParam.ofNameEqualsValue(FullTextSearchSqlDocumentFilterConverter.PARAM_Context, context))
				.build();

		return ImmutableDocumentFilterDescriptorsProvider.of(filterDescriptor);
	}

	private FullTextSearchFilterContext createFullTextSearchFilterContext(final IESModelIndexer modelIndexer)
	{
		final Client elasticsearchClient = Adempiere.getBean(org.elasticsearch.client.Client.class);

		return FullTextSearchFilterContext.builder()
				.elasticsearchClient(elasticsearchClient)
				.modelTableName(modelIndexer.getModelTableName())
				.esIndexName(modelIndexer.getIndexName())
				.esSearchFieldNames(modelIndexer.getFullTextSearchFieldNames())
				.build();
	}

}
