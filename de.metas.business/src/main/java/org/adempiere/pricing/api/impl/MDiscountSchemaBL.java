package org.adempiere.pricing.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IMDiscountSchemaBL;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.MProductCategory;
import org.compiere.model.X_M_DiscountSchema;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.NonNull;

public class MDiscountSchemaBL implements IMDiscountSchemaBL
{

	private static final Logger logger = LogManager.getLogger(MDiscountSchemaBL.class);

	/**
	 * Calculate Discount Percentage
	 *
	 * @param Qty quantity
	 * @param Price price
	 * @param M_Product_ID product
	 * @param M_Product_Category_ID category
	 * @param BPartnerFlatDiscount flat discount
	 * @return discount or zero
	 */
	@Override
	public DiscountRequest calculateDiscount(final I_M_DiscountSchema schema,
			final BigDecimal qty,
			final BigDecimal Price,
			final int M_Product_ID,
			final int M_Product_Category_ID,
			final BigDecimal bPartnerFlatDiscount)
	{
		return calculateDiscount(
				schema,
				qty,
				Price,
				M_Product_ID,
				M_Product_Category_ID,
				Collections.<I_M_AttributeInstance> emptyList(), // attribute instances
				bPartnerFlatDiscount);
	}	// calculateDiscount

	@Override
	public DiscountRequest calculateDiscount(@NonNull final I_M_DiscountSchema schema,
			final BigDecimal qty,
			final BigDecimal Price,
			final int M_Product_ID,
			final int M_Product_Category_ID,
			final List<I_M_AttributeInstance> instances,
			final BigDecimal bPartnerFlatDiscount)
	{
		final BigDecimal bpFlatDiscountToUse = bPartnerFlatDiscount == null ? BigDecimal.ZERO : bPartnerFlatDiscount;
		final String discountType = schema.getDiscountType();


		if (X_M_DiscountSchema.DISCOUNTTYPE_FlatPercent.equals(discountType))
		{
			return computeFlatDiscount(schema, bpFlatDiscountToUse);
		}
		else if (X_M_DiscountSchema.DISCOUNTTYPE_Formula.equals(discountType)
				|| X_M_DiscountSchema.DISCOUNTTYPE_Pricelist.equals(discountType))
		{
			logger.warn("Not supported (yet) DiscountType={}", discountType);

			return DiscountRequest.builder()
					.discount(BigDecimal.ZERO)
					.build();
		}

		final I_M_DiscountSchemaBreak breakApplied = fetchDiscountSchemaBreak(schema, qty, Price, M_Product_ID, M_Product_Category_ID, instances, bPartnerFlatDiscount);

		if (breakApplied != null)
		{

			// Line applies
			BigDecimal discount = null;
			if (breakApplied.isBPartnerFlatDiscount())
			{
				discount = bPartnerFlatDiscount;
			}
			else
			{
				discount = breakApplied.getBreakDiscount();
			}
			logger.debug("Discount=>{}", discount);

			return DiscountRequest.builder()
					.discount(discount)
					.C_PaymentTerm_ID(breakApplied.getC_PaymentTerm_ID())
					.build();
			// for all breaks
		}

		return DiscountRequest.builder()
				.discount(BigDecimal.ZERO)
				.build();
	}

	private I_M_DiscountSchemaBreak fetchDiscountSchemaBreak(@NonNull final I_M_DiscountSchema schema,
			final BigDecimal qty,
			final BigDecimal Price,
			final int M_Product_ID,
			final int M_Product_Category_ID,
			final List<I_M_AttributeInstance> instances,
			final BigDecimal bPartnerFlatDiscount)
	{
		// Price Breaks
		final List<I_M_DiscountSchemaBreak> breaks = Services.get(IMDiscountSchemaDAO.class).retrieveBreaks(schema);
		BigDecimal amt = Price.multiply(qty);
		final boolean isQtyBased = schema.isQuantityBased();

		I_M_DiscountSchemaBreak breakApplied = null;
		if (hasNoValues(instances))
		{
			breakApplied = pickApplyingBreak(
					breaks,
					-1,  // attributeValueID
					isQtyBased,
					M_Product_ID,
					M_Product_Category_ID,
					qty,
					amt);
		}
		else
		{
			final Optional<I_M_DiscountSchemaBreak> optionalBreak = instances.stream()
					.filter(instance -> !hasNoValue(instance))
					.map(instance -> pickApplyingBreak(
							breaks,
							instance.getM_AttributeValue_ID(),
							isQtyBased,
							M_Product_ID,
							M_Product_Category_ID,
							qty,
							amt))
					.filter(schemaBreak -> schemaBreak != null)
					.findFirst();
			breakApplied = optionalBreak.orElse(null);
		}

		return breakApplied;
	}

