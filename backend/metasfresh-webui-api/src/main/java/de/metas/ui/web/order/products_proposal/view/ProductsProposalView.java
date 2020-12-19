package de.metas.ui.web.order.products_proposal.view;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.pricing.PriceListVersionId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.product.ProductId;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.order.products_proposal.filters.ProductsProposalViewFilter;
import de.metas.ui.web.order.products_proposal.filters.ProductsProposalViewFilters;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowAddRequest;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowChangeRequest;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsData;
import de.metas.ui.web.order.products_proposal.service.Order;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
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

public class ProductsProposalView extends AbstractCustomView<ProductsProposalRow> implements IEditableView
{
	public static ProductsProposalView cast(final Object viewObj)
	{
		return (ProductsProposalView)viewObj;
	}

	private final ProductsProposalRowsData rowsData;
	private final ImmutableList<RelatedProcessDescriptor> processes;
	private final ViewId initialViewId;

	@Builder
	private ProductsProposalView(
			@NonNull final WindowId windowId,
			@NonNull final ProductsProposalRowsData rowsData,
			@Nullable final List<RelatedProcessDescriptor> processes,
			@Nullable final ViewId initialViewId)
	{
		super(
				ViewId.random(windowId),
				TranslatableStrings.empty(),
				rowsData,
				NullDocumentFilterDescriptorsProvider.instance);

		this.rowsData = rowsData;
		this.processes = processes != null ? ImmutableList.copyOf(processes) : ImmutableList.of();

		this.initialViewId = initialViewId;
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
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

	@Override
	public ViewHeaderProperties getHeaderProperties()
	{
		return rowsData.getHeaderProperties();
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return processes;
	}

	public ViewId getInitialViewId()
	{
		return initialViewId != null ? initialViewId : getViewId();
	}

	@Override
	public ViewId getParentViewId()
	{
		return initialViewId;
	}

	public Optional<OrderId> getOrderId()
	{
		return rowsData.getOrder().map(Order::getOrderId);
	}

	public Optional<BPartnerId> getBpartnerId()
	{
		return rowsData.getBpartnerId();
	}

	public SOTrx getSoTrx()
	{
		return rowsData.getSoTrx();
	}

	public CurrencyId getCurrencyId()
	{
		
		return rowsData.getCurrencyId();
	}

	public Set<ProductId> getProductIds()
	{
		return rowsData.getProductIds();
	}

	public Optional<PriceListVersionId> getSinglePriceListVersionId()
	{
		return rowsData.getSinglePriceListVersionId();
	}

	public Optional<PriceListVersionId> getBasePriceListVersionId()
	{
		return rowsData.getBasePriceListVersionId();
	}

	public PriceListVersionId getBasePriceListVersionIdOrFail()
	{
		return rowsData.getBasePriceListVersionId()
				.orElseThrow(() -> new AdempiereException("@NotFound@ @M_Pricelist_Version_Base_ID@"));
	}

	public List<ProductsProposalRow> getAllRows()
	{
		return ImmutableList.copyOf(getRows());
	}

	public void addOrUpdateRows(@NonNull final List<ProductsProposalRowAddRequest> requests)
	{
		rowsData.addOrUpdateRows(requests);
		invalidateAll();
	}

	public void patchViewRow(@NonNull final DocumentId rowId, @NonNull final ProductsProposalRowChangeRequest request)
	{
		rowsData.patchRow(rowId, request);
	}

	public void removeRowsByIds(final Set<DocumentId> rowIds)
	{
		rowsData.removeRowsByIds(rowIds);
		invalidateAll();
	}

	public ProductsProposalView filter(final ProductsProposalViewFilter filter)
	{
		rowsData.filter(filter);
		return this;
	}

	@Override
	public DocumentFilterList getFilters()
	{
		return ProductsProposalViewFilters.toDocumentFilters(rowsData.getFilter());
	}
}
