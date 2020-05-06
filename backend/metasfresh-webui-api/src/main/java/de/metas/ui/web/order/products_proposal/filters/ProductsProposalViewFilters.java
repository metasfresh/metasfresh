package de.metas.ui.web.order.products_proposal.filters;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterInlineRenderMode;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class ProductsProposalViewFilters
{
	public static DocumentFilterDescriptorsProvider getDescriptors()
	{
		return ImmutableDocumentFilterDescriptorsProvider.builder()
				.addDescriptor(createDefaultFilterDescriptor())
				.build();
	}

	private static DocumentFilterDescriptor createDefaultFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(ProductsProposalViewFilter.FILTER_ID)
				.setFrequentUsed(true)
				.setInlineRenderMode(DocumentFilterInlineRenderMode.INLINE_PARAMETERS)
				.setDisplayName(getDefaultFilterCaption())
				.addParameter(newParamDescriptor(ProductsProposalViewFilter.PARAM_ProductName)
						.setWidgetType(DocumentFieldWidgetType.Text))
				.build();
	}

	private static ITranslatableString getDefaultFilterCaption()
	{
		return Services.get(IMsgBL.class).getTranslatableMsgText("Default");
	}

	private static DocumentFilterParamDescriptor.Builder newParamDescriptor(final String fieldName)
	{
		return DocumentFilterParamDescriptor.builder()
				.setFieldName(fieldName)
				.setDisplayName(Services.get(IMsgBL.class).translatable(fieldName));
	}

	public static ProductsProposalViewFilter extractPackageableViewFilterVO(@NonNull final JSONFilterViewRequest filterViewRequest)
	{
		final DocumentFilterList filters = filterViewRequest.getFiltersUnwrapped(getDescriptors());
		return extractPackageableViewFilterVO(filters);
	}

	private static ProductsProposalViewFilter extractPackageableViewFilterVO(final DocumentFilterList filters)
	{
		return filters.getFilterById(ProductsProposalViewFilter.FILTER_ID)
				.map(filter -> toProductsProposalViewFilterValue(filter))
				.orElse(ProductsProposalViewFilter.ANY);
	}

	private static ProductsProposalViewFilter toProductsProposalViewFilterValue(final DocumentFilter filter)
	{
		return ProductsProposalViewFilter.builder()
				.productName(filter.getParameterValueAsString(ProductsProposalViewFilter.PARAM_ProductName, null))
				.build();
	}

	public static DocumentFilterList toDocumentFilters(final ProductsProposalViewFilter filter)
	{
		final DocumentFilter.Builder builder = DocumentFilter.builder()
				.setFilterId(ProductsProposalViewFilter.FILTER_ID)
				.setCaption(getDefaultFilterCaption());

		if (!Check.isEmpty(filter.getProductName()))
		{
			builder.addParameter(DocumentFilterParam.ofNameEqualsValue(ProductsProposalViewFilter.PARAM_ProductName, filter.getProductName()));
		}

		if (!builder.hasParameters())
		{
			return DocumentFilterList.EMPTY;
		}

		return DocumentFilterList.of(builder.build());
	}
}
