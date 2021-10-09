/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.edi.esb.commons.processor.strategy.aggregation;

import org.apache.camel.RuntimeCamelException;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AggregationHelper
{
	private static final transient Logger logger = Logger.getLogger(AggregationHelper.class.getName());

	private AggregationHelper()
	{
		super();
	}

	/**
	 * @see {@link #aggregateElement(Collection, Object, boolean, Set)}, under the assumption that isNullableResultElement=true && validElementTypes=null
	 */
	public static void aggregateElement(final Collection<Object> aggregationResult, final Object element)
	{
		aggregateElement(aggregationResult, element, true, null);
	}

	/**
	 * Aggregate or throw {@link RuntimeCamelException} in unfavorable cases. Favorable cases are:
	 * <ul>
	 * <li>element is a collection (assume that previous aggregated elements were already checked)</li>
	 * <li>element is null and can be null (isNullableElement=true) - in this case, element will NOT be aggregated</li>
	 * <li>element is not null, and it can be any supported type (validElementType=null)</li>
	 * <li>element is not null, and it matches the supported types</li>
	 * </ul>
	 */
	public static void aggregateElement(final Collection<Object> aggregationResult, final Object element, final boolean isNullableResultElement, final Set<Class<?>> validElementTypes)
	{
		if (element instanceof Collection<?>)
		{
			aggregationResult.addAll((Collection<?>)element); // assume that previous aggregated elements were already checked
		}
		// if element not null and there's either no validation for supported type, or it's included in the supported types
		else if (element != null && (validElementTypes == null || validElementTypes.contains(element.getClass())))
		{
			aggregationResult.add(element);
		}
		// if it's null-able, do not add it
		else if (element == null)
		{
			if (isNullableResultElement)
			{
				AggregationHelper.logger.log(Level.WARNING, "Skipping aggregation for null element..."); // TODO handle this case separately (add it or not if null), depending on our needs
				return;
			}
			throw new RuntimeCamelException("Aggregation failed because element to be aggregated was null. Previously aggregated elements are: " + aggregationResult);
		}
		else
		{
			throw new RuntimeCamelException("Invalid content type detected(class=" + element.getClass() + ") when aggregating element: " + element);
		}
	}
}
