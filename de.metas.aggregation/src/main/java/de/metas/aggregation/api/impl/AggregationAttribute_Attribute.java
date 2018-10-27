package de.metas.aggregation.api.impl;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Evaluatee;

import de.metas.aggregation.api.IAggregationAttribute;
import de.metas.util.Check;

/**
 * Attribute implementation: evaluated as <code>@AttributeName@</code> which will be appended to aggregation key.
 * 
 * The actual evaluation of the attribute will be done by API later.
 * 
 * @author tsa
 *
 */
public class AggregationAttribute_Attribute implements IAggregationAttribute
{
	private final String attributeNameEval;

	public AggregationAttribute_Attribute(final String attributeName)
	{
		super();

		Check.assumeNotEmpty(attributeName, "attributeName not empty");
		attributeNameEval = "@" + attributeName.trim() + "@";
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public Object evaluate(final Evaluatee ctx)
	{
		return attributeNameEval;
	}
}
