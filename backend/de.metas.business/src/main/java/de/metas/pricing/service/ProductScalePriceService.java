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

import de.metas.pricing.ProductPriceId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.compiere.Adempiere;
import org.compiere.model.I_M_ProductPrice;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductScalePriceService
{
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private ProductScalePriceRepository productScalePriceRepository;

	public static ProductScalePriceService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ProductScalePriceService(new ProductScalePriceRepository());
	}

	@Nullable
	public ProductPriceSettings getProductPriceSettings(@NonNull final I_M_ProductPrice productPrice, @Nullable final Quantity qty)
	{
		if (qty == null)
		{
			return ProductPriceSettings.of(productPrice);
		}

		final ScalePriceUsage scalePriceUsage = ScalePriceUsage.ofCode(productPrice.getUseScalePrice());
		if (!scalePriceUsage.isUseScalePrice())
		{
			return ProductPriceSettings.of(productPrice);
		}

		final ScalePriceQtyFrom scalePriceQtyFrom = ScalePriceQtyFrom.optionalOfNullableCode(productPrice.getScalePriceQuantityFrom()).orElse(null);
		if (scalePriceQtyFrom == null || !scalePriceQtyFrom.isQuantity())
		{
			return ProductPriceSettings.of(productPrice);
		}

		final ProductPriceId productPriceId = ProductPriceId.ofRepoId(productPrice.getM_ProductPrice_ID());
		final ScaleProductPriceList scalePrices = getScalePrices(productPriceId);
		if (scalePrices.isEmpty())
		{
			return ProductPriceSettings.of(productPrice);
		}

		final BigDecimal qtyInProductPriceUom = convertQtyToPriceUOM(qty, productPrice).toBigDecimal();
		final ScaleProductPrice scalePrice = scalePrices.getByQuantity(qtyInProductPriceUom).orElse(null);
		if (scalePrice != null)
		{
			return ProductPriceSettings.of(scalePrice);

		}
		else if (scalePriceUsage.isAllowFallbackToProductPrice())
		{
			return ProductPriceSettings.of(productPrice);
		}
		else
		{
			return null;
		}
	}

	public ScaleProductPriceList getScalePrices(final ProductPriceId productPriceId)
	{
		return productScalePriceRepository.retrieveScalePrices(productPriceId);
	}

	@NonNull
	private Quantity convertQtyToPriceUOM(@NonNull final Quantity quantity, @NonNull final I_M_ProductPrice productPrice)
	{
		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());
		final UomId productPriceUomId = UomId.ofRepoId(productPrice.getC_UOM_ID());
		return uomConversionBL.convertQuantityTo(quantity, productId, productPriceUomId);
	}

	public void deleteByProductPriceId(@NonNull final ProductPriceId productPriceId)
	{
		productScalePriceRepository.deleteByProductPriceId(productPriceId);
	}

	public void createScalePriceIfMissing(final ProductPriceId productPriceId, final ScaleProductPrice scalePrice)
	{
		if (getScalePrices(productPriceId).getByQuantity(scalePrice.getQuantityMin()).isPresent())
		{
			return;
		}

		productScalePriceRepository.createNew(productPriceId, scalePrice);
	}

	@Value
	public static class ProductPriceSettings
	{
		@NonNull BigDecimal priceStd;
		@NonNull BigDecimal priceLimit;
		@NonNull BigDecimal priceList;

		public static ProductPriceSettings of(@NonNull final I_M_ProductPrice productPrice)
		{
			return new ProductPriceSettings(productPrice.getPriceStd(), productPrice.getPriceLimit(), productPrice.getPriceList());
		}

		public static ProductPriceSettings of(@NonNull final ScaleProductPrice productScalePrice)
		{
			return new ProductPriceSettings(productScalePrice.getPriceStd(), productScalePrice.getPriceLimit(), productScalePrice.getPriceList());
		}
	}
}
