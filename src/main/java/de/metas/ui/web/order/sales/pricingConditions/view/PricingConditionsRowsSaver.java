package de.metas.ui.web.order.sales.pricingConditions.view;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.conditions.service.PricingConditionsBreakChangeRequest;
import de.metas.pricing.conditions.service.PricingConditionsBreakChangeRequest.PricingConditionsBreakChangeRequestBuilder;
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
	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);

	private final PricingConditionsRow row;

	@Builder
	private PricingConditionsRowsSaver(final PricingConditionsRow row)
	{
		this.row = row;
	}

	public PricingConditionsBreak save()
	{
		if (!row.isEditable())
		{
			throw new AdempiereException("Saving not editable rows is not allowed")
					.setParameter("row", row);
		}

		final PricingConditionsId pricingConditionsId = row.getPricingConditionsId();
		if (pricingConditionsId == null)
		{
			throw new AdempiereException("Cannot save row because no discount schema was defined"); // TODO trl
		}

		final PricingConditionsBreakChangeRequestBuilder requestBuilder = PricingConditionsBreakChangeRequest.builder()
				.pricingConditionsId(pricingConditionsId)
				.pricingConditionsBreakId(row.getPricingConditionsBreakId())
				.matchCriteria(row.getBreakMatchCriteria())
				//
				.updateFromPricingConditionsBreakId(row.getCopiedFromPricingConditionsBreakId())
				//
				.price(row.getPrice())
				.discount(row.getDiscount())
				.paymentTermId(row.getPaymentTermId());

		return pricingConditionsRepo.changePricingConditionsBreak(requestBuilder.build());
	}
}
