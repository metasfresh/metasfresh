package de.metas.aggregation.api;

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
	Aggregation retrieveAggregation(Properties ctx, int aggregationId);

	IQueryBuilder<I_C_AggregationItem> retrieveAllItemsQuery(I_C_Aggregation aggregation);

	<ModelType> Aggregation retrieveDefaultAggregationOrNull(Properties ctx, Class<ModelType> modelClass, Boolean isSOTrx, final String aggregationUsageLevel);

	/**
	 * Make sure given {@link I_C_AggregationItem} is not introducing cycles
	 * 
	 * @param aggregationItemDef
	 * @throws AdempiereException in case a cycle is detected
	 */
	void checkIncludedAggregationCycles(I_C_AggregationItem aggregationItemDef);
}
