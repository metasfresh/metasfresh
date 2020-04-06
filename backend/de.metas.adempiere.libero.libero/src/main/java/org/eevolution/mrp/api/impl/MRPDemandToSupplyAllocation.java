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


import java.math.BigDecimal;

import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;

import de.metas.util.Check;

/* package */class MRPDemandToSupplyAllocation implements IMRPDemandToSupplyAllocation
{
	private final I_PP_MRP mrpDemand;
	private final I_PP_MRP mrpSupply;
	private final BigDecimal qtyAllocated;

	public MRPDemandToSupplyAllocation(final I_PP_MRP mrpDemand, final I_PP_MRP mrpSupply, final BigDecimal qtyAllocated)
	{
		super();

		this.mrpDemand = mrpDemand;
		this.mrpSupply = mrpSupply;

		Check.assumeNotNull(qtyAllocated, "qtyAllocated not null");
		this.qtyAllocated = qtyAllocated;
	}

	@Override
	public I_PP_MRP getMRPDemand()
	{
		return mrpDemand;
	}

	@Override
	public I_PP_MRP getMRPSupply()
	{
		return mrpSupply;
	}

	@Override
	public BigDecimal getQtyAllocated()
	{
		return qtyAllocated;
	}

	@Override
	public boolean isMRPDemandFullyAllocated()
	{
		final BigDecimal mrpDemandQty = mrpDemand.getQty();
		final boolean mrpDemandFullyAllocated = mrpDemandQty.compareTo(qtyAllocated) <= 0;
		return mrpDemandFullyAllocated;
	}
}
