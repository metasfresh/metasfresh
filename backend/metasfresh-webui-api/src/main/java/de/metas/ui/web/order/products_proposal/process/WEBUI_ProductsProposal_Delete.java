package de.metas.ui.web.order.products_proposal.process;

import java.util.List;
import java.util.Set;

import java.util.Objects;
import com.google.common.collect.ImmutableSet;

import de.metas.pricing.ProductPriceId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.window.datatypes.DocumentId;
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

public class WEBUI_ProductsProposal_Delete extends ProductsProposalViewBasedProcess
{
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final List<ProductsProposalRow> selectedRows = getSelectedRows();
		if (selectedRows.isEmpty()) // shall not happen
		{
			return MSG_OK;
		}

		//
		// Remove product prices from database
		final Set<ProductPriceId> productPriceIds = selectedRows.stream()
				.map(ProductsProposalRow::getProductPriceId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		priceListsRepo.deleteProductPricesByIds(productPriceIds);

		//
		// Remove rows from view
		final Set<DocumentId> rowIds = selectedRows.stream()
				.map(ProductsProposalRow::getId)
				.collect(ImmutableSet.toImmutableSet());
		getView().removeRowsByIds(rowIds);

		return MSG_OK;
	}

}
