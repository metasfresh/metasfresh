package de.metas.pricing.interceptor;

import java.math.BigDecimal;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import de.metas.i18n.BooleanWithReason;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.conditions.PriceSpecification;
import de.metas.pricing.conditions.PriceSpecificationType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakMatchCriteria;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.IPricingConditionsService;
import de.metas.pricing.conditions.service.PricingConditionsErrorCode;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.pricing.conditions.service.impl.PricingConditionsRepository;
import de.metas.pricing.limit.IPriceLimitRule;
import de.metas.pricing.limit.PriceLimitRuleContext;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Interceptor(I_M_DiscountSchemaBreak.class)
@Component
public class M_DiscountSchemaBreak
{
	private static final Logger logger = LogManager.getLogger(M_DiscountSchemaBreak.class);

	private static final String MSG_UnderLimitPriceWithExplanation = "UnderLimitPriceWithExplanation";

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_M_DiscountSchemaBreak schemaBreak)
	{
		// If M_Product_ID and M_Product_Category_ID is set, M_Product_ID has priority - teo_sarca [ 2012439 ]
		if (schemaBreak.getM_Product_ID() > 0 && schemaBreak.getM_Product_Category_ID() > 0)
		{
			schemaBreak.setM_Product_Category_ID(0);
		}

		//
		// Validate
		try
		{
			enforcePriceLimit(schemaBreak);

			schemaBreak.setIsValid(true);
			schemaBreak.setNotValidReason(null);
		}
		catch (AdempiereException ex)
		{
			// NOTE: catch only AdempiereExceptions. All others like NPE etc shall be propagated.
			logger.debug("Schema break become invalid", ex);
			schemaBreak.setIsValid(false);
			schemaBreak.setNotValidReason(AdempiereException.extractMessage(ex));
		}
	}

	private void enforcePriceLimit(@NonNull final I_M_DiscountSchemaBreak schemaBreak)
	{
		if (!schemaBreak.isActive())
		{
			return;
		}

		final int productId = schemaBreak.getM_Product_ID();
		if (productId <= 0)
		{
			return;
		}

		final PricingConditionsBreak pricingConditionsBreak = PricingConditionsRepository.toPricingConditionsBreak(schemaBreak);
		final PriceSpecification priceOverride = pricingConditionsBreak.getPriceSpecification();
		final PriceSpecificationType priceOverrideType = priceOverride.getType();
		final Set<CountryId> countryIds;
		if (priceOverrideType == PriceSpecificationType.NONE)
		{
			// nothing to validate
			return;
		}
		else if (priceOverrideType == PriceSpecificationType.BASE_PRICING_SYSTEM)
		{
			countryIds = Services.get(IPriceListDAO.class).retrieveCountryIdsByPricingSystem(priceOverride.getBasePricingSystemId());

		}
		else if (priceOverrideType == PriceSpecificationType.FIXED_PRICE)
		{
			countryIds = Services.get(IPricingBL.class).getPriceLimitCountryIds();
		}
		else
		{
			throw new AdempiereException("Unknown " + PriceSpecificationType.class + ": " + priceOverrideType);
		}

		// If there were no countryIds, there is nothing to enforce
		if (countryIds.isEmpty())
		{
			return;
		}

		countryIds.stream()
				.map(countryId -> PriceLimitEnforceContext.builder()
						.pricingConditionsBreak(pricingConditionsBreak)
						.soTrx(SOTrx.SALES)
						.countryId(countryId)
						.build())
				.forEach(this::enforcePriceLimit);
	}

	private void enforcePriceLimit(@NonNull final PriceLimitEnforceContext context)
	{
		final CalculatePricingConditionsRequest request = createCalculateDiscountRequest(context);

		final PricingConditionsResult pricingConditionsResult = calculatePricingConditions(request);
		if (pricingConditionsResult == null)
		{
			return;
		}
		final BigDecimal price = pricingConditionsResult.getPriceStdOverride();
		if (price == null)
		{
			return;
		}

		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final PriceLimitRuleResult priceLimitResult = pricingBL.computePriceLimit(PriceLimitRuleContext.builder()
				.priceActual(BigDecimal.ZERO) // N/A
				.priceLimit(BigDecimal.ZERO) // N/A
				.pricingContext(request.getPricingCtx())
				.build());
		if (!priceLimitResult.isApplicable())
		{
			return;
		}

		final BooleanWithReason priceBelowLimit = priceLimitResult.checkApplicableAndBelowPriceLimit(price);
		if (priceBelowLimit.isTrue())
		{
			final Object[] msgArgs = new Object[] { price, priceLimitResult.getPriceLimit(), priceLimitResult.getPriceLimitExplanation() };
			throw new AdempiereException(MSG_UnderLimitPriceWithExplanation, msgArgs)
					.markAsUserValidationError()
					.setParameter("context", context)
					.setParameter("pricingCtx", request.getPricingCtx());
		}
	}

	private PricingConditionsResult calculatePricingConditions(final CalculatePricingConditionsRequest request)
	{
		try
		{
			return Services.get(IPricingConditionsService.class)
					.calculatePricingConditions(request)
					.orElse(null);
		}
		catch (final AdempiereException ex)
		{
			if (PricingConditionsErrorCode.SurchargeCurrencyNotMatchingPriceListCurrency.matches(ex))
			{
				logger.info("Cannot calculate pricing conditions. Skipping. \n request: {}", request, ex);
				return null;
			}
			else
			{
				throw ex;
			}
		}
	}

	private static CalculatePricingConditionsRequest createCalculateDiscountRequest(@NonNull final PriceLimitEnforceContext context)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final IProductBL productBL = Services.get(IProductBL.class);

		final PricingConditionsBreak pricingConditionsBreak = context.getPricingConditionsBreak();
		final PricingConditionsBreakMatchCriteria matchCriteria = pricingConditionsBreak.getMatchCriteria();

		final ProductId productId = matchCriteria.getProductId();
		final BigDecimal qty = matchCriteria.getBreakValue();

		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setConvertPriceToContextUOM(true);
		pricingCtx.setProductId(productId);
		pricingCtx.setUomId(productBL.getStockUOMId(productId));
		pricingCtx.setSOTrx(context.getSoTrx());
		pricingCtx.setQty(qty);

		pricingCtx.setProperty(IPriceLimitRule.OPTION_SkipCheckingBPartnerEligible);
		pricingCtx.setCountryId(context.getCountryId());

		final CalculatePricingConditionsRequest request = CalculatePricingConditionsRequest.builder()
				.pricingConditionsId(pricingConditionsBreak.getPricingConditionsIdOrNull())
				.forcePricingConditionsBreak(pricingConditionsBreak)
				// .qty(pricingCtx.getQty())
				// .price(BigDecimal.ZERO) // N/A
				// .productId(pricingCtx.getM_Product_ID())
				.pricingCtx(pricingCtx)
				.build();
		return request;
	}

	@lombok.Value
	@lombok.Builder
	private static class PriceLimitEnforceContext
	{
		@NonNull
		final PricingConditionsBreak pricingConditionsBreak;
		@NonNull
		final SOTrx soTrx;
		@NonNull
		private CountryId countryId;
	}

}
