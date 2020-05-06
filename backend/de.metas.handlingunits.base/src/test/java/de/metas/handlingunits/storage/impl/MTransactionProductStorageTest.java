package de.metas.handlingunits.storage.impl;

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

import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;

import de.metas.handlingunits.storage.IProductStorage;

public class MTransactionProductStorageTest extends AbstractProductStorageTest
{
	@Override
	protected IProductStorage createStorage(final String qtyStr, final boolean reversal, final boolean outboundTrx)
	{
		final BigDecimal qty = new BigDecimal(qtyStr);

		final String movementType;
		if (outboundTrx)
		{
			movementType = X_M_Transaction.MOVEMENTTYPE_CustomerShipment;
		}
		else
		{
			movementType = X_M_Transaction.MOVEMENTTYPE_VendorReceipts;
		}

		final I_M_Transaction mtrx = helper.createMTransaction(movementType, product, qty);

		final MTransactionProductStorage storage = new MTransactionProductStorage(mtrx);
		return storage;
	}
}
