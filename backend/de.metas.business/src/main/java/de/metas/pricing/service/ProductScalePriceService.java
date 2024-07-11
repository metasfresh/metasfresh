/*
 * #%L
 * de.metas.business
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

package de.metas.pricing.service;

import de.metas.product.IProductPA;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.I_M_ProductScalePrice;
import org.compiere.model.I_M_ProductPrice;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Service
public class ProductScalePriceService
{
	private final IProductPA productPA = Services.get(IProductPA.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@Nullable
	public ProductPriceSettings getProductPriceSettings(@NonNull final I_M_ProductPrice productPrice, @Nullable final Quantity qty)
	{
		if (qty == null)
		{
			return ProductPriceSettings.of(productPrice);
		}

		final ScalePriceUsage scalePriceUsage = ScalePriceUsage.ofCode(productPrice.getUseScalePrice());

		if (!scalePriceUsage.useScalePrice())
		{
			return ProductPriceSettings.of(productPrice);
		}

		final BigDecimal qtyInProductPriceUom = getQtyInProductPriceUOM(productPrice, qty);

		final I_M_ProductScalePrice scalePrice = productPA.retrieveScalePrices(productPrice.getM_ProductPrice_ID(), qtyInProductPriceUom, ITrx.TRXNAME_None);

		if (scalePrice != null)
		{
			return ProductPriceSettings.of(scalePrice);

		}
		else if (scalePriceUsage.allowFallbackToProductPrice())
		{
			return ProductPriceSettings.of(productPrice);
		}
		else
		{
			return null;
		}
	}

	@NonNull
	private BigDecimal getQtyInProductPriceUOM(@NonNull final I_M_ProductPrice productPrice, @NonNull final Quantity quantity)
	{
		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());
		final UomId productPriceUomId = UomId.ofRepoId(productPrice.getC_UOM_ID());

		return uomConversionBL.convertQty(productId, quantity.toBigDecimal(), quantity.getUomId(), productPriceUomId);
	}

	@Value
	public static class ProductPriceSettings
	{
		@NonNull
		BigDecimal priceStd;

		@NonNull
		BigDecimal priceLimit;

		@NonNull
		BigDecimal priceList;

		public static ProductPriceSettings of(@NonNull final I_M_ProductPrice productPrice)
		{
			return new ProductPriceSettings(productPrice.getPriceStd(), productPrice.getPriceLimit(), productPrice.getPriceList());
		}

		public static ProductPriceSettings of(@NonNull final I_M_ProductScalePrice productScalePrice)
		{
			return new ProductPriceSettings(productScalePrice.getPriceStd(), productScalePrice.getPriceLimit(), productScalePrice.getPriceList());
		}
	}
}
