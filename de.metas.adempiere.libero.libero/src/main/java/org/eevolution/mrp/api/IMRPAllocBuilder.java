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


import java.util.Collection;

import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;

/**
 * Creates {@link I_PP_MRP_Alloc} records for allocating an MRP Supply to particular MRP Demands.
 * 
 * @author tsa
 *
 */
public interface IMRPAllocBuilder
{
	/**
	 * Create {@link I_PP_MRP_Alloc}s
	 */
	void build();

	/**
	 * Set MRP Demands to be linked to given MRP Supply
	 * 
	 * @param mrpDemands
	 * @return this
	 */
	IMRPAllocBuilder setMRPDemands(final Collection<I_PP_MRP> mrpDemands);

	/**
	 * Convenient method for calling {@link #setMRPDemands(Collection)}.
	 * 
	 * @param mrpDemand
	 * @return this
	 */
	IMRPAllocBuilder setMRPDemand(final I_PP_MRP mrpDemand);

	/**
	 * Set MRP Supply to be allocated.
	 * 
	 * @param mrpSupply
	 * @return this
	 */
	IMRPAllocBuilder setMRPSupply(final I_PP_MRP mrpSupply);
}
