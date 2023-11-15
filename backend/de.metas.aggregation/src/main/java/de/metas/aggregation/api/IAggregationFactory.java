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

import de.metas.util.ISingletonService;

import java.util.Properties;

/**
 * Factory service used to create {@link IAggregationKeyBuilder}s.
 * 
 * @author tsa
 *
 */
public interface IAggregationFactory extends ISingletonService
{
	/**
	 * @return aggregation key builder; never returns <code>null</code>
	 */
	<ModelType> IAggregationKeyBuilder<ModelType> getAggregationKeyBuilder(Properties ctx, Class<ModelType> modelClass, int aggregationId);

	/**
	 * @return default aggregation key builder; never returns <code>null</code>
	 */
	<ModelType> IAggregationKeyBuilder<ModelType> getDefaultAggregationKeyBuilder(Properties ctx, Class<ModelType> modelClass, Boolean isSOTrx, String aggregationUsageLevel);

	/**
	 * Sets the default aggregation key builder to be used if nothing else was found.
	 * <p> 
	 * Basically this is the LAST place that will be checked.
	 */
	<ModelType> void setDefaultAggregationKeyBuilder(Class<? extends ModelType> modelClass, String aggregationUsageLevel, IAggregationKeyBuilder<ModelType> aggregationKeyBuilder);

}
