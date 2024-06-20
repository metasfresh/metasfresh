package de.metas.ui.web.order.products_proposal.process;

import com.google.common.collect.ImmutableList;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowAddRequest;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalView;

import java.util.List;

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

public class WEBUI_ProductsProposal_AddProductFromBasePriceList extends ProductsProposalViewBasedProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		addSelectedRowsToInitialView();
		closeAllViewsAndShowInitialView();
		return MSG_OK;
	}

	private void addSelectedRowsToInitialView()
	{
		final ProductsProposalView initialView = getInitialView();

		final List<ProductsProposalRowAddRequest> addRequests = getSelectedRows()
				.stream()
				.map(this::toProductsProposalRowAddRequest)
				.collect(ImmutableList.toImmutableList());

		initialView.addOrUpdateRows(addRequests);
	}

	private ProductsProposalRowAddRequest toProductsProposalRowAddRequest(final ProductsProposalRow row)
	{
		return ProductsProposalRowAddRequest.builder()
				.product(row.getProduct())
				.asiDescription(row.getAsiDescription())
				.asiId(row.getAsiId())
				.priceListPrice(row.getPrice().getUserEnteredPrice())
				.lastShipmentDays(row.getLastShipmentDays())
				.copiedFromProductPriceId(row.getProductPriceId())
				.packingMaterialId(row.getPackingMaterialId())
				.packingDescription(row.getPackingDescription())
				.build();
	}
}
