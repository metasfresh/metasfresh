package de.metas.ui.web.order.sales.pricingConditions.view;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.adempiere.util.Services;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.IMsgBL;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.DocumentFiltersList;
import de.metas.ui.web.document.filter.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

class PricingConditionsViewFilters
{
	private static String FILTERID_IsCustomer = "IsCustomer";
	private static String PARAM_IsCustomer = "IsCustomer";

	private static String FILTERID_IsVendor = "IsVendor";
	private static String PARAM_IsVendor = "IsVendor";

	private ImmutableDocumentFilterDescriptorsProvider filterDescriptorsProvider;
	private DocumentFiltersList defaultFilters;

	public DocumentFilterDescriptorsProvider getFilterDescriptorsProvider()
	{
		if (filterDescriptorsProvider == null)
		{
			filterDescriptorsProvider = ImmutableDocumentFilterDescriptorsProvider.of(createFilterDescriptors());
		}
		return filterDescriptorsProvider;
	}

	public Collection<DocumentFilterDescriptor> getFilterDescriptors()
	{
		return getFilterDescriptorsProvider().getAll();
	}

	private List<DocumentFilterDescriptor> createFilterDescriptors()
	{
		return ImmutableList.of(
				createIsCustomerFilterDescriptor(),
				createIsVendorFilterDescriptor());
	}

	private DocumentFilterDescriptor createIsCustomerFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTERID_IsCustomer)
				.setFrequentUsed(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(PARAM_IsCustomer)
						.setDisplayName(Services.get(IMsgBL.class).translatable(PARAM_IsCustomer))
						.setWidgetType(DocumentFieldWidgetType.YesNo))
				.build();
	}

	private DocumentFilterDescriptor createIsVendorFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTERID_IsVendor)
				.setFrequentUsed(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(PARAM_IsVendor)
						.setDisplayName(Services.get(IMsgBL.class).translatable(PARAM_IsVendor))
						.setWidgetType(DocumentFieldWidgetType.YesNo))
				.build();
	}

	public static Predicate<PricingConditionsRow> isEditableRowOrMatching(final DocumentFiltersList filters)
	{
		if (filters.isEmpty())
		{
			return Predicates.alwaysTrue();
		}

		final boolean showCustomers = filters.getParamValueAsBoolean(FILTERID_IsCustomer, PARAM_IsCustomer, false);
		final boolean showVendors = filters.getParamValueAsBoolean(FILTERID_IsVendor, PARAM_IsVendor, false);

		return row -> row.isEditable()
				|| ((showCustomers && row.isCustomer()) || (showVendors && row.isVendor()));
	}

	public DocumentFiltersList extractFilters(final CreateViewRequest request)
	{
		return request.isUseAutoFilters() ? getDefaultFilters() : request.getFilters().unwrapAndCopy(getFilterDescriptorsProvider());
	}

	private DocumentFiltersList getDefaultFilters()
	{
		if (defaultFilters == null)
		{
			final DocumentFilter isCustomer = DocumentFilter.singleParameterFilter(FILTERID_IsCustomer, PARAM_IsCustomer, Operator.EQUAL, true);

			defaultFilters = DocumentFiltersList.ofFilters(isCustomer);
		}
		return defaultFilters;
	}

}
