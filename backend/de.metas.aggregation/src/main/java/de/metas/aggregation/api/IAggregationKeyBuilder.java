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


import java.util.List;

/**
 * Aggregation key builder
 * 
 * @author tsa
 *
 * @param <ModelType> model type on which is aggregation key builder applies
 */
public interface IAggregationKeyBuilder<ModelType> extends org.adempiere.util.agg.key.IAggregationKeyBuilder<ModelType>
{
	/**
	 * Build aggregation key string
	 * <p> 
	 * NOTE: this method is introduced to be able to extend {@link org.adempiere.util.agg.key.IAggregationKeyBuilder}. Please consider using {@link #buildAggregationKey(Object)}.
	 * 
	 * @return aggregation key string
	 */
	@Override
	String buildKey(ModelType model);

	AggregationKey buildAggregationKey(ModelType model);

	/**
	 * Build aggregation keys for the given items and compare them.
	 * 
	 * @return true if their aggregation keys are equal
	 * @throws UnsupportedOperationException in case this method is not supported
	 */
	@Override
	boolean isSame(ModelType model1, ModelType model2);

	/**
	 * @return table name on which this aggregation key builder applies
	 */
	String getTableName();

	/**
	 * @return list of columns that <b>might</b> be relevant in order to build the aggregation key
	 */
	@Override
	List<String> getDependsOnColumnNames();
}
