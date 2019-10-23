package de.metas.pricing.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.exceptions.PriceListVersionNotFoundException;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.limit.CompositePriceLimitRule;
import de.metas.pricing.limit.IPriceLimitRule;
import de.metas.pricing.limit.PriceLimitRuleContext;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.pricing.rules.AggregatedPricingRule;
import de.metas.pricing.rules.IPricingRule;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.pricing.service.IPricingDAO;
import de.metas.pricing.service.PricingRuleDescriptor;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.NonNull;

public class PricingBL implements IPricingBL
{
	private static final Logger logger = LogManager.getLogger(PricingBL.class);

	private final CompositePriceLimitRule priceLimitRules = new CompositePriceLimitRule();

	@Override
	public IEditablePricingContext createPricingContext()
	{
		return new PricingContext();
	}

	@Override
	public IEditablePricingContext createInitialContext(
			@Nullable final ProductId productId,
			@Nullable BPartnerId bPartnerId,
			@Nullable final Quantity quantity,
			@NonNull final SOTrx soTrx)
	{
		final IEditablePricingContext pricingCtx = createPricingContext();
		pricingCtx.setProductId(productId);
		pricingCtx.setBPartnerId(bPartnerId);
		pricingCtx.setConvertPriceToContextUOM(true); // backward compatibility

		if (quantity != null)
		{
			if (quantity.signum() != 0)
			{
				pricingCtx.setQty(quantity.toBigDecimal());
			}
			pricingCtx.setUomId(quantity.getUomId());
		}
		else
		{
			pricingCtx.setQty(BigDecimal.ONE);
		}
		pricingCtx.setSOTrx(soTrx);

		return pricingCtx;
	}

	@Override
	public IEditablePricingContext createInitialContext(
			final int M_Product_ID,
			final int C_BPartner_ID,
			final int C_UOM_ID,
			@NonNull final BigDecimal Qty,
			final boolean isSOTrx)
	{
		final IEditablePricingContext pricingCtx = createPricingContext();
		pricingCtx.setProductId(ProductId.ofRepoIdOrNull(M_Product_ID));
		pricingCtx.setBPartnerId(BPartnerId.ofRepoIdOrNull(C_BPartner_ID));
		pricingCtx.setConvertPriceToContextUOM(true); // backward compatibility

		if (Qty != null && Qty.signum() != 0)
		{
			pricingCtx.setQty(Qty);
		}
		else
		{
			pricingCtx.setQty(BigDecimal.ONE);
		}
		pricingCtx.setSOTrx(SOTrx.ofBoolean(isSOTrx));
		pricingCtx.setUomId(UomId.ofRepoIdOrNull(C_UOM_ID));

		return pricingCtx;
	}

	@Override
	public IPricingResult calculatePrice(final IPricingContext pricingCtx)
	{
		final IPricingContext pricingCtxToUse = setupPricingContext(pricingCtx);
		final PricingResult result = createInitialResult(pricingCtxToUse);

		//
		// Do not change anything if the price is manual (task 08908)
		if (isManualPrice(pricingCtxToUse))
		{
			// Returning the result is not reliable enough because we are not sure the values
			// in the initial result are the ones from the reference object.
			// TODO: a new pricing rule for manual prices (if needed)
			// Keeping the fine log anyway
			logger.debug("The pricing engine doesn't have to calculate the price because it was already manually set in the pricing context: {}.", pricingCtxToUse);

			// FIXME tsa: figure out why the line below was commented out?!
			// I think we can drop this feature all together

			// return result;
		}

		final AggregatedPricingRule rules = createPricingRules();
		rules.calculate(pricingCtxToUse, result);

		//
		// After calculation
		//

		// Fail if not calculated
		if (pricingCtxToUse.isFailIfNotCalculated() && !result.isCalculated())
		{
			throw new ProductNotOnPriceListException(pricingCtxToUse)
					.setParameter("pricingResult", result);
		}

		// Convert prices to price UOM if required
		convertResultToContextUOMIfNeeded(result, pricingCtxToUse);

		setPrecisionAndPriceScales(pricingCtxToUse, result);

		if (logger.isDebugEnabled())
		{
			logger.debug("calculatePrice (final context): {}", pricingCtxToUse);
			logger.debug("calculatePrice (result): {}", result);
		}

		return result;
	}

	private static boolean isManualPrice(final IPricingContext pricingCtx)
	{
		// Direct
		{
			final OptionalBoolean manualPriceEnabled = pricingCtx.getManualPriceEnabled();
			if (manualPriceEnabled.isPresent())
			{
				return manualPriceEnabled.isTrue();
			}
		}

		// Try to extract it from referenced object
		final Object referenceObject = pricingCtx.getReferencedObject();
		if (referenceObject != null)
		{
			final Boolean isManualPrice = DisplayType.toBoolean(InterfaceWrapperHelper.getValueOrNull(referenceObject, I_C_InvoiceLine.COLUMNNAME_IsManualPrice), null);
			if (isManualPrice != null)
			{
				return isManualPrice;
			}
		}

		// Fallback: not a manual price
		return false;
	}

