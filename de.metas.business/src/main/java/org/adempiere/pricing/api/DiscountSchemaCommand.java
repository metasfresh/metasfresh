/**
 *
 */
package org.adempiere.pricing.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.X_M_DiscountSchema;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Setter
@Getter
@Immutable
public class DiscountSchemaCommand
{
	@NonNull
	private final I_M_DiscountSchema schema;
	private final BigDecimal qty;
	private final BigDecimal Price;
	private final int M_Product_ID;
	private final int M_Product_Category_ID;
	private final BigDecimal bPartnerFlatDiscount;
	private final List<I_M_AttributeInstance> instances;

	private BigDecimal discount = BigDecimal.ZERO;
	private int C_PaymentTerm_ID =-1;

	@Builder
	private DiscountSchemaCommand(
			@NonNull final I_M_DiscountSchema schema,
			final BigDecimal qty,
			final BigDecimal Price,
			final int M_Product_ID,
			final int M_Product_Category_ID,
			final BigDecimal bPartnerFlatDiscount,
			final List<I_M_AttributeInstance> instances)
	{
		this.schema = schema;
		this.qty = qty;
		this.Price = Price;
		this.M_Product_ID = M_Product_ID;
		this.M_Product_Category_ID = M_Product_Category_ID;
		this.bPartnerFlatDiscount = bPartnerFlatDiscount;
		this.instances = instances != null ? ImmutableList.copyOf(instances) : ImmutableList.of();
	}


	public void calculateDiscount()
	{
		final BigDecimal bpFlatDiscountToUse = getBPartnerFlatDiscount() == null ? BigDecimal.ZERO : getBPartnerFlatDiscount();
		final String discountType = getSchema().getDiscountType();


		if (X_M_DiscountSchema.DISCOUNTTYPE_FlatPercent.equals(discountType))
		{
			computeFlatDiscount(getSchema(), bpFlatDiscountToUse);
		}
		else if (X_M_DiscountSchema.DISCOUNTTYPE_Formula.equals(discountType)
				|| X_M_DiscountSchema.DISCOUNTTYPE_Pricelist.equals(discountType))
		{
			setDiscount(BigDecimal.ZERO);
		}
		else
		{
			final I_M_DiscountSchemaBreak breakApplied = fetchDiscountSchemaBreak();
			if (breakApplied != null)
			{
				if (breakApplied.isBPartnerFlatDiscount())
				{
					setDiscount(bPartnerFlatDiscount);
				}
				else
				{
					setDiscount(breakApplied.getBreakDiscount());
				}
				setC_PaymentTerm_ID(breakApplied.getC_PaymentTerm_ID());
			}
		}

	}

	private void computeFlatDiscount(@NonNull final I_M_DiscountSchema schema, @NonNull final BigDecimal bpFlatDiscountToUse)
	{
		if (schema.isBPartnerFlatDiscount())
		{
			setDiscount(bpFlatDiscountToUse);
		}
		else
		{
			setDiscount(schema.getFlatDiscount());
		}
	}

	private I_M_DiscountSchemaBreak fetchDiscountSchemaBreak()
	{
		// Price Breaks
		final List<I_M_DiscountSchemaBreak> breaks = Services.get(IMDiscountSchemaDAO.class).retrieveBreaks(schema);
		final BigDecimal amt = getPrice().multiply(getQty());
		final boolean isQtyBased = schema.isQuantityBased();

		final IMDiscountSchemaBL discountSchemaBL = Services.get(IMDiscountSchemaBL.class);
		I_M_DiscountSchemaBreak breakApplied = null;
		if (hasNoValues())
		{
			breakApplied = discountSchemaBL.pickApplyingBreak(
					breaks,
					-1,  // attributeValueID
					isQtyBased,
					getM_Product_ID(),
					getM_Product_Category_ID(),
					getQty(),
					amt);
		}
		else
		{
			final Optional<I_M_DiscountSchemaBreak> optionalBreak = getInstances().stream()
					.filter(instance -> !hasNoValue(instance))
					.map(instance -> discountSchemaBL.pickApplyingBreak(
							breaks,
							instance.getM_AttributeValue_ID(),
							isQtyBased,
							getM_Product_ID(),
							getM_Product_Category_ID(),
							getQty(),
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
		if (getInstances() == null || getInstances().isEmpty())
		{
			return true;
		}

		final boolean anyAttributeInstanceMatches = getInstances().stream()
				.anyMatch(instance -> !hasNoValue(instance));

		return !anyAttributeInstanceMatches;
	}
}
