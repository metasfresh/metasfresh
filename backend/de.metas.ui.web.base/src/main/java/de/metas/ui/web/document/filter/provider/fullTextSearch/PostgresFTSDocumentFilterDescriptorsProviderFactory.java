/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterInlineRenderMode;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsConstants;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class PostgresFTSDocumentFilterDescriptorsProviderFactory implements DocumentFilterDescriptorsProviderFactory
{
	@NonNull protected static final String FILTER_ID = "postgres-full-text-search";
	@NonNull private static final AdMessageKey MSG_FULL_TEXT_SEARCH_CAPTION = AdMessageKey.of("Search");
	@NonNull public static final String PARAM_SearchText = "Search";
	@NonNull public static final String PARAM_TableName = "TableName";
	@NonNull private static final String SYSCONFIG_ENABLED =  "de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled";
	@NonNull private static final ImmutableList<String> supportedTableNames = ImmutableList.of(I_C_BPartner.Table_Name, I_C_Invoice.Table_Name, I_M_Product.Table_Name);

	// services
	@NonNull private static final Logger logger = LogManager.getLogger(PostgresFTSDocumentFilterDescriptorsProviderFactory.class);
    @NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);


	@Nullable
	@Override
	public DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull final CreateFiltersProviderContext context)
	{
		return StringUtils.trimBlankToOptional(context.getTableName())
				.map(this::createFiltersProvider)
				.orElse(null);
	}

	@NonNull
	private DocumentFilterDescriptorsProvider createFiltersProvider(
			@NonNull final String tableName)
	{
		if (!supportedTableNames.contains(tableName))
		{
			logger.debug("Skip creating Postgres FTS filters because table {} isn't supported", tableName);
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		if (!sysConfigBL.getBooleanValue(SYSCONFIG_ENABLED, false))
		{
			logger.debug("Skip creating Postgres FTS filters because it is disabled by {}", SYSCONFIG_ENABLED);
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		final ITranslatableString caption = TranslatableStrings.adMessage(MSG_FULL_TEXT_SEARCH_CAPTION);

		return ImmutableDocumentFilterDescriptorsProvider.of(
				DocumentFilterDescriptor.builder()
						.setFilterId(FILTER_ID)
						.setSortNo(DocumentFilterDescriptorsConstants.SORT_NO_POSTGRES_FULL_TEXT_SEARCH)
						.setDisplayName(caption)
						.setFrequentUsed(true)
						.setInlineRenderMode(DocumentFilterInlineRenderMode.INLINE_PARAMETERS)
						.addParameter(DocumentFilterParamDescriptor.builder()
								.fieldName(PARAM_SearchText)
								.displayName(caption)
								.widgetType(DocumentFieldWidgetType.Text))
						.addInternalParameter(PARAM_TableName, tableName)
						.build());
	}
}
