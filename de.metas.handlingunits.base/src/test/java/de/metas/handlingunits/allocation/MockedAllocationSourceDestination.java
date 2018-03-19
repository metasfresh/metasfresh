package de.metas.handlingunits.allocation;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.lang.IPair;
import org.compiere.util.Util;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU_Item;

public class MockedAllocationSourceDestination implements IAllocationSource, IAllocationDestination
{
	/**
	 * Any quantity to Load/Unload.
	 *
	 * i.e. it will accept the required quantity.
	 */
	public static final BigDecimal ANY = new BigDecimal("0");

	private BigDecimal qtyToUnload = null;
	private BigDecimal qtyToLoad = null;

	@Override
	public IAllocationResult unload(final IAllocationRequest request)
	{
		final boolean outTrx = true;
		return processRequest(request, getQtyToUnload(), outTrx);
	}

	@Override
	public IAllocationResult load(final IAllocationRequest request)
	{
		final boolean outTrx = false;
		return processRequest(request, getQtyToLoad(), outTrx);
	}

	private IAllocationResult processRequest(final IAllocationRequest request, final BigDecimal qtyActual, final boolean outTrx)
	{
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request);

		final IAllocationRequest requestActual;
		if (Util.same(qtyActual, MockedAllocationSourceDestination.ANY))
		{
			requestActual = request;
		}
		else
		{
			requestActual = AllocationUtils.createQtyRequest(request, qtyActual);
		}

		final Object referencedModel = AllocationUtils.getReferencedModel(requestActual);
		final I_M_HU_Item huItem = null; // no HU item
		final I_M_HU_Item vhuItem = null; // no VHU item
		final HUTransactionCandidate trx = new HUTransactionCandidate(referencedModel,
				huItem,
				vhuItem,
				requestActual,
				outTrx);

		result.subtractAllocatedQty(requestActual.getQty());
		result.addTransaction(trx);

		return result;
	}

	private BigDecimal getQtyToUnload()
	{
		Check.assumeNotNull(qtyToUnload, "qtyToUnload not null");
		return qtyToUnload;
	}

	public void setQtyToUnload(final BigDecimal qtyToUnload)
	{
		this.qtyToUnload = qtyToUnload;
	}

	private BigDecimal getQtyToLoad()
	{
		Check.assumeNotNull(qtyToLoad, "qtyToLoad not null");
		return qtyToLoad;
	}

	public void setQtyToLoad(final BigDecimal qtyToLoad)
	{
		this.qtyToLoad = qtyToLoad;
	}

	@Override
	public List<IPair<IAllocationRequest, IAllocationResult>> unloadAll(final IHUContext huContext)
	{
		throw new UnsupportedOperationException("not implemented");
	}
}
