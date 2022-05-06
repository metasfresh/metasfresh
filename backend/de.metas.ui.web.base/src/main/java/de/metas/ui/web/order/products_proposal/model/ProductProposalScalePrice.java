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

package de.metas.ui.web.order.products_proposal.model;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.AdMessageKey;
import de.metas.pricing.ProductPriceId;
import de.metas.product.IProductPA;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.X_M_ProductPrice;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Builder
@EqualsAndHashCode
@ToString
public final class ProductProposalScalePrice
{
	@NonNull
	private final ProductPriceId productPriceId;

	public BigDecimal withQty(final BigDecimal qtyEntered)
	{
		final BigDecimal qty = qtyEntered != null && qtyEntered.signum() > 0 ?
				qtyEntered :
				BigDecimal.ONE;

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.load(productPriceId, I_M_ProductPrice.class);
		final I_M_ProductScalePrice scalePrice = Services.get(IProductPA.class)
				.retrieveOrCreateScalePrices(productPriceId.getRepoId()
						, qty
						, false // createNew
						, ITrx.TRXNAME_None);

		if (Objects.isNull(scalePrice))
		{
			if (Objects.equals(productPrice.getUseScalePrice(), X_M_ProductPrice.USESCALEPRICE_UseScalePriceFallbackToProductPrice))
			{
				return productPrice.getPriceStd();
			}
			else
			{
				throw new AdempiereException(AdMessageKey.of("NoScalePrice"), qtyEntered);
			}
		}
		return scalePrice.getPriceStd();
	}

	public static boolean isProductPriceUseScalePrice(@NonNull final I_M_ProductPrice productPrice)
	{
		return Objects.equals(productPrice.getUseScalePrice(), X_M_ProductPrice.USESCALEPRICE_UseScalePriceFallbackToProductPrice)
				|| Objects.equals(productPrice.getUseScalePrice(), X_M_ProductPrice.USESCALEPRICE_UseScalePriceStrict);
	}

}
