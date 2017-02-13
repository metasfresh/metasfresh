package de.metas.handlingunits;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.util.lang.IReference;

import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.impl.CompositeHUTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;

/**
 * Implementors can be registered using {@link IHUTrxBL#addListener(IHUTrxListener)} or to the {@link CompositeHUTrxListener} instance returned by {@link IHUContext#getTrxListeners()}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUTrxListener
{
	/**
	 * Method called after a transaction line was processed. This default implementation does nothing.
	 *
	 * @param huContext
	 * @param trxLine
	 */
	default void trxLineProcessed(final IHUContext huContext, final I_M_HU_Trx_Line trxLine)
	{
		// nothing
	}

	/**
	 * Method called after the whole transaction was processed. This default implementation does nothing.
	 *
	 * @param trxHdrRef
	 * @param trxLines
	 */
	default void afterTrxProcessed(IReference<I_M_HU_Trx_Hdr> trxHdrRef, List<I_M_HU_Trx_Line> trxLines)
	{
		// nothing
	}

	/**
	 * Method called when a whole HU Load is completed. This default implementation does nothing.
	 *
	 * NOTE: this is the last method which is called, right before the final result is returned.
	 *
	 * @param huContext
	 * @param loadResults
	 */
	default void afterLoad(IHUContext huContext, List<IAllocationResult> loadResults)
	{
		// nothing
	}

	/**
	 * This default implementation does nothing.
	 * 
	 * @param hu handling unit (never null)
	 * @param parentHUItemOld
	 */
	// TODO: we shall have a proper transaction for this case
	default void huParentChanged(final I_M_HU hu, I_M_HU_Item parentHUItemOld)
	{
		// nothing
	}

	/**
	 * Called by {@link HULoader} each time a single unload/load transaction was performed. This default implementation does nothing.
	 *
	 * @param huContext
	 * @param unloadTrx
	 * @param loadTrx
	 */
	default void onUnloadLoadTransaction(IHUContext huContext, IHUTransaction unloadTrx, IHUTransaction loadTrx)
	{
		// nothing
	}

	/**
	 * Called when a split is performed. This default implementation does nothing.
	 *
	 * @param huContext
	 * @param unloadTrx transaction on source HU
	 * @param loadTrx transaction on destination HU
	 */
	default void onSplitTransaction(IHUContext huContext, IHUTransaction unloadTrx, IHUTransaction loadTrx)
	{
		// nothing
	}
}
