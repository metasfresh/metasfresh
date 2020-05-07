package de.metas.aggregation.api;

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


import org.compiere.util.Evaluatee;

/**
 * Built aggregation key (immutable)
 * 
 * @author tsa
 *
 */
public interface IAggregationKey
{
	/** @return aggregation key as string */
	String getAggregationKeyString();

	/** @return C_Aggregation_ID or -1 */
	int getC_Aggregation_ID();

	/** Same as {@link #getAggregationKeyString()} */
	@Override
	public String toString();

	@Override
	public boolean equals(final Object obj);

	@Override
	public int hashCode();

	/**
	 * Parse any existing variables which are still included in aggregation key.
	 * 
	 * If a variable is not found in context it will be preserved.
	 * 
	 * @param ctx evaluatee
	 * @return a new key with found variables replaced
	 */
	IAggregationKey parse(Evaluatee ctx);
}
