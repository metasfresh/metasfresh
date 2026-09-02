package de.metas.pricing;

import org.adempiere.mm.attributes.AttributeListValue;

import javax.annotation.Nullable;

/**
 * Contains information about a particular pricing relevant attribute and its value. <br>
 * A list of instances can be obtained from {@link IPricingResult#getPricingAttributes()}, if there were attributes relevant for the price calculation.
 */
public interface IPricingAttribute
{
	@Nullable
	AttributeListValue getAttributeValue();
}
