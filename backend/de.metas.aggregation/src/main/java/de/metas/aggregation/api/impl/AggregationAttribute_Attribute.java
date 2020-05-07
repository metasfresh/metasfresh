package de.metas.aggregation.api.impl;

/*
 * #%L
 * de.metas.aggregation
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Evaluatee;

import de.metas.aggregation.api.IAggregationAttribute;

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
