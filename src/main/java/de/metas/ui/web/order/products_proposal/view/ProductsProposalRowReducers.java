package de.metas.ui.web.order.products_proposal.view;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

import de.metas.ui.web.order.products_proposal.view.ProductsProposalRow.ProductsProposalRowBuilder;
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
	public static ProductsProposalRow copyAndChange(
			@NonNull final ProductsProposalRowChangeRequest request,
			@NonNull final ProductsProposalRow row)
	{
		final ProductsProposalRowBuilder newRowBuilder = row.toBuilder();

		if (request.getQty() != null)
		{
			newRowBuilder.qty(request.getQty().orElse(null));
		}

		if (request.getPrice() != null)
		{
			if (request.isUserChange() && !row.isPriceEditable())
			{
				throw new AdempiereException("Price is not editable")
						.setParameter("row", row);
			}

			newRowBuilder.price(request.getPrice().orElse(BigDecimal.ZERO));
		}

		if (request.getProductPriceId() != null)
		{
			newRowBuilder.productPriceId(request.getProductPriceId().orElse(null));
		}

		return newRowBuilder.build();
	}

}
