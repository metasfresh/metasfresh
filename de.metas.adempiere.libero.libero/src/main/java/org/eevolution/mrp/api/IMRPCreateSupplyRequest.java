package org.eevolution.mrp.api;

import java.util.List;

import org.eevolution.model.I_PP_MRP;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Request object used to group all parameters needed by {@link org.eevolution.mrp.spi.IMRPSupplyProducer#createSupply(IMRPCreateSupplyRequest)} in order to create the supply document.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IMRPCreateSupplyRequest extends de.metas.material.planning.IMaterialRequest
{
	/**
	 * Gets single MRP Demand Record that needs to be allocated.
	 * 
	 * NOTE: in case there are more MRP demand records that needs to be allocated, this method will return null
	 * 
	 * @return MRP demand record or null
	 */
	I_PP_MRP getMRPDemandRecordOrNull();

	/**
	 * @return all MRP Demand records
	 */
	List<I_PP_MRP> getMRPDemandRecords();
}
