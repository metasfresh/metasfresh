/**
 *
 */
package org.adempiere.pricing.api.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pricing.api.CalculateDiscountRequest;
import org.adempiere.pricing.api.DiscountResult;
import org.adempiere.pricing.api.DiscountResult.DiscountResultBuilder;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IMDiscountSchemaBL;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.pricing.api.IPriceListBL;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.X_M_DiscountSchema;
import org.compiere.model.X_M_DiscountSchemaBreak;

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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */ class CalculateDiscountCommand
{
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IPriceListBL priceListBL = Services.get(IPriceListBL.class);

	final CalculateDiscountRequest request;

	public CalculateDiscountCommand(@NonNull final CalculateDiscountRequest request)
	{
		this.request = request;
	}

	public DiscountResult calculateDiscount()
	{
		final BigDecimal bpFlatDiscountToUse = request.getBPartnerFlatDiscount() == null ? BigDecimal.ZERO : request.getBPartnerFlatDiscount();
		final String discountType = request.getSchema().getDiscountType();

		if (X_M_DiscountSchema.DISCOUNTTYPE_FlatPercent.equals(discountType))
		{
			return computeFlatDiscount(request.getSchema(), bpFlatDiscountToUse);
		}
		else if (X_M_DiscountSchema.DISCOUNTTYPE_Formula.equals(discountType)
				|| X_M_DiscountSchema.DISCOUNTTYPE_Pricelist.equals(discountType))
		{
			return DiscountResult.builder()
					.discount(BigDecimal.ZERO)
					.build();
		}

		final I_M_DiscountSchemaBreak breakApplied = fetchDiscountSchemaBreak();

		DiscountResultBuilder priceForDiscountSchemaBreak = null;

		if (breakApplied != null)
		{
			if (breakApplied.isPriceOverride())
			{
				priceForDiscountSchemaBreak = computePriceForDiscountSchemaBreak(breakApplied);
			}

			return computeDefaultDiscountForDiscoutSchemaBreak(priceForDiscountSchemaBreak, breakApplied);

		}

		return DiscountResult.builder()
				.discount(BigDecimal.ZERO)
				.build();

	}

	private DiscountResultBuilder computePriceForDiscountSchemaBreak(final I_M_DiscountSchemaBreak breakApplied)
	{
		final String priceBase = breakApplied.getPriceBase();

		if (X_M_DiscountSchemaBreak.PRICEBASE_PricingSystem.equals(priceBase))
		{

			final IPricingResult productPrices = findPricesForSchemaBreak(breakApplied);

			final BigDecimal priceStd = productPrices.getPriceStd();
			final BigDecimal priceList = productPrices.getPriceList();
			final BigDecimal priceLimit = productPrices.getPriceLimit();

			final BigDecimal stdAddAmt = breakApplied.getStd_AddAmt();

			return DiscountResult.builder()
					.priceListOverride(priceList)
					.priceLimitOverride(priceLimit)
					.priceStdOverride(priceStd.add(stdAddAmt));

		}
		else if (X_M_DiscountSchemaBreak.PRICEBASE_Fixed.equals(priceBase))
		{
			final BigDecimal discountSchemaBreakStdPrice = breakApplied.getPriceStd();

			return DiscountResult.builder()
					.priceStdOverride(discountSchemaBreakStdPrice);
		}
		else
		{
			throw new AdempiereException("PriceBase value unknown: " + priceBase);
		}
	}

	private IPricingResult findPricesForSchemaBreak(final I_M_DiscountSchemaBreak breakApplied)
	{
		final I_M_PricingSystem basePricingSystem = breakApplied.getBase_PricingSystem();

		Check.assumeNotNull(basePricingSystem, "BasePricingSystem shall not be not null for the discount schema break {}", breakApplied);

		final IPricingContext pricingCtx = request.getPricingCtx();

		final IPricingContext basePricingSystemPricingCtx = createBasePricingSystemPricingCtx(pricingCtx, basePricingSystem);

		final IPricingResult pricingResult = pricingBL.calculatePrice(basePricingSystemPricingCtx);

		return pricingResult;
	}

	private IPricingContext createBasePricingSystemPricingCtx(final IPricingContext pricingCtx, final I_M_PricingSystem basePricingSystem)
	{
		final I_C_Country country = pricingCtx.getC_Country();
		final boolean isSOTrx = pricingCtx.isSOTrx();
		final Timestamp priceDate = pricingCtx.getPriceDate();

		final I_M_PriceList_Version priceListVersion = priceListBL.getCurrentPriceListVersionOrNull(
				basePricingSystem,
				country,
				priceDate,
				isSOTrx,
				null);

		if (priceListVersion == null)
		{
			throw new AdempiereException("Price list version not found");
		}

		final IEditablePricingContext newPricingCtx = pricingCtx.copy();
		newPricingCtx.setM_PricingSystem_ID(basePricingSystem.getM_PricingSystem_ID());
		newPricingCtx.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());
		newPricingCtx.setM_PriceList_ID(priceListVersion.getM_PriceList_ID());
		newPricingCtx.setDisallowDiscount(true);

		return newPricingCtx;
	}

	private DiscountResult computeDefaultDiscountForDiscoutSchemaBreak(final DiscountResultBuilder priceForDiscountSchemaBreak, final I_M_DiscountSchemaBreak breakApplied)
	{
		final DiscountResultBuilder discountForDiscountSchemaBreak = priceForDiscountSchemaBreak == null ? DiscountResult.builder() : priceForDiscountSchemaBreak;

		final BigDecimal discount;
		if (breakApplied.isBPartnerFlatDiscount())
		{
			discount = request.getBPartnerFlatDiscount();
		}
		else
		{
			discount = breakApplied.getBreakDiscount();
		}
		
		return discountForDiscountSchemaBreak
				.discount(discount)
				.C_PaymentTerm_ID(breakApplied.getC_PaymentTerm_ID())
				.build();
	}

	private DiscountResult computeFlatDiscount(@NonNull final I_M_DiscountSchema schema, @NonNull final BigDecimal bpFlatDiscountToUse)
	{
		if (schema.isBPartnerFlatDiscount())
		{
			return DiscountResult.builder()
					.discount(bpFlatDiscountToUse)
					.build();
		}
		else
		{
			return DiscountResult.builder()
					.discount(schema.getFlatDiscount())
					.build();
		}
	}

	private I_M_DiscountSchemaBreak fetchDiscountSchemaBreak()
	{
		// Price Breaks
		final List<I_M_DiscountSchemaBreak> breaks = Services.get(IMDiscountSchemaDAO.class).retrieveBreaks(request.getSchema());
		final BigDecimal amt = request.getPrice().multiply(request.getQty());
		final boolean isQtyBased = request.getSchema().isQuantityBased();

		final IMDiscountSchemaBL discountSchemaBL = Services.get(IMDiscountSchemaBL.class);
		I_M_DiscountSchemaBreak breakApplied = null;
		if (hasNoValues())
		{
			breakApplied = discountSchemaBL.pickApplyingBreak(
					breaks,
					-1,  // attributeValueID
					isQtyBased,
					request.getM_Product_ID(),
					request.getM_Product_Category_ID(),
					request.getQty(),
					amt);
		}
		else
		{
			final Optional<I_M_DiscountSchemaBreak> optionalBreak = request.getInstances().stream()
					.filter(instance -> !hasNoValue(instance))
					.map(instance -> discountSchemaBL.pickApplyingBreak(
							breaks,
							instance.getM_AttributeValue_ID(),
							isQtyBased,
							request.getM_Product_ID(),
							request.getM_Product_Category_ID(),
							request.getQty(),
							amt))
					.filter(schemaBreak -> schemaBreak != null)
					.findFirst();
			breakApplied = optionalBreak.orElse(null);
		}

		return breakApplied;
	}

	/**
	 * Check if the instance has no value set
	 *
	 * @param instance
	 * @return true if the instance has no value set, false if it has one
	 */
	private boolean hasNoValue(final I_M_AttributeInstance instance)
	{
		return instance.getM_AttributeValue_ID() <= 0;
	}

	/**
	 * Check if the instances are empty ( have "" value)
	 *
	 * @param instances
	 * @return true if all the instances are empty, false if at least one is not
	 */
	private boolean hasNoValues()
	{
		if (request.getInstances().isEmpty())
		{
			return true;
		}

		final boolean anyAttributeInstanceMatches = request.getInstances().stream()
				.anyMatch(instance -> !hasNoValue(instance));

		return !anyAttributeInstanceMatches;
	}
}
