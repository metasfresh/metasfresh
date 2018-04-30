package de.metas.pricing.rules;

import org.adempiere.pricing.model.I_C_PricingRule;

import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;

/**
 * Pluggable Pricing Rule interface.<br>
 * 
 * To be used by the system, an implementation of this interface needs to have its own {@link I_C_PricingRule} record.
 * 
 * @author tsa
 * 
 */
public interface IPricingRule
{
	/**
	 * 
	 * @param pricingCtx
	 * @param result
	 * @return true if this pricing rule shall be applied
	 */
	boolean applies(final IPricingContext pricingCtx, final IPricingResult result);

	/**
	 * Calculate the pricing values based on pricingCtx and saves the result in <code>result</code>
	 * 
	 * @param pricingCtx
	 * @param result
	 */
	void calculate(IPricingContext pricingCtx, IPricingResult result);
}
