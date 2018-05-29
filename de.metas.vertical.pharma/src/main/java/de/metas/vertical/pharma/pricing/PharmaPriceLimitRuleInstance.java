package de.metas.vertical.pharma.pricing;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;

import de.metas.payment.api.IPaymentTermRepository;
import de.metas.payment.api.PaymentTermId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.limit.IPriceLimitRestrictionsRepository;
import de.metas.pricing.limit.IPriceLimitRule;
import de.metas.pricing.limit.PriceLimitRestrictions;
import de.metas.pricing.limit.PriceLimitRuleContext;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.pricing.service.IPricingBL;
import de.metas.vertical.pharma.PharmaCustomerPermission;
import de.metas.vertical.pharma.PharmaCustomerPermissions;
import de.metas.vertical.pharma.model.I_M_Product;

/*
 * #%L
 * metasfresh-pharma
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

class PharmaPriceLimitRuleInstance
{
	private final transient IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final transient IPaymentTermRepository paymentTermsRepo = Services.get(IPaymentTermRepository.class);
	private final transient IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

	private final PriceLimitRuleContext context;

	PharmaPriceLimitRuleInstance(final PriceLimitRuleContext context)
	{
		this.context = context;
	}

	public PriceLimitRuleResult execute()
	{
		//
		// Preconditions
		if (!hasPriceLimitRestrictions())
		{
			return PriceLimitRuleResult.notApplicable("no PriceLimitRestrictions defined");
		}

		final IPricingContext pricingContext = context.getPricingContext();
		if (!isEligibleBPartner(pricingContext))
		{
			return PriceLimitRuleResult.notApplicable("BPartner not eligible");
		}
		if (!isEligibleProduct(pricingContext.getM_Product_ID()))
		{
			return PriceLimitRuleResult.notApplicable("Product not eligible");
		}

		//
		// Actual computation
		final PriceLimit priceLimit = computePriceLimitOrNull();
		if (priceLimit == null)
		{
			return PriceLimitRuleResult.notApplicable("No PriceLimit found for product");
		}
		return PriceLimitRuleResult.priceLimit(priceLimit.getValueAsBigDecimal(), priceLimit.toFormulaString());
	}

	private boolean hasPriceLimitRestrictions()
	{
		return Services.get(IPriceLimitRestrictionsRepository.class)
				.get()
				.isPresent();
	}

	private PriceLimitRestrictions getPriceLimitRestrictions()
	{
		return Services.get(IPriceLimitRestrictionsRepository.class)
				.get()
				.orElseThrow(() -> new AdempiereException("No price limit restrictions defined"));
	}

	private boolean isEligibleBPartner(final IPricingContext pricingContext)
	{
		if (pricingContext.isPropertySet(IPriceLimitRule.OPTION_SkipCheckingBPartnerEligible))
		{
			return true;
		}

		return isEligibleBPartner(pricingContext.getBPartnerId());
	}

	private boolean isEligibleBPartner(final BPartnerId bpartnerId)
	{
		if (bpartnerId == null)
		{
			return false;
		}

		final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);
		if (bpartner == null || !bpartner.isActive())
		{
			return false;
		}

		return PharmaCustomerPermissions.of(bpartner).hasOnlyPermission(PharmaCustomerPermission.PHARMACIE);
	}

	private static boolean isEligibleProduct(final int productId)
	{
		if (productId <= 0)
		{
			return false;
		}

		final I_M_Product product = loadOutOfTrx(productId, I_M_Product.class);
		if (product == null || !product.isActive())
		{
			return false;
		}

		if (!I_M_Product.FAM_ZUP_FAM.equals(product.getFAM_ZUB()))
		{
			return false;
		}

		return product.isPrescription();
	}

	private PriceLimit computePriceLimitOrNull()
	{
		final IPricingResult basePriceResult = computeBasePriceOrNull();
		if (basePriceResult == null)
		{
			return null;
		}

		final PriceLimitRestrictions priceLimitRestrictions = getPriceLimitRestrictions();

		return PriceLimit.builder()
				.basePrice(basePriceResult.getPriceStd())
				.priceAddAmt(priceLimitRestrictions.getPriceAddAmt())
				.discountPercentToSubtract(priceLimitRestrictions.getDiscountPercent())
				.paymentTermDiscountPercentToAdd(getPaymentTermDiscountPercent())
				.precision(basePriceResult.getPrecision())
				.build();
	}

	private IPricingResult computeBasePriceOrNull()
	{
		final PriceLimitRestrictions priceLimitRestrictions = getPriceLimitRestrictions();
		final IEditablePricingContext basePricingContext = context.getPricingContext().copy();
		basePricingContext.setM_PricingSystem_ID(priceLimitRestrictions.getBasePricingSystemId());
		basePricingContext.setM_PriceList_ID(-1); // will be recomputed
		basePricingContext.setM_PriceList_Version_ID(-1); // will be recomputed
		basePricingContext.setDisallowDiscount(true);

		final IPricingResult basePriceResult = pricingBL.calculatePrice(basePricingContext);
		if (!basePriceResult.isCalculated())
		{
			return null;
		}

		return basePriceResult;
	}

	private BigDecimal getPaymentTermDiscountPercent()
	{
		final int paymentTermId = context.getPaymentTermId();
		if (paymentTermId <= 0)
		{
			return BigDecimal.ZERO;
		}

		return paymentTermsRepo
				.getPaymentTermDiscount(PaymentTermId.ofRepoId(paymentTermId))
				.getValueAsBigDecimal();
	}

	@lombok.Value
	private static class PriceLimit
	{
		private BigDecimal valueAsBigDecimal;

		private BigDecimal basePrice;
		private BigDecimal priceAddAmt;
		private BigDecimal discountPercentToSubtract;
		private BigDecimal paymentTermDiscountPercentToAdd;
		private int precision;

		@lombok.Builder
		private PriceLimit(
				@lombok.NonNull final BigDecimal basePrice,
				@lombok.NonNull final BigDecimal priceAddAmt,
				@lombok.NonNull final BigDecimal discountPercentToSubtract,
				@lombok.NonNull final BigDecimal paymentTermDiscountPercentToAdd,
				@lombok.NonNull final Integer precision)
		{
			Check.assume(precision >= 0, "precision >= 0");

			this.basePrice = NumberUtils.stripTrailingDecimalZeros(basePrice);
			this.priceAddAmt = NumberUtils.stripTrailingDecimalZeros(priceAddAmt);
			this.discountPercentToSubtract = NumberUtils.stripTrailingDecimalZeros(discountPercentToSubtract);
			this.paymentTermDiscountPercentToAdd = NumberUtils.stripTrailingDecimalZeros(paymentTermDiscountPercentToAdd);
			this.precision = precision;

			//
			// Formula:
			// PriceLimit = (basePrice + priceAddAmt) - discountPercentToSubtract% + paymentTermDiscountPercentToAdd%
			BigDecimal value = basePrice.add(priceAddAmt);
			final BigDecimal multiplier = Env.ONEHUNDRED.subtract(discountPercentToSubtract).add(paymentTermDiscountPercentToAdd)
					.divide(Env.ONEHUNDRED, 12, RoundingMode.HALF_UP);
			if (multiplier.compareTo(Env.ONEHUNDRED) != 0)
			{
				value = value.multiply(multiplier).setScale(precision, RoundingMode.HALF_UP);
			}

			valueAsBigDecimal = value;
		}

		public String toFormulaString()
		{
			return "(" + basePrice + " + " + priceAddAmt + ") - " + discountPercentToSubtract + "% + " + paymentTermDiscountPercentToAdd + "% (precision: " + precision + ")";
		}
	}
}
