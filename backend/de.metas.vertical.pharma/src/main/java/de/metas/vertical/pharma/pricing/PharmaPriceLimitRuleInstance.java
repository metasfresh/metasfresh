package de.metas.vertical.pharma.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.limit.IPriceLimitRestrictionsRepository;
import de.metas.pricing.limit.IPriceLimitRule;
import de.metas.pricing.limit.PriceLimitRestrictions;
import de.metas.pricing.limit.PriceLimitRuleContext;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
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
		final BooleanWithReason bpartnerEligible = checkEligibleBPartner(pricingContext);
		if (bpartnerEligible.isFalse())
		{
			return PriceLimitRuleResult.notApplicable(bpartnerEligible.getReason());
		}

		final BooleanWithReason productEligible = checkEligibleProduct(pricingContext.getProductId());
		if (productEligible.isFalse())
		{
			return PriceLimitRuleResult.notApplicable(productEligible.getReason());
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

	private BooleanWithReason checkEligibleBPartner(final IPricingContext pricingContext)
	{
		if (pricingContext.isPropertySet(IPriceLimitRule.OPTION_SkipCheckingBPartnerEligible))
		{
			return BooleanWithReason.TRUE;
		}

		return checkEligibleBPartner(pricingContext.getBPartnerId());
	}

	private BooleanWithReason checkEligibleBPartner(final BPartnerId bpartnerId)
	{
		if (bpartnerId == null)
		{
			return BooleanWithReason.falseBecause("no bpartner");
		}

		final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);
		if (bpartner == null || !bpartner.isActive())
		{
			return BooleanWithReason.falseBecause("bpartner not found or not active");
		}

		final PharmaCustomerPermissions pharmaCustomerPermissions = PharmaCustomerPermissions.of(bpartner);
		if (!pharmaCustomerPermissions.hasOnlyPermission(PharmaCustomerPermission.PHARMACIE))
		{
			return BooleanWithReason.falseBecause(TranslatableStrings.builder()
					.append("BPartner shall have only PHARMACIE permission but it has: ")
					.append(pharmaCustomerPermissions.toTrlString())
					.build());
		}

		return BooleanWithReason.TRUE; // eligible
	}

	private static BooleanWithReason checkEligibleProduct(final ProductId productId)
	{
		if (productId == null)
		{
			return BooleanWithReason.falseBecause("no product");
		}

		final I_M_Product product = Services.get(IProductDAO.class).getById(productId, I_M_Product.class);
		if (product == null || !product.isActive())
		{
			return BooleanWithReason.falseBecause("product missing or not active");
		}

		boolean isFAM = I_M_Product.FAM_ZUP_FAM.equals(product.getFAM_ZUB());
		if (!isFAM)
		{
			return BooleanWithReason.falseBecause("product's FAM/ZUP attribute is not FAM");
		}

		if (!product.isPrescription())
		{
			return BooleanWithReason.falseBecause("product is not a prescription product");
		}

		return BooleanWithReason.TRUE;
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
		basePricingContext.setPricingSystemId(priceLimitRestrictions.getBasePricingSystemId());
		basePricingContext.setPriceListId(null); // will be recomputed
		basePricingContext.setPriceListVersionId(null); // will be recomputed
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
		final PaymentTermId paymentTermId = context.getPaymentTermId();
		if (paymentTermId == null)
		{
			return BigDecimal.ZERO;
		}

		return paymentTermsRepo
				.getPaymentTermDiscount(paymentTermId)
				.toBigDecimal();
	}

	@lombok.Value
	private static class PriceLimit
	{
		private BigDecimal valueAsBigDecimal;

		private BigDecimal basePrice;
		private BigDecimal priceAddAmt;
		private BigDecimal discountPercentToSubtract;
		private BigDecimal paymentTermDiscountPercentToAdd;
		private CurrencyPrecision precision;

		@lombok.Builder
		private PriceLimit(
				@lombok.NonNull final BigDecimal basePrice,
				@lombok.NonNull final BigDecimal priceAddAmt,
				@lombok.NonNull final BigDecimal discountPercentToSubtract,
				@lombok.NonNull final BigDecimal paymentTermDiscountPercentToAdd,
				@lombok.NonNull final CurrencyPrecision precision)
		{
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
				value = precision.round(value.multiply(multiplier));
			}

			valueAsBigDecimal = value;
		}

		public ITranslatableString toFormulaString()
		{
			return TranslatableStrings.builder()
					.append("(").append(basePrice, DisplayType.CostPrice).append(" + ").append(priceAddAmt, DisplayType.CostPrice).append(")")
					.append(" - ").append(discountPercentToSubtract, DisplayType.Number).append("%")
					.append(" + ").append(paymentTermDiscountPercentToAdd, DisplayType.Number).append("%")
					.append(" (precision: ").append(precision.toInt()).append(")")
					.build();
		}
	}
}
