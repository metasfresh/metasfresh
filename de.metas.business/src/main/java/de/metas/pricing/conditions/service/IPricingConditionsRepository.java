package de.metas.pricing.conditions.service;

import java.util.Collection;

import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_M_DiscountSchemaBreak;

import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;

public interface IPricingConditionsRepository extends ISingletonService
{
	PricingConditions getPricingConditionsById(PricingConditionsId pricingConditionsId);

	default PricingConditions getPricingConditionsById(final int discountSchemaId)
	{
		return getPricingConditionsById(PricingConditionsId.ofDiscountSchemaId(discountSchemaId));
	}

	Collection<PricingConditions> getPricingConditionsByIds(Collection<PricingConditionsId> pricingConditionIds);

	PricingConditionsBreak changePricingConditionsBreak(PricingConditionsBreakChangeRequest request);

	/**
	 * Re-sequence the lines and breaks of the given schema.
	 * This means setting their sequences from 10 to 10.
	 *
	 * @return number of things (lined + breaks) that were modified
	 */
	int resequence(int discountSchemaId);

	void copyDiscountSchemaBreaks(PricingConditionsId pricingConditionsId, IQueryFilter<I_M_DiscountSchemaBreak> queryFilter);

	boolean selectionHasMultipleProductsOrNone(IQueryFilter<I_M_DiscountSchemaBreak> queryFilter);

	ProductId retrieveUniqueProductIdForSelectionOrNull(IQueryFilter<I_M_DiscountSchemaBreak> selectionFilter);

	void copyDiscountSchemaBreaksWithProductId(PricingConditionsId pricingConditionsId, IQueryFilter<I_M_DiscountSchemaBreak> queryFilter, ProductId productId, boolean allowCopyToSameSchema);

}
