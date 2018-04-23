package de.metas.vertical.pharma.pricing;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.limit.IPriceLimitRestrictionsRepository;
import org.adempiere.pricing.limit.PriceLimitRestrictions;
import org.adempiere.pricing.limit.PriceLimitRuleContext;
import org.adempiere.pricing.limit.PriceLimitRuleResult;
import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.util.Env;

import de.metas.vertical.pharma.PharmaCustomerPermission;
import de.metas.vertical.pharma.PharmaCustomerPermissions;
import de.metas.vertical.pharma.model.I_C_BPartner;
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
			return PriceLimitRuleResult.notEligible("no PriceLimitRestrictions defined");
		}
		final IPricingContext pricingContext = context.getPricingContext();
		if (!isEligibleBPartner(pricingContext.getC_BPartner_ID()))
		{
			return PriceLimitRuleResult.notEligible("BPartner not eligible");
		}
		if (!isEligibleProduct(pricingContext.getM_Product_ID()))
		{
			return PriceLimitRuleResult.notEligible("Product not eligible");
		}

		//
		// Actual computation
		final PriceLimit priceLimit = computePriceLimitOrNull();
		if (priceLimit == null)
		{
			return PriceLimitRuleResult.notEligible("No PriceLimit found for product");
		}
		return PriceLimitRuleResult.priceLimit(priceLimit.getValueAsBigDecimal(), priceLimit.toFormulaString());
	}

	private boolean hasPriceLimitRestrictions()
	{
		final PriceLimitRestrictions priceLimitRestrictions = Services.get(IPriceLimitRestrictionsRepository.class).get();
		return priceLimitRestrictions != null;
	}

	private PriceLimitRestrictions getPriceLimitRestrictions()
	{
		final PriceLimitRestrictions priceLimitRestrictions = Services.get(IPriceLimitRestrictionsRepository.class).get();
		Check.assumeNotNull(priceLimitRestrictions, "priceLimitRestrictions shall not be null");
		return priceLimitRestrictions;
	}

	private static boolean isEligibleBPartner(final int bpartnerId)
	{
		if (bpartnerId <= 0)
		{
			return false;
		}

		final I_C_BPartner bpartner = loadOutOfTrx(bpartnerId, I_C_BPartner.class);
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

		if (!product.isPrescription())
		{
			return false;
		}

		return true;
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

		final I_C_PaymentTerm paymentTerm = loadOutOfTrx(paymentTermId, I_C_PaymentTerm.class);
		if (paymentTerm == null)
		{
			return BigDecimal.ZERO;
		}

		return paymentTerm.getDiscount();
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
