package org.adempiere.pricing.api;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;

public interface IMDiscountSchemaBL extends ISingletonService
{
	/**
	 * Calculate Discount Percentage
	 *
	 */
	DiscountResult calculateDiscount(final CalculateDiscountRequest request);

	/**
	 * Re-sequence the lines and breaks of the given schema
	 * This means setting their sequences from 10 to 10
	 *
	 * @param schema
	 * @return number of things (lined + breaks) that were modified
	 */
	int reSeq(I_M_DiscountSchema schema);

	/**
	 * Pick the first break that applies based on product, category and attribute instance
	 * @return schema break or null
	 */
	I_M_DiscountSchemaBreak pickApplyingBreak(SchemaBreakQuery query);

}
