package org.eevolution.mrp.api;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_PP_Product_Planning;

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
