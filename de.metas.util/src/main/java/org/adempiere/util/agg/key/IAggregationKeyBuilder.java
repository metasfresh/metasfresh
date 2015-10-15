package org.adempiere.util.agg.key;

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


import java.util.List;

/**
 * Implementors are responsible for building an aggregation key for given items. Known examples are invoice candidates, receipt schedules and shipment schedules.
 *
 * Often, implementations of this interface are used to build both HeaderAggregationKeys and LineAggregationKeys.
 *
 * @author tsa
 *
 */
public interface IAggregationKeyBuilder<T>
{
	String buildKey(T item);

	/**
	 * @return list of columns that <b>might</b> be relevant in order to build the aggregation key
	 */
	List<String> getDependsOnColumnNames();

	/**
	 * Build aggregation keys for the given items and compare them.
	 * 
	 * @param item1
	 * @param item2
	 * @return
	 */
	boolean isSame(T item1, T item2);
}
