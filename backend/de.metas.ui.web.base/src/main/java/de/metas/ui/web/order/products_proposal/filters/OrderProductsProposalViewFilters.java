package de.metas.ui.web.order.products_proposal.filters;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterInlineRenderMode;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Filter descriptors for the {@code orderProductsProposal} view ("Produktvorschläge") only.
 * <p>
 * Kept deliberately separate from {@link ProductsProposalViewFilters} so that the sibling
 * "Andere Produkte" sub-view ({@code basePLVProductsProposal}) gains no new filter parameter.
 */
@UtilityClass
public class OrderProductsProposalViewFilters
{
	public static final String FILTER_ID = "onlyDeliveredFilter";

	public static DocumentFilterDescriptorsProvider getDescriptors()
	{
		return ImmutableDocumentFilterDescriptorsProvider.builder()
				.addDescriptor(createOnlyDeliveredFilterDescriptor())
				.build();
	}

	private static DocumentFilterDescriptor createOnlyDeliveredFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID)
				.setFrequentUsed(true)
				.setInlineRenderMode(DocumentFilterInlineRenderMode.INLINE_PARAMETERS)
				.setDisplayName(getOnlyDeliveredCaption())
				.addParameter(DocumentFilterParamDescriptor.builder()
						.fieldName(ProductsProposalViewFilter.PARAM_OnlyDelivered)
						.displayName(getOnlyDeliveredCaption())
						.widgetType(DocumentFieldWidgetType.YesNo))
				.build();
	}

	static ITranslatableString getOnlyDeliveredCaption()
	{
		return Services.get(IMsgBL.class).getTranslatableMsgText(ProductsProposalViewFilter.PARAM_OnlyDelivered);
	}

	public static ProductsProposalViewFilter extractFilter(@NonNull final JSONFilterViewRequest filterViewRequest)
	{
		final DocumentFilterList filters = filterViewRequest.getFiltersUnwrapped(getDescriptors());
		return filters.getFilterById(FILTER_ID)
				.map(OrderProductsProposalViewFilters::toProductsProposalViewFilterValue)
				.orElse(ProductsProposalViewFilter.ANY);
	}

	private static ProductsProposalViewFilter toProductsProposalViewFilterValue(final DocumentFilter filter)
	{
		return ProductsProposalViewFilter.builder()
				.onlyDelivered(filter.getParameterValueAsBoolean(ProductsProposalViewFilter.PARAM_OnlyDelivered, false))
				.build();
	}
}
