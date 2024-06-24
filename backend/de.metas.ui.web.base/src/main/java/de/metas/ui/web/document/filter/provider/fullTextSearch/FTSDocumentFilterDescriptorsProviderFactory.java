/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.document.filter.provider.fullTextSearch;

import de.metas.fulltextsearch.config.FTSConfigService;
import de.metas.fulltextsearch.config.FTSFilterDescriptor;
import de.metas.fulltextsearch.query.FTSSearchService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterInlineRenderMode;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsConstants;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;

@Component
public class FTSDocumentFilterDescriptorsProviderFactory implements DocumentFilterDescriptorsProviderFactory
{
	// services
	private static final Logger logger = LogManager.getLogger(FTSDocumentFilterDescriptorsProviderFactory.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final FTSConfigService ftsConfigService;
	private final FTSSearchService ftsSearchService;

	private static final AdMessageKey MSG_FULL_TEXT_SEARCH_CAPTION = AdMessageKey.of("Search");
	static final String FILTER_ID = "full-text-search";
	static final String PARAM_SearchText = "Search";
	static final String PARAM_Context = "Context";

	public FTSDocumentFilterDescriptorsProviderFactory(
			@NonNull final FTSConfigService ftsConfigService,
			@NonNull final FTSSearchService ftsSearchService)
	{
		this.ftsConfigService = ftsConfigService;
		this.ftsSearchService = ftsSearchService;
	}

	@Nullable
	@Override
	public DocumentFilterDescriptorsProvider createFiltersProvider(
			@NonNull final CreateFiltersProviderContext context,
			@NonNull final Collection<DocumentFieldDescriptor> fields)
	{
		return StringUtils.trimBlankToOptional(context.getTableName())
				.map(this::createFiltersProvider)
				.orElse(null);
	}

	@Nullable
	private DocumentFilterDescriptorsProvider createFiltersProvider(
			@NonNull final String tableName)
	{
		final BooleanWithReason elasticsearchEnabled = ftsConfigService.getEnabled();
		if (elasticsearchEnabled.isFalse())
		{
			logger.debug("Skip creating FTS filters because Elasticsearch is not enabled because: {}", elasticsearchEnabled.getReasonAsString());
			return null;
		}

		final FTSFilterDescriptor ftsFilterDescriptor = ftsConfigService
				.getFilterByTargetTableName(tableName)
				.orElse(null);
		if (ftsFilterDescriptor == null)
		{
			return null;
		}

		final FTSFilterContext context = FTSFilterContext.builder()
				.filterDescriptor(ftsFilterDescriptor)
				.searchService(ftsSearchService)
				.build();

		final ITranslatableString caption = msgBL.getTranslatableMsgText(MSG_FULL_TEXT_SEARCH_CAPTION);

		return ImmutableDocumentFilterDescriptorsProvider.of(
				DocumentFilterDescriptor.builder()
						.setFilterId(FILTER_ID)
						.setSortNo(DocumentFilterDescriptorsConstants.SORT_NO_FULL_TEXT_SEARCH)
						.setDisplayName(caption)
						.setFrequentUsed(true)
						.setInlineRenderMode(DocumentFilterInlineRenderMode.INLINE_PARAMETERS)
						.addParameter(DocumentFilterParamDescriptor.builder()
								.setFieldName(PARAM_SearchText)
								.setDisplayName(caption)
								.setWidgetType(DocumentFieldWidgetType.Text))
						.addInternalParameter(PARAM_Context, context)
						.build());
	}
}
