package de.metas.ui.web.order.sales.pricingConditions.view;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pricing.conditions.PricingConditionsBreak.PriceOverrideType;
import org.adempiere.pricing.conditions.service.IMDiscountSchemaDAO;
import org.adempiere.pricing.conditions.service.PricingConditionsBreakChangeRequest;
import org.adempiere.pricing.conditions.service.PricingConditionsBreakChangeRequest.PricingConditionsBreakChangeRequestBuilder;
import org.adempiere.util.Services;

import lombok.Builder;

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

public class PricingConditionsRowsSaver
{
	// services
	private final IMDiscountSchemaDAO pricingConditionsRepo = Services.get(IMDiscountSchemaDAO.class);

	private final PricingConditionsRow row;

	@Builder
	private PricingConditionsRowsSaver(final PricingConditionsRow row)
	{
		this.row = row;
	}

	public int save()
	{
		if (!row.isEditable())
		{
			throw new AdempiereException("Saving not editable rows is not allowed")
					.setParameter("row", row);
		}

		final int discountSchemaId = row.getDiscountSchemaId();
		if (discountSchemaId <= 0)
		{
			throw new AdempiereException("Cannot save row because no discount schema was defined"); // TODO trl
		}

		final PricingConditionsBreakChangeRequestBuilder requestBuilder = PricingConditionsBreakChangeRequest.builder()
				.discountSchemaId(discountSchemaId)
				.discountSchemaBreakId(row.getDiscountSchemaBreakId())
				//
				.updateFromDiscountSchemaBreakId(row.getCopiedFromDiscountSchemaBreakId())
				//
				.discount(row.getDiscount())
				.paymentTermId(row.getPaymentTermId());

		updatePrice(requestBuilder, row.getPrice());

		return pricingConditionsRepo.changePricingConditionsBreak(requestBuilder.build());
	}

	private void updatePrice(final PricingConditionsBreakChangeRequestBuilder requestBuilder, final Price price)
	{
		final PriceType priceType = price.getPriceType();
		if (priceType == PriceType.NONE)
		{
			requestBuilder.priceOverride(PriceOverrideType.NONE);
		}
		else if (priceType == PriceType.BASE_PRICING_SYSTEM)
		{
			requestBuilder.priceOverride(PriceOverrideType.BASE_PRICING_SYSTEM);
			requestBuilder.basePricingSystemId(price.getPricingSystemId());
			requestBuilder.basePriceAddAmt(price.getBasePriceAddAmt());
		}
		else if (priceType == PriceType.FIXED_PRICED)
		{
			requestBuilder.priceOverride(PriceOverrideType.FIXED_PRICED);
			requestBuilder.fixedPrice(price.getPriceValue());
		}
		else
		{
			throw new AdempiereException("Unknown priceType: " + priceType);
		}
	}
}
