
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
import java.util.Comparator;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.CalculateDiscountRequest;
import org.adempiere.pricing.api.DiscountResult;
import org.adempiere.pricing.api.IMDiscountSchemaBL;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.pricing.api.SchemaBreakQuery;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicates;

import de.metas.product.IProductDAO;
import lombok.NonNull;

public class MDiscountSchemaBL implements IMDiscountSchemaBL
{
	private static final Comparator<I_M_DiscountSchemaBreak> REVERSED_BREAKS_COMPARATOR = Comparator.<I_M_DiscountSchemaBreak, BigDecimal> comparing(I_M_DiscountSchemaBreak::getBreakValue)
			.reversed();

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
	static I_M_DiscountSchemaBreak pickApplyingBreak(
			final List<I_M_DiscountSchemaBreak> schemaBreaks,
			final int attributeValueId,
			final boolean isQtyBased,
			final int productId,
			final int productCategoryId,
			final BigDecimal qty,
			final BigDecimal amt)
	{
		final BigDecimal breakValue = isQtyBased ? qty : amt;

		return schemaBreaks.stream()
				.filter(schemaBreak -> schemaBreakMatches(schemaBreak, breakValue, productId, productCategoryId, attributeValueId))
				.sorted(REVERSED_BREAKS_COMPARATOR)
				.findFirst()
				.orElse(null);
	}

	private static boolean hasAttributeValue(final I_M_AttributeInstance instance)
	{
		return instance.getM_AttributeValue_ID() > 0;
	}

	/**
	 * Check if the instances are empty ( have "" value)
	 *
	 * @param instances
	 * @return true if all the instances are empty, false if at least one is not
	 */
	private static boolean hasNoAttributeValues(final Collection<I_M_AttributeInstance> instances)
	{
		if (instances == null || instances.isEmpty())
		{
			return true;
		}

		return instances.stream().noneMatch(instance -> hasAttributeValue(instance));
	}

	@Override
	public I_M_DiscountSchemaBreak pickApplyingBreak(final @NonNull SchemaBreakQuery query)
	{
		final IMDiscountSchemaDAO schemaRepo = Services.get(IMDiscountSchemaDAO.class);
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final I_M_DiscountSchema schema = schemaRepo.getById(query.getDiscountSchemaId());
		final List<I_M_DiscountSchemaBreak> schemaBreak = schemaRepo.retrieveBreaks(schema);
		final boolean isQtyBased = schema.isQuantityBased();

		final List<I_M_AttributeInstance> attributeInstances = query.getAttributeInstances();
		final int productId = query.getProductId();
		final int productCategoryId = productDAO.retrieveProductCategoryByProductId(productId);

		if (hasNoAttributeValues(attributeInstances))
		{
			return pickApplyingBreak(
					schemaBreak,
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
					.filter(attributeInstance -> hasAttributeValue(attributeInstance))
					.map(instance -> pickApplyingBreak(
							schemaBreak,
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

	private static boolean schemaBreakMatches(
			final I_M_DiscountSchemaBreak schemaBreak,
			final BigDecimal value,
			final int productId,
			final int productCategoryId,
			final int attributeValueId)
	{
		if (!schemaBreak.isActive())
		{
			return false;
		}

		if (!schemaBreak.isValid())
		{
			return false;
		}

		// below break value
		if (value.compareTo(schemaBreak.getBreakValue()) < 0)
		{
			return false;
		}

		//
		// Product / Category
		if (!schemaBreakMatchesProduct(schemaBreak, productId, productCategoryId))
		{
			return false;
		}

		// If the break has an attribute that is not the same as the one given as parameter, break does not apply
		final int breakAttributeValueId = schemaBreak.getM_AttributeValue_ID();
		if (breakAttributeValueId > 0 && breakAttributeValueId != attributeValueId)
		{
			return false;
		}

		return true;
	}

	private static boolean schemaBreakMatchesProduct(
			final I_M_DiscountSchemaBreak schemaBreak,
			final int productId,
			final int productCategoryId)
	{
		final int breakProductId = schemaBreak.getM_Product_ID();
		if (breakProductId > 0)
		{
			return breakProductId == productId;
		}

		final int breakProductCategoryId = schemaBreak.getM_Product_Category_ID();
		if (breakProductCategoryId > 0)
		{
			return breakProductCategoryId == productCategoryId;
		}

		return true;
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
}
