package de.metas.handlingunits.allocation.impl;

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


import org.adempiere.util.Check;
import org.compiere.model.I_M_Transaction;

import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.impl.MTransactionProductStorage;

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
