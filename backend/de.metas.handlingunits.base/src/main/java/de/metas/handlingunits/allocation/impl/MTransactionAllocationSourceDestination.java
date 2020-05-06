package de.metas.handlingunits.allocation.impl;

import org.compiere.model.I_M_Transaction;

import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.impl.MTransactionProductStorage;
import de.metas.util.Check;

/**
 * Wraps a given inbound {@link I_M_Transaction} to a source of allocation.
 *
 * @author tsa
 *
 */
public class MTransactionAllocationSourceDestination extends AbstractAllocationSourceDestination
{
	public MTransactionAllocationSourceDestination(final I_M_Transaction mtrx)
	{
		super(new MTransactionProductStorage(mtrx),
				(I_M_HU_Item)null, // HU Item
				mtrx);
		Check.assumeNotNull(mtrx, "mtrx not null");
	}
}
