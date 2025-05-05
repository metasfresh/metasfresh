/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.util.agg.key;

import de.metas.util.ISingletonService;

import java.util.List;

/**
 * Registry used for making aggregation keys (tables, columns, values)
 *
 * @author al
 */
public interface IAggregationKeyRegistry extends ISingletonService
{
	/**
	 * Register given column names for that registration key (i.e tableName)
	 */
	void registerDependsOnColumnnames(String registrationKey, String... columnNames);

	/**
	 * @return registered column name dependencies
	 */
	List<String> getDependsOnColumnNames(String registrationKey);

	/**
	 * Register aggregation key value handler (usually on module or package level)
	 */
	void registerAggregationKeyValueHandler(String registrationKey, IAggregationKeyValueHandler<?> aggregationKeyValueHandler);

	/**
	 * Remove all handlers for the given <code>registrationKey</code>. Need this method to set a defined stage for testing. If there isn'T a handler for the given key, do nothing.
	 */
	void clearHandlers(String registrationKey);

	/**
	 * @return values from all registered aggregation key value handlers of the registrationKey <b>of the given model</b>
	 */
	<T> List<Object> getValuesForModel(String registrationKey, T model);
}
