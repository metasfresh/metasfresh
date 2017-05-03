package org.eevolution.mrp.spi;

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

import org.adempiere.util.ISingletonService;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPExecutor;

import de.metas.material.planning.IMaterialPlanningContext;

public interface IMRPSupplyProducerFactory extends ISingletonService
{
	void registerSupplyProducer(final IMRPSupplyProducer supplyProducer);

	IMRPSupplyProducer getSupplyProducer(final IMaterialPlanningContext mrpContext);

	List<IMRPSupplyProducer> getSupplyProducers(final String tableName);

	IMRPSupplyProducer getAllSupplyProducers();

	List<IMRPSupplyProducer> getAllSupplyProducersList();

	void notifyQtyOnHandAllocated(IMaterialPlanningContext mrpContext,
			IMRPExecutor mrpExecutor,
			List<IMRPDemandToSupplyAllocation> mrpDemandsToSupplyAllocations);

}
