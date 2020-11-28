package org.adempiere.util.lang;

/*
 * #%L
 * de.metas.util
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


/**
 * Standard Aggregator pattern
 * 
 * @author tsa
 *
 * @param <ResultType> result type (of the aggregated result)
 * @param <InputType> input type (of the collected items)
 */
public interface IAggregator<ResultType, InputType> extends IReference<ResultType>
{
	/**
	 * Add <code>value</code> to current aggregation.
	 * 
	 * After this invocation, the current aggregated value will be adjusted.
	 * 
	 * @param value
	 */
	void add(InputType value);

	/**
	 * @return current aggregated value (up to date)
	 */
	@Override
	ResultType getValue();
}
