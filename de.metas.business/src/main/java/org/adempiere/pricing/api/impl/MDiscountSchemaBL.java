
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
import java.util.List;

import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.CalculateDiscountRequest;
import org.adempiere.pricing.api.DiscountResult;
import org.adempiere.pricing.api.IMDiscountSchemaBL;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.pricing.api.SchemaBreakQuery;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicates;

import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import lombok.NonNull;

public class MDiscountSchemaBL implements IMDiscountSchemaBL
{

	@Override
	public DiscountResult calculateDiscount(final CalculateDiscountRequest request)
	{
		final CalculateDiscountCommand command = new CalculateDiscountCommand(request);
		return command.calculateDiscount();
	}

	/**
	 * Pick the first break that applies based on product, category and attribute value.
	 */
	@VisibleForTesting
	I_M_DiscountSchemaBreak pickApplyingBreak(
			final List<I_M_DiscountSchemaBreak> breaks,
			final int attributeValueId,
			final boolean isQtyBased,
			final int M_Product_ID,
			final int M_Product_Category_ID,
			final BigDecimal qty,
			final BigDecimal amt)
	{

		return breaks.stream()
				.filter(I_M_DiscountSchemaBreak::isActive)
				.filter(schemaBreak -> breakApplies(schemaBreak, isQtyBased ? qty : amt, M_Product_ID, M_Product_Category_ID, attributeValueId))
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
	public I_M_DiscountSchemaBreak pickApplyingBreak(final @NonNull SchemaBreakQuery query)
	{
		final IMDiscountSchemaDAO schemaRepo = Services.get(IMDiscountSchemaDAO.class);
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final I_M_DiscountSchema schema = schemaRepo.getById(query.getDiscountSchemaId());
		final List<I_M_DiscountSchemaBreak> breaks = schemaRepo.retrieveBreaks(schema);
		final boolean isQtyBased = schema.isQuantityBased();

		final List<I_M_AttributeInstance> attributeInstances = query.getAttributeInstances();
		final int productId = query.getProductId();
		final int productCategoryId = productDAO.retrieveProductCategoryByProductId(productId);

		if (hasNoValues(attributeInstances))
		{
			return pickApplyingBreak(
					breaks,
					-1,  // attributeValueId
					isQtyBased,
					productId,
					productCategoryId,
					query.getQty(),
					query.getAmt());
		}
		else
		{
			return attributeInstances.stream()
					.filter(instance -> !hasNoValue(instance))
					.map(instance -> pickApplyingBreak(
							breaks,
							instance.getM_AttributeValue_ID(),
							isQtyBased,
							productId,
							productCategoryId,
							query.getQty(),
							query.getAmt()))
					.filter(Predicates.notNull())
					.findFirst()
					.orElse(null);
		}
	}

	private boolean breakApplies(
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

		if (!br.isValid())
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
		return Services.get(IProductBL.class).isProductInCategory(product_ID, br.getM_Product_Category_ID());
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
		final int discountSchemaId = Services.get(IBPartnerBL.class).getDiscountSchemaId(partner, isSOTrx);
		if (discountSchemaId <= 0)
		{
			return null;
		}

		return Services.get(IMDiscountSchemaDAO.class).getById(discountSchemaId);
	}
}
