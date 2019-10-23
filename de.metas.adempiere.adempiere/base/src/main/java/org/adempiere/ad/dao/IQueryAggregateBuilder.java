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


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.impl.SumQueryAggregateColumnBuilder;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.model.ModelColumn;

/**
 * Aggregates a list of source models, based on a given model column.
 *
 * As a result you will get the list of models from model column. Each of those models (target models) will have set dynamic attributes for the aggregated values that we calculated along.
 *
 * @author tsa
 *
 * @param <SourceModelType>
 * @param <TargetModelType>
 */
public interface IQueryAggregateBuilder<SourceModelType, TargetModelType>
{
	/**
	 * Aggregate source models and return resulting target models.
	 *
	 * Please note that resulting target models will have the aggregated values as dynamic attributes.
	 *
	 * @return target/aggregated models
	 */
	List<TargetModelType> aggregate();

	/**
	 * Creates an COUNT-IF aggregations.
	 *
	 * e.g. We use this aggregation when we want to count how many source models where wich matched the filter that we specified in the COUNT-IF aggregation.
	 *
	 * @param dynAttribute dynamic attribute which we will set to retrieved target models, and which will contain the value of this aggregation
	 * @return COUNT-IF aggregation
	 */
	IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, Integer> countIf(ModelDynAttributeAccessor<TargetModelType, Integer> dynAttribute);

	/**
	 * Create a SUM aggregation.
	 *
	 * @param dynAttribute dynamic attribute which we will set to retrieved target models, and which will contain the value of this aggregation
	 * @param amountColumn
	 * @return SUM aggregation
	 */
	SumQueryAggregateColumnBuilder<SourceModelType, TargetModelType> sum(ModelDynAttributeAccessor<TargetModelType, BigDecimal> dynAttribute, ModelColumn<SourceModelType, ?> amountColumn);
}
