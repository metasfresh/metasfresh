package de.metas.ui.web.order.products_proposal.model;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.ProductsProposalRowBuilder;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowChangeRequest.RowSaved;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowChangeRequest.RowUpdate;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowChangeRequest.UserChange;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class ProductsProposalRowReducers
{
	public static ProductsProposalRow reduce(
			@NonNull final ProductsProposalRow row,
			@NonNull final ProductsProposalRowChangeRequest request)
	{
		if (request instanceof UserChange)
		{
			return reduceUserRequest(row, (UserChange)request);
		}
		else if (request instanceof RowUpdate)
		{
			return reduceRowUpdate(row, (RowUpdate)request);
		}
		if (request instanceof RowSaved)
		{
			return reduceRowSaved(row, (RowSaved)request);
		}
		else
		{
			throw new AdempiereException("Unknown request: " + request);
		}
	}

	private static ProductsProposalRow reduceUserRequest(final ProductsProposalRow row, final UserChange request)
	{
		final ProductsProposalRowBuilder newRowBuilder = row.toBuilder();

		if (request.getQty() != null)
		{
			newRowBuilder.qty(request.getQty().orElse(null));
		}

		if (request.getPrice() != null)
		{
			if (!row.isPriceEditable())
			{
				throw new AdempiereException("Price is not editable")
						.setParameter("row", row);
			}

			final BigDecimal newUserEnteredPriceValue = request.getPrice().orElse(BigDecimal.ZERO);
			final ProductProposalPrice newPrice = row.getPrice().withUserEnteredPriceValue(newUserEnteredPriceValue);
			newRowBuilder.price(newPrice);
		}

		if (request.getDescription() != null)
		{
			newRowBuilder.description(request.getDescription().orElse(null));
		}

		return newRowBuilder.build();
	}

	private static ProductsProposalRow reduceRowUpdate(final ProductsProposalRow row, final RowUpdate request)
	{
		return row.toBuilder()
				.price(request.getPrice())
				.lastShipmentDays(request.getLastShipmentDays())
				.copiedFromProductPriceId(request.getCopiedFromProductPriceId())
				.build();
	}

	private static ProductsProposalRow reduceRowSaved(final ProductsProposalRow row, final RowSaved request)
	{
		return row.toBuilder()
				.productPriceId(request.getProductPriceId())
				.price(request.getPrice())
				.copiedFromProductPriceId(null)
				.build();
	}
}
