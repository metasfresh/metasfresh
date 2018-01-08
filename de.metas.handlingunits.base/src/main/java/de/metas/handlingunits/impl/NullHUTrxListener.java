package de.metas.handlingunits.impl;

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


import java.util.List;

import org.adempiere.util.lang.IReference;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;

/**
 * An {@link IHUTrxListener} which does nothing.
 *
 * @author tsa
 *
 */
public final class NullHUTrxListener implements IHUTrxListener
{
	public static final NullHUTrxListener instance = new NullHUTrxListener();

	private NullHUTrxListener()
	{
		super();
	}

	@Override
	public void trxLineProcessed(final IHUContext huContext, final I_M_HU_Trx_Line trxLine)
	{
		// nothing
	}

	@Override
	public void huParentChanged(final I_M_HU hu, final I_M_HU_Item parentHUItemOld)
	{
		// nothing
	}

	@Override
	public void afterTrxProcessed(final IReference<I_M_HU_Trx_Hdr> trxHdrRef, final List<I_M_HU_Trx_Line> trxLines)
	{
		// nothing
	}

	@Override
	public void afterLoad(final IHUContext huContext, final List<IAllocationResult> loadResults)
	{
		// nothing;
	}

	@Override
	public void onUnloadLoadTransaction(final IHUContext huContext, final IHUTransactionCandidate unloadTrx, final IHUTransactionCandidate loadTrx)
	{
		// nothing
	}

	@Override
	public void onSplitTransaction(final IHUContext huContext, final IHUTransactionCandidate unloadTrx, final IHUTransactionCandidate loadTrx)
	{
		// nothing
	}
}
