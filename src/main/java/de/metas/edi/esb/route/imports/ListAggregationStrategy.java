/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.edi.esb.route.imports;

import de.metas.edi.esb.processor.strategy.aggregation.AggregationHelper;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;

public class ListAggregationStrategy implements AggregationStrategy
{
	@Override
	public Exchange aggregate(final Exchange oldExchange, final Exchange newExchange)
	{
		final List<Object> aggregationResult = new ArrayList<>();
		// aggregate old body
		if (oldExchange != null) // if it's not the first iteration
		{
			final Object oldBody = oldExchange.getIn().getBody();
			AggregationHelper.aggregateElement(aggregationResult, oldBody);
		}
		// aggregate new body
		final Object newBody = newExchange.getIn().getBody();
		AggregationHelper.aggregateElement(aggregationResult, newBody);

		newExchange.getIn().setBody(aggregationResult);
		return newExchange;
	}
}
