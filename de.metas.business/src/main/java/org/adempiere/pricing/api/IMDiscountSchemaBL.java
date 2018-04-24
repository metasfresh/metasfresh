package org.adempiere.pricing.api;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
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
	 * Search for the discount schema set in the partner or in the bp_group of the partner
	 *
	 * @param partner
	 * @param isSOTrx decide if the SO or PO discount schema will be returned
	 * @return the discount schema if found, null otherwise
	 */
	I_M_DiscountSchema getDiscountSchemaForPartner(I_C_BPartner partner, boolean isSOTrx);

	/**
	 * Pick the first break that applies based on product, category and attribute instance
	 * @return schema break or null
	 */
	I_M_DiscountSchemaBreak pickApplyingBreak(SchemaBreakQuery query);

}
