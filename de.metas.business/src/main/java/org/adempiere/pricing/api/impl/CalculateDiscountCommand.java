/**
 *
 */
package org.adempiere.pricing.api.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.adempiere.pricing.api.CalculateDiscountRequest;
import org.adempiere.pricing.api.DiscountResult;
import org.adempiere.pricing.api.IMDiscountSchemaBL;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.X_M_DiscountSchema;

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

	final CalculateDiscountRequest request;

	public CalculateDiscountCommand(final CalculateDiscountRequest request)
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
		if (breakApplied != null)
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
			return DiscountResult.builder()
					.discount(discount)
					.C_PaymentTerm_ID(breakApplied.getC_PaymentTerm_ID())
					.build();
		}

		return DiscountResult.builder()
				.discount(BigDecimal.ZERO)
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
		if (request.getInstances() == null || request.getInstances().isEmpty())
		{
			return true;
		}

		final boolean anyAttributeInstanceMatches = request.getInstances().stream()
				.anyMatch(instance -> !hasNoValue(instance));

		return !anyAttributeInstanceMatches;
	}
}
