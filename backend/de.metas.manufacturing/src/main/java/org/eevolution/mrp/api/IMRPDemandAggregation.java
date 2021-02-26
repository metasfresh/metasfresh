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


import java.util.Date;

import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Product_Planning;

/**
 * MRP Demands aggregator which at the end will create the {@link IMRPDemand}.
 * 
 * @author tsa
 *
 */
public interface IMRPDemandAggregation
{
	/** Creates and return an {@link IMRPDemand} based on what we have aggregated so far */
	IMRPDemand createMRPDemand();

	/**
	 * Called by API when a new instance is created. Please don't call it.
	 * 
	 * @param productPlanning
	 */
	void init(I_PP_Product_Planning productPlanning);

	/** @return date when the aggregated demands needs to be supplied */
	Date getDateStartSchedule();

	/** @return true if given MRP record can be aggregated */
	boolean canAdd(final I_PP_MRP mrpDemand);

	/** Adds the given MRP demand to be aggregated */
	void addMRPDemand(final I_PP_MRP mrpDemand);

	/** @return true if this aggregation contains MRP demands which were aggregated */
	boolean hasDemands();
}
