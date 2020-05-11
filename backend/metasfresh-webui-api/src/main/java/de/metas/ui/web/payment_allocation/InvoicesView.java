package de.metas.ui.web.payment_allocation;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import lombok.Builder;
import lombok.NonNull;

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

public class InvoicesView extends AbstractCustomView<InvoiceRow> implements IEditableView
{
	public static InvoicesView cast(final IView view)
	{
		return (InvoicesView)view;
	}

	private final ImmutableList<RelatedProcessDescriptor> processes;

	@Builder
	private InvoicesView(
			final ViewId viewId,
			final InvoiceRows rows,
			@Nullable final List<RelatedProcessDescriptor> processes)
	{
		super(viewId,
				TranslatableStrings.empty(),
				rows,
				NullDocumentFilterDescriptorsProvider.instance);

		this.processes = processes != null ? ImmutableList.copyOf(processes) : ImmutableList.of();
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return processes;
	}

	@Override
	protected InvoiceRows getRowsData()
	{
		return InvoiceRows.cast(super.getRowsData());
	}

	public void addInvoice(@NonNull final InvoiceId invoiceId)
	{
		final InvoiceRows invoiceRows = getRowsData();
		invoiceRows.addInvoice(invoiceId);
	}

	@Override
	public LookupValuesList getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName)
	{
		throw new UnsupportedOperationException();
	}
}
