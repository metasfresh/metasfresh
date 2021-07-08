package de.metas.ui.web.order.products_proposal.process;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;

import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.service.CopyProductPriceRequest;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.UpdateProductPriceRequest;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowChangeRequest.RowSaved;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalView;
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
		if (!hasChangedRows())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("nothing to save");
		}

		final ProductsProposalView view = getView();
		if (!view.getSinglePriceListVersionId().isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no base price list version");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		streamChangedRows()
				.forEach(this::save);

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}

	private boolean hasChangedRows()
	{
		return streamChangedRows()
				.findAny()
				.isPresent();
	}

	private Stream<ProductsProposalRow> streamChangedRows()
	{
		return getSelectedRows()
				.stream()
				.filter(ProductsProposalRow::isChanged);
	}

	private void save(final ProductsProposalRow row)
	{
		if (!row.isChanged())
		{
			return;
		}

		final BigDecimal userEnteredPriceValue = row.getPrice().getUserEnteredPriceValue();
		final ProductPriceId productPriceId;

		//
		// Update existing product price
		if (row.getProductPriceId() != null)
		{
			productPriceId = row.getProductPriceId();
			pricesListsRepo.updateProductPrice(UpdateProductPriceRequest.builder()
					.productPriceId(productPriceId)
					.priceStd(userEnteredPriceValue)
					.build());
		}
		//
		// Save a new product price which was copied from some other price list version
		else if (row.getCopiedFromProductPriceId() != null)
		{
			productPriceId = pricesListsRepo.copyProductPrice(CopyProductPriceRequest.builder()
					.copyFromProductPriceId(row.getCopiedFromProductPriceId())
					.copyToPriceListVersionId(getPriceListVersionId())
					.priceStd(userEnteredPriceValue)
					.build());
		}
		else
		{
			throw new AdempiereException("Cannot save row: " + row);
		}

		//
		// Refresh row
		getView().patchViewRow(row.getId(), RowSaved.builder()
				.productPriceId(productPriceId)
				.price(row.getPrice().withPriceListPriceValue(userEnteredPriceValue))
				.build());
	}

	private PriceListVersionId getPriceListVersionId()
	{
		return getView()
				.getSinglePriceListVersionId()
				.orElseThrow(() -> new AdempiereException("@NotFound@ @M_PriceList_Version_ID@"));

	}
}