	/**
	 * Set various fields in context, before using it.
	 *
	 * @return configured pricing context (to be used in pricing calculations)
	 */
	private IPricingContext setupPricingContext(final IPricingContext pricingCtx)
	{
		final IEditablePricingContext pricingCtxToUse = pricingCtx.copy();
		setupPriceListAndDate(pricingCtxToUse);

		return pricingCtxToUse;
	}

	private void setupPriceListAndDate(final IEditablePricingContext pricingCtx)
	{
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final LocalDate priceDate = pricingCtx.getPriceDate();

		//
		// Set M_PriceList_ID and M_PriceList_Version_ID from pricingSystem, date and country, if necessary;
		// if set and there is one in the pricingCtx, also check if it is consistent.
		if (pricingCtx.getPricingSystemId() != null
				&& priceDate != null
				&& pricingCtx.getProductId() != null
				&& pricingCtx.getCountryId() != null)
		{
			final IPriceListBL priceListBL = Services.get(IPriceListBL.class);
			final I_M_PriceList_Version computedPLV = priceListBL.getCurrentPriceListVersionOrNull(
					pricingCtx.getPricingSystemId(),
					pricingCtx.getCountryId(),
					pricingCtx.getPriceDate(),
					pricingCtx.isSkipCheckingPriceListSOTrxFlag() ? null : pricingCtx.getSoTrx(),
					null);

			if (computedPLV != null)
			{
				pricingCtx.setPriceListId(PriceListId.ofRepoId(computedPLV.getM_PriceList_ID()));

				// while we are at it, do a little sanity check and also set the PLV-ID
				Check.assume(pricingCtx.getPriceListVersionId() == null
						|| pricingCtx.getPriceListVersionId().getRepoId() == computedPLV.getM_PriceList_Version_ID(),
						"Given PricingContext {} has M_PriceList_Version={}, but from M_PricingSystem={}, Product={}, Country={} and SOTrx={}, we computed a different M_PriceList_Version={}",
						pricingCtx,  // 0
						pricingCtx.getM_PriceList_Version(),  // 1
						pricingCtx.getPricingSystemId(),  // 2
						pricingCtx.getProductId(),  // 3
						pricingCtx.getCountryId(),  // 4
						pricingCtx.getSoTrx(),  // 5
						computedPLV);
				pricingCtx.setPriceListVersionId(PriceListVersionId.ofRepoId(computedPLV.getM_PriceList_Version_ID()));
			}
		}

		//
		// Set M_PriceList_Version_ID from PL and date, if necessary.
		if (pricingCtx.getPriceListVersionId() == null
				&& pricingCtx.getPriceListId() != null
				&& priceDate != null)
		{
			final I_M_PriceList priceList = priceListDAO.getById(pricingCtx.getPriceListId());
			try
			{
				final Boolean processedPLVFiltering = null; // task 09533: the user doesn't know about PLV's processed flag, so we can't filter by it
				final I_M_PriceList_Version plv = priceListDAO.retrievePriceListVersionOrNull(priceList, priceDate, processedPLVFiltering);
				if (plv != null)
				{
					final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID());
					logger.debug("Setting to context: M_PriceList_Version_ID={} from M_PriceList={} and PriceDate={}", priceListVersionId, priceList, priceDate);
					pricingCtx.setPriceListVersionId(priceListVersionId);
				}
			}
			catch (PriceListVersionNotFoundException e)
			{
				// NOTE: don't fail here because it could be a valid case and some particular pricing rules can handle it.
				// NOTE2: also pls keep in mind that if we would fail here the whole pricing calculation would fail.
				logger.info("Skip setting pricing context's price list version because it was not found", e);
			}
		}

		//
		// set PL from PLV
		if (pricingCtx.getPriceListId() == null
				&& pricingCtx.getPriceListVersionId() != null)
		{
			final I_M_PriceList_Version priceListVersion = pricingCtx.getM_PriceList_Version();

			logger.info("Setting to context: M_PriceList_ID={} from M_PriceList_Version={}", priceListVersion.getM_PriceList_ID(), priceListVersion);
			pricingCtx.setPriceListId(PriceListId.ofRepoId(priceListVersion.getM_PriceList_ID()));
		}

