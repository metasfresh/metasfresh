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


import java.math.BigDecimal;

import org.eevolution.model.I_PP_MRP;

public interface IMRPDemandToSupplyAllocation
{

	public I_PP_MRP getMRPDemand();

	public I_PP_MRP getMRPSupply();

	public BigDecimal getQtyAllocated();

	/**
	 * 
	 * @return true if the {@link #getMRPDemand()} is fully allocated (i.e. MRP Demand's Qty is less or equals with {@link #getQtyAllocated()})
	 */
	boolean isMRPDemandFullyAllocated();

}
