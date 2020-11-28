package de.metas.aggregation.api;

import org.compiere.util.Evaluatee;

import de.metas.util.Check;
import lombok.ToString;

/**
 * Attribute implementation: evaluated as <code>@AttributeName@</code> which will be appended to aggregation key.
 * 
 * The actual evaluation of the attribute will be done by API later.
 */
@ToString
public final class AggregationAttribute
{
	private final String attributeNameEval;

	public AggregationAttribute(final String attributeName)
	{
		Check.assumeNotEmpty(attributeName, "attributeName not empty");
		attributeNameEval = "@" + attributeName.trim() + "@";
	}

	public Object evaluate(final Evaluatee ctx)
	{
		return attributeNameEval;
	}
}
