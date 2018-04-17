/**
 *
 */
package org.adempiere.pricing.api.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pricing.api.CalculateDiscountRequest;
import org.adempiere.pricing.api.DiscountResult;
import org.adempiere.pricing.api.DiscountResult.DiscountResultBuilder;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IMDiscountSchemaBL;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.X_M_DiscountSchema;
import org.compiere.model.X_M_DiscountSchemaBreak;
import org.compiere.util.Util;

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

	final CalculateDiscountRequest request;

	public CalculateDiscountCommand(@NonNull final CalculateDiscountRequest request)
	{
		this.request = request;
	}

	public DiscountResult calculateDiscount()
	{
		final String discountType = request.getSchema().getDiscountType();

		if (X_M_DiscountSchema.DISCOUNTTYPE_FlatPercent.equals(discountType))
		{
			return computeFlatDiscount();
		}
		else if (X_M_DiscountSchema.DISCOUNTTYPE_Formula.equals(discountType)
				|| X_M_DiscountSchema.DISCOUNTTYPE_Pricelist.equals(discountType))
		{
			return DiscountResult.ZERO;
		}
		else if (X_M_DiscountSchema.DISCOUNTTYPE_Breaks.equals(discountType))
		{
			return computeBreaksDiscount();
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @DiscountType@: " + discountType);
		}
	}

	private DiscountResult computeFlatDiscount()
	{
		final I_M_DiscountSchema schema = request.getSchema();
		if (schema.isBPartnerFlatDiscount())
		{
			final BigDecimal bpFlatDiscountToUse = Util.coalesce(request.getBPartnerFlatDiscount(), BigDecimal.ZERO);
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

	private DiscountResult computeBreaksDiscount()
	{
		final I_M_DiscountSchemaBreak breakApplied = fetchDiscountSchemaBreak();
		if (breakApplied == null)
		{
			return DiscountResult.ZERO;
		}

		final DiscountResultBuilder result = DiscountResult.builder()
				.discountSchemaBreakId(breakApplied.getM_DiscountSchemaBreak_ID());

		if (breakApplied.isPriceOverride())
		{
			computePriceForDiscountSchemaBreak(result, breakApplied);
		}

		computeDefaultDiscountForDiscountSchemaBreak(result, breakApplied);

		return result.build();
	}

	private void computePriceForDiscountSchemaBreak(final DiscountResultBuilder result, final I_M_DiscountSchemaBreak discountSchemaBreak)
	{
		final String priceBase = discountSchemaBreak.getPriceBase();
		if (X_M_DiscountSchemaBreak.PRICEBASE_PricingSystem.equals(priceBase))
		{
			final IPricingResult productPrices = findPricesForSchemaBreak(discountSchemaBreak);
			final BigDecimal priceStd = productPrices.getPriceStd();
			final BigDecimal priceList = productPrices.getPriceList();
			final BigDecimal priceLimit = productPrices.getPriceLimit();

			final BigDecimal stdAddAmt = discountSchemaBreak.getStd_AddAmt();

			result.priceListOverride(priceList);
			result.priceLimitOverride(priceLimit);
			result.priceStdOverride(priceStd.add(stdAddAmt));
		}
		else if (X_M_DiscountSchemaBreak.PRICEBASE_Fixed.equals(priceBase))
		{
			result.priceStdOverride(discountSchemaBreak.getPriceStd());
		}
		else
		{
			throw new AdempiereException("PriceBase value unknown: " + priceBase);
		}
	}

	private IPricingResult findPricesForSchemaBreak(final I_M_DiscountSchemaBreak discountSchemaBreak)
	{
		final int basePricingSystemId = discountSchemaBreak.getBase_PricingSystem_ID();
		Check.assumeGreaterThanZero(basePricingSystemId, "basePricingSystemId");

		final IPricingContext pricingCtx = request.getPricingCtx();
		Check.assumeNotNull(pricingCtx, "pricingCtx shall not be null for {}", request);

		final IPricingContext basePricingSystemPricingCtx = createBasePricingSystemPricingCtx(pricingCtx, basePricingSystemId);
		final IPricingResult pricingResult = pricingBL.calculatePrice(basePricingSystemPricingCtx);

		return pricingResult;
	}

	private IPricingContext createBasePricingSystemPricingCtx(final IPricingContext pricingCtx, final int basePricingSystemId)
	{
		Check.assumeGreaterThanZero(basePricingSystemId, "basePricingSystemId");

		final IEditablePricingContext newPricingCtx = pricingCtx.copy();
		newPricingCtx.setM_PricingSystem_ID(basePricingSystemId);
		newPricingCtx.setM_PriceList_ID(-1); // will be recomputed
		newPricingCtx.setM_PriceList_Version_ID(-1); // will be recomputed
		newPricingCtx.setDisallowDiscount(true);

		return newPricingCtx;
	}

	private void computeDefaultDiscountForDiscountSchemaBreak(final DiscountResultBuilder result, final I_M_DiscountSchemaBreak breakApplied)
	{
		final BigDecimal discount;
		if (breakApplied.isBPartnerFlatDiscount())
		{
			discount = request.getBPartnerFlatDiscount();
		}
		else
		{
			discount = breakApplied.getBreakDiscount();
		}

		result.discount(discount);
		result.C_PaymentTerm_ID(breakApplied.getC_PaymentTerm_ID());
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
