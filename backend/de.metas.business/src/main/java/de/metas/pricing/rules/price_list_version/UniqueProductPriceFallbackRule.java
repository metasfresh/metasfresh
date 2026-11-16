/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.pricing.IPackingMaterialAware;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

import java.util.List;

/**
 * Last-resort fallback after {@link MainProductPriceRule}.
 *
 * <p>If the product has <b>exactly one</b> active, valid {@link I_M_ProductPrice} on the
 * effective price-list-version, that single record is used as the price - regardless of
 * {@code M_HU_PI_Item_Product_ID} or {@code IsAttributeDependant}. This handles the case
 * where a customer maintains a single tagged price row per product on a PLV without a
 * separate "baseline" sibling that the strict rules require.
 *
 * <p>Zero or multiple candidates -> no-op (genuinely missing or ambiguous; existing strict
 * semantics keep applying).
 *
 * <p>See https://github.com/metasfresh/me03/issues/29144 (Spavetti / sp80).
 */
class UniqueProductPriceFallbackRule extends AbstractPriceListBasedRule
{
	private static final Logger logger = LogManager.getLogger(UniqueProductPriceFallbackRule.class);

	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);

	private final ProductTaxCategoryService productTaxCategoryService = SpringContextHolder.instance.getBean(ProductTaxCategoryService.class);
	private final ProductScalePriceService productScalePriceService = SpringContextHolder.instance.getBean(ProductScalePriceService.class);

	@Override
	public void calculate(@NonNull final IPricingContext pricingCtx, @NonNull final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final I_M_PriceList_Version plv = pricingCtx.getM_PriceList_Version();
		if (plv == null || !plv.isActive())
		{
			return;
		}

		final ProductId productId = pricingCtx.getProductId();

		// Query without IsAttributeDependant / M_HU_PI_Item_Product_ID filtering.
		// dontMatchAttributes() puts the query in IGNORE mode so neither the IsAttributeDependant
		// flag nor the registered HUPIItemProductMatcher_None main-matcher is applied.
		final List<I_M_ProductPrice> candidates = ProductPrices.newQuery(plv)
				.setProductId(productId)
				.onlyValidPrices(true)
				.dontMatchAttributes()
				.list(I_M_ProductPrice.class);

		if (candidates.size() != 1)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(
					"UniqueProductPriceFallbackRule - skip; productId={} on plv={} has {} candidates",
					productId, plv.getM_PriceList_Version_ID(), candidates.size());
			return;
		}

		final I_M_ProductPrice productPrice = candidates.get(0);

		final ProductScalePriceService.ProductPriceSettings productPriceSettings =
				productScalePriceService.getProductPriceSettings(productPrice, pricingCtx.getQuantity());
		if (productPriceSettings == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(
					"UniqueProductPriceFallbackRule - skip; no ProductPriceSettings for qty={} M_ProductPrice_ID={}",
					pricingCtx.getQty(), productPrice.getM_ProductPrice_ID());
			return;
		}

		final PriceListVersionId resultPriceListVersionId = PriceListVersionId.ofRepoId(productPrice.getM_PriceList_Version_ID());
		final I_M_PriceList priceList = priceListsRepo.getById(plv.getM_PriceList_ID());
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
		result.setPriceUomId(getProductPriceUomId(productPrice, productId));
		result.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.ofNullableCodeOrNominal(productPrice.getInvoicableQtyBasedOn()));
		result.setCalculated(true);

		// Propagate the price's attribute set and packing material into the pricing result so
		// downstream consumers (order line, invoice candidate) inherit the same ASI / packing
		// dimensions as the price record we matched. Mirrors
		// AttributePricing.setResultForProductPriceAttribute.
		result.addPricingAttributes(attributePricingBL.extractPricingAttributes(productPrice));
		InterfaceWrapperHelper.getRepoIdOptional(productPrice, IPackingMaterialAware.COLUMNNAME_M_HU_PI_Item_Product_ID, HUPIItemProductId::ofRepoId)
				.ifPresent(result::setPackingMaterialId);

		// BOM override - keep parity with MainProductPriceRule.
		BOMPriceCalculator.builder()
				.bomProductId(productId)
				.asiAware(pricingCtx.getAttributeSetInstanceAware().orElse(null))
				.priceListVersion(plv)
				.calculate()
				.ifPresent(bomPrices -> updatePricingResultFromBOMPrices(result, bomPrices));
	}

	private BooleanWithReason extractEnforcePriceLimit(@NonNull final I_M_PriceList priceList)
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

	private UomId getProductPriceUomId(@NonNull final I_M_ProductPrice productPrice, @NonNull final ProductId productId)
	{
		final UomId productPriceUomId = UomId.ofRepoIdOrNull(productPrice.getC_UOM_ID());
		if (productPriceUomId != null)
		{
			return productPriceUomId;
		}

		return productsService.getStockUOMId(productId);
	}

	private static void updatePricingResultFromBOMPrices(@NonNull final IPricingResult to, @NonNull final BOMPrices from)
	{
		to.setPriceStd(from.getPriceStd());
		to.setPriceList(from.getPriceList());
		to.setPriceLimit(from.getPriceLimit());
	}
}
