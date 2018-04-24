package org.adempiere.pricing.api;

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
import java.util.Comparator;
import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;

public interface IMDiscountSchemaBL extends ISingletonService
{
	final public static Comparator<I_M_DiscountSchemaBreak> REVERSED_BREAKS_COMPARATOR = Comparator.<I_M_DiscountSchemaBreak, BigDecimal> comparing(I_M_DiscountSchemaBreak::getBreakValue)
			.reversed();

	/**
	 * Calculate Discount Percentage
	 *
	 */
	DiscountResult calculateDiscount(final CalculateDiscountRequest request);

	/**
	 * Criteria apply
	 *
	 * @param br
	 * @param value
	 * @param product_ID
	 * @param product_Category_ID
	 * @return true if criteria met
	 */
	boolean breakApplies(I_M_DiscountSchemaBreak br, BigDecimal value, int product_ID, int product_Category_ID);

	/**
	 * Re-sequence the lines and breaks of the given schema
	 * This means setting their sequences from 10 to 10
	 *
	 * @param schema
	 * @return number of things (lined + breaks) that were modified
	 */
	int reSeq(I_M_DiscountSchema schema);

	/**
	 * Search for the discount schema set in the partner or in the bp_group of the partner
	 *
	 * @param partner
	 * @param isSOTrx decide if the SO or PO discount schema will be returned
	 * @return the discount schema if found, null otherwise
	 */
	I_M_DiscountSchema getDiscountSchemaForPartner(I_C_BPartner partner, boolean isSOTrx);

	/**
	 * Criteria apply, based also on attribute value
	 *
	 * @param br
	 * @param value
	 * @param product_ID
	 * @param product_Category_ID
	 * @param attributeValue_ID
	 * @return
	 */
	boolean breakApplies(I_M_DiscountSchemaBreak br, BigDecimal value, int product_ID, int product_Category_ID, int attributeValue_ID);

	/**
	 * Pick the first break that applies based on product, category and attribute value
	 *
	 * @param breaks
	 * @param attributeValueID
	 * @param isQtyBased
	 * @param M_Product_ID
	 * @param M_Product_Category_ID
	 * @param qty
	 * @param amt
	 * @return
	 */
	I_M_DiscountSchemaBreak pickApplyingBreak(List<I_M_DiscountSchemaBreak> breaks, int attributeValueID, boolean isQtyBased, int M_Product_ID, int M_Product_Category_ID, BigDecimal qty,
			BigDecimal amt);

	/**
	 * Pick the first break that applies based on product, category and attribute instance
	 *
	 * @param breaks
	 * @param instances
	 * @param isQtyBased
	 * @param M_Product_ID
	 * @param M_Product_Category_ID
	 * @param qty
	 * @param amt
	 * @return
	 */
	I_M_DiscountSchemaBreak pickApplyingBreak(List<I_M_DiscountSchemaBreak> breaks, List<I_M_AttributeInstance> instances, boolean isQtyBased, int M_Product_ID, int M_Product_Category_ID,
			BigDecimal qty, BigDecimal amt);
}