		//
		// set priceDate from PLV
		if (pricingCtx.getPriceDate() == null
				&& pricingCtx.getPriceListVersionId() != null)
		{
			final I_M_PriceList_Version priceListVersion = pricingCtx.getM_PriceList_Version();

			logger.info("Setting to context: PriceDate={} from M_PriceList_Version={}", priceListVersion.getValidFrom(), priceListVersion);
			pricingCtx.setPriceDate(TimeUtil.asLocalDate(priceListVersion.getValidFrom()));
		}
	}

	private void setPrecisionAndPriceScales(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final PricingResult result)
	{
		if (pricingCtx.getPriceListId() != null && result.getPrecision() == null)
		{
			final CurrencyPrecision precision = getPricePrecision(pricingCtx.getPriceListId());
			result.setPrecision(precision);
		}
		result.updatePriceScales();
	}

	private void convertResultToContextUOMIfNeeded(
			@NonNull final IPricingResult result,
			@NonNull final IPricingContext pricingCtx)
	{
		// We are asked to keep the prices in context's UOM, so do nothing
		if (!pricingCtx.isConvertPriceToContextUOM())
		{
			return;
		}

		if (pricingCtx.getUomId() != null
				&& !UomId.equals(pricingCtx.getUomId(), result.getPriceUomId()))
		{
			final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
			final I_C_UOM uomTo = uomsRepo.getById(pricingCtx.getUomId());
			final I_C_UOM uomFrom = uomsRepo.getById(result.getPriceUomId());

			final BigDecimal factor = Services.get(IUOMConversionBL.class).convertQty(
					result.getProductId(),
					BigDecimal.ONE,
					uomFrom,
					uomTo);

			result.setPriceLimit(factor.multiply(result.getPriceLimit()));
			result.setPriceList(factor.multiply(result.getPriceList()));
			result.setPriceStd(factor.multiply(result.getPriceStd()));
			result.setPriceUomId(pricingCtx.getUomId());
		}
	}

	private void setProductInfo(final IPricingContext pricingCtx, final IPricingResult result)
	{
		final ProductId productId = pricingCtx.getProductId();
		if (productId == null)
		{
			return;
		}

		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final ProductCategoryId productCategoryId = productDAO.retrieveProductCategoryByProductId(productId);
		result.setProductCategoryId(productCategoryId);

		//
		// Set Price_UOM_ID (06942)
		final I_M_PriceList_Version plv = pricingCtx.getM_PriceList_Version();
		if (plv != null)
		{
			final I_M_ProductPrice productPrice = ProductPrices.retrieveMainProductPriceOrNull(plv, productId);
			if (productPrice == null)
			{
				final UomId uomId = Services.get(IProductBL.class).getStockUOMId(productId);
				result.setPriceUomId(uomId);
			}
			else
			{
				result.setPriceUomId(UomId.ofRepoId(productPrice.getC_UOM_ID()));
			}
		}
		else
		{
			final UomId uomId = Services.get(IProductBL.class).getStockUOMId(productId);
			result.setPriceUomId(uomId);
		}
	}

	@Override
	public PricingResult createInitialResult(@NonNull final IPricingContext pricingCtx)
	{
		final PricingResult result = PricingResult.builder()
				.priceDate(pricingCtx.getPriceDate())
				//
				.pricingSystemId(pricingCtx.getPricingSystemId())
				.priceListId(pricingCtx.getPriceListId())
				.priceListVersionId(pricingCtx.getPriceListVersionId())
				.currencyId(pricingCtx.getCurrencyId())
				//
				.productId(pricingCtx.getProductId())
				//
				.disallowDiscount(pricingCtx.isDisallowDiscount())
				//
				.build();

		setProductInfo(pricingCtx, result);

		return result;
	}

	private AggregatedPricingRule createPricingRules()
	{
		final IPricingDAO pricingRulesRepo = Services.get(IPricingDAO.class);

		final ImmutableList<IPricingRule> rules = pricingRulesRepo.getPricingRules()
				.stream()
				.map(this::createPricingRuleNoFail)
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());

		return AggregatedPricingRule.of(rules);
	}

	private IPricingRule createPricingRuleNoFail(final PricingRuleDescriptor ruleDef)
	{
		try
		{
			return ruleDef.getPricingRuleClass().getReferencedClass().newInstance();
		}
		catch (final Exception ex)
		{
			logger.warn("Cannot load rule for {}", ruleDef, ex);
			return null;
		}
	}

	private CurrencyPrecision getPricePrecision(@NonNull final PriceListId priceListId)
	{
		return Services.get(IPriceListBL.class).getPricePrecision(priceListId);
	}

	@Override
	public void registerPriceLimitRule(@NonNull final IPriceLimitRule rule)
	{
		priceLimitRules.addEnforcer(rule);
	}

	@Override
	public PriceLimitRuleResult computePriceLimit(@NonNull final PriceLimitRuleContext context)
	{
		return priceLimitRules.compute(context);
	}

	@Override
	public Set<CountryId> getPriceLimitCountryIds()
	{
		return priceLimitRules.getPriceCountryIds();
	}
}