	private DiscountRequest computeFlatDiscount(@NonNull final I_M_DiscountSchema schema, @NonNull final BigDecimal bpFlatDiscountToUse)
	{
		if (schema.isBPartnerFlatDiscount())
		{
			return DiscountRequest.builder()
					.discount(bpFlatDiscountToUse)
					.build();
		}

		return DiscountRequest.builder()
				.discount(schema.getFlatDiscount())
				.build();
	}

	@Override
	public I_M_DiscountSchemaBreak pickApplyingBreak(
			final List<I_M_DiscountSchemaBreak> breaks,
			final int attributeValueID,
			final boolean isQtyBased,
			final int M_Product_ID,
			final int M_Product_Category_ID,
			final BigDecimal qty,
			final BigDecimal amt)
	{

		return breaks.stream()
				.filter(I_M_DiscountSchemaBreak::isActive)
				.filter(schemaBreak -> breakApplies(schemaBreak, isQtyBased ? qty : amt, M_Product_ID, M_Product_Category_ID, attributeValueID))
				.sorted(REVERSED_BREAKS_COMPARATOR)
				.findFirst().orElse(null);
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
	private boolean hasNoValues(final Collection<I_M_AttributeInstance> instances)
	{
		if (instances == null || instances.isEmpty())
		{
			return true;
		}

		final boolean anyAttributeInstanceMatches = instances.stream()
				.anyMatch(instance -> !hasNoValue(instance));

		return !anyAttributeInstanceMatches;
	}

	@Override
	public I_M_DiscountSchemaBreak pickApplyingBreak(
			final List<I_M_DiscountSchemaBreak> breaks,
			final List<I_M_AttributeInstance> instances,
			final boolean isQtyBased,
			final int M_Product_ID,
			final int M_Product_Category_ID,
			final BigDecimal qty,
			final BigDecimal amt)
	{
		I_M_DiscountSchemaBreak breakApplied = null;

		if (hasNoValues(instances))
		{
			breakApplied = pickApplyingBreak(
					breaks,
					-1,  // attributeValueID
					isQtyBased,
					M_Product_ID,
					M_Product_Category_ID,
					qty,
					amt);
		}
		else
		{

			for (final I_M_AttributeInstance instance : instances)
			{
				if (hasNoValue(instance))
				{
					continue;
				}

				final int attributeValueID = instance.getM_AttributeValue_ID();

				breakApplied = pickApplyingBreak(
						breaks,
						attributeValueID,
						isQtyBased,
						M_Product_ID,
						M_Product_Category_ID,
						qty,
						amt);

				if (breakApplied != null)
				{
					break;
				}
			}
		}

		return breakApplied;
	}

	/**
	 * Calculate Discounted Price
	 *
	 * @param qty quantity
	 * @param price price
	 * @param product_ID product
	 * @param product_Category_ID category
	 * @param bPartnerFlatDiscount flat discount
	 * @return discount or zero
	 */
	@Override
	public BigDecimal calculatePrice(
			final I_M_DiscountSchema schema,
			final BigDecimal qty,
			final BigDecimal price,
			final int product_ID,
			final int product_Category_ID,
			final BigDecimal bPartnerFlatDiscount)
	{
		return calculatePrice(
				schema,
				qty,
				price,
				product_ID,
				product_Category_ID,
				Collections.<I_M_AttributeInstance> emptyList(),// instances
				bPartnerFlatDiscount);

	}

	@Override
	public BigDecimal calculatePrice(
			final I_M_DiscountSchema schema,
			final BigDecimal qty,
			final BigDecimal price,
			final int product_ID,
			final int product_Category_ID,
			final List<I_M_AttributeInstance> instances,
			final BigDecimal bPartnerFlatDiscount)
	{
		if (price == null || price.signum() == 0)
		{
			return price;
		}

		final DiscountRequest discountRequest = calculateDiscount(
				schema,
				qty,
				price,
				product_ID,
				product_Category_ID,
				instances,
				bPartnerFlatDiscount);

		final BigDecimal discount = discountRequest.getDiscount();
		if (discount == null || discount.signum() == 0)
		{
			return price;
		}

		return applyDiscount(price, discount);
	}

	private BigDecimal applyDiscount(@NonNull final BigDecimal price, @NonNull final BigDecimal discount)
	{
		BigDecimal multiplier = (Env.ONEHUNDRED).subtract(discount);
		multiplier = multiplier.divide(Env.ONEHUNDRED, 6, BigDecimal.ROUND_HALF_UP);
		return price.multiply(multiplier);
	}

	/**
	 * Criteria apply
	 *
	 * @param Value amt or qty
	 * @param M_Product_ID product
	 * @param M_Product_Category_ID category
	 * @return true if criteria met
	 */
	@Override
	public boolean breakApplies(
			final I_M_DiscountSchemaBreak br,
			final BigDecimal value,
			final int product_ID,
			final int product_Category_ID)
	{
		return breakApplies(
				br,
				value,
				product_ID,
				product_Category_ID,
				-1 // attributeValue_ID
		);
	}	// breakApplies

	@Override
	public boolean breakApplies(
			final I_M_DiscountSchemaBreak br,
			final BigDecimal value,
			final int product_ID,
			final int product_Category_ID,
			final int attributeValue_ID)
	{
		if (!br.isActive())
		{
			return false;
		}

		// below break value
		if (value.compareTo(br.getBreakValue()) < 0)
		{
			return false;
		}

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak breakInstance = InterfaceWrapperHelper.create(br, de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		// break attribute value id
		final int breakAttributeValueID = breakInstance.getM_AttributeValue_ID();

		// If the break has an attribute that is not the same as the one given as parameter, break does not apply

		if (breakAttributeValueID > 0 && breakAttributeValueID != attributeValue_ID)
		{
			return false;
		}

		// Everything shall work as before: the break either doesn't have an attribute set or the attributes are equal

		// No Product / Category
		if (br.getM_Product_ID() <= 0
				&& br.getM_Product_Category_ID() <= 0)
		{
			return true;
		}

		// Product
		if (br.getM_Product_ID() == product_ID)
		{
			return true;
		}

		// Category
		if (product_Category_ID > 0)
		{
			return br.getM_Product_Category_ID() == product_Category_ID;
		}

		// Look up Category of Product
		return MProductCategory.isCategory(br.getM_Product_Category_ID(), product_ID);
	}

	/**
	 * Renumber
	 *
	 * @return lines updated
	 */
	@Override
	public int reSeq(final I_M_DiscountSchema schema)
	{
		final IMDiscountSchemaDAO discountSchemaDAO = Services.get(IMDiscountSchemaDAO.class);

		int count = 0;
		// Lines

		final List<I_M_DiscountSchemaLine> lines = discountSchemaDAO.retrieveLines(schema);

		// initialize i = 0.
		int i = 0;
		for (final I_M_DiscountSchemaLine currentLine : lines)
		{
			int currentSeq = (i + 1) * 10;
			if (currentSeq != currentLine.getSeqNo())
			{
				currentLine.setSeqNo(currentSeq);
				InterfaceWrapperHelper.save(currentLine);
				count++;
			}
			i++;
		}

		// Breaks

		final List<I_M_DiscountSchemaBreak> breaks = discountSchemaDAO.retrieveBreaks(schema);

		// re-initialize i
		i = 0;
		for (final I_M_DiscountSchemaBreak br : breaks)
		{
			int currentSeq = (i + 1) * 10;
			if (currentSeq != br.getSeqNo())
			{
				br.setSeqNo(currentSeq);
				InterfaceWrapperHelper.save(br);
				count++;
			}
			i++;
		}

		return count;
	}	// reSeq

	@Override
	public I_M_DiscountSchema getDiscountSchemaForPartner(final I_C_BPartner partner, final boolean isSOTrx)
	{
		final I_C_BP_Group group = partner.getC_BP_Group();

		I_M_DiscountSchema schema;

		// SO TRX
		if (isSOTrx)
		{
			schema = partner.getM_DiscountSchema();

			if (schema != null)
			{
				return schema;
			}

			return group.getM_DiscountSchema();
		}

		// PO TRX

		schema = partner.getPO_DiscountSchema();

		if (schema != null)
		{
			return schema;
		}

		return group.getPO_DiscountSchema();
	}

}
