package org.adempiere.pricing.spi.rules;

import org.adempiere.pricing.spi.IPricingRule;

/**
 * Adapter for {@link IPricingRule} which allows developer to implement only the mandatory methods.
 * 
 * It is highly recommended to extend this class instead of extending the {@link IPricingRule} interface.
 * 
 * @author tsa
 */
public abstract class PricingRuleAdapter implements IPricingRule
{
}
