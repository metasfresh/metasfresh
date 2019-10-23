package org.adempiere.ad.dao;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Properties;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.util.lang.IAggregator;

/**
 * Query Aggregation Builder.
 * 
 * Instances are created from methods like {@link IQueryBuilder#aggregateOnColumn(org.adempiere.model.ModelColumn)}.
 * 
 * @author tsa
 *
 * @param <SourceModelType> source model type
 * @param <TargetModelType> target model type
 * @param <ValueType> aggregation value type
 */
public interface IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, ValueType>
{
	/**
	 * @return source model filters. Only source models which are accepted by this filters will be considered for aggregation
	 */
	ICompositeQueryFilter<SourceModelType> filter();

	/**
	 * Set dynamic attribute on which this aggregation maps to.
	 * 
	 * @param dynAttribute
	 * @return this
	 */
	IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, ValueType> setDynAttribute(ModelDynAttributeAccessor<TargetModelType, ValueType> dynAttribute);

	/** @return dynamic attribute on which this aggregation maps to. */
	ModelDynAttributeAccessor<TargetModelType, ValueType> getDynAttribute();

	/** @return SQL code to calculate this aggregation */
	String getSql(Properties ctx, List<Object> sqlParamsOut);

	/**
	 * Creates an {@link IAggregator} instance which will be used to aggregate the values from source models.
	 * 
	 * @param targetModel target model, on which the aggregated value will be set as a dynamic attribute.
	 * @return aggregator instance
	 */
	IAggregator<ValueType, SourceModelType> createAggregator(final TargetModelType targetModel);
}
