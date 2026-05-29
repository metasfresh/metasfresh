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

package de.metas.ui.web.invoice.match_inout_costs;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.lang.SOTrx;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.List;

public class InOutCostsView extends AbstractCustomView<InOutCostRow>
{
	@NonNull private final ImmutableList<RelatedProcessDescriptor> relatedProcesses;

	@Builder
	private InOutCostsView(
			final @NonNull ViewId viewId,
			final @NonNull InOutCostsViewData rowsData,
			final @NonNull DocumentFilterDescriptor filterDescriptor,
			final @NonNull @Singular ImmutableList<RelatedProcessDescriptor> relatedProcesses)
	{
		super(viewId, null, rowsData, ImmutableDocumentFilterDescriptorsProvider.of(filterDescriptor));
		this.relatedProcesses = relatedProcesses;
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId) {return null;}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors() {return relatedProcesses;}

	@Override
	public DocumentFilterList getFilters() {return DocumentFilterList.ofNullable(getRowsData().getFilter());}

	@Override
	protected InOutCostsViewData getRowsData() {return (InOutCostsViewData)super.getRowsData();}

	public SOTrx getSoTrx() {return getRowsData().getSoTrx();}

	public InvoiceAndLineId getInvoiceLineId() {return getRowsData().getInvoiceAndLineId();}

	@Override
	public ViewHeaderProperties getHeaderProperties() {return getRowsData().getHeaderProperties();}
}
