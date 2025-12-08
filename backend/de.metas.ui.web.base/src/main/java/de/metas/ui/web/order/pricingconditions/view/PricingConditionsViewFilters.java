package de.metas.ui.web.order.pricingconditions.view;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.IMsgBL;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Services;
import lombok.NonNull;

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
	private static final String FILTERID_IsCustomer = "IsCustomer";
	private static final String PARAM_IsCustomer = "IsCustomer";

	private static final String FILTERID_IsVendor = "IsVendor";
	private static final String PARAM_IsVendor = "IsVendor";

	private ImmutableDocumentFilterDescriptorsProvider filterDescriptorsProvider; // lazy
	private DocumentFilterList defaultFilters; // lazy

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
						.fieldName(PARAM_IsCustomer)
						.displayName(Services.get(IMsgBL.class).translatable(PARAM_IsCustomer))
						.widgetType(DocumentFieldWidgetType.YesNo))
				.build();
	}

	private DocumentFilterDescriptor createIsVendorFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTERID_IsVendor)
				.setFrequentUsed(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.fieldName(PARAM_IsVendor)
						.displayName(Services.get(IMsgBL.class).translatable(PARAM_IsVendor))
						.widgetType(DocumentFieldWidgetType.YesNo))
				.build();
	}

	public static Predicate<PricingConditionsRow> isEditableRowOrMatching(final DocumentFilterList filters)
	{
		if (filters.isEmpty())
		{
			return Predicates.alwaysTrue();
		}

		final boolean showCustomers = filters.getParamValueAsBoolean(FILTERID_IsCustomer, PARAM_IsCustomer, false);
		final boolean showVendors = filters.getParamValueAsBoolean(FILTERID_IsVendor, PARAM_IsVendor, false);
		final boolean showAll = !showCustomers && !showVendors;
		if (showAll)
		{
			return Predicates.alwaysTrue();
		}

		return row -> row.isEditable()
				|| ((showCustomers && row.isCustomer()) || (showVendors && row.isVendor()));
	}

	public DocumentFilterList extractFilters(@NonNull final JSONFilterViewRequest filterViewRequest)
	{
		return filterViewRequest.getFiltersUnwrapped(getFilterDescriptorsProvider());
	}

	public DocumentFilterList extractFilters(@NonNull final CreateViewRequest request)
	{
		return request.isUseAutoFilters()
				? getDefaultFilters()
				: request.getFiltersUnwrapped(getFilterDescriptorsProvider());
	}

	private DocumentFilterList getDefaultFilters()
	{
		if (defaultFilters == null)
		{
			final DocumentFilter isCustomer = DocumentFilter.singleParameterFilter(FILTERID_IsCustomer, PARAM_IsCustomer, Operator.EQUAL, true);

			defaultFilters = DocumentFilterList.of(isCustomer);
		}
		return defaultFilters;
	}
}
