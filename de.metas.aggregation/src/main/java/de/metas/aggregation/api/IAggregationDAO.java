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
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;

import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.util.ISingletonService;

public interface IAggregationDAO extends ISingletonService
{
	/**
	 * Retrieves {@link IAggregation} from {@link I_C_Aggregation}
	 * 
	 * @param ctx
	 * @param aggregationId
	 * @return aggregation model; never returns <code>null</code>
	 */
	IAggregation retrieveAggregation(Properties ctx, int aggregationId);

	IQueryBuilder<I_C_AggregationItem> retrieveAllItemsQuery(I_C_Aggregation aggregation);

	List<I_C_AggregationItem> retrieveAllItems(I_C_Aggregation aggregation);

	<ModelType> IAggregation retrieveDefaultAggregationOrNull(Properties ctx, Class<ModelType> modelClass, Boolean isSOTrx, final String aggregationUsageLevel);

	/**
	 * Make sure given {@link I_C_AggregationItem} is not introducing cycles
	 * 
	 * @param aggregationItemDef
	 * @throws AdempiereException in case a cycle is detected
	 */
	void checkIncludedAggregationCycles(I_C_AggregationItem aggregationItemDef);
}
