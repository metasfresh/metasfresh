package de.metas.ui.web.order.products_proposal.process;

import java.util.stream.Stream;

import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.service.CopyProductPriceRequest;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalRowChangeRequest;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalView;
import de.metas.util.Check;
import de.metas.util.Services;

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

public class WEBUI_ProductsProposal_SaveProductPriceToCurrentPriceListVersion extends ProductsProposalViewBasedProcess
{
	private final IPriceListDAO pricesListsRepo = Services.get(IPriceListDAO.class);

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!hasRowsCopiedFromButNotSaved())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("nothing to save");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		streamRowsCopiedFromButNotSaved()
				.forEach(this::save);

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}

	private boolean hasRowsCopiedFromButNotSaved()
	{
		return streamRowsCopiedFromButNotSaved()
				.findAny()
				.isPresent();
	}

	private Stream<ProductsProposalRow> streamRowsCopiedFromButNotSaved()
	{
		return getSelectedRows()
				.stream()
				.filter(ProductsProposalRow::isCopiedFromButNotSaved);
	}

	public void save(final ProductsProposalRow row)
	{
		Check.assume(row.isCopiedFromButNotSaved(), "row shall be copied but not saved: {}", row);

		final ProductsProposalView view = getView();
		final PriceListVersionId priceListVersionId = view.getSinglePriceListVersionIdOrNull();

		final ProductPriceId productPriceId = pricesListsRepo.copyProductPrice(CopyProductPriceRequest.builder()
				.copyFromProductPriceId(row.getCopiedFromProductPriceId())
				.copyToPriceListVersionId(priceListVersionId)
				.build());

		view.patchViewRow(row.getId(), ProductsProposalRowChangeRequest.rowWasSaved(productPriceId));
	}
}
