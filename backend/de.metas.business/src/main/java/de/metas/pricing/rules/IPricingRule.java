package de.metas.pricing.rules;

import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import org.adempiere.pricing.model.I_C_PricingRule;

/**
 * Pluggable Pricing Rule interface.<br>
 * <p>
 * To be used by the system, an implementation of this interface needs to have its own {@link I_C_PricingRule} record.
 * <p>
 * NOTEs:
 * <li>the system is creating a new instance each time a price calculation is required,
 * so it's safe to have private fields.</li>
 * <li>if {@link #applies(IPricingContext, IPricingResult)}  returns true, then the same instance's {@link #calculate(IPricingContext, IPricingResult)} method is called, so it can resuse results that were computed by the former.</li>
 */
public interface IPricingRule
{
	/**
	 * @return true if this pricing rule shall be applied
	 */
	boolean applies(IPricingContext pricingCtx, IPricingResult result);

	/**
	 * Calculate the pricing values based on pricingCtx and saves the result in <code>result</code>
	 */
	void calculate(IPricingContext pricingCtx, IPricingResult result);
}
