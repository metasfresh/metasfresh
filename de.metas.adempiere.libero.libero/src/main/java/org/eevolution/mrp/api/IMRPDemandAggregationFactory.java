package org.eevolution.mrp.api;

import org.eevolution.model.I_PP_Product_Planning;

import de.metas.util.ISingletonService;

/**
 * MRP Demands aggregations factory.
 * 
 * @author tsa
 *
 */
public interface IMRPDemandAggregationFactory extends ISingletonService
{
	/**
	 * Registers a new aggregation class to be used for a given Ordering Policy.
	 * 
	 * @param orderPolicy ordering policy (see {@link I_PP_Product_Planning#getOrder_Policy()})
	 * @param mrpDemandAggregationClass aggregation class
	 */
	void registerMRPDemandAggregationClass(final String orderPolicy, final Class<? extends IMRPDemandAggregation> mrpDemandAggregationClass);

	/**
	 * Creates a new instance of {@link IMRPDemandAggregation} based on the ordering policy specified in <code>productPlanning</code>.
	 * 
	 * Depends on the actual implementation of {@link IMRPDemandAggregation}, more config parameters will be set from product planning.
	 * 
	 * @param productPlanning
	 * @return new aggregation instance
	 */
	IMRPDemandAggregation createMRPDemandAggregator(final I_PP_Product_Planning productPlanning);
}
