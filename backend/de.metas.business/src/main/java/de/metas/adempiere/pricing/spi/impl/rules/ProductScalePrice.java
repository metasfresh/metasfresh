package de.metas.adempiere.pricing.spi.impl.rules;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.money.CurrencyId;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.pricing.rules.price_list_version.AbstractPriceListBasedRule;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductBL;
import de.metas.product.IProductPA;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.I_M_ProductScalePrice;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.X_M_ProductPrice;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Calculate price using {@link I_M_ProductScalePrice}
 *
 * @author tsa
 *
 */
public class ProductScalePrice extends AbstractPriceListBasedRule
{

	public static final AdMessageKey MSG_NO_SCALE_PRICE = AdMessageKey.of("NoScalePrice");

	private final ProductTaxCategoryService productTaxCategoryService = SpringContextHolder.instance.getBean(ProductTaxCategoryService.class);

	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!super.applies(pricingCtx, result))
		{
			return false;
		}

		if (pricingCtx.getPriceListVersionId() == null)
		{
			return false;
		}

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final I_M_PriceList_Version priceListVersion = pricingCtx.getM_PriceList_Version();
		if (priceListVersion == null)
		{
			return;
		}

		final I_M_ProductPrice productPrice = ProductPrices.newQuery(priceListVersion)
				.setProductId(pricingCtx.getProductId())
				.noAttributePricing()
				.onlyValidPrices(true)
				.onlyScalePrices()
				.firstMatching();
		if (productPrice == null)
		{
			return;
		}

		if (Objects.equals(productPrice.getUseScalePrice(), X_M_ProductPrice.USESCALEPRICE_DonTUseScalePrice))
		{
			return;
		}

		final BigDecimal qty = pricingCtx.getQty();
		final I_M_ProductScalePrice scalePrice = Services.get(IProductPA.class)
				.retrieveOrCreateScalePrices(productPrice.getM_ProductPrice_ID()
						, qty
						, false // createNew
						, ITrx.TRXNAME_None);
		if (scalePrice == null)
		{
			if (Objects.equals(productPrice.getUseScalePrice(), X_M_ProductPrice.USESCALEPRICE_UseScalePriceFallbackToProductPrice))
			{
				return;
			}
			else
			{
				throw new AdempiereException(MSG_NO_SCALE_PRICE, qty);
			}
		}

		calculateWithScalePrice(pricingCtx, result, scalePrice);

		result.setTaxCategoryId(productTaxCategoryService.getTaxCategoryId(productPrice));
	}

	private IPricingResult calculateWithScalePrice(
			final IPricingContext pricingCtx,
			final IPricingResult result,
			@NonNull final I_M_ProductScalePrice scalePrice)
	{
		final ProductId productId = pricingCtx.getProductId();
		PriceListVersionId priceListVersionId = pricingCtx.getPriceListVersionId();
		PriceListId priceListId = pricingCtx.getPriceListId();
		//
		BigDecimal m_PriceStd = null;
		BigDecimal m_PriceList = null;
		BigDecimal m_PriceLimit = null;
		UomId uomId;
		//
		//

		final I_M_ProductPrice productPrice = scalePrice.getM_ProductPrice();
		UomId ppUomId = UomId.ofRepoId(productPrice.getC_UOM_ID());
		m_PriceStd = scalePrice.getPriceStd();
		m_PriceList = scalePrice.getPriceList();
		m_PriceLimit = scalePrice.getPriceLimit();
		uomId = Services.get(IProductBL.class).getStockUOMId(productId);

		if (priceListId == null)
		{
			final I_M_PriceList_Version priceListVersion = pricingCtx.getM_PriceList_Version();
			priceListId = PriceListId.ofRepoId(priceListVersion.getM_PriceList_ID());
		}

		if (priceListVersionId == null)
		{
			priceListVersionId = PriceListVersionId.ofRepoId(productPrice.getM_PriceList_Version_ID());
		}

		final I_M_PriceList priceList = Services.get(IPriceListDAO.class).getById(priceListId);
		final CurrencyId currencyId = CurrencyId.ofRepoId(priceList.getC_Currency_ID());
		final BooleanWithReason enforcePriceLimit = extractEnforcePriceLimit(priceList);
		final boolean isTaxIncluded = priceList.isTaxIncluded();

		result.setPriceStd(m_PriceStd);
		result.setPriceList(m_PriceList);
		result.setPriceLimit(m_PriceLimit);
		result.setCurrencyId(currencyId);
		result.setPriceEditable(productPrice.isPriceEditable());
		result.setDiscountEditable(productPrice.isDiscountEditable());
		result.setEnforcePriceLimit(enforcePriceLimit);
		result.setTaxIncluded(isTaxIncluded);
		result.setCalculated(true);

		// 06942 : use product price uom all the time
		if (ppUomId == null)
		{
			result.setPriceUomId(uomId);
		}
		else
		{
			result.setPriceUomId(ppUomId);
		}
		return result;
	}

	private BooleanWithReason extractEnforcePriceLimit(final I_M_PriceList priceList)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		return priceList.isEnforcePriceLimit()
				? BooleanWithReason.trueBecause(msgBL.translatable("M_PriceList_ID"))
				: BooleanWithReason.falseBecause(msgBL.translatable("M_PriceList_ID"));
	}

}
