package org.eevolution.mrp.api.impl;

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


import java.util.HashMap;
import java.util.Map;

import org.adempiere.util.Check;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Product_Planning;
import org.eevolution.mrp.api.IMRPDemandAggregation;
import org.eevolution.mrp.api.IMRPDemandAggregationFactory;

import de.metas.material.planning.pporder.LiberoException;

public class MRPDemandAggregationFactory implements IMRPDemandAggregationFactory
{
	private final Map<String, Class<? extends IMRPDemandAggregation>> orderPolicy2mrpDemandAggregationClasses = new HashMap<>();

	public MRPDemandAggregationFactory()
	{
		super();

		// Register standard MRP demand classes
		registerMRPDemandAggregationClass(X_PP_Product_Planning.ORDER_POLICY_Lot_For_Lot, LFL_MRPDemandAggregation.class);
		registerMRPDemandAggregationClass(X_PP_Product_Planning.ORDER_POLICY_PeriodOrderQuantity, POQ_MRPDemandAggregation.class);
	}

	@Override
	public void registerMRPDemandAggregationClass(final String orderPolicy, final Class<? extends IMRPDemandAggregation> mrpDemandAggregationClass)
	{
		Check.assumeNotEmpty(orderPolicy, "orderPolicy not empty");
		Check.assumeNotNull(mrpDemandAggregationClass, "mrpDemandAggregationClass not null");

		orderPolicy2mrpDemandAggregationClasses.put(orderPolicy, mrpDemandAggregationClass);
	}

	@Override
	public IMRPDemandAggregation createMRPDemandAggregator(final I_PP_Product_Planning productPlanning)
	{
		Check.assumeNotNull(productPlanning, "productPlanning not null");
		final String orderPolicy = productPlanning.getOrder_Policy();
		final Class<? extends IMRPDemandAggregation> mrpDemandAggregationClass = orderPolicy2mrpDemandAggregationClasses.get(orderPolicy);
		if (mrpDemandAggregationClass == null)
		{
			throw new LiberoException("No " + IMRPDemandAggregation.class + " was found for order policy: " + orderPolicy
					+ "\n @PP_Product_Planning_ID@: " + productPlanning);
		}

		final IMRPDemandAggregation mrpDemandAggregation;
		try
		{
			mrpDemandAggregation = mrpDemandAggregationClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new LiberoException("Cannot instantiate: " + mrpDemandAggregationClass, e);
		}

		mrpDemandAggregation.init(productPlanning);

		return mrpDemandAggregation;
	}
}
