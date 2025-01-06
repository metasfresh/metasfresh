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

package de.metas.pricing.rules.price_list_version;

import ch.qos.logback.classic.Level;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.pricing.service.ProductScalePriceService;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/**
 * Calculate Price using Price List Version
 *
 * @author tsa
 */
class MainProductPriceRule extends AbstractPriceListBasedRule
{
	private static final Logger logger = LogManager.getLogger(MainProductPriceRule.class);

	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);

	private final ProductTaxCategoryService productTaxCategoryService = SpringContextHolder.instance.getBean(ProductTaxCategoryService.class);
	
	private final ProductScalePriceService productScalePriceService = SpringContextHolder.instance.getBean(ProductScalePriceService.class);

	@Override
	public void calculate(@NonNull final IPricingContext pricingCtx, @NonNull final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final I_M_PriceList_Version ctxPriceListVersion = getPriceListVersionEffective(pricingCtx);
		if (ctxPriceListVersion == null)
		{
			return;
		}

		final I_M_ProductPrice productPrice = getProductPriceOrNull(pricingCtx.getProductId(),
				PriceListVersionId.ofRepoId(ctxPriceListVersion.getM_PriceList_Version_ID()));

		if (productPrice == null)
		{
			logger.trace("Not found (PLV)");
			return;
		}

		final ProductScalePriceService.ProductPriceSettings productPriceSettings = productScalePriceService.getProductPriceSettings(productPrice, pricingCtx.getQuantity());
		if (productPriceSettings == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No ProductPriceSettings returned for qty : {} and M_ProductPrice_ID: {}", pricingCtx.getQty(), productPrice.getM_ProductPrice_ID());
			return;
		}

		final PriceListVersionId resultPriceListVersionId = PriceListVersionId.ofRepoId(productPrice.getM_PriceList_Version_ID());
		final I_M_PriceList_Version resultPriceListVersion = getOrLoadPriceListVersion(resultPriceListVersionId, ctxPriceListVersion);
		final I_M_PriceList priceList = priceListsRepo.getById(resultPriceListVersion.getM_PriceList_ID());

		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());
		final ProductCategoryId productCategoryId = productsRepo.retrieveProductCategoryByProductId(productId);

		result.setPriceStd(productPriceSettings.getPriceStd());
		result.setPriceList(productPriceSettings.getPriceList());
		result.setPriceLimit(productPriceSettings.getPriceLimit());
		result.setCurrencyId(CurrencyId.ofRepoId(priceList.getC_Currency_ID()));
		result.setProductId(productId);
		result.setProductCategoryId(productCategoryId);
		result.setPriceEditable(productPrice.isPriceEditable());
		result.setDiscountEditable(result.isDiscountEditable() && productPrice.isDiscountEditable());
		result.setEnforcePriceLimit(extractEnforcePriceLimit(priceList));
		result.setTaxIncluded(priceList.isTaxIncluded());
		result.setTaxCategoryId(productTaxCategoryService.getTaxCategoryId(productPrice));
		result.setPriceListVersionId(resultPriceListVersionId);
		result.setPriceUomId(getProductPriceUomId(productPrice)); // 06942 : use product price uom all the time
		result.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.ofNullableCodeOrNominal(productPrice.getInvoicableQtyBasedOn()));
		result.setCalculated(true);

		//
		// Override with calculated BOM price if suitable
		BOMPriceCalculator.builder()
				.bomProductId(productId)
				.asiAware(pricingCtx.getAttributeSetInstanceAware().orElse(null))
				.priceListVersion(resultPriceListVersion)
				.calculate()
				.ifPresent(bomPrices -> updatePricingResultFromBOMPrices(result, bomPrices));
	}

	private BooleanWithReason extractEnforcePriceLimit(final I_M_PriceList priceList)
	{
		final ITranslatableString reason = TranslatableStrings.builder()
				.appendADElement("M_PriceList_ID")
				.append(": ")
				.append(priceList.getName())
				.build();

		return priceList.isEnforcePriceLimit()
				? BooleanWithReason.trueBecause(reason)
				: BooleanWithReason.falseBecause(reason);
	}

	@Nullable
	private I_M_ProductPrice getProductPriceOrNull(final ProductId productId,
			final PriceListVersionId priceListVersionId)
	{
		return ProductPrices.retrieveMainProductPriceOrNull(priceListVersionId, productId);
	}

	private I_M_PriceList_Version getOrLoadPriceListVersion(
			@NonNull final PriceListVersionId priceListVersionId,
			final I_M_PriceList_Version existingPriceListVersion)
	{
		if (existingPriceListVersion != null
				&& existingPriceListVersion.getM_PriceList_Version_ID() == priceListVersionId.getRepoId())
		{
			return existingPriceListVersion;
		}

		return priceListsRepo.getPriceListVersionById(priceListVersionId);
	}

	@Nullable
	private I_M_PriceList_Version getPriceListVersionEffective(final IPricingContext pricingCtx)
	{
		final I_M_PriceList_Version contextPLV = pricingCtx.getM_PriceList_Version();
		if (contextPLV != null)
		{
			return contextPLV.isActive() ? contextPLV : null;
		}

		final I_M_PriceList_Version plv = priceListsRepo.retrievePriceListVersionOrNull(
				pricingCtx.getPriceListId(),
				TimeUtil.asZonedDateTime(pricingCtx.getPriceDate(), SystemTime.zoneId()),
				null // processed
		);

		return plv != null && plv.isActive() ? plv : null;
	}

	private UomId getProductPriceUomId(final I_M_ProductPrice productPrice)
	{
		final UomId productPriceUomId = UomId.ofRepoIdOrNull(productPrice.getC_UOM_ID());
		if (productPriceUomId != null)
		{
			return productPriceUomId;
		}

		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());
		return productsService.getStockUOMId(productId);
	}

	private static void updatePricingResultFromBOMPrices(final IPricingResult to, final BOMPrices from)
	{
		to.setPriceStd(from.getPriceStd());
		to.setPriceList(from.getPriceList());
		to.setPriceLimit(from.getPriceLimit());
	}
}
