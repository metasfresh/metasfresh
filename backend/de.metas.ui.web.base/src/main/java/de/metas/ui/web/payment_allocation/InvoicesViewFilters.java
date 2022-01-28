/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.payment_allocation;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.IMsgBL;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Services;
import de.metas.util.StringUtils;

class InvoicesViewFilters
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final String FILTER_ID = "default";
	private static final String PARAM_DocumentNo = "DocumentNo";
	private static final String PARAM_POReference = "POReference";

	public ImmutableList<DocumentFilterDescriptor> getDescriptors()
	{
		return ImmutableList.of(createDefaultFilterDescriptor());
	}

	private DocumentFilterDescriptor createDefaultFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID)
				.addParameter(DocumentFilterParamDescriptor.builder()
									  .setFieldName(PARAM_DocumentNo)
									  .setDisplayName(msgBL.translatable(PARAM_DocumentNo))
									  .setMandatory(false)
									  .setWidgetType(DocumentFieldWidgetType.Text))
				.addParameter(DocumentFilterParamDescriptor.builder()
									  .setFieldName(PARAM_POReference)
									  .setDisplayName(msgBL.translatable(PARAM_POReference))
									  .setMandatory(false)
									  .setWidgetType(DocumentFieldWidgetType.Text))
				.build();
	}

	public InvoiceRowFilter extractFilter(final JSONFilterViewRequest filterViewRequest)
	{
		final DocumentFilterList filters = filterViewRequest.getFiltersUnwrapped(ImmutableDocumentFilterDescriptorsProvider.of(getDescriptors()));
		return extractFilter(filters);

	}

	private InvoiceRowFilter extractFilter(final DocumentFilterList filters)
	{
		return filters.getFilterById(FILTER_ID)
				.map(InvoicesViewFilters::toInvoiceRowFilter)
				.orElse(InvoiceRowFilter.ANY);
	}

	private static InvoiceRowFilter toInvoiceRowFilter(final DocumentFilter filter)
	{
		return InvoiceRowFilter.builder()
				.documentNo(StringUtils.trimBlankToNull(filter.getParameterValueAsString(PARAM_DocumentNo, null)))
				.poReference(StringUtils.trimBlankToNull(filter.getParameterValueAsString(PARAM_POReference, null)))
				.build();
	}
}
