package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;

import de.metas.pricing.conditions.PriceOverride;
import de.metas.pricing.conditions.PriceOverrideType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreak.PricingConditionsBreakBuilder;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.CompletePriceChange;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.PartialPriceChange;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.PriceChange;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
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

@UtilityClass
class PricingConditionsRowReducers
{
	public static PricingConditionsRow copyAndChange(final PricingConditionsRowChangeRequest request, final PricingConditionsRow row)
	{
		row.assertEditable();

		boolean changed = false;

		final PricingConditionsBreak pricingConditionsBreakOld = row.getPricingConditionsBreak();
		final PricingConditionsBreak pricingConditionsBreak = applyTo(request, pricingConditionsBreakOld)
				.toTemporaryPricingConditionsBreakIfPriceRelevantFieldsChanged(pricingConditionsBreakOld);
		if (!Objects.equals(pricingConditionsBreak, pricingConditionsBreakOld))
		{
			changed = true;
		}

		//
		// Copied from ID
		PricingConditionsBreakId copiedFromPricingConditionsBreakId = row.getCopiedFromPricingConditionsBreakId();
		if (!Objects.equals(request.getSourcePricingConditionsBreakId(), copiedFromPricingConditionsBreakId))
		{
			copiedFromPricingConditionsBreakId = request.getSourcePricingConditionsBreakId();
			changed = true;
		}

		//
		if (!changed)
		{
			return row;
		}

		return row.toBuilder()
				.pricingConditionsBreak(pricingConditionsBreak)
				.copiedFromPricingConditionsBreakId(copiedFromPricingConditionsBreakId)
				.build();
	}

	private static PricingConditionsBreak applyTo(final PricingConditionsRowChangeRequest request, final PricingConditionsBreak rowPricingConditionsBreak)
	{
		final PricingConditionsBreak pricingConditionsBreakEffective = Util.coalesce(request.getPricingConditionsBreak(), rowPricingConditionsBreak);
		final PricingConditionsBreakBuilder builder = pricingConditionsBreakEffective.toBuilder();

		//
		// Discount%
		if (request.getDiscount() != null)
		{
			builder.discount(request.getDiscount());
			builder.hasChanges(true);
		}

		//
		// Payment Term
		if (request.getPaymentTermId() != null)
		{
			builder.paymentTermId(request.getPaymentTermId().orElse(null));
		}

		//
		// Price
		if (request.getPriceChange() != null)
		{
			final PriceOverride price = applyPriceChangeTo(request.getPriceChange(), rowPricingConditionsBreak.getPriceOverride());
			builder.priceOverride(price);
		}

		PricingConditionsBreak newPricingConditionsBreak = builder.build();
		if (!Objects.equals(newPricingConditionsBreak, pricingConditionsBreakEffective))
		{
			newPricingConditionsBreak = newPricingConditionsBreak.toBuilder().hasChanges(true).build();
		}

		return newPricingConditionsBreak;
	}

	private static PriceOverride applyPriceChangeTo(final PriceChange priceChange, final PriceOverride price)
	{
		if (priceChange == null)
		{
			// no change
			return price;
		}
		else if (priceChange instanceof CompletePriceChange)
		{
			return ((CompletePriceChange)priceChange).getPrice();
		}
		else if (priceChange instanceof PartialPriceChange)
		{
			return applyPartialPriceChangeTo((PartialPriceChange)priceChange, price);
		}
		else
		{
			throw new AdempiereException("Unknow price change request: " + priceChange);
		}

	}

	private static PriceOverride applyPartialPriceChangeTo(final PartialPriceChange changes, final PriceOverride price)
	{
		final PriceOverrideType priceType = changes.getPriceType() != null ? changes.getPriceType() : price.getType();
		if (priceType == PriceOverrideType.NONE)
		{
			return PriceOverride.none();
		}
		else if (priceType == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			final int requestBasePricingSystemId = changes.getBasePricingSystemId() != null ? changes.getBasePricingSystemId().orElse(-1) : -1;
			final int basePricingSystemId = Util.firstGreaterThanZero(requestBasePricingSystemId, price.getBasePricingSystemId(), IPriceListDAO.M_PricingSystem_ID_None);
			final BigDecimal basePriceAddAmt = changes.getBasePriceAddAmt() != null ? changes.getBasePriceAddAmt() : price.getBasePriceAddAmt();
			return PriceOverride.basePricingSystem(
					basePricingSystemId,
					basePriceAddAmt != null ? basePriceAddAmt : BigDecimal.ZERO);
		}
		else if (priceType == PriceOverrideType.FIXED_PRICE)
		{
			final BigDecimal fixedPrice = Util.coalesce(changes.getFixedPrice(), price.getFixedPrice(), BigDecimal.ZERO);
			return PriceOverride.fixedPrice(fixedPrice);
		}
		else
		{
			throw new AdempiereException("Unknow price type: " + priceType);
		}
	}

}
