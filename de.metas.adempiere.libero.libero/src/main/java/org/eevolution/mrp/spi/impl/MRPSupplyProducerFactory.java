package org.eevolution.mrp.spi.impl;

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


import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.spi.IMRPSupplyProducer;
import org.eevolution.mrp.spi.IMRPSupplyProducerFactory;

import de.metas.material.planning.IMaterialPlanningContext;

public class MRPSupplyProducerFactory implements IMRPSupplyProducerFactory
{
	private final CompositeMRPSupplyProducer producers = new CompositeMRPSupplyProducer();

	public MRPSupplyProducerFactory()
	{
		// Register default producers
		registerSupplyProducer(new ProductMRPSupplyProducer());
		registerSupplyProducer(new OrderMRPSupplyProducer());
		registerSupplyProducer(new ForecastMRPSupplyProducer());
		//
		registerSupplyProducer(new DDOrderMRPSupplyProducer());
		registerSupplyProducer(new RequisitionMRPSupplyProducer());
		registerSupplyProducer(new PPOrderMRPSupplyProducer());
	}

	@Override
	public void registerSupplyProducer(final IMRPSupplyProducer supplyProducer)
	{
		producers.addMRPSupplyProducer(supplyProducer);
	}

	@Override
	public IMRPSupplyProducer getSupplyProducer(final IMaterialPlanningContext mrpContext)
	{
		return producers.getSupplyProducers(mrpContext);
	}

	@Override
	public List<IMRPSupplyProducer> getSupplyProducers(final String tableName)
	{
		return producers.getSupplyProducers(tableName);
	}

	@Override
	public IMRPSupplyProducer getAllSupplyProducers()
	{
		return producers;
	}

	@Override
	public List<IMRPSupplyProducer> getAllSupplyProducersList()
	{
		return producers.getAllSupplyProducers();
	}

	@Override
	public void notifyQtyOnHandAllocated(final IMaterialPlanningContext mrpContext,
			final IMRPExecutor mrpExecutor,
			final List<IMRPDemandToSupplyAllocation> mrpDemandsToSupplyAllocations)
	{
		if (Check.isEmpty(mrpDemandsToSupplyAllocations))
		{
			return;
		}

		final IMRPBL mrpBL = Services.get(IMRPBL.class);
		final IMRPSupplyProducer producers = getAllSupplyProducers();

		for (final IMRPDemandToSupplyAllocation mrpDemandToSupplyAllocation : mrpDemandsToSupplyAllocations)
		{
			final I_PP_MRP mrpSupply = mrpDemandToSupplyAllocation.getMRPSupply();
			if (!mrpBL.isQtyOnHandReservation(mrpSupply))
			{
				continue;
			}
			
			producers.onQtyOnHandReservation(mrpContext, mrpExecutor, mrpDemandToSupplyAllocation);
		}
	}
}
